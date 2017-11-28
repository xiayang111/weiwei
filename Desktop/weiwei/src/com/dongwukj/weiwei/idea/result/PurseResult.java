package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class PurseResult extends BaseResult {
	private String account;
	private String payCredits;
	private String coupons;
	private String redpaper;
	private ArrayList<PaymentEntity> payPluginList;
	
	public ArrayList<PaymentEntity> getPayPluginList() {
		return payPluginList;
	}
	public void setPayPluginList(ArrayList<PaymentEntity> payPluginList) {
		this.payPluginList = payPluginList;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPayCredits() {
		return payCredits;
	}
	public void setPayCredits(String payCredits) {
		this.payCredits = payCredits;
	}
	public String getCoupons() {
		return coupons;
	}
	public void setCoupons(String coupons) {
		this.coupons = coupons;
	}
	public String getRedpaper() {
		return redpaper;
	}
	public void setRedpaper(String redpaper) {
		this.redpaper = redpaper;
	}
	@Override
	public String toString() {
		return "PurseResult [account=" + account + ", payCredits=" + payCredits
				+ ", coupons=" + coupons + ", redpaper=" + redpaper + "]";
	}
	
}
