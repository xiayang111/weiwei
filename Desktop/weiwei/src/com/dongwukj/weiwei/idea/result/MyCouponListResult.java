package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class MyCouponListResult extends BaseResult {

	private ArrayList<CouponTypeEntity> CouponList ;

	public ArrayList<CouponTypeEntity> getCouponList() {
		return CouponList;
	}

	public void setCouponList(ArrayList<CouponTypeEntity> couponList) {
		CouponList = couponList;
	}

	public MyCouponListResult(ArrayList<CouponTypeEntity> couponList) {
		super();
		CouponList = couponList;
	}

	public MyCouponListResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
}
