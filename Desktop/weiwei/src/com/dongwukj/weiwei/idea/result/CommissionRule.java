package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;

public class CommissionRule implements Serializable{
	private int endamount;
	private int startamount;
	private int fullcut;
	private String ruleStr;
	private String coupontypeid;
	private String couponuse;
	private String id;
	private String isvalid;
	private String mid;
	private String name;
	private String ordercommission;
	private String sendcouponnum;
	private String shipcommission;
	private String shipfee;
	private String validendtime;
		private String validstarttime;
		
	public String getCoupontypeid() {
			return coupontypeid;
		}
		public void setCoupontypeid(String coupontypeid) {
			this.coupontypeid = coupontypeid;
		}
		public String getCouponuse() {
			return couponuse;
		}
		public void setCouponuse(String couponuse) {
			this.couponuse = couponuse;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getIsvalid() {
			return isvalid;
		}
		public void setIsvalid(String isvalid) {
			this.isvalid = isvalid;
		}
		public String getMid() {
			return mid;
		}
		public void setMid(String mid) {
			this.mid = mid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getOrdercommission() {
			return ordercommission;
		}
		public void setOrdercommission(String ordercommission) {
			this.ordercommission = ordercommission;
		}
		public String getSendcouponnum() {
			return sendcouponnum;
		}
		public void setSendcouponnum(String sendcouponnum) {
			this.sendcouponnum = sendcouponnum;
		}
		public String getShipcommission() {
			return shipcommission;
		}
		public void setShipcommission(String shipcommission) {
			this.shipcommission = shipcommission;
		}
		public String getShipfee() {
			return shipfee;
		}
		public void setShipfee(String shipfee) {
			this.shipfee = shipfee;
		}
		public String getValidendtime() {
			return validendtime;
		}
		public void setValidendtime(String validendtime) {
			this.validendtime = validendtime;
		}
		public String getValidstarttime() {
			return validstarttime;
		}
		public void setValidstarttime(String validstarttime) {
			this.validstarttime = validstarttime;
		}
	public int getEndamount() {
		return endamount;
	}
	public void setEndamount(int endamount) {
		this.endamount = endamount;
	}
	public int getStartamount() {
		return startamount;
	}
	public void setStartamount(int startamount) {
		this.startamount = startamount;
	}
	public int getFullcut() {
		return fullcut;
	}
	public void setFullcut(int fullcut) {
		this.fullcut = fullcut;
	}
	public String getRuleStr() {
		return ruleStr;
	}
	public void setRuleStr(String ruleStr) {
		this.ruleStr = ruleStr;
	}
	
}
