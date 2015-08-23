package com.xian.myapp;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import com.xian.myapp.logs.SLLog;

import java.util.List;

public class MyApplication extends Application {

    private static final String TAG="MyApplication";

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();    //调用父类
        SLLog.e("lmf",">>>>>MyApplication>>>>>onCreate>>>");
        sContext=this;
        if(shouldInit()){
            SLLog.e("lmf",">>>>>MyApplication>>>>>onCreate>>isfirst>>");
        }
    }


    /**
     * 只有应用第一次调用的时候才返回true
     *
     * @return
     */
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    public final static Context getContext() {
        return sContext;
    }
}
