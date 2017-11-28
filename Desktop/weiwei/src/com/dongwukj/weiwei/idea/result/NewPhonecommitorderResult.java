package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewPhonecommitorderResult extends BaseResult implements Serializable{
	private  long oid;
	private String osn;
	private String orderTime;
	private float surplusmoney;
	private float paydiscountamount;
	
	
	
	public float getPaydiscountamount() {
		return paydiscountamount;
	}
	public void setPaydiscountamount(float paydiscountamount) {
		this.paydiscountamount = paydiscountamount;
	}
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public String getOsn() {
		return osn;
	}
	public void setOsn(String osn) {
		this.osn = osn;
	}
	  public String getOrderTime() {
	        return orderTime;
	    }

	    public void setOrderTime(String orderTime) {
	        Pattern pattern = Pattern.compile("\\d+");
	        Matcher matcher = pattern.matcher(orderTime);
	        if(matcher.find()){
	            this.orderTime=matcher.group();
	            return;
	        }
	        this.orderTime = orderTime;
	    }
	public float getSurplusmoney() {
		return surplusmoney;
	}
	public void setSurplusmoney(float surplusmoney) {
		this.surplusmoney = surplusmoney;
	}
	
	
}
