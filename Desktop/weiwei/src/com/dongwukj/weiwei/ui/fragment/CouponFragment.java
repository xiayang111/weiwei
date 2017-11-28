package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.FragmentTabAdapter;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.ClaimCouponRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.CouponListEntity;
import com.dongwukj.weiwei.idea.result.CouponListResult;
import com.dongwukj.weiwei.idea.result.MyCouponListResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CouponFragment extends AbstractHeaderFragment {

	private ListView lv_coupon;
	private ArrayList<CouponListEntity> list = new ArrayList<CouponListEntity>();
	private RadioGroup coupon_radio;
	
	private List<Fragment> lists= new ArrayList<Fragment>();
	
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_coupon_layout, container, false);
		return view;
	}

	@Override
	protected String setTitle() {
		return activity.getResources().getString(R.string.purse_coupon);
	}

	@Override
	protected void findView(View v) {
		FragmentManager manager = activity.getSupportFragmentManager();
		
		coupon_radio = (RadioGroup) v.findViewById(R.id.coupon_radio);  //实例化 RadioGroup
		
		Coupon_AlreadyFragment coupon_already = new Coupon_AlreadyFragment();
		//Coupon_ReadyFragment coupon_ready = new Coupon_ReadyFragment();
		Coupon_OutDate_Fragment coupon_outdate = new Coupon_OutDate_Fragment();
		
		//lists.add(coupon_ready);
		lists.add(coupon_already);
		lists.add(coupon_outdate);
		
		
		
/*		Coupon_Unify_Fragment coupon_Already = new Coupon_Unify_Fragment();
		Coupon_Unify_Fragment coupon_Ready = new Coupon_Unify_Fragment();
		Coupon_Unify_Fragment coupon_Outdate = new Coupon_Unify_Fragment();
		lists.add(coupon_ready);
		lists.add(coupon_ready);
		lists.add(coupon_ready);*/

		
		new FragmentTabAdapter(activity, lists, R.id.fl_coupon, coupon_radio,0);
		
		
	}
	
	
	
}
