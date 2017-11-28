package com.dongwukj.weiwei.idea.result;

public class UpdateClassifyEntity {
	private int cateId;
	private String name;
	public int getCateId() {
		return cateId;
	}
	public void setCateId(int cateId) {
		this.cateId = cateId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UpdateClassifyEntity(int cateId, String name) {
		super();
		this.cateId = cateId;
		this.name = name;
	}
	public UpdateClassifyEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
