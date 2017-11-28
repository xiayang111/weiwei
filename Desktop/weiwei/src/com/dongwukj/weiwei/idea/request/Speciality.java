package com.dongwukj.weiwei.idea.request;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

@Table("speciality")
public class Speciality {
	@PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
	private long id;
	private String icon;
	private String name;
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
