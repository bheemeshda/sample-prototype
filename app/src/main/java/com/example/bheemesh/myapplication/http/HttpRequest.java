package com.example.bheemesh.myapplication.http;

import android.os.Handler;

import com.example.bheemesh.myapplication.Utils.Constants;

/**
 * This class defines the parameters and properties of a network request made.
 * Also include properties which HttpEngine uses for internal processing.
 * 
 * @author arun.babu
 * 
 */

public class HttpRequest {

	/**
	 * Constant for Image content type in the response object.
	 */
	public static final int CONTENT_TYPE_IMAGE = 0;
	/**
	 * Constant for Text content type in the response object.
	 */
	public static final int CONTENT_TYPE_TEXT = 1;
	/**
	 * Constant for XML content type in the response object.
	 */
	public static final int CONTENT_TYPE_XML = 2;
	/**
	 * Constant for Binary content type in the response object.
	 */
	public static final int CONTENT_TYPE_BINARY = 3;
	/**
	 * Constant for Ads content type in the response object.
	 */
	public static final int CONTENT_TYPE_ADS = 4;
	/**
	 * Constant for Icon content type in the response object.
	 */
	public static final int CONTENT_TYPE_ICON = 5;

	// Maintain this list strictly ascending one by one. HTTP Engine queue is
	// based on this
	public static final int PRIORITY_CRITICAL = 0;
	public static final int PRIORITY_HIGH = 1;
	public static final int PRIORITY_MEDIUM = 2;
	public static final int PRIORITY_LOW = 3;
	public static final int PRIORITY_VERYLOW = 4;
	public static final int PRIORITY_COUNT = 5; // Ensure this is updated when
												// prorities are modified

	public static final String PROP_CONTENT_TYPE_APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded;charset=utf-8";
	public static final String PROP_CONTENT_TYPE_MULTIPART_FORM_DATA = "multipart/form-data";

	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_POST = "POST";

	public static final int HTTP_DEF_RETRY_COUNT = 2;

	/**
	 * Listener for this transaction.
	 */
	public HttpListener listener;
	/**
	 * Unique identifier to identify this Http Transaction.
	 */
	public int responseType;
	/**
	 * URL to post Http requests.
	 */
	public String url;
	/**
	 * Type of the HttpRequest. (GET/POST)
	 */
	public String httpType;
	/**
	 * payload to sent with HttpPost request.
	 */
	public String postData = Constants.EMPTY_STRING;
	/**
	 * Priority of the transaction. 0 is highest priority and bigger the number
	 * lower the priority
	 */
	public int priority;
	/**
	 * Actual payload of the Http Response.
	 */
	public byte[] content;
	/**
	 * Handler which initiated the httpRequest
	 * 
	 */
	public Handler mHandler;

	public String languageMask;
	public String requestId;
	public boolean isHttpOK;
	public String contentType;
	public String contentEncoding;
	public int contentLength;
	public boolean useGZIP;
	public int responseCode;
	public String location;
	public boolean addParams = true;
	public boolean headerParams = false;
	public boolean mNewsHuntUrl = true;
	public long time;
	public long req_id;
	public int repeatCount = HTTP_DEF_RETRY_COUNT;
	public boolean mIgnoreDuplicate = true;
	public boolean mRespException = false;

	public String propContentType = PROP_CONTENT_TYPE_APPLICATION_X_WWW_FORM_URLENCODED;

	/**
	 * Creates a new instance of HttpRequest
	 * 
	 * 
	 * @param responseType
	 *            Key for this transaction.
	 * @param url
	 *            URL to connect.
	 * @param httpType
	 *            Type of the request.
	 * @param postData
	 *            Post data if the type is POST.
	 * @param priority
	 * @param priority
	 *            Priority of the transaction.
	 * @param listener
	 *            Listener for this transaction.
	 */
	public HttpRequest(int responseType, String url, String httpType,
			String postData, int priority, HttpListener listener) {
		this.responseType = responseType;
		this.url = url;
		this.httpType = httpType;
		this.postData = postData;
		this.listener = listener;
		this.priority = priority;
	}

	boolean isImageRequest() {
		if (CONTENT_TYPE_IMAGE == responseType)
			return true;

		if (url.toLowerCase().endsWith(".jpg")
				|| url.toLowerCase().endsWith(".png")
				|| url.toLowerCase().endsWith(".gif")
				|| url.toLowerCase().endsWith(".bmp")) {
			responseType = CONTENT_TYPE_IMAGE;
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result
				+ ((httpType == null) ? 0 : httpType.hashCode());
		result = prime * result
				+ ((postData == null) ? 0 : postData.hashCode());
		result = prime * result
				+ ((requestId == null) ? 0 : requestId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HttpRequest other = (HttpRequest) obj;

		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;

		if (httpType == null) {
			if (other.httpType != null)
				return false;
		} else if (!httpType.equals(other.httpType))
			return false;

		if (requestId == null) {
			if (other.requestId != null)
				return false;
		} else if (!requestId.equals(other.requestId))
			return false;

		return true;
	}

}
