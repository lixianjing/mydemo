package com.xian.myapp.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.xian.myapp.R;
import com.xian.myapp.utils.SLNavigation;

/**
 * 提供简捷的创建通用对话框的方法（Alert/Confirm/Progress），复杂窗口请自行创建。 如果指定了
 * customDialogLayoutResID 参数，则使用该布局创建自定义窗口，否则默认是系统风格。
 *
 * @author huangshan1 2012-9-12
 */
public class BaseActivity extends FragmentActivity {

    public static final String EXTRA_OPEN_ANIM_IN = "extra_open_anim_in";
    public static final String EXTRA_OPEN_ANIM_OUT = "extra_open_anim_out";
    public static final String EXTRA_CLOSE_ANIM_IN = "extra_close_anim_in";
    public static final String EXTRA_CLOSE_ANIM_OUT = "extra_close_anim_out";

    public static final int DIALOG_ID_ALERT = 689;
    public static final int DIALOG_ID_CONFIRM = 670;
    public static final int DIALOG_ID_PROGRESS = 671;
    public static final int DIALOG_ID_CUSTOM_PROGRESS = 672;

    /**
     * 自定义对话框布局资源id，如果指定则使用该布局创建所有alert/confirm/progress对话框
     */
    public static int customDialogLayoutResID = R.layout.sl_progress_dialog;

    private String mDialogTitle; // 窗口的标题
    private String mDialogMessage; // 窗口的提示文本
    private OnClickListener mOnOkButtonClickListener; // 窗口确定按钮点击时调用的 listener
    private OnClickListener mOnCancelButtonClickListener; // 窗口取消按钮点击时调用的 listener
    private OnCancelListener mOnCancelListener;//窗口取消后回调的listener 目前只用于mCustomDialog和mProgressDialog by wuqiang

    private AlertDialog mAlertDialog, mConfirmDialog, mProgressDialog;

    private boolean mIsCustomDialog;
    private Dialog mCustomDialog;
    private boolean mCancelable = false; // 对话框是否可取消

    protected Button mLeftBtn;
    protected Button mRightBtn;

    protected boolean mApplicationStopedUnexpectedly;

    /**
     * 默认进入 activity 的动画，如果什么都不设置，退出动画是 R.anim.activity_slide_out_right
     */
    protected int mDefaultOpenAnimationIn = R.anim.sl_slide_in_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsCustomDialog = customDialogLayoutResID > 0;

        overridePendingTransition(R.anim.anim_enter_right, R.anim.anim_leave_left);
    }

    private void configCloseAnimation() {
        int closeAnimOutRes = getIntent().getIntExtra(EXTRA_CLOSE_ANIM_OUT, -1);
        if (closeAnimOutRes == -1) {
            // 如果未指定退出动画，则检查是否设置进入动画，如果设置了，则使用相对应的退出动画
            int openAnimInRes = getIntent().getIntExtra(EXTRA_OPEN_ANIM_IN, mDefaultOpenAnimationIn);
            if (openAnimInRes != -1) {
                if (openAnimInRes == R.anim.sl_push_up_in) {
                    closeAnimOutRes = R.anim.sl_push_down_out;
                } else if (openAnimInRes == R.anim.sl_scale_in) {
                    closeAnimOutRes = R.anim.sl_scale_out;
                } else if (openAnimInRes == R.anim.sl_slide_in_right) {
                    closeAnimOutRes = R.anim.sl_slide_out_right;
                }
            }
        }

        if (closeAnimOutRes != -1) {
            int closeAnimInRes = getIntent().getIntExtra(EXTRA_CLOSE_ANIM_IN, R.anim.sl_no_anim);
            overridePendingTransition(closeAnimInRes, closeAnimOutRes);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        configCloseAnimation();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_enter_left, R.anim.anim_leave_right);
    }

    /**
     * 显示正在加载窗口
     *
     * @param message loading 窗口的提示文本
     */
    public Dialog showProgressDialog(String message) {
        return showProgressDialog(message, true);
    }

    /**
     * 显示正在加载窗口
     *
     * @param message loading 窗口的提示文本
     */
    public Dialog showProgressDialog(String message, boolean cancelable) {
        if (isFinishing()) {
            return null;
        }
        reset();
        mCancelable = cancelable;
        mDialogMessage = message;
        showDialog(mIsCustomDialog ? DIALOG_ID_CUSTOM_PROGRESS : DIALOG_ID_PROGRESS);
        return mIsCustomDialog ? mCustomDialog : mProgressDialog;
    }

    public Dialog showProgressDialog(boolean cancelable) {
        return showProgressDialog(null,cancelable);
    }

    /**
     * 显示正在加载窗口
     *
     * @param message loading 窗口的提示文本
     */
    public Dialog showProgressDialog(String message, boolean cancelable, OnCancelListener onCancelListener) {
        if (isFinishing()) {
            return null;
        }
        reset();
        mCancelable = cancelable;
        mDialogMessage = message;
        mOnCancelListener = onCancelListener;
        showDialog(mIsCustomDialog ? DIALOG_ID_CUSTOM_PROGRESS : DIALOG_ID_PROGRESS);
        return mIsCustomDialog ? mCustomDialog : mProgressDialog;
    }

    /**
     * 派生类必须自己保证窗口能正确关闭！例如在设备屏幕方向发生变化后
     */
    public void dismissProgressDialog() {
        reset();
        Dialog dialog = mIsCustomDialog ? mCustomDialog : mProgressDialog;
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            //removeDialog(mIsCustomDialog ? DIALOG_ID_CUSTOM_PROGRESS : DIALOG_ID_PROGRESS);
        }
    }

    public void setDialogRightButtonText(String text) {
        if (mRightBtn != null) {
            mRightBtn.setText(text);
        }
    }

    @Override
    protected Dialog onCreateDialog(final int id) {
        switch (id) {
            case DIALOG_ID_PROGRESS: {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setOnCancelListener(new OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (mOnCancelListener != null) {
                            mOnCancelListener.onCancel(dialog);
                        }
                    }
                });
                if (!TextUtils.isEmpty(mDialogMessage)) {
                    mProgressDialog.setTitle(mDialogMessage);
                }
                mProgressDialog.setCancelable(mCancelable);
                //((ProgressDialog)mProgressDialog).setIndeterminateDrawable(getResources().getDrawable(R.drawable.sl_progress_dialog));
                return mProgressDialog;
            }
            case DIALOG_ID_CUSTOM_PROGRESS: {
                mCustomDialog = SLNavigation.getCustomDialog(this, R.layout.sl_progress_dialog);
                mCustomDialog.setCancelable(mCancelable);
                TextView view=(TextView) mCustomDialog.findViewById(R.id.txt_progress_title);
                if (!TextUtils.isEmpty(mDialogMessage)) {
                    view.setVisibility(View.VISIBLE);
                    view.setText(mDialogMessage);
                } else {
                    view.setVisibility(View.GONE);
                }
                return mCustomDialog;
            }
            default:
                break;
        }

        return super.onCreateDialog(id);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case DIALOG_ID_CUSTOM_PROGRESS: {
                mCustomDialog.setCancelable(mCancelable);
                if (!TextUtils.isEmpty(mDialogMessage)) {
                    TextView view = (TextView) mCustomDialog.findViewById(R.id.txt_progress_title);
                    view.setText(mDialogMessage);
                    mCustomDialog.findViewById(R.id.txt_progress_title).setVisibility(View.VISIBLE);
                } else {
                    mCustomDialog.findViewById(R.id.txt_progress_title).setVisibility(View.GONE);
                }
            }
        }
    }

    private void reset() {
        mCancelable = true;
        mDialogTitle = "提示";
        mDialogMessage = null;
        mOnOkButtonClickListener = null;
        mOnCancelButtonClickListener = null;

        if (mLeftBtn != null) {
            mLeftBtn.setText("取消");
        }
        if (mRightBtn != null) {
            mRightBtn.setText("确定");
        }
    }
}
