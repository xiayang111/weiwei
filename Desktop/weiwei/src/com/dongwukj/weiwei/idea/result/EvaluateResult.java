package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class EvaluateResult extends BaseResult {
	private ArrayList<EvaluateEntity> productReviews;
	private int listNumber;
	public int getListNumber() {
		return listNumber;
	}
	public void setListNumber(int listNumber) {
		this.listNumber = listNumber;
	}
	public ArrayList<EvaluateEntity> getProductReviews() {
		return productReviews;
	}

	public void setProductReviews(ArrayList<EvaluateEntity> productReviews) {
		this.productReviews = productReviews;
	}
	
	
	@Override
	public String toString() {
		return "EvaluateResult [productReviews=" + productReviews
				+ ", listNumber=" + listNumber + "]";
	}


	public static class EvaluateEntity{
		private int reviewId;
		private int pId;
		private int uId;
		private int oprId;
		private int oId;
		private int parentId;
		private int state;
		private float star;
		private String quality;
		private String message;
		private String reviewTime;
		private String nickName;
		private String avatar;
		private String buyTime;
		private String title;
		
		public int getReviewId() {
			return reviewId;
		}
		public void setReviewId(int reviewId) {
			this.reviewId = reviewId;
		}
		public int getpId() {
			return pId;
		}
		public void setpId(int pId) {
			this.pId = pId;
		}
		public int getuId() {
			return uId;
		}
		public void setuId(int uId) {
			this.uId = uId;
		}
		public int getOprId() {
			return oprId;
		}
		public void setOprId(int oprId) {
			this.oprId = oprId;
		}
		public int getoId() {
			return oId;
		}
		public void setoId(int oId) {
			this.oId = oId;
		}
		public int getParentId() {
			return parentId;
		}
		public void setParentId(int parentId) {
			this.parentId = parentId;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		
		public float getStar() {
			return star;
		}
		public void setStar(float star) {
			this.star = star;
		}
		public String getQuality() {
			return quality;
		}
		public void setQuality(String quality) {
			this.quality = quality;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getReviewTime() {
			return reviewTime;
		}
		public void setReviewTime(String reviewTime) {
			this.reviewTime = reviewTime;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public String getBuyTime() {
			return buyTime;
		}
		public void setBuyTime(String buyTime) {
			this.buyTime = buyTime;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		@Override
		public String toString() {
			return "EvaluateEntity [reviewId=" + reviewId + ", pId=" + pId
					+ ", uId=" + uId + ", oprId=" + oprId + ", oId=" + oId
					+ ", parentId=" + parentId + ", state=" + state + ", star="
					+ star + ", quality=" + quality + ", message=" + message
					+ ", reviewTime=" + reviewTime + ", nickName=" + nickName
					+ ", avatar=" + avatar + ", buyTime=" + buyTime
					+ ", title=" + title + ", getReviewId()=" + getReviewId()
					+ ", getpId()=" + getpId() + ", getuId()=" + getuId()
					+ ", getOprId()=" + getOprId() + ", getoId()=" + getoId()
					+ ", getParentId()=" + getParentId() + ", getState()="
					+ getState() + ", getStar()=" + getStar()
					+ ", getQuality()=" + getQuality() + ", getMessage()="
					+ getMessage() + ", getReviewTime()=" + getReviewTime()
					+ ", getNickName()=" + getNickName() + ", getAvatar()="
					+ getAvatar() + ", getBuyTime()=" + getBuyTime()
					+ ", getTitle()=" + getTitle() + ", getClass()="
					+ getClass() + ", hashCode()=" + hashCode()
					+ ", toString()=" + super.toString() + "]";
		}
		
		
		
	}
}
