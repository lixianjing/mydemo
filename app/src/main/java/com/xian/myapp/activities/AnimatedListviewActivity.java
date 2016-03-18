package com.xian.myapp.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.xian.myapp.R;
import com.xian.myapp.widget.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an example usage of the AnimatedExpandableListView class.
 * <p/>
 * It is an activity that holds a listview which is populated with 100 groups
 * where each group has from 1 to 100 children (so the first group will have one
 * child, the second will have two children and so on...).
 */
public class AnimatedListviewActivity extends Activity {
    private ListView listView;
    private MyAdapter adapter;
    private int mPosition = 0;//默认选择第一个展开
    private BounceInterpolator bounceInterpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        bounceInterpolator = new BounceInterpolator();
        List<String> items = new ArrayList<String>();

        // Populate our list with groups and it's children
        for (int i = 1; i < 20; i++) {

            items.add("Group " + i);
        }

        adapter = new MyAdapter(this,items);

        listView = (AnimatedExpandableListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.

    }



    private static class ChildHolder {
        TextView text1;
        TextView text2;
    }


    private class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<String> dataList;

        public MyAdapter(Context context,List<String> list) {
            inflater = LayoutInflater.from(context);
            dataList=list;
        }


        @Override
        public int getCount() {
            return dataList.size();
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

            ChildHolder holder;
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.animate_list_item_test, parent, false);
                holder.text1 = (TextView) convertView.findViewById(R.id.text1);
                holder.text2 = (TextView) convertView.findViewById(R.id.text2);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            final Animation slidedown = AnimationUtils.loadAnimation(convertView.getContext(), R.anim.slide_down);
            final Animation slideup = AnimationUtils.loadAnimation(convertView.getContext(), R.anim.slide_up);



            /**SET BOUNCE INTERPOLATOR TO SLIDEDOWN**/
            slidedown.setInterpolator(bounceInterpolator);

            final ChildHolder h=holder;
            slideup.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationRepeat(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    h.text2.setVisibility(View.GONE) ;
                }});

            String temp=dataList.get(position);
            holder.text1.setText(temp);
            holder.text2.setText(temp);
            holder.text1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    h.text2.startAnimation(slidedown);
                    h.text2.setVisibility(View.VISIBLE);
                }
            });
            return convertView;
        }
    }

}
