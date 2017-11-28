package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/3/24.
 */
public class CategoryEntity {
    private Integer cateId;
    private Integer displayOrder;
    private String name;
    private Integer parentId;
    private String icon;
    private String layer;
    private boolean hasChild;
    private String path;
    private String priceRange;
    private boolean isChecked=false;
    
    public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	private ArrayList<CategoryEntity> childCategorys;

    public ArrayList<CategoryEntity> getChildCategorys() {
        return childCategorys;
    }

    public void setChildCategorys(ArrayList<CategoryEntity> childCategorys) {
        this.childCategorys = childCategorys;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }
}
