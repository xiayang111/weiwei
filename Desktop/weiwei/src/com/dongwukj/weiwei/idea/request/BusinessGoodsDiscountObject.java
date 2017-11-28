package com.dongwukj.weiwei.idea.request;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

@Table("businessGoodsDiscountObject")
public class BusinessGoodsDiscountObject {
    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
	private long id;
    private float discount;
    		private float discountprice;
    		private int discountType;
    		private float payType;
			public float getDiscount() {
				return discount;
			}
			public void setDiscount(float discount) {
				this.discount = discount;
			}
			public float getDiscountprice() {
				return discountprice;
			}
			public void setDiscountprice(float discountprice) {
				this.discountprice = discountprice;
			}
			public int getDiscountType() {
				return discountType;
			}
			public void setDiscountType(int discountType) {
				this.discountType = discountType;
			}
			public float getPayType() {
				return payType;
			}
			public void setPayType(float payType) {
				this.payType = payType;
			}
    		
}
