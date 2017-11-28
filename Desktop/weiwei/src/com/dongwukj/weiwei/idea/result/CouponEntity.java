package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;

public class CouponEntity implements Serializable{
	private String name;
	private String couponid;
	private float money;
	private int usemode;
	private boolean ischecked;
	
	public boolean isIschecked() {
		return ischecked;
	}
	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCouponid() {
		return couponid;
	}
	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}

	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public int getUsemode() {
		return usemode;
	}
	public void setUsemode(int usemode) {
		this.usemode = usemode;
	}
	
}
