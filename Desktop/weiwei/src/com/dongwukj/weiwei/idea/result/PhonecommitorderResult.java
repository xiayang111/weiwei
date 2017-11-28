package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunjaly on 15/4/16.
 */
public class PhonecommitorderResult extends BaseResult implements Serializable{

    private String contentId;
    private String contentName;
    private String orderTime;
    private String sing;
    private Float orderAmount;
    private Float surplusMoney;
    private String paySystemName;
    private String osn;
    private String payKey;
    private String payMid;
    private Integer Oid;
    private float paydiscountamount;
    
    
    public float getPaydiscountamount() {
		return paydiscountamount;
	}

	public void setPaydiscountamount(float paydiscountamount) {
		this.paydiscountamount = paydiscountamount;
	}

	public Integer getOid() {
		return Oid;
	}

	public void setOid(Integer oid) {
		Oid = oid;
	}

	public String getOsn() {
		return osn;
	}

	public void setOsn(String osn) {
		this.osn = osn;
	}

	public String getSing() {
        return sing;
    }

    public void setSing(String sing) {
        this.sing = sing;
    }

    public Float getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Float orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Float getSurplusMoney() {
        return surplusMoney;
    }

    public void setSurplusMoney(Float surplusMoney) {
        this.surplusMoney = surplusMoney;
    }

    public String getPaySystemName() {
        return paySystemName;
    }

    public void setPaySystemName(String paySystemName) {
        this.paySystemName = paySystemName;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public String getPayMid() {
        return payMid;
    }

    public void setPayMid(String payMid) {
        this.payMid = payMid;
    }

   


	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(orderTime);
        if(matcher.find()){
            this.orderTime=matcher.group();
            return;
        }
        this.orderTime = orderTime;
    }
}
