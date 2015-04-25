package com.xian.myapp.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.xian.myapp.MyApplication;
import com.xian.myapp.R;

/**
 * 用以替代系统Toast，弹出对话框通知。
 *
 * @author 吴书永 2014/8/25 :15:38
 */
public class SLNotifyUtil {

    public final static View makeToastView(String txt) {
        View overlay = ((LayoutInflater) MyApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
                inflate(R.layout.sl_toast_view, null);
        TextView textView = (TextView) overlay.findViewById(R.id.txt_toast_text);
        textView.setText(txt);

        return overlay;
    }

    public static final Toast makeToast(String txt) {
        View overlay = makeToastView(txt);
        Toast toast = new Toast(MyApplication.getContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(overlay);
        return toast;
    }

    public static final Toast makeToast(String txt, int delay) {
        View overlay = makeToastView(txt);
        Toast toast = new Toast(MyApplication.getContext());
        toast.setDuration(delay);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(overlay);
        return toast;
    }

    public static final Toast makeToast(int resId) {
        View overlay = makeToastView(MyApplication.getContext().getString(resId));
        Toast toast = new Toast(MyApplication.getContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(overlay);
        return toast;
    }

    public static final Toast makeToast(int resId, int delay) {
        View overlay = makeToastView(MyApplication.getContext().getString(resId));
        Toast toast = new Toast(MyApplication.getContext());
        toast.setDuration(delay);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(overlay);
        return toast;
    }

    public static final void showToast(String txt) {
        Toast toast = makeToast(txt);
        toast.show();
    }

    public static final void showToast(int resId) {
        Toast toast = makeToast(resId);
        toast.show();
    }

    public static final void showToast(final String message, final int delay) {
        Toast toast = makeToast(message, delay);
        toast.show();
    }

    public static final void showToast(final int resId, final int delay) {
        Toast toast = makeToast(resId, delay);
        toast.show();
    }
}
