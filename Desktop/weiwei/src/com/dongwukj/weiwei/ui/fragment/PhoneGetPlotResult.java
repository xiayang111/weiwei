package com.dongwukj.weiwei.ui.fragment;

import java.io.Serializable;

import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.Plot;

public class PhoneGetPlotResult extends BaseResult implements Serializable{
	private Plot plot; 
	
	public Plot getPlot() {
		return plot;
	}

	public void setPlot(Plot plot) {
		this.plot = plot;
	}


}
