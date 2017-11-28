package com.dongwukj.weiwei.idea.result;

public class AlipayResult extends BaseResult {
	private String mysign;
	private String Partner;
	private String Seller_email;
	private String orderInfotext;
	private String osn;
	
	public String getOsn() {
		return osn;
	}

	public void setOsn(String osn) {
		this.osn = osn;
	}

	public String getPartner() {
		return Partner;
	}

	public void setPartner(String partner) {
		Partner = partner;
	}

	public String getSeller_email() {
		return Seller_email;
	}

	public void setSeller_email(String seller_email) {
		Seller_email = seller_email;
	}

	public String getOrderInfotext() {
		return orderInfotext;
	}

	public void setOrderInfotext(String orderInfotext) {
		this.orderInfotext = orderInfotext;
	}

	public String getMysign() {
		return mysign;
	}

	public void setMysign(String mysign) {
		this.mysign = mysign;
	}
	
}
