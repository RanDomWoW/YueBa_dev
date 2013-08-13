package com.qihoo.yueba.ui.activities;

import com.ckt.vas.miles.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SettingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_);
		
		  ImageView iv = (ImageView) findViewById(R.id.cancel);
	        iv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					Intent intent = new Intent();
//					intent.setClass(TimeMergeActivity.this, FriendListActivity.class);
//					startActivity(intent);
					SettingActivity.this.finish();
				}
			});
	}
}
