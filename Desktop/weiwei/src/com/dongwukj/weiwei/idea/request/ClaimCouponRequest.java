package com.dongwukj.weiwei.idea.request;

public class ClaimCouponRequest extends BaseRequest {

	private int couponTypeId;
	private String pullIP;
	
	public int getCouponTypeId() {
		return couponTypeId;
	}
	public void setCouponTypeId(int couponTypeId) {
		this.couponTypeId = couponTypeId;
	}
	public String getPullIP() {
		return pullIP;
	}
	public void setPullIP(String pullIP) {
		this.pullIP = pullIP;
	}
	
	
	
}
