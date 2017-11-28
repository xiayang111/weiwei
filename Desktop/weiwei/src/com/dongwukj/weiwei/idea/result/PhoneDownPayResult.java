package com.dongwukj.weiwei.idea.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneDownPayResult extends BaseResult {
	private String addtime;
	private float discountafteramount;
	private int oid;
	private float orderamount;
	private String osn;
	private float usermoney;
	
	
	
	
	public float getDiscountafteramount() {
		return discountafteramount;
	}

	public void setDiscountafteramount(float discountafteramount) {
		this.discountafteramount = discountafteramount;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public float getOrderamount() {
		return orderamount;
	}

	public void setOrderamount(float orderamount) {
		this.orderamount = orderamount;
	}

	public String getOsn() {
		return osn;
	}

	public void setOsn(String osn) {
		this.osn = osn;
	}

	public float getUsermoney() {
		return usermoney;
	}

	public void setUsermoney(float usermoney) {
		this.usermoney = usermoney;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		 Pattern pattern = Pattern.compile("\\d+");
	        Matcher matcher = pattern.matcher(addtime);
	        if(matcher.find()){
	            this.addtime=matcher.group();
	            return;
	        }
	        this.addtime = addtime;
	}

	
    
}
