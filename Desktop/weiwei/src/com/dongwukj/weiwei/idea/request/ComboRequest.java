package com.dongwukj.weiwei.idea.request;

public class ComboRequest extends BaseRequest {
	private int pageIndex;
	private int addNum;
	
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getAddNum() {
		return addNum;
	}
	public void setAddNum(int addNum) {
		this.addNum = addNum;
	}
	@Override
	public String toString() {
		return "ComboRequest [pageIndex=" + pageIndex + ", addNum=" + addNum
				+ "]";
	}
	
}
