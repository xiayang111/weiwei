package com.dongwukj.weiwei.idea.request;

public class PhoneScanGetCouponRequest extends BaseRequest {
	private String coupontypeid;
	private String ip;
	public String getCoupontypeid() {
		return coupontypeid;
	}
	public void setCoupontypeid(String coupontypeid) {
		this.coupontypeid = coupontypeid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
