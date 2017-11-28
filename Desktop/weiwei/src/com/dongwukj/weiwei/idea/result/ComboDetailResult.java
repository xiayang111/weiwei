package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class ComboDetailResult extends BaseResult {
	private ArrayList<ComboDetailEntity> products;

	public ArrayList<ComboDetailEntity> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<ComboDetailEntity> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "ComboDetailResult [products=" + products + "]";
	}
	

	public static class ComboDetailEntity{

		private int pId;
		private int cateId;
		private int brandId;
		private String name;
		private float shopPrice;
		private float marketPrice;
		private String displayOrder;
		private int isBest;
		private int isHot;
		private int isNew;
		private String weight;
		private String showImg;
		private int saleCount;
		private int visitCount;
		private int reviewCount;
		private int number;
		
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public int getpId() {
			return pId;
		}
		public void setpId(int pId) {
			this.pId = pId;
		}
		public int getCateId() {
			return cateId;
		}
		public void setCateId(int cateId) {
			this.cateId = cateId;
		}
		public int getBrandId() {
			return brandId;
		}
		public void setBrandId(int brandId) {
			this.brandId = brandId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public float getShopPrice() {
			return shopPrice;
		}
		public void setShopPrice(float shopPrice) {
			this.shopPrice = shopPrice;
		}
		public float getMarketPrice() {
			return marketPrice;
		}
		public void setMarketPrice(float marketPrice) {
			this.marketPrice = marketPrice;
		}
		public String getDisplayOrder() {
			return displayOrder;
		}
		public void setDisplayOrder(String displayOrder) {
			this.displayOrder = displayOrder;
		}
		public int getIsBest() {
			return isBest;
		}
		public void setIsBest(int isBest) {
			this.isBest = isBest;
		}
		public int getIsHot() {
			return isHot;
		}
		public void setIsHot(int isHot) {
			this.isHot = isHot;
		}
		public int getIsNew() {
			return isNew;
		}
		public void setIsNew(int isNew) {
			this.isNew = isNew;
		}
		public String getWeight() {
			return weight;
		}
		public void setWeight(String weight) {
			this.weight = weight;
		}
		public String getShowImg() {
			return showImg;
		}
		public void setShowImg(String showImg) {
			this.showImg = showImg;
		}
		public int getSaleCount() {
			return saleCount;
		}
		public void setSaleCount(int saleCount) {
			this.saleCount = saleCount;
		}
		public int getVisitCount() {
			return visitCount;
		}
		public void setVisitCount(int visitCount) {
			this.visitCount = visitCount;
		}
		public int getReviewCount() {
			return reviewCount;
		}
		public void setReviewCount(int reviewCount) {
			this.reviewCount = reviewCount;
		}
		@Override
		public String toString() {
			return "ComboDetailEntity [pId=" + pId + ", cateId=" + cateId
					+ ", brandId=" + brandId + ", name=" + name
					+ ", shopPrice=" + shopPrice + ", marketPrice="
					+ marketPrice + ", displayOrder=" + displayOrder
					+ ", isBest=" + isBest + ", isHot=" + isHot + ", isNew="
					+ isNew + ", weight=" + weight + ", showImg=" + showImg
					+ ", saleCount=" + saleCount + ", visitCount=" + visitCount
					+ ", reviewCount=" + reviewCount + "]";
		}

	}


}
