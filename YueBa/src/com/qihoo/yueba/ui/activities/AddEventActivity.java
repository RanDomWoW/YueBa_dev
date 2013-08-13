package com.qihoo.yueba.ui.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ckt.vas.miles.R;
import com.qihoo.yueba.db.service.DBService;
import com.qihoo.yueba.dto.ActivityMessage;
import com.renren.android.util.SlipButton;

public class AddEventActivity extends Activity {
	// Time changed flag
	private boolean timeChanged = false;
	
	// Time scrolled flag
	private boolean timeScrolled = false;
	private DBService dbService;
	private TextView start_time;
	private TextView start_time_a;
	private TextView end_time;
	private TextView end_time_a;
	private ImageView save_time;
	private ImageView add_cancel;
	private EditText event_title;
	private EditText event_body;
	private View v;
	private SimpleDateFormat sdf;
	private Date st=new Date();
	private Date et=new Date();
    private SlipButton sb = null;
    private Button btn = null;
    private String title_tp;
    private ActivityMessage p;
    private ActivityMessage p_temp;
    private int flag =0;
    private int c_year=0;
    private int c_month=0;
    private int c_date=0;
    private String getter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		
		dbService = new DBService(AddEventActivity.this);
		p=new ActivityMessage();
		p_temp=new ActivityMessage();
		v=(FrameLayout)LayoutInflater.from(this).inflate(R.layout.activity_fullscreen, null); 
		start_time= (TextView)findViewById(R.id.eventStartTime);
		start_time_a= (TextView)findViewById(R.id.eventStartTime_a);
		end_time= (TextView)findViewById(R.id.eventEndTime);
		end_time_a= (TextView)findViewById(R.id.eventEndTime_a);
		save_time= (ImageView)findViewById(R.id.save);
		add_cancel= (ImageView)findViewById(R.id.cancel);
		event_title= (EditText)findViewById(R.id.scheduleTitle);
		event_body= (EditText)findViewById(R.id.scheduleText);
		sdf = new SimpleDateFormat("HH:mm");
		if(getIntent().getSerializableExtra("title")!=null){
			event_title.setText(getIntent().getSerializableExtra("title").toString());
			if((p_temp=dbService.findByTitle(getIntent().getSerializableExtra("title").toString()))!=null){
				
				p=p_temp;
				
				//event_title.setText(p.getTitle());
				
				try {
					start_time.setText("已设置起始时间为:"+sdf.format(p.getStartTime()));
					end_time.setText("已设置终止时间为:"+sdf.format(p.getEndTime()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//event_body.setText(p.getBody());
				flag=1;
		}

			
		}

		
//        findView();
//        setListener();
		
		

		
		
        //sb = (SlipButton) v.findViewById(R.id.splitbutton);
        //btn = (Button) v.findViewById(R.id.ringagain);
        //sb.setCheck(true);
        
		//数字轮盘
		final WheelView hours = (WheelView) findViewById(R.id.hour);
		hours.setViewAdapter(new NumericWheelAdapter(this, 0, 23));
	
		final WheelView mins = (WheelView) findViewById(R.id.mins);
		mins.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
		mins.setCyclic(true);
		
		//获取时间
		final TimePicker picker = (TimePicker) findViewById(R.id.time);
		picker.setIs24HourView(true);
		
		//初始化为当前时间
		Calendar c = Calendar.getInstance();
		int curHours = c.get(Calendar.HOUR_OF_DAY);
		int curMinutes = c.get(Calendar.MINUTE);
		
		c_year=c.get(Calendar.YEAR);
		c_date=c.get(Calendar.DATE);
		c_month=c.get(Calendar.MONTH);
		
		hours.setCurrentItem(curHours);
		mins.setCurrentItem(curMinutes);
	
		picker.setCurrentHour(curHours);
		picker.setCurrentMinute(curMinutes);
	
		//添加监听器
		addChangingListener(mins, "min");
		addChangingListener(hours, "hour");
		
//        sb.SetOnChangedListener(new OnChangedListener()
//        {
//            
//            public void OnChanged(boolean CheckState)
//            {
//                btn.setText(CheckState ? "起始时间" : "结束时间");
//            }
//        });
        
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					timeChanged = true;
					//获取当前轮盘时间并设置
					picker.setCurrentHour(hours.getCurrentItem());
					picker.setCurrentMinute(mins.getCurrentItem());

					timeChanged = false;
				}
			}
		};
		hours.addChangingListener(wheelListener);
		mins.addChangingListener(wheelListener);
		
		OnWheelClickedListener click = new OnWheelClickedListener() {
            public void onItemClicked(WheelView wheel, int itemIndex) {
                wheel.setCurrentItem(itemIndex, true);
            }
        };
        hours.addClickingListener(click);
        mins.addClickingListener(click);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}
			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true;
				picker.setCurrentHour(hours.getCurrentItem());
				picker.setCurrentMinute(mins.getCurrentItem());
				getter=hours.getCurrentItem()+":"+mins.getCurrentItem();
				try {
					st=sdf.parse(getter);
					et=sdf.parse(getter);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				timeChanged = false;
			}
		};
		
		hours.addScrollingListener(scrollListener);
		mins.addScrollingListener(scrollListener);
		
		picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			public void onTimeChanged(TimePicker  view, int hourOfDay, int minute) {
				if (!timeChanged) {
					hours.setCurrentItem(hourOfDay, true);
					mins.setCurrentItem(minute, true);
					
				}
			}
		});
		
		start_time_a.setFocusable(true);
		start_time_a.setOnFocusChangeListener(new OnFocusChangeListener(){
			public void onFocusChange(View v,boolean hasFocus) {
				if(hasFocus) {

					// 此处为得到焦点时的处理内容
					} 
					else {
						
						start_time.setBackgroundResource(0);
						start_time.invalidate();
						// 此处为失去焦点时的处理内容
						} 
				
					


			} 


			
		
		});
		start_time_a.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				start_time.setBackgroundResource(R.drawable.m_bar1);
				start_time_a.setText(sdf.format(st));
				
			}
		});
		end_time_a.setFocusable(true);
		end_time_a.setOnFocusChangeListener(new OnFocusChangeListener(){

			public void onFocusChange(View v,boolean hasFocus) {


				if(hasFocus) {

					// 此处为得到焦点时的处理内容
					} 
					else {
						
						end_time.setBackgroundResource(0);
						end_time.invalidate();
						// 此处为失去焦点时的处理内容
						} 

		}
			
		});
		end_time_a.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				end_time.setBackgroundResource(R.drawable.m_bar1);
				end_time_a.setText(sdf.format(et));
			}
		});
		save_time.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//设置用户名称
				p.setName("RanDom");
				if(event_title.getText()!=null)
				p.setTitle(event_title.getText().toString());
				if(event_body.getText()!=null)
				p.setBody(event_body.getText().toString());
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					getter=c_year+"-"+c_month+"-"+c_date+" "+sdf.format(st);
					p.setStartTime(getter);
					getter=c_year+"-"+c_month+"-"+c_date+" "+sdf.format(et);
					p.setEndTime(getter);
					
				try {
				if(flag==1){
					//Log.d("ab", dbService.findByTitle("RanDom").getBody());

						dbService.update(p);

					flag=0;
				}
				else 
					dbService.save(p);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(getApplication(), "保存成功！", Toast.LENGTH_SHORT).show();
				dbService.closeDB();
				AddEventActivity.this.finish();
			}
		});
		add_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AddEventActivity.this.finish();
			}
		});
		
		
	}
	
	
//	@Override
//	public void onDestroy() {
//		if(dbService != null){
//			dbService.closeDB();
//		}
//	}

	/**
	 * Adds changing listener for wheel that updates the wheel label
	 * @param wheel the wheel
	 * @param label the wheel label
	 */
	private void addChangingListener(final WheelView wheel, final String label) {
		wheel.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				//wheel.setLabel(newValue != 1 ? label + "s" : label);
			}
		});
	
	
	
}
}
