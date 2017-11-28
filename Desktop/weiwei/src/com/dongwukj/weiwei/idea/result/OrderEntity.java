package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by pc on 2015/3/17.
 */
public class OrderEntity {
    private String orderNum;
    private String sender;
    private String senderPhone;
    private Date orderTime;
    private Date sendTime;
    private String sendAddress;
    private Integer orderState;
    private Float orderPrice;
    private Float orderPay;
    private Float expenses;
    private Integer orderAmount;
    private Integer orderCount;
    private String senderCar;
    private String orderTitle;

    public Float getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Float orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    private ArrayList<ProductEntity> productEntityList;


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Float getOrderPay() {
        return orderPay;
    }

    public void setOrderPay(Float orderPay) {
        this.orderPay = orderPay;
    }

    public Float getExpenses() {
        return expenses;
    }

    public void setExpenses(Float expenses) {
        this.expenses = expenses;
    }

    public String getSenderCar() {
        return senderCar;
    }

    public void setSenderCar(String senderCar) {
        this.senderCar = senderCar;
    }

    public ArrayList<ProductEntity> getProductEntityList() {
        return productEntityList;
    }

    public void setProductEntityList(ArrayList<ProductEntity> productEntityList) {
        this.productEntityList = productEntityList;
    }
}
