package com.dongwukj.weiwei.idea.request;

public class AddToCartRequest extends BaseRequest {

    private int goodsId=0;
    private String pmId="0";
    private String goodsNum;
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getPmId() {
		return pmId;
	}
	public void setPmId(String pmId) {
		this.pmId = pmId;
	}
	public String getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}
	public AddToCartRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AddToCartRequest(int goodsId, String pmId, String goodsNum) {
		super();
		this.goodsId = goodsId;
		this.pmId = pmId;
		this.goodsNum = goodsNum;
	}
	@Override
	public String toString() {
		return "AddToCartRequest [goodsId=" + goodsId + ", pmId=" + pmId
				+ ", goodsNum=" + goodsNum + "]";
	}
	
    
    
    
    
    
}
