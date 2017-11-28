package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.NewAddressRequest;
import com.dongwukj.weiwei.idea.result.AreaEntity;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.NewAddressResult;
import com.dongwukj.weiwei.idea.result.NewAddressResult.NewAddressEntity;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

public class LoucationFragment extends AbstractHeaderFragment {

	private ListView lv_region;
	private List<NewAddressEntity> lists = new ArrayList<NewAddressEntity>();
	private TextView tv_loucation;
	private MyAdapter adapter;
	private ProgressDialog pd;
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	private AreaEntity areaEntity;
	private boolean isConn;  //判断百度地图是否定位到地址信息

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.loucation_layout, container, false);
		return view;
	}

	@Override
	protected String setTitle() {
		return activity.getResources().getString(R.string.my_loucation);
	}

	@Override
	protected void findView(View v) {
		lv_region = (ListView) v.findViewById(R.id.lv_region);
		tv_loucation = (TextView) v.findViewById(R.id.tv_loucation);
		tv_loucation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isConn){
					activity.setResult(Activity.RESULT_OK);
					activity.finish();
				}else{
					activity.finish();
				}
			}
		});
		areaEntity = AreaEntity.getInstance();
		adapter = new MyAdapter();
		lv_region.setAdapter(adapter);
		
		lv_region.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				NewAddressEntity addressentity = (NewAddressEntity) lists.get(position);
				String regions = addressentity.getName();  //条目点击获取到区域
				areaEntity.setArea(regions);
				/*
				 * BUG 270
				 */
				//将用户选择配送的区域保存到首选项
				SharedPreferences sp = activity.getSharedPreferences("Enable", Activity.MODE_PRIVATE);
				Editor edit = sp.edit();
				edit.putBoolean("isAddress", true);
				edit.commit();
				activity.setResult(Activity.RESULT_OK);
				activity.finish();
			}
		});
		getAreaData(); 
		location();
	}
	
	
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lists.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return lists.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if(convertView==null){
				holder = new ViewHolder();
				convertView = View.inflate(activity, R.layout.loucation_item_layout, null);
				holder.tv_region = (TextView) convertView.findViewById(R.id.tv_region);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_region.setText(lists.get(position).getName());
			
			return convertView;
		}
		
	}
	
	class ViewHolder{
		TextView tv_region;
	}

	private void getAreaData() {
		pd = new ProgressDialog(activity);
		pd.setTitle("提示");
		pd.setMessage("加载中...");
		pd.show();
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		NewAddressRequest request=new NewAddressRequest();
		client.httpPostByJson("Phoneregions", result, request, NewAddressResult.class, new BaseRequestClient.RequestClientCallBack<NewAddressResult>() {

			@Override
			public void callBack(NewAddressResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						if (result.getRegions().size()==0) {
							Toast.makeText(activity,"暂时没有地址列表", Toast.LENGTH_SHORT).show();
							return;
						}
						lists.addAll(result.getRegions());
						adapter.notifyDataSetChanged();
					}else {
						Toast.makeText(activity,result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				pd.dismiss();
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void logOut(boolean isLogOut,NewAddressResult result) {FinalDb finalDb=FinalDb.create(activity);
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
	
	private void location() {
    	mLocationClient = new LocationClient(baseApplication.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		initLocation();
		mLocationClient.start();
		
	}
	
	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
		option.setCoorType("gcj02");	//返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(1000);		//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {



		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			String province = location.getProvince();
			String city = location.getCity();
			String district = location.getDistrict();
			
			String street = location.getStreet();
			String streetNumber = location.getStreetNumber();
			

			if(TextUtils.isEmpty(province)||TextUtils.isEmpty(city)||TextUtils.isEmpty(district)||TextUtils.isEmpty(street)||TextUtils.isEmpty(streetNumber)){
				tv_loucation.setText("无法定位到位置,请检查网络设置!");
				isConn = false;
			}else{
				//如果不为空,把定位到的数据存入实体类中
				areaEntity.setProvince(province);
				areaEntity.setCity(city);
				areaEntity.setArea(district);
				areaEntity.setStreet(street);
				areaEntity.setStreetnumber(streetNumber);
				tv_loucation.setText("当前位置:"+province+"-"+city+"-"+district);
				isConn = true;
			}
			
			mLocationClient.stop();
		}

	}
	
}
