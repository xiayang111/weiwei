package com.dongwukj.weiwei.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.NewAddressRequest;
import com.dongwukj.weiwei.idea.result.ApkInfo;
import com.dongwukj.weiwei.idea.result.AreaEntity;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.NewAddressResult;
import com.dongwukj.weiwei.idea.result.NewAddressResult.NewAddressEntity;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.HomeRequestClient;
import com.dongwukj.weiwei.net.update.NewUpgradeManager;
import com.dongwukj.weiwei.net.update.UpgradeManager;
import com.dongwukj.weiwei.ui.AppManager;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SettingFragment extends AbstractHeaderFragment implements
		OnClickListener, OnCheckedChangeListener {

	public static final String MY_PREFS = "MySP";
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;

	private ImageView iv1_address;
	private CheckBox iv_hint;
	private CheckBox iv_notice;
	Editor edit;
	// private FinalDb db;
	private TextView tv_address_content;
	private List<NewAddressEntity> lists = new ArrayList<NewAddressEntity>();
	private PopupWindow pw;
	private MyAdapter adapter;
	private int regionId_selected;
	private Button btn_exit;
	private Button btn_clear_cache;
	private FinalDb finalDb;
	SharedPreferences sp;
	private boolean islogin = false;
	boolean isexit = false;
	
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.setting_fragment, container,
				false);
		return view;
	}

	@Override
	protected String setTitle() {
		return getResources().getString(R.string.seting_up);
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams lp = child.getLayoutParams();
		if (lp == null) {
			lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		// headerView的宽度信息
		int childMeasureWidth = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
		int childMeasureHeight;
		if (lp.height > 0) {
			childMeasureHeight = MeasureSpec.makeMeasureSpec(lp.height,
					MeasureSpec.EXACTLY);
			// 最后一个参数表示：适合、匹配
		} else {
			childMeasureHeight = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);// 未指定
		}
		// System.out.println("childViewWidth"+childMeasureWidth);
		// System.out.println("childViewHeight"+childMeasureHeight);
		// 将宽和高设置给child
		child.measure(childMeasureWidth, childMeasureHeight);
	}

	private boolean hasMeasure = false;

	@Override
	protected void findView(View v) {

//		scrollview_setting = (ScrollView) v
//				.findViewById(R.id.scrollview_setting);
		rl_setting_content = (RelativeLayout) v
				.findViewById(R.id.rl_setting_content);
		rl_8 = (RelativeLayout) v
				.findViewById(R.id.rl_8);
		rl_6 = (RelativeLayout) v
				.findViewById(R.id.rl_6);
		rl_6.setOnClickListener(this);
		rl_8.setOnClickListener(this);
		/*ViewTreeObserver viewTreeObserver = scrollview_setting
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
		});*///动态控制高度

		// measureView(scrollview_setting);
		// measureView(rl_setting_content);
		// v.measure(-1, -1);
		// scrollview_setting.measure(-1, -1);
		// rl_setting_content.measure(-1, -1);

		finalDb = FinalDb.create(activity);
		v.findViewById(R.id.rl_7).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NewUpgradeManager upgradeManager = new NewUpgradeManager(
						activity, baseApplication, true);
				upgradeManager.checkVersion();
			}
		});

		View findViewById = v.findViewById(R.id.bt_login);
		rl_3 = (RelativeLayout) v.findViewById(R.id.rl_3);
		iv1_address = (ImageView) v.findViewById(R.id.iv1_address);
		iv_hint = (CheckBox) v.findViewById(R.id.cb_hint);
		iv_notice = (CheckBox) v.findViewById(R.id.cb_notice);
		tv_address_content = (TextView) v.findViewById(R.id.tv_address_content);
		btn_exit = (Button) v.findViewById(R.id.btn_exit);
		btn_clear_cache = (Button) v.findViewById(R.id.btn_clear_cache);
		btn_clear_cache.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(progressDialog==null){
					progressDialog=new ProgressDialog(activity);
					//progressDialog.setTitle();
					progressDialog.setMessage("清理中。。");
				}
				if(!progressDialog.isShowing()){
					progressDialog.show();
					clearCacheHandler.sendEmptyMessageDelayed(FinishClearCache,2000);
				}

			}
		});
		tv_safe = (TextView) v.findViewById(R.id.tv_safe);
		tv_about_us = (TextView) v.findViewById(R.id.tv_about_us);
		tv_versions = (TextView) v.findViewById(R.id.tv_versions);
		new_version = (TextView) v.findViewById(R.id.new_version);

		// 把配置信息初始化为false,保存到首选项里面.
		sp = activity.getSharedPreferences("config", Activity.MODE_PRIVATE);
		iv_hint.setChecked(sp.getBoolean("isHint", false));
		iv_notice.setChecked(sp.getBoolean("isNotice", false));
		edit = sp.edit();
	
		// iv1_address.setOnClickListener(this); //监听暂时不用,只给tv_address_content
		// 设置监听调整到locationfragment界面
		iv_hint.setOnCheckedChangeListener(this); // 给checkbox设置改变监听
		iv_notice.setOnCheckedChangeListener(this); // 监听checkbox
		btn_exit.setOnClickListener(this); // 退出按钮监听
		tv_safe.setOnClickListener(this); // 账户安全监听
		tv_address_content.setOnClickListener(this); // 地理位置点击监听
		tv_about_us.setOnClickListener(this);
		rl_3.setOnClickListener(this);
		// getAreaData(); //发请求获取地址数据

		// db = FinalDb.create(activity);
		try {
			PackageInfo info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
			
			tv_versions.setText("当前版本 "+info.versionName);
		} catch (NameNotFoundException e1) {
			tv_versions.setText("版本跟新");
		}
		checkVersion();
	}

	private void checkVersion() {
		 HomeRequestClient homeRequestClient=new HomeRequestClient(activity,baseApplication);
         homeRequestClient.checkUpgrade(new HomeRequestClient.upgradeRequestClientCallback() {
             @Override
             protected void success(ApkInfo apkInfo1) {
            	 if (apkInfo1.getIsUpdate()) {
					new_version.setText("最新版本 "+apkInfo1.getApkVersion());
				}else {
					new_version.setText("");
				}
             }

             @Override
             protected void fail(ApkInfo apkInfo) {
                
             }
             @Override
             protected void logOut(BaseResult result) {

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

	private Handler clearCacheHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case FinishClearCache:
					FinalBitmap.create(activity).clearCache();
					if(progressDialog!=null &&progressDialog.isShowing()){
						Toast.makeText(activity, "清除缓存成功！", Toast.LENGTH_SHORT).show();
						progressDialog.dismiss();
					}
					break;
			}
		}
	};

	public final int FinishClearCache=110;

	private ProgressDialog progressDialog;


	@Override
	public void onResume() {
		super.onResume();
		if (baseApplication.getUserResult() == null) {
			islogin = false;
		} else {
			islogin = true;
		}
		if (islogin) {
			btn_exit.setText("退出当前账号");
		} else {
			btn_exit.setText("重新登录");
		}
		// 把定位数据写入 我的地理textview里面
		areaEtity = AreaEntity.getInstance();
		if (TextUtils.isEmpty(areaEtity.getCity())||TextUtils.isEmpty(areaEtity.getArea())||TextUtils.isEmpty(areaEtity.getArea())||TextUtils.isEmpty(areaEtity.getStreetnumber())) {
			tv_address_content.setText("未定位到您的位置");
		} else {
			String province = areaEtity.getProvince();
			String city = areaEtity.getCity();
			String area = areaEtity.getArea();
			// tv_address_content.setText(province+"-"+city+"-"+area);
			tv_address_content.setText("湖北省" + "-" + "武汉市" + "-" + area);
		}
	}

	private void getAreaData() {
		BaseRequestClient client = new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		NewAddressRequest request = new NewAddressRequest();
		client.httpPostByJson(
				"Phoneregions",
				result,
				request,
				NewAddressResult.class,
				new BaseRequestClient.RequestClientCallBack<NewAddressResult>() {

					@Override
					public void callBack(NewAddressResult result) {
						if (result != null) {
							if (result.getCode() == BaseResult.CodeState.Success
									.getCode()) {
								if (result.getRegions().size() == 0) {
									Toast.makeText(activity, "暂时没有地址列表",
											Toast.LENGTH_SHORT).show();
									return;
								}
								lists.addAll(result.getRegions());

							} else {
								Toast.makeText(activity,
										result.getMessage(), Toast.LENGTH_SHORT)
										.show();
							}
						} else {
							Toast.makeText(
									activity,
									activity.getResources().getString(
											R.string.data_error),
									Toast.LENGTH_SHORT).show();
						}

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv1_address:
			/*iv1_address.setImageResource(R.drawable.f107);
			ListView lv_area = new ListView(activity);
			lv_area.setScrollbarFadingEnabled(false);
			lv_area.setVerticalScrollBarEnabled(false);
			adapter = new MyAdapter();
			lv_area.setAdapter(adapter);
			pw = new PopupWindow(lv_area, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			pw.setBackgroundDrawable(activity.getResources().getDrawable(
					R.drawable.bg));
			pw.setFocusable(true); // 点击屏幕其它位置使popupwindow自动消失.
			pw.showAsDropDown(iv1_address);

			lv_area.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					tv_address_content.setText("湖北省-" + "武汉市-"
							+ lists.get(position).getName());
					areaEtity = AreaEntity.getInstance();
					areaEtity.setProvince("湖北省");
					areaEtity.setCity("武汉市");
					areaEtity.setArea(lists.get(position).getName());
					finalDb.save(areaEtity);
					pw.dismiss();
					iv1_address.setImageResource(R.drawable.f105);
				}
			});
			iv1_address.setImageResource(R.drawable.f105);*/
			break;
		case R.id.cb_hint:
			
			break;
		case R.id.cb_notice:

			break;
		case R.id.rl_6:
			  Intent intent = new Intent();
			   intent.setAction("android.intent.action.CALL");
			   intent.setData(Uri.parse("tel:"+"027-87776107"));
			   startActivity(intent);
			break;
		case R.id.rl_3:
			openNewActivityWithHeader(
					HeaderActivityType.SecurityAccountFragment.ordinal(), true);
			break;
		case R.id.tv_versions: // 版本更新
			
			break;
		case R.id.rl_8: // 关于我们
			Intent intent1 = new Intent(activity, HomeHeaderActivity.class);
			intent1.putExtra("type", HeaderActivityType.AboutUs.ordinal());
			intent1.putExtra("url", "http://www.vvlife.com/about/aboutusmobile");
			intent1.putExtra("title", "关于我们");
			startActivity(intent1);
			
			
			
			//Toast.makeText(activity, "关于我们", Toast.LENGTH_SHORT).show();
			/*openNewActivityWithHeader(HeaderActivityType.AboutUs.ordinal(),
					false);*/
			break;

		case R.id.btn_exit:
			if (btn_exit.getText().equals("退出当前账号")) {
				//showDialog();
				showIsExitDialog();

			} else {
				Intent intent2 = new Intent(activity, LoginActivity.class);
			
				startActivity(intent2);
				
			}
			break;
		case R.id.tv_safe:
			//Toast.makeText(activity, "账号安全模式", Toast.LENGTH_SHORT).show();
			break;
		case R.id.tv_address_content:
			// Toast.makeText(activity, "正在定位您的位置",
			// Toast.LENGTH_LONG).show();
			// location();
			openNewActivityWithHeader(HeaderActivityType.Loucation.ordinal(),
					true);

			break;
		default:
			break;
		}

	}

/*	protected void showDialog() {

		final Dialog dialog = new Dialog(activity, R.style.Dialog);
		LinearLayout view = (LinearLayout) View.inflate(activity,
				R.layout.is_exit_dialog, null);
		dialog.setContentView(view);
		dialog.setCancelable(false); // 为false 按返回键不能 dismiss Dialog
		view.findViewById(R.id.tv_ok).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				List<UserResult> findAllByWhere = finalDb.findAllByWhere(
						UserResult.class, "isLogin=1");
				for (UserResult userResult : findAllByWhere) {
					userResult.setLogin(false);
					finalDb.update(userResult);
				}
				// finalDb.deleteAll(UserResult.class);
				baseApplication.setUserResult(null);
				baseApplication.setCartCount(0);
				Toast.makeText(activity, "账号已经退出", Toast.LENGTH_SHORT).show();
				btn_exit.setText("重新登录");

				// isexit = true;
				dialog.dismiss();
			}
		});

		view.findViewById(R.id.tv_cancle).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// isexit = false;
						dialog.dismiss();

					}
				});

		WindowManager m = activity.getWindowManager();
		Window dialogWindow = dialog.getWindow();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		p.height = (int) (d.getHeight() * 0.17); // 高度设置为屏幕的0.6
		p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
		dialogWindow.setAttributes(p);

		dialog.show();

	}*/
	
	protected void showIsExitDialog(){
		showDialogs(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.tv_ok:
					List<UserResult> findAllByWhere = finalDb.findAllByWhere(
							UserResult.class, "isLogin=1");
					for (UserResult userResult : findAllByWhere) {
						userResult.setLogin(false);
						finalDb.update(userResult);
					}
					// finalDb.deleteAll(UserResult.class);
					baseApplication.setUserResult(null);
					baseApplication.setCartCount(0);
					/*Toast.makeText(activity, "账号已经退出", Toast.LENGTH_SHORT).show();
					btn_exit.setText("重新登录");*/
					dialog.dismiss();
					Intent intent = new Intent(activity, LoginActivity.class);
					intent.putExtra("isFromModifyLoginPassword", false);
					startActivity(intent);
					break;
				
				case R.id.tv_cancel:
					dialog.dismiss();
					break;
				default:
					break;
				}
				
			}
		}, "是/否确定退出当前账户?");
	}

	private void openNewActivityWithHeader(int type, Boolean needLogin) {
		Intent intent = new Intent(activity, HomeHeaderActivity.class);
		intent.putExtra("type", type);
		intent.putExtra("needLogin", needLogin);
		startActivity(intent);
	}

	private void location() {
		mLocationClient = new LocationClient(
				baseApplication.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		initLocation();
		mLocationClient.start();

	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("gcj02"); // 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(1000); // 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			String province = location.getProvince();
			String city = location.getCity();
			String district = location.getDistrict();

			tv_address_content.setText(province + "-" + city + "-" + district);
			mLocationClient.stop();
		}

	}
	
	private TextView tv_safe;
	private AreaEntity areaEtity;
	private TextView tv_about_us;
	private TextView tv_versions,new_version;
	private RelativeLayout rl_3;
	//private ScrollView scrollview_setting;
	private RelativeLayout rl_setting_content,rl_8,rl_6;

	private class MyAdapter extends BaseAdapter {

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
			LinearLayout view = (LinearLayout) View.inflate(activity,
					R.layout.pop_item, null);
			tv = (TextView) view.findViewById(R.id.tv);
			tv.setText(lists.get(position).getName());
			return view;
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		switch (buttonView.getId()) {
		case R.id.cb_hint:
			edit.putBoolean("isHint", isChecked).commit();
			if (isChecked) {
				
				Toast.makeText(activity, "开启手机流量下手动下载图片",
						Toast.LENGTH_SHORT).show();
			} else {
				
				Toast.makeText(activity, "关闭手机流量下手动下载图片",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.cb_notice:
			edit.putBoolean("isNotice", isChecked).commit();
			if (isChecked) {
				
				Toast.makeText(activity, "接收应用程序通知", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(activity, "不接收应用程序通知", Toast.LENGTH_SHORT)
						.show();
			}

			break;

		default:
			break;
		}

	}

}
