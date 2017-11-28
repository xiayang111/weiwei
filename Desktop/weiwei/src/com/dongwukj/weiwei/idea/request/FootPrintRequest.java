package com.dongwukj.weiwei.idea.request;

public class FootPrintRequest extends BaseRequest {
	
	private int addNum;
	private int pageIndex;
	
	public int getAddNum() {
		return addNum;
	}
	public void setAddNum(int addNum) {
		this.addNum = addNum;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
}
