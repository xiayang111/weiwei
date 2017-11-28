package com.dongwukj.weiwei.idea.request;

public class SmsRequest extends BaseRequest {
	private String mobileNo;

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Override
	public String toString() {
		return "SmsRequest [mobileNo=" + mobileNo + "]";
	}    
	
}
