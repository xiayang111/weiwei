package com.dongwukj.weiwei.idea.result;

public class OrderDetailResult extends BaseResult {
	private OrderDetailEntity order;

	public OrderDetailEntity getOrder() {
		return order;
	}

	public void setOrder(OrderDetailEntity order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderDetailResult [order=" + order + "]";
	}
	
}
