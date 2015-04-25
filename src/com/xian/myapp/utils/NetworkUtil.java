package com.xian.myapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.xian.myapp.MyApplication;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

/**
 * Network Helper Util
 *
 * @author ZhangYunfang
 */
public final class NetworkUtil {

    /**
     * 网络连接枚举类型
     */
    public enum NetworkConnectType {
        MOBILE, WIFI
    }

    private NetworkUtil() {
    }

    /**
     * 设置网络，主要是判断是否使用WAP连接
     */
    public static void setupNetwork(Context context, HttpClient httpClient) {
        if (isUsingWap(context)) {
            final String host = Proxy.getDefaultHost();
            final int port = Proxy.getDefaultPort();

            if (!TextUtils.isEmpty(host) && port != -1) {
                final HttpHost proxy = new HttpHost(host, port);
                httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            }
        }
    }

    /**
     * 设置网络
     */
    public static HttpURLConnection setupNetwork(Context context, String urlStr) throws IOException {
        return setupNetwork(context, new URL(urlStr));
    }

    public static HttpURLConnection setupNetwork(Context context, URL url) throws IOException {
        HttpURLConnection httpURLConnection = null;
        if (isUsingWap(context)) {
            final String host = Proxy.getDefaultHost();
            final int port = Proxy.getDefaultPort();
            if (!TextUtils.isEmpty(host) && port != -1) {
                httpURLConnection = (HttpURLConnection) url.openConnection(new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(host, port)));
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
        } else {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }
        return httpURLConnection;
    }

    /**
     * 判断是否使用WAP连接
     */
    public static boolean isUsingWap(Context context) {
        boolean result = false;
        if (isNetworkAvailable(context) && getNetworkConnectType(context) == NetworkConnectType.MOBILE) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                final String netExtraInfo = networkInfo.getExtraInfo();
                if (!TextUtils.isEmpty(netExtraInfo) && netExtraInfo.toLowerCase().contains("wap")) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 判断是否使用3g连接
     */
    public static boolean isUsing3g(Context context) {
        boolean result = false;
        if (isNetworkAvailable(context) && getNetworkConnectType(context) == NetworkConnectType.MOBILE) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                final String netExtraInfo = networkInfo.getExtraInfo();
                if (!TextUtils.isEmpty(netExtraInfo) && netExtraInfo.toLowerCase().contains("3g")) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean result = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if (networkInfos != null) {
                final int length = networkInfos.length;
                for (int i = 0; i < length; i++) {
                    if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获得当前网络连接类型
     */
    public static NetworkConnectType getNetworkConnectType(Context context) {
        NetworkConnectType networkConnectType = null;
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // mobile
            final NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            // wifi
            final NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mobile != null && (mobile.getState() == NetworkInfo.State.CONNECTED || mobile.getState() == NetworkInfo.State.CONNECTING)) {
                // mobile
                networkConnectType = NetworkConnectType.MOBILE;
            } else if (wifi != null && (wifi.getState() == NetworkInfo.State.CONNECTED || wifi.getState() == NetworkInfo.State.CONNECTING)) {
                // wifi
                networkConnectType = NetworkConnectType.WIFI;
            }
        }
        return networkConnectType;
    }

    public static boolean isUsingMobileNetwork() {
        Context context = MyApplication.getContext();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return mobile != null && (mobile.getState() == NetworkInfo.State.CONNECTED || mobile.getState() == NetworkInfo.State.CONNECTING);
        }
        return false;
    }

    /**
     * 判断WIFI是否可用
     */
    public static boolean isWIFIAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        /*if (networkInfo == null || !connectivityManager.getBackgroundDataSetting()) {
            return false;
		}*/
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 判断WIFI或者3G网络是否可用
     */
    public static boolean isWIFIor3GNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		/*if (networkInfo == null || !connectivityManager.getBackgroundDataSetting()) {
		    return false;
		}*/
        if (networkInfo == null) {
            return false;
        }
        final int networkType = networkInfo.getType();
        final int networkSubType = networkInfo.getSubtype();
        // Only update if WiFi or 3G is connected
        if (networkType == ConnectivityManager.TYPE_WIFI) {
            return networkInfo.isConnected();
        } else if (networkType == ConnectivityManager.TYPE_MOBILE && networkSubType == TelephonyManager.NETWORK_TYPE_UMTS) {
            return networkInfo.isConnected();
        } else {
            return false;
        }
    }

    /**
     * 判断网络是否处于漫游状态
     */
    public static boolean isNetworkRoaming(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null && tm.isNetworkRoaming()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取接入方式~ //cmwap/cmnet/wifi/uniwap/uninet
     *
     * @param context
     * @return
     */
    public static String getNetworkCarrier(Context context) {
        String typeName = "";
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            if (networkInfo != null) {
                typeName = networkInfo.getExtraInfo();
                if (TextUtils.isEmpty(typeName)) {
                    typeName = networkInfo.getTypeName();
                }
            }

        }
        return typeName;
    }

    /**
     * 获取手机wifi的Ip地址~
     *
     * @param context
     * @return
     */
    public static String getIPAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        // 格式化IP address，例如：格式化前：1828825280，格式化后：192.168.1.109
        String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return ip;
    }

    /**
     * 判断网络类型 在中国，联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO
     *
     * @param context
     * @param type
     * @param subType
     * @return false就是2G true就是3g或wifi
     */
    public static boolean isConnectionFast(Context context, NetworkConnectType type, int subType) {
        if (type == NetworkConnectType.WIFI) {
            return true;
        } else if (type == NetworkConnectType.MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    return false;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isConnectionFast() {
        Context context = MyApplication.getContext();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //在中国，联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO
        int networkType = tm.getNetworkType();
        NetworkUtil.NetworkConnectType type = NetworkUtil.getNetworkConnectType(context);
        return NetworkUtil.isConnectionFast(context, type, networkType);
    }

    /**
     * 获得手机当前的网络连接类型
     */
    public static String getNetworkType() {
        String networkType = "";

        TelephonyManager manager = (TelephonyManager) MyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        NetworkUtil.NetworkConnectType connectType = NetworkUtil.getNetworkConnectType(MyApplication.getContext());
        int subType = manager.getNetworkType();
        if (connectType == NetworkConnectType.MOBILE) {
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_EDGE || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
                networkType = "2g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_0 || subType == TelephonyManager.NETWORK_TYPE_EVDO_A) {
                networkType = "3g";
            } else {
                networkType = "2g";
            }
        } else if (connectType == NetworkConnectType.WIFI) {
            networkType = "wifi";
        } else {
            networkType = "2g";
        }

        return networkType;
    }

    public static String getNetworkType2() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                if (ni.getType() == ConnectivityManager.TYPE_WIFI) {
                    return "wifi";
                } else {
                    TelephonyManager tm = (TelephonyManager) MyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
                    if (tm != null) {
                        switch (tm.getNetworkType()) {
                            case TelephonyManager.NETWORK_TYPE_CDMA:
                                return "2g/cdma"; // ~ 14-64 kbps
                            case TelephonyManager.NETWORK_TYPE_EDGE:
                                return "2g/edge"; // ~ 50-100 kbps
                            case TelephonyManager.NETWORK_TYPE_GPRS:
                                return "2g/gprs"; // ~ 100 kbps
                            case TelephonyManager.NETWORK_TYPE_UMTS:
                                return "3g/umts"; // ~ 400-7000 kbps
                            case TelephonyManager.NETWORK_TYPE_HSDPA:
                                return "3g/hsdpa"; // ~ 2-14 Mbps
                            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                return "3g/evdo_0"; // ~ 400-1000 kbps
                            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                return "3g/evdo_a"; // ~ 600-1400 kbps
                            // Unknown
                            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                                return "2g";
                            default:
                                break;
                        }
                    }
                }
            }
        }
        return "";
    }
}
