package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;

public class NewPhoneConfirmOrderResult extends BaseResult implements Serializable{
	private ConfirmOrderObject confirmOrderObject;
	private String discountprefix;
	private String discountsuffix;
	private String specialwords;
	private String fulltextreduction;
	
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

	public String getFulltextreduction() {
		return fulltextreduction;
	}

	public void setFulltextreduction(String fulltextreduction) {
		this.fulltextreduction = fulltextreduction;
	}

	public ConfirmOrderObject getConfirmOrderObject() {
		return confirmOrderObject;
	}

	public void setConfirmOrderObject(ConfirmOrderObject confirmOrderObject) {
		this.confirmOrderObject = confirmOrderObject;
	}
	
}
