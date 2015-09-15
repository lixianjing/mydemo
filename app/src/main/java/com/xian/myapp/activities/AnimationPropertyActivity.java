package com.xian.myapp.activities;

import android.content.Context;
import android.content.Intent;
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

public class AnimationPropertyActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    private Context mContext;
    private ListView mListView;
    private String[] items = new String[]{"view animation","drawable animation","property animation"};
   private Handler mHandler = new Handler();
    // open api
    private static final int OPEN_API_DELAY = 100;
    private OpenApiController openApiController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext=this;
        openApiController = new OpenApiController(this);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=null;

                switch (position) {

                    case 0:
                        //event bus
                        intent=new Intent(mContext,EventBusTest.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //tab test
                        intent=new Intent(mContext,TabMainTest.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //actionbar test
                        intent=new Intent(mContext,ActionBarTest.class);
                        startActivity(intent);
                        break;
                    case 3:
                        //photo show test
                        intent=new Intent(mContext,PhotoShowTest.class);
                        startActivity(intent);
                        break;
                    case 4:
                        //photo show test
                        intent=new Intent(mContext,VolleyActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        //photo show test
                        intent=new Intent(mContext,TouchActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        //photo show test
                        intent=new Intent(mContext,UpLoadAndDownLoadActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        //uncaughtException
                        intent=new Intent(mContext,UnCatchExceptionActivity.class);
                        startActivity(intent);

                        break;
                    case 8:
                        //fragment
                        intent=new Intent(mContext,MyFragmentActivity.class);
                        startActivity(intent);

                        break;
                    case 9:
                        //view
                        intent=new Intent(mContext,ViewActivity.class);
                        startActivity(intent);
                        break;
                    case 10:
                        //view
                        intent=new Intent(mContext,SwipeActivity.class);
                        startActivity(intent);

                        break;
                    case 11:
                        // web view
                        intent=new Intent(mContext,WebViewActivity.class);
                        startActivity(intent);
                        break;
                    case 12:
                        // md5
                        intent=new Intent(mContext,MD5Activity.class);
                        startActivity(intent);

                        break;
                    case 13:
                        // RecyclerActivity
                        intent=new Intent(mContext,RecyclerActivity.class);
                        startActivity(intent);

                        break;
                    case 14:
                        // jni
                        intent=new Intent(mContext,JNIActivity.class);
                        startActivity(intent);

                        break;
                    case 15:
                        // jni
                        intent=new Intent(mContext,OkHttpActivity.class);
                        startActivity(intent);

                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        openApiController = new OpenApiController(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        openAPI();
    }


    public void openAPI() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openApiController.execute();
            }
        }, OPEN_API_DELAY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("lmf",">>>>>onSaveInstanceState>>>>>>");
    }
}
