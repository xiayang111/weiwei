package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class FootPrintResult extends BaseResult {

	private ArrayList<FootPrintEntity> products;
	private int listnumber ;  //商品总数量
	
	public int getListnumber() {
		return listnumber;
	}

	public void setListnumber(int listnumber) {
		this.listnumber = listnumber;
	}

	public ArrayList<FootPrintEntity> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<FootPrintEntity> list) {
		this.products = list;
	}
	
}
