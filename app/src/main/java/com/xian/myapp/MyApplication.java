package com.xian.myapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import com.xian.myapp.logs.SLLog;
import com.xian.myapp.utils.CrashHandler;
import com.xian.myapp.utils.Envi;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private static final String TAG="MyApplication";

    private static Context sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();    //调用父类
        sContext=this;
        Envi.initialize(this);
        if(shouldInit()){
            SLLog.e("lmf",">>>>>MyApplication>>>>>onCreate>>isfirst>>");
//            init();
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



    ArrayList<Activity> list = new ArrayList<Activity>();

    public void init(){
        //设置该CrashHandler为程序的默认处理器
        CrashHandler catchExcep = new CrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }

    /**
     * Activity关闭时，删除Activity列表中的Activity对象*/
    public void removeActivity(Activity a){
        list.remove(a);
    }

    /**
     * 向Activity列表中添加Activity对象*/
    public void addActivity(Activity a){
        list.add(a);
    }

    /**
     * 关闭Activity列表中的所有Activity*/
    public void finishActivity(){
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    public final static Context getContext() {
        return sContext;
    }
}
