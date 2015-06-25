package com.xian.myapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;

public class MyActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    private Context mContext;
    private ListView mListView;
    private String[] items = new String[]{"eventBus","tabtest","actionbar","photo show"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext=this;
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
                    default:
                        break;
                }
            }
        });
    }
}
