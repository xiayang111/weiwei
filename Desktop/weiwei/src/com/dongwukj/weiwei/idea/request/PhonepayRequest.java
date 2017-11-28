package com.dongwukj.weiwei.idea.request;

import com.dongwukj.weiwei.idea.result.BaseResult;

/**
 * Created by sunjaly on 15/4/18.
 */
public class PhonepayRequest extends BaseRequest{

    private String paymentPassword;
    private int logType;
    private String orderId;
    private int isoffline;
    
    public int getIsoffline() {
		return isoffline;
	}

	public void setIsoffline(int isoffline) {
		this.isoffline = isoffline;
	}

	public String getPaymentPassword() {
        return paymentPassword;
    }

    public void setPaymentPassword(String paymentPassword) {
        this.paymentPassword = paymentPassword;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
