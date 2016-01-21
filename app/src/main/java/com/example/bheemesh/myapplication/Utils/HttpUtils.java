package com.example.bheemesh.myapplication.Utils;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;

import com.example.bheemesh.myapplication.http.HttpEngine;
import com.example.bheemesh.myapplication.http.NHProxy;

import java.net.Proxy;

/**
 * Created by bheemesh on 19/1/16.
 *
 * @author bheemesh
 */
public class HttpUtils {
    public static String sConnectionType = "0";

    public static boolean isNetworkAvailable(Context aContext) {
        if (aContext == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) aContext.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void getConnectionType() {
        try {

            ConnectivityManager mConnectivity = (ConnectivityManager) Utils.getApplication()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            // Skip if no connection, or background data disabled
            NetworkInfo info = mConnectivity.getActiveNetworkInfo();
            HttpEngine.currentAPNName = info.getExtraInfo();
            // default is '0'
            HttpUtils.sConnectionType = "0";

            if (info == null || !mConnectivity.getBackgroundDataSetting()) {
                HttpUtils.sConnectionType = "0";
                return;
            }

            // Only update if WiFi or 3G is connected and not roaming
            if ((null != info) && (info.isAvailable() && info.isConnected())) {

                int netType = info.getType();
                int netSubtype = info.getSubtype();
                if (netType == ConnectivityManager.TYPE_WIFI) {
                    HttpUtils.sConnectionType = "w";
                    return;
                } else if (((netType == ConnectivityManager.TYPE_MOBILE) || (netType == ConnectivityManager.TYPE_WIMAX))
                        && ((netSubtype == TelephonyManager.NETWORK_TYPE_LTE) || (netSubtype == TelephonyManager.NETWORK_TYPE_HSPAP))) {
                    HttpUtils.sConnectionType = "4G";

                } else if ((netType == ConnectivityManager.TYPE_MOBILE)
                        && ((netSubtype == TelephonyManager.NETWORK_TYPE_EHRPD)
                        || (netSubtype == TelephonyManager.NETWORK_TYPE_EVDO_B)
                        || (netSubtype == TelephonyManager.NETWORK_TYPE_HSDPA)
                        || (netSubtype == TelephonyManager.NETWORK_TYPE_HSPA)
                        || (netSubtype == TelephonyManager.NETWORK_TYPE_HSUPA) || (netSubtype == TelephonyManager.NETWORK_TYPE_UMTS))) {
                    HttpUtils.sConnectionType = "3G";
                } else if ((netType == ConnectivityManager.TYPE_MOBILE)
                        && ((netSubtype == TelephonyManager.NETWORK_TYPE_EVDO_0) || (netSubtype == TelephonyManager.NETWORK_TYPE_EVDO_A))) {
                    HttpUtils.sConnectionType = "3C";
                } else if ((netType == ConnectivityManager.TYPE_MOBILE)
                        && (netSubtype == TelephonyManager.NETWORK_TYPE_CDMA)) {
                    HttpUtils.sConnectionType = "2C";
                } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                    HttpUtils.sConnectionType = "2G";
                }
            }

        } catch (Exception e) {
        }
    }

    public static Proxy getProxy() {
        Proxy p = null;
        try {
            HttpUtils.getConnectionType();

            if (!HttpUtils.sConnectionType.equals("w")) {
                if (HttpEngine.nhProxy == null
                        || !HttpEngine.nhProxy.currentAPNName
                        .equalsIgnoreCase(HttpEngine.currentAPNName)) {
                    /*
					 * Read Proxy from databse in following condition 1) Current
					 * proxy object is null 2) If last read apn name is
					 * different from the current one i.e. user switches between
					 * access points
					 */
                    HttpUtils.setProxy();
                }
                java.net.InetSocketAddress inet = java.net.InetSocketAddress
                        .createUnresolved(HttpEngine.nhProxy.proxyAddress,
                                Integer.parseInt(HttpEngine.nhProxy.proxyPort));

                p = new java.net.Proxy(java.net.Proxy.Type.HTTP, inet);

            }
        } catch (Exception e) {
            p = null;
        }
        return p;
    }

    public static void setProxy() {

        try {
            disableProxy();
            HttpEngine.nhProxy = new NHProxy();
            HttpUtils.getConnectionType();
            HttpEngine.nhProxy.currentAPNName = HttpEngine.currentAPNName;

            Cursor cursor = null;
            if (!Utils.isEmpty(HttpEngine.currentAPNName)) {
                cursor = Utils.getApplication()
                        .getContentResolver()
                        .query(Uri.parse("content://telephony/carriers/preferapn_no_update"),
                                new String[]{"proxy", "port"},
                                "apn= " + "'" + HttpEngine.currentAPNName + "'",
                                null, null);
            } else {
                cursor = Utils.getApplication()
                        .getContentResolver()
                        .query(Uri.parse("content://telephony/carriers/preferapn_no_update"),
                                new String[]{"proxy", "port"}, null, null,
                                null);
            }

            if (cursor != null) {
                try {
                    cursor.moveToFirst();
                    HttpEngine.nhProxy.proxyAddress = cursor.getString(cursor
                            .getColumnIndex("proxy"));
                    HttpEngine.nhProxy.proxyPort = cursor.getString(cursor
                            .getColumnIndex("port"));

                } catch (Exception e) {
                    disableProxy();
                } finally {
                    cursor.close();
                }
            } else {
                setProxySettings(Utils.getApplication(),
                        HttpEngine.currentAPNName);
            }

            if (null != HttpEngine.nhProxy
                    && !Utils.isEmpty(HttpEngine.nhProxy.proxyAddress)) {
                // Do nothing
            } else {
                disableProxy();
            }
        } catch (Exception e) {
            disableProxy();
        }
    }

    public static void disableProxy() {
        HttpEngine.nhProxy = null;
    }

    public static void setProxySettings(Context context) {
        try {
            String proxyAddress = null;
            int proxyPort = 0;
            TelephonyManager telephonyManager = ((TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE));
            String operatorName = telephonyManager.getNetworkOperatorName();

            if (operatorName.toLowerCase().contains("airtel")) {
                proxyAddress = "100.1.200.99";
                proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("vodafone")) {
                proxyAddress = "10.10.1.100";
                proxyPort = 9401;
            } else if (operatorName.toLowerCase().contains("hutch")) {
                proxyAddress = "10.10.1.100";
                proxyPort = 9401;
            } else if (operatorName.toLowerCase().contains("aircel")) {
                proxyAddress = "192.168.35.201";
                proxyPort = 8081;
            } else if (operatorName.toLowerCase().contains("idea")) {
                proxyAddress = "10.4.42.15";
                proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("bpl")) {
                proxyAddress = "10.0.0.10";
                proxyPort = 9401;
            } else if (operatorName.toLowerCase().contains("spice")) {
                proxyAddress = "10.200.200.3";
                proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("reliance")) {
                proxyAddress = "10.239.221.5";
                proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("docomo")) {
                proxyAddress = "10.124.94.7";
                proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("mtnl")
                    && operatorName.toLowerCase().contains("mumbai")) {
                proxyAddress = "172.16.39.10";
                proxyPort = 9401;
            } else if (operatorName.toLowerCase().contains("mtnl")
                    && operatorName.toLowerCase().contains("delhi")) {
                proxyAddress = "172.16.31.10";
                proxyPort = 9401;
            } else if (operatorName.toLowerCase().contains("cellone")
                    && operatorName.toLowerCase().contains("north")) {
                proxyAddress = "10.132.194.196";
                proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("cellone")
                    && operatorName.toLowerCase().contains("south")) {
                proxyAddress = "10.31.54.2";
                proxyPort = 9401;
            } else if (operatorName.toLowerCase().contains("cellone")
                    && operatorName.toLowerCase().contains("east")) {
                proxyAddress = "192.168.81.163";
                proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("cellone")
                    && operatorName.toLowerCase().contains("west")) {
                proxyAddress = "10.100.3.2";
                proxyPort = 9209;
            }
            if (proxyAddress != null) {
                System.setProperty("http.proxyHost", proxyAddress);
                System.setProperty("http.proxyPort", "" + proxyPort);
            }
        } catch (Exception e) {

        }
    }

    public static void setProxySettings(Context context, String aApnName) {
        try {
            String proxyAddress = null;
            int proxyPort = 0;
            TelephonyManager telephonyManager = ((TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE));
            String operatorName = telephonyManager.getNetworkOperatorName();

            if (operatorName.toLowerCase().contains("airtel")
                    && aApnName.equalsIgnoreCase("airtelfun.com")) {
                proxyAddress = "100.1.200.99";
                proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("vodafone")
                    && aApnName.equalsIgnoreCase("portalnmms")) {
                proxyAddress = "10.10.1.100";
                proxyPort = 9401;
            } else if (operatorName.toLowerCase().contains("hutch")
                    && aApnName.equalsIgnoreCase("portalnmms")) {
                proxyAddress = "10.10.1.100";
                proxyPort = 9401;
            } else if (operatorName.toLowerCase().contains("aircel")
                    && (aApnName.equalsIgnoreCase("aircelgprs.pr") || aApnName
                    .equalsIgnoreCase("aircelgprs.po"))) {
                proxyAddress = "192.168.35.201";
                proxyPort = 8081;
            } else if (operatorName.toLowerCase().contains("idea")
                    && aApnName.equalsIgnoreCase("imis")) {
                proxyAddress = "10.4.42.15";
                proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("bpl")
                    && aApnName.equalsIgnoreCase("mizone")) {
                proxyAddress = "10.0.0.10";
                proxyPort = 8080;
                // } else if (operatorName.toLowerCase().contains("spice")&&
                // aApnName.equalsIgnoreCase("")) {
                // proxyAddress = "10.200.200.3";
                // proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("reliance")
                    && aApnName.equalsIgnoreCase("rcomwap")) {
                proxyAddress = "10.239.221.5";
                proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("docomo")
                    && aApnName.equalsIgnoreCase("TATA.DOCOMO.DIVE.IN")) {
                proxyAddress = "10.124.94.7";
                proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("mtnl")
                    /* && operatorName.toLowerCase().contains("mumbai") */ && aApnName
                    .equalsIgnoreCase("mtnl.net")) {
                proxyAddress = "10.10.10.10";
                proxyPort = 9401;
                // } else if (operatorName.toLowerCase().contains("mtnl")
                // && operatorName.toLowerCase().contains("delhi")&&
                // aApnName.equalsIgnoreCase("airtelfun.com")) {
                // proxyAddress = "172.16.31.10";
                // proxyPort = 9401;
            } else if (operatorName.toLowerCase().contains("cellone")
                    && operatorName.toLowerCase().contains("north")
                    && aApnName.equalsIgnoreCase("wapnorth.cellone.in")) {
                proxyAddress = "10.132.194.196";
                proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("cellone")
                    && operatorName.toLowerCase().contains("south")
                    && (aApnName.equalsIgnoreCase("wapsouth.cellone.in") || aApnName
                    .equalsIgnoreCase("bsnlwap"))) {
                proxyAddress = "10.31.54.2";
                proxyPort = 9401;
            } else if (operatorName.toLowerCase().contains("cellone")
                    && operatorName.toLowerCase().contains("east")
                    && (aApnName.equalsIgnoreCase("wapeast.cellone.in"))) {
                proxyAddress = "192.168.81.163";
                proxyPort = 8080;
            } else if (operatorName.toLowerCase().contains("cellone")
                    && operatorName.toLowerCase().contains("west")
                    && aApnName.equalsIgnoreCase("wapwest.cellone.in")) {
                proxyAddress = "10.100.3.2";
                proxyPort = 9209;
            } else if ((operatorName.toLowerCase().contains("cellone") || operatorName
                    .toLowerCase().contains("bsnl"))
                    && (aApnName.equalsIgnoreCase("bsnllive") || aApnName
                    .equalsIgnoreCase("bsnlportal"))) {
                proxyAddress = "10.220.67.131";
                proxyPort = 8080;
            }

            // if proxy and port found - set it, else clear proxy
            if (!Utils.isEmpty(proxyAddress)) {
                // NetworkCheck.proxyAddress = proxyAddress;
                // NetworkCheck.proxyPort = proxyPort;
                // System.setProperty("http.proxyHost", proxyAddress);
                // System.setProperty("http.proxyPort", "" + proxyPort);
                HttpEngine.nhProxy.proxyAddress = proxyAddress;
                HttpEngine.nhProxy.proxyPort = "" + proxyPort;
            } else {
                disableProxy();
            }
        } catch (Exception e) {
            disableProxy();
        }
    }
}
