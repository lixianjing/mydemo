package com.xian.myapp.activities;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xian.myapp.R;
import com.xian.myapp.aidl.IRemoteService;
import com.xian.myapp.base.BaseActivity;

import java.util.Random;

public class AidlService extends Service {

    private Thread uiThread;
    private int i;

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        AidlService getService() {
            // Return this instance of LocalService so clients can call public methods
            return AidlService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        uiThread = Thread.currentThread();
        return super.bindService(service, conn, flags);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getStringAndInt(String str) throws RemoteException {
            Log.e("lmf",">>>getStringAndIntLongTime>>>>>>>>>>>"+(Thread.currentThread() == uiThread));
            return str+(i++);
        }

        @Override
        public synchronized String getStringAndIntLongTime(String str) throws RemoteException {
            Log.e("lmf",">>>getStringAndIntLongTime>>>>>>>>>>>"+(Thread.currentThread() == uiThread));
            try {
                Thread.sleep(5 * 1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return str+(i++);
        }

        @Override
        public void getStringAndIntLongTimeOneWay(String str) throws RemoteException {
            Log.e("lmf",">>>getStringAndIntLongTimeOneWay>>>>>1111>>>>>>"+(Thread.currentThread() == uiThread));
            try {
                Thread.sleep(5 * 1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            Log.e("lmf",">>>getStringAndIntLongTimeOneWay>>>>>>>2222>>>>"+(Thread.currentThread() == uiThread));
        }
    };
}
