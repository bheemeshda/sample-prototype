/*
 * HttpEngine.java
 *
 */

package com.example.bheemesh.myapplication.http;

import com.example.bheemesh.myapplication.Utils.Constants;
import com.example.bheemesh.myapplication.Utils.HttpUtils;
import com.example.bheemesh.myapplication.Utils.Logger;
import com.example.bheemesh.myapplication.Utils.StringUtil;

import org.apache.poi.util.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpEngine {

    public static String currentAPNName = null;
    public static NHProxy nhProxy = null;
    public static String userAgent = "Android";
    public static String baseURL;
    public static String requestparams = Constants.EMPTY_STRING;
    public static boolean sShowNetworkFailureToast = false;

    // Singleton instance of HTTPEngine
    private static final HttpEngine mInst = new HttpEngine();

    // Object for synchronizing all the direct requests
    private static final Object mDirSyncObj = new Object();

    // A set which maintains the currently being processed items
    private final HashSet<HttpRequest> mProcSet = new HashSet<HttpRequest>();

    // An array of double ended queue of requests maintained based on priority
    private final ArrayList<LinkedList<HttpRequest>> mReqListArray = new ArrayList<LinkedList<HttpRequest>>();

    // An array of Thread instances used for processing
    private final Thread[] mThreadArray = new Thread[HttpRequest.PRIORITY_COUNT + 5];

    // Header map which stores extra header information. On 08 Apr 2014, it had
    // only one entry "nhut"
    private final Map<String, String> mHeaderMap = new ConcurrentHashMap<String, String>();

    // Timeout for connections
    public static final int TIMEOUT_MSEC = 60000;

    // Helper for logging
    private static int req_id = 1;

    private HttpEngine() {
        // Private constructor to avoid object creation externally

        for (int i = 0; i < HttpRequest.PRIORITY_COUNT; i++) {
            mReqListArray.add(new LinkedList<HttpRequest>());
        }
    }

    public static HttpEngine getInstance() {
        return mInst;
    }

    public void addHeaderValues(String key, String value) {
        mHeaderMap.clear();
        mHeaderMap.put(key, value);
    }

    public synchronized void addRequest(HttpRequest request) {
        request.time = System.currentTimeMillis();
        request.req_id = req_id++;
        Logger.e("HTTP", "Request added " + request.req_id + " "
                + request.priority + " " + request.time + " " + request.url);
        Logger.e("HTTP", "Post body " + request.req_id + " " + request.postData);

        if (null == request || null == request.url || isAlreadyAdded(request))
            return;

        adjustPriority(request);

        if (HttpRequest.CONTENT_TYPE_ICON == request.responseType
                || HttpRequest.CONTENT_TYPE_IMAGE == request.responseType)
            mReqListArray.get(request.priority).addFirst(request);
        else
            mReqListArray.get(request.priority).addLast(request);

        startThread(request.priority);
    }

    public synchronized void removeRequest(HttpRequest request) {
        for (int i = 0; i < mReqListArray.size(); i++) {
            List<HttpRequest> list = mReqListArray.get(i);
            list.remove(request);
        }
    }

    public synchronized void removeAllRequest() {
        for (int i = 0; i < mReqListArray.size(); i++) {

            List<HttpRequest> list = mReqListArray.get(i);

            for (int j = list.size() - 1; j >= 0; j--) {
                HttpRequest request = list.get(j);
                if (request.httpType.equals(HttpRequest.HTTP_METHOD_GET))
                    list.remove(j);
                else
                    request.listener = null;
            }
        }
    }

    public synchronized void removeAllImageRequests() {
        for (int i = 0; i < mReqListArray.size(); i++) {

            List<HttpRequest> list = mReqListArray.get(i);

            for (int j = list.size() - 1; j >= 0; j--) {
                HttpRequest request = list.get(j);
                if (HttpRequest.CONTENT_TYPE_IMAGE == request.responseType
                        || HttpRequest.CONTENT_TYPE_ICON == request.responseType)
                    list.remove(j);
            }
        }
    }

    public synchronized void resetConnection() {
        for (HttpRequest request : mProcSet)
            request.listener = null;
    }

    public byte[] requestURL(HttpRequest httpRequest) throws Exception {

        httpRequest.req_id = req_id++;
        httpRequest.time = System.currentTimeMillis();
        Logger.e("HTTP", "Direct " + httpRequest.req_id + " "
                + httpRequest.time + " " + httpRequest.url);
        Logger.e("HTTP", "Post body " + httpRequest.req_id + " "
                + httpRequest.postData);

        synchronized (mDirSyncObj) {
            return request(httpRequest);
        }
    }

    private byte[] request(HttpRequest httpRequest) throws Exception {
        byte[] data = null;
        long time = System.currentTimeMillis() - httpRequest.time;
        Logger.e("HTTP", "Started " + httpRequest.req_id + " " + time);

        do {
            try {
                data = request(httpRequest, false);
                if (httpRequest.responseCode == HttpURLConnection.HTTP_MULT_CHOICE
                        || httpRequest.responseCode == HttpURLConnection.HTTP_MOVED_PERM
                        || httpRequest.responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                        || httpRequest.responseCode == HttpURLConnection.HTTP_SEE_OTHER) {

                    HttpRequest redirect = new HttpRequest(
                            httpRequest.responseType, httpRequest.location,
                            httpRequest.httpType, null, httpRequest.priority,
                            httpRequest.listener);
                    httpRequest = redirect;
                } else {
                    break;
                }
            } catch (Exception e) {
                data = null;
                break;
            }
        } while (true);

        time = System.currentTimeMillis() - httpRequest.time;
        Logger.e("HTTP", "HTTP over " + httpRequest.req_id + " " + time);

        return data;
    }

    private byte[] request(HttpRequest httpRequest, boolean temp)
            throws Exception {

        if (httpRequest == null)
            return null;

        byte[] data = null;

        try {

            String url = httpRequest.url;
            if (httpRequest.mNewsHuntUrl) {

                // This is temporary fix , need to fix from server side
                if (!baseURL.endsWith("/") && !baseURL.endsWith("?")) {
                    baseURL = baseURL + "/";
                }

                // Bheemesh: 07 Jul 2014: When the url starts with http, then no
                // need to add base URL
                if (!httpRequest.url.startsWith("http://")
                        && !httpRequest.url.startsWith("https://")) {
                    url = baseURL + httpRequest.url;
                }

                url = StringUtil.replaceAdditionalColon(url);

                if (httpRequest.addParams
                        && httpRequest.responseType != HttpRequest.CONTENT_TYPE_ICON
                        && httpRequest.responseType != HttpRequest.CONTENT_TYPE_IMAGE) {
                    if (url.contains("&") || url.contains("?")) {
                        url = url + requestparams;
                    } else {
                        url = url + "?" + requestparams;
                    }
                }
            }

            // Needed for the Debugging dont delete
            if (httpRequest.responseType == HttpRequest.CONTENT_TYPE_XML
                    && !url.contains("/openx/ads/index.php")) {
                Logger.d("JU", "url :: " + url);
                // Logger.d("JU", "id :: " + httpRequest.requestId);
                Logger.d("JU", "postBody :: " + httpRequest.postData);
            }

            if (httpRequest.responseType != HttpRequest.CONTENT_TYPE_IMAGE
                    || httpRequest.responseType != HttpRequest.CONTENT_TYPE_ICON) {
                httpRequest.useGZIP = true;
            }

            int repeatCount = httpRequest.repeatCount;
            do {
                try {
                    data = postViaHttpConnection(url, httpRequest);
                    if ("application/binary".equals(httpRequest.contentType)) {
                        data = StringUtil.getUnicodeFromAscii(
                                Integer.parseInt(httpRequest.languageMask),
                                data, 0, data.length);
                    }

                    httpRequest.isHttpOK = true;
                    break;

                } catch (Exception e) {
                    repeatCount--;

                    if (repeatCount <= 0)
                        throw e;
                }
            } while (repeatCount > 0);

        } catch (Exception ex) {
            if (httpRequest.listener != null) {
                httpRequest.listener.handleHttpException(ex, httpRequest);
                httpRequest.mRespException = true;
            } else {
                throw ex;
            }
        }

        return data;
    }

    private void closeConnection(HttpTask task) {
        if (null == task)
            return;

        try {
            if (task.mConn != null)
                task.mConn.disconnect();
        } catch (Exception e) {
            // e.printStackTrace();
        }

        try {
            if (task.mInput != null)
                task.mInput.close();
        } catch (Exception e) {
            // e.printStackTrace();
        }

        try {
            if (task.mOutput != null)
                task.mOutput.close();
        } catch (Exception e) {
            // e.printStackTrace();
        }

        try {
            if (task.mTimer != null)
                task.mTimer.cancel();
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    private void adjustPriority(HttpRequest request) {
        // Priority representations start from 1. We start from 0 internally
        request.priority--;

        if (request.priority < 0
                || request.priority >= HttpRequest.PRIORITY_COUNT) {
            switch (request.responseType) {
                case HttpRequest.CONTENT_TYPE_XML:
                    request.priority = HttpRequest.PRIORITY_CRITICAL;
                    break;

                case HttpRequest.CONTENT_TYPE_TEXT:
                    request.priority = HttpRequest.PRIORITY_HIGH;
                    break;

                case HttpRequest.CONTENT_TYPE_ICON:
                case HttpRequest.CONTENT_TYPE_ADS:
                    request.priority = HttpRequest.PRIORITY_MEDIUM;
                    break;

                case HttpRequest.CONTENT_TYPE_IMAGE:
                case HttpRequest.CONTENT_TYPE_BINARY:
                    request.priority = HttpRequest.PRIORITY_LOW;
                    break;

                default:
                    request.priority = HttpRequest.PRIORITY_VERYLOW;
                    break;
            }
        }
    }

    private void startThread(int priority) {
        for (int i = priority; i < mThreadArray.length; i++) {
            if (null == mThreadArray[i]) {
                mThreadArray[i] = new Thread(new Process(i));
                mThreadArray[i].start();
                break;
            }
        }
    }

    // always verify the host - dont check for certificate
    private final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * Trust every server - dont check for any certificate
     */
    private void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            // //// e.printStackTrace();
        }
    }

    private HttpURLConnection createHttpConnection(String url) throws Exception {
        HttpURLConnection httpCon = null;
        URL urlObj = new URL(url);
        Proxy p = HttpUtils.getProxy();

        if (p != null) {
            if (urlObj.getProtocol().toLowerCase().equals("https")) {
                trustAllHosts();
                httpCon = (HttpsURLConnection) urlObj.openConnection(p);
                ((HttpsURLConnection) httpCon)
                        .setHostnameVerifier(DO_NOT_VERIFY);
            } else {
                httpCon = (HttpURLConnection) urlObj.openConnection(p);
            }

        } else {
            if (urlObj.getProtocol().toLowerCase().equals("https")) {
                trustAllHosts();
                httpCon = (HttpsURLConnection) urlObj.openConnection();
                ((HttpsURLConnection) httpCon)
                        .setHostnameVerifier(DO_NOT_VERIFY);
            } else {
                httpCon = (HttpURLConnection) urlObj.openConnection();
            }
        }

        return httpCon;
    }

    private byte[] postViaHttpConnection(String url, HttpRequest httpRequest)
            throws Exception {

        HttpTask task = new HttpTask();
        byte data[] = null;

        try {
            // postData local variable is used to avoid changing object data
            String postData = httpRequest.postData;
            if (null == postData)
                postData = "";

            // httpType local variable is used to avoid changing object data
            String httpType = httpRequest.httpType;
            if (postData.length() <= 0)
                httpType = HttpRequest.HTTP_METHOD_GET;

            if (httpType.equals(HttpRequest.HTTP_METHOD_GET)) {
                task.mConn = createHttpConnection(url + postData);
            } else {
                task.mConn = createHttpConnection(url);
                task.mConn.setRequestMethod(HttpRequest.HTTP_METHOD_POST);
                task.mConn.setDoOutput(true);
            }

            task.mConn.setConnectTimeout(TIMEOUT_MSEC);
            task.mConn.setReadTimeout(TIMEOUT_MSEC);
            task.mConn.setRequestProperty("Content-Type",
                    httpRequest.propContentType);

            if (httpRequest.headerParams) {
                for (Entry<String, String> entry : mHeaderMap.entrySet())
                    task.mConn.addRequestProperty(entry.getKey(),
                            entry.getValue());
            }

            if (httpRequest.useGZIP)
                task.mConn.setRequestProperty("Accept-Encoding", "gzip");

            if (!httpType.equals(HttpRequest.HTTP_METHOD_GET)) {
                // write the POST data
                task.mOutput = task.mConn.getOutputStream();

                byte postmsg[] = postData.getBytes("UTF-8");
                for (int i = 0; i < postmsg.length; i++) {
                    task.mOutput.write(postmsg[i]);
                }
            }

            long time = System.currentTimeMillis() - httpRequest.time;
            Logger.e("HTTP", "getResponseCode " + httpRequest.req_id + " "
                    + time);

            // Getting the response code will open the connection,
            // send the request, and read the HTTP response headers.
            // The headers are stored until requested.
            httpRequest.responseCode = task.mConn.getResponseCode();

            time = System.currentTimeMillis() - httpRequest.time;
            Logger.e("HTTP", "getResponseCode over " + httpRequest.req_id + " "
                    + time + " Response " + httpRequest.responseCode);

            httpRequest.contentType = task.mConn.getHeaderField("Content-Type");
            httpRequest.contentEncoding = task.mConn
                    .getHeaderField("Content-Encoding");
            httpRequest.location = task.mConn.getHeaderField("Location");
            httpRequest.languageMask = task.mConn
                    .getHeaderField("Packing-Mask");

            httpRequest.contentLength = task.mConn.getContentLength();
            if (httpRequest.responseCode == HttpURLConnection.HTTP_OK
                    || httpRequest.responseCode == HttpURLConnection.HTTP_MULT_CHOICE
                    || httpRequest.responseCode == HttpURLConnection.HTTP_MOVED_PERM
                    || httpRequest.responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                    || httpRequest.responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
                task.mInput = task.mConn.getInputStream();
            } else {
                throw new IOException("HTTP Invalid Response Code: "
                        + httpRequest.responseCode);
            }

            time = System.currentTimeMillis() - httpRequest.time;
            Logger.e("HTTP", "readData " + httpRequest.req_id + " " + time);

            if (null != task.mInput) {
                if ("gzip".equals(httpRequest.contentEncoding)) {
                    data = IOUtils.toByteArray(new GZIPInputStream(
                            new BufferedInputStream(task.mInput)));
                } else {
                    data = IOUtils.toByteArray(new BufferedInputStream(
                            task.mInput));
                }
            }

            time = System.currentTimeMillis() - httpRequest.time;
            Logger.e("HTTP", "readData over " + httpRequest.req_id + " " + time);
        } catch (Exception e) {
            // e.printStackTrace();
            throw e;
        } finally {
            closeConnection(task);
        }

        return data;
    }

    private synchronized boolean isAlreadyAdded(HttpRequest request) {
        if (!request.mIgnoreDuplicate)
            return false;

        for (HttpRequest temp : mProcSet) {
            if (request.equals(temp))
                return true;
        }

        for (List<HttpRequest> list : mReqListArray) {
            for (HttpRequest temp : list) {
                if (request.equals(temp))
                    return true;
            }
        }

        return false;
    }

    private static class HttpTask extends TimerTask {

        public HttpURLConnection mConn;
        public InputStream mInput;
        public OutputStream mOutput;
        public Timer mTimer;

        @Override
        public void run() {
            mInst.closeConnection(this);
        }
    }

    private static class Process implements Runnable {

        final int mIndex;

        public Process(int index) {
            mIndex = index;
        }

        @Override
        public void run() {
            while (true) {
                HttpRequest request = null;

                synchronized (mInst) {
                    for (int i = 0; i < mInst.mReqListArray.size()
                            && i <= mIndex; i++) {
                        if (!mInst.mReqListArray.get(i).isEmpty()) {
                            request = mInst.mReqListArray.get(i).removeFirst();
                            if (request != null)
                                mInst.mProcSet.add(request);
                            break;
                        }
                    }
                }

                if (null == request)
                    break;

                try {
                    request.content = mInst.request(request);
                } catch (Exception e) {
                    // e.printStackTrace();
                } finally {
                    synchronized (mInst) {
                        mInst.mProcSet.remove(request);
                    }

                    if (!request.mRespException && request.listener != null) {
                        request.listener.handleHttpResponse(request);
                    }
                }

                long time = System.currentTimeMillis() - request.time;
                Logger.e("HTTP", "Handled " + request.req_id + " " + time);
            }

            synchronized (mInst) {
                // This is done to clear the thread entry from array. Do not
                // remove.
                mInst.mThreadArray[mIndex] = null;
            }
        }
    }
}
