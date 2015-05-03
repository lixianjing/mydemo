package com.xian.myapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.*;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.fragments.TabFragment1;
import com.xian.myapp.fragments.TabFragment2;
import com.xian.myapp.fragments.TabFragment3;
import com.xian.myapp.fragments.TabFragment4;

public class TabStyle2 extends BaseActivity {
    // 定义FragmentTabHost对象
    private FragmentTabHost mTabHost;

    private final Class[] fragments = { TabFragment1.class, TabFragment2.class,
            TabFragment3.class, TabFragment4.class };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_style2);
        initView();
    }

    private void initView() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        // 得到fragment的个数
        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(
                    getIndicatorView(i + "xxx", R.drawable.ic_launcher));

            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragments[i], null);
        }

        mTabHost.setCurrentTab(0);
    }

    private View getIndicatorView(String name, int imgRes) {
        View v = getLayoutInflater().inflate(R.layout.tab_style2_item, null);
        TextView tv = (TextView) v.findViewById(R.id.tv);
        ImageView iv = (ImageView) v.findViewById(R.id.iv);
        tv.setText(name);
        iv.setImageResource(imgRes);
        return v;
    }
}
