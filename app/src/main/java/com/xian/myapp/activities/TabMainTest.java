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

public class TabMainTest extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    private Context mContext;
    private ListView mListView;
    private String[] items = new String[]{"底部菜单使用RadioGroup(推荐)","底部菜单使用FragmentTabHost(不推荐)","底部菜单使用自定义layout(推荐)","可滑动的tab","赶集tab"};

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
                        intent=new Intent(mContext,TabStyle1.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent=new Intent(mContext,TabStyle2.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent=new Intent(mContext,TabStyle3.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent=new Intent(mContext,TabViewPager.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent=new Intent(mContext,TabViewPagerGanji.class);
                        startActivity(intent);


                        break;
                    default:
                        break;
                }
            }
        });
    }
}
