package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;
import java.util.Arrays;

public class linkedwordsResult extends BaseResult {
/*	private String[] keywords;
	
	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "linkedwordsResult [keywords=" + Arrays.toString(keywords) + "]";
	}
	*/
	private ArrayList<keyWordEntity> keyWordObjects;
	
	public ArrayList<keyWordEntity> getKeyWordObjects() {
		return keyWordObjects;
	}

	public void setKeyWordObjects(ArrayList<keyWordEntity> keyWordObjects) {
		this.keyWordObjects = keyWordObjects;
	}

	public class keyWordEntity{
		private int businessgoodsid;
		private int gid;
		private String  name;
		public int getBusinessgoodsid() {
			return businessgoodsid;
		}
		public void setBusinessgoodsid(int businessgoodsid) {
			this.businessgoodsid = businessgoodsid;
		}
		public int getGid() {
			return gid;
		}
		public void setGid(int gid) {
			this.gid = gid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	
	}
}
