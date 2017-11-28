package com.dongwukj.weiwei.idea.result;

public class ComboEntity {
	private int pmId;
	private String photoUrl;
	private String name;
	private String describe;
	private double cruPrice;
	private double oldPrice;
	private String repertoryCount;
	private String participatorCount;
	private int onlyOnce;
	private String quotaUpper;
	private String ExtField1;
	private String body;
	private int url;
	
	public int getUrl() {
		return url;
	}
	public void setUrl(int url) {
		this.url = url;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getExtField1() {
		return ExtField1;
	}
	public void setExtField1(String extField1) {
		ExtField1 = extField1;
	}
	
	public float getExtField2() {
		return ExtField2;
	}
	public void setExtField2(float extField2) {
		ExtField2 = extField2;
	}
	public float getExtField3() {
		return ExtField3;
	}
	public void setExtField3(float extField3) {
		ExtField3 = extField3;
	}

	private float ExtField2;
	private float ExtField3;
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	public double getCruPrice() {
		return cruPrice;
	}
	public void setCruPrice(double cruPrice) {
		this.cruPrice = cruPrice;
	}
	public double getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(double oldPrice) {
		this.oldPrice = oldPrice;
	}
	public String getRepertoryCount() {
		return repertoryCount;
	}
	public void setRepertoryCount(String repertoryCount) {
		this.repertoryCount = repertoryCount;
	}
	
	public String getParticipatorCount() {
		return participatorCount;
	}
	public void setParticipatorCount(String participatorCount) {
		this.participatorCount = participatorCount;
	}
	
	public int getPmId() {
		return pmId;
	}
	public void setPmId(int pmId) {
		this.pmId = pmId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOnlyOnce() {
		return onlyOnce;
	}
	public void setOnlyOnce(int onlyOnce) {
		this.onlyOnce = onlyOnce;
	}
	public String getQuotaUpper() {
		return quotaUpper;
	}
	public void setQuotaUpper(String quotaUpper) {
		this.quotaUpper = quotaUpper;
	}
	@Override
	public String toString() {
		return "ComboEntity [pmId=" + pmId + ", photoUrl=" + photoUrl
				+ ", name=" + name + ", describe=" + describe + ", cruPrice="
				+ cruPrice + ", oldPrice=" + oldPrice + ", repertoryCount="
				+ repertoryCount + ", participatorCount=" + participatorCount
				+ ", onlyOnce=" + onlyOnce + ", quotaUpper=" + quotaUpper
				+ ", ExtField1=" + ExtField1 + ", body=" + body
				+ ", ExtField2=" + ExtField2 + ", ExtField3=" + ExtField3 + "]";
	}
	
	
	
}
