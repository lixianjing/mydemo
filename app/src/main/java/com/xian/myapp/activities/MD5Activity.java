package com.xian.myapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.utils.ToastUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by limingfeng on 2015/8/5.
 */
public class MD5Activity extends BaseActivity {
    ListView mListView;
    private String[] data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md5);
        mListView= (ListView) findViewById(R.id.listview);
        new AsyncTask<Integer,Integer,List<String>>(){

            @Override
            protected void onPostExecute(List<String> o) {
                data=o.toArray(new String[o.size()]);
                mListView.setAdapter(new ArrayAdapter<String>(MD5Activity.this,
                        android.R.layout.simple_list_item_1,data));

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected List<String> doInBackground(Integer[] params) {
                List<String> list=new ArrayList<String>();
                File file =new File(Environment.getExternalStorageDirectory(),"download");
                if(file==null||file.isFile()){
                    ToastUtils.showToast("出错啦");
                }else {
                    for (File f : file.listFiles()) {
                        Log.e("lmf", ">>>>>>>>>>" + f.getPath());
                        if (f.isFile() && f.getPath().endsWith(".apk")) {
                            list.add(f.getPath());
                        }
                    }
                }

                return list;
            }
        }.execute();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file= new File(data[position]);
                if(file!=null&&file.isFile()){
                    long size=file.length();
                    long begin =System.currentTimeMillis();
                    String md5=md5sum(file.getPath());
                    long end =System.currentTimeMillis();

                    Toast.makeText(mContext,"file size is:"+size/100 +"k and md5 take time "+ (end-begin) +" ms And md5 value "+md5 ,Toast.LENGTH_LONG).show();



                }else{
                    ToastUtils.showToast("error");
                }


            }
        });
    }


    /**
     * 使用 MD5 算法加密字符串
     */
    public static String MD5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(data.getBytes());
            return bytesToHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
        }
        return data;
    }

    public String md5sum(String filename) {
        InputStream fis;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;
        try {
            fis = new FileInputStream(filename);
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return bytesToHexString(md5.digest());
        } catch (Exception e) {
            System.out.println("error");
            return null;
        }
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
