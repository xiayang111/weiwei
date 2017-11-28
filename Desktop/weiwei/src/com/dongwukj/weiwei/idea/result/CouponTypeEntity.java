package com.dongwukj.weiwei.idea.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CouponTypeEntity {

	
	private CouponListEntity couponType ;
	
//	private int CouponTypeId;
//	private String Name;
//	private float Money;		  //优惠券面值
//	private int Count;			  //优惠券数量
//	private int UseMode;		  //使用方式(0代表可以叠加,1代表不可以叠加)
//	private int UserRankLower;    //最低用户级别
//	private int OrderAmountLower; //订单金额下限
//	private int LimitCateId;      //限制分类id
//	private String LimitCateName;    //限制分类名称
//	private int LimitBrandId; 	  //限制品牌id
//	private String LimitBrandName;   //限制品牌名称
//	private int LimitProduct;  	  //限制商品
//	private String LimitProductName; //限制商品名称
//	private String SendStartTime; 	  //发放开始时间
//	private String SendEndTime;  	  //发放结束时间
//	private String UseExpireTime; 	  //使用过期时间
//	private String UseStartTime; 	  //使用开始时间
//	private String UseEndTime;   	  //使用结束时间
	private boolean ischecked=false;
	private int Couponid;  //优惠劵编号
	private Long Couponsn;  //优惠劵编号
	
	

public boolean isIschecked() {
		return ischecked;
	}
	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}
	//	public int getCouponTypeId() {
//		return CouponTypeId;
//	}
//	public void setCouponTypeId(int couponTypeId) {
//		CouponTypeId = couponTypeId;
//	}
//	public String getName() {
//		return Name;
//	}
//	public void setName(String name) {
//		Name = name;
//	}
//	public float getMoney() {
//		return Money;
//	}
//	public void setMoney(float money) {
//		Money = money;
//	}
//	public int getCount() {
//		return Count;
//	}
//	public void setCount(int count) {
//		Count = count;
//	}
//	public int getUseMode() {
//		return UseMode;
//	}
//	public void setUseMode(int useMode) {
//		UseMode = useMode;
//	}
//	public int getUserRankLower() {
//		return UserRankLower;
//	}
//	public void setUserRankLower(int userRankLower) {
//		UserRankLower = userRankLower;
//	}
//	public int getOrderAmountLower() {
//		return OrderAmountLower;
//	}
//	public void setOrderAmountLower(int orderAmountLower) {
//		OrderAmountLower = orderAmountLower;
//	}
//	public int getLimitCateId() {
//		return LimitCateId;
//	}
//	public void setLimitCateId(int limitCateId) {
//		LimitCateId = limitCateId;
//	}
//	public String getLimitCateName() {
//		return LimitCateName;
//	}
//	public void setLimitCateName(String limitCateName) {
//		LimitCateName = limitCateName;
//	}
//	public int getLimitBrandId() {
//		return LimitBrandId;
//	}
//	public void setLimitBrandId(int limitBrandId) {
//		LimitBrandId = limitBrandId;
//	}
//	public String getLimitBrandName() {
//		return LimitBrandName;
//	}
//	public void setLimitBrandName(String limitBrandName) {
//		LimitBrandName = limitBrandName;
//	}
//	public int getLimitProduct() {
//		return LimitProduct;
//	}
//	public void setLimitProduct(int limitProduct) {
//		LimitProduct = limitProduct;
//	}
//	public String getLimitProductName() {
//		return LimitProductName;
//	}
//	public void setLimitProductName(String limitProductName) {
//		LimitProductName = limitProductName;
//	}
//	public String getSendStartTime() {
//		return SendStartTime;
//	}
//	public void setSendStartTime(String sendStartTime) {
//		Pattern pattern = Pattern.compile("\\d+");
//        Matcher matcher = pattern.matcher(sendStartTime);
//        if(matcher.find()){
//            this.SendStartTime=matcher.group();
//            return;
//        }
//        this.SendStartTime = sendStartTime;
//	}
//	public String getSendEndTime() {
//		return SendEndTime;
//	}
//	public void setSendEndTime(String sendEndTime) {
//		Pattern pattern = Pattern.compile("\\d+");
//        Matcher matcher = pattern.matcher(sendEndTime);
//        if(matcher.find()){
//            this.SendEndTime=matcher.group();
//            return;
//        }
//        this.SendEndTime = sendEndTime;
//	}
//	public String getUseExpireTime() {
//		return UseExpireTime;
//	}
//	public void setUseExpireTime(String useExpireTime) {
//		Pattern pattern = Pattern.compile("\\d+");
//        Matcher matcher = pattern.matcher(useExpireTime);
//        if(matcher.find()){
//            this.UseExpireTime=matcher.group();
//            return;
//        }
//        this.UseExpireTime = useExpireTime;
//	}
//	public String getUseStartTime() {
//		return UseStartTime;
//	}
//	public void setUseStartTime(String useStartTime) {
//		Pattern pattern = Pattern.compile("\\d+");
//        Matcher matcher = pattern.matcher(useStartTime);
//        if(matcher.find()){
//            this.UseStartTime=matcher.group();
//            return;
//        }
//        this.UseStartTime = useStartTime;
//	}
//	public String getUseEndTime() {
//		return UseEndTime;
//	}
//	public void setUseEndTime(String useEndTime) {
//		Pattern pattern = Pattern.compile("\\d+");
//        Matcher matcher = pattern.matcher(useEndTime);
//        if(matcher.find()){
//            this.UseEndTime=matcher.group();
//            return;
//        }
//        this.UseEndTime = useEndTime;
//	}
	public int getCouponid() {
		return Couponid;
	}
	public void setCouponid(int couponid) {
		Couponid = couponid;
	}
	public Long getCouponsn() {
		return Couponsn;
	}
	public void setCouponsn(Long couponsn) {
		Couponsn = couponsn;
	}
	
	
	 
	public CouponListEntity getCouponType() {
		return couponType;
	}
	public void setCouponType(CouponListEntity couponType) {
		this.couponType = couponType;
	}
	@Override
	public String toString() {
//		return "CouponTypeEntity [CouponTypeId=" + CouponTypeId + ", Name="
//				+ Name + ", Money=" + Money + ", Count=" + Count + ", UseMode="
//				+ UseMode + ", UserRankLower=" + UserRankLower
//				+ ", OrderAmountLower=" + OrderAmountLower + ", LimitCateId="
//				+ LimitCateId + ", LimitCateName=" + LimitCateName
//				+ ", LimitBrandId=" + LimitBrandId + ", LimitBrandName="
//				+ LimitBrandName + ", LimitProduct=" + LimitProduct
//				+ ", LimitProductName=" + LimitProductName + ", SendStartTime="
//				+ SendStartTime + ", SendEndTime=" + SendEndTime
//				+ ", UseExpireTime=" + UseExpireTime + ", UseStartTime="
//				+ UseStartTime + ", UseEndTime=" + UseEndTime + ", Couponid="
//				+ Couponid + ", Couponsn=" + Couponsn + "]";
		return "";
	}
	
	
	
}
