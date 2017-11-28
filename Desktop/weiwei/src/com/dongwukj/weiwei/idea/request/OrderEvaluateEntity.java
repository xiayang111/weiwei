package com.dongwukj.weiwei.idea.request;

public class OrderEvaluateEntity {
	private Integer recordId;
	private Integer orderId;
	private String message;
	private float star;
	
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public float getStar() {
		return star;
	}
	public void setStar(float star) {
		this.star = star;
	}
	@Override
	public String toString() {
		return "OrderEvaluateEntity [recordId=" + recordId + ", orderId="
				+ orderId + ", message=" + message + ", star=" + star + "]";
	}
	
	
}
