package com.dongwukj.weiwei.test;

import com.dongwukj.weiwei.idea.request.BaseRequest;

public class PhoneCollapseLogRequest extends BaseRequest {
	private String PhoneType;
	private String collapseTime;
	private String collapseLog;
	private String phoneVersion;
	private String phoneName;
	
	public String getPhoneName() {
		return phoneName;
	}
	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}
	public String getPhoneType() {
		return PhoneType;
	}
	public void setPhoneType(String phoneType) {
		PhoneType = phoneType;
	}
	public String getCollapseTime() {
		return collapseTime;
	}
	public void setCollapseTime(String collapseTime) {
		this.collapseTime = collapseTime;
	}
	public String getCollapseLog() {
		return collapseLog;
	}
	public void setCollapseLog(String collapseLog) {
		this.collapseLog = collapseLog;
	}
	public String getPhoneVersion() {
		return phoneVersion;
	}
	public void setPhoneVersion(String phoneVersion) {
		this.phoneVersion = phoneVersion;
	}
	
}
