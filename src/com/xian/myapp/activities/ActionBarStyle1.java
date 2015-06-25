package com.xian.myapp.activities;

import android.content.Context;
import android.os.Bundle;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;

public class ActionBarStyle1 extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actionbar_style1);
        mContext=this;
    }
}
