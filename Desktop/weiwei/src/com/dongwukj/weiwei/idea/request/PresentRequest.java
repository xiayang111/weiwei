package com.dongwukj.weiwei.idea.request;

public class PresentRequest extends BaseRequest {
	private int pageType;
	private int advertsType;
	public int getPageType() {
		return pageType;
	}
	public void setPageType(int pageType) {
		this.pageType = pageType;
	}
	public int getAdvertsType() {
		return advertsType;
	}
	public void setAdvertsType(int advertsType) {
		this.advertsType = advertsType;
	}
	@Override
	public String toString() {
		return "PresentRequest [pageType=" + pageType + ", advertsType="
				+ advertsType + "]";
	}
	public PresentRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PresentRequest(int pageType, int advertsType) {
		super();
		this.pageType = pageType;
		this.advertsType = advertsType;
	}
	
}
