package com.dongwukj.weiwei.idea.result;

public class PrepaidRuleEntity {
	private float MinMoney;
	private float SendMoney;
	private Integer id;
	private Integer prid;
	private boolean ischecked=false;
	
	public boolean isIschecked() {
		return ischecked;
	}
	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}
	public Integer getPrid() {
		return prid;
	}
	public void setPrid(Integer prid) {
		this.prid = prid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public float getMinMoney() {
		return MinMoney;
	}
	public void setMinMoney(float minMoney) {
		MinMoney = minMoney;
	}
	public float getSendMoney() {
		return SendMoney;
	}
	public void setSendMoney(float sendMoney) {
		SendMoney = sendMoney;
	}
	
}
