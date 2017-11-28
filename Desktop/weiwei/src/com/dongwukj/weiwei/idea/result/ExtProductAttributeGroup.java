package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/4/2.
 */
public class ExtProductAttributeGroup {
    private Integer attrGroupId;
    private String attrGroupName;
    private ArrayList<ProductAttribute> attributes;

    public Integer getAttrGroupId() {
        return attrGroupId;
    }

    public void setAttrGroupId(Integer attrGroupId) {
        this.attrGroupId = attrGroupId;
    }

    public String getAttrGroupName() {
        return attrGroupName;
    }

    public void setAttrGroupName(String attrGroupName) {
        this.attrGroupName = attrGroupName;
    }

    public ArrayList<ProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<ProductAttribute> attributes) {
        this.attributes = attributes;
    }
}
