package com.dongwukj.weiwei.idea.request;

import java.util.List;

public class OrderEvaluateRequest extends BaseRequest {
	List<OrderEvaluateEntity> OrderEvaluates;
	private float logistics;
	private float deliver;
	private float serve;
	public List<OrderEvaluateEntity> getOrderEvaluates() {
		return OrderEvaluates;
	}

	public void setOrderEvaluates(List<OrderEvaluateEntity> orderEvaluates) {
		OrderEvaluates = orderEvaluates;
	}

	public float getLogistics() {
		return logistics;
	}

	public void setLogistics(float logistics) {
		this.logistics = logistics;
	}

	public float getDeliver() {
		return deliver;
	}

	public void setDeliver(float deliver) {
		this.deliver = deliver;
	}

	public float getServe() {
		return serve;
	}

	public void setServe(float serve) {
		this.serve = serve;
	}

	@Override
	public String toString() {
		return "OrderEvaluateRequest [OrderEvaluates=" + OrderEvaluates
				+ ", logistics=" + logistics + ", deliver=" + deliver
				+ ", serve=" + serve + "]";
	}

	
	
	
}
