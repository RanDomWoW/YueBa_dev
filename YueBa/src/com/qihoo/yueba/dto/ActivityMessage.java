package com.qihoo.yueba.dto;

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
	//empty used for loading header
	private long realTime;
	private Date realDate=new Date();
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
	public ActivityMessage(int isDate, String name, String title, String stime, String etime) {
		this.type = 1;
		this.isDate = isDate;
		this.name = name;
		this.title = title;
		this.startTime = stime;
		this.endTime = etime;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String t) {
		 this.title = t;
	}
	
	public String getStartTime() {

			return this.startTime;

	}
	public void setStartTime(String st) {
		this.startTime = st;
	}
	public String getEndTime() {
			return this.endTime;

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
