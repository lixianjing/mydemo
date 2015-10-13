package com.xian.myapp.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;

public class AidlActivity extends BaseActivity {

    private AidlService mService;
    private boolean mBound;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        text = (TextView) findViewById(R.id.text1);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time=System.nanoTime();
                Log.e("lmf", "btn1>>>>>"+mService.getStringAndNumber("hello>>"));
                Log.e("lmf", "btn1>>>>>"+(System.nanoTime()-time)/1000);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time=System.nanoTime();
                Log.e("lmf", "btn2>>>>>" + mService.getStringAndNumberLongTime("hello>>"));
                Log.e("lmf", "btn2>>>>>"+(System.nanoTime()-time)/1000);

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time=System.nanoTime();
                Log.e("lmf", "btn3>>>>>" + mService.getStringAndNumberLongTime("hello>>"));
                Log.e("lmf", "btn3>>>>>"+(System.nanoTime()-time)/1000);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long time=System.nanoTime();
                        Log.e("lmf", "1111>>>>>" + mService.getStringAndNumberLongTime("1111>>"));
                        Log.e("lmf", "1111>>>>>"+(System.nanoTime()-time)/1000);
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long time=System.nanoTime();
                        Log.e("lmf", "2222>>>>>" + mService.getStringAndNumberLongTime("222>>"));
                        Log.e("lmf", "2222>>>>>"+(System.nanoTime()-time)/1000);
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long time=System.nanoTime();
                        Log.e("lmf", "333>>>>>" + mService.getStringAndNumberLongTime("333>>"));
                        Log.e("lmf", "333>>>>>"+(System.nanoTime()-time)/1000);
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long time=System.nanoTime();
                        Log.e("lmf", "444>>>>>" + mService.getStringAndNumberLongTime("4444>>"));
                        Log.e("lmf", "444>>>>>"+(System.nanoTime()-time)/1000);
                    }
                }).start();

            }
        });
    }
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            AidlService.LocalBinder binder = (AidlService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            text.setText("服务器启动了");
            Log.e("lmf","onServiceConnected>>>>>");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            text.setText("服务器没有启动");
            Log.e("lmf", "onServiceDisconnected>>>>>");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, AidlService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }


}
