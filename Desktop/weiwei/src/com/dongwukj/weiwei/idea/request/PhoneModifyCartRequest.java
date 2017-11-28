package com.dongwukj.weiwei.idea.request;

/**
 * Created by sunjaly on 15/4/11.
 */
public class PhoneModifyCartRequest extends BaseRequest {
    private int goodsId=0;
    private int pmId=0;
    private int goodsNum;
    private String selectedCartItemKeyList;
    
    public String getSelectedCartItemKeyList() {
		return selectedCartItemKeyList;
	}

	public void setSelectedCartItemKeyList(String selectedCartItemKeyList) {
		this.selectedCartItemKeyList = selectedCartItemKeyList;
	}

	public int getPmId() {
        return pmId;
    }

    public void setPmId(int pmId) {
        this.pmId = pmId;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
