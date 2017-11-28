package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class PhoneGetOrderExtendReceivingTimeResult extends BaseResult {
	   private ArrayList<TimeConfigLis> deliveryTimeConfigList;

	public ArrayList<TimeConfigLis> getDeliveryTimeConfigList() {
		return deliveryTimeConfigList;
	}

	public void setDeliveryTimeConfigList(
			ArrayList<TimeConfigLis> deliveryTimeConfigList) {
		this.deliveryTimeConfigList = deliveryTimeConfigList;
	}
	   
}
