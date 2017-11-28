package com.dongwukj.weiwei.idea.request;

public class PhoneorderpayeditstateRequest extends BaseRequest {
	private String osn;
	private int ordertype;
	private int type;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOsn() {
		return osn;
	}
	public void setOsn(String osn) {
		this.osn = osn;
	}
	public int getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(int ordertype) {
		this.ordertype = ordertype;
	}
	
}
