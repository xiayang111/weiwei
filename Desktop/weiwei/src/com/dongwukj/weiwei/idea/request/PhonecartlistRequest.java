package com.dongwukj.weiwei.idea.request;

/**
 * Created by sunjaly on 15/3/31.
 */
public class PhonecartlistRequest extends BaseRequest {
	private String selectedCartItemKeyList="";

	public String getSelectedCartItemKeyList() {
		return selectedCartItemKeyList;
	}

	public void setSelectedCartItemKeyList(String selectedCartItemKeyList) {
		this.selectedCartItemKeyList = selectedCartItemKeyList;
	}
	
}
