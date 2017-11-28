package com.dongwukj.weiwei.idea.request;

public class PhoneJpushRequest extends BaseRequest {
	private int businesstype;
	private int pageNumber;
	public int getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(int businesstype) {
		this.businesstype = businesstype;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}
