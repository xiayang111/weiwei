package com.dongwukj.weiwei.idea.result;

public class EventBusEntity {
	private int cateId;
	private boolean isClassifyMenu;
	public int getCateId() {
		return cateId;
	}
	public void setCateId(int cateId) {
		this.cateId = cateId;
	}
	public boolean isClassifyMenu() {
		return isClassifyMenu;
	}
	public void setClassifyMenu(boolean isClassifyMenu) {
		this.isClassifyMenu = isClassifyMenu;
	}
	public EventBusEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EventBusEntity(int cateId, boolean isClassifyMenu) {
		super();
		this.cateId = cateId;
		this.isClassifyMenu = isClassifyMenu;
	}
	
}
