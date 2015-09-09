package com.xian.myapp.activities;

import android.app.Activity;
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
import android.widget.TextView;

import com.xian.myapp.R;

/**
 * 嵌套Fragment使用
 *
 * @author 农民伯伯
 * @see http://www.cnblogs.com/over140/archive/2013/01/02/2842227.html
 *
 */
public class MyFragmentActivity2 extends FragmentActivity implements OnClickListener {

    private int currentIndex=0;
    Fragment[] myFragment=new Fragment[3];

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_fragment);

        myFragment[0]=FragmentParent.newInstance(0);
        myFragment[1]=FragmentParent.newInstance(1);
        myFragment[2]=FragmentParent.newInstance(2);

        findViewById(R.id.btnModule1).setOnClickListener(this);
        findViewById(R.id.btnModule2).setOnClickListener(this);
        findViewById(R.id.btnModule3).setOnClickListener(this);

        initFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnModule1:
                addFragmentToStack(0);
                break;
            case R.id.btnModule2:
                addFragmentToStack(1);
                break;
            case R.id.btnModule3:
                addFragmentToStack(2);
                break;
        }
    }


    private void initFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        currentIndex=0;
        ft.add(R.id.fragment_container, myFragment[0],currentIndex+"");
        ft.commit();
    }

    private void addFragmentToStack(int index) {


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(myFragment[currentIndex]);
        Fragment fragment=getSupportFragmentManager().findFragmentByTag(index + "");
        if(fragment==null){
            ft.add(R.id.fragment_container, myFragment[index],index+"");
        }else {
            ft.show(fragment);
        }
        currentIndex= index;
        ft.commit();
    }

    /** 嵌套Fragment */
    public final static class FragmentParent extends Fragment {

        public static final FragmentParent newInstance(int position) {
            FragmentParent f = new FragmentParent();
            Bundle args = new Bundle(2);
            args.putInt("position", position);
            f.setArguments(args);
            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View convertView = inflater.inflate(R.layout.viewpager_fragments, container, false);
            ViewPager pager = (ViewPager) convertView.findViewById(R.id.pager);

            final int parent_position = getArguments().getInt("position");
            //注意这里的代码
            pager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

                @Override
                public Fragment getItem(final int position) {
                    return new Fragment() {
                        @Override
                        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                            TextView convertView = new TextView(getActivity());
                            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
                            convertView.setGravity(Gravity.CENTER);
                            convertView.setTextSize(30);
                            convertView.setTextColor(Color.BLACK);
                            convertView.setText("Page " + position);
                            return convertView;
                        }
                    };
                }

                @Override
                public int getCount() {
                    return 3;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return "Page " + parent_position + " - " + position;
                }

            });

            return convertView;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            Log.e("lmf", this + ">>>>>>onAttach>>>>>>>");
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.e("lmf", this + ">>>>>>onCreate>>>>>>>");
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Log.e("lmf", this + ">>>>>>onViewCreated>>>>>>>");
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.e("lmf", this + ">>>>>>onActivityCreated>>>>>>>");
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.e("lmf", this + ">>>>>>onStart>>>>>>>");
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.e("lmf", this + ">>>>>>onResume>>>>>>>");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.e("lmf", this + ">>>>>>onPause>>>>>>>");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.e("lmf", this + ">>>>>>onStop>>>>>>>");
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.e("lmf", this + ">>>>>>onDestroyView>>>>>>>");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.e("lmf", this + ">>>>>>onDestroy>>>>>>>");
        }

        @Override
        public void onDetach() {
            super.onDetach();
            Log.e("lmf", this + ">>>>>>onDetach>>>>>>>");
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            Log.e("lmf", this + ">>>>>>setUserVisibleHint>>>>>>>"+isVisibleToUser);
        }

        @Override
        public void onHiddenChanged(boolean hidden) {
            super.onHiddenChanged(hidden);
            Log.e("lmf", this + ">>>>>>onHiddenChanged>>>>>>>" + hidden);
        }

    }
}