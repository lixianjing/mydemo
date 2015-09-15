package com.xian.myapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.openapi.OpenApiController;
import com.xian.myapp.volley.VolleyActivity;

public class AnimationActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    private Context mContext;
    private ListView mListView;
    private String[] items = new String[]{"view animation", "drawable animation", "property animation"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;

                switch (position) {

                    case 0:
                        //event bus
                        intent = new Intent(mContext, AnimationViewActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //tab test
                        intent = new Intent(mContext, AnimationDrawableActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //actionbar test
                        intent = new Intent(mContext, AnimationPropertyActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                    default:
                        break;
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


}
