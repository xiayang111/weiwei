package com.dongwukj.weiwei.idea.request;

public class PurchaseRequest extends BaseRequest {
	private int pageIndex;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	@Override
	public String toString() {
		return "PurchaseRequest [pageIndex=" + pageIndex + "]";
	}
	
}
