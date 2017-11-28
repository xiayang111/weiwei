package com.dongwukj.weiwei.idea.request;

public class PlotsRequest extends BaseRequest {

	private int regionId;
	private int plId;
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public int getPlId() {
		return plId;
	}
	public void setPlId(int plId) {
		this.plId = plId;
	}
	public PlotsRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PlotsRequest(int regionId, int plId) {
		super();
		this.regionId = regionId;
		this.plId = plId;
	}
	@Override
	public String toString() {
		return "PlotsRequest [regionId=" + regionId + ", plId=" + plId + "]";
	}

	
	
}
