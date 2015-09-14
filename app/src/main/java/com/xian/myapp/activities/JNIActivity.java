package com.xian.myapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.jni.JNIControl;

public class JNIActivity extends BaseActivity {



    private Button btn1;
    private Button btn2;
    private Button btn3;
    JNIControl control=new JNIControl();

    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn1:
                    Log.e("lmf", ">>>>>" + control.stringFromJNI("xxxx"));
                    break;
                case R.id.btn2:
                    Log.e("lmf", ">>>>>" + control.stringFromJNILongTime("hello long time"));
                    break;
                case R.id.btn3:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        btn1.setOnClickListener(myClickListener);
        btn2.setOnClickListener(myClickListener);
        btn3.setOnClickListener(myClickListener);


    }







}
