package com.dongwukj.weiwei.idea.result;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

@Table("SuitPromotion")
public class SuitPromotion {
    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
	private long id;
	private String name;
	private String ShowImg;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShowImg() {
		return ShowImg;
	}
	public void setShowImg(String showImg) {
		ShowImg = showImg;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
