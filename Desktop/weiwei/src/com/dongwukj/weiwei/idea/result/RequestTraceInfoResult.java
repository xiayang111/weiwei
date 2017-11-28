package com.dongwukj.weiwei.idea.result;

public class RequestTraceInfoResult extends BaseResult {
	private String businessconfirmtime;//接单时间
	private String businessimg;//商户头像
	private String businesslicense;//营业执照
	private String businessname;//商户名称
	private String gname;//商品名称
	private String healthcertificate;//健康许可证
	private String hygienelicense;//卫生许可证
	private String ordertime;//下单时间
	private String osn;
	private String paymode;//支付方式
	private String paytime;//支付时间
	private String price;//商品价格
	private String realweight;//实际重量
	private String receivetime;//收货时间
	private String sendermobile;
	private String sendname;
	private String sendtime;//派送时间
	private String sortingtime;//分拣时间
	private String businessidcard;
	private String weight;
	
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBusinessidcard() {
		return businessidcard;
	}
	public void setBusinessidcard(String businessidcard) {
		this.businessidcard = businessidcard;
	}
	public String getBusinessconfirmtime() {
		return businessconfirmtime;
	}
	public void setBusinessconfirmtime(String businessconfirmtime) {
		this.businessconfirmtime = businessconfirmtime;
	}
	public String getBusinessimg() {
		return businessimg;
	}
	public void setBusinessimg(String businessimg) {
		this.businessimg = businessimg;
	}
	public String getBusinesslicense() {
		return businesslicense;
	}
	public void setBusinesslicense(String businesslicense) {
		this.businesslicense = businesslicense;
	}
	public String getBusinessname() {
		return businessname;
	}
	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getHealthcertificate() {
		return healthcertificate;
	}
	public void setHealthcertificate(String healthcertificate) {
		this.healthcertificate = healthcertificate;
	}
	public String getHygienelicense() {
		return hygienelicense;
	}
	public void setHygienelicense(String hygienelicense) {
		this.hygienelicense = hygienelicense;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public String getOsn() {
		return osn;
	}
	public void setOsn(String osn) {
		this.osn = osn;
	}
	public String getPaymode() {
		return paymode;
	}
	public void setPaymode(String paymode) {
		if (paymode.equals("0")) {
			this.paymode = "余额支付";
		}else if (paymode.equals("1")) {
			this.paymode = "支付宝支付";
		}else {
			this.paymode = "微信支付";
		}
		
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRealweight() {
		return realweight;
	}
	public void setRealweight(String realweight) {
		this.realweight = realweight+"克";
	}
	public String getReceivetime() {
		return receivetime;
	}
	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}
	public String getSendermobile() {
		return sendermobile;
	}
	public void setSendermobile(String sendermobile) {
		this.sendermobile = sendermobile;
	}
	public String getSendname() {
		return sendname;
	}
	public void setSendname(String sendname) {
		this.sendname = sendname;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	public String getSortingtime() {
		return sortingtime;
	}
	public void setSortingtime(String sortingtime) {
		this.sortingtime = sortingtime;
	}
	
	
}
