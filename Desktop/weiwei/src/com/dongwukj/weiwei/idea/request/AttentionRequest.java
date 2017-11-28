package com.dongwukj.weiwei.idea.request;

/**
 * Created by sunjaly on 15/3/24.
 */
public class AttentionRequest extends BaseRequest {
    private Integer pageIndex;
    private Integer addNum;

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
}
