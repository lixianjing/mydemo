package com.xian.myapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xian.myapp.MyApplication;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.openapi.OpenApiController;
import com.xian.myapp.utils.CrashHandler;
import com.xian.myapp.volley.VolleyActivity;

public class UnCatchExceptionActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */

    MyApplication app;

    private String s=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext=this;


        s.equals("xxx");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }




}
