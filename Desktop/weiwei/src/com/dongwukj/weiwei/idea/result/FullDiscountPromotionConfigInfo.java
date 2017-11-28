package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullDiscountPromotionConfigInfo {
	private float Discount;
	private String EndTime;
	private String name;
	private ArrayList<Integer> FromCateIdList;
	private ArrayList<Integer> TargetCateIdList;
	private int PmId;
	private String StartTime;
	private int State;
	private float TargetProducMoneyLower;
	private int UserRankLower;
	public float getDiscount() {
		return Discount;
	}
	public void setDiscount(float discount) {
		Discount = discount;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(endTime);
        if(matcher.find()){
            this.EndTime=matcher.group();
            return;
        }
        this.EndTime = endTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Integer> getFromCateIdList() {
		return FromCateIdList;
	}
	public void setFromCateIdList(ArrayList<Integer> fromCateIdList) {
		FromCateIdList = fromCateIdList;
	}
	public ArrayList<Integer> getTargetCateIdList() {
		return TargetCateIdList;
	}
	public void setTargetCateIdList(ArrayList<Integer> targetCateIdList) {
		TargetCateIdList = targetCateIdList;
	}
	public int getPmId() {
		return PmId;
	}
	public void setPmId(int pmId) {
		PmId = pmId;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(startTime);
        if(matcher.find()){
            this.StartTime=matcher.group();
            return;
        }
        this.StartTime = startTime;
	}
	public int getState() {
		return State;
	}
	public void setState(int state) {
		State = state;
	}
	public float getTargetProducMoneyLower() {
		return TargetProducMoneyLower;
	}
	public void setTargetProducMoneyLower(float targetProducMoneyLower) {
		TargetProducMoneyLower = targetProducMoneyLower;
	}
	public int getUserRankLower() {
		return UserRankLower;
	}
	public void setUserRankLower(int userRankLower) {
		UserRankLower = userRankLower;
	}
	
}
