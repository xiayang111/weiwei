package com.dongwukj.weiwei.idea.request;

public class RegisterEntity extends BaseRequest {

	private String userPassword;
	private String recomMander;
	private String registerip;
	private double lat;
	private double lng;
	private String Recommander;
	private String mobileNo;
	private int plotId;
	

	
	public int getPlotId() {
		return plotId;
	}
	public void setPlotId(int plotId) {
		this.plotId = plotId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getRecomMander() {
		return recomMander;
	}
	public void setRecomMander(String recomMander) {
		this.recomMander = recomMander;
	}
	public String getRegisterip() {
		return registerip;
	}
	public void setRegisterip(String registerip) {
		this.registerip = registerip;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getRecommander() {
		return Recommander;
	}
	public void setRecommander(String recommander) {
		Recommander = recommander;
	}
	@Override
	public String toString() {
		return "RegisterEntity [userPassword=" + userPassword
				+ ", recomMander=" + recomMander + ", registerip=" + registerip
				+ ", lat=" + lat + ", lng=" + lng + ", Recommander="
				+ Recommander + ", mobileNo=" + mobileNo + "]";
	}
	
	

	
}
