package com.dongwukj.weiwei.idea.result;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
@Table("NewOrderProductEntity")
public class NewOrderProductEntity {
	@PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
	private long id;
	private String addtime;
	private int brandid;
	private int cateid;
	private int isreview;
	private String name;
	private int oid;
	private int pid;
	private float realcount;
	private float realweight;
	private float rebateamount;
	private float shopprice;
	private String showimg;
	private int state;
	private float weight=50;
	private String uomname;
	private double productamount;
	private int isweightunit;
	
	
	public int getIsweightunit() {
		return isweightunit;
	}
	public void setIsweightunit(int isweightunit) {
		this.isweightunit = isweightunit;
	}
	public double getProductamount() {
		return productamount;
	}
	public void setProductamount(double productamount) {
		this.productamount = productamount;
	}
	public String getUomname() {
		return uomname;
	}
	public void setUomname(String uomname) {
		this.uomname = uomname;
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
	public int getBrandid() {
		return brandid;
	}
	public void setBrandid(int brandid) {
		this.brandid = brandid;
	}
	public int getCateid() {
		return cateid;
	}
	public void setCateid(int cateid) {
		this.cateid = cateid;
	}
	public int getIsreview() {
		return isreview;
	}
	public void setIsreview(int isreview) {
		this.isreview = isreview;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public float getRealcount() {
		return realcount;
	}
	public void setRealcount(float realcount) {
		this.realcount = realcount;
	}
	public float getRealweight() {
		return realweight;
	}
	public void setRealweight(float realweight) {
		this.realweight = realweight;
	}
	public float getRebateamount() {
		return rebateamount;
	}
	public void setRebateamount(float rebateamount) {
		this.rebateamount = rebateamount;
	}
	public float getShopprice() {
		return shopprice;
	}
	public void setShopprice(float shopprice) {
		this.shopprice = shopprice;
	}
	public String getShowimg() {
		return showimg;
	}
	public void setShowimg(String showimg) {
		this.showimg = showimg;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
}
