package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.app.ProgressDialog;
import android.content.ComponentName;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.RechargeGridViewAdapter;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.PhoneorderpayeditstateRequest;
import com.dongwukj.weiwei.idea.request.Phonerecharge;
import com.dongwukj.weiwei.idea.result.AlipayResult;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.PaymentEntity;








import com.dongwukj.weiwei.idea.result.PhoneprepaidruleResult;
import com.dongwukj.weiwei.idea.result.PrepaidRuleEntity;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.idea.result.WeixinResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.utils.DeviceUtil;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.activity.PaymentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RechargeAffirmFragment extends AbstractHeaderFragment {
	private int[] images={R.drawable.weiwei_pay_zhifubao,R.drawable.weiwei_pay_weixin,R.drawable.weiwei_pay_weixin};
	private TextView tv_phone,tv_amount,tv_denomination,tv_payStyle,tv_SurplusMoney;
	 private ArrayList<PaymentEntity> paymentEntityArrayList;
	private PaymentListAdapter adapter;
	private PaymentEntity entity;
	private float money;
	private float total;
	private String phone;
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.recharge_affirm, null);
		return view;
	}

	@Override
	protected String setTitle() {
		
		return "充值确认";
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==200&&resultCode==-1&&data!=null) {
			 if(progressDialog!=null&&progressDialog.isShowing()){
	                progressDialog.dismiss();
	            }
			String extra = data.getStringExtra("pay_result");
			if (extra.equals("success")) {
				Toast.makeText(activity, "充值成功", Toast.LENGTH_SHORT).show();
				Phoneorderpayeditstate();
			}else if (extra.equals("cancel")) {
				//Toast.makeText(activity, "user_cancelled", Toast.LENGTH_SHORT).show();
			}else if (extra.equals("fail")) {
				Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
			}
		}
	
		
	}
	private int type;
	private String osn;
	  private void Phoneorderpayeditstate() {
	    	PhoneorderpayeditstateRequest request=new PhoneorderpayeditstateRequest();
			request.setOrdertype(1);
			request.setType(type);
			request.setOsn(osn);
			BaseRequestClient client=new BaseRequestClient(activity);
			client.httpPostByJsonNew("Phoneorderpayeditstate", baseApplication.getUserResult(), request, BaseResult.class, new BaseRequestClient.RequestClientNewCallBack<BaseResult>() {

				@Override
				public void callBack(BaseResult result) {
					if (result!=null) {
						if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
							activity.finish();
						}else {
							Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
						}
					}else {
						Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void loading(long count, long current) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void logOut(BaseResult result) {
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
					startActivity(intent);
				}
			});
		}
	  @Override
	public void onResume() {
		  if (progressDialog!=null&&progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		super.onResume();
	}
	private void Phonerecharge() {
		BaseRequestClient client=new BaseRequestClient(activity);
		Phonerecharge Phonerecharge=new Phonerecharge();
		Phonerecharge.setMobileNo(phone);
		Phonerecharge.setSurplusMoney(money);
		Phonerecharge.setOrderAmount((money+total));
		Phonerecharge.setDenomination(money);
		Phonerecharge.setIp(NetworkUtil.getLocalIpAddress());
		Phonerecharge.setPayName(2);
		Phonerecharge.setPrid(prid);
		client.httpPostByJsonNew("Phonerecharge", baseApplication.getUserResult(),Phonerecharge, AlipayResult.class, new BaseRequestClient.RequestClientNewCallBack<AlipayResult>() {

			@Override
			public void callBack(AlipayResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						type=0;
						osn=result.getOsn();
						Intent intent2=new Intent(activity, PaymentActivity.class);
						intent2.putExtra("type", 8);
						intent2.putExtra("orderInfo", result.getOrderInfotext()+"&sign="+"\""+result.getMysign()+"\""+"&sign_type=\"RSA\"");
						startActivityForResult(intent2, 200);

						//todo:切换跳转方式。提取到公共方法中。
					/*	Intent intent1 = new Intent();
						String packageName = activity.getPackageName();
						ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
						intent1.setComponent(componentName);
						intent1.putExtra("type", 8);
						intent1.putExtra("orderInfo", result.getOrderInfotext() + "&sign=" + "\"" + result.getMysign() + "\"" + "&sign_type=\"RSA\"");

						//intent1.putExtra(PaymentActivity.EXTRA_CHARGE, data);
						startActivityForResult(intent1, -1);*/


					}else {
						if (progressDialog!=null&&progressDialog.isShowing()) {
							progressDialog.dismiss();
						}
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					if (progressDialog!=null&&progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void loading(long count, long current) {
				
				
			}

			@Override
			public void logOut(AlipayResult result) {
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
				startActivity(intent);
			}
		});

	}
	private void Phonerewin() {
		BaseRequestClient client=new BaseRequestClient(activity);
		Phonerecharge Phonerecharge=new Phonerecharge();
		Phonerecharge.setMobileNo(phone);
		Phonerecharge.setSurplusMoney(money);
		Phonerecharge.setOrderAmount((money+total));
		Phonerecharge.setDenomination(money);
		Phonerecharge.setIp(NetworkUtil.getLocalIpAddress());
		Phonerecharge.setPayName(4);
		Phonerecharge.setPrid(prid);
		client.httpPostByJsonNew("Phonerecharge", baseApplication.getUserResult(),Phonerecharge, WeixinResult.class, new BaseRequestClient.RequestClientNewCallBack<WeixinResult>() {

			@Override
			public void callBack(WeixinResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
					/*	Intent intent=new Intent(activity, PaymentActivity.class);
						intent.putExtra("type", 10);
						intent.putExtra("orderInfo", result);
						startActivity(intent);*/
						type=1;
						osn=result.getOsn();
						Intent intent1 = new Intent();
						String packageName = activity.getPackageName();
						ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
						intent1.setComponent(componentName);
						intent1.putExtra("type", 10);
						intent1.putExtra("orderInfo", result);

						//intent1.putExtra(PaymentActivity.EXTRA_CHARGE, data);
						startActivityForResult(intent1, 200);

					}else {
						if (progressDialog!=null&&progressDialog.isShowing()) {
							progressDialog.dismiss();
						}
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					if (progressDialog!=null&&progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void loading(long count, long current) {
				
				
			}

			@Override
			public void logOut(WeixinResult result) {
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
				startActivity(intent);
			}
		});
		
	}
	private ProgressDialog progressDialog;
	private int prid;

	@Override
	protected void findView(View v) {
		Intent intent = activity.getIntent();
		paymentEntityArrayList=(ArrayList<PaymentEntity>) intent.getSerializableExtra("paymentEntityArrayList");
		money = intent.getFloatExtra("money", 0);
		total = intent.getFloatExtra("total", 0);
		prid = intent.getIntExtra("Prid", 0);
		phone = intent.getStringExtra("phone");
	    ListView payment_list_view=(ListView)v.findViewById(R.id.payment_list_view);
	    Button bt=(Button) v.findViewById(R.id.bt);
	    bt.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				if (entity==null) {
					Toast.makeText(activity, "请选择支付方式", Toast.LENGTH_SHORT).show();
					return;
				}else {
					progressDialog = new ProgressDialog(activity);
		            progressDialog.setMessage("请稍后，正在准备支付...");
		            progressDialog.setCancelable(false);
		            progressDialog.show();
					if (entity.getKey().equals("支付宝")) {
						Phonerecharge();
					}else {
						Phonerewin();
					}
				}
			}
		});
		tv_phone = (TextView) v.findViewById(R.id.tv_phone);
		tv_amount = (TextView) v.findViewById(R.id.tv_amount);
		tv_denomination = (TextView) v.findViewById(R.id.tv_denomination);
		tv_payStyle = (TextView) v.findViewById(R.id.tv_payStyle);
		tv_SurplusMoney = (TextView) v.findViewById(R.id.tv_SurplusMoney);
		//tv_payStyle.setText(Html.fromHtml("支付方式:<font color='red'>"+"请选择支付方式"+"</font>"));
		adapter = new PaymentListAdapter();
	    payment_list_view.setAdapter(adapter);
		tv_phone.setText(Html.fromHtml("充值号码:<font color='red'>"+phone+"</font>"));
		tv_amount.setText(Html.fromHtml("到帐金额:<font color='red'>￥"+(int)(total+money)+"</font>"));
		tv_denomination.setText(Html.fromHtml("应付金额:<font color='red'>￥"+(int)money+"</font>"));
		tv_SurplusMoney.setText(Html.fromHtml("应付金额:<font color='red'>￥"+(int)money+"</font>"));
		payment_list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				for (int i = 0; i < paymentEntityArrayList.size(); i++) {
					if (i==position) {
						entity = paymentEntityArrayList.get(i);
						entity.setIschecked(true);
						
					}else {
						PaymentEntity entity2 = paymentEntityArrayList.get(i);
						entity2.setIschecked(false);
					}
				}
				tv_payStyle.setText(Html.fromHtml("支付方式:<font color='red'>"+entity.getKey()+"</font>"));
				adapter.notifyDataSetChanged();
			}
		});
		
	}
	   private class PaymentListAdapter extends BaseAdapter{

	        @Override
	        public int getCount() {
	            return paymentEntityArrayList.size();
	        }

	        @Override
	        public Object getItem(int position) {
	            return paymentEntityArrayList.get(position);
	        }

	        @Override
	        public long getItemId(int position) {
	            return position;
	        }

	        @Override
	        public View getView(final int position, View convertView, ViewGroup parent) {
	            ViewHolder viewHolder=null;
	            if(convertView==null){
	                convertView=View.inflate(activity,R.layout.payment_list_item,null);
	                viewHolder=new ViewHolder();
	                viewHolder.imageView=(ImageView)convertView.findViewById(R.id.payment_list_item_img);
	                viewHolder.textView=(TextView)convertView.findViewById(R.id.payment_list_item_text);
	                viewHolder.checkBox=(CheckBox)convertView.findViewById(R.id.payment_list_item_check);
	                convertView.setTag(viewHolder);
	            }else{
	                viewHolder=(ViewHolder)convertView.getTag();
	            }
	            viewHolder.textView.setText(paymentEntityArrayList.get(position).getKey());

	            
	            viewHolder.imageView.setImageResource(images[position]);
	            viewHolder.checkBox.setChecked(paymentEntityArrayList.get(position).isIschecked());
	            /*viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	                @Override
	                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	                       for(Integer index:checkList.keySet()){
	                           checkList.put(index,false);
	                       }
	                    checkList.put(position,isChecked);
	                    notifyDataSetChanged();

	                }
	            });*/

	            return convertView;
	        }

	        private class ViewHolder{
	            public ImageView imageView;
	            public TextView textView;
	            public CheckBox checkBox;
	        }
	    }
}
