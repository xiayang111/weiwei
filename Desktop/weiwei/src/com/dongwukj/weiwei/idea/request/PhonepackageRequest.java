package com.dongwukj.weiwei.idea.request;

/**
 * Created by sunjaly on 15/3/30.
 */
public class PhonepackageRequest extends BaseRequest {
    private Integer pageSize;
    private Integer pageNumber;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
