package com.dongwukj.weiwei.idea.request;

public class PhoneOrderExtendReceivingRequest extends BaseRequest {
	private Integer oid;
	private String deliveryTime;
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
}
