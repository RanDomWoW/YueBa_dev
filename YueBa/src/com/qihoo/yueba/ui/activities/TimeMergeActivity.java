
package com.qihoo.yueba.ui.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ckt.vas.miles.R;
import com.qihoo.yueba.db.service.DBService;
import com.qihoo.yueba.dto.ActivityMessage;
import com.qihoo.yueba.ui.adapters.Desktop;
import com.qihoo.yueba.ui.adapters.TimeMergeActivityAdapter;
import com.qihoo.yueba.ui.views.ExtendedListView;
import com.qihoo.yueba.ui.views.ExtendedListView.OnPositionChangedListener;
import com.qihoo.yueba.ui.views.InOutFrameLayout;
import com.qihoo.yueba.ui.views.MenuRightAnimations;

public class TimeMergeActivity extends Activity implements OnTouchListener, OnPositionChangedListener {
    /** Called when the activity is first created. */
    private boolean areButtonsShowing;
    private RelativeLayout composerButtonsWrapper;
    private ImageView composerButtonsShowHideButtonIcon;
    private RelativeLayout composerButtonsShowHideButton;
    private RelativeLayout overlayView;

    private ExtendedListView dataListView;

    // clock
    private FrameLayout clockLayout;

    // activity_overlay

    private TextView timeshow;
    private com.qihoo.yueba.ui.adapters.Desktop mDesktop;
    SlidingMenu mSlidingMenu;
    
	DBService db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		db = new DBService(TimeMergeActivity.this);

        setContentView(R.layout.feed_activity3);
        
        mDesktop = new Desktop(this);
		mSlidingMenu = new SlidingMenu(this);
		setContentView(mSlidingMenu);
		View menu = mDesktop.getView();
		View content = getLayoutInflater().inflate(R.layout.feed_activity3,
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
        
        ImageView iv = (ImageView) findViewById(R.id.qa_bar_friends);
        iv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(TimeMergeActivity.this, FriendListActivity.class);
//				startActivity(intent);
				TimeMergeActivity.this.finish();
			}
		});
        
        MenuRightAnimations.initOffset(TimeMergeActivity.this);
        System.out.println(" findViewById(R.id.composer_buttons_wrapper);=="
                + findViewById(R.id.composer_buttons_wrapper));
       
        //
        dataListView = (ExtendedListView) findViewById(R.id.list_view);
        setAdapterForThis();
    }
    
    public void onClickView(View v, boolean isOnlyClose) {
        if (isOnlyClose) {
            if (areButtonsShowing) {
                MenuRightAnimations.startAnimationsOut(composerButtonsWrapper, 300);
                composerButtonsShowHideButtonIcon.startAnimation(MenuRightAnimations
                        .getRotateAnimation(-315, 0, 300));
                areButtonsShowing = !areButtonsShowing;
            }

        } else {

            if (!areButtonsShowing) {
                MenuRightAnimations.startAnimationsIn(composerButtonsWrapper, 300);
                composerButtonsShowHideButtonIcon.startAnimation(MenuRightAnimations
                        .getRotateAnimation(0, -315, 300));
            } else {
                MenuRightAnimations.startAnimationsOut(composerButtonsWrapper, 300);
                composerButtonsShowHideButtonIcon.startAnimation(MenuRightAnimations
                        .getRotateAnimation(-315, 0, 300));
            }
            areButtonsShowing = !areButtonsShowing;
        }

    }
    
    
 
    private List<ActivityMessage> messages = new ArrayList<ActivityMessage>();
    private void initMessages() {
        // set header
        //messages.add(new ActivityMessage());
        // data
        // text
  /*      messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "上课ing", "真不错",
                null, null));*/
//    	messages.add(new ActivityMessage( R.drawable.gauss0, "Gauss", "8:00至9:00", "空闲时间： 8:00至9:00",
//                "8:00", "9:00", 1333253510605l));
//    	messages.add(new ActivityMessage( R.drawable.gauss0, "Gauss", "8:00至9:00", "空闲时间： 12:00至13:50",
//                "8:00", "9:00", 1333253510605l));
//    	messages.add(new ActivityMessage( R.drawable.gauss0, "Gauss", "8:00至9:00", "空闲时间： 18:00至19:00",
//                "8:00", "9:00", 1333253510605l));
    	
		messages.clear();
		messages.add(new ActivityMessage());
		int i = 0;
		int size = db.findByName("Bian").size();
		for (i = 0; i < size; i ++) {
			messages.add(db.findByName("Bian").get(i));
		}
//		size = db.findByName("Lei").size();
//		for (i = 0; i < size; i ++) {
//			messages.add(db.findByName("Lei").get(i));
//		}
    }

    TimeMergeActivityAdapter chatHistoryAdapter;

    private void setAdapterForThis() {
        initMessages();
        chatHistoryAdapter = new TimeMergeActivityAdapter(this, messages);
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

    private float[] lastTime = {
            0f, 0f
    };

   

    @Override
    public void onPositionChanged(ExtendedListView listView, int firstVisiblePosition,
            View scrollBarPanel) {
        System.out.println("layout=======padding top========"+scrollBarPanel.getPaddingTop());
  

    }

    @Override
    public void onScollPositionChanged(View scrollBarPanel,int top) {
       
        System.out.println("onScollPositionChanged======================");
        MarginLayoutParams layoutParams = (MarginLayoutParams)clockLayout.getLayoutParams();
        System.out.println("left=="+layoutParams.leftMargin+" top=="+layoutParams.topMargin+" bottom=="+layoutParams.bottomMargin+" right=="+layoutParams.rightMargin);
        layoutParams.setMargins(0, top, 0, 0);
        clockLayout.setLayoutParams(layoutParams);
    }

}
