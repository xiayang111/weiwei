package com.dongwukj.weiwei.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.CouponAdapter;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.CouponListEntity;
import com.dongwukj.weiwei.idea.result.CouponTypeEntity;
import com.dongwukj.weiwei.idea.result.MyCouponListResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;


import com.dongwukj.weiwei.ui.activity.LoginActivity;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.FinalDb;

public class Coupon_AlreadyFragment extends BaseFragment {
	private TextView tv_null_coupon;
	private ListView lv_coupon;
	private ArrayList<CouponTypeEntity> list = new ArrayList<CouponTypeEntity>();
	private BaseApplication baseApplication;
	
	@Override
	public View setView_parent(LayoutInflater inflater) {
		view_parent = inflater.inflate(R.layout.coupon_list_fragment, null);
		return view_parent;
	}

	@Override
	public void setListener() {
		

	}
	@Override
	public void onResume() {
		myHandler.sendEmptyMessage(300);
		super.onResume();
	}
	/*protected void onFragmentResume() {
		// TODO Auto-generated method stub
		super.onFragmentResume();
		myHandler.sendEmptyMessage(300);
	}*/
	
	@Override
	public void initview() {
		tv_null_coupon = (TextView) view_parent.findViewById(R.id.tv_null_coupon);
		ll_null_coupon = (LinearLayout) view_parent.findViewById(R.id.ll_null_coupon);
		baseApplication = (BaseApplication) activity.getApplication();
		//adapter = new MyAdapter();
		adapter = new CouponAdapter(list, activity);
		lv_coupon = (ListView) view_parent.findViewById(R.id.lv_coupon);
		//myHandler.sendEmptyMessage(300);
		lv_coupon.setAdapter(adapter);
		
	}


	private Handler myHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 300:
				getUserCouponList();
				break;
			default:
				break;
			}
		}


	};
	private CouponAdapter adapter;
	private LinearLayout ll_null_coupon;

	
	private void getUserCouponList() {
		BaseRequestClient client = new BaseRequestClient(activity);
		UserResult userResult = baseApplication.getUserResult();
		BaseRequest request = new BaseRequest();
		progressDialog.setMessage("优惠劵获取中...");
		showProgress(true);
		client.httpPostByJson("PhoneGetUserCouponList", userResult, request, MyCouponListResult.class, new BaseRequestClient.RequestClientCallBack<MyCouponListResult>() {

			@Override
			public void callBack(MyCouponListResult result) {
				if(result!=null){
					if(result.getCode()==BaseResult.CodeState.Success.getCode()){
						
						if(result.getCouponList()==null||result.getCouponList().size()==0){
							lv_coupon.setVisibility(View.GONE);
							ll_null_coupon.setVisibility(View.VISIBLE);
							tv_null_coupon.setText("您还没有领取优惠劵!");
						}else{
							list.clear();
							adapter.notifyDataSetChanged();
							ArrayList<CouponTypeEntity> list2 = result.getCouponList();
							ArrayList<CouponTypeEntity> list3 = new ArrayList<CouponTypeEntity>();
							for (CouponTypeEntity couponTypeEntity : list2) {
								if (Long.parseLong(couponTypeEntity.getCouponType().getUseEndTime())>Long.parseLong(result.getTimestamp())) {
									list3.add(couponTypeEntity);
								}
							}
							list.addAll(list3);
							adapter.notifyDataSetChanged();
						}

					}else{
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				showProgress(false);
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void logOut(boolean isLogOut,MyCouponListResult result) {FinalDb finalDb=FinalDb.create(activity);
			List<UserResult> findAllByWhere = finalDb.findAllByWhere(
					UserResult.class, "isLogin=1");
			for (UserResult userResult : findAllByWhere) {
				userResult.setLogin(false);
				finalDb.update(userResult);
			}
			baseApplication.setUserResult(null);
			Intent intent = new Intent(activity, LoginActivity.class);
			intent.putExtra("isLoginOut", true);
			startActivity(intent);}
		});
	}
	
	/*private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView==null){
				holder =new ViewHolder();
				convertView = View.inflate(activity, R.layout.my_coupon_item_layout, null);
				holder.tv_claim_coupon = (TextView) convertView.findViewById(R.id.tv_claim_coupon);
				
				holder.tv_claim_coupon.setVisibility(View.GONE);
				
				holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
				holder.tv_order_amountlower = (TextView) convertView.findViewById(R.id.tv_order_amountlower);
				holder.tv_UseStartTime = (TextView) convertView.findViewById(R.id.tv_UseStartTime);
				holder.tv_UseEndTime = (TextView) convertView.findViewById(R.id.tv_UseEndTime);
				
				convertView.setTag(holder);
				
			}else{
				
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.tv_money.setText(list.get(position).getCouponType().getMoney()+"");
			holder.tv_order_amountlower.setText("消费满"+list.get(position).getCouponType().getOrderAmountLower()+"元使用(不含促销商品)");
			 
			holder.tv_UseStartTime.setText(sm.format(new Date(Long.parseLong(list.get(position).getCouponType().getUseStartTime()))));
			holder.tv_UseEndTime.setText(sm.format(new Date(Long.parseLong(list.get(position).getCouponType().getUseEndTime()))));
			return convertView;
		}
		
	}*/
	
	/*SimpleDateFormat sm=new SimpleDateFormat("yyyy.MM.dd");*/
/*	class ViewHolder{

		TextView tv_money;
		TextView tv_order_amountlower;
		TextView tv_UseStartTime;  //使用开始时间
		TextView tv_UseEndTime;  //使用过期时间
		
		TextView tv_claim_coupon;
		
	}*/
	

}
