package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.PhoneGetSincesRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.DeliverEntity;
import com.dongwukj.weiwei.idea.result.DeliveryTimeObject;
import com.dongwukj.weiwei.idea.result.PhoneGetDeliveryTimeModelResult;
import com.dongwukj.weiwei.idea.result.PhoneGetSincesEntity;
import com.dongwukj.weiwei.idea.result.PhoneGetSincesResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.idea.result.sinceMealtimesEntity;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SendTypeFragment extends AbstractHeaderFragment {

	

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sendtypefragment, null);
		return view;
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "送货时间";
	}

	@Override
	protected void findView(View v) {
		type = getActivity().getIntent().getIntExtra("sendType", 0);
		tv_type = (TextView) v.findViewById(R.id.tv_type);
		if (type==0) {
			tv_type.setText("请选择配送时间");
			PhoneGetDeliveryTimeModel();
		}else {
			getPhoneGetSinces(getActivity().getIntent().getIntExtra("plotId", 0));
			tv_type.setText("请选择自提点");
		}
		ListView lv=(ListView) v.findViewById(R.id.lv);
		myadapter = new Myadapter();
		
		lv.setAdapter(myadapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (type==0) {
					Intent intent=new Intent();
					DeliverEntity entity=new DeliverEntity();
					entity.setDeliverDate(DeliveryTimeObjects.get(arg2).getDeliveryDate());
					entity.setMealtimes(DeliveryTimeObjects.get(arg2).getMealtimes());
					entity.setDeliverTime(DeliveryTimeObjects.get(arg2).getDisplaytext());
					intent.putExtra("DeliverEntity", entity);
					activity.setResult(activity.RESULT_OK, intent);
					activity.finish();
					//Toast.makeText(activity, DeliveryTimeObjects.get(arg2).getDeliveryDate()+DeliveryTimeObjects.get(arg2).getDisplaytext(), Toast.LENGTH_SHORT).show();
				}else if(type==1&&!isShowTime){
					isShowTime=true;
					sincefee=sinceList.get(arg2).getSincefee();
					sinceMealtimes = sinceList.get(arg2).getSinceMealtimes();
					sinceid=sinceList.get(arg2).getSid();
					name=sinceList.get(arg2).getName();
					myadapter.notifyDataSetChanged();
				}else if (type==1&&isShowTime) {
					Intent intent=new Intent();
					DeliverEntity entity=new DeliverEntity();
					entity.setSincefee(sincefee);
					entity.setDeliverDate(sinceMealtimes.get(arg2).getDate());
					entity.setMealtimes(sinceMealtimes.get(arg2).getMealtimes());
					entity.setDeliverTime(sinceMealtimes.get(arg2).getSinceMealtimesFormatString());
					entity.setSinceid(sinceid);
					entity.setName(name);
					intent.putExtra("DeliverEntity", entity);
					activity.setResult(activity.RESULT_OK, intent);
					activity.finish();
					//Toast.makeText(activity, sinceid+sinceMealtimes.get(arg2).getDate()+sinceMealtimes.get(arg2).getSinceMealtimesFormatString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	private String name;
	private int sinceid;
	private float sincefee;
	private ArrayList<DeliveryTimeObject> DeliveryTimeObjects;
	private ArrayList<PhoneGetSincesEntity> sinceList;
	private ArrayList<sinceMealtimesEntity> sinceMealtimes;
	private Myadapter myadapter;
	private TextView tv_type;
	private int type;
	private boolean isShowTime;
	private void PhoneGetDeliveryTimeModel() {
		BaseRequestClient client=new BaseRequestClient(activity);
		client.httpPostByJsonNew("PhoneGetDeliveryTimeModel", baseApplication.getUserResult(), new BaseRequest(), PhoneGetDeliveryTimeModelResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneGetDeliveryTimeModelResult>() {

			@Override
			public void callBack(PhoneGetDeliveryTimeModelResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
					 DeliveryTimeObjects = result.getDeliveryTimeObjects();
					 myadapter.notifyDataSetChanged();
					}else {
						showtoast(result.getMessage(), activity);
					}
				}else {
					showtoast(getResources().getString(R.string.data_error), activity);
				}
			}

			@Override
			public void loading(long count, long current) {
				
			}

			@Override
			public void logOut(PhoneGetDeliveryTimeModelResult result) {
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

	private void getPhoneGetSinces(int plotId) {
		BaseRequestClient client=new BaseRequestClient(activity);
		PhoneGetSincesRequest request=new PhoneGetSincesRequest();
		request.setPlotId(plotId);
		client.httpPostByJsonNew("PhoneGetSinces", baseApplication.getUserResult(), request, PhoneGetSincesResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneGetSincesResult>() {

			@Override
			public void callBack(PhoneGetSincesResult result) {

				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
					  sinceList = result.getSinceList();
					 myadapter.notifyDataSetChanged();
					}else {
						showtoast(result.getMessage(), activity);
					}
				}else {
					showtoast(getResources().getString(R.string.data_error), activity);
				}
			
			}

			@Override
			public void loading(long count, long current) {
				
			}

			@Override
			public void logOut(PhoneGetSincesResult result) {
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
	
	

	class Myadapter extends BaseAdapter{
		
		
		@Override
		public int getCount() {
			int count=0;
			if (type==0&&DeliveryTimeObjects!=null) {
				count=DeliveryTimeObjects.size();
			}else if (type==1&&sinceList!=null&&!isShowTime) {
				count=sinceList.size();
			}else if (type==1&&isShowTime) {
				count=sinceMealtimes.size();
			}
			return count;
		}

		@Override
		public Object getItem(int paramInt) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int paramInt) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int paramInt, View paramView,
				ViewGroup paramViewGroup) {
			LinearLayout ll=new LinearLayout(activity);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, activity.getResources().getDisplayMetrics()));
			params.setMargins((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, activity.getResources().getDisplayMetrics()), 0, 0, 0);
			TextView tv=new TextView(activity);
			tv.setGravity(Gravity.CENTER_VERTICAL);
		//	tv.setText("1233");
			tv.setLayoutParams(params);
			tv.setTextSize(18);
			ll.addView(tv);
			if (type==0&&DeliveryTimeObjects!=null) {
				tv.setText(DeliveryTimeObjects.get(paramInt).getDeliveryDate()+DeliveryTimeObjects.get(paramInt).getDisplaytext());
			}else if(type==1&&sinceList!=null){
				if (!isShowTime) {
					tv.setText(sinceList.get(paramInt).getName());
				}else {
					tv.setText(sinceMealtimes.get(paramInt).getDate()+sinceMealtimes.get(paramInt).getSinceMealtimesFormatString());
				}
				
			}
			return ll;
		}
		
	}
}
