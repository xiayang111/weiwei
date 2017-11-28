package com.dongwukj.weiwei.idea.result;



public class PhoneGetDefaultAddressResult extends BaseResult {
	private Plot plot;
	private AddressEntity shipAddress;
	
	
	public Plot getPlot() {
		return plot;
	}

	public void setPlot(Plot plot) {
		this.plot = plot;
	}

	public AddressEntity getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(AddressEntity shipAddress) {
		this.shipAddress = shipAddress;
	}


}
