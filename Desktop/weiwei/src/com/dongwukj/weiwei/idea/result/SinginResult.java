package com.dongwukj.weiwei.idea.result;

public class SinginResult extends BaseResult {
	private Integer payCredits;
	private Integer rankCredits;
	private Integer sumpayCredits;
	private Integer sumrankCredits;
	@Override
	public String toString() {
		return "SinginResult [payCredits=" + payCredits + ", rankCredits="
				+ rankCredits + ", sumpayCredits=" + sumpayCredits
				+ ", sumrankCredits=" + sumrankCredits + ", getMessage()="
				+ getMessage() + ", getCode()=" + getCode()
				+ ", getTimestamp()=" + getTimestamp() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	public Integer getPayCredits() {
		return payCredits;
	}
	public void setPayCredits(Integer payCredits) {
		this.payCredits = payCredits;
	}
	public Integer getRankCredits() {
		return rankCredits;
	}
	public void setRankCredits(Integer rankCredits) {
		this.rankCredits = rankCredits;
	}
	public Integer getSumpayCredits() {
		return sumpayCredits;
	}
	public void setSumpayCredits(Integer sumpayCredits) {
		this.sumpayCredits = sumpayCredits;
	}
	public Integer getSumrankCredits() {
		return sumrankCredits;
	}
	public void setSumrankCredits(Integer sumrankCredits) {
		this.sumrankCredits = sumrankCredits;
	}
	
	
}
