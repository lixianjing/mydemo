package com.xian.myapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by limingfeng on 2015/8/7.
 */
public class MyLayout1 extends RelativeLayout{

    private static final String TAG="MyLayout1";

    public MyLayout1(Context context) {
        super(context);
    }

    public MyLayout1(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public MyLayout1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("lmf",TAG+">onInterceptTouchEvent>"+ev.getAction());
        boolean result=super.onInterceptTouchEvent(ev);
        Log.e("lmf",TAG+">onInterceptTouchEvent>result>>"+result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("lmf",TAG+">onTouchEvent>"+event.getAction());
        boolean result=super.onTouchEvent(event);
        Log.e("lmf",TAG+">onTouchEvent>result>>"+result);
        return result;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("lmf",TAG+">dispatchTouchEvent>"+ev.getAction());
        boolean result=super.dispatchTouchEvent(ev);
        Log.e("lmf",TAG+">dispatchTouchEvent>result>>"+result);
        return result;
    }
}
