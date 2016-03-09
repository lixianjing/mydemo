package com.xian.myapp.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.xian.myapp.utils.Utils;

/**
 * Created by limingfeng on 2015/8/7.
 */
public class NormalFlakeView extends ViewGroup {

    private static final String TAG = "NormalFlake";

    private static final int FLAKE_COUNT = 8;
    private static final int DURATION_TIME_RETAIN = 2000;
    private static final int DURATION_TIME_CHANGE = 3000;

    private static final int SIZE_RETAIN = 50;
    private static final int SIZE_CHANGE = 50;

    private Context mContext;
    private ImageView[] images = new ImageView[FLAKE_COUNT];
    private int width;
    private int height;

    public NormalFlakeView(Context context) {
        this(context, null);
    }

    public NormalFlakeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public NormalFlakeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        for (int i = 0; i < FLAKE_COUNT; i++) {
            images[i] = new ImageView(mContext);
            int size =SIZE_RETAIN+ (int) (SIZE_CHANGE * Math.random());
            addView(images[i], size, size);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("lmf", ">>>>>onLayout>>>>>>");
        int cCount = getChildCount();
        // 遍历所有的孩子
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            LayoutParams lp = child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            child.layout(0, 0, childWidth, childHeight);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int cCount = getChildCount();
        // 遍历所有的孩子
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        width = sizeWidth;
        height = sizeHeight;
        setMeasuredDimension(sizeWidth, sizeHeight);
        Log.e("lmf", ">>>>>onMeasure>>>>>>");
    }

    public void start(final Bitmap bitmap) {

        for (int i = 0; i < FLAKE_COUNT; i++) {
            startAnimation(images[i], bitmap);
        }

    }


    private void startAnimation(final ImageView view, final Bitmap bitmap) {

        TranslateAnimation tAnim = new TranslateAnimation(0, 0, 0, height);
        // 4: 确定Interpolator
        tAnim.setInterpolator(new AccelerateInterpolator());
        int time =DURATION_TIME_RETAIN+ (int) (DURATION_TIME_CHANGE * Math.random());
        tAnim.setDuration(time);
        tAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setImageBitmap(bitmap);
                view.setVisibility(View.VISIBLE);
                int offset = (int) ((width - view.getWidth()) * Math.random());
                view.setX(offset);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // 启动动画
        view.startAnimation(tAnim);
    }
}
