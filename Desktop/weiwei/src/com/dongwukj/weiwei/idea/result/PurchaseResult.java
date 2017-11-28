package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class PurchaseResult extends BaseResult {
	private ArrayList<ComboEntity> listProduct;
	private int listNumber;
	private long limitTime;
	public ArrayList<ComboEntity> getListProduct() {
		return listProduct;
	}
	public void setListProduct(ArrayList<ComboEntity> listProduct) {
		this.listProduct = listProduct;
	}
	public int getListNumber() {
		return listNumber;
	}
	public void setListNumber(int listNumber) {
		this.listNumber = listNumber;
	}
	public long getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(long limitTime) {
		this.limitTime = limitTime;
	}
	@Override
	public String toString() {
		return "PurchaseResult [listProduct=" + listProduct + ", listNumber="
				+ listNumber + ", limitTime=" + limitTime + "]";
	}
	
}
