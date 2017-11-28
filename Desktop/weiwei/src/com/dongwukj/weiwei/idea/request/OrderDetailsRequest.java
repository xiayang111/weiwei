package com.dongwukj.weiwei.idea.request;

public class OrderDetailsRequest extends BaseRequest {

	private int orderId;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public OrderDetailsRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDetailsRequest(int orderId) {
		super();
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrderDetailsRequest [orderId=" + orderId + "]";
	}
	
}
