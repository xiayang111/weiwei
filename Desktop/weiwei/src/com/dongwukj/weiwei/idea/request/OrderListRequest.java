package com.dongwukj.weiwei.idea.request;

public class OrderListRequest extends BaseRequest {

	private int page;		 //页码
	private int orderState;  //订单状态
	private int day;         //查询多少天以内的订单
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getOrderState() {
		return orderState;
	}
	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public OrderListRequest(int page, int orderState, int day) {
		super();
		this.page = page;
		this.orderState = orderState;
		this.day = day;
	}
	
	
	
	public OrderListRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "OrderListRequest [page=" + page + ", orderState=" + orderState
				+ ", day=" + day + "]";
	}
	
	
}
