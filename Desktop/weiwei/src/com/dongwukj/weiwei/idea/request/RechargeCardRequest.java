package com.dongwukj.weiwei.idea.request;

public class RechargeCardRequest extends BaseRequest {
	private String cardNo;
	private String password;
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
