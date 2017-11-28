package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dongwukj.weiwei.idea.request.NewHomeEntity;

public class ConfirmOrderObject implements Serializable{
	private String defaultPayPluginInfo;
	private String deliveryEndTime;
	private String deliveryStartTime;
	private float orderAmount;
	private ArrayList<PaymentEntity> payPluginList;
	private float productAmount;
	private ArrayList<AddressEntity> shipAddressList;
	private float shipFee;
	private String deliverDate;
	private ArrayList<String> deliverTime;
	private float UserPayMoney;
	private ArrayList<CouponEntity> validCouponList;
	private ArrayList<NewHomeEntity> returnBusinessGoodsList;
	private CommissionRule commissionRule;
	private float paydiscountamount;
	private int canSince;
	private int isFullt;
	private int isToDoor;
	
	public int getIsToDoor() {
		return isToDoor;
	}
	public void setIsToDoor(int isToDoor) {
		this.isToDoor = isToDoor;
	}
	public int getIsFullt() {
		return isFullt;
	}
	public void setIsFullt(int isFullt) {
		this.isFullt = isFullt;
	}
	public int getCanSince() {
		return canSince;
	}
	public void setCanSince(int canSince) {
		this.canSince = canSince;
	}
	public float getPaydiscountamount() {
		return paydiscountamount;
	}
	public void setPaydiscountamount(float paydiscountamount) {
		this.paydiscountamount = paydiscountamount;
	}
	public CommissionRule getCommissionRule() {
		return commissionRule;
	}
	public void setCommissionRule(CommissionRule commissionRule) {
		this.commissionRule = commissionRule;
	}
	public ArrayList<NewHomeEntity> getReturnBusinessGoodsList() {
		return returnBusinessGoodsList;
	}
	public void setReturnBusinessGoodsList(
			ArrayList<NewHomeEntity> returnBusinessGoodsList) {
		this.returnBusinessGoodsList = returnBusinessGoodsList;
	}
	public String getDeliverDate() {
		return deliverDate;
	}
	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}
	public ArrayList<String> getDeliverTime() {
		return deliverTime;
	}
	public void setDeliverTime(ArrayList<String> deliverTime) {
		this.deliverTime = deliverTime;
	}
	public String getDefaultPayPluginInfo() {
		return defaultPayPluginInfo;
	}
	public void setDefaultPayPluginInfo(String defaultPayPluginInfo) {
		this.defaultPayPluginInfo = defaultPayPluginInfo;
	}
	public String getDeliveryEndTime() {
		return deliveryEndTime;
	}
	public void setDeliveryEndTime(String deliveryEndTime) {
		 Pattern pattern = Pattern.compile("\\d+");
	        Matcher matcher = pattern.matcher(deliveryEndTime);
	        if(matcher.find()){
	            this.deliveryEndTime=matcher.group();
	            return;
	        }
	        this.deliveryEndTime = deliveryEndTime;
	}
	public String getDeliveryStartTime() {
		return deliveryStartTime;
	}
	public void setDeliveryStartTime(String deliveryStartTime) {
		   Pattern pattern = Pattern.compile("\\d+");
	        Matcher matcher = pattern.matcher(deliveryStartTime);
	        if(matcher.find()){
	            this.deliveryStartTime=matcher.group();
	            return;
	        }
	        this.deliveryStartTime = deliveryStartTime;
		
	}
	public float getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(float orderAmount) {
		this.orderAmount = orderAmount;
	}
	public ArrayList<PaymentEntity> getPayPluginList() {
		return payPluginList;
	}
	public void setPayPluginList(ArrayList<PaymentEntity> payPluginList) {
		this.payPluginList = payPluginList;
	}
	public float getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(float productAmount) {
		this.productAmount = productAmount;
	}
	public ArrayList<AddressEntity> getShipAddressList() {
		return shipAddressList;
	}
	public void setShipAddressList(ArrayList<AddressEntity> shipAddressList) {
		this.shipAddressList = shipAddressList;
	}
	public float getShipFee() {
		return shipFee;
	}
	public void setShipFee(float shipFee) {
		this.shipFee = shipFee;
	}
	public float getUserPayMoney() {
		return UserPayMoney;
	}
	public void setUserPayMoney(float userPayMoney) {
		UserPayMoney = userPayMoney;
	}
	public ArrayList<CouponEntity> getValidCouponList() {
		return validCouponList;
	}
	public void setValidCouponList(ArrayList<CouponEntity> validCouponList) {
		this.validCouponList = validCouponList;
	}

}























