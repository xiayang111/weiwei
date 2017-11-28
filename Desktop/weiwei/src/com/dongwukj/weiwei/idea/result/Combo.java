package com.dongwukj.weiwei.idea.result;

public class Combo {
	private String id_1;
	private String url_1;
	private String name;
	private String describe;
	private String price;
	public String getId() {
		return id_1;
	}
	public void setId(String id) {
		this.id_1 = id;
	}
	public String getUrl() {
		return url_1;
	}
	public void setUrl(String url) {
		this.url_1 = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Combo(String id, String url, String name, String describe,
			String price) {
		super();
		this.id_1 = id;
		this.url_1 = url;
		this.name = name;
		this.describe = describe;
		this.price = price;
	}
	
}
