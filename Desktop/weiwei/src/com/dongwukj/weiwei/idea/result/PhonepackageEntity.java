package com.dongwukj.weiwei.idea.result;

/**
 * Created by sunjaly on 15/3/30.
 */
public class PhonepackageEntity {
    private String name;
    private Integer onlyOnce;
    private Float packPrice;
    private Integer pmId;
    private Integer quotaUpper;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOnlyOnce() {
        return onlyOnce;
    }

    public void setOnlyOnce(Integer onlyOnce) {
        this.onlyOnce = onlyOnce;
    }

    public Float getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(Float packPrice) {
        this.packPrice = packPrice;
    }

    public Integer getPmId() {
        return pmId;
    }

    public void setPmId(Integer pmId) {
        this.pmId = pmId;
    }

    public Integer getQuotaUpper() {
        return quotaUpper;
    }

    public void setQuotaUpper(Integer quotaUpper) {
        this.quotaUpper = quotaUpper;
    }
}
