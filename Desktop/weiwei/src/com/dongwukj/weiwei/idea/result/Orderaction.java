package com.dongwukj.weiwei.idea.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

@Table("Orderaction")
public class Orderaction {
	@PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
	private long id;
	private String actiondes;
	private String actiontime;
	private int actiontype;
	public String getActiondes() {
		return actiondes;
	}
	public void setActiondes(String actiondes) {
		this.actiondes = actiondes;
	}
	public String getActiontime() {
		return actiontime;
	}
	public void setActiontime(String actiontime) {
		 Pattern pattern = Pattern.compile("\\d+");
	        Matcher matcher = pattern.matcher(actiontime);
	        if(matcher.find()){
	            this.actiontime=matcher.group();
	            return;
	        }
	        this.actiontime = actiontime;
	}
	public int getActiontype() {
		return actiontype;
	}
	public void setActiontype(int actiontype) {
		this.actiontype = actiontype;
	}
	
	
}
