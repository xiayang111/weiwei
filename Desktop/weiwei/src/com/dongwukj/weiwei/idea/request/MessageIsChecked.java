package com.dongwukj.weiwei.idea.request;

import com.litesuits.orm.db.annotation.PrimaryKey;

import com.litesuits.orm.db.annotation.Table;

@Table("MessageIsChecked")
public class MessageIsChecked {
	@PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
	private long id;
	private String userAccount;
	private int jpushid;
	private int isChecked;
	
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public int getJpushid() {
		return jpushid;
	}
	public void setJpushid(int jpushid) {
		this.jpushid = jpushid;
	}
	public int getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}
	
}
