package com.xian.myapp.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import com.xian.myapp.logs.SLLog;
import com.xian.myapp.widget.SLActionBar;


/**
 * @version 1.00.00
 * @description: 基础的Fragment，
 * @author 吴书永 11-11-17
 */
public abstract class SLAbstractBaseFragment extends Fragment {

    public static final String TAG = "SLAbstractBaseFragment";
    protected Handler mHandler = new Handler();
    /**
     * 页面标题
     */
    protected String title;

    protected SLActionBar mSlActionBar;
    protected View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackClick(v);
        }
    };

    public SLAbstractBaseFragment() {
    }

    /**
     * 左侧按钮点击事件
     *
     * @param v
     */
    protected void onBackClick(View v) {
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            title = getArguments().getString("title");
        }
        if (null == title && null != savedInstanceState) {
            title = savedInstanceState.getString("title");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SLLog.d(TAG, "onDestroy:" + this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        SLLog.d(TAG, "onAttach:" + this);
    }

    @Override
    public void onResume() {
        super.onResume();
        SLLog.d(TAG, "onResume:" + this);
    }

    @Override
    public void onPause() {
        super.onPause();
        SLLog.d(TAG, "onPause:" + this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SLLog.d(TAG, "onDestroyView:" + this);
    }

    /**
     * 由于Fragment是嵌入到Activity,但无法控制后退键,所以在顶级超类添加后退键的方法.
     *
     * @return
     */
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!TextUtils.isEmpty(title)) {
            outState.putString("title", title);
        }
    }
}
