package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;

public class WeixinResult extends BaseResult implements Serializable{
	private String timeStampWX;
	private String appId;
	private String partnerId;
	private String prepayId;
	private String nonceStr;
	private String mysign;
	private String packageValue;
	private String sign;
	private String osn;
	
	public String getOsn() {
		return osn;
	}
	public void setOsn(String osn) {
		this.osn = osn;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTimeStampWX() {
		return timeStampWX;
	}
	public void setTimeStampWX(String timeStampWX) {
		this.timeStampWX = timeStampWX;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getPrepayId() {
		return prepayId;
	}
	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getMysign() {
		return mysign;
	}
	public void setMysign(String mysign) {
		this.mysign = mysign;
	}
	public String getPackageValue() {
		return packageValue;
	}
	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}

}
