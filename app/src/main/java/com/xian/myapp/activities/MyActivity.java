package com.xian.myapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.openapi.OpenApiController;
import com.xian.myapp.pojo.GsonMainActivity;
import com.xian.myapp.pojo.GsonMainActivityInfo;
import com.xian.myapp.services.TestService;
import com.xian.myapp.utils.ToastUtils;
import com.xian.myapp.volley.VolleyActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class MyActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    private ListView mListView;
    private Handler mHandler = new Handler();
    // open api
    private static final int OPEN_API_DELAY = 100;
    private OpenApiController openApiController;
    private GsonMainActivity mGsonActivitys;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        openApiController = new OpenApiController(this);
        mListView = (ListView) findViewById(R.id.listview);
        try {

            Gson gson = new Gson();
            mGsonActivitys = gson.fromJson(new InputStreamReader(mContext.getAssets().open("activity.config")), GsonMainActivity.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mGsonActivitys != null && mGsonActivitys.data != null && mGsonActivitys.data.size() > 0) {
            mListView.setAdapter(new MyAdapter(mContext,mGsonActivitys.data));
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (mGsonActivitys != null && mGsonActivitys.data != null && mGsonActivitys.data.size() > 0) {
                        try {
                            GsonMainActivityInfo info = mGsonActivitys.data.get(position);
                            Intent intent = new Intent(mContext, Class.forName(info.activityClassName));
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showToast("没找到相关类哦");
                        }
                    }
                }
            });
        }


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

    class MyAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<GsonMainActivityInfo> infos;

        MyAdapter(Context context, ArrayList<GsonMainActivityInfo> infos) {
            this.context = context;
            this.infos = infos;
        }

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                TextView tv=new TextView(context);
                convertView=LayoutInflater.from(context).inflate(R.layout.item_main_activity, parent, false);
                holder=new ViewHolder();
                holder.nameTv = (TextView) convertView.findViewById(R.id.item_main_tv);
                convertView.setTag(holder);
            }

            holder = (ViewHolder) convertView.getTag();
            holder.nameTv.setText(infos.get(position).name);
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

    private final class ViewHolder {
        TextView nameTv;
    }
}
