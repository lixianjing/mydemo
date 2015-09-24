package com.xian.myapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.utils.ToastUtils;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class TestActivity extends BaseActivity {

    private Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory() + "/test/" + "GJAndroidClient15-dev-debug.apk";
                File file = new File(path);
                if (file.exists()) {
                    PathClassLoader mPathClassLoader = new PathClassLoader(file.getAbsolutePath(), mContext.getClassLoader());
                    try {
                        Class mClass = mPathClassLoader.loadClass("com.ganji.android.core.util.TimeUtil");
                        Log.e("lmf", ">>>mClass>>>>" + mClass);
                        Method[] allMethod = mClass.getDeclaredMethods();
                        Method[] myMethod = mClass.getMethods();
                        Log.e("lmf", ">>>getDeclaredMethods>>>>" + allMethod.length);
                        Log.e("lmf", ">>>getMethods>>>>" + myMethod.length);
                        Method mMethod = mClass.getMethod("getTimeStamp", null);
                        Object obj = mMethod.invoke(null, null);
                        Log.e("lmf", ">>>date>>>>" + (String)obj);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtils.showToast("没有找到相关的apk包");
                }

            }
        });
    }


}
