package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

/**
 * Created by pc on 2015/3/17.
 */
public class ProductEntity {
    private String productTitle;
    private Float productCurrentPrice;
    private Float productPrePrice;
    private ArrayList<String> productIcons;
    private String productDescription;
    private ArrayList<AppraiseEntity> appraiseEntityList;

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Float getProductCurrentPrice() {
        return productCurrentPrice;
    }

    public void setProductCurrentPrice(Float productCurrentPrice) {
        this.productCurrentPrice = productCurrentPrice;
    }

    public Float getProductPrePrice() {
        return productPrePrice;
    }

    public void setProductPrePrice(Float productPrePrice) {
        this.productPrePrice = productPrePrice;
    }

    public ArrayList<String> getProductIcons() {
        return productIcons;
    }

    public void setProductIcons(ArrayList<String> productIcons) {
        this.productIcons = productIcons;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public ArrayList<AppraiseEntity> getAppraiseEntityList() {
        return appraiseEntityList;
    }

    public void setAppraiseEntityList(ArrayList<AppraiseEntity> appraiseEntityList) {
        this.appraiseEntityList = appraiseEntityList;
    }
}
