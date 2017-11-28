package com.dongwukj.weiwei.idea.request;

import java.util.Arrays;

public class DeleteAddressRequest extends BaseRequest {
	private int[] saIdList;
	private int saId;
	
	public int getSaId() {
		return saId;
	}

	public void setSaId(int saId) {
		this.saId = saId;
	}

	public int[] getSaIdList() {
		return saIdList;
	}

	public void setSaIdList(int[] saIdList) {
		this.saIdList = saIdList;
	}

	@Override
	public String toString() {
		return "DeleteAddressRequest [saIdList=" + Arrays.toString(saIdList)
				+ "]";
	}
	
}
