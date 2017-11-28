package com.dongwukj.weiwei.idea.request;

public class RecommendRequest extends BaseRequest {
	private int pageIndex;
	private int advertsType;
	
	public int getAdvertsType() {
		return advertsType;
	}

	public void setAdvertsType(int advertsType) {
		this.advertsType = advertsType;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	@Override
	public String toString() {
		return "RecommendRequest [pageIndex=" + pageIndex + ", advertsType="
				+ advertsType + "]";
	}

	

	
}
