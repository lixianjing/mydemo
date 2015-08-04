package com.xian.myapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.demo.DemoEvent;
import com.xian.myapp.logs.SLLog;
import com.xian.myapp.services.EventBusRemoteService;
import com.xian.myapp.services.EventBusService;
import de.greenrobot.event.EventBus;

public class EventBusTest extends BaseActivity implements View.OnClickListener{
    /**
     * Called when the activity is first created.
     */

    private Context mContext;
    private Button startActivityBtn;
    private Button startServiceBtn;
    private Button startRemoteServiceBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventbus_main);
        mContext=this;
        startActivityBtn=(Button)findViewById(R.id.start_activity);
        startServiceBtn=(Button)findViewById(R.id.start_service);
        startRemoteServiceBtn=(Button)findViewById(R.id.start_remote_service);
        startActivityBtn.setOnClickListener(this);
        startServiceBtn.setOnClickListener(this);
        startRemoteServiceBtn.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.start_activity){
           Intent intent=new Intent(mContext,EventBusTest1.class);
            startActivity(intent);
        }else if(v.getId()==R.id.start_service){
            Intent intent=new Intent(mContext,EventBusService.class);
            startService(intent);
        }else if(v.getId()==R.id.start_remote_service){
            Intent intent=new Intent(mContext,EventBusRemoteService.class);
            startService(intent);
        }
    }




    //SecondEvent接收函数一
    public void onEventMainThread(DemoEvent event) {
        SLLog.e("lmf",">>>>onEventMainThread>>>>"+event.toString()+":"+System.currentTimeMillis());
        SLLog.d("harvic", "onEventMainThread收到了消息：" + event.getMsg());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //SecondEvent接收函数二
    public void onEventBackgroundThread(DemoEvent event){
        SLLog.e("lmf",">>>>onEventBackgroundThread>>>>"+event.toString()+":"+System.currentTimeMillis());
        SLLog.d("harvic", "onEventBackground收到了消息：" + event.getMsg());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //SecondEvent接收函数三
    public void onEventAsync(DemoEvent event){
        SLLog.e("lmf",">>>>onEventAsync>>>>"+event.toString()+":"+System.currentTimeMillis());
        SLLog.d("harvic", "onEventAsync收到了消息：" + event.getMsg());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onEvent(DemoEvent event) {
        SLLog.e("lmf",">>>>onEvent>>>>"+event.toString()+":"+System.currentTimeMillis());
        SLLog.d("harvic", "OnEvent收到了消息：" + event.getMsg());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
