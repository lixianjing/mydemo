package com.xian.myapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.xian.myapp.R;
import com.xian.myapp.activities.TabSecondActivity;

public class TabFragment1 extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View parentView = inflater.inflate(R.layout.tab_fragment1, container, false);
		Button backBtn = (Button)parentView.findViewById(R.id.button1);
		Button toOtherActivityBtn = (Button)parentView.findViewById(R.id.button2);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				if(mListener!=null)mListener.backEvent();
			}
		});
		
		toOtherActivityBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), TabSecondActivity.class);
				startActivity(intent);
			}
		});
		
		return parentView;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		Log.e("lmf", this + ">>>>>>setUserVisibleHint>>>>>>>" + isVisibleToUser);
	}
}
