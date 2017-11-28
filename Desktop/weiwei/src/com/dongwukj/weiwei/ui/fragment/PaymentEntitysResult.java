package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;

import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.PaymentEntity;

public class PaymentEntitysResult extends BaseResult {
	private ArrayList<PaymentEntity> payPluginList;

	public ArrayList<PaymentEntity> getPayPluginList() {
		return payPluginList;
	}

	public void setPayPluginList(ArrayList<PaymentEntity> payPluginList) {
		this.payPluginList = payPluginList;
	}
	
}
