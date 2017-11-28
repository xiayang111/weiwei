package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class LockerResult extends BaseResult {

	private ArrayList<PlotsEntity> ctrns;

	public ArrayList<PlotsEntity> getCtrns() {
		return ctrns;
	}

	public void setCtrns(ArrayList<PlotsEntity> ctrns) {
		this.ctrns = ctrns;
	}

	public LockerResult(ArrayList<PlotsEntity> ctrns) {
		super();
		this.ctrns = ctrns;
	}

	public LockerResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "LockerResult [ctrns=" + ctrns + "]";
	}
	
	
}
