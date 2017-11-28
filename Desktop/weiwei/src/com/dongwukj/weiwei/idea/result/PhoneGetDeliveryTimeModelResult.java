package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class PhoneGetDeliveryTimeModelResult extends BaseResult {
	private ArrayList<DeliveryTimeObject> deliveryTimeObjects;

	public ArrayList<DeliveryTimeObject> getDeliveryTimeObjects() {
		return deliveryTimeObjects;
	}

	public void setDeliveryTimeObjects(
			ArrayList<DeliveryTimeObject> deliveryTimeObjects) {
		this.deliveryTimeObjects = deliveryTimeObjects;
	}
	
}
