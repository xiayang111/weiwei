package com.dongwukj.weiwei.idea.request;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;


@Table("Unit")
public class Unit {
	  @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
		private long id;
	private int unitid;
	private int isweightunit;
	private String name;
	private String note;
	public int getUnitid() {
		return unitid;
	}
	public void setUnitid(int unitid) {
		this.unitid = unitid;
	}
	public int getIsweightunit() {
		return isweightunit;
	}
	public void setIsweightunit(int isweightunit) {
		this.isweightunit = isweightunit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
