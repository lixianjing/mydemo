package com.xian.myapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xian.myapp.R;
import com.xian.myapp.adapter.RecyclerAdapter;
import com.xian.myapp.base.BaseActivity;


/**
 * Created by limingfeng on 2015/8/5.
 */
public class RecyclerActivity extends BaseActivity {
    private RecyclerView mRecyclerView1,mRecyclerView2;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager1,mLayoutManager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        String[] dataset = new String[100];
        for (int i = 0; i < dataset.length; i++) {
            dataset[i] = "item" + i;
        }

        mRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);
        // improve performance if you know that changes in content
        // do not change the size of the RecyclerView
        mRecyclerView1.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter(dataset);
        mRecyclerView1.setAdapter(mAdapter);

        mRecyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
        mRecyclerView2.setHasFixedSize(true);
        mLayoutManager2= new LinearLayoutManager(this);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        mRecyclerView2.setAdapter(new RecyclerAdapter(dataset));
    }




}
