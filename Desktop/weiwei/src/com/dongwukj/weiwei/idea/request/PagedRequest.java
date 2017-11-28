package com.dongwukj.weiwei.idea.request;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

/**
 * Created by sunjaly on 15/4/18.
 */
public class PagedRequest extends BaseRequest{

    private int pageIndex;

    private int listCount;

    public int getPageIndex() {
        return pageIndex;
    }

    @JSONField(name = "pageIndex")
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getListCount() {
        return listCount;
    }

    public void setListCount(int listCount) {
        this.listCount = listCount;
    }
}
