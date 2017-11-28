package com.dongwukj.weiwei.ui.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.DeleteAddressRequest;
import com.dongwukj.weiwei.idea.request.NewAddressRequest;
import com.dongwukj.weiwei.idea.request.PlotsRequest;
import com.dongwukj.weiwei.idea.request.UpdateAddressRequest;
import com.dongwukj.weiwei.idea.result.AddressEntity;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.LockerResult;
import com.dongwukj.weiwei.idea.result.NewAddressResult;
import com.dongwukj.weiwei.idea.result.NewAddressResult.NewAddressEntity;
import com.dongwukj.weiwei.idea.result.PlotsEntity;
import com.dongwukj.weiwei.idea.result.PlotsResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;

import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OldNewAddressFragment extends AbstractHeaderFragment implements OnClickListener {
	private PopupWindow pw;
	private TextView te;
	private List<NewAddressEntity> lists=new ArrayList<NewAddressEntity>();
	private MyBaseAdapter adapter;
	private MyAdapter myAdapter;
	private AddressEntity entity_update;
	private EditText et_name;
	private EditText et_phone;
	private TextView tv_add;
	private EditText et_address;
	private String name;
	private String mobile;
	private String region;
	private String address;
	private int regionId_selected=-1;//区域id

	private CheckBox addressSetDefaultCheckBox;

	private boolean isDelete;
	private TextView tv_district;
	private TextView tv_plots;
	private TextView tv_locker;
	ListView contentView = null;
	private int plId=-1;		//请求小区列表,返回的小区id号码,作为请求快递柜的参数使用
	private int ctrnId;			//柜子id
	
	

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_newaddress, null);
		entity_update = (AddressEntity) activity.getIntent().getSerializableExtra("AddressEntity");
		if (entity_update!=null) {
			plId=entity_update.getPlotId();
			ctrnId=entity_update.getCtrnId();
			regionId_selected=entity_update.getRegionId();
		}
		contentView=new ListView(activity);
		return view;
	}

	@Override
	protected String setTitle() {
		String title;
		if (entity_update==null) {
			 title = activity.getResources().getString(R.string.newaddress_title);
            tv_add.setText("保存");
		}else {
			title = activity.getResources().getString(R.string.updateaddress_title);
			setRightButtonClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

                    checkSubmit();



				}

				
			}, "",R.drawable.weiwei_address_ok);
            tv_add.setText("删除地址");
		}
		
		return title;
	}

	/*protected void deleteAddress() {
		DeleteAddressRequest request=new DeleteAddressRequest();
		request.setSaIdList(new int[]{entity_update.getSaId()});

        MyWeiWeiRequestClient addressRequestClient=new MyWeiWeiRequestClient(activity,baseApplication);
        addressRequestClient.addAddress(request,new MyWeiWeiRequestClient.AddressRequestCallback(){
            @Override
            protected void add(BaseResult result) {
            	if (getActivity()==null) {
					return;
				}
                activity.setResult(Activity.RESULT_OK);
                activity.finish();
            }
        });

		
	}*/
	//private boolean isPhone;
	private boolean ismoblie;
	
	private boolean isPhoneOrmoblle(String phone_num){
		
		 Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
         Matcher matcher = p.matcher(phone_num);
         ismoblie = matcher.matches();
         return ismoblie;
        /* Pattern p1 = Pattern.compile("0\\d{2,3}-\\d{7,8}");
         Matcher matcher1 = p1.matcher(phone_num);
         isPhone = matcher1.matches();*/
        /* if (!isPhone&&!ismoblie) {
			return false;
		}else {
			return true;
		}*/
	}
	private void checkSubmit() {
		name = et_name.getText().toString().trim();
        mobile = et_phone.getText().toString().trim();
        region = tv_district.getText().toString().trim();
        plots = tv_plots.getText().toString().trim();
        locker = tv_locker.getText().toString().trim();
        address = et_address.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            remind("请输入收货人姓名");
            return;
        }else if (TextUtils.isEmpty(mobile)){
        	 remind("请输入收货人电话号码");
            return;
        }else if (TextUtils.isEmpty(address)){
            remind("请输入详细地址");
            return;
        }else if (!isPhoneOrmoblle(mobile)) {
        	 remind("请输入正确的手机号码");
             return;
		}
        
        if (entity_update!=null) {
            updateAddress();
        }else {
            addAddress();
        }
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void findView(View v) {
		//te=(TextView) v.findViewById(R.id.te);
		
		/*
		 * 测量控件的宽高
		 */
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		te.measure(w, h);
		int width = te.getMeasuredWidth();
		int height = te.getMeasuredHeight();
		
		contentView.setScrollbarFadingEnabled(false);
		contentView.setVerticalScrollBarEnabled(false);
		pw=new PopupWindow(contentView, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		//pw=new PopupWindow(contentView, width*2,LayoutParams.WRAP_CONTENT);
		pw.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.bg));
		//pw.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.pingjia));
		pw.setFocusable(true);
		scrollview_setting = (ScrollView) v.findViewById(R.id.scrollview_setting);
		rl_setting_content = (RelativeLayout) v.findViewById(R.id.rl_setting_content);
		list_header_rightbutton = (TextView) activity.findViewById(R.id.list_header_rightbutton);
		
		
		//获取设置默认地址控件
		addressSetDefaultCheckBox=(CheckBox)v.findViewById(R.id.addressSetDefaultCheckBox);
		addressSetDefaultCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(addressSetDefaultCheckBox.isChecked()){
					list_header_rightbutton.setBackgroundResource(R.drawable.weiwei_address_ok_green);
				}else{
					list_header_rightbutton.setBackgroundResource(R.drawable.weiwei_address_ok);
				}
				
			}
			
		});
		//实例化下拉控件,并设置点击监听
		tv_district = (TextView) v.findViewById(R.id.tv_district);
		tv_district.setOnClickListener(this);
		
		
		tv_plots = (TextView) v.findViewById(R.id.tv_plots);
		tv_plots.setOnClickListener(this);
		
		
		tv_locker = (TextView) v.findViewById(R.id.tv_locker);
		tv_locker.setOnClickListener(this);
		
		
		et_name = (EditText) v.findViewById(R.id.et_name);
		et_phone = (EditText) v.findViewById(R.id.et_phone);
		tv_add = (TextView) v.findViewById(R.id.tv_add);
		et_address = (EditText) v.findViewById(R.id.et_address);
		tv_add.setOnClickListener(this);
		if (entity_update!=null) {    //修改地址
			et_name.setText(entity_update.getConsignee());
			if (TextUtils.isEmpty(entity_update.getMobile())) {
				et_phone.setText(entity_update.getPhone());
			}else {
				et_phone.setText(entity_update.getMobile());
			}
			
			//te.setVisibility(View.GONE);
			//te.setText(entity_update.getRegion());
			
			tv_district.setText(entity_update.getRegion().substring(6));
			tv_plots.setText(entity_update.getPlotName());
			tv_locker.setText(entity_update.getCtrnName());
			
			et_address.setText(entity_update.getAddress());
			if(entity_update.getIsDefault()==1){
				addressSetDefaultCheckBox.setChecked(true);
			}
		}
		te.setOnClickListener(this);
		ViewTreeObserver viewTreeObserver = scrollview_setting
				.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				if (!hasMeasure) {
					int scroll_Height = scrollview_setting.getMeasuredHeight();
					int rl_Height = rl_setting_content.getMeasuredHeight();

					if (scroll_Height > rl_Height) {
						ViewGroup.LayoutParams layoutParams = rl_setting_content
								.getLayoutParams();
						layoutParams.height = scroll_Height;
						rl_setting_content.setLayoutParams(layoutParams);
						// rl_setting_content.invalidate();
						ViewCompat
								.postInvalidateOnAnimation(scrollview_setting);
						rl_setting_content.postInvalidate();
					}
					hasMeasure = true;
				}
				return true;
			}
		});
		//getData();
	}
	private boolean hasMeasure=false;
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
						lists.addAll(result.getRegions());
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
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void logOut(boolean isLogOut,NewAddressResult result) {
				if(isLogOut){
					FinalDb finalDb = FinalDb.create(activity);
					List<UserResult> findAllByWhere = finalDb.findAllByWhere(
							UserResult.class, "isLogin=1");
					for (UserResult userResult : findAllByWhere) {
						userResult.setLogin(false);
						finalDb.update(userResult);
					}
					// finalDb.deleteAll(UserResult.class);
					baseApplication.setUserResult(null);
					baseApplication.setCartCount(0);
					Toast.makeText(activity,"您的账号已经在其他地方登录！",Toast.LENGTH_SHORT).show();
				}
				showProgress(false);
			}
		});
		
	}
private class MyBaseAdapter extends BaseAdapter{

	private TextView tv;

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
		LinearLayout view = (LinearLayout)View.inflate(activity, R.layout.pop_item, null);
		tv = (TextView) view.findViewById(R.id.tv);
		tv.setText(lists.get(position).getName());
		return view;
	}
	
}

private class MyAdapter extends BaseAdapter{

	private TextView tv;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return plots_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return plots_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
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
@Override
public void onClick(View v) {
	switch (v.getId()) {
	/*case R.id.te:

		ListView contentView =  new ListView(activity);
		contentView.setScrollbarFadingEnabled(false);
		contentView.setVerticalScrollBarEnabled(false);
		adapter=new MyBaseAdapter();
		contentView.setAdapter(adapter);
		pw=new PopupWindow(contentView, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		pw.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.bg));
		pw.setFocusable(true);
		pw.showAsDropDown(te);
		contentView.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				te.setText(lists.get(position).getName());
				regionId_selected = lists.get(position).getRegionId();
				pw.dismiss();
			}
		});
	
		break;
	*/
	case R.id.tv_district://新增区域
		lists.clear();
		adapter=new MyBaseAdapter();
		contentView.setAdapter(adapter);
		getData();
		
		pw.showAsDropDown(tv_district);
		contentView.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				tv_district.setText(lists.get(position).getName());
				regionId_selected = lists.get(position).getRegionId();
				tv_plots.setText("选择小区");
				tv_locker.setText("选择柜子");
				ctrnId=-1;
				plId=-1;
				pw.dismiss();
			}
		});
		ll_locker = (LinearLayout) activity.findViewById(R.id.ll_locker);
		ll_locker.setVisibility(View.VISIBLE);
		break;
	
	case R.id.tv_plots://小区 点击监听
		plots_list.clear();
		
		
		if (regionId_selected==-1) {
			Toast.makeText(activity, "请先选择区域",Toast.LENGTH_SHORT).show();
			return;
		}
		getPlots();
		pw.dismiss();
		myAdapter = new MyAdapter();
		contentView.setAdapter(myAdapter); //给listview设置适配器
		pw.showAsDropDown(tv_plots);	   //让PopupWindow 在tv_plots下面显示
		contentView.setOnItemClickListener(new OnItemClickListener() {

			

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				tv_plots.setText(plots_list.get(position).getName());
				plId = plots_list.get(position).getId();
				ctrnId=-1;
				tv_locker.setText("选择柜子");
				pw.dismiss();
			}
		});

		break;
	
	case R.id.tv_locker:  //快递柜点击监听
		plots_list.clear();
		if (myAdapter==null) {
			myAdapter = new MyAdapter();
			contentView.setAdapter(myAdapter);
		}
		if (regionId_selected==-1) {
			Toast.makeText(activity, "请先选择区域",Toast.LENGTH_SHORT).show();
			return;
		}else if (plId==-1) {
			Toast.makeText(activity, "请先选择小区",Toast.LENGTH_SHORT).show();
			return;
		}else {
			getLocker();
			pw.dismiss(); 
			
			pw.showAsDropDown(tv_locker);
			contentView.setOnItemClickListener(new OnItemClickListener() {

				

				

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					tv_locker.setText(plots_list.get(position).getName());
					ctrnId = plots_list.get(position).getId();
					pw.dismiss();
				}
			});
		}
		
		break;
	
	   case R.id.tv_add:
		if (entity_update==null) {
			checkSubmit();
		}else{
			//showDialog();
			//deleteAddress();
			showIsDeleteDialog();
		}
		
        
		break;

	default:
		break;
	}
}

	/*
	 * 获取小区快递柜列表
	 */
	private void getLocker() {
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		PlotsRequest request = new PlotsRequest();
		request.setPlId(plId);
		progressDialog.setMessage("数据获取中...");
		showProgress(true);
		client.httpPostByJson("Phonectrns", result, request, LockerResult.class, new BaseRequestClient.RequestClientCallBack<LockerResult>() {

			@Override
			public void callBack(LockerResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						if (result.getCtrns().size()==0) {
							Toast.makeText(activity,"暂时没有快递柜列表", Toast.LENGTH_SHORT).show();
							return;
						}
						plots_list.addAll(result.getCtrns());
						myAdapter.notifyDataSetChanged();
						
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
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void logOut(boolean isLogOut, LockerResult result) {
				// TODO Auto-generated method stub
				
			}
		});
}

	/*
	 * 获取小区地址列表
	 */
	ArrayList<PlotsEntity> plots_list = new ArrayList<PlotsEntity>();
	
	private LinearLayout ll_locker;
	
	private String plots;
	private String locker;
	private TextView list_header_rightbutton;
	private ScrollView scrollview_setting;
	private RelativeLayout rl_setting_content;

	private void getPlots() {
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		PlotsRequest request = new PlotsRequest();
		request.setRegionId(1902); //目前固定写洪山区 id 1902
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
						plots_list.addAll(result.getPlots());
						myAdapter.notifyDataSetChanged();
						
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
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void logOut(boolean isLogOut, PlotsResult result) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	
}

	/*protected void showDialog(){
		final Dialog dialog = new Dialog(activity, R.style.Dialog);
		LinearLayout view = (LinearLayout) View.inflate(activity, R.layout.is_delete_address_dialog, null);
		dialog.setContentView(view);
		dialog.setCancelable(false); //为false 按返回键不能 dismiss Dialog
		view.findViewById(R.id.tv_ok).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deleteAddress();
			}
		});
		
		view.findViewById(R.id.tv_cancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		    WindowManager m = activity.getWindowManager();
	        Window dialogWindow = dialog.getWindow();
	        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
	        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
	        p.height = (int) (d.getHeight() * 0.18); // 高度设置为屏幕的0.6
	        p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕的0.65
	        dialogWindow.setAttributes(p);
		
		dialog.show();
		
	}*/
	
	protected void showIsDeleteDialog(){
		showDialogs(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.tv_ok:
					//deleteAddress();
					dialog.dismiss();
					break;
				case R.id.tv_cancel:
					dialog.dismiss();
					break;
				default:
					break;
				}
				
			}
		},"是/否删除当前地址?");
	}

	private void remind(String string) {
	Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();
}

	private void addAddress() {
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		UpdateAddressRequest request=new UpdateAddressRequest();
		request.setAddress(address);
		request.setConsignee(name);
		if (ismoblie) {
			request.setMobile(mobile);
			
		}else {
			request.setPhone(mobile);
		}
		//request.setRegion(region); 接口更改,不需要请求此字段
		request.setRegionId(regionId_selected);
		request.setPlotId(plId);
		request.setCtrnId(ctrnId);
		//设置默认地址
		if(addressSetDefaultCheckBox!=null &&addressSetDefaultCheckBox.isChecked()){
			request.setIsDefault(1);
		}else{

		}
		progressDialog.setMessage("保存中...");
		showProgress(true);
		client.httpPostByJson("Phoneaddressadd", result, request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

			@Override
			public void callBack(BaseResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						activity.setResult(Activity.RESULT_OK);
						activity.finish();
					}else {
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();

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
			public void logOut(boolean isLogOut,BaseResult result) {
				if(isLogOut){
					FinalDb finalDb = FinalDb.create(activity);
					List<UserResult> findAllByWhere = finalDb.findAllByWhere(
							UserResult.class, "isLogin=1");
					for (UserResult userResult : findAllByWhere) {
						userResult.setLogin(false);
						finalDb.update(userResult);
					}
					// finalDb.deleteAll(UserResult.class);
					baseApplication.setUserResult(null);
					baseApplication.setCartCount(0);
					Toast.makeText(activity,"您的账号已经在其他地方登录！",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void updateAddress() {
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		UpdateAddressRequest request=new UpdateAddressRequest();
		request.setAddress(address);
		request.setAlias(entity_update.getAlias());
		request.setConsignee(name);
		request.setEmail(entity_update.getEmail());
		request.setIsDefault(entity_update.getIsDefault());
		request.setLockersId(entity_update.isLockersId());
//		request.setMobile(mobile);
//		request.setPhone(entity_update.getPhone());
		if (ismoblie) {
			request.setMobile(mobile);
			request.setPhone("");
		}else {
			request.setPhone(mobile);
			request.setMobile("");
		}
		request.setRegion(region);
		request.setRegionId(regionId_selected);
		request.setSaId(entity_update.getSaId());
		request.setZipCode(entity_update.getZipCode());
		
		request.setPlotId(plId);
		request.setCtrnId(ctrnId);
		//设置默认地址
		if(addressSetDefaultCheckBox!=null &&addressSetDefaultCheckBox.isChecked()){
			request.setIsDefault(1);
		}else{
			request.setIsDefault(0);
		}
		client.httpPostByJson("Phoneaddressmodify", result, request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

			@Override
			public void callBack(BaseResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						activity.setResult(Activity.RESULT_OK);
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
				
				
			}
			@Override
			public void logOut(boolean isLogOut,BaseResult result) {
				if(isLogOut){
					FinalDb finalDb = FinalDb.create(activity);
					List<UserResult> findAllByWhere = finalDb.findAllByWhere(
							UserResult.class, "isLogin=1");
					for (UserResult userResult : findAllByWhere) {
						userResult.setLogin(false);
						finalDb.update(userResult);
					}
					// finalDb.deleteAll(UserResult.class);
					baseApplication.setUserResult(null);
					baseApplication.setCartCount(0);
					Toast.makeText(activity,"您的账号已经在其他地方登录！",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
