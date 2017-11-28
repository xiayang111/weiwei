package com.dongwukj.weiwei.idea.result;

public class OrderStatisticsResult extends BaseResult {

	private int orderCount;
	private int waitPaying ;
	private int preProducting ;
	private int sended ;
	private int completed;
	private int unfinishedOrderCount;
	private int unReviewOrderCount=0;
	private int finishedOrderCount=0;
	private int isOpenDownPay;
	private String downPayName;
	
	public int getIsOpenDownPay() {
		return isOpenDownPay;
	}
	public void setIsOpenDownPay(int isOpenDownPay) {
		this.isOpenDownPay = isOpenDownPay;
	}
	public String getDownPayName() {
		return downPayName;
	}
	public void setDownPayName(String downPayName) {
		this.downPayName = downPayName;
	}
	public int getUnfinishedOrderCount() {
		return unfinishedOrderCount;
	}
	public void setUnfinishedOrderCount(int unfinishedOrderCount) {
		this.unfinishedOrderCount = unfinishedOrderCount;
	}
	public int getUnReviewOrderCount() {
		return unReviewOrderCount;
	}
	public void setUnReviewOrderCount(int unReviewOrderCount) {
		this.unReviewOrderCount = unReviewOrderCount;
	}
	public int getFinishedOrderCount() {
		return finishedOrderCount;
	}
	public void setFinishedOrderCount(int finishedOrderCount) {
		this.finishedOrderCount = finishedOrderCount;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public int getWaitPaying() {
		return waitPaying;
	}
	public void setWaitPaying(int waitPaying) {
		this.waitPaying = waitPaying;
	}
	public int getPreProducting() {
		return preProducting;
	}
	public void setPreProducting(int preProducting) {
		this.preProducting = preProducting;
	}
	public int getSended() {
		return sended;
	}
	public void setSended(int sended) {
		this.sended = sended;
	}
	public int getCompleted() {
		return completed;
	}
	public void setCompleted(int completed) {
		this.completed = completed;
	}
	public OrderStatisticsResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderStatisticsResult(int orderCount, int waitPaying,
			int preProducting, int sended, int completed) {
		super();
		this.orderCount = orderCount;
		this.waitPaying = waitPaying;
		this.preProducting = preProducting;
		this.sended = sended;
		this.completed = completed;
	}
	@Override
	public String toString() {
		return "OrderStatisticsResult [orderCount=" + orderCount
				+ ", waitPaying=" + waitPaying + ", preProducting="
				+ preProducting + ", sended=" + sended + ", completed="
				+ completed + "]";
	}
}
