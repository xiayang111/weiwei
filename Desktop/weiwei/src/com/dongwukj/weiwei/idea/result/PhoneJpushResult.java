package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;



public class PhoneJpushResult extends BaseResult {
	private ArrayList<JpushMessageEntity> jpushinfolist;
	private int listNumber;
	
	public int getListNumber() {
		return listNumber;
	}

	public void setListNumber(int listNumber) {
		this.listNumber = listNumber;
	}

	public ArrayList<JpushMessageEntity> getJpushinfolist() {
		return jpushinfolist;
	}

	public void setJpushinfolist(ArrayList<JpushMessageEntity> jpushinfolist) {
		this.jpushinfolist = jpushinfolist;
	}
	
}
