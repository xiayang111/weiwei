package com.dongwukj.weiwei.idea.request;

public class EvaluateRequest extends BaseRequest {
	private int pId;
	private int addNum;
	private int pageIndex;
	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

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

	@Override
	public String toString() {
		return "EvaluateRequest [pId=" + pId + ", addNum=" + addNum
				+ ", pageIndex=" + pageIndex + "]";
	}
	
	
	
}
