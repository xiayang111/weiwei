package com.dongwukj.weiwei.idea.result;

public class DeleteAddressResult extends BaseResult {
	private int defaultSaId;
	private int marketId;
	
	public int getMarketId() {
		return marketId;
	}

	public void setMarketId(int marketId) {
		this.marketId = marketId;
	}

	public int getDefaultSaId() {
		return defaultSaId;
	}

	public void setDefaultSaId(int defaultSaId) {
		this.defaultSaId = defaultSaId;
	}
}
