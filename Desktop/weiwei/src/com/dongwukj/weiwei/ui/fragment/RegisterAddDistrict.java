package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.AddDistrictEntity;
import com.dongwukj.weiwei.idea.request.NewAddressRequest;
import com.dongwukj.weiwei.idea.request.PlotsRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.NewAddressResult;
import com.dongwukj.weiwei.idea.result.PlotsEntity;
import com.dongwukj.weiwei.idea.result.PlotsResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.idea.result.NewAddressResult.NewAddressEntity;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RegisterAddDistrict extends AbstractHeaderFragment {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.add_istrict, null);
		isselectpolt = activity.getIntent().getBooleanExtra("isselectpolt", false);
		return view;
	}

	@Override
	protected String setTitle() {
		if (isselectpolt) {
			return "选择小区";
		}else {
			return "我的地址";	
		}
		
	}
	private int regionId_selected=-1;
	private int plId=-1;
	private String regionName;
	private String plotName;
	@Override
	protected void findView(View v) {
		lv_plot = (ListView) v.findViewById(R.id.lv_plot);
		tv_region = (TextView) v.findViewById(R.id.tv_region);
		lv = (ListView) v.findViewById(R.id.lv);
		adapter = new MyAdapter();
		baseAdapter = new MyBaseAdapter();
		lv.setAdapter(baseAdapter);
		lv_plot.setAdapter(adapter);
		tv_title = (TextView) activity.findViewById(R.id.list_header_title);
	       
		if (isselectpolt) {
			String RegionName = activity.getIntent().getStringExtra("RegionName");
			tv_region.setText(RegionName);
			regionId_selected=activity.getIntent().getIntExtra("RegionId",-1);
			lv_plot.setVisibility(View.VISIBLE);
			regionName=RegionName;
			getPlots();
			tv_title.setText("选择小区");
		}else {
			getData();
			lv.setVisibility(View.VISIBLE);
		}
		lv.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				regionId_selected = lists.get(position).getRegionId();
				regionName = lists.get(position).getName();
				lv.setVisibility(View.GONE);
				lv_plot.setVisibility(View.VISIBLE);
				tv_region.setText(regionName);
				tv_title.setText("选择小区");
				getPlots();
			}
		});
		lv_plot.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				plotName=plots_list.get(position).getName();
				plId = plots_list.get(position).getId();
				Intent intent=new Intent();
				AddDistrictEntity entity=new AddDistrictEntity(regionId_selected, plId, regionName, plotName);
				intent.putExtra("AddDistrictEntity", entity);
				activity.setResult(Activity.RESULT_OK, intent);
				activity.finish();
			}
		});
	}
	ArrayList<PlotsEntity> plots_list = new ArrayList<PlotsEntity>();
	private List<NewAddressEntity> lists=new ArrayList<NewAddressEntity>();
	private MyAdapter adapter;
	private boolean isselectpolt;
	private MyBaseAdapter baseAdapter;
	private ListView lv_plot;
	private ListView lv;
	private TextView tv_region;
	private TextView tv_title;
	private void getData() {
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		NewAddressRequest request=new NewAddressRequest();
		progressDialog.setMessage("数据获取中...");
		showProgress(true);
		client.httpPostByJson("Phoneregions", result, request, NewAddressResult.class, new BaseRequestClient.RequestClientCallBack<NewAddressResult>() {

			@Override
			public void callBack(NewAddressResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						if (result.getRegions().size()==0) {
							Toast.makeText(activity,"暂时没有地址列表", Toast.LENGTH_SHORT).show();
							return;
						}
						lists.clear();
						lists.addAll(result.getRegions());
						baseAdapter.notifyDataSetChanged();
						
					}else {
						Toast.makeText(activity,result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				showProgress(false);
			}

			@Override
			public void loading(long count, long current) {
				
			}
			@Override
			public void logOut(boolean isLogOut,NewAddressResult result) {
				
				
				showProgress(true);
				/*
				
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
			startActivity(intent);*/}
		});
		
	}
	private class MyBaseAdapter extends BaseAdapter{

	private TextView tv;

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view = (LinearLayout)View.inflate(activity, R.layout.pop_item, null);
		tv = (TextView) view.findViewById(R.id.tv);
		ImageView image_more = (ImageView) view.findViewById(R.id.image_more);
		image_more.setVisibility(View.VISIBLE);
		tv.setText(lists.get(position).getName());
		return view;
	}
	
}
	private void getPlots() {
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		PlotsRequest request = new PlotsRequest();
		request.setRegionId(regionId_selected); //目前固定写洪山区 id 1902
		progressDialog.setMessage("数据获取中...");
		showProgress(true);
		client.httpPostByJson("Phoneplots", result, request, PlotsResult.class, new BaseRequestClient.RequestClientCallBack<PlotsResult>() {

			@Override
			public void callBack(PlotsResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						if (result.getPlots().size()==0) {
							Toast.makeText(activity,"暂时没有小区列表", Toast.LENGTH_SHORT).show();
							return;
						}
						plots_list.clear();
						plots_list.addAll(result.getPlots());
						adapter.notifyDataSetChanged();
						
					}else {
						Toast.makeText(activity,result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				showProgress(false);
			}

			@Override
			public void loading(long count, long current) {}
			
			@Override
			public void logOut(boolean isLogOut, PlotsResult result) {
				showProgress(false);
			}
		});
		}
	private class MyAdapter extends BaseAdapter{

	private TextView tv;

	@Override
	public int getCount() {
		return plots_list.size();
	}

	@Override
	public Object getItem(int position) {
		return plots_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view = (LinearLayout)View.inflate(activity, R.layout.pop_item, null);
		tv = (TextView) view.findViewById(R.id.tv);
		tv.setText(plots_list.get(position).getName());
		return view;
	}
	
}
}
