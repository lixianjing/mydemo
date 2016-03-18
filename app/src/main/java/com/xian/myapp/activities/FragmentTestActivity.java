package com.xian.myapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.xian.myapp.R;
import com.xian.myapp.adapter.CustomELVAdapter;
import com.xian.myapp.fragments.TestFragment1;
import com.xian.myapp.fragments.TestFragment2;
import com.xian.myapp.fragments.TestFragment3;


public class FragmentTestActivity extends FragmentActivity {
	private Button btn1,btn2,btn3;
	private TestFragment1 fragment1;
	private TestFragment2 fragment2;
	private TestFragment3 fragment3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_fragment);
		btn1= (Button) findViewById(R.id.btn1);
		btn2= (Button) findViewById(R.id.btn2);
		btn3= (Button) findViewById(R.id.btn3);

		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (fragment1==null) {
					fragment1=new TestFragment1();
				}
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.content, fragment1);
				ft.addToBackStack(null);
				ft.commit();
			}
		});

		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (fragment2==null) {
					fragment2=new TestFragment2();
				}
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.content, fragment2);
				ft.addToBackStack(null);
				ft.commit();
			}
		});

		btn3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (fragment3==null) {
					fragment3=new TestFragment3();
				}
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.content, fragment3);
				ft.addToBackStack(null);
				ft.commit();
			}
		});

		fragment1=new TestFragment1();
		fragment2=new TestFragment2();
		fragment3=new TestFragment3();

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.content, fragment1);
		ft.addToBackStack(null);
		ft.commit();

      }

	public void gotoTwo(){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.content, fragment2);
		ft.addToBackStack(null);
		ft.commit();
	}

}
