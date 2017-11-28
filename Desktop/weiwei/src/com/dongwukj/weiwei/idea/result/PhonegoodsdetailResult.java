package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/4/1.
 */
public class PhonegoodsdetailResult extends BaseResult {
    private BrandEntity brand;
    private ArrayList<String> buySendPromotions;
    private CategoryEntity category;
    private Float discountPrice;
    private ArrayList<String> extGifts;
    private ArrayList<ExtProductAttributeGroup> extProductAttributeGroups;
    private String fullCutPromotion;
    private String fullSendPromotion;
    private String giftPromotion;
    private Integer pId;
    private ProductDetailEntity product;
    private ArrayList<ProductConsultType> productConsultTypes;
    private ArrayList<ProductImage> productImages;
    private ArrayList<String> productSKUs;
    private String promotionMsg;
    private ArrayList<String> relateProducts;
    private SinglePromotion singlePromotion;
    private String slogan;
    private Integer stockNumber;
    private ArrayList<String> suitProducts;
    private ArrayList<ProductDetailEntity> userBrowseHistory;
    private Float shippingCosts;
    private ArrayList<ProductReview> productReviews;
    private Boolean isExistFavoriteProduct;
    
    public Boolean getIsExistFavoriteProduct() {
		return isExistFavoriteProduct;
	}

	public void setIsExistFavoriteProduct(Boolean isExistFavoriteProduct) {
		this.isExistFavoriteProduct = isExistFavoriteProduct;
	}

	public ArrayList<ExtProductAttributeGroup> getExtProductAttributeGroups() {
        return extProductAttributeGroups;
    }

    public void setExtProductAttributeGroups(ArrayList<ExtProductAttributeGroup> extProductAttributeGroups) {
        this.extProductAttributeGroups = extProductAttributeGroups;
    }

    public ArrayList<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(ArrayList<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

    public Float getShippingCosts() {
        return shippingCosts;
    }

    public void setShippingCosts(Float shippingCosts) {
        this.shippingCosts = shippingCosts;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public void setBrand(BrandEntity brand) {
        this.brand = brand;
    }

    public ArrayList<String> getBuySendPromotions() {
        return buySendPromotions;
    }

    public void setBuySendPromotions(ArrayList<String> buySendPromotions) {
        this.buySendPromotions = buySendPromotions;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public Float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public ArrayList<String> getExtGifts() {
        return extGifts;
    }

    public void setExtGifts(ArrayList<String> extGifts) {
        this.extGifts = extGifts;
    }



    public String getFullCutPromotion() {
        return fullCutPromotion;
    }

    public void setFullCutPromotion(String fullCutPromotion) {
        this.fullCutPromotion = fullCutPromotion;
    }

    public String getFullSendPromotion() {
        return fullSendPromotion;
    }

    public void setFullSendPromotion(String fullSendPromotion) {
        this.fullSendPromotion = fullSendPromotion;
    }

    public String getGiftPromotion() {
        return giftPromotion;
    }

    public void setGiftPromotion(String giftPromotion) {
        this.giftPromotion = giftPromotion;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public ProductDetailEntity getProduct() {
        return product;
    }

    public void setProduct(ProductDetailEntity product) {
        this.product = product;
    }

    public ArrayList<ProductConsultType> getProductConsultTypes() {
        return productConsultTypes;
    }

    public void setProductConsultTypes(ArrayList<ProductConsultType> productConsultTypes) {
        this.productConsultTypes = productConsultTypes;
    }

    public ArrayList<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(ArrayList<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public ArrayList<String> getProductSKUs() {
        return productSKUs;
    }

    public void setProductSKUs(ArrayList<String> productSKUs) {
        this.productSKUs = productSKUs;
    }

    public String getPromotionMsg() {
        return promotionMsg;
    }

    public void setPromotionMsg(String promotionMsg) {
        this.promotionMsg = promotionMsg;
    }

    public ArrayList<String> getRelateProducts() {
        return relateProducts;
    }

    public void setRelateProducts(ArrayList<String> relateProducts) {
        this.relateProducts = relateProducts;
    }



    public SinglePromotion getSinglePromotion() {
		return singlePromotion;
	}

	public void setSinglePromotion(SinglePromotion singlePromotion) {
		this.singlePromotion = singlePromotion;
	}

	public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public Integer getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
    }

    public ArrayList<String> getSuitProducts() {
        return suitProducts;
    }

    public void setSuitProducts(ArrayList<String> suitProducts) {
        this.suitProducts = suitProducts;
    }

    public ArrayList<ProductDetailEntity> getUserBrowseHistory() {
        return userBrowseHistory;
    }

    public void setUserBrowseHistory(ArrayList<ProductDetailEntity> userBrowseHistory) {
        this.userBrowseHistory = userBrowseHistory;
    }
}
