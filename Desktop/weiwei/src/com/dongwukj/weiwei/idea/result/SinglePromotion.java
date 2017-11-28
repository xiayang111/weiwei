package com.dongwukj.weiwei.idea.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
@Table("SinglePromotion")
public class SinglePromotion {
	@PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
	private long id;
	private int pmId;	//促销活动id
	private int pId;//商品id
	private String startTime1; 	//开始时间private1
	private String endTime1; 	//结束时间private1
	private int userRankLower;	//用户等级下限
	private int state;	//状态
	private String name;	//活动名称
	private String sLogan;	//活动广告语
	private int discountType;	//折扣类型,0代表折扣，private int 代表直降，2代表折后价
	private int discountValue;	//折扣值
	private int couponTypeId;	//优惠劵类型id
	private float payCredits;	//支付积分
	private int isStock;	//是否限制库存
	private int stock;	//库存
	private String quotaLower;	//订单配额上限
	private String quotaUpper;	//订单配额下限
	private int allowBuyCount;	//最大购买数量
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getPmId() {
		return pmId;
	}
	public void setPmId(int pmId) {
		this.pmId = pmId;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public String getStartTime1() {
		return startTime1;
	}
	public void setStartTime1(String startTime1) {
		 Pattern pattern = Pattern.compile("\\d+");
	        Matcher matcher = pattern.matcher(startTime1);
	        if(matcher.find()){
	            this.startTime1=matcher.group();
	            return;
	        }
	        this.startTime1 = startTime1;
	}
	public String getEndTime1() {
		return endTime1;
	}
	public void setEndTime1(String endTime1) {
		 Pattern pattern = Pattern.compile("\\d+");
	        Matcher matcher = pattern.matcher(endTime1);
	        if(matcher.find()){
	            this.endTime1=matcher.group();
	            return;
	        }
	        this.endTime1 = endTime1;
	}
	public int getUserRankLower() {
		return userRankLower;
	}
	public void setUserRankLower(int userRankLower) {
		this.userRankLower = userRankLower;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getsLogan() {
		return sLogan;
	}
	public void setsLogan(String sLogan) {
		this.sLogan = sLogan;
	}
	public int getDiscountType() {
		return discountType;
	}
	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}
	public int getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(int discountValue) {
		this.discountValue = discountValue;
	}
	public int getCouponTypeId() {
		return couponTypeId;
	}
	public void setCouponTypeId(int couponTypeId) {
		this.couponTypeId = couponTypeId;
	}
	public float getPayCredits() {
		return payCredits;
	}
	public void setPayCredits(float payCredits) {
		this.payCredits = payCredits;
	}
	public int getIsStock() {
		return isStock;
	}
	public void setIsStock(int isStock) {
		this.isStock = isStock;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getQuotaLower() {
		return quotaLower;
	}
	public void setQuotaLower(String quotaLower) {
		this.quotaLower = quotaLower;
	}
	public String getQuotaUpper() {
		return quotaUpper;
	}
	public void setQuotaUpper(String quotaUpper) {
		this.quotaUpper = quotaUpper;
	}
	public int getAllowBuyCount() {
		return allowBuyCount;
	}
	public void setAllowBuyCount(int allowBuyCount) {
		this.allowBuyCount = allowBuyCount;
	}
	

}
