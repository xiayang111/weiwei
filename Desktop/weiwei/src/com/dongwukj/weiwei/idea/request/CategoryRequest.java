package com.dongwukj.weiwei.idea.request;

/**
 * Created by sunjaly on 15/3/24.
 */
public class CategoryRequest extends BaseRequest {
    private Integer categoryType;
    private Integer categoryId;

    public CategoryRequest(Integer categoryType, Integer categoryId) {
        this.categoryType = categoryType;
        this.categoryId = categoryId;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
