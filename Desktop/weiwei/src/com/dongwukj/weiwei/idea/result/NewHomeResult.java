package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.dongwukj.weiwei.idea.result.BaseResult;

public class NewHomeResult extends BaseResult {
	private float deliveryOfflinePrice;
	private float deliveryLatestTime;
	private ArrayList<NewHomeEntity> goodsList;
	private Integer total;
	private String discountprefix;
	private String discountsuffix;
	private String specialwords;
	private String fulltextreduction;
	public String getDiscountprefix() {
		return discountprefix;
	}

	public void setDiscountprefix(String discountprefix) {
		this.discountprefix = discountprefix;
	}

	public String getDiscountsuffix() {
		return discountsuffix;
	}

	public void setDiscountsuffix(String discountsuffix) {
		this.discountsuffix = discountsuffix;
	}

	public String getSpecialwords() {
		return specialwords;
	}

	public void setSpecialwords(String specialwords) {
		this.specialwords = specialwords;
	}

	public String getFulltextreduction() {
		return fulltextreduction;
	}

	public void setFulltextreduction(String fulltextreduction) {
		this.fulltextreduction = fulltextreduction;
	}

	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public float getDeliveryOfflinePrice() {
		return deliveryOfflinePrice;
	}
	public void setDeliveryOfflinePrice(float deliveryOfflinePrice) {
		this.deliveryOfflinePrice = deliveryOfflinePrice;
	}
	public float getDeliveryLatestTime() {
		return deliveryLatestTime;
	}
	public void setDeliveryLatestTime(float deliveryLatestTime) {
		this.deliveryLatestTime = deliveryLatestTime;
	}
	public ArrayList<NewHomeEntity> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(ArrayList<NewHomeEntity> goodsList) {
		this.goodsList = goodsList;
	}
	
	
}
