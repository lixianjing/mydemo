package com.xian.myapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.xian.myapp.R;
import com.xian.myapp.demo.DemoEvent;
import com.xian.myapp.logs.SLLog;
import de.greenrobot.event.EventBus;

public class EventBusTest1 extends Activity {
    /**
     * Called when the activity is first created.
     */

    private Button btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventbus_second);
        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoEvent event=new DemoEvent("hello");
                SLLog.e("lmf",">>>>send>>>>"+event.toString()+":"+System.currentTimeMillis());
                EventBus.getDefault().post(event);
                SLLog.e("lmf",">>>>send>>>>"+event.toString()+":"+System.currentTimeMillis());
            }
        });
    }
}
