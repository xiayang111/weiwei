package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class PresentResult extends BaseResult{
/*	private List<AdEntity> listVp;	
	private List<ComboEntity> listProduct;
	private int listNumber;
	public List<AdEntity> getListVp() {
		return listVp;
	}
	public void setListVp(List<AdEntity> listVp) {
		this.listVp = listVp;
	}
	public List<ComboEntity> getListProduct() {
		return listProduct;
	}
	public void setListProduct(List<ComboEntity> listProduct) {
		this.listProduct = listProduct;
	}
	public int getListNumber() {
		return listNumber;
	}
	public void setListNumber(int listNumber) {
		this.listNumber = listNumber;
	}
	@Override
	public String toString() {
		return "RecommendResult [listVp=" + listVp + ", listProduct="
				+ listProduct + ", listNumber=" + listNumber + "]";
	}*/
	private int listNumber;
	private ArrayList<PresentEntity> bannersList;
	public int getListNumber() {
		return listNumber;
	}
	public void setListNumber(int listNumber) {
		this.listNumber = listNumber;
	}
	public ArrayList<PresentEntity> getBannersList() {
		return bannersList;
	}
	public void setBannersList(ArrayList<PresentEntity> bannersList) {
		this.bannersList = bannersList;
	}
	public PresentResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PresentResult(int listNumber, ArrayList<PresentEntity> bannersList) {
		super();
		this.listNumber = listNumber;
		this.bannersList = bannersList;
	}
	@Override
	public String toString() {
		return "PresentResult [listNumber=" + listNumber + ", bannersList="
				+ bannersList + "]";
	}

}
