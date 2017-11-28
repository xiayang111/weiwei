package com.dongwukj.weiwei.ui.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.AddDistrictEntity;
import com.dongwukj.weiwei.idea.request.NewAddressRequest;
import com.dongwukj.weiwei.idea.request.PlotsRequest;
import com.dongwukj.weiwei.idea.request.UpdateAddressRequest;
import com.dongwukj.weiwei.idea.result.AddAddressResult;
import com.dongwukj.weiwei.idea.result.AddressEntity;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.LockerResult;
import com.dongwukj.weiwei.idea.result.NewAddressResult;
import com.dongwukj.weiwei.idea.result.NewAddressResult.NewAddressEntity;
import com.dongwukj.weiwei.idea.result.Plot;
import com.dongwukj.weiwei.idea.result.PlotsEntity;
import com.dongwukj.weiwei.idea.result.PlotsResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;




import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity.CancleAddAddress;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

import de.greenrobot.event.EventBus;
import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NewAddressFragment extends AbstractHeaderFragment implements OnClickListener {
	private PopupWindow pw;
	
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
	
	ListView contentView = null;
	private int plId=-1;		//请求小区列表,返回的小区id号码,作为请求快递柜的参数使用

	
	
	
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_newaddress, null);
		entity_update = (AddressEntity) activity.getIntent().getSerializableExtra("AddressEntity");
		phoneGetPlotResult = (PhoneGetPlotResult) activity.getIntent().getSerializableExtra("fromorder");
	contentView=new ListView(activity);
		return view;
	}
	
	@Override
	protected void setTitleAndLeftButton() {
		 View view1=activity.findViewById(R.id.list_header_title);
	        if(view1!=null){
	            ((TextView)view1).setText(setTitle());
	        }
	        View view2=activity.findViewById(R.id.ll_left);
	        
	        
	        if(view2!=null){
	            ((LinearLayout)view2).setOnClickListener(new View.OnClickListener() {
	                @Override
	                public void onClick(View v) {
	                	showDialogs();
	                }
	            });
	        }
	}
	
	 protected void showDialogs(){
	    	final Dialog dialog = new Dialog(activity, R.style.Dialog);
			LinearLayout view = (LinearLayout) View.inflate(activity, R.layout.is_delete_address_dialog, null);
			dialog.setContentView(view);
			dialog.setCancelable(false); //为false 按返回键不能 dismiss Dialog
			
			TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
			tv_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View paramView) {
					
					dialog.dismiss();
					activity.finish();
				}
			});
			TextView title = (TextView) view.findViewById(R.id.title);
			title.setText("是否放弃当前操作？");
			
			TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
			tv_cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View paramView) {
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
			
	    }
	    
	@Override
	protected String setTitle() {
		String title;
		String name = activity.getIntent().getStringExtra("title");
			if (!TextUtils.isEmpty(name)) {
				title=name;
			}else {
			title = activity.getResources().getString(R.string.newaddress_title);
           }
			 tv_add.setText("保存");	
		
		return title;
	}
	private boolean ismoblie;
	
	private boolean isPhoneOrmoblle(String phone_num){
		
		 Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[678])|(14[57])|(18[0-9]))\\d{8}$");
         Matcher matcher = p.matcher(phone_num);
         ismoblie = matcher.matches();
         return ismoblie;
        
	}
	
	private void checkSubmit() {
		name = et_name.getText().toString().trim();
        mobile = et_phone.getText().toString().trim();
        region = tv_district.getText().toString().trim();

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
		}else if (regionId_selected==-1) {
			 remind("请选择您所在的区域");
			 return;
		}else if (plId==-1) {
			 remind("请选择您所在的小区");
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
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	
	public void onEventMainThread(String type){	}
	
	@Override
	protected void findView(View v) {
		img1 = (ImageView) v.findViewById(R.id.img1);
		img2 = (ImageView) v.findViewById(R.id.img2);
		if (entity_update!=null) {
			plId=entity_update.getPlotId();
			regionId_selected=entity_update.getRegionId();
			img1.setVisibility(View.GONE);
			img2.setVisibility(View.GONE);
			
		}if (phoneGetPlotResult!=null) {
			img1.setVisibility(View.GONE);
			img2.setVisibility(View.GONE);
		}
		
		((HomeHeaderActivity)activity).setCancleAddAddress(new CancleAddAddress() {
			
			@Override
			public void cancleAddAddress() {
				showDialogs();
			}
		});
		contentView.setScrollbarFadingEnabled(false);
		contentView.setVerticalScrollBarEnabled(false);
		pw=new PopupWindow(contentView, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		pw.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.bg));
		pw.setFocusable(true);
		scrollview_setting = (ScrollView) v.findViewById(R.id.scrollview_setting);
		rl_setting_content = (RelativeLayout) v.findViewById(R.id.rl_setting_content);
		
		
		//获取设置默认地址控件
		addressSetDefaultCheckBox=(CheckBox)v.findViewById(R.id.addressSetDefaultCheckBox);
		addressSetDefaultCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(addressSetDefaultCheckBox.isChecked()){
					//list_header_rightbutton.setBackgroundResource(R.drawable.weiwei_address_ok_green);
				}else{
					//list_header_rightbutton.setBackgroundResource(R.drawable.weiwei_address_ok);
				}
				
			}
			
		});
		//实例化下拉控件,并设置点击监听
		tv_district = (TextView) v.findViewById(R.id.tv_district);//选择小区
		//tv_district.setTextColor(activity.getResources().getColor(R.color.red));
		tv_district.setOnClickListener(this);
		
		
		tv_plots = (TextView) v.findViewById(R.id.tv_plots);
		//tv_plots.setTextColor(activity.getResources().getColor(R.color.red));
		tv_plots.setOnClickListener(this);
		
		
		et_name = (EditText) v.findViewById(R.id.et_name);
		et_name.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					if ((TextUtils.isEmpty(et_name.getText().toString().trim())||et_name.getText().toString().trim().length()>10)){
						et_name.setBackgroundResource(R.drawable.weiwei_edittext_red_transparency);
					}else {
						et_name.setBackgroundResource(R.drawable.weiwei_edittext_gray_transparency);
					}
					
				}}
		});
		et_name.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (s.length()>0) {
					et_name.setBackgroundResource(R.drawable.weiwei_edittext_gray_transparency);
				}else {
					et_name.setBackgroundResource(R.drawable.weiwei_edittext_red_transparency);
				}
			}
		});
		et_phone = (EditText) v.findViewById(R.id.et_phone);
		et_phone.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					if (!isPhoneOrmoblle(et_phone.getText().toString().trim())) {
						et_phone.setBackgroundResource(R.drawable.weiwei_edittext_red_transparency);
					}else {
						et_phone.setBackgroundResource(R.drawable.weiwei_edittext_gray_transparency);
					}
					//et_phone.setBackgroundResource(R.drawable.weiwei_edittext_red_transparency);
				}
			}
		});
		et_phone.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (isPhoneOrmoblle(s.toString().trim())) {
					et_phone.setBackgroundResource(R.drawable.weiwei_edittext_gray_transparency);
				}else {
					et_phone.setBackgroundResource(R.drawable.weiwei_edittext_red_transparency);
				}
			}
		});
				
		
				
				
		
		tv_add = (TextView) v.findViewById(R.id.tv_add);
		et_address = (EditText) v.findViewById(R.id.et_address);
		et_address.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					if ((TextUtils.isEmpty(et_address.getText().toString().trim()))){
						et_address.setBackgroundResource(R.drawable.weiwei_edittext_red_transparency);
					}else {
						et_address.setBackgroundResource(R.drawable.weiwei_edittext_gray_transparency);
					}
					
				}}
		});
		et_address.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (s.length()>0) {
					et_address.setBackgroundResource(R.drawable.weiwei_edittext_gray_transparency);
				}else {
					et_address.setBackgroundResource(R.drawable.weiwei_edittext_red_transparency);
				}
			}
		});
		tv_add.setOnClickListener(this);
		if (entity_update!=null) {    //修改地址
			et_name.setText(entity_update.getConsignee());
			if (TextUtils.isEmpty(entity_update.getMobile())) {
				et_phone.setText(entity_update.getPhone());
			}else {
				et_phone.setText(entity_update.getMobile());
			}
			
			tv_district.setText(entity_update.getRegion().substring(6));
			//tv_district.setTextColor(activity.getResources().getColor(R.color.gray_button_text_color));
			tv_plots.setText(entity_update.getPlotName());
			//tv_plots.setTextColor(activity.getResources().getColor(R.color.gray_button_text_color));
			et_address.setText(entity_update.getAddress());
			if(entity_update.getIsDefault()==1){
				addressSetDefaultCheckBox.setChecked(true);
				addressSetDefaultCheckBox.setClickable(false);
			}
		}else if (phoneGetPlotResult!=null) {
			Plot plot = phoneGetPlotResult.getPlot();
			//tv_district.setTextColor(activity.getResources().getColor(R.color.gray_button_text_color));
			tv_district.setText(plot.getRegionName());
			//tv_plots.setTextColor(activity.getResources().getColor(R.color.gray_button_text_color));
			tv_plots.setText(plot.getName());
			plId=plot.getId();
			regionId_selected=plot.getRegionid();
		}
		//te.setOnClickListener(this);
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
		getData();
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
						}else if (result.getRegions().size()==1) {
							regionId_selected=result.getRegions().get(0).getRegionId();
							regionName=result.getRegions().get(0).getName();
							//tv_district.setTextColor(activity.getResources().getColor(R.color.gray_button_text_color));
							tv_district.setText(result.getRegions().get(0).getName());
						}
						lists.clear();
						lists.addAll(result.getRegions());
						//adapter.notifyDataSetChanged();
						
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
		tv.setText(lists.get(position).getName());
		return view;
	}
	
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
		private String regionName;
	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.tv_district://新增区域
		if (phoneGetPlotResult!=null||entity_update!=null||lists.size()==1) {
			return;
		}else {
			Intent intent=new Intent(activity,HomeHeaderActivity.class);
	        intent.putExtra("type",HeaderActivityType.AddDistrict.ordinal());
	        intent.putExtra("isselectpolt", false);
	        startActivityForResult(intent, 100);
			//openNewActivityWithHeader(HeaderActivityType.AddDistrict.ordinal());
		}
		
	/*	adapter=new MyBaseAdapter();
		contentView.setAdapter(adapter);
		//getData();
		
		pw.showAsDropDown(tv_district);
		contentView.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				tv_district.setText(lists.get(position).getName());
				regionId_selected = lists.get(position).getRegionId();
				tv_plots.setText("选择小区");
				plId=-1;
				pw.dismiss();
			}
		});*/
		break;
	
	case R.id.tv_plots://小区 点击监听
		if (phoneGetPlotResult!=null||entity_update!=null) {
			return;
		}
		//plots_list.clear();
		
		
		if (regionId_selected==-1) {
			Toast.makeText(activity, "请先选择区域",Toast.LENGTH_SHORT).show();
			return;
		}else {
			Intent intent=new Intent(activity,HomeHeaderActivity.class);
	        intent.putExtra("type",HeaderActivityType.AddDistrict.ordinal());
	        intent.putExtra("isselectpolt", true);
	        intent.putExtra("RegionId", regionId_selected);
	        intent.putExtra("RegionName",regionName);
	        startActivityForResult(intent, 100);
			//openNewActivityWithHeader(HeaderActivityType.AddDistrict.ordinal());
		}
		/*getPlots();
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
				pw.dismiss();
			}
		});*/

		break;
	
	
	   case R.id.tv_add:
		
			checkSubmit();
		
		break;

	default:
		break;
	}
}
	
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode==100&&resultCode==Activity.RESULT_OK) {
			if (data!=null) {
				AddDistrictEntity entity=(AddDistrictEntity) data.getSerializableExtra("AddDistrictEntity");
				plId=entity.getPlId();
				//tv_plots.setTextColor(activity.getResources().getColor(R.color.gray_button_text_color));
				tv_plots.setText(entity.getPlotName());
				
				if (entity.getRegionId_selected()!=-1) {
					regionId_selected=entity.getRegionId_selected();
					//tv_district.setTextColor(activity.getResources().getColor(R.color.gray_button_text_color));
					tv_district.setText(entity.getRegionName());
					regionName=entity.getRegionName();
				}
			}
		}
    };
	/*
	 * 获取小区地址列表
	 */
	ArrayList<PlotsEntity> plots_list = new ArrayList<PlotsEntity>();
	private ScrollView scrollview_setting;
	private RelativeLayout rl_setting_content;
	private PhoneGetPlotResult phoneGetPlotResult;
	private ImageView img1;
	private ImageView img2;




	

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
		//request.setCtrnId(ctrnId);
		//设置默认地址
		if(addressSetDefaultCheckBox!=null &&addressSetDefaultCheckBox.isChecked()){
			request.setIsDefault(1);
		}else{

		}
		progressDialog.setMessage("保存中...");
		showProgress(true);
		client.httpPostByJson("Phoneaddressadd", result, request, AddAddressResult.class, new BaseRequestClient.RequestClientCallBack<AddAddressResult>() {

			@Override
			public void callBack(AddAddressResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						if (result.getShipAddress()!=null&&result.getShipAddress().getIsDefault()==1) {
							saveResultToDB(result.getMarket().getId());
						}
						Intent intent=new Intent();
						intent.putExtra("addressEntity", result.getShipAddress());
						activity.setResult(Activity.RESULT_OK,intent);
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
			public void logOut(boolean isLogOut,AddAddressResult result) {FinalDb finalDb=FinalDb.create(activity);
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
	 private void saveResultToDB(int id) {
		 FinalDb db=FinalDb.create(activity);
		 UserResult result = baseApplication.getUserResult();
		 int marketId = result.getMarketId();
	        List<UserResult> list = db.findAllByWhere(UserResult.class, "userAccount='" + result.getUserAccount() + "'");
	        if (list.size() > 0) {
	        	result.setMarketId(id);
	        	baseApplication.setUserResult(result);
	            db.update(result, "userAccount='" + result.getUserAccount() + "'");
	           // if (marketId!=id) {
	            	EventBus.getDefault().post("login");
				//}
	            }
	     }
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		super.onCreate(savedInstanceState);
	}
	 
	private void updateAddress() {
		progressDialog.setMessage("地址跟新中。。。");
		progressDialog.show();
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		final UpdateAddressRequest request=new UpdateAddressRequest();
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
		//request.setCtrnId(ctrnId);
		//设置默认地址
		if(addressSetDefaultCheckBox!=null &&addressSetDefaultCheckBox.isChecked()){
			request.setIsDefault(1);
		}else{
			request.setIsDefault(0);
		}
		client.httpPostByJson("Phoneaddressmodify", result, request, AddAddressResult.class, new BaseRequestClient.RequestClientCallBack<AddAddressResult>() {

			@Override
			public void callBack(AddAddressResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						if (request.getIsDefault()==1) {
							saveResultToDB(entity_update.getMarketId());
						}
						activity.setResult(Activity.RESULT_OK);
						activity.finish();
					}else {
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();

					}
				}else {
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				progressDialog.dismiss();
			}

			@Override
			public void loading(long count, long current) {
				
				
			}
			@Override
			public void logOut(boolean isLogOut,AddAddressResult result) {FinalDb finalDb=FinalDb.create(activity);
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
}
