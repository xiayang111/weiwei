package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.annotation.Mapping.Relation;



@Table("NewOrderEntity")
public class NewOrderEntity {
	@PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
	private long id;
	private String address;
	private String addtime;
	private String besttime;
	private String besttimestr;
	private String buyerremark;
	private String consignee;
	private float couponmoney;
	private int  ctrnid;
	private float discount;
	//private int  id;
	private String ip;
	private int isdelayed;
	private int isreview;
	private int isshow;
	private int  lid;
	private String mobile;
	private int oid;
	private float orderamount;
	private int orderstate;
	private String osn;
	private float payfee;
	private String payfriendname;
	private int paymode;
	private float productamount;
	private String realweight;
	private int regionid;
	private int shiptype;
	private float shipfee;
	private String shipfriendname;
	private String shipsn;
	private String shiptime;
	private float surplusmoney;
	private int uid;
	private int vid;
	private float weight;
	private String recode;
	private Plot plot;
	private float fullcut;
	private float cancelbackamount;
	private float cashbackamount;
	private float discountbeforeproductprice;
	private int goodsCount;
	private String sinceaddress;
	@Mapping(Relation.OneToMany)
	private ArrayList<Orderaction> orderactionList;
	public float getCancelbackamount() {
		return cancelbackamount;
	}
	public void setCancelbackamount(float cancelbackamount) {
		this.cancelbackamount = cancelbackamount;
	}
	
	public String getSinceaddress() {
		return sinceaddress;
	}
	public void setSinceaddress(String sinceaddress) {
		this.sinceaddress = sinceaddress;
	}
	public int getShiptype() {
		return shiptype;
	}
	public void setShiptype(int shiptype) {
		this.shiptype = shiptype;
	}
	public String getBesttimestr() {
		return besttimestr;
	}
	public void setBesttimestr(String besttimestr) {
		this.besttimestr = besttimestr;
	}
	public float getCashbackamount() {
		return cashbackamount;
	}
	
	public void setCashbackamount(float cashbackamount) {
		this.cashbackamount = cashbackamount;
	}
	
	public float getDiscountbeforeproductprice() {
		return discountbeforeproductprice;
	}
	public void setDiscountbeforeproductprice(float discountbeforeproductprice) {
		this.discountbeforeproductprice = discountbeforeproductprice;
	}
	public int getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}
	public float getFullcut() {
		return fullcut;
	}
	public void setFullcut(float fullcut) {
		this.fullcut = fullcut;
	}
	@Mapping(Relation.OneToMany)
	private ArrayList<NewOrderProductEntity> products;
	
	
	public ArrayList<Orderaction> getOrderactionList() {
		return orderactionList;
	}
	public void setOrderactionList(ArrayList<Orderaction> orderactionList) {
		this.orderactionList = orderactionList;
	}
	public Plot getPlot() {
		return plot;
	}
	public void setPlot(Plot plot) {
		this.plot = plot;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	
	public String getRecode() {
		return recode;
	}
	public void setRecode(String recode) {
		this.recode = recode;
	}
	public ArrayList<NewOrderProductEntity> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<NewOrderProductEntity> products) {
		this.products = products;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		 Pattern pattern = Pattern.compile("\\d+");
	        Matcher matcher = pattern.matcher(addtime);
	        if(matcher.find()){
	            this.addtime=matcher.group();
	            return;
	        }
	        this.addtime = addtime;
	}
	public String getBesttime() {
		return besttime;
	}
	public void setBesttime(String besttime) {
		 Pattern pattern = Pattern.compile("\\d+");
	        Matcher matcher = pattern.matcher(besttime);
	        if(matcher.find()){
	            this.besttime=matcher.group();
	            return;
	        }
	        this.besttime = besttime;
	}
	public String getBuyerremark() {
		return buyerremark;
	}
	public void setBuyerremark(String buyerremark) {
		this.buyerremark = buyerremark;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public float getCouponmoney() {
		return couponmoney;
	}
	public void setCouponmoney(float couponmoney) {
		this.couponmoney = couponmoney;
	}
	public int getCtrnid() {
		return ctrnid;
	}
	public void setCtrnid(int ctrnid) {
		this.ctrnid = ctrnid;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	/*public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}*/
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getIsdelayed() {
		return isdelayed;
	}
	public void setIsdelayed(int isdelayed) {
		this.isdelayed = isdelayed;
	}
	public int getIsreview() {
		return isreview;
	}
	public void setIsreview(int isreview) {
		this.isreview = isreview;
	}
	public int getIsshow() {
		return isshow;
	}
	public void setIsshow(int isshow) {
		this.isshow = isshow;
	}
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public float getOrderamount() {
		return orderamount;
	}
	public void setOrderamount(float orderamount) {
		this.orderamount = orderamount;
	}
	public int getOrderstate() {
		return orderstate;
	}
	public void setOrderstate(int orderstate) {
		this.orderstate = orderstate;
	}
	public String getOsn() {
		return osn;
	}
	public void setOsn(String osn) {
		this.osn = osn;
	}
	public float getPayfee() {
		return payfee;
	}
	public void setPayfee(float payfee) {
		this.payfee = payfee;
	}
	public String getPayfriendname() {
		return payfriendname;
	}
	public void setPayfriendname(String payfriendname) {
		this.payfriendname = payfriendname;
	}
	public int getPaymode() {
		return paymode;
	}
	public void setPaymode(int paymode) {
		this.paymode = paymode;
	}
	public float getProductamount() {
		return productamount;
	}
	public void setProductamount(float productamount) {
		this.productamount = productamount;
	}
	public String getRealweight() {
		return realweight;
	}
	public void setRealweight(String realweight) {
		this.realweight = realweight;
	}
	public int getRegionid() {
		return regionid;
	}
	public void setRegionid(int regionid) {
		this.regionid = regionid;
	}
	public float getShipfee() {
		return shipfee;
	}
	public void setShipfee(float shipfee) {
		this.shipfee = shipfee;
	}
	public String getShipfriendname() {
		return shipfriendname;
	}
	public void setShipfriendname(String shipfriendname) {
		this.shipfriendname = shipfriendname;
	}
	public String getShipsn() {
		return shipsn;
	}
	public void setShipsn(String shipsn) {
		this.shipsn = shipsn;
	}
	public String getShiptime() {
		return shiptime;
	}
	public void setShiptime(String shiptime) {
		this.shiptime = shiptime;
	}
	public float getSurplusmoney() {
		return surplusmoney;
	}
	public void setSurplusmoney(float surplusmoney) {
		this.surplusmoney = surplusmoney;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	
}

