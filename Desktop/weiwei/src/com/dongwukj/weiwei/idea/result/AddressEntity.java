package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;

public class AddressEntity implements Serializable{

	private String address;
	private String alias;
	private String consignee;
	private String email;
	private int isDefault;
	private boolean lockersId;
	private String mobile;
	private String phone;
	private String region;
	private int regionId;
	private int saId;
	private String zipCode;
	private Integer plotId;
	private String plotName;
	private String ctrnName;
	private Integer ctrnId;
	private int marketId;
	private Plot plot;

	
	public Plot getPlot() {
		return plot;
	}

	public void setPlot(Plot plot) {
		this.plot = plot;
	}

	public int getMarketId() {
		return marketId;
	}

	public void setMarketId(int marketId) {
		this.marketId = marketId;
	}

	public Integer getCtrnId() {
		return ctrnId;
	}

	public void setCtrnId(Integer ctrnId) {
		this.ctrnId = ctrnId;
	}

	public Integer getPlotId() {
		return plotId;
	}

	public void setPlotId(Integer plotId) {
		this.plotId = plotId;
	}

	public AddressEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
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
	
	
	
	public String getPlotName() {
		return plotName;
	}
	public void setPlotName(String plotName) {
		this.plotName = plotName;
	}
	public String getCtrnName() {
		return ctrnName;
	}
	public void setCtrnName(String ctrnName) {
		this.ctrnName = ctrnName;
	}


	
	
	
}
