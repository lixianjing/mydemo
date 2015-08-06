package com.xian.myapp.volley;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.xian.myapp.MyApplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by limingfeng on 2015/8/6.
 */
public class CustomImageCache implements ImageLoader.ImageCache {


    private static final String DISK_CACHE_NAME = "bitmap";
    private static final int DISK_CACHE_MAX = 10 * 1024 * 1024;
    //��ȡϵͳ�����ÿ��Ӧ�ó��������ڴ棬ÿ��Ӧ��ϵͳ����32M
    int maxMemory = (int) Runtime.getRuntime().maxMemory();
    int mCacheSize = maxMemory / 8;
    //��LruCache����1/8 4M
    private final LruCache<String, Bitmap>
            cache = new LruCache<String, Bitmap>(mCacheSize);

    private DiskLruCache mDiskLruCache;


    public CustomImageCache() {

        try {
            File cacheDir = getDiskCacheDir(MyApplication.getContext(), DISK_CACHE_NAME);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir,
                    getAppVersion(MyApplication.getContext()), 1, DISK_CACHE_MAX);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Bitmap getBitmap(String url) {

        InputStream is = null;
        String key = hashKeyForDisk(url);
        try {
            Bitmap bitmap = cache.get(key);
            if (bitmap != null) {
                return bitmap;
            } else if (mDiskLruCache != null) {
                DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
                if (snapShot != null) {
                    is = snapShot.getInputStream(0);
                    bitmap = BitmapFactory.decodeStream(is);
                    cache.put(key, bitmap);
                }
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {

        String key = hashKeyForDisk(url);
        cache.put(key, bitmap);
        if (mDiskLruCache != null) {
            OutputStream outputStream = null;
            try {
                if (null == mDiskLruCache.get(key)) {
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        outputStream = editor.newOutputStream(0);
                        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    //����ÿ�ζ����ø÷���
                    //mDiskLruCache.flush();

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //we should do this option in activity onPause
    public void diskFlush() {
        if (mDiskLruCache != null) {
            try {
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    //�÷������жϵ�ǰsd���Ƿ���ڣ�Ȼ��ѡ�񻺴��ַ
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    //���Ӧ��version����
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
