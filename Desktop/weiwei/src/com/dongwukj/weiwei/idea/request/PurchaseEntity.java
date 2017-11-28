package com.dongwukj.weiwei.idea.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PurchaseEntity {
	private Integer AllowBuyCount;
	private Integer DiscountType;
	private Integer DiscountValue;
	private String EndTime1;
	private Integer IsStock;
	private String Name;
	private Integer Pid;
	private Integer PmId;
	private String Pname;
	private float Pshopprice;
	private float Discountprice;
	private String Pstate ;//活动广告语
	private String Slogan ;//活动广告语
	private String StartTime1 ;
	private String Stock ;//库存
	private Integer UserRankLower ;//用户等级下限
	private String Pshowimg;
	
	public float getDiscountprice() {
		return Discountprice;
	}
	public void setDiscountprice(float discountprice) {
		Discountprice = discountprice;
	}
	public Integer getAllowBuyCount() {
		return AllowBuyCount;
	}
	public void setAllowBuyCount(Integer allowBuyCount) {
		AllowBuyCount = allowBuyCount;
	}
	public Integer getDiscountType() {
		return DiscountType;
	}
	public void setDiscountType(Integer discountType) {
		DiscountType = discountType;
	}
	public Integer getDiscountValue() {
		return DiscountValue;
	}
	public void setDiscountValue(Integer discountValue) {
		DiscountValue = discountValue;
	}
	
	public Integer getIsStock() {
		return IsStock;
	}
	public void setIsStock(Integer isStock) {
		IsStock = isStock;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Integer getPid() {
		return Pid;
	}
	public void setPid(Integer pid) {
		Pid = pid;
	}
	public Integer getPmId() {
		return PmId;
	}
	public void setPmId(Integer pmId) {
		PmId = pmId;
	}
	public String getPname() {
		return Pname;
	}
	public void setPname(String pname) {
		Pname = pname;
	}
	public float getPshopprice() {
		return Pshopprice;
	}
	public void setPshopprice(float pshopprice) {
		Pshopprice = pshopprice;
	}
	public String getPstate() {
		return Pstate;
	}
	public void setPstate(String pstate) {
		Pstate = pstate;
	}
	public String getSlogan() {
		return Slogan;
	}
	public void setSlogan(String slogan) {
		Slogan = slogan;
	}
	
	public String getEndTime1() {
		return EndTime1;
	}
	public void setEndTime1(String endTime1) {
		 Pattern pattern = Pattern.compile("\\d+");
	        Matcher matcher = pattern.matcher(endTime1);
	        if(matcher.find()){
	            this.EndTime1=matcher.group();
	            return;
	        }
	        this.EndTime1 = endTime1;
	}
	public String getStartTime1() {
		return StartTime1;
	}
	public void setStartTime1(String startTime1) {
		 Pattern pattern = Pattern.compile("\\d+");
	        Matcher matcher = pattern.matcher(startTime1);
	        if(matcher.find()){
	            this.StartTime1=matcher.group();
	            return;
	        }
	        this.StartTime1 = startTime1;
	}
	public String getStock() {
		return Stock;
	}
	public void setStock(String stock) {
		Stock = stock;
	}
	public Integer getUserRankLower() {
		return UserRankLower;
	}
	public void setUserRankLower(Integer userRankLower) {
		UserRankLower = userRankLower;
	}
	public String getPshowimg() {
		return Pshowimg;
	}
	public void setPshowimg(String pshowimg) {
		Pshowimg = pshowimg;
	}
	@Override
	public String toString() {
		return "PurchaseEntity [AllowBuyCount=" + AllowBuyCount
				+ ", DiscountType=" + DiscountType + ", DiscountValue="
				+ DiscountValue + ", EndTime1=" + EndTime1 + ", IsStock="
				+ IsStock + ", Name=" + Name + ", Pid=" + Pid + ", PmId="
				+ PmId + ", Pname=" + Pname + ", Pshopprice=" + Pshopprice
				+ ", Pstate=" + Pstate + ", Slogan=" + Slogan + ", StartTime1="
				+ StartTime1 + ", Stock=" + Stock + ", UserRankLower="
				+ UserRankLower + ", Pshowimg=" + Pshowimg + "]";
	}
	
	
}
