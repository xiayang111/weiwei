package com.dongwukj.weiwei.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.ClaimCouponRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.CouponListEntity;
import com.dongwukj.weiwei.idea.result.CouponListResult;
import com.dongwukj.weiwei.idea.result.LimitProducts;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

public class Coupon_ReadyFragment extends BaseFragment {
	private LinearLayout ll_null_coupon;
	private ListView lv_coupon;
	private ArrayList<CouponListEntity> list = new ArrayList<CouponListEntity>();
	private MyAdapter adapter;
	private TextView tv_null_coupon;
	private BaseApplication baseApplication;
	
	@Override
	public View setView_parent(LayoutInflater inflater) {
		view_parent = inflater.inflate(R.layout.coupon_list_fragment, null);
		return view_parent;
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}
	@Override
	public void onResume() {
		myHandler.sendEmptyMessage(100);
		super.onResume();
	}
	/*protected void onFragmentResume() {
		// TODO Auto-generated method stub
		super.onFragmentResume();
		myHandler.sendEmptyMessage(100);
	}*/
	
	@Override
	public void initview() {
		ll_null_coupon = (LinearLayout) view_parent.findViewById(R.id.ll_null_coupon);
		tv_null_coupon = (TextView) view_parent.findViewById(R.id.tv_null_coupon);
		baseApplication = (BaseApplication) activity.getApplication();
		adapter = new MyAdapter();
		//adapter = new CouponAdapter(list,activity);
		lv_coupon = (ListView) view_parent.findViewById(R.id.lv_coupon);
		lv_coupon.setAdapter(adapter);
		//myHandler.sendEmptyMessage(100);
		
		lv_coupon.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				int couponTypeId = list.get(position).getCouponTypeId();
				claimCoupon(couponTypeId);
			}
		});
		

	}
	
	private Handler myHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 100:
				getCouponList();  //获取待领取的优惠券列表
				break;
			case 200:				//领取优惠券
				int id=(Integer) msg.obj;
				claimCoupon(id);
				break;
			default:
				break;
			}
		}


	};

	private void getCouponList() {
		BaseRequestClient client = new BaseRequestClient(activity);
		UserResult userResult = baseApplication.getUserResult();
		BaseRequest request = new BaseRequest();
		progressDialog.setMessage("优惠劵获取中...");
		showProgress(true);
		client.httpPostByJson("PhoneGetCouponList", userResult, request, CouponListResult.class, new BaseRequestClient.RequestClientCallBack<CouponListResult>() {

			

			@Override
			public void callBack(CouponListResult result) {
				if(result!=null){
					if(result.getCode()==BaseResult.CodeState.Success.getCode()){
						if(result.getCouponList()==null||result.getCouponList().size()==0){
							tv_null_coupon.setText("没有可领取优惠劵!");
							lv_coupon.setVisibility(View.GONE);
							ll_null_coupon.setVisibility(View.VISIBLE);
						}else {
							list = result.getCouponList();
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
			public void logOut(boolean isLogOut,CouponListResult result) {FinalDb finalDb=FinalDb.create(activity);
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
		
	};
	
	private void claimCoupon(int couponTypeId) {
		BaseRequestClient client = new BaseRequestClient(activity);
		UserResult userResult = baseApplication.getUserResult();
		ClaimCouponRequest request = new ClaimCouponRequest();
		request.setCouponTypeId(couponTypeId);
		progressDialog.setMessage("领取中。。。");
		showProgress(true);
		String ipAddress = NetworkUtil.getLocalIpAddress();//获取用户ip
		request.setPullIP(ipAddress);
		
		client.httpPostByJson("PhoneClaimCoupon", userResult, request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>(){

			@Override
			public void callBack(BaseResult result) {
				if(result!=null){
					if(result.getCode()==BaseResult.CodeState.Success.getCode()){
						Toast.makeText(activity, "优惠券领取成功!请到我的优惠券查看", Toast.LENGTH_SHORT).show();
						getCouponList();
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
				
			}
			@Override
			public void logOut(boolean isLogOut,BaseResult result) {
				FinalDb finalDb=FinalDb.create(activity);
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
	
	private class MyAdapter extends BaseAdapter{

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
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView==null){
				holder =new ViewHolder();
				convertView = View.inflate(activity, R.layout.my_coupon_item_layout, null);
				
				holder.tv_claim_coupon = (TextView) convertView.findViewById(R.id.tv_claim_coupon);
				holder.tv_claim_coupon .setVisibility(View.VISIBLE);
				holder.ll=(LinearLayout) convertView.findViewById(R.id.ll);
				holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
				holder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
				holder.tv_order_amountlower = (TextView) convertView.findViewById(R.id.tv_order_amountlower);
				holder.tv_UseStartTime = (TextView) convertView.findViewById(R.id.tv_UseStartTime);
				holder.tv_UseEndTime = (TextView) convertView.findViewById(R.id.tv_UseEndTime);
				
				/*
				holder.tv_claim_coupon.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						//Toast.makeText(activity, "123", 0).show();
						
						//myHandler.sendEmptyMessage(Message.o);
						Message msg=Message.obtain();
						msg.what=200;
						msg.obj=list.get(position).getCouponTypeId();
						myHandler.sendMessage(msg);
					}
				});*/
				convertView.setTag(holder);
				
			}else{
				
				holder = (ViewHolder) convertView.getTag();
			}
			CouponListEntity entity = list.get(position);
			if (TextUtils.isEmpty(entity.getLimitCateName())&&entity.getLimitProducts().size()==0) {
				holder.ll.setBackgroundResource(R.drawable.weiwei_coupon_green);
				holder.tv_order_amountlower.setText("消费满"+list.get(position).getOrderAmountLower()+"元使用(所有商品可用)");
				holder.tv_product_name.setVisibility(View.GONE);
			}else if (TextUtils.isEmpty(entity.getLimitCateName())&&entity.getLimitProducts().size()!=0) {
				holder.ll.setBackgroundResource(R.drawable.weiwei_coupon_yellow);
				holder.tv_order_amountlower.setText("消费满"+list.get(position).getOrderAmountLower()+"元使用(以下商品可用)");
				holder.tv_product_name.setVisibility(View.VISIBLE);
				StringBuffer name=new StringBuffer();
				ArrayList<LimitProducts> products = entity.getLimitProducts();
				for (LimitProducts limitProducts : products) {
					name=name.append(limitProducts.getName());
				}
				holder.tv_product_name.setText(name.toString());
			}else if (!TextUtils.isEmpty(entity.getLimitCateName())&&entity.getLimitProducts().size()==0) {
				holder.ll.setBackgroundResource(R.drawable.weiwei_coupon_red);
				holder.tv_order_amountlower.setText("消费满"+list.get(position).getOrderAmountLower()+"元使用("+entity.getLimitCateName()+"类别可用)");
				holder.tv_product_name.setVisibility(View.GONE);
			}
			
			holder.tv_money.setText(list.get(position).getMoney()+"");
			
			holder.tv_UseStartTime.setText(sm.format(new Date(Long.parseLong(list.get(position).getUseStartTime()))));
			holder.tv_UseEndTime.setText(sm.format(new Date(Long.parseLong(list.get(position).getUseEndTime()))));
			
			
			return convertView;
		}
		
	}
	SimpleDateFormat sm=new SimpleDateFormat("yyyy.MM.dd");
	class ViewHolder{
		LinearLayout ll;
		TextView tv_product_name;
		TextView tv_money;
		TextView tv_order_amountlower;
		TextView tv_UseStartTime;  //使用开始时间
		TextView tv_UseEndTime;  //使用过期时间
		TextView tv_claim_coupon;
	}
	
}
