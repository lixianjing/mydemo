package com.xian.myapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xian.myapp.R;

public class TestFragment2 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View parentView = inflater.inflate(R.layout.tab_fragment2, container, false);
        return parentView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("lmf", ">>onAttach>>>>>>>>2");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("lmf", ">>onDetach>>>>>>>>2");
    }
}
