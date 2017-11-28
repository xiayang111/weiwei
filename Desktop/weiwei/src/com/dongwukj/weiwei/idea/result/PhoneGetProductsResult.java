package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/3/30.
 */
public class PhoneGetProductsResult extends BaseResult {
    private Integer listNumber;
    private ArrayList<ProductInfoEntity> products;

    public Integer getListNumber() {
        return listNumber;
    }

    public void setListNumber(Integer listNumber) {
        this.listNumber = listNumber;
    }

    public ArrayList<ProductInfoEntity> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductInfoEntity> products) {
        this.products = products;
    }

	@Override
	public String toString() {
		return "PhoneGetProductsResult [listNumber=" + listNumber
				+ ", products=" + products + "]";
	}
    
}
