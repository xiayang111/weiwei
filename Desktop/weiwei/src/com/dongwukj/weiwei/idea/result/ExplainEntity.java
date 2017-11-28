package com.dongwukj.weiwei.idea.result;

public class ExplainEntity {
	private String discountprefix;
	private String discountsuffix;
	private String specialwords;
	private String fulltextreduction;
	
	public ExplainEntity() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getFulltextreduction() {
		return fulltextreduction;
	}

	public void setFulltextreduction(String fulltextreduction) {
		this.fulltextreduction = fulltextreduction;
	}

	public ExplainEntity(String discountprefix, String discountsuffix,
			String specialwords, String fulltextreduction) {
		super();
		this.discountprefix = discountprefix;
		this.discountsuffix = discountsuffix;
		this.specialwords = specialwords;
		this.fulltextreduction = fulltextreduction;
	}

	
	
}
