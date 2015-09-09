package com.xian.myapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.openapi.OpenApiController;
import com.xian.myapp.volley.VolleyActivity;

public class ViewActivity extends BaseActivity {

    private ViewStub viewStub;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        mContext=this;

        viewStub= (ViewStub) findViewById(R.id.view_stub);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStub.setVisibility(View.VISIBLE);
                initViewStub(viewStub);
            }
        });

    }

    private void initViewStub(final ViewStub viewStub){

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStub.setVisibility(View.GONE);
            }
        });
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
