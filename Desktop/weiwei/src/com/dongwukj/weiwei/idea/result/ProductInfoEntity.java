package com.dongwukj.weiwei.idea.result;

/**
 * Created by sunjaly on 15/3/30.
 */
public class ProductInfoEntity {
    private Integer brandId;
    private Integer cateId;
    private Integer displayOrder;
    private Boolean isBest;
    private Boolean isHot;
    private Boolean isNew;
    private Float marketPrice;
    private String name;
    private Integer pId;
    private Integer reviewCount;
    private Integer saleCount;
    private Float shopPrice;
    private String showImg;
    private Integer visitCount;
    private Integer weight;
    private Float dicountPrice;
    
    public Float getDicountPrice() {
		return dicountPrice;
	}

	public void setDicountPrice(Float dicountPrice) {
		this.dicountPrice = dicountPrice;
	}

	public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
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

    public Boolean getIsBest() {
        return isBest;
    }

    public void setIsBest(Boolean isBest) {
        this.isBest = isBest;
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Float getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(Float shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

	@Override
	public String toString() {
		return "ProductInfoEntity [brandId=" + brandId + ", cateId=" + cateId
				+ ", displayOrder=" + displayOrder + ", isBest=" + isBest
				+ ", isHot=" + isHot + ", isNew=" + isNew + ", marketPrice="
				+ marketPrice + ", name=" + name + ", pId=" + pId
				+ ", reviewCount=" + reviewCount + ", saleCount=" + saleCount
				+ ", shopPrice=" + shopPrice + ", showImg=" + showImg
				+ ", visitCount=" + visitCount + ", weight=" + weight + "]";
	}
    
}
