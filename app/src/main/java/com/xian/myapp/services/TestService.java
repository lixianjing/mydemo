package com.xian.myapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by lucien on 2015/8/28.
 */
public class TestService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("lmf",">>>>TestService>>>>onCreate>>");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("lmf",">>>>TestService>>>>onStartCommand>>");
        return super.onStartCommand(intent, flags, startId);
    }
}
