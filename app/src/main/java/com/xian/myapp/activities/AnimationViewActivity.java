package com.xian.myapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.openapi.OpenApiController;
import com.xian.myapp.volley.VolleyActivity;

public class AnimationViewActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    private Button btn1, btn2;
    private TextView translation;
    private TextView rotate;
    private TextView scale;
    private TextView alpha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_view);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        translation = (TextView) findViewById(R.id.translation);
        rotate = (TextView) findViewById(R.id.rotate);
        scale = (TextView) findViewById(R.id.scale);
        alpha = (TextView) findViewById(R.id.alpha);

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 1&2: 确定起始状态，结束状态
                TranslateAnimation tAnim = new TranslateAnimation(0, 400, 0, 0);//横向位移400个单位
                RotateAnimation rAnima = new RotateAnimation(0, 70);//顺时针旋转70度
                ScaleAnimation sAnima = new ScaleAnimation(0, 5, 0, 5);//横向放大5倍，纵向放大5倍
                AlphaAnimation aAnima = new AlphaAnimation(1.0f, 0.0f);//从全不透明变为全透明
                // 3: 确定持续时间
                tAnim.setDuration(2000);
                rAnima.setDuration(2000);
                sAnima.setDuration(2000);
                aAnima.setDuration(2000);

                // 4: 确定Interpolator
                tAnim.setInterpolator(new AccelerateDecelerateInterpolator());

                // 启动动画
                translation.startAnimation(tAnim);
                rotate.startAnimation(rAnima);
                scale.startAnimation(sAnima);
                alpha.startAnimation(aAnima);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();


    }
}

