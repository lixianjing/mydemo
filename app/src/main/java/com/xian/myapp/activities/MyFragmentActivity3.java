package com.xian.myapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xian.myapp.R;

/**
 * 嵌套Fragment使用
 *
 * @author 农民伯伯
 */
public class MyFragmentActivity3 extends FragmentActivity   {


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.fragment_inner_test3);
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText("root");
        Button btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, FragmentParent.newInstance(0));
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("lmf",">>>>>>>>>>>"+getSupportFragmentManager().getFragments().size());
            }
        });

    }



    /**
     * 嵌套Fragment
     */
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
            View convertView = inflater.inflate(R.layout.fragment_inner_test3, container, false);

            TextView textView = (TextView) convertView.findViewById(R.id.text);
            Button btn = (Button) convertView.findViewById(R.id.btn1);
            textView.setText("Page " );
            btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    ft.add(R.id.content, FragmentParent.newInstance(0));
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
            Button btn2 = (Button) convertView.findViewById(R.id.btn2);
            btn2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    getChildFragmentManager().popBackStack();
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
            Log.e("lmf", this + ">>>>>>setUserVisibleHint>>>>>>>" + isVisibleToUser);
        }


    }
}