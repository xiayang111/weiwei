package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class PhoneprepaidruleResult extends BaseResult {
	private ArrayList<PaymentEntity> payPluginList;
	private ArrayList<PrepaidRuleEntity> PrepaidRuleList;
	public ArrayList<PaymentEntity> getPayPluginList() {
		return payPluginList;
	}
	public void setPayPluginList(ArrayList<PaymentEntity> payPluginList) {
		this.payPluginList = payPluginList;
	}
	public ArrayList<PrepaidRuleEntity> getPrepaidRuleList() {
		return PrepaidRuleList;
	}
	public void setPrepaidRuleList(ArrayList<PrepaidRuleEntity> prepaidRuleList) {
		PrepaidRuleList = prepaidRuleList;
	}
	
}
