package com.qihoo.yueba.ui.activities;

import com.ckt.vas.miles.R;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SlidingActivity extends FragmentActivity {
	SlidingMenu mSlidingMenu;
	Button btn;
	Handler mHandler  = new Handler();
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mSlidingMenu = new SlidingMenu(this);
		setContentView(mSlidingMenu);
		View menu = getLayoutInflater().inflate(R.layout.menu, null);
		View content = getLayoutInflater().inflate(R.layout.feed_activity2, null);
		//btn = (Button) content.findViewById(R.id.btn);
		//Button m = (Button) menu.findViewById(R.id.menu);
		mSlidingMenu.setMenu(menu);
		mSlidingMenu.setContent(content);
		
//		btn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mSlidingMenu.showMenu();
//			}
//		});
		
//		m.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Log.e("ad", "on click");
//			}
//		});
		
		 mHandler.post(new Runnable() {
			
			@Override
			public void run() {
//				mSlidingMenu.showMenu();
			}
		});
	}

}
