package com.xian.myapp.volley;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;

/**
 * Created by limingfeng on 2015/8/5.
 */
public class VolleyActivity extends BaseActivity {


    private TextView mTextView;
    private Button mBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_activity);
        mTextView = (TextView) findViewById(R.id.text);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(mContext);
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
                queue.add(stringRequest);
            }
        });
    }
}
