package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/4/2.
 */
public class ProductAttribute {
    private Integer attrId;
    private String attrName;
    private ArrayList<ProductAttributeValue> attributeValues;

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public ArrayList<ProductAttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(ArrayList<ProductAttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
