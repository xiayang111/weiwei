package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

import com.dongwukj.weiwei.idea.request.BusinessGoodsDiscountObject;
import com.dongwukj.weiwei.idea.request.NewHomeattribute;
import com.dongwukj.weiwei.idea.request.Speciality;
import com.dongwukj.weiwei.idea.request.Unit;

public class NewPhonegoodsdetailResult extends BaseResult {
	private int stockNum;
	//private NewHomeattribute defaultGoods;
	//private ArrayList<NewHomeattribute> AttrModelList;
	private float courierFee;
	private String description;
	private float shopPrice;
	private float weight;
	private int goodsId;
	private String goodsName;
	private ArrayList<Icon> Imagelist;
	private String icon;
	private int salecount;
	private int isback;
	private int isFullcut;
	private int isgift;
	private int ishot;
	private int isnew;
	private String imageUrl;
	private String descriptionimage;
	private ArrayList<Speciality> iconList;
	private String goodsdescription;
	private int limitamount;
	private BusinessGoodsDiscountObject businessGoodsDiscountObject;
	private int gid;
	private String discountprefix;
	private String discountsuffix;
	private String specialwords;
	private String fulltextreduction;
	private double gramprice;
	private double gramdiscountprice;
	private Unit unit;
	
	private int sidecourseminweight;
	private int maincourseminweight;
	private int incrementweight;
	private int ismaincourse;
	
	
	public int getIsmaincourse() {
		return ismaincourse;
	}

	public void setIsmaincourse(int ismaincourse) {
		this.ismaincourse = ismaincourse;
	}

	public int getSidecourseminweight() {
		return sidecourseminweight;
	}

	public void setSidecourseminweight(int sidecourseminweight) {
		this.sidecourseminweight = sidecourseminweight;
	}

	public int getMaincourseminweight() {
		return maincourseminweight;
	}

	public void setMaincourseminweight(int maincourseminweight) {
		this.maincourseminweight = maincourseminweight;
	}

	public int getIncrementweight() {
		return incrementweight;
	}

	public void setIncrementweight(int incrementweight) {
		this.incrementweight = incrementweight;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getDiscountprefix() {
		return discountprefix;
	}

	public void setDiscountprefix(String discountprefix) {
		this.discountprefix = discountprefix;
	}

	public String getDiscountsuffix() {
		return discountsuffix;
	}

	public void setDiscountsuffix(String discountsuffix) {
		this.discountsuffix = discountsuffix;
	}

	public String getSpecialwords() {
		return specialwords;
	}

	public void setSpecialwords(String specialwords) {
		this.specialwords = specialwords;
	}

	public String getFulltextreduction() {
		return fulltextreduction;
	}

	public void setFulltextreduction(String fulltextreduction) {
		this.fulltextreduction = fulltextreduction;
	}

	public double getGramprice() {
		return gramprice;
	}

	public void setGramprice(double gramprice) {
		this.gramprice = gramprice;
	}

	public double getGramdiscountprice() {
		return gramdiscountprice;
	}

	public void setGramdiscountprice(double gramdiscountprice) {
		this.gramdiscountprice = gramdiscountprice;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public BusinessGoodsDiscountObject getBusinessGoodsDiscountObject() {
		return businessGoodsDiscountObject;
	}

	public void setBusinessGoodsDiscountObject(
			BusinessGoodsDiscountObject businessGoodsDiscountObject) {
		this.businessGoodsDiscountObject = businessGoodsDiscountObject;
	}

	public int getLimitamount() {
		return limitamount;
	}

	public void setLimitamount(int limitamount) {
		this.limitamount = limitamount;
	}

	public String getGoodsdescription() {
		return goodsdescription;
	}

	public void setGoodsdescription(String goodsdescription) {
		this.goodsdescription = goodsdescription;
	}

	public int getIsback() {
		return isback;
	}

	public void setIsback(int isback) {
		this.isback = isback;
	}

	public int getIsFullcut() {
		return isFullcut;
	}

	public void setIsFullcut(int isFullcut) {
		this.isFullcut = isFullcut;
	}

	public int getIsgift() {
		return isgift;
	}

	public void setIsgift(int isgift) {
		this.isgift = isgift;
	}

	public int getIshot() {
		return ishot;
	}

	public void setIshot(int ishot) {
		this.ishot = ishot;
	}

	public int getIsnew() {
		return isnew;
	}

	public void setIsnew(int isnew) {
		this.isnew = isnew;
	}

	public ArrayList<Speciality> getIconList() {
		return iconList;
	}

	public void setIconList(ArrayList<Speciality> iconList) {
		this.iconList = iconList;
	}

	public String getDescriptionimage() {
		return descriptionimage;
	}

	public void setDescriptionimage(String descriptionimage) {
		this.descriptionimage = descriptionimage;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getSalecount() {
		return salecount;
	}

	public void setSalecount(int salecount) {
		this.salecount = salecount;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public int getStockNum() {
		return stockNum;
	}

	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}

	

	public float getCourierFee() {
		return courierFee;
	}

	public void setCourierFee(float courierFee) {
		this.courierFee = courierFee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(float shopPrice) {
		this.shopPrice = shopPrice;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	

	public ArrayList<Icon> getImagelist() {
		return Imagelist;
	}

	public void setImagelist(ArrayList<Icon> imagelist) {
		Imagelist = imagelist;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}


}	
