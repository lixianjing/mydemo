package com.xian.myapp.logs;

import android.text.TextUtils;
import android.util.Log;

/**
 * @author 吴书永
 *         Log工具类，是Log类的扩展
 */
public class SLLog {

    public static boolean isLog = true;

    private static final int MAX_LENGTH = 2000;

    /**
     * Information
     */
    public static void i(String tag, String msg) {
        if (isLog && msg != null) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isLog && msg != null) {
            Log.v(tag, msg);
        }
    }

    /**
     * Debug
     */
    public static void d(String tag, String msg) {
        if (isLog && msg != null) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isLog && msg != null) {
            Log.w(tag, msg);
        }
    }

    /**
     * Error
     */
    public static void e(String tag, String msg) {
        if (isLog && msg != null) {
            Log.e(tag, msg);
        }
    }

    /**
     * Error
     */
    public static void e(String tag, Throwable throwable) {
        if (isLog) {
            Log.e(tag, throwable.getMessage(), throwable);
        }
    }

    /**
     * Error
     */
    public static void e(String tag, String msg, Throwable throwable) {
        if (isLog) {
            Log.e(tag, msg, throwable);
        }
    }

    public static void longLog(String tag, String tempData) {
        if (!isLog) {
            return;
        }
        if (TextUtils.isEmpty(tempData)) {
            SLLog.d(tag, "result is null.");
            return;
        }
        final int len = tempData.length();

        int count = len / MAX_LENGTH;
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                SLLog.d(tag, tempData.substring(i * MAX_LENGTH, (i + 1) * MAX_LENGTH));
            }
            int mode = len % MAX_LENGTH;
            if (mode > 0) {
                SLLog.d(tag, tempData.substring(MAX_LENGTH * count, len));
            }
        } else {
            SLLog.d(tag, tempData);
        }
    }
}
