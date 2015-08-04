package com.xian.myapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.fragments.TabFragment1;
import com.xian.myapp.fragments.TabFragment2;
import com.xian.myapp.fragments.TabFragment3;
import com.xian.myapp.logs.SLLog;
import com.xian.myapp.utils.SLNotifyUtil;

import java.util.ArrayList;

public class TabViewPagerGanji extends BaseActivity {

    private static  final String TAG="TabViewPagerGanji";
    private ViewPager mHomeViewPager;
    private View homeContainer;
    private View orderListContainer;
    private View myContainer;
    private final static int HOME_BTN = 0;
    private final static int ORDER_LIST_BTN = 1;
    private final static int MY_BTN = 2;
    private static final int CACHE_NUMBER = 3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SLLog.d(TAG, "SLHomeActivity.onCreate:" + getIntent());

        setContentView(R.layout.activity_tab_pager_style_ganji);
        mHomeViewPager = (ViewPager) findViewById(R.id.pager);
        homeContainer = findViewById(R.id.rl_home);
        homeContainer.setOnClickListener(homeListener);
        orderListContainer = findViewById(R.id.rl_order_list);
        orderListContainer.setOnClickListener(homeListener);
        myContainer = findViewById(R.id.rl_my_page);
        myContainer.setOnClickListener(homeListener);

        HomePagerAdapter mHomePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        mHomeViewPager.setOnPageChangeListener(mHomePagerAdapter);
        mHomeViewPager.setAdapter(mHomePagerAdapter);
        mHomeViewPager.setOffscreenPageLimit(CACHE_NUMBER);
        mHomeViewPager.setCurrentItem(HOME_BTN);
        changeSelectViewState(HOME_BTN);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        SLLog.d(TAG, "onNewIntent:" + intent);
        if (null != intent.getExtras()) {
            final Intent i = intent;
            processIntent(i);

        }
    }

    private void processIntent(Intent intent) {
        int current_item = intent.getExtras().getInt("current_item", 0);

        //切换到当前的页面
        mHomeViewPager.setCurrentItem(current_item);
        changeSelectViewState(current_item);


    }


    //-----------------------------------------------
    private static final int RELEASE_EXIT_CHECK_TIMEOUT = 3500;
    private boolean mExitFlag = false;
    private long mExitBackTimeout = -1;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (checkBackAction()) {
                return true;
            }

            // An exit event has occurred, force the destroy the consoles
            exit();
        }
        return super.onKeyUp(keyCode, event);
    }

    private boolean checkBackAction() {
        //Do back operation over the navigation history
        boolean flag = this.mExitFlag;

        this.mExitFlag = !false;

        // Retrieve if the exit status timeout has expired
        long now = System.currentTimeMillis();
        boolean timeout = (this.mExitBackTimeout == -1 ||
                (now - this.mExitBackTimeout) > RELEASE_EXIT_CHECK_TIMEOUT);

        //Check if there no history and if the user was advised in the last back action
        if (this.mExitFlag && (this.mExitFlag != flag || timeout)) {
            //Communicate the user that the next time the application will be closed
            this.mExitBackTimeout = System.currentTimeMillis();
            SLNotifyUtil.showToast("再次点击即可退出.");
            return true;
        }

        //Back action not applied
        return !this.mExitFlag;
    }
    //-----------------------------------------------

    public void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    class HomePagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        private ArrayList<Fragment> mFragments;

        public HomePagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<Fragment>();
            Fragment tabFragment1=new TabFragment1();
            Fragment tabFragment2=new TabFragment2();
            Fragment tabFragment3=new TabFragment3();
            mFragments.add(tabFragment1);
            mFragments.add(tabFragment2);
            mFragments.add(tabFragment3);
        }

        @Override
        public Fragment getItem(int arg0) {
            return mFragments.get(arg0);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
            changeSelectViewState(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    }

    private View.OnClickListener homeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_home:
                    mHomeViewPager.setCurrentItem(HOME_BTN);
                    break;
                case R.id.rl_order_list:
                    mHomeViewPager.setCurrentItem(ORDER_LIST_BTN);
                    break;
                case R.id.rl_my_page:
                    mHomeViewPager.setCurrentItem(MY_BTN);
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
