package com.xian.myapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.openapi.OpenApiController;
import com.xian.myapp.volley.VolleyActivity;
import com.xian.myapp.widget.FlakeView;

public class FlakeActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    private Context mContext;
    private FlakeView mFlakeView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flake);
        mContext=this;
        mFlakeView=(FlakeView)findViewById(R.id.flake);
        mFlakeView.start(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.ic_launcher));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("lmf",">>>>>onSaveInstanceState>>>>>>");
    }
}
