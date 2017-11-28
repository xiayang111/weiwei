package com.dongwukj.weiwei.idea.request;

public class PhoneReturnPayRequest extends BaseRequest {
	private Integer orderId;
	private int isoffline;
	
	public int getIsoffline() {
		return isoffline;
	}

	public void setIsoffline(int isoffline) {
		this.isoffline = isoffline;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
}
