package com.dongwukj.weiwei.idea.result;

/**
 * Created by sunjaly on 15/4/1.
 */
public class ProductImage {
    private Integer displayOrder;
    private Boolean isMain;
    private Integer pId;
    private Integer pimgId;
    private String showImg;

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getIsMain() {
        return isMain;
    }

    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getPimgId() {
        return pimgId;
    }

    public void setPimgId(Integer pimgId) {
        this.pimgId = pimgId;
    }

    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg;
    }
}
