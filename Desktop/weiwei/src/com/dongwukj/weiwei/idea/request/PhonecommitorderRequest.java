package com.dongwukj.weiwei.idea.request;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/4/16.
 */
public class PhonecommitorderRequest extends BaseRequest {

    private String ip;
    private String selectedCartItemKeyList;
    private int saId;
    private String payName;
    private int payCreditCount;
    private ArrayList<String> couponIdList;
    private String buyerRemark;
    private String bestTime;
    private String DeliveryEndTime;
    
    public String getDeliveryEndTime() {
		return DeliveryEndTime;
	}

	public void setDeliveryEndTime(String deliveryEndTime) {
		DeliveryEndTime = deliveryEndTime;
	}

	public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSelectedCartItemKeyList() {
        return selectedCartItemKeyList;
    }

    public void setSelectedCartItemKeyList(String selectedCartItemKeyList) {
        this.selectedCartItemKeyList = selectedCartItemKeyList;
    }

    public int getSaId() {
        return saId;
    }

    public void setSaId(int saId) {
        this.saId = saId;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public int getPayCreditCount() {
        return payCreditCount;
    }

    public void setPayCreditCount(int payCreditCount) {
        this.payCreditCount = payCreditCount;
    }

   

    public ArrayList<String> getCouponIdList() {
		return couponIdList;
	}

	public void setCouponIdList(ArrayList<String> couponIdList) {
		this.couponIdList = couponIdList;
	}

	public String getBuyerRemark() {
        return buyerRemark;
    }

    public void setBuyerRemark(String buyerRemark) {
        this.buyerRemark = buyerRemark;
    }

    public String getBestTime() {
        return bestTime;
    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }
}
