package com.dongwukj.weiwei.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dongwukj.weiwei.R;

public class UserLevelFragment extends AbstractHeaderFragment {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_userlevel, null);
		return view;
	}

	@Override
	protected String setTitle() {
		return activity.getResources().getString(R.string.userlevel);
	}

	@Override
	protected void findView(View v) {
		// TODO Auto-generated method stub

	}

}
