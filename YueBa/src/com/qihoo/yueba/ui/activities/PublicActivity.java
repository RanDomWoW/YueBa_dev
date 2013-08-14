package com.qihoo.yueba.ui.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ckt.vas.miles.R;
import com.qihoo.yueba.db.service.DBService;
import com.qihoo.yueba.dto.ActivityMessage;
import com.qihoo.yueba.ui.adapters.Desktop;
import com.qihoo.yueba.ui.adapters.PublicActivityAdapter;
import com.qihoo.yueba.ui.views.ExtendedListView;
import com.qihoo.yueba.ui.views.MenuRightAnimations;
import com.qihoo.yueba.ui.views.ExtendedListView.OnPositionChangedListener;

public class PublicActivity extends Activity implements OnTouchListener,
		OnPositionChangedListener {
	/** Called when the activity is first created. */
	private boolean areButtonsShowing;
	private RelativeLayout composerButtonsWrapper;
	private ImageView composerButtonsShowHideButtonIcon;
	private RelativeLayout composerButtonsShowHideButton;
	private RelativeLayout overlayView;
	SlidingMenu mSlidingMenu;
	Handler mHandler = new Handler();
	private SimpleDateFormat formatter;
	private ExtendedListView dataListView;
	private com.qihoo.yueba.ui.adapters.Desktop mDesktop;
	// Tool bar buttons...
	private ImageView groupButton;
	// clock
	private FrameLayout clockLayout;
	private int flag=0;
	// activity_overlay

	private TextView timeshow;
	protected static final String ACTION = "com.qihoo.psec.startactivity";
	private MyRecv receiver;

	DBService db;
	ActivityMessage m;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Date currentTime = new Date();   
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");   
		String dateString = formatter.format(currentTime);   
		
		db = new DBService(PublicActivity.this);
		Log.d("DB", "table " + db.getTotalCounts());
		int j = 0;
		while (db.getTotalCounts() < 7) {
			Log.d("DB", "new");
			m = new ActivityMessage();
			m.setTitle("空闲时间： 8:00 至 11:00...");
			m.setName("tangwentao");
			m.setBody("Add there...");
			m.setIsDate(0);
			m.setStartTime(dateString);
			m.setEndTime(dateString);
			j ++;
			if (j == 5) {
				m.setName("Lei");
			} else if (j == 6) {
				m.setName("Bian");
			}
			try {
				db.save(m);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		mDesktop = new Desktop(this);
		mSlidingMenu = new SlidingMenu(this);
		setContentView(mSlidingMenu);
		View menu = mDesktop.getView();
		View content = getLayoutInflater().inflate(R.layout.feed_activity2,
				null);
		// btn = (Button) content.findViewById(R.id.btn);
		// Button m = (Button) menu.findViewById(R.id.menu);
		mSlidingMenu.setMenu(menu);
		mSlidingMenu.setContent(content);
		ImageView menu_bar = (ImageView) findViewById(R.id.qa_bar_menu);
		// Button add_thing = (Button)findViewById(R.id.qa_bar_friends);
		// mSlidingMenu.showMenu();
		// setContentView(R.layout.feed_activity2);

		menu_bar.setClickable(true);
		menu_bar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSlidingMenu.showMenu();
			}
		});
		// add_thing.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent();
		// intent.setClass(PublicActivity.this, AddEventActivity.class);
		// startActivity(intent);
		// }
		// });

		MenuRightAnimations.initOffset(PublicActivity.this);
		System.out.println(" findViewById(R.id.composer_buttons_wrapper);=="
				+ findViewById(R.id.composer_buttons_wrapper));
		composerButtonsWrapper = (RelativeLayout) findViewById(R.id.composer_buttons_wrapper);
		composerButtonsShowHideButton = (RelativeLayout) findViewById(R.id.composer_buttons_show_hide_button);
		composerButtonsShowHideButtonIcon = (ImageView) findViewById(R.id.composer_buttons_show_hide_button_icon);

		composerButtonsShowHideButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					Toast.makeText(PublicActivity.this, "鏂藉伐ing...", Toast.LENGTH_SHORT).show();
			}
		});

		for (int i = 0; i < composerButtonsWrapper.getChildCount(); i++) {
			composerButtonsWrapper.getChildAt(i).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View arg0) {

							System.out.println("argo=" + arg0.getId()
									+ " click");

						}
					});
		}
		composerButtonsShowHideButton.startAnimation(MenuRightAnimations
				.getRotateAnimation(0, 360, 200));

		groupButton = (ImageView) findViewById(R.id.qa_bar_friends);
		groupButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PublicActivity.this, AddEventActivity.class);
				startActivity(intent);
			}
		});

		//
		dataListView = (ExtendedListView) findViewById(R.id.list_view);
		try {
			setAdapterForThis();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// dataListView.setCacheColorHint(Color.TRANSPARENT);
		dataListView.setOnPositionChangedListener(this);
		clockLayout = (FrameLayout) findViewById(R.id.clock);
		// clockLayout.setLayoutChangedListener(dataListView);

		// splash.setVisibility(View.GONE);
		View v = findViewById(R.id.composer_buttons_wrapper);
		v.setOnTouchListener(this);

		receiver = new MyRecv();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION);
		registerReceiver(receiver, filter);
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// mSlidingMenu.showMenu();
			}
		});

	}

    @Override 
    public void onStart() {
        try {
			setAdapterForThis();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	super.onStart();
    }
	
	public void onClickView(View v, boolean isOnlyClose) {
		if (isOnlyClose) {
			if (areButtonsShowing) {
				MenuRightAnimations.startAnimationsOut(composerButtonsWrapper,
						300);
				composerButtonsShowHideButtonIcon
						.startAnimation(MenuRightAnimations.getRotateAnimation(
								-315, 0, 300));
				areButtonsShowing = !areButtonsShowing;
			}

		} else {

			if (!areButtonsShowing) {
				MenuRightAnimations.startAnimationsIn(composerButtonsWrapper,
						300);
				composerButtonsShowHideButtonIcon
						.startAnimation(MenuRightAnimations.getRotateAnimation(
								0, -315, 300));
			} else {
				MenuRightAnimations.startAnimationsOut(composerButtonsWrapper,
						300);
				composerButtonsShowHideButtonIcon
						.startAnimation(MenuRightAnimations.getRotateAnimation(
								-315, 0, 300));
			}
			areButtonsShowing = !areButtonsShowing;
		}

	}

	private List<ActivityMessage> messages = new ArrayList<ActivityMessage>();

	private void initMessages() throws ParseException {
		messages.clear();
		messages=db.findByName("tangwentao");
		// data
		// text
//		messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "鍟ヤ篃娌″共",
//				"meisssss", "123", "123", 1333153510605l));
//		messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "鍟ヤ篃娌″共",
//				"meisssss", "123", "123", 1333163510605l));
//		messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "鍟ヤ篃娌″共",
//				"meisssss", "123", "123", 1333173510605l));
//		messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "鍟ヤ篃娌″共",
//				"meisssss", "123", "123", 1333183510605l));
//		
		Log.d("DB", "find");
		//Log.d("DB", "find1 "+db.findByName("tangwentao").get(3).getEndTime());
		int size =  db.findByName("tangwentao").size();
		
//		Log.d("DB", "Size: " + size);
		for (int i = 0; i < size; i ++) {
			Log.d("DB", "AM: " + db.findByName("tangwentao").get(i).getIsDate() + " " + db.findByName("tangwentao").get(i).getName()  + " " + 
					db.findByName("tangwentao").get(i).getTitle()  + " " + db.findByName("tangwentao").get(i).getBody()  + " " +
					formatter.format(db.findByName("tangwentao").get(i).getStartTime())  + " " + formatter.format(db.findByName("tangwentao").get(i).getEndTime()));
//			ActivityMessage am = db.findByName("tangwentao").get(i);
//			messages.add(new ActivityMessage(am.getIsDate(), am.getName(), am.getTitle(), am.getBody(), am.getStartTime().toString(), am.getEndTime().toString()));
//			Log.d("DB", "AM: " + am.getIsDate() + " " + am.getName()  + " " + 
//					am.getTitle()  + " " + am.getBody()  + " " + am.getStartTime().toString()  + " " + am.getEndTime().toString());
		}
//		List<ActivityMessage> msgs = db.findByName("tangwentao");
//		if (!msgs.isEmpty()) {
//			
//			
//		} 
		//Log.d("DB", "size" + msgs.get(0).getName());

		//messages.add(0, new ActivityMessage());

		
	}

	PublicActivityAdapter chatHistoryAdapter;

	private void setAdapterForThis() throws ParseException {
		initMessages();
		chatHistoryAdapter = new PublicActivityAdapter(this, messages);
		dataListView.setAdapter(chatHistoryAdapter);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.out.println("ontouch...................");
		onClickView(v, true);
		return false;
	}

	private float[] computMinAndHour(int currentMinute, int currentHour) {
		float minuteRadian = 6f * currentMinute;

		float hourRadian = 360f / 12f * currentHour;

		float[] rtn = new float[2];
		rtn[0] = minuteRadian;
		rtn[1] = hourRadian;
		return rtn;
	}

	private float[] lastTime = { 0f, 0f };

	private RotateAnimation[] computeAni(int min, int hour) {

		RotateAnimation[] rtnAni = new RotateAnimation[2];
		float[] timef = computMinAndHour(min, hour);
		System.out.println("min===" + timef[0] + " hour===" + timef[1]);
		// AnimationSet as = new AnimationSet(true);
		// 鍒涘缓RotateAnimation瀵硅薄
		// 0--鍥剧墖浠庡摢锟�锟斤拷鏃嬭浆
		// 360--鍥剧墖鏃嬭浆澶氬皯锟�
		// Animation.RELATIVE_TO_PARENT, 0f,// 瀹氫箟鍥剧墖鏃嬭浆X杞寸殑绫诲瀷鍜屽潗锟�
		// Animation.RELATIVE_TO_PARENT, 0f);// 瀹氫箟鍥剧墖鏃嬭浆Y杞寸殑绫诲瀷鍜屽潗锟�
		RotateAnimation ra = new RotateAnimation(lastTime[0], timef[0],
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		ra.setFillAfter(true);
		ra.setFillBefore(true);
		// 璁剧疆鍔ㄧ敾鐨勬墽琛屾椂锟�
		ra.setDuration(800);
		// 灏哛otateAnimation瀵硅薄娣诲姞鍒癆nimationSet
		// as.addAnimation(ra);
		// 灏嗗姩鐢讳娇鐢ㄥ埌ImageView
		rtnAni[0] = ra;

		lastTime[0] = timef[0];

		// AnimationSet as2 = new AnimationSet(true);
		// 鍒涘缓RotateAnimation瀵硅薄
		// 0--鍥剧墖浠庡摢锟�锟斤拷鏃嬭浆
		// 360--鍥剧墖鏃嬭浆澶氬皯锟�
		// Animation.RELATIVE_TO_PARENT, 0f,// 瀹氫箟鍥剧墖鏃嬭浆X杞寸殑绫诲瀷鍜屽潗锟�
		// Animation.RELATIVE_TO_PARENT, 0f);// 瀹氫箟鍥剧墖鏃嬭浆Y杞寸殑绫诲瀷鍜屽潗锟�
		RotateAnimation ra2 = new RotateAnimation(lastTime[1], timef[1],
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);

		// 璁剧疆鍔ㄧ敾鐨勬墽琛屾椂锟�
		ra2.setFillAfter(true);
		ra2.setFillBefore(true);
		ra2.setDuration(800);
		// 灏哛otateAnimation瀵硅薄娣诲姞鍒癆nimationSet
		// as2.addAnimation(ra2);
		// 灏嗗姩鐢讳娇鐢ㄥ埌ImageView
		rtnAni[1] = ra2;
		lastTime[1] = timef[1];
		return rtnAni;
	}

	private long lastUTime;
	private int down = 45;
	private int up = -45;

	@SuppressWarnings("deprecation")
	@Override
	public void onPositionChanged(ExtendedListView listView,
			int firstVisiblePosition, View scrollBarPanel) {
		System.out.println("layout=======padding top========"
				+ scrollBarPanel.getPaddingTop());
		TextView datestr = ((TextView) findViewById(R.id.clock_digital_date));
		datestr.setText("上午");
		ActivityMessage msg = messages.get(firstVisiblePosition);

		System.out.println("firstVisiblePosition============="
				+ firstVisiblePosition);
		System.out.println("scrollBarPanel class==="
				+ scrollBarPanel.getClass());

		int hour = (new Date(msg.getRealTime())).getHours();
		String tmpstr = "";
		if (hour > 12) {
			hour = hour - 12;
			datestr.setText("下午");
			tmpstr += " ";
		} else if (0 < hour && hour < 10) {

			tmpstr += " ";
		}
		tmpstr += hour + ":" + (new Date(msg.getRealTime())).getMinutes();
		((TextView) findViewById(R.id.clock_digital_time)).setText(tmpstr);
		RotateAnimation[] tmp = computeAni((new Date(msg.getRealTime())).getMinutes(), hour);

		System.out.println("tmp==========" + tmp);

		ImageView minView = (ImageView) findViewById(R.id.clock_face_minute);
		System.out.println("minView============" + minView);
		minView.startAnimation(tmp[0]);

		ImageView hourView = (ImageView) findViewById(R.id.clock_face_hour);
		hourView.setImageResource(R.drawable.clock_hour_rotatable);
		hourView.startAnimation(tmp[1]);

		lastUTime = msg.getRealTime();
		long curTime = System.currentTimeMillis();
		long d = lastUTime - curTime;
		
//		if (d > 0) {
			composerButtonsShowHideButtonIcon
					.startAnimation(MenuRightAnimations.getRotateAnimation(
							down - 45, down + 45, 600));
//		} else {
//			composerButtonsShowHideButtonIcon
//					.startAnimation(MenuRightAnimations.getRotateAnimation(
//							down + 45, down - 45, 600));
//		}
	}

	@Override
	public void onScollPositionChanged(View scrollBarPanel, int top) {

		System.out.println("onScollPositionChanged======================");
		MarginLayoutParams layoutParams = (MarginLayoutParams) clockLayout
				.getLayoutParams();
		System.out.println("left==" + layoutParams.leftMargin + " top=="
				+ layoutParams.topMargin + " bottom=="
				+ layoutParams.bottomMargin + " right=="
				+ layoutParams.rightMargin);
		layoutParams.setMargins(0, top, 0, 0);
		clockLayout.setLayoutParams(layoutParams);
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);

	}

	public class MyRecv extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(ACTION)) {
				Log.d("debug", "........");
				Intent newIntent = new Intent();
				newIntent.setClass(PublicActivity.this, AddEventActivity.class);
				newIntent.putExtra("title", intent.getSerializableExtra("title").toString());
				startActivity(newIntent);
				
			}
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(flag==0){
			Toast.makeText(PublicActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
			flag++;
			}
			else {
			flag=0;
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
			
			}
			// if (mRoot.getScreenState() == FlipperLayout.SCREEN_STATE_CLOSE) {
			// mRoot.open();
			// } else {
			//dialog();
			// }
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

//	private void dialog() {
//		AlertDialog.Builder builder = new Builder(PublicActivity.this);
//		builder.setMessage("鎮ㄧ‘瀹氳锟�锟斤拷锟�");
//		builder.setPositiveButton("纭畾", new DialogInterface.OnClickListener() {
//
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				finish();
//				android.os.Process.killProcess(android.os.Process.myPid());
//				System.exit(0);
//			}
//		});
//		builder.setNegativeButton("鍙栨秷", new DialogInterface.OnClickListener() {
//
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.cancel();
//			}
//		});
//		builder.create().show();
//	}

}
