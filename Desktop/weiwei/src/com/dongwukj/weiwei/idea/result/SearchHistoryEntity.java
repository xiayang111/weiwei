package com.dongwukj.weiwei.idea.result;

import net.tsz.afinal.annotation.sqlite.Id;

public class SearchHistoryEntity {
	@Id(column="User_Id")
	private int historyId;
	private String keyword;
	private long time;
	public int getHistoryId() {
		return historyId;
	}
	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "SearchHistoryEntity [historyId=" + historyId + ", keyword="
				+ keyword + ", time=" + time + "]";
	}
	
}
