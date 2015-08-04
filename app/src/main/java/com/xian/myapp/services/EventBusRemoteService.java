package com.xian.myapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.xian.myapp.demo.DemoEvent;
import com.xian.myapp.logs.SLLog;
import de.greenrobot.event.EventBus;

/**
 * User: 李明锋
 * Date: 2015/4/23
 * Time: 17:41
 */
public class EventBusRemoteService extends Service{

    @Override
    public void onCreate() {
        super.onCreate();    //调用父类
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;  //TODO 实现
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DemoEvent event=new DemoEvent("hello");
        SLLog.e("lmf", ">>>>send>>>>" + event.toString() + ":" + System.currentTimeMillis());
        EventBus.getDefault().post(event);
        SLLog.e("lmf", ">>>>send>>>>" + event.toString() + ":" + System.currentTimeMillis());
        return super.onStartCommand(intent, flags, startId);    //调用父类
    }
}
