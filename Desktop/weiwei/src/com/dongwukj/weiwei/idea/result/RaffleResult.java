package com.dongwukj.weiwei.idea.result;

public class RaffleResult extends BaseResult {

	private int remainingLotteryCount;
	private String lotteryUrl;
	public int getRemainingLotteryCount() {
		return remainingLotteryCount;
	}
	public void setRemainingLotteryCount(int remainingLotteryCount) {
		this.remainingLotteryCount = remainingLotteryCount;
	}
	public String getLotteryUrl() {
		return lotteryUrl;
	}
	public void setLotteryUrl(String lotteryUrl) {
		this.lotteryUrl = lotteryUrl;
	}
	public RaffleResult(int remainingLotteryCount, String lotteryUrl) {
		super();
		this.remainingLotteryCount = remainingLotteryCount;
		this.lotteryUrl = lotteryUrl;
	}
	public RaffleResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
