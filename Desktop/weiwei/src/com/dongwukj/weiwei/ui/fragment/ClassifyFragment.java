package com.dongwukj.weiwei.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongwukj.weiwei.R;





import com.dongwukj.weiwei.ui.activity.SearchActivity;

import de.greenrobot.event.EventBus;



public class ClassifyFragment extends BaseFragment{

	private DrawerLayout dw;
	@Override
	public View setView_parent(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.new_classify, null);
	}

	@Override
	public void setListener() {
		
	}
	public void onEventMainThread(Boolean item){};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	@Override
	public void onPause() {
		EventBus.getDefault().post(true);
		super.onPause();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (dw!=null) {
			if ( dw.isDrawerVisible(Gravity.LEFT)) {
				dw.closeDrawer(Gravity.LEFT);
			}
		}
	
	}
	@Override
	public void initview() {
		
		dw = (DrawerLayout)view_parent.findViewById(R.id.dw);
		TextView tv_address=(TextView) view_parent.findViewById(R.id.tv_address);
		LinearLayout tv_changeAddress=(LinearLayout) view_parent.findViewById(R.id.ll_changeAddress);
		tv_changeAddress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean visible = dw.isDrawerVisible(Gravity.LEFT);
				if (visible) {
					dw.closeDrawer(Gravity.LEFT);
				}else {
					dw.openDrawer(Gravity.LEFT);
				}
			}
		});
		LinearLayout search=(LinearLayout) view_parent.findViewById(R.id.ll_search);
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  Intent intent=new Intent(activity, SearchActivity.class);
	              startActivity(intent);
			}
		});
		ClassifyMain main = new ClassifyMain(dw,tv_address);
		ClassifyMenu menu2 = new ClassifyMenu(dw);
		//dw.openDrawer(Gravity.LEFT);
		getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.classfymainone,main).commitAllowingStateLoss();
		getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.classifymenu,menu2).commitAllowingStateLoss();
		
	}
	
}
