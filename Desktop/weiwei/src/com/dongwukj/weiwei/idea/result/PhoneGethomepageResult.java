package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;


public class PhoneGethomepageResult extends BaseResult {
	private ArrayList<AdEntity> bannersList;
	private ArrayList<HomeCategorie> homeCategories;
	private ArrayList<FullGiftEntity> referralsList;
	public ArrayList<AdEntity> getBannersList() {
		return bannersList;
	}
	public void setBannersList(ArrayList<AdEntity> bannersList) {
		this.bannersList = bannersList;
	}
	public ArrayList<HomeCategorie> getHomeCategories() {
		return homeCategories;
	}
	public void setHomeCategories(ArrayList<HomeCategorie> homeCategories) {
		this.homeCategories = homeCategories;
	}
	public ArrayList<FullGiftEntity> getReferralsList() {
		return referralsList;
	}
	public void setReferralsList(ArrayList<FullGiftEntity> referralsList) {
		this.referralsList = referralsList;
	}
	
	
}
