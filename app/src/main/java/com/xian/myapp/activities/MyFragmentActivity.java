package com.xian.myapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;

/**
 * 嵌套Fragment使用
 *
 * @author 农民伯伯
 * @see http://www.cnblogs.com/over140/archive/2013/01/02/2842227.html
 *
 */
public class MyFragmentActivity extends FragmentActivity  {

    /**
     * Called when the activity is first created.
     */
    private Context mContext;
    private ListView mListView;
    private String[] items = new String[]{"test1", "test2", "test3"};

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
                        intent = new Intent(mContext, MyFragmentActivity1.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(mContext, MyFragmentActivity2.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(mContext, MyFragmentActivity3.class);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }
            }
        });
    }
}