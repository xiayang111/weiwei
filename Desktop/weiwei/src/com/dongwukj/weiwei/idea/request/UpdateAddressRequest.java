package com.dongwukj.weiwei.idea.request;

public class UpdateAddressRequest extends BaseRequest {


	private String address;  	//地址
	private String alias;    	//地址别名
	private String consignee;   //收货人
	private String email;		//电子邮箱
	private int isDefault;		//是否默认地址
	private boolean lockersId;  //储物柜Id
	private String mobile;		//手机
	private String phone;		//固定电话
	private String region;		// 新接口暂时没有此字段
	private int regionId;		//区域id
	private int saId;    		//地址id
	private String zipCode;		//邮政编码
	
	
	
	private int plotId ; 		//小区id
	private int ctrnId ;		//快递柜id

	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	public boolean isLockersId() {
		return lockersId;
	}
	public void setLockersId(boolean lockersId) {
		this.lockersId = lockersId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public int getSaId() {
		return saId;
	}
	public void setSaId(int saId) {
		this.saId = saId;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	
	
	@Override
	public String toString() {
		return "UpdateAddressRequest [address=" + address + ", alias=" + alias
				+ ", consignee=" + consignee + ", email=" + email
				+ ", isDefault=" + isDefault + ", lockersId=" + lockersId
				+ ", mobile=" + mobile + ", phone=" + phone + ", region="
				+ region + ", regionId=" + regionId + ", saId=" + saId
				+ ", zipCode=" + zipCode + ", plotId=" + plotId + ", ctrnId="
				+ ctrnId + "]";
	}
	public int getPlotId() {
		return plotId;
	}
	public void setPlotId(int plotId) {
		this.plotId = plotId;
	}
	public int getCtrnId() {
		return ctrnId;
	}
	public void setCtrnId(int ctrnId) {
		this.ctrnId = ctrnId;
	}
	
	
	

}
