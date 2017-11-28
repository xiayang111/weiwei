package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/3/31.
 */
public class PhonepackagedetailResult extends BaseResult {
    private ArrayList<ProductInfoEntity> products;

    public ArrayList<ProductInfoEntity> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductInfoEntity> products) {
        this.products = products;
    }
}
