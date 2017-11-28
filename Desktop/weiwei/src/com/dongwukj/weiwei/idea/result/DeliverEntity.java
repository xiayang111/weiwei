package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;

public class DeliverEntity implements Serializable{
	private String deliverDate;
	private String deliverTime;
	private String name;
	private int  mealtimes;
	private int sinceid;
	private float sincefee;
	
	public float getSincefee() {
		return sincefee;
	}
	public void setSincefee(float sincefee) {
		this.sincefee = sincefee;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeliverDate() {
		return deliverDate;
	}
	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}
	public String getDeliverTime() {
		return deliverTime;
	}
	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}
	public int getMealtimes() {
		return mealtimes;
	}
	public void setMealtimes(int mealtimes) {
		this.mealtimes = mealtimes;
	}
	public int getSinceid() {
		return sinceid;
	}
	public void setSinceid(int sinceid) {
		this.sinceid = sinceid;
	}
	
}
