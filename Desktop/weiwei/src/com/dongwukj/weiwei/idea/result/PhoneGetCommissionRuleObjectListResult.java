package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class PhoneGetCommissionRuleObjectListResult extends BaseResult {
	private ArrayList<CommissionRule> commissionRuleList;
	private ArrayList<CommissionRule> ruleDescribes;
	private int isFullt;
	private String discountprefix;
	private String discountsuffix;
	private String specialwords;
	private String fulltextreduction;
	
	public String getFulltextreduction() {
		return fulltextreduction;
	}

	public void setFulltextreduction(String fulltextreduction) {
		this.fulltextreduction = fulltextreduction;
	}

	public String getDiscountprefix() {
		return discountprefix;
	}

	public void setDiscountprefix(String discountprefix) {
		this.discountprefix = discountprefix;
	}

	

	public String getDiscountsuffix() {
		return discountsuffix;
	}

	public void setDiscountsuffix(String discountsuffix) {
		this.discountsuffix = discountsuffix;
	}

	public String getSpecialwords() {
		return specialwords;
	}

	public void setSpecialwords(String specialwords) {
		this.specialwords = specialwords;
	}

	public int getIsFullt() {
		return isFullt;
	}

	public void setIsFullt(int isFullt) {
		this.isFullt = isFullt;
	}

	public ArrayList<CommissionRule> getRuleDescribes() {
		return ruleDescribes;
	}

	public void setRuleDescribes(ArrayList<CommissionRule> ruleDescribes) {
		this.ruleDescribes = ruleDescribes;
	}

	public ArrayList<CommissionRule> getCommissionRuleList() {
		return commissionRuleList;
	}

	public void setCommissionRuleList(ArrayList<CommissionRule> commissionRuleList) {
		this.commissionRuleList = commissionRuleList;
	}
	
}
