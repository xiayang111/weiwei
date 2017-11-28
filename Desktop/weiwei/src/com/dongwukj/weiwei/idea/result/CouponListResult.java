package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class CouponListResult extends BaseResult {

	private ArrayList<CouponListEntity> CouponList;
	

	public ArrayList<CouponListEntity> getCouponList() {
		return CouponList;
	}

	public void setCouponList(ArrayList<CouponListEntity> couponList) {
		CouponList = couponList;
	}

	public CouponListResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CouponListResult(ArrayList<CouponListEntity> couponList) {
		super();
		CouponList = couponList;
	}

	
	
	
	
}
