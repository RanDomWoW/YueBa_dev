package com.qihoo.yueba.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityMessage {
	// Text
	public static final int MESSAGE_TYPE_TEXT = 1;
	
	private int id;
	private String title;
	private int type;

	private String name;	 
	
	private String startTime = null;
	private String endTime = null;
	private String body=null;
	//empty used for loading header
	private long realTime;
	private Date tempDate=new Date();
	private SimpleDateFormat sdf;
	private int isDate;

	public ActivityMessage(){
		
	}
	
	/**
	 * // text
	 * 
	 * @param authorAvatar
	 * @param authorName
	 * @param storeName
	 * @param body
	 */
	public ActivityMessage(int isDate, String name, String title, String body, String stime, String etime) {
		this.type = 1;
		this.isDate = isDate;
		this.name = name;
		this.title = title;
		this.body = body;
		this.startTime = stime;
		this.endTime = etime;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String t) {
		 this.title = t;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String b) {
		 this.body = b;
	}
	public Date getStartTime()  {
		if(this.startTime!=null)
			
			try {
				tempDate=sdf.parse(this.startTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else tempDate=null;
		return tempDate;
		

	}
	public void setStartTime(String st) {
		this.startTime = st;
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(this.startTime!=null){
		try {
			this.realTime = sdf.parse(this.startTime).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	public Date getEndTime() {
		if(this.endTime!=null)
			try {
				tempDate=sdf.parse(this.endTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else tempDate=null;
		return tempDate;

	}
	public void setEndTime(String et) {
		this.endTime = et;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	 
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	public long getRealTime() {
		return realTime;
	}

	public void setRealTime(long time) {
		this.realTime = time;
	}

	public int getIsDate(){
		return this.isDate;
	}
	
	public void setIsDate(int flag) {
		this.isDate = flag;
	}


}
