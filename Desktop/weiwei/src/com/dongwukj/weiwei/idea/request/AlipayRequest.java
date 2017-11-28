package com.dongwukj.weiwei.idea.request;

public class AlipayRequest extends BaseRequest {
	private Integer orderId;
	private Integer logType;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getLogType() {
		return logType;
	}
	public void setLogType(Integer logType) {
		this.logType = logType;
	}
	
}
