package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class PlotsResult extends BaseResult {

	private ArrayList<PlotsEntity> plots ;

	public ArrayList<PlotsEntity> getPlots() {
		return plots;
	}

	public void setPlots(ArrayList<PlotsEntity> plots) {
		this.plots = plots;
	}

	public PlotsResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlotsResult(ArrayList<PlotsEntity> plots) {
		super();
		this.plots = plots;
	}

	@Override
	public String toString() {
		return "PlotsResult [plots=" + plots + "]";
	}
	
	
}
