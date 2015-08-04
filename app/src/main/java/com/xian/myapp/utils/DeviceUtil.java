package com.xian.myapp.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.xian.myapp.MyApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class DeviceUtil {

    private static final String MATCHER_TEST_KEY = "test-keys";
    private static final String MATCHER_RELEASE_KEY = "release-keys";
    private static final String MATCHER_SDK = "sdk";

    /**
     * 判断是否为模拟器
     */
    public static boolean isEmulator() {
        boolean isEmulator = false;

        String model = Build.MODEL;
        String product = Build.PRODUCT;
        String tags = Build.TAGS;
        String fingerprint = Build.FINGERPRINT;

        isEmulator = model.contains(MATCHER_SDK) || product.contains(MATCHER_SDK) || tags.contains(MATCHER_TEST_KEY) || !tags.contains(MATCHER_RELEASE_KEY)
                || fingerprint.contains(MATCHER_TEST_KEY) || !fingerprint.contains(MATCHER_RELEASE_KEY);
        return isEmulator;
    }

    /**
     * 获取UUID
     */
    public static String getUUID() {
        final UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 获取到移动设备的IMEI号
     */
    public static String getDeviceImei(Context context) {
        String imei = null;
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager != null) {
            imei = manager.getDeviceId();
        }
        return imei;
    }

    /**
     * 获得手机号码，某些时候为null
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String number = manager.getLine1Number();
        return number;
    }

    /**
     * 获得厂商信息，某些手机为null
     */
    public static String getManufacturerInfo() {
        return System.getProperty("ro.product.manufacturer");
    }

    /**
     * 获取操作系统版本~
     *
     * @param context
     * @return
     */
    public static String getOSVersion(Context context) {
        String version = Build.VERSION.RELEASE;
        return version;
    }

    /**
     * 检查系统版本是否为2.1以上版本
     */
    public static boolean isCheckSystemVersion() {
        boolean isValue = false;
        String systemVersion = Build.VERSION.RELEASE;
        if (systemVersion == "" || systemVersion.length() == 0) {
            return isValue;
        }
        systemVersion = systemVersion.replace(".", "");

        if (systemVersion.length() == 1) {
            int version = 0;
            try {
                version = Integer.valueOf(systemVersion);
            } catch (NumberFormatException e) {
            }
            if (version > 2) {
                return true;
            }
        } else {
            systemVersion = systemVersion.substring(0, 2);
            int version = 0;
            try {
                version = Integer.valueOf(systemVersion);
            } catch (NumberFormatException e) {
            }
            if (version > 21) {
                isValue = true;
            }
        }
        return isValue;
    }

    /**
     * 获取手机客户端UA
     *
     * @param context
     * @return
     */
    public static String getUA(Context context) {
        WebView webview;
        webview = new WebView(context);
        webview.layout(0, 0, 0, 0);
        WebSettings settings = webview.getSettings();
        String ua = settings.getUserAgentString();
        webview = null;
        return ua;
    }

    /**
     * 返回当前可用的内存，单位为K
     */
    public static long getAvailableMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem / 1024;
    }

    /**
     * 获得手机系统总内存，单位为MB
     */
    public static String getTotalMemory() {
        String str1 = "/proc/meminfo";//系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();//读取meminfo第一行，系统内存大小
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;//获得系统总内存，单位KB
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return Formatter.formatFileSize(MyApplication.getContext(), initial_memory);
        //Byte转位KB或MB
    }

    /**
     * 返回手机当前可用内存，单位为MB
     */
    public static String getAvailMemory() {
        //获取android当前可用内存大小
        ActivityManager am = (ActivityManager) MyApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.avaiMem;当前系统可用内存
        return Formatter.formatFileSize(MyApplication.getContext(), mi.availMem);
        //将获得的内存大小规格化
    }

    /**
     * 返回手机wifi mac地址
     */
    public static String getMacAddress(Context context) {

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        return info.getMacAddress();
    }

}
