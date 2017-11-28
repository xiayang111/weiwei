package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.Mapping.Relation;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

@Table("orderProductEntity")
public class OrderProductEntity implements Serializable{

	@PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
	private long id;
	
	@Mapping(Relation.OneToMany)
	private ArrayList<CartItemEntity> CartItemList;
	
	private int Oid;
	private String OSN;
	private String OrderState;
	private String PayFriendName;
	private float OrderAmount;
	private int ParentId;
	private int IsReview;
	private String AddTime;
	private String ShipFriendName;
	private int PayMode;
	private int OrderStateForDetail;
	public int getOrderStateForDetail() {
		return OrderStateForDetail;
	}
	private String Consignee;

	public int getOid() {
		return Oid;
	}
	public void setOid(int oid) {
		Oid = oid;
	}

	public String getOSN() {
		return OSN;
	}
	public void setOSN(String oSN) {
		OSN = oSN;
	}
	public String getOrderState() {
		return OrderState;
	}
	public void setOrderState(int orderState) {
		OrderStateForDetail=orderState;
		if(orderState==50||orderState==70){
			OrderState = "待发货";
		}else if(orderState==30 ){
			OrderState = "等待付款";
		}else if (orderState==10) {
			OrderState = "已提交";
		}else if (orderState==110) {
			OrderState="已发货";
		}else if (orderState==140) {
			OrderState="已完成";
		}else if (orderState==90) {
			OrderState="正在出库";
		}else if (orderState==200) {
			OrderState="已取消";
		}else if (orderState==20) {
			OrderState="等待付款";
		}
	}
	public String getPayFriendName() {
		return PayFriendName;
	}
	public void setPayFriendName(String payFriendName) {
		PayFriendName = payFriendName;
	}
	
	public float getOrderAmount() {
		return OrderAmount;
	}
	public void setOrderAmount(float orderAmount) {
		OrderAmount = orderAmount;
	}
	public int getParentId() {
		return ParentId;
	}
	public void setParentId(int parentId) {
		ParentId = parentId;
	}
	public int getIsReview() {
		return IsReview;
	}
	public void setIsReview(int isReview) {
		IsReview = isReview;
	}
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
	public String getShipFriendName() {
		return ShipFriendName;
	}
	public void setShipFriendName(String shipFriendName) {
		ShipFriendName = shipFriendName;
	}
	public int getPayMode() {
		return PayMode;
	}
	public void setPayMode(int payMode) {
		PayMode = payMode;
	}
	public String getConsignee() {
		return Consignee;
	}
	public void setConsignee(String consignee) {
		Consignee = consignee;
	}
	public OrderProductEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ArrayList<CartItemEntity> getCartItemList() {
		return CartItemList;
	}
	public void setCartItemList(ArrayList<CartItemEntity> cartItemList) {
		CartItemList = cartItemList;
	}
	public void setOrderState(String orderState) {
		OrderState = orderState;
	}
	public void setOrderStateForDetail(int orderStateForDetail) {
		OrderStateForDetail = orderStateForDetail;
	}
	


	
	
	
	
	
}
