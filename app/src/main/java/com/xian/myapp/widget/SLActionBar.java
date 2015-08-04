package com.xian.myapp.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xian.myapp.R;

/**
 * ActionBar,一个简单地自定义ActionBar,
 * <li>左侧包含一个文本按钮与一个图片按钮.只显示一个,都显示都会被覆盖.</li>
 * <li>右侧包含一个文本按钮,一个图片按钮,都显示,则并排</li>
 * <li>中间包含标题与搜索框,一次只显示一个,同时显示则会被覆盖.</li>
 *
 * @author 吴书永 13-12-18 :下午4:47
 */
public class SLActionBar extends LinearLayout {

    /**
     * 左侧文本按钮
     */
    TextView mTxtLeftBtn;
    /**
     * 左边的文本按钮,包含上面的文本,与消息
     */
    View lay_title_left_btn;
    /**
     * 消息
     */
    TextView txt_title_left_msg;
    /**
     * 左侧图片按钮
     */
    ImageView mImgLeftBtn;
    /**
     * 右侧文本按钮
     */
    TextView mTxtRightBtn;
    /**
     * 右侧图片按钮
     */
    ImageView mImgRightBtn;
    /**
     * 中间布局
     */
    RelativeLayout mLayoutTitleContainer;
    /**
     * 中间标题
     */
    LinearLayout mLayoutTitleTxtContainer;
    /**
     * 中间搜索布局
     */
    RelativeLayout mLayoutTitleSearchContainer;
    //-----------------
    /**
     * 中间大标题
     */
    TextView mTxtTitle;
    /**
     * 中间小标题,比如大标题-(小标题)
     */
    TextView mTxtSubTitle;
    /**
     * 标题右边下拉按钮,城市列表中使用.
     */
    ImageView mImgTitleRight;
    //-----------------
    /**
     * 搜索输入框
     */
    EditText mEditInput;
    /**
     * 搜索框右边的清除按钮.默认隐藏,输入内容后显示.内容为空则不显示.
     */
    ImageView mImgClear;
    View lay_action_bar;

    public SLActionBar(Context context) {
        super(context);

        initActionBar(context, null, 0);
    }

    public SLActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initActionBar(context, attrs, 0);
    }

    private void initActionBar(Context context, AttributeSet attrs, int defStyle) {
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.common_action_bar, this);
        mTxtLeftBtn = (TextView) findViewById(R.id.txt_title_left);
        lay_title_left_btn = findViewById(R.id.lay_title_left_btn);
        txt_title_left_msg = (TextView) findViewById(R.id.txt_title_left_msg);
        mImgLeftBtn = (ImageView) findViewById(R.id.btn_title_left);
        mTxtRightBtn = (TextView) findViewById(R.id.txt_title_right);
        mImgRightBtn = (ImageView) findViewById(R.id.btn_title_right);
        mLayoutTitleContainer = (RelativeLayout) findViewById(R.id.sl_lay_title_center);
        mLayoutTitleTxtContainer = (LinearLayout) findViewById(R.id.lay_center_text_container);
        mLayoutTitleSearchContainer = (RelativeLayout) findViewById(R.id.lay_center_input_container);

        mTxtTitle = (TextView) findViewById(R.id.center_text);
        mTxtSubTitle = (TextView) findViewById(R.id.center_text_right);
        mImgTitleRight = (ImageView) findViewById(R.id.title_right_drawable);

        mEditInput = (EditText) findViewById(R.id.edit_center);
        mImgClear = (ImageView) findViewById(R.id.clear_btn);

        mEditInput.addTextChangedListener(watcher);

        lay_action_bar = findViewById(R.id.lay_action_bar);
    }

    public void setBackground(int resid) {
        lay_action_bar.setBackgroundResource(resid);
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (TextUtils.isEmpty(s)) {
                mImgClear.setVisibility(GONE);
            } else {
                mImgClear.setVisibility(VISIBLE);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    public TextView getTxtLeftBtn() {
        return mTxtLeftBtn;
    }

    public ImageView getImgLeftBtn() {
        return mImgLeftBtn;
    }

    public TextView getTxtTitleLeftMsg() {
        return txt_title_left_msg;
    }

    public TextView getTxtRightBtn() {
        return mTxtRightBtn;
    }

    public ImageView getImgRightBtn() {
        return mImgRightBtn;
    }

    public RelativeLayout getLayoutTitleContainer() {
        return mLayoutTitleContainer;
    }

    public LinearLayout getLayoutTitleTxtContainer() {
        return mLayoutTitleTxtContainer;
    }

    public RelativeLayout getLayoutTitleSearchContainer() {
        return mLayoutTitleSearchContainer;
    }

    public TextView getTxtTitle() {
        return mTxtTitle;
    }

    public TextView getTxtSubTitle() {
        return mTxtSubTitle;
    }

    public ImageView getImgTitleRight() {
        return mImgTitleRight;
    }

    public EditText getEditInput() {
        return mEditInput;
    }

    public ImageView getImgClear() {
        return mImgClear;
    }

    public TextView getmTxtLeftBtn() {
        return mTxtLeftBtn;
    }

    public View getLay_title_left_btn() {
        return lay_title_left_btn;
    }

    public TextView getTxt_title_left_msg() {
        return txt_title_left_msg;
    }

    public ImageView getmImgLeftBtn() {
        return mImgLeftBtn;
    }

    public TextView getmTxtRightBtn() {
        return mTxtRightBtn;
    }

    public ImageView getmImgRightBtn() {
        return mImgRightBtn;
    }

    public RelativeLayout getmLayoutTitleContainer() {
        return mLayoutTitleContainer;
    }

    public LinearLayout getmLayoutTitleTxtContainer() {
        return mLayoutTitleTxtContainer;
    }

    public RelativeLayout getmLayoutTitleSearchContainer() {
        return mLayoutTitleSearchContainer;
    }

    public TextView getmTxtTitle() {
        return mTxtTitle;
    }

    public TextView getmTxtSubTitle() {
        return mTxtSubTitle;
    }

    public ImageView getmImgTitleRight() {
        return mImgTitleRight;
    }

    public EditText getmEditInput() {
        return mEditInput;
    }

    public ImageView getmImgClear() {
        return mImgClear;
    }

    public View getLay_action_bar() {
        return lay_action_bar;
    }
    //-------------------------------------------------

    /**
     * 设置左侧的图标按钮
     *
     * @param resId         图片的资源id,如果<=0,则不改变
     * @param clickListener
     */
    public void setBackImage(int resId, OnClickListener clickListener) {
        mImgLeftBtn.setVisibility(VISIBLE);
        lay_title_left_btn.setVisibility(GONE);
        if (resId > 0) {
            mImgLeftBtn.setImageResource(resId);
        }
        if (null != clickListener) {
            mImgLeftBtn.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置左侧文本按钮
     *
     * @param txt           显示的文本
     * @param clickListener 监听
     */
    public void setBackText(String txt, OnClickListener clickListener) {
        lay_title_left_btn.setVisibility(VISIBLE);
        mImgLeftBtn.setVisibility(GONE);
        mTxtLeftBtn.setText(txt);
        if (null != clickListener) {
            lay_title_left_btn.setOnClickListener(clickListener);
        }
    }

    public void setBackText(int resId, OnClickListener clickListener) {
        String txt = getResources().getString(resId);
        setBackText(txt, clickListener);
    }

    /**
     * 隐藏左侧的所有图标
     */
    public void hideBack() {
        lay_title_left_btn.setVisibility(GONE);
        mImgLeftBtn.setVisibility(GONE);
    }

    public void showBack() {
        lay_title_left_btn.setVisibility(GONE);
        mImgLeftBtn.setVisibility(VISIBLE);
    }

    /**
     * 设置右侧的图标按钮,互斥的,只显示这个按钮
     *
     * @param resId         图片的资源id,如果<=0,则不改变
     * @param clickListener
     */
    public void setConfirmImage(int resId, OnClickListener clickListener) {
        mImgRightBtn.setVisibility(VISIBLE);
        mTxtRightBtn.setVisibility(GONE);
        if (resId > 0) {
            mImgRightBtn.setImageResource(resId);
        }
        if (null != clickListener) {
            mImgRightBtn.setOnClickListener(clickListener);
        } else {
            mImgRightBtn.setOnClickListener(null);
        }
    }

    /**
     * 设置右侧文本按钮,只显示这个按钮
     *
     * @param txt           显示的文本
     * @param clickListener 监听
     */
    public void setConfirmText(String txt, OnClickListener clickListener) {
        mTxtRightBtn.setVisibility(VISIBLE);
        mImgRightBtn.setVisibility(GONE);
        mTxtRightBtn.setText(txt);
        if (null != clickListener) {
            mTxtRightBtn.setOnClickListener(clickListener);
        } else {
            mTxtRightBtn.setOnClickListener(null);
        }
    }

    public void setConfirmText(int resId, OnClickListener clickListener) {
        String txt = getResources().getString(resId);
        setConfirmText(txt, clickListener);
    }

    public void showTitle() {
        mLayoutTitleTxtContainer.setVisibility(VISIBLE);
        mLayoutTitleSearchContainer.setVisibility(GONE);
    }

    /**
     * 显示输入框
     */
    public void showSearchLayout() {
        mLayoutTitleTxtContainer.setVisibility(GONE);
        mLayoutTitleSearchContainer.setVisibility(VISIBLE);
        mEditInput.setText(null);
    }

    /**
     * 设置标题
     *
     * @param txt 显示文本
     */
    public void setTitle(String txt) {
        if (mLayoutTitleTxtContainer.getVisibility() != VISIBLE) {
            showTitle();
        }

        mTxtTitle.setText(txt);
    }

    public void setTitle(int resId) {
        setTitle(getResources().getString(resId));
    }

    /**
     * 设置小标题,比如显示城市,
     *
     * @param txt          显示文本
     * @param showDropdown 是否显示右侧的下拉按钮
     */
    public void setSubTitle(String txt, boolean showDropdown) {
        if (mLayoutTitleTxtContainer.getVisibility() != VISIBLE) {
            showTitle();
        }

        mTxtSubTitle.setText(txt);

        if (showDropdown) {
            mImgTitleRight.setVisibility(VISIBLE);
        } else {
            mImgTitleRight.setVisibility(GONE);
        }
    }

    public void setSubTitle(int resId, boolean showDropdown) {
        setSubTitle(getResources().getString(resId), showDropdown);
    }

    public void setDropdown(boolean showDropdown) {
        if (showDropdown) {
            mImgTitleRight.setVisibility(VISIBLE);
        } else {
            mLayoutTitleTxtContainer.setOnClickListener(null);
            mImgTitleRight.setVisibility(GONE);
        }
    }

    public void setDropdown(OnClickListener clickListener) {
        mImgTitleRight.setVisibility(VISIBLE);
        mLayoutTitleTxtContainer.setOnClickListener(clickListener);
    }

    public void hide() {
        setVisibility(GONE);
    }

    public void show() {
        setVisibility(VISIBLE);
    }

}
