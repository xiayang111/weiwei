package com.dongwukj.weiwei.idea.result;

public class PlotsEntity {

	private String name;
	private int id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PlotsEntity(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}
	public PlotsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "PlotsEntity [name=" + name + ", id=" + id + "]";
	}
	
}
