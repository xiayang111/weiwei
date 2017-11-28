package com.dongwukj.weiwei.idea.request;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

@Table("attribute")
public class NewHomeattribute{
    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
	private long id;
    
	private Integer attrvalueid;
	private String attrvaluename;
	public Integer getAttrvalueid() {
		return attrvalueid;
	}
	public void setAttrvalueid(Integer attrvalueid) {
		this.attrvalueid = attrvalueid;
	}
	public String getAttrvaluename() {
		return attrvaluename;
	}
	public void setAttrvaluename(String attrvaluename) {
		this.attrvaluename = attrvaluename;
	}
	
	
}