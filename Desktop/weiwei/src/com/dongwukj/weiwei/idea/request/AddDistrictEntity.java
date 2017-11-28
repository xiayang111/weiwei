package com.dongwukj.weiwei.idea.request;

import java.io.Serializable;

public class AddDistrictEntity implements Serializable{
	private int regionId_selected;
	private int plId;
	private String regionName;
	private String plotName;
	
	public int getRegionId_selected() {
		return regionId_selected;
	}

	public void setRegionId_selected(int regionId_selected) {
		this.regionId_selected = regionId_selected;
	}

	public int getPlId() {
		return plId;
	}

	public void setPlId(int plId) {
		this.plId = plId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getPlotName() {
		return plotName;
	}

	public void setPlotName(String plotName) {
		this.plotName = plotName;
	}

	public AddDistrictEntity(int regionId_selected, int plId,
			String regionName, String plotName) {
		super();
		this.regionId_selected = regionId_selected;
		this.plId = plId;
		this.regionName = regionName;
		this.plotName = plotName;
	}
	
}
