package com.dongwukj.weiwei.idea.request;

import java.util.ArrayList;

import com.dongwukj.weiwei.idea.result.CommissionRule;

public class NewPhoneConfirmOrderRequest extends BaseRequest {
	private ArrayList<NewHomeEntity> goodsList;
	private int totalamount;
	private  CommissionRule ruleObject;
	private int mid;
	
	public CommissionRule getRuleObject() {
		return ruleObject;
	}
	public void setRuleObject(CommissionRule ruleObject) {
		this.ruleObject = ruleObject;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public ArrayList<NewHomeEntity> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(ArrayList<NewHomeEntity> goodsList) {
		this.goodsList = goodsList;
	}
	public int getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(int totalamount) {
		this.totalamount = totalamount;
	}
	
}
