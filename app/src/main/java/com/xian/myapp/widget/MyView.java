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
public class MyView extends View{

    private static final String TAG="MyView";

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
        Log.e("lmf",TAG+">dispatchTouchEvent>result>>"+false);
        return false;
    }

}
