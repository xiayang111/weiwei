package com.dongwukj.weiwei.idea.result;

public class ClassifyDetails {
	private String product_name;
	private int product_id;
	private String photoUrl;
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public ClassifyDetails(String product_name, int product_id, String photoUrl) {
		super();
		this.product_name = product_name;
		this.product_id = product_id;
		this.photoUrl = photoUrl;
	}
	public ClassifyDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ClassifyDetails [product_name=" + product_name
				+ ", product_id=" + product_id + ", photoUrl=" + photoUrl + "]";
	}
	
}
