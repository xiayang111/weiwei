package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.tsz.afinal.annotation.sqlite.OneToMany;

import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.annotation.Mapping.Relation;

public class OrderListResult extends BaseResult {

	private int listNumber;      //订单数量
	private ArrayList<NewOrderEntity> orderList;
	private String imgUrl;
	
	


	public int getListNumber() {
		return listNumber;
	}


	public void setListNumber(int listNumber) {
		this.listNumber = listNumber;
	}


	public ArrayList<NewOrderEntity> getOrderList() {
		return orderList;
	}


	public void setOrderList(ArrayList<NewOrderEntity> orderList) {
		this.orderList = orderList;
	}


	public String getImgUrl() {
		return imgUrl;
	}


	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	
	
}
