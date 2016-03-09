package com.xian.myapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.xian.myapp.R;
import com.xian.myapp.activities.FragmentTestActivity;
import com.xian.myapp.activities.TabSecondActivity;

public class TestFragment1 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        FragmentTestActivity mActivity= (FragmentTestActivity) getActivity();
//        mActivity.gotoTwo();
//        return null;
        View parentView = inflater.inflate(R.layout.fragment_test1, container, false);
        return parentView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("lmf",">>onAttach>>>>>>>>1");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("lmf", ">>onDetach>>>>>>>>1");
    }
}
