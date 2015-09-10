package com.xian.myapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 现有工具类太多，且很多工具类中只有很少的方法，故诞生此类，所有不容易归类的方法都可以定义在该类中。
 * 
 * @author huangshan1 2012-11-8
 * 
 */
public class Utils {

    private static AtomicLong sAtomicLong = new AtomicLong(System.currentTimeMillis());

    /**
     * 将 Java 对象序列化到文件
     *
     * @param obj 要序列化的 Java 对象
     * @param filePath 要保存到的文件绝对路径
     */
    public static void serializeObject(Object obj, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(obj);
            out.flush();
            out.close();
        } catch (Exception e) {
            DLog.e("Utils", e);
        }
    }

    /**
     * 从文件中反序列化出 Java 对象
     *
     * @param filePath 保存了序列化信息的文件绝对路径
     * @return 成功反序列化后的 Java 对象，失败则返回 null。
     */
    public static Object deserializeObject(String filePath) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            Object object = in.readObject();
            in.close();
            return object;
        } catch (Exception e) {
            DLog.e("Utils", e);
        }
        return null;
    }

    /**
     * 将二进制数据中反序列化成 Java 对象
     *
     * @param data 包含了序列化信息的字节数组
     * @return 成功反序列化后的 Java 对象，失败则返回 null。
     */
    public static Object deserializeObject(byte[] data) {
        if (data != null && data.length > 0) {
            try {
                ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
                Object obj = in.readObject();
                in.close();
                return obj;
            } catch (Exception e) {
                DLog.e("Utils", e);
            }
        }
        return null;
    }

    /**
     * 将 Java 对象序列化成二进制数据
     *
     * @param obj 要序列化的 Java 对象
     * @return 成功序列化后的二进制数据，失败则返回 null。
     */
    public static byte[] getSerializedBytes(Object obj) {
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bao);
            out.writeObject(obj);
            out.flush();
            byte[] data = bao.toByteArray();
            out.close();
            bao.close();
            return data;
        } catch (Exception e) {
            DLog.e("Utils", e);
        }
        return null;
    }
    
    /**
     * 在主线程中执行 runnable 任务
     */
    public static void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != Envi.uiThread) {
            new Handler(Looper.getMainLooper()).post(action);
        } else {
            action.run();
        }
    }

    /**
     * 检查某个应用程序是否已安装
     *
     * @param packageName 要检查的应用程序的包名
     * @return true 时表示应用已安装，否则为未安装。
     */
	public static boolean isAppInstalled(String packageName) {
		PackageManager pm = Envi.appContext.getPackageManager();
		try {
			pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
		return true;
	}

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 pixel(像素)
     */
    public static int dipToPixel(float dip) {
        float scale =Envi.appContext.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 pixel(像素) 的单位 转成为 dip
     */
    public static int pixelToDip(float px) {
        float scale = Envi.appContext.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取在当前应用程序上下文内全局唯一的 id
     */
    public static long getUniqueId() {
        return sAtomicLong.getAndAdd(1);
    }

    /**
     * 将 assets 目录下的文件拷贝到指定目录，如果目标文件已存在则不进行拷贝。
     *
     * @param fromFileName 主项目 assets
     *        目录下的文件路径，可以是直接的文件名，如：ganji.db，也可以是带目录层级的，如：bundle/ganji.db。
     * @param toFile 要拷贝到的目标文件
     */
    public static void copyAssetFile(String fromFileName, File toFile) {
        InputStream in = null;
        OutputStream fos = null;
        if (!toFile.exists()) {
            try {
                in = Envi.appContext.getAssets().open(fromFileName, Context.MODE_PRIVATE);
                fos = new FileOutputStream(toFile);
                StreamUtil.copyStream(in, fos);
                if (fos != null) {
                    fos.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                DLog.e("common", e);
            }
        }
    }

    /**
     * 针对某个控件禁用硬件加速功能
     */
    @SuppressLint("NewApi")
    public static void disableHardwareAccelerate(View view) {
        if (Build.VERSION.SDK_INT >= 11) {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    /**
     * 快速查找 view。用法：
     * TextView textView = fastFindViewById(convertView, R.id.text);
     *
     * @param <T> 要查找的 View 的类型
     * @param viewGroup 父 view，会在该 view 中调用 findViewById 方法。
     * @param id 要查找的 view 的 id
     * @return 查找到的 view，已做类型转换。
     */
    public static <T extends View> T fastFindViewById(View viewGroup, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) viewGroup.getTag(0x11000000);
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            viewGroup.setTag(0x11000000, viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = viewGroup.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    /**
     * 获取某个范围内的一个随机整数
     *
     * @param min 随机数的最小值
     * @param max 随机数的最大值
     */
    public static int randomInt(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
