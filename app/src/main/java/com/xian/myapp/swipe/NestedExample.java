package com.xian.myapp.swipe;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xian.myapp.R;

public class NestedExample extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_nested);
        findViewById(R.id.hhhhh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Tag","got");
            }
        });
    }
}
