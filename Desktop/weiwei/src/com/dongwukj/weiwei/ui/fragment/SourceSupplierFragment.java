package com.dongwukj.weiwei.ui.fragment;

import com.dongwukj.weiwei.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SourceSupplierFragment extends AbstractHeaderFragment {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sourcesupplierfragment, null);
		return view;
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "供应商资质";
	}

	@Override
	protected void findView(View v) {
		// TODO Auto-generated method stub

	}

}
