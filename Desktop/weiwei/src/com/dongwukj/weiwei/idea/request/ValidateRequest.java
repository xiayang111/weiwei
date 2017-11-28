package com.dongwukj.weiwei.idea.request;

public class ValidateRequest extends BaseRequest {

	private String noteverify;

	public String getNoteverify() {
		return noteverify;
	}

	public void setNoteverify(String noteverify) {
		this.noteverify = noteverify;
	}

	public ValidateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValidateRequest(String noteverify) {
		super();
		this.noteverify = noteverify;
	}

	@Override
	public String toString() {
		return "ValidateRequest [noteverify=" + noteverify + "]";
	}
	
}
