/*
 * Copyright (C) 2010~2014 FMSoft (Espier Studio)
 * 
 * This file is a part of Espier apps.
 * 
 * All rights reserved.
 */

package com.xian.myapp.swipe;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xian.myapp.R;

public class ListViewCursorExample extends Activity {

    private ListView mListView;
    private ListViewCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_listview);
        mListView = (ListView)findViewById(R.id.listview);



        mAdapter = new ListViewCursorAdapter(this, queryContact());
        mListView.setAdapter(mAdapter);
        mAdapter.setMode(SwipeCusorAdapter.Mode.Single);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView", "onItemClick:" + position);
            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView","OnTouch");
                return false;
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView","onItemLongClick:" + position);
                return false;
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("ListView","onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.swipe_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_listview) {
            startActivity(new Intent(this, ListViewCursorExample.class));
            finish();
            return true;
        }else if(id == R.id.action_gridview){
            startActivity(new Intent(this, GridViewExample.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public Cursor queryContact() {

        Cursor cursor = null;

        cursor =
                getContentResolver().query(Contacts.CONTENT_URI,
                        new String[] {Contacts._ID, Contacts.DISPLAY_NAME}, null, null, null);

        return cursor;

    }

}
