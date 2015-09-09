package com.xian.myapp.activities;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by limingfeng on 2015/8/5.
 */
public class UpLoadAndDownLoadActivity extends BaseActivity {

    private Button btn1;
    private Button btn2;
    private TextView text;

    private DownloadManager downloadManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_download);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        text = (TextView) findViewById(R.id.text);


        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://img.meilishuo.net/css/images/AndroidShare/Meilishuo_3.6.1_10006.apk");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                long reference = downloadManager.enqueue(request);
            }
        });


    }

    final String ENCORDING = "UTF-8";

    public boolean upload(String filepath) throws Exception {
        String boundary = "---------------------------7db1c523809b2";//+java.util.UUID.randomUUID().toString();//
        // 分割线
        File file = new File(filepath);

        String fileName = new String("哈哈嗨".getBytes(), "ISO-8859-1");
        // 用来解析主机名和端口
        URL url = new URL("http://192.168.1.120/dev/index.php/Device/UploadFile?filename=" + fileName + "&filetype=IMAGE");
        // 用来开启连接
        StringBuilder sb = new StringBuilder();
        // 用来拼装请求

		/*// username字段
        sb.append("--" + boundary + "\r\n");
    	sb.append("Content-Disposition: form-data; name=\"username\"" + "\r\n");
    	sb.append("\r\n");
    	sb.append(username + "\r\n");

    	// password字段
    	sb.append("--" + boundary + "\r\n");
    	sb.append("Content-Disposition: form-data; name=\"password\"" + "\r\n");
    	sb.append("\r\n");
    	sb.append(password + "\r\n");*/

        // 文件部分
        sb.append("--" + boundary + "\r\n");
        sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + filepath + "\"" + "\r\n");
        sb.append("Content-Type: application/octet-stream" + "\r\n");
        sb.append("\r\n");

        // 将开头和结尾部分转为字节数组，因为设置Content-Type时长度是字节长度
        byte[] before = sb.toString().getBytes(ENCORDING);
        byte[] after = ("\r\n--" + boundary + "--\r\n").getBytes(ENCORDING);

        // 打开连接, 设置请求头
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setRequestProperty("Content-Length", before.length + file.length() + after.length + "");

        conn.setDoOutput(true);
        conn.setDoInput(true);

        // 获取输入输出流
        OutputStream out = conn.getOutputStream();
        FileInputStream fis = new FileInputStream(file);
        // 将开头部分写出
        out.write(before);

        // 写出文件数据
        byte[] buf = new byte[1024 * 5];
        int len;
        while ((len = fis.read(buf)) != -1)
            out.write(buf, 0, len);

        // 将结尾部分写出
        out.write(after);

        InputStream in = conn.getInputStream();
        InputStreamReader isReader = new InputStreamReader(in);
        BufferedReader bufReader = new BufferedReader(isReader);
        String line = null;
        String data = "getResult=";
        while ((line = bufReader.readLine()) != null)
            data += line;
        Log.e("fromServer", "result=" + data);
        boolean sucess = conn.getResponseCode() == 200;
        in.close();
        fis.close();
        out.close();
        conn.disconnect();

        return sucess;
    }


    public void uploadFile() {
        try {
            String name = URLEncoder.encode("name", "utf-8");
            String pass = URLEncoder.encode("pw", "utf-8");
            Map<String, String> params = new HashMap<String, String>();
            params.put("NAME", name);
            params.put("PASSWORD", pass);
            getFile();

            post("http://192.168.1.120/dev/index.php/Device/UploadFile?filename=111&filetype=IMAGE", params, upfiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Map<String, File> upfiles = new HashMap<String, File>();

    void getFile() {
        File file = new File("/sdcard/");
        File[] files = file.listFiles(new fileFilter());

        for (File f : files) {
            upfiles.put(f.getName(), new File("/sdcard/" + f.getName()));

        }
        //  Toast.makeText(this, filename, Toast.LENGTH_LONG).show();

    }

    class fileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String filename) {
            return filename.endsWith(".mp3");
        }
    }


    // 上传代码，第一个参数，为要使用的URL，第二个参数，为表单内容，第三个参数为要上传的文件，可以上传多个文件，这根据需要页定
    public static boolean post(String actionUrl, Map<String, String> params, Map<String, File> files) throws IOException {

        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";
        URL uri = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(5 * 1000);
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false);
        conn.setRequestMethod("POST"); // Post方式
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                + ";boundary=" + BOUNDARY);
        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\""
                    + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }
        DataOutputStream outStream = new DataOutputStream(
                conn.getOutputStream());
        outStream.write(sb.toString().getBytes());
        // 发送文件数据
        if (files != null)
            for (Map.Entry<String, File> file : files.entrySet()) {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                        + file.getKey() + "\"" + LINEND);
                sb1.append("Content-Type: multipart/form-data; charset="
                        + CHARSET + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());
                InputStream is = new FileInputStream(file.getValue());
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                outStream.write(LINEND.getBytes());
            }
        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();
        // 得到响应码
        boolean success = conn.getResponseCode() == 200;
        InputStream in = conn.getInputStream();
        InputStreamReader isReader = new InputStreamReader(in);
        BufferedReader bufReader = new BufferedReader(isReader);
        String line = null;
        String data = "getResult=";
        while ((line = bufReader.readLine()) != null)
            data += line;

        outStream.close();
        conn.disconnect();
        return success;
    }


}
