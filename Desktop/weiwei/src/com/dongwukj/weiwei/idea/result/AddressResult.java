package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class AddressResult extends BaseResult {

	private ArrayList<AddressEntity> shipAddresses;
	private int maxshipaddress;
	
	public int getMaxshipaddress() {
		return maxshipaddress;
	}

	public void setMaxshipaddress(int maxshipaddress) {
		this.maxshipaddress = maxshipaddress;
	}

	public AddressResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressResult(ArrayList<AddressEntity> shipAddresses) {
		super();
		this.shipAddresses = shipAddresses;
	}

	public ArrayList<AddressEntity> getShipAddresses() {
		return shipAddresses;
	}

	public void setShipAddresses(ArrayList<AddressEntity> shipAddresses) {
		this.shipAddresses = shipAddresses;
	}
	
}
