package com.dongwukj.weiwei.idea.request;

public class Phonerecharge extends BaseRequest {
	private float SurplusMoney;
	private int payName;
	private String MobileNo;
	private float OrderAmount;
	private float Denomination;
	private String ip;
	private int Prid;
	
	public int getPrid() {
		return Prid;
	}
	public void setPrid(int prid) {
		Prid = prid;
	}
	public Integer getPayName() {
		return payName;
	}
	public void setPayName(Integer payName) {
		this.payName = payName;
	}
	
	public String getMobileNo() {
		return MobileNo;
	}
	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}
	
	
	public float getSurplusMoney() {
		return SurplusMoney;
	}
	public void setSurplusMoney(float surplusMoney) {
		SurplusMoney = surplusMoney;
	}
	public float getOrderAmount() {
		return OrderAmount;
	}
	public void setOrderAmount(float orderAmount) {
		OrderAmount = orderAmount;
	}
	public float getDenomination() {
		return Denomination;
	}
	public void setDenomination(float denomination) {
		Denomination = denomination;
	}
	public void setPayName(int payName) {
		this.payName = payName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
