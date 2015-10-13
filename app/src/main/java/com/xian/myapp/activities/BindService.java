package com.xian.myapp.activities;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class BindService extends Service {


    private int i;
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    private final Random mGenerator = new Random();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        BindService getService() {
            // Return this instance of LocalService so clients can call public methods
            return BindService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * method for clients
     */
    public String getStringAndNumber(String str) {
        return str + ":" + i++;
    }
    /**
     * method for clients
     */
    public synchronized String getStringAndNumberLongTime(String str) {
        Log.e("lmf",">>>>>>>>>"+str);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str + ":" + i++;
    }
}
