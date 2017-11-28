package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class ComboResult extends BaseResult {
	private ArrayList<ComboEntity> packages;
	private int listNumber;
	
	public int getListNumber() {
		return listNumber;
	}
	public void setListNumber(int listNumber) {
		this.listNumber = listNumber;
	}
	public ArrayList<ComboEntity> getPackages() {
		return packages;
	}
	public void setPackages(ArrayList<ComboEntity> packages) {
		this.packages = packages;
	}
	@Override
	public String toString() {
		return "ComboResult [packages=" + packages + ", listNumber="
				+ listNumber + "]";
	}
	
	
	
}
