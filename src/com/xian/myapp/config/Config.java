package com.xian.myapp.config;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.xian.myapp.logs.SLLog;
import com.xian.myapp.utils.StreamUtil;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 基础配置
 * User: 孙伟力
 * Date: 15/2/11
 * Time: 下午2:14
 */
public class Config {
    private String clientAgent;
    private String versionId;
    private String agency;
    private int width;
    private int height;
    private static Config instance = new Config();

    public static Config getInstance() {
        return instance;
    }

    private Config() {
    }

    public void init(Application context) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        clientAgent = Build.MODEL + "#" + width + "*" + height;

        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionId = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            loadAgency(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private final void loadAgency(Application context) throws IOException {
        int index;
        String name = "";
        String value = "";

        ArrayList<String> lines = StreamUtil.loadStringLinesFromStream(context.getAssets().open("agency.txt"));

        for (String str : lines) {
            index = str.indexOf("=");
            if (index > 0) {
                name = str.substring(0, index);
                if (index + 1 < str.length()) {
                    value = str.substring(index + 1);
                    if (name.equals("agencyid") && !TextUtils.isEmpty(value)) {
                        agency = value;
                    } else if (name.equals("log") && value.equals("true")) {
                        SLLog.isLog = true;
                    }
                }
            }
        }
    }

    public String getCustomerId() {
        return "877";
    }

    public String getClientAgent() {
        return clientAgent;
    }

    public int getScreenWidth() {
        return width;
    }

    public int getScreenHeight() {
        return height;
    }

    public String getVersionId() {
        return versionId;
    }

    public String getVersionName() {
        return "V" + getVersionId();
    }

    public String getAgency() {
        return agency;
    }

    public String getModel() {
        return "Generic/AnyPhone";
    }

    public String getClientTest() {
        return "false";
    }

}
