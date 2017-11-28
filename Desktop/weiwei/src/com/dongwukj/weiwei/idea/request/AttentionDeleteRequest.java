package com.dongwukj.weiwei.idea.request;

import java.util.List;

/**
 * Created by sunjaly on 15/3/25.
 */
public class AttentionDeleteRequest extends BaseRequest {
    private List<Integer> productIdList;

    public List<Integer> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<Integer> productIdList) {
        this.productIdList = productIdList;
    }
}
