package com.xian.myapp.volley;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;

/**
 * Created by limingfeng on 2015/8/5.
 */
public class VolleyActivity extends BaseActivity {


    private NetworkImageView mNetImageView;
    private ImageView mImageView;
    private TextView mTextView;
    private Button mBtn1, mBtn2, clearBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_activity);
        mTextView = (TextView) findViewById(R.id.text);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mImageView = (ImageView) findViewById(R.id.image);
        mNetImageView = (NetworkImageView) findViewById(R.id.networkImageView);
        clearBtn = (Button) findViewById(R.id.clear_btn);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(mContext);
                //不是必须的
                // Instantiate the cache
                Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

                // Set up the network to use HttpURLConnection as the HTTP client.
                Network network = new BasicNetwork(new HurlStack());

                // Instantiate the RequestQueue with the cache and network.
                RequestQueue mRequestQueue = new RequestQueue(cache, network);
                //自定义的需要启动
                mRequestQueue.start();


                String url = "http://www.baidu.com";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                mTextView.setText("Response is: " + response.substring(0, 500));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("That didn't work!");
                    }
                });

                // Add the request to the RequestQueue.
//                mRequestQueue.add(stringRequest);

                VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
            }
        });

        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://h.hiphotos.baidu.com/image/pic/item/3b292df5e0fe992573ef9abd30a85edf8cb17178.jpg";
//                ImageRequest request = new ImageRequest(url,
//                        new Response.Listener<Bitmap>() {
//                            @Override
//                            public void onResponse(Bitmap bitmap) {
//                                mImageView.setImageBitmap(bitmap);
//                            }
//                        }, 0, 0, null,
//                        new Response.ErrorListener() {
//                            public void onErrorResponse(VolleyError error) {
//                                mImageView.setImageResource(R.drawable.ic_launcher);
//                            }
//                        });
//// Access the RequestQueue through your singleton class.
//                VolleySingleton.getInstance(mContext).addToRequestQueue(request);





                 String IMAGE_URL =
                        "http://b.hiphotos.baidu.com/image/pic/item/060828381f30e924e9666fdc48086e061c95f719.jpg";

                // Get the ImageLoader through your singleton class.
                ImageLoader   mImageLoader = VolleySingleton.getInstance(mContext).getImageLoader();
                mImageLoader.get(IMAGE_URL, ImageLoader.getImageListener(mImageView,
                        R.drawable.abc_tab_indicator_mtrl_alpha, R.drawable.ic_launcher));


                mNetImageView.setImageUrl(url, mImageLoader);


            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("clear");

                mImageView.setImageResource(R.drawable.sl_bg_comment_tab);
                mNetImageView.setImageResource(R.drawable.sl_bg_comment_tab);
            }
        });
    }
}
