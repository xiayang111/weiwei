package com.dongwukj.weiwei.ui.fragment;

import com.dongwukj.weiwei.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SourceWeiweiFragment extends AbstractHeaderFragment {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sourceweiweifragment, null);
		return view;
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "惟惟检测报告";
	}

	@Override
	protected void findView(View v) {
		// TODO Auto-generated method stub

	}

}
