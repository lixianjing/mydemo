package com.xian.myapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xian.myapp.R;


/**
 * 通知工具类,toast或者状态栏通知
 *
 * @author archko 2015-8-27
 */
public class ToastUtils {

    private static Toast sToast;

    /**
     * 显示toast
     *
     * @param content 显示的内容
     */
    public static void showToast(final String content) {
        showToast(content, R.layout.default_toast_view, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast提示信息
     *
     * @param content
     * @param resId   布局资源,
     */
    public static void showToast(final String content, final int resId) {
        showToast(content, resId, Toast.LENGTH_SHORT);
    }

    public static void showToast(final int resId) {
        showToast(Envi.appContext.getString(resId), R.layout.default_toast_view, Toast.LENGTH_SHORT);
    }

    public static void showToast(final int stringId, final int resId) {
        showToast(Envi.appContext.getString(stringId), resId, Toast.LENGTH_SHORT);
    }

    public static void showToast(final String content, final int resId, final int duration) {
      Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (sToast == null) {
                        sToast = new Toast(Envi.appContext);
                        LayoutInflater factory1 = LayoutInflater.from(Envi.appContext);
                        LinearLayout layoutview = (LinearLayout) factory1.inflate(resId, null);
                        TextView tv = (TextView) layoutview.findViewById(R.id.toast_textview);
                        tv.setText(content);
                        sToast.setView(layoutview);
                        sToast.setDuration(duration);
                    } else {
                        View view = sToast.getView();
                        TextView tv = (TextView) view.findViewById(R.id.toast_textview);
                        tv.setText(content);
                        sToast.setDuration(duration);
                    }
                    sToast.show();
                } catch (Exception e) {
                    Toast.makeText(Envi.appContext, content, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 显示一个自定义图片的toast,这里主要是上下线用到.
     *
     * @param bgId 图片背景资源id,
     * @param content
     */
    public static void showToast(final int bgId, final String content) {
       Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (sToast == null) {
                        sToast = new Toast(Envi.appContext);
                        LayoutInflater factory1 = LayoutInflater.from(Envi.appContext);
                        LinearLayout layoutview = (LinearLayout) factory1.inflate(R.layout.default_toast_view, null);
                        TextView tv = (TextView) layoutview.findViewById(R.id.toast_textview);
                        tv.setText(content);

                        ImageView img = (ImageView) layoutview.findViewById(R.id.toast_imageview);
                        img.setVisibility(View.VISIBLE);
                        Bitmap bmp = BitmapFactory.decodeResource(Envi.appContext.getResources(), bgId);
                        Matrix matrix = new Matrix();
                        matrix.postScale(1.5f, 1.5f);
                        Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                        BitmapDrawable bd = new BitmapDrawable(Envi.appContext.getResources(), newbm);
                        img.setBackgroundDrawable(bd);

                        sToast.setView(layoutview);
                        sToast.setDuration(Toast.LENGTH_LONG);
                    } else {
                        View view = sToast.getView();
                        TextView tv = (TextView) view.findViewById(R.id.toast_textview);
                        tv.setText(content);
                        sToast.setDuration(Toast.LENGTH_LONG);
                    }
                    sToast.show();
                } catch (Exception e) {
                    Toast.makeText(Envi.appContext, content, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
        }
    }
}
