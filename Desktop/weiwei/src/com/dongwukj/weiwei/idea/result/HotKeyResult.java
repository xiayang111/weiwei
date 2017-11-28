package com.dongwukj.weiwei.idea.result;

import java.util.Arrays;

public class HotKeyResult extends BaseResult {
	private String[] keywords;

	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "HotKeyResult [keywords=" + Arrays.toString(keywords) + "]";
	}
	
}
