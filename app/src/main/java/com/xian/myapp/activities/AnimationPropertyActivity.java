package com.xian.myapp.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.model.Flake;
import com.xian.myapp.openapi.OpenApiController;
import com.xian.myapp.utils.Utils;
import com.xian.myapp.volley.VolleyActivity;
import com.xian.myapp.widget.NormalFlakeView;
import com.xian.myapp.widget.PropertyFlakeView;

import java.util.ArrayList;

public class AnimationPropertyActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    private Context mContext;
    private Button btn1, btn2;
    private PropertyFlakeView flakeView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_property_flake);
        mContext = this;
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        flakeView = (PropertyFlakeView) findViewById(R.id.property_flake_view);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 1&2: 确定起始状态，结束状态
                flakeView.start(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher));
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
        Log.e("lmf", ">>>>>onSaveInstanceState>>>>>>");
    }
}
