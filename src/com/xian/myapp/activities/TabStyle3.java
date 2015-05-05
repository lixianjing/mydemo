package com.xian.myapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.fragments.TabFragment1;
import com.xian.myapp.fragments.TabFragment2;
import com.xian.myapp.fragments.TabFragment3;
import com.xian.myapp.fragments.TabFragment4;

public class TabStyle3 extends BaseActivity {
    // 定义FragmentTabHost对象
    private FragmentTabHost mTabHost;
    private View homeContainer;
    private View orderListContainer;
    private View myContainer;
    private final static int HOME_BTN = 0;
    private final static int ORDER_LIST_BTN = 1;
    private final static int MY_BTN = 2;

    private final Class[] fragments = { TabFragment1.class, TabFragment2.class,
            TabFragment3.class, TabFragment4.class };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_style3);
        initView();
    }

    private void initView() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 得到fragment的个数
        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragments[i], null);
        }

        homeContainer = findViewById(R.id.rl_home);
        homeContainer.setOnClickListener(homeListener);
        orderListContainer = findViewById(R.id.rl_order_list);
        orderListContainer.setOnClickListener(homeListener);
        myContainer = findViewById(R.id.rl_my_page);
        myContainer.setOnClickListener(homeListener);

//        mTabRg = (RadioGroup) findViewById(R.id.tab_rg_menu);
//        mTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.tab_rb_1:
//                        mTabHost.setCurrentTab(0);
//                        break;
//                    case R.id.tab_rb_2:
//                        mTabHost.setCurrentTab(1);
//
//                        break;
//                    case R.id.tab_rb_3:
//
//                        mTabHost.setCurrentTab(2);
//                        break;
//                    case R.id.tab_rb_4:
//
//                        mTabHost.setCurrentTab(3);
//                        break;
//
//                    default:
//                        break;
//                }
//            }
//        });

        mTabHost.setCurrentTab(0);
    }


    private View.OnClickListener homeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_home:
                    changeSelectViewState(HOME_BTN);
                    mTabHost.setCurrentTab(HOME_BTN);
                    break;
                case R.id.rl_order_list:
                    changeSelectViewState(ORDER_LIST_BTN);
                    mTabHost.setCurrentTab(ORDER_LIST_BTN);
                    break;
                case R.id.rl_my_page:
                    changeSelectViewState(MY_BTN);
                    mTabHost.setCurrentTab(MY_BTN);
                    break;
                default:
                    break;
            };
        }
    };

    /**
     * 切换tab的选中状态
     * @param index
     */
    private void changeSelectViewState(int index) {
        View[] viewArray = {homeContainer,orderListContainer,myContainer};
        for (int i = 0; i < viewArray.length; i++) {
            if (index == i) {
                viewArray[i].setSelected(true);
            } else {
                viewArray[i].setSelected(false);
            }
        }
    }
}
