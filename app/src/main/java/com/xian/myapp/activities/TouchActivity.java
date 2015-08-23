package com.xian.myapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.volley.VolleySingleton;

/**
 * Created by limingfeng on 2015/8/5.
 */
public class TouchActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("lmf",  "TouchActivity>onTouchEvent>" + event.getAction());
        boolean result=super.onTouchEvent(event);
        Log.e("lmf","TouchActivity>onTouchEvent>result>>"+result);
        return result;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("lmf",  "TouchActivity>dispatchTouchEvent>" + ev.getAction());
        boolean result=super.dispatchTouchEvent(ev);
        Log.e("lmf","TouchActivity>dispatchTouchEvent>result>>"+result);
        return result;
    }
}
