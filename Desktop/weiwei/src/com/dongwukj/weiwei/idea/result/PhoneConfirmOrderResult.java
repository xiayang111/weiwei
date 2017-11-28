package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

/**
 * Created by sunjaly on 15/4/15.
 */
@Table("phoneConfirmOrderResult")
public class PhoneConfirmOrderResult extends BaseResult{
	@PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    private long id;
    private String defaultPayPluginInfo;
    private Float fullCut;
    private Integer maxUsePayCredits;
    private Float orderAmount;
    private Float payCreditName;
    private Float UserPayMoney;
    @Mapping(Mapping.Relation.OneToMany)
    private ArrayList<PaymentEntity> payPluginList;
    private Float productAmount;
    private Float shipFee;
    private Integer totalCount;
    private Integer userPayCredits;
    @Mapping(Mapping.Relation.OneToMany)
    private ArrayList<ValidCouponEntity> validCouponList;
    @Mapping(Mapping.Relation.OneToMany)
    private ArrayList<TimeConfigLis> deliveryTimeConfigList;
    

    
   
	public ArrayList<TimeConfigLis> getDeliveryTimeConfigList() {
		return deliveryTimeConfigList;
	}

	public void setDeliveryTimeConfigList(
			ArrayList<TimeConfigLis> deliveryTimeConfigList) {
		this.deliveryTimeConfigList = deliveryTimeConfigList;
	}

	public Float getUserPayMoney() {
		return UserPayMoney;
	}

	public void setUserPayMoney(Float userPayMoney) {
		UserPayMoney = userPayMoney;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDefaultPayPluginInfo() {
        return defaultPayPluginInfo;
    }

    public void setDefaultPayPluginInfo(String defaultPayPluginInfo) {
        this.defaultPayPluginInfo = defaultPayPluginInfo;
    }

    public Float getFullCut() {
        return fullCut;
    }

    public void setFullCut(Float fullCut) {
        this.fullCut = fullCut;
    }

    public Integer getMaxUsePayCredits() {
        return maxUsePayCredits;
    }

    public void setMaxUsePayCredits(Integer maxUsePayCredits) {
        this.maxUsePayCredits = maxUsePayCredits;
    }

    public Float getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Float orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Float getPayCreditName() {
        return payCreditName;
    }

    public void setPayCreditName(Float payCreditName) {
        this.payCreditName = payCreditName;
    }

    public ArrayList<PaymentEntity> getPayPluginList() {
        return payPluginList;
    }

    public void setPayPluginList(ArrayList<PaymentEntity> payPluginList) {
        this.payPluginList = payPluginList;
    }

    public Float getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Float productAmount) {
        this.productAmount = productAmount;
    }

    public Float getShipFee() {
        return shipFee;
    }

    public void setShipFee(Float shipFee) {
        this.shipFee = shipFee;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getUserPayCredits() {
        return userPayCredits;
    }

    public void setUserPayCredits(Integer userPayCredits) {
        this.userPayCredits = userPayCredits;
    }

    public ArrayList<ValidCouponEntity> getValidCouponList() {
        return validCouponList;
    }

    public void setValidCouponList(ArrayList<ValidCouponEntity> validCouponList) {
        this.validCouponList = validCouponList;
    }
}
