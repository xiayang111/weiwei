package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;



public class AddAddressResult extends BaseResult implements Serializable{
	private AddressEntity shipAddress;
	private Market market;
	
	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public AddressEntity getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(AddressEntity shipAddress) {
		this.shipAddress = shipAddress;
	}
	
}
