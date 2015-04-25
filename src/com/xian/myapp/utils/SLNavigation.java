package com.xian.myapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.xian.myapp.activities.SLFragmentActivity;
import com.xian.myapp.activities.SLWebViewActivity;
import com.xian.myapp.logs.SLLog;
import com.xian.myapp.model.SLImageBean;

import java.util.ArrayList;


/**
 * 导航类,因为Fragment只需要嵌入到Activity,建立一个工具类.
 *
 * @author 吴书永 13-12-20 :上午10:58
 */
public class SLNavigation {

    private static final String TAG="SLNavigation";

    /**
     * 获取自定义对话框
     *
     * @param context
     * @param layoutId 对话框布局id
     * @return
     */
    public static Dialog getCustomDialog(Context context, int layoutId) {
        Dialog dialog = new Dialog(context);
        Window win = dialog.getWindow();
        win.requestFeature(Window.FEATURE_NO_TITLE);
        win.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(layoutId);
        win.getAttributes().width = WindowManager.LayoutParams.FILL_PARENT;
        win.getAttributes().gravity = Gravity.CENTER;

        return dialog;
    }

    public static Dialog getCustomDialog(Context context, int layoutId, boolean cancleable) {
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(cancleable);
        Window win = dialog.getWindow();
        win.requestFeature(Window.FEATURE_NO_TITLE);
        win.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(layoutId);
        win.getAttributes().width = WindowManager.LayoutParams.FILL_PARENT;
        win.getAttributes().gravity = Gravity.CENTER;

        return dialog;
    }

    public static void startActivity(Activity self, Bundle bundle, Class cls) {
        Intent intent = new Intent(self, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        self.startActivity(intent);
    }

    /**
     * 启动列表页,列表页可能是垂直化的,
     *
     * @param activity
     * @param bundle            数据
     * @param fragmentClassName 对应的Fragment
     */
    public static void startActivity(Activity activity, Bundle bundle, String fragmentClassName) {
        startFragmentActivity(activity, bundle, fragmentClassName, SLFragmentActivity.class);
    }

    /**
     * 开打可返回数据的Activity
     *
     * @param activity
     * @param bundle
     * @param clazz
     * @param requestCode
     */
    public static void startActivityForResult(Activity activity, Bundle bundle, Class clazz, int requestCode) {
        Intent intent = new Intent(activity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动一个Activity，之后可能去除其它的方法,保留这两个,以传入的参数决定启动对象.可以启动一个Activity,然后承载着Fragment,
     * 也可以没有Fragment,在于传入的参数cls与fragmentClassName.
     *
     * @param self              自身,用于启动的
     * @param bundle            参数,目前都以Bundle作为参数传递媒介
     * @param fragmentClassName 启动的Fragment类名
     * @param cls               承载Fragment的Activity,没有检查类型,调用时需要注意.
     */
    public static void startFragmentActivity(Activity self, Bundle bundle, String fragmentClassName, Class cls) {
        Intent intent = new Intent(self, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        intent.putExtra("fragment_class", fragmentClassName);
        self.startActivity(intent);
    }

    public static void startFragmentActivity(Context self, Bundle bundle, String fragmentClassName, Class cls) {
        Intent intent = new Intent(self, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        intent.putExtra("fragment_class", fragmentClassName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        self.startActivity(intent);
    }

    /**
     * 启动主页,且进入相应的标签
     *
     * @param activity
     * @param currentItem 标签所在的位置.首页0,发布需求1,拨号历史2,预约3
     * @param bundle
     */
    public static void startHomeTab(Activity activity, int currentItem, Bundle bundle, Class cls) {
        if (activity == null || cls ==null) {
           return;
        }
        Intent intent = new Intent(activity, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        /*if (ApolloUtils.hasHoneycomb()) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }*/
        if (null == bundle) {
            bundle = new Bundle();
            SLLog.d(TAG, "bundle:" + bundle);
        }
        bundle.putInt("current_item", currentItem);

        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public static void startAdItemWeb(String url, String title, Activity activity) {
        Intent intent = new Intent(activity, SLWebViewActivity.class);
        intent.putExtra(SLWebViewActivity.EXTRA_URL, url);
        intent.putExtra(SLWebViewActivity.EXTRA_TITLE, title);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * 启动一个Fragment.且有返回值的,它由Activity启动
     *
     * @param activity    请求者
     * @param requestCode 请求的代码
     * @param className   Fragment类名,
     * @param title       标题
     * @param bundle      数据,可为空
     */
    public static void startFragmentForResult(Activity activity, int requestCode, String className,
                                              String title, Bundle bundle) {
        startFragmentForResult(activity, requestCode, className, title, bundle, SLFragmentActivity.class);
    }

    /**
     * 启动一个Fragment.且有返回值的,它由Activity启动
     *
     * @param activity    请求者
     * @param requestCode 请求的代码
     * @param className   Fragment类名,
     * @param title       标题
     * @param bundle      数据,可为空
     */
    public static void startFragmentForResult(Activity activity, int requestCode, String className,
                                              String title, Bundle bundle, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtra("title", title);
        intent.putExtra("fragment_class", className);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动一个Fragment.且有返回值的,但是它要附加于Activity,而要从Fragment中start
     *
     * @param activity
     * @param fragment    发起启动的Fragment
     * @param requestCode 请求的代码
     * @param className   Fragment类名,
     * @param title       标题
     * @param bundle      数据,可为空
     */
    public static void startFragmentForResult(Context activity, Fragment fragment, int requestCode, String className,
                                              String title, Bundle bundle) {
        startFragmentForResult(activity, fragment, requestCode, className, title, bundle, SLFragmentActivity.class);
    }

    /**
     * 启动一个Fragment.且有返回值的,但是它要附加于Activity,而要从Fragment中start
     *
     * @param activity
     * @param fragment    发起启动的Fragment
     * @param requestCode 请求的代码
     * @param className   Fragment类名,
     * @param title       标题
     * @param bundle      数据,可为空
     */
    public static void startFragmentForResult(Context activity, Fragment fragment, int requestCode, String className,
                                              String title, Bundle bundle, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtra("title", title);
        intent.putExtra("fragment_class", className);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        fragment.startActivityForResult(intent, requestCode);
    }


}
