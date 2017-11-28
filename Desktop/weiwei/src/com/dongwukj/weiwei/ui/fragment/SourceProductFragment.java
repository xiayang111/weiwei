package com.dongwukj.weiwei.ui.fragment;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class SourceProductFragment extends AbstractHeaderFragment implements OnClickListener {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.source_fragment_product, null);
		return view;
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "溯源查询";
	}

	@Override
	protected void findView(View v) {
		RelativeLayout rl_supplier=(RelativeLayout) v.findViewById(R.id.rl_supplier);
		
		RelativeLayout rl_weiwei=(RelativeLayout) v.findViewById(R.id.rl_weiwei);
		rl_supplier.setOnClickListener(this);
		rl_weiwei.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_supplier:
			Intent intent=new Intent(activity, HomeHeaderActivity.class);
			intent.putExtra("type", HeaderActivityType.SourceSupplierFragment.ordinal());
			startActivity(intent);
			break;
		case R.id.rl_weiwei:
			Intent intent1=new Intent(activity, HomeHeaderActivity.class);
			intent1.putExtra("type", HeaderActivityType.SourceWeiweiFragment.ordinal());
			startActivity(intent1);
			break;

		default:
			break;
		}
		
	}

}
