package com.xian.myapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.jni.JNIControl;

import java.io.IOException;

public class OkHttpActivity extends BaseActivity {


    private OkHttpClient client = new OkHttpClient();

    private Button btn1;
    private Button btn2;

    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn1:
                    try {
                        run("http://www.baidu.com");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn2:
                    try {
                        runUi("http://www.baidu.com");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn3:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        btn1.setOnClickListener(myClickListener);
        btn2.setOnClickListener(myClickListener);


    }




    void run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("lmf", ">>>>>onFailure>>>>>>" + request.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.e("lmf", ">>>>>onResponse>>>>>>" + response.body().toString());
            }
        });

    }

    void runUi(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        Log.e("lmf", ">>>>>onResponse>>>>>>" + response.body().toString());

    }


}
