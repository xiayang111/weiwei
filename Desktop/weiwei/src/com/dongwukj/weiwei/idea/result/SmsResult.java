package com.dongwukj.weiwei.idea.result;

public class SmsResult extends BaseResult {

	private int limitTime;
	//private String smsCode;
	private String noteverify;
	
	public int getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(int limitTime) {
		this.limitTime = limitTime;
	}
	public String getNoteverify() {
		return noteverify;
	}
	public void setNoteverify(String noteverify) {
		this.noteverify = noteverify;
	}
	@Override
	public String toString() {
		return "SmsResult [limitTime=" + limitTime + ", noteverify="
				+ noteverify + "]";
	}
	public SmsResult(int limitTime, String noteverify) {
		super();
		this.limitTime = limitTime;
		this.noteverify = noteverify;
	}
	public SmsResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
