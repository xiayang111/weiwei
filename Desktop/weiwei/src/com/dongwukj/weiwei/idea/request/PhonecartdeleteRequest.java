package com.dongwukj.weiwei.idea.request;

import android.content.Intent;

import java.util.List;

/**
 * Created by sunjaly on 15/4/11.
 */
public class PhonecartdeleteRequest extends BaseRequest {
    private String goodsId;
    private String pmId;

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
