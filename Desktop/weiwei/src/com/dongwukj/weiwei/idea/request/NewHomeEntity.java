package com.dongwukj.weiwei.idea.request;

import java.util.ArrayList;

import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.Mapping.Relation;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
@Table("cartlist")
public class NewHomeEntity {
	@PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
	private long id;
	private int businessgoodsid;
	
	private String	name;
	private float weight;
	private float price;
	private int stockNum;
	private int count;
	private String icon;
	private boolean isChecked=true;
/*	@Mapping(Relation.OneToMany)
	private ArrayList<NewHomeattribute> attributeValueList;*/
	
	private Integer userAccount;
	private Integer plotid;
	private int gid;
	@Mapping(Mapping.Relation.OneToOne)
	private BusinessGoodsDiscountObject businessGoodsDiscountObject;
	private int marketId;
	private int isback;
	private int isFullcut;
	private int isgift;
	private int ishot;
	private int isnew;
	private int limitamount;
	@Ignore
	private ArrayList<Speciality> iconList;
	private int status;
	private long addTime;
	
	private int ismain=1;
	private int incrementweight;
	private int ismaincourse;
	private int maincourseminweight;
	private int sidecourseminweight;
	private String gramdiscount;
	private double gramprice;
	private double gramdiscountprice;
	@Mapping(Mapping.Relation.OneToOne)
	private Unit unit;
	
	public int getIsmain() {
		return ismain;
	}

	public void setIsmain(int ismain) {
		this.ismain = ismain;
	}

	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIncrementweight() {
		return incrementweight;
	}

	public void setIncrementweight(int incrementweight) {
		this.incrementweight = incrementweight;
	}

	public int getIsmaincourse() {
		return ismaincourse;
	}

	public void setIsmaincourse(int ismaincourse) {
		this.ismaincourse = ismaincourse;
	}

	public int getMaincourseminweight() {
		return maincourseminweight;
	}

	public void setMaincourseminweight(int maincourseminweight) {
		this.maincourseminweight = maincourseminweight;
	}

	public int getSidecourseminweight() {
		return sidecourseminweight;
	}

	public void setSidecourseminweight(int sidecourseminweight) {
		this.sidecourseminweight = sidecourseminweight;
	}

	


	
	public String getGramdiscount() {
		return gramdiscount;
	}

	public void setGramdiscount(String gramdiscount) {
		this.gramdiscount = gramdiscount;
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

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public long getAddTime() {
		return addTime;
	}

	public void setAddTime(long addTime) {
		this.addTime = addTime;
	}

	public int getStockNum() {
		return stockNum;
	}

	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BusinessGoodsDiscountObject getBusinessGoodsDiscountObject() {
		return businessGoodsDiscountObject;
	}

	public void setBusinessGoodsDiscountObject(
			BusinessGoodsDiscountObject businessGoodsDiscountObject) {
		this.businessGoodsDiscountObject = businessGoodsDiscountObject;
	}

	public ArrayList<Speciality> getIconList() {
		return iconList;
	}

	public void setIconList(ArrayList<Speciality> iconList) {
		this.iconList = iconList;
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

	public int getLimitamount() {
		return limitamount;
	}

	public void setLimitamount(int limitamount) {
		this.limitamount = limitamount;
	}

	public void setUserAccount(Integer userAccount) {
		this.userAccount = userAccount;
	}

	public void setPlotid(Integer plotid) {
		this.plotid = plotid;
	}

	public int getMarketId() {
		return marketId;
	}

	public void setMarketId(int marketId) {
		this.marketId = marketId;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(int userAccount) {
		this.userAccount = userAccount;
	}

	public int getPlotid() {
		return plotid;
	}

	public void setPlotid(int plotid) {
		this.plotid = plotid;
	}



	public int getBusinessgoodsid() {
		return businessgoodsid;
	}

	public void setBusinessgoodsid(int businessgoodsid) {
		this.businessgoodsid = businessgoodsid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	
	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	/*public ArrayList<NewHomeattribute> getAttributeValueList() {
		return attributeValueList;
	}

	public void setAttributeValueList(ArrayList<NewHomeattribute> attributeValueList) {
		this.attributeValueList = attributeValueList;
	}*/
	
}
