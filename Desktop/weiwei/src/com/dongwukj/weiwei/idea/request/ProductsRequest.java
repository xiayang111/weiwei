package com.dongwukj.weiwei.idea.request;

public class ProductsRequest extends BaseRequest {

	private int productId;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public ProductsRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductsRequest(int productId) {
		super();
		this.productId = productId;
	}
	
	
}
