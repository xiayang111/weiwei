package com.dongwukj.weiwei.idea.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderDetailEntity {
			private String AddTime; 
	        private String Address;
	        private String BestTime;
	        private String BuyerRemark;
	        private String Consignee;
	        private float CouponMoney; 
	        private double Discount; 
	        private String Email;
	        private float FullCut;
	        private String IP;
	        private String IsReview;
	        private String Mobile;
	        private String OSN;
	        private Integer Oid;
	        private float OrderAmount;
	        private Integer OrderState;
	        private String ParentId;
	        private String PayCreditCount;
	        private String PayCreditMoney;
	        private double PayFee;
	        private String PayFriendName;
	        private String PayMode;
	        private String PaySN;
	        private String PaySystemName;
	        private String PayTime;
	        private String Phone;
	        private float ProductAmount;
	        private String RegionId;
	        private double ShipFee;
	        private String ShipFriendName;
	        private String ShipSN;
	        private String ShipSystemName;
	        private String ShipTime;
	        private float SurplusMoney;
	        private String Uid;
	        private String Weight;
	        private String ZipCode;
			public String getAddTime() {
				return AddTime;
			}
			public void setAddTime(String addTime) {
				Pattern pattern = Pattern.compile("\\d+");
		        Matcher matcher = pattern.matcher(addTime);
		        if(matcher.find()){
		            this.AddTime=matcher.group();
		            return;
		        }
		        this.AddTime = addTime;
			}
			public String getAddress() {
				return Address;
			}
			public void setAddress(String address) {
				Address = address;
			}
			public String getBestTime() {
				return BestTime;
			}
			public void setBestTime(String bestTime) {
				Pattern pattern = Pattern.compile("\\d+");
		        Matcher matcher = pattern.matcher(bestTime);
		        if(matcher.find()){
		            this.BestTime=matcher.group();
		            return;
		        }
		        this.BestTime = bestTime;
			}
			public String getBuyerRemark() {
				return BuyerRemark;
			}
			public void setBuyerRemark(String buyerRemark) {
				BuyerRemark = buyerRemark;
			}
			public String getConsignee() {
				return Consignee;
			}
			public void setConsignee(String consignee) {
				Consignee = consignee;
			}
			
			
			public float getCouponMoney() {
				return CouponMoney;
			}
			public void setCouponMoney(float couponMoney) {
				CouponMoney = couponMoney;
			}
			public String getEmail() {
				return Email;
			}
			public void setEmail(String email) {
				Email = email;
			}
			
			public float getFullCut() {
				return FullCut;
			}
			public void setFullCut(float fullCut) {
				FullCut = fullCut;
			}
			public String getIP() {
				
				return IP;
			}
			public void setIP(String iP) {
				IP = iP;
			}
			public String getIsReview() {
				return IsReview;
			}
			public void setIsReview(String isReview) {
				IsReview = isReview;
			}
			public String getMobile() {
				return Mobile;
			}
			public void setMobile(String mobile) {
				Mobile = mobile;
			}
			public String getOSN() {
				return OSN;
			}
			public void setOSN(String oSN) {
				OSN = oSN;
			}
			
			public Integer getOid() {
				return Oid;
			}
			public void setOid(Integer oid) {
				Oid = oid;
			}
			
			
			public Integer getOrderState() {
				return OrderState;
			}
			public void setOrderState(Integer orderState) {
				OrderState = orderState;
			}
			public String getParentId() {
				return ParentId;
			}
			public void setParentId(String parentId) {
				ParentId = parentId;
			}
			public String getPayCreditCount() {
				return PayCreditCount;
			}
			public void setPayCreditCount(String payCreditCount) {
				PayCreditCount = payCreditCount;
			}
			public String getPayCreditMoney() {
				return PayCreditMoney;
			}
			public void setPayCreditMoney(String payCreditMoney) {
				PayCreditMoney = payCreditMoney;
			}
		
			public String getPayFriendName() {
				return PayFriendName;
			}
			public void setPayFriendName(String payFriendName) {
				PayFriendName = payFriendName;
			}
			public String getPayMode() {
				return PayMode;
			}
			public void setPayMode(String payMode) {
				PayMode = payMode;
			}
			public String getPaySN() {
				return PaySN;
			}
			public void setPaySN(String paySN) {
				PaySN = paySN;
			}
			public String getPaySystemName() {
				return PaySystemName;
			}
			public void setPaySystemName(String paySystemName) {
				PaySystemName = paySystemName;
			}
			public String getPayTime() {
				return PayTime;
			}
			public void setPayTime(String payTime) {
				PayTime = payTime;
			}
			public String getPhone() {
				return Phone;
			}
			public void setPhone(String phone) {
				Phone = phone;
			}
			
			public String getRegionId() {
				return RegionId;
			}
			public void setRegionId(String regionId) {
				RegionId = regionId;
			}
			
			public String getShipFriendName() {
				return ShipFriendName;
			}
			public void setShipFriendName(String shipFriendName) {
				ShipFriendName = shipFriendName;
			}
			public String getShipSN() {
				return ShipSN;
			}
			public void setShipSN(String shipSN) {
				ShipSN = shipSN;
			}
			public String getShipSystemName() {
				return ShipSystemName;
			}
			public void setShipSystemName(String shipSystemName) {
				ShipSystemName = shipSystemName;
			}
			public String getShipTime() {
				return ShipTime;
			}
			public void setShipTime(String shipTime) {
				ShipTime = shipTime;
			}
			
			public float getOrderAmount() {
				return OrderAmount;
			}
			public void setOrderAmount(float orderAmount) {
				OrderAmount = orderAmount;
			}
			public float getProductAmount() {
				return ProductAmount;
			}
			public void setProductAmount(float productAmount) {
				ProductAmount = productAmount;
			}
			public float getSurplusMoney() {
				return SurplusMoney;
			}
			public void setSurplusMoney(float surplusMoney) {
				SurplusMoney = surplusMoney;
			}
			public String getUid() {
				return Uid;
			}
			public void setUid(String uid) {
				Uid = uid;
			}
			public String getWeight() {
				return Weight;
			}
			public void setWeight(String weight) {
				Weight = weight;
			}
			public String getZipCode() {
				return ZipCode;
			}
			public void setZipCode(String zipCode) {
				ZipCode = zipCode;
			}
			
			public double getDiscount() {
				return Discount;
			}
			public void setDiscount(double discount) {
				Discount = discount;
			}
			public double getPayFee() {
				return PayFee;
			}
			public void setPayFee(double payFee) {
				PayFee = payFee;
			}
			public double getShipFee() {
				return ShipFee;
			}
			public void setShipFee(double shipFee) {
				ShipFee = shipFee;
			}
			@Override
			public String toString() {
				return "OrderDetailResult [AddTime=" + AddTime + ", Address="
						+ Address + ", BestTime=" + BestTime + ", BuyerRemark="
						+ BuyerRemark + ", Consignee=" + Consignee
						+ ", CouponMoney=" + CouponMoney + ", Discount="
						+ Discount + ", Email=" + Email + ", FullCut="
						+ FullCut + ", IP=" + IP + ", IsReview=" + IsReview
						+ ", Mobile=" + Mobile + ", OSN=" + OSN + ", Oid="
						+ Oid + ", OrderAmount=" + OrderAmount
						+ ", OrderState=" + OrderState + ", ParentId="
						+ ParentId + ", PayCreditCount=" + PayCreditCount
						+ ", PayCreditMoney=" + PayCreditMoney + ", PayFee="
						+ PayFee + ", PayFriendName=" + PayFriendName
						+ ", PayMode=" + PayMode + ", PaySN=" + PaySN
						+ ", PaySystemName=" + PaySystemName + ", PayTime="
						+ PayTime + ", Phone=" + Phone + ", ProductAmount="
						+ ProductAmount + ", RegionId=" + RegionId
						+ ", ShipFee=" + ShipFee + ", ShipFriendName="
						+ ShipFriendName + ", ShipSN=" + ShipSN
						+ ", ShipSystemName=" + ShipSystemName + ", ShipTime="
						+ ShipTime + ", SurplusMoney=" + SurplusMoney
						+ ", Uid=" + Uid + ", Weight=" + Weight + ", ZipCode="
						+ ZipCode + "]";
			}
	        
	 
}
