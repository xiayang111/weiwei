package com.dongwukj.weiwei.idea.request;

/**
 * Created by sunjaly on 15/4/15.
 */
public class PhoneConfirmOrderRequest extends BaseRequest {
	private String selectedCartItemKeyList;
	
    public String getSelectedCartItemKeyList() {
		return selectedCartItemKeyList;
	}

	public void setSelectedCartItemKeyList(String selectedCartItemKeyList) {
		this.selectedCartItemKeyList = selectedCartItemKeyList;
	}

	private String goodsId;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
