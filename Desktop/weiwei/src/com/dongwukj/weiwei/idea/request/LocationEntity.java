package com.dongwukj.weiwei.idea.request;

public class LocationEntity extends BaseRequest {
	private double lng;
	private double lat;
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	
}
