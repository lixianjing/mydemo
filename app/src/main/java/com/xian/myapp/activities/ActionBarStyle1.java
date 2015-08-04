package com.xian.myapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.helper.DefaultLayoutLoadingHelper;
import com.xian.myapp.widget.SLActionBar;

public class ActionBarStyle1 extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    private Context mContext;
    private View mRootView;
    private SLActionBar mActionBar;
    private DefaultLayoutLoadingHelper mDefaultLayoutLoadingHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = LayoutInflater.from(this).inflate(R.layout.actionbar_style1, null);
        setContentView(mRootView);
        mContext = this;
        mActionBar = (SLActionBar) findViewById(R.id.actionbar);
        mDefaultLayoutLoadingHelper = new DefaultLayoutLoadingHelper(mRootView, R.id.com_content_layout, R.id.com_error_layout, R.id.com_loading_layout);
        mDefaultLayoutLoadingHelper.loading();
        loadData();
        mDefaultLayoutLoadingHelper.setLoadCommand(new DefaultLayoutLoadingHelper.Command() {
            @Override
            public void exe() {
                mDefaultLayoutLoadingHelper.loading();
                loadData();
            }
        });
    }

    private void loadData() {
        new Thread() {
            @Override
            public void run() {
                super.run();    //调用父类
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ActionBarStyle1.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((int)(Math.random()* 10) % 2 == 0) {
                            mDefaultLayoutLoadingHelper.showContent();
                        } else {
                            mDefaultLayoutLoadingHelper.showError();
                        }


                    }
                });
            }
        }.start();
    }
}
