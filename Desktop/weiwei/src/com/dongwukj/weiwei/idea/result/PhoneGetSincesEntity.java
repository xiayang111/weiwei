package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class PhoneGetSincesEntity {
	private String address;
	private int sid;
	private String name;
	private float sincefee;
	
	public float getSincefee() {
		return sincefee;
	}
	public void setSincefee(float sincefee) {
		this.sincefee = sincefee;
	}
	private ArrayList<sinceMealtimesEntity> sinceMealtimes;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<sinceMealtimesEntity> getSinceMealtimes() {
		return sinceMealtimes;
	}
	public void setSinceMealtimes(ArrayList<sinceMealtimesEntity> sinceMealtimes) {
		this.sinceMealtimes = sinceMealtimes;
	}





}
