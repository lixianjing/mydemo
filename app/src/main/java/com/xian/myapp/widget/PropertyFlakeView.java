package com.xian.myapp.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
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

/**
 * Created by limingfeng on 2015/8/7.
 */
public class PropertyFlakeView extends ViewGroup {

    private static final String TAG = "NormalFlake";

    private static final int FLAKE_COUNT = 8;
    private static final int DURATION_TIME_RETAIN = 5000;

    private static final int SIZE_RETAIN = 50;
    private static final int SIZE_CHANGE = 50;

    private Context mContext;
    private ImageView[] images = new ImageView[FLAKE_COUNT];
    private float[] distances = new float[FLAKE_COUNT];
    private int width;
    private int height;
    private ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

    public PropertyFlakeView(Context context) {
        this(context, null);
    }

    public PropertyFlakeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public PropertyFlakeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initAnimation();
    }

    private void initView() {
        for (int i = 0; i < FLAKE_COUNT; i++) {
            images[i] = new ImageView(mContext);
            int size = SIZE_RETAIN + (int) (SIZE_CHANGE * Math.random());
            addView(images[i], size, size);
        }
    }

    private void initAnimation() {

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float offset = (float) animation.getAnimatedValue();
                Log.e("lmf", ">>>>>>onAnimationUpdate>>>>" + offset);
                for (int i = 0; i < FLAKE_COUNT; i++) {
                    Log.e("lmf", ">>>>>>onAnimationUpdate>>111>>" + distances[i]);
                    images[i].setY(offset * distances[i]);
                }

            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                for (int i = 0; i < FLAKE_COUNT; i++) {
                    images[i].setVisibility(View.VISIBLE);
                    int offset = (int) ((width - images[i].getWidth()) * Math.random());
                    images[i].setX(offset);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                for (int i = 0; i < FLAKE_COUNT; i++) {
                    images[i].setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                for (int i = 0; i < FLAKE_COUNT; i++) {
                    images[i].setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(DURATION_TIME_RETAIN);
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
            images[i].setImageBitmap(bitmap);
            distances[i] = (float) ((height * Math.random()) + height);
        }
        animator.start();

    }


}
