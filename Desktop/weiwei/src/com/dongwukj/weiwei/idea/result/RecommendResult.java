package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class RecommendResult extends BaseResult{
//	private List<AdEntity> listVp;
	private ArrayList<ComboEntity> bannersList;
	private int listCount;
	/*public List<AdEntity> getListVp() {
		return listVp;
	}
	public void setListVp(List<AdEntity> listVp) {
		this.listVp = listVp;
	}*/
	
	public int getListCount() {
		return listCount;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
	public ArrayList<ComboEntity> getBannersList() {
		return bannersList;
	}
	public void setBannersList(ArrayList<ComboEntity> bannersList) {
		this.bannersList = bannersList;
	}
	@Override
	public String toString() {
		return "RecommendResult [bannersList=" + bannersList + ", listCount="
				+ listCount + "]";
	}
	
	
	
	
}
