package com.dongwukj.weiwei.idea.request;

public class GetProductsRequest extends BaseRequest {
	 private Integer pageIndex;
	 private Integer addNum;
	 private Integer categoryId;
	 private String priceRange;
	 private String attrValueIdListString;
	 private String sortColumn;
	 private Integer sortDirection;
	 private String keyword;
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getAddNum() {
		return addNum;
	}
	public void setAddNum(Integer addNum) {
		this.addNum = addNum;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getPriceRange() {
		return priceRange;
	}
	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}
	public String getAttrValueIdListString() {
		return attrValueIdListString;
	}
	public void setAttrValueIdListString(String attrValueIdListString) {
		this.attrValueIdListString = attrValueIdListString;
	}
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public Integer getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(Integer sortDirection) {
		this.sortDirection = sortDirection;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		return "GetProductsRequest [pageIndex=" + pageIndex + ", addNum="
				+ addNum + ", categoryId=" + categoryId + ", priceRange="
				+ priceRange + ", attrValueIdListString="
				+ attrValueIdListString + ", sortColumn=" + sortColumn
				+ ", sortDirection=" + sortDirection + ", keyword=" + keyword
				+ "]";
	}
	 
}
