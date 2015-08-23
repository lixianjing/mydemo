package com.xian.myapp.activities;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;

/**
 * Created by limingfeng on 2015/8/5.
 */
public class UpLoadAndDownLoadActivity extends BaseActivity {

    private Button btn1;
    private Button btn2;
    private TextView text;

    private DownloadManager downloadManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_download);
        btn1= (Button) findViewById(R.id.btn1);
        btn2= (Button) findViewById(R.id.btn2);
        text= (TextView) findViewById(R.id.text);


        downloadManager = (DownloadManager) getSystemService( Context.DOWNLOAD_SERVICE);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://img.meilishuo.net/css/images/AndroidShare/Meilishuo_3.6.1_10006.apk");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                long reference = downloadManager.enqueue(request);
            }
        });


    }

}
