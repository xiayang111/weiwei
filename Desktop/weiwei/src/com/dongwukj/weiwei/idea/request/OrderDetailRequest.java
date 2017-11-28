package com.dongwukj.weiwei.idea.request;

public class OrderDetailRequest extends BaseRequest {
	private Integer orderId;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrderDetailRequest [orderId=" + orderId + "]";
	}

	public OrderDetailRequest(Integer orderId) {
		super();
		this.orderId = orderId;
	}
	
}
