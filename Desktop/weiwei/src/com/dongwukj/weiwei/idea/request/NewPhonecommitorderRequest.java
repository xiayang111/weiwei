package com.dongwukj.weiwei.idea.request;

import java.util.ArrayList;

import com.dongwukj.weiwei.idea.result.CommissionRule;

public class NewPhonecommitorderRequest extends BaseRequest {
	private String deliverTime;
	private String deliverDate;
	private String buyerRemark;
	private int plotId;
	private ArrayList<NewHomeEntity> goodsList=new ArrayList<NewHomeEntity>();
	private int saId;
	private ArrayList<String> couponIdList;
	private CommissionRule ruleObject;
	private int mid;
	private int mealtimes;
	private int shiptype;
	private int sinceid;
	
	
	public int getMealtimes() {
		return mealtimes;
	}
	public void setMealtimes(int mealtimes) {
		this.mealtimes = mealtimes;
	}
	public int getShiptype() {
		return shiptype;
	}
	public void setShiptype(int shiptype) {
		this.shiptype = shiptype;
	}
	public int getSinceid() {
		return sinceid;
	}
	public void setSinceid(int sinceid) {
		this.sinceid = sinceid;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public CommissionRule getRuleObject() {
		return ruleObject;
	}
	public void setRuleObject(CommissionRule ruleObject) {
		this.ruleObject = ruleObject;
	}
	public int getPlotId() {
		return plotId;
	}
	public void setPlotId(int plotId) {
		this.plotId = plotId;
	}
	public String getDeliverTime() {
		return deliverTime;
	}
	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}
	public String getDeliverDate() {
		return deliverDate;
	}
	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}
	public String getBuyerRemark() {
		return buyerRemark;
	}
	public void setBuyerRemark(String buyerRemark) {
		this.buyerRemark = buyerRemark;
	}
	public ArrayList<NewHomeEntity> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(ArrayList<NewHomeEntity> goodsList) {
		this.goodsList = goodsList;
	}
	public int getSaId() {
		return saId;
	}
	public void setSaId(int saId) {
		this.saId = saId;
	}
	public ArrayList<String> getCouponIdList() {
		return couponIdList;
	}
	public void setCouponIdList(ArrayList<String> couponIdList) {
		this.couponIdList = couponIdList;
	}
	
	
}
