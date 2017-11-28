package com.dongwukj.weiwei.idea.request;

public class PhoneGetProductTraceRequest extends BaseRequest {
	private String psn;
	private String suppid;
	private String deliveryDate;
	public String getPsn() {
		return psn;
	}
	public void setPsn(String psn) {
		this.psn = psn;
	}
	public String getSuppid() {
		return suppid;
	}
	public void setSuppid(String suppid) {
		this.suppid = suppid;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
}
