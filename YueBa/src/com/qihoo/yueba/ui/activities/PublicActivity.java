package com.qihoo.yueba.ui.activities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
					Toast.makeText(PublicActivity.this, "施工ing...", Toast.LENGTH_SHORT).show();
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
		setAdapterForThis();
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
        setAdapterForThis();
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

	private void initMessages() {
		messages.clear();
		messages.add(new ActivityMessage());
		// data
		// text
//		messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "啥也没干",
//				"meisssss", "123", "123", 1333153510605l));
//		messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "啥也没干",
//				"meisssss", "123", "123", 1333163510605l));
//		messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "啥也没干",
//				"meisssss", "123", "123", 1333173510605l));
//		messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "啥也没干",
//				"meisssss", "123", "123", 1333183510605l));
//		
	}

	PublicActivityAdapter chatHistoryAdapter;

	private void setAdapterForThis() {
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
		// 创建RotateAnimation对象
		// 0--图片从哪�?��旋转
		// 360--图片旋转多少�?
		// Animation.RELATIVE_TO_PARENT, 0f,// 定义图片旋转X轴的类型和坐�?
		// Animation.RELATIVE_TO_PARENT, 0f);// 定义图片旋转Y轴的类型和坐�?
		RotateAnimation ra = new RotateAnimation(lastTime[0], timef[0],
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		ra.setFillAfter(true);
		ra.setFillBefore(true);
		// 设置动画的执行时�?
		ra.setDuration(800);
		// 将RotateAnimation对象添加到AnimationSet
		// as.addAnimation(ra);
		// 将动画使用到ImageView
		rtnAni[0] = ra;

		lastTime[0] = timef[0];

		// AnimationSet as2 = new AnimationSet(true);
		// 创建RotateAnimation对象
		// 0--图片从哪�?��旋转
		// 360--图片旋转多少�?
		// Animation.RELATIVE_TO_PARENT, 0f,// 定义图片旋转X轴的类型和坐�?
		// Animation.RELATIVE_TO_PARENT, 0f);// 定义图片旋转Y轴的类型和坐�?
		RotateAnimation ra2 = new RotateAnimation(lastTime[1], timef[1],
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);

		// 设置动画的执行时�?
		ra2.setFillAfter(true);
		ra2.setFillBefore(true);
		ra2.setDuration(800);
		// 将RotateAnimation对象添加到AnimationSet
		// as2.addAnimation(ra2);
		// 将动画使用到ImageView
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
			Toast.makeText(PublicActivity.this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
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
//		builder.setMessage("您确定要�?���?");
//		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				finish();
//				android.os.Process.killProcess(android.os.Process.myPid());
//				System.exit(0);
//			}
//		});
//		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.cancel();
//			}
//		});
//		builder.create().show();
//	}

}
