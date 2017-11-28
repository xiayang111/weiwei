package com.dongwukj.weiwei.idea.request;

public class NewHomeRequest extends BaseRequest {
	private Integer  pageIndex;
	private Integer categoryId;
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	

}
