package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/3/31.
 */
public class CartItemResult extends BaseResult {
    private ArrayList<CartItemEntity> CartItemList;
    private Float FullCut;
    private Float OrderAmount;
    private Float ProductAmount;
    private Integer TotalCount;
    private FullDiscountPromotionConfigInfo fullDiscountPromotionConfigInfo;
    private String ImgUrl;
    private String TaocanImgUrl;
    
    public String getTaocanImgUrl() {
		return TaocanImgUrl;
	}

	public void setTaocanImgUrl(String taocanImgUrl) {
		TaocanImgUrl = taocanImgUrl;
	}

	public String getImgUrl() {
		return ImgUrl;
	}

	public void setImgUrl(String imgUrl) {
		ImgUrl = imgUrl;
	}

	public ArrayList<CartItemEntity> getCartItemList() {
        return CartItemList;
    }

    public void setCartItemList(ArrayList<CartItemEntity> cartItemList) {
        CartItemList = cartItemList;
    }
    
    public FullDiscountPromotionConfigInfo getFullDiscountPromotionConfigInfo() {
		return fullDiscountPromotionConfigInfo;
	}

	public void setFullDiscountPromotionConfigInfo(
			FullDiscountPromotionConfigInfo fullDiscountPromotionConfigInfo) {
		this.fullDiscountPromotionConfigInfo = fullDiscountPromotionConfigInfo;
	}

	public Float getFullCut() {
        return FullCut;
    }

    public void setFullCut(Float fullCut) {
        FullCut = fullCut;
    }

    public Float getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(Float orderAmount) {
        OrderAmount = orderAmount;
    }

    public Float getProductAmount() {
        return ProductAmount;
    }

    public void setProductAmount(Float productAmount) {
        ProductAmount = productAmount;
    }

    public Integer getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(Integer totalCount) {
        TotalCount = totalCount;
    }
}
