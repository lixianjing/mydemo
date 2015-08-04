package com.xian.myapp.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.base.OnBackPressedInterface;
import com.xian.myapp.base.SLAbstractBaseFragment;
import com.xian.myapp.logs.SLLog;
import com.xian.myapp.utils.SLNotifyUtil;

/**
 * 承载Fragment的公用的Activity,从Intent中传来标题,初始化的类.
 *
 * @author 吴书永 13-12-16 :下午2:01
 */
public class SLFragmentActivity extends BaseActivity {

    public static final String TAG = "SLFragmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SLLog.d(TAG, "SLFragmentActivity.onCreate");
        setContentView(R.layout.sl_fragment_with_guide);
        Intent intent = getIntent();

        if (null == intent) {
            SLNotifyUtil.showToast("系统错误.");
            finish();
            return;
        }

        String title = intent.getStringExtra("title");
        String className = intent.getStringExtra("fragment_class");
        Bundle bundle = intent.getExtras();
        if (null == bundle) {
            bundle = new Bundle();
        }
        if (!TextUtils.isEmpty(title)) {
            bundle.putString("title", title);
        }
        initFragment(className, bundle);
    }

    /**
     * 这个方法在Fragment的执行之后才执行的.而且两个requestCode不同.使用fragment.startActivityForResult时,这里不需要处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SLLog.d(TAG, "onActivityResult:" + requestCode);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void initFragment(String className, Bundle bundle) {
        Intent intent = getIntent();
        if (null == intent) {
            finish();
            return;
        }

        try {
            SLLog.d(TAG, "initFragment." + className);
            Fragment old = getSupportFragmentManager().findFragmentById(R.id.content);
            SLLog.d(TAG, "initFragment." + className + " old:" + old);
            if (null == old) {
                Fragment newFragment = Fragment.instantiate(this, className, intent.getExtras());
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (bundle.getBoolean("add_to_back_stack", false)) {
                    ft.addToBackStack(null);
                }
                ft.add(R.id.content, newFragment).commitAllowingStateLoss();
            } else {
                Fragment newFragment = Fragment.instantiate(this, className, bundle);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.content, newFragment);
                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                //ft.addToBackStack(null);
                if (bundle.getBoolean("add_to_back_stack", false)) {
                    ft.addToBackStack(null);
                }
                ft.commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (null != fragment && fragment instanceof SLAbstractBaseFragment) {
            SLAbstractBaseFragment absFragment = (SLAbstractBaseFragment) fragment;
            if (!absFragment.onBackPressed()) {
                super.onBackPressed();
            }
        } else if (null != fragment && fragment instanceof OnBackPressedInterface) {
            OnBackPressedInterface baseFragment = (OnBackPressedInterface) fragment;
            if (!baseFragment.onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
}
