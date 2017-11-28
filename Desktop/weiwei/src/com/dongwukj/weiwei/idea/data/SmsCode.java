package com.dongwukj.weiwei.idea.data;

import java.util.ArrayList;

import net.tsz.afinal.annotation.sqlite.Id;

public class SmsCode {
	@Id(column="id") 		//设置id 为主键,并且自增
	private int id;
	private String message; //存放短信验证码的集合
	private int smscount ;				//短信验证码的次数
	private long startTime;				//开始时间
	private String tel;
	
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public boolean isLock() {
		return isLock;
	}
	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}
	private long endTime;				//结束判断时间
	
	private boolean isLock;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getSmscount() {
		return smscount;
	}
	public void setSmscount(int smscount) {
		this.smscount = smscount;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	
	
}
