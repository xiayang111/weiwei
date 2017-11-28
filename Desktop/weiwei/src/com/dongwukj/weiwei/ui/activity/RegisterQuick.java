package com.dongwukj.weiwei.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.data.SmsCode;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.AddDistrictEntity;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.LoginEntity;
import com.dongwukj.weiwei.idea.request.NewAddressRequest;
import com.dongwukj.weiwei.idea.request.PlotsRequest;
import com.dongwukj.weiwei.idea.request.RegisterEntity;
import com.dongwukj.weiwei.idea.request.SmsRequest;
import com.dongwukj.weiwei.idea.request.ValidateRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.NewAddressResult;
import com.dongwukj.weiwei.idea.result.PlotsEntity;
import com.dongwukj.weiwei.idea.result.PlotsResult;
import com.dongwukj.weiwei.idea.result.RegisterResult;
import com.dongwukj.weiwei.idea.result.SmsResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.idea.result.NewAddressResult.NewAddressEntity;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.net.utils.DESUtils;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.fragment.UserAgreementFragment;
import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;
import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterQuick extends BaseActivity {

	private static final int Request_SmsCoe = 0;
	protected static final int Validate_SmsCoe = 1;
	protected static final int Request_Register = 2;
	private static final int Request_Sms_Again = 3;
	private BaseApplication baseApplication;
	private Button btn_get_smscode;
	private EditText et_smscode;
	private EditText et_pwd;
	private String smsCode;  //服务器返回的短信验证码
	private int limitTime;
	private String phone_Number;
	private String smscode;  //本地输入的短信验证码
	private TextView tv_time;
	private FinalDb db;
	private RelativeLayout rl_one;
	private RelativeLayout rl_two,rl_3;
	private CheckBox cb;
	private EditText et_phone;
	private String password;
	private TextView tv_phonenum;
	private Button bt_complete;
	private TextView tv_agreement;
	private String phone;
	private ListView contentView;
	private MyBaseAdapter adapter;
	private PopupWindow pw;
	private MyAdapter myAdapter;
	private View view;
	private LinearLayout ll_all;
	private int plotId=-1;
	private ProgressDialog progressDialog;
	ArrayList<PlotsEntity> plots_list = new ArrayList<PlotsEntity>();
	private List<NewAddressEntity> list=new ArrayList<NewAddressEntity>();
	@Override
	protected void initView() {
		setContentView(R.layout.activity_registerquick);
		baseApplication = (BaseApplication) getApplication();
		db=FinalDb.create(this);
		progressDialog = new ProgressDialog(this);
    	progressDialog.setCancelable(false);
	}
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	if (requestCode==100&&resultCode==Activity.RESULT_OK) {
				if (data!=null) {
					AddDistrictEntity entity=(AddDistrictEntity) data.getSerializableExtra("AddDistrictEntity");
					plotId=entity.getPlId();
					plot_tv.setText(entity.getPlotName());
					
					if (entity.getRegionId_selected()!=-1) {
						regionId=entity.getRegionId_selected();
						area.setText(entity.getRegionName());
						regionName=entity.getRegionName();
					}
				}
			}
	    };
	@Override
	protected void findViewById() {
		btn_get_smscode = (Button) findViewById(R.id.btn_get_smscode);//下一步
		et_smscode = (EditText) findViewById(R.id.et_smscode);//验证码编辑框
		et_pwd = (EditText) findViewById(R.id.et_pwd);//密码
		tv_time = (TextView) findViewById(R.id.tv_time);//倒计时时间
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		plot_tv = (TextView) findViewById(R.id.plot_tv);
		tv_title.setText("注册");
		findViewById(R.id.ll_left).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});;//返回按钮
		rl_one = (RelativeLayout) findViewById(R.id.rl_one);
		rl_two = (RelativeLayout) findViewById(R.id.rl_two);
		rl_3=(RelativeLayout) findViewById(R.id.rl_3);
		Button com=(Button) findViewById(R.id.com);
		com.setOnClickListener(this);
		cb = (CheckBox) findViewById(R.id.cb_xieyi);
		et_phone = (EditText) findViewById(R.id.et_phone);
		tv_phonenum = (TextView) findViewById(R.id.tv_phonenum);
		bt_complete = (Button) findViewById(R.id.btn_complete);
		//----------------------------------跟新部分--------------------------
		btn_get_smscode.setOnClickListener(this);
		tv_time.setOnClickListener(this);
		bt_complete.setOnClickListener(this);
		view = findViewById(R.id.view);
		tv_agreement = (TextView) findViewById(R.id.tv_agreement);
		tv_agreement.setOnClickListener(this);
		 
		final RelativeLayout tv_area=(RelativeLayout) findViewById(R.id.tv_area);
		final RelativeLayout tv_plot=(RelativeLayout) findViewById(R.id.tv_plot);
		final TextView plot=(TextView) findViewById(R.id.plot);
		area = (TextView) findViewById(R.id.area);
		contentView = new ListView(getApplicationContext());
		contentView.setScrollbarFadingEnabled(false);
		contentView.setVerticalScrollBarEnabled(false);
		ll_all = (LinearLayout) findViewById(R.id.ll_all);
		adapter = new MyBaseAdapter();
		
		tv_area.setOnClickListener(new OnClickListener() {
			
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				if (list.size()==1) {
					Intent intent=new Intent(getApplicationContext(),HomeHeaderActivity.class);
			        intent.putExtra("type",HeaderActivityType.AddDistrict.ordinal());
			        intent.putExtra("isselectpolt", true);
			        intent.putExtra("RegionId", regionId);
			        intent.putExtra("RegionName",regionName);
			        startActivityForResult(intent, 100);
				}else {
					Intent intent=new Intent(getApplicationContext(),HomeHeaderActivity.class);
			        intent.putExtra("type",HeaderActivityType.AddDistrict.ordinal());
			        intent.putExtra("isselectpolt", false);
			        startActivityForResult(intent, 100);
				}
			}
		});
		myAdapter = new MyAdapter();
	 
		tv_plot.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (regionId==-1) {
					Toast.makeText(getApplicationContext(), "请选择区域", Toast.LENGTH_SHORT).show();;
					return;
				}
				pw = new PopupWindow(contentView, view.getWidth(),LayoutParams.WRAP_CONTENT);
				pw.setBackgroundDrawable(getApplication().getResources().getDrawable(R.drawable.bg));
				pw.setOutsideTouchable(true);
				pw.setFocusable(true);
				contentView.setAdapter(myAdapter);
				plots_list.clear();
				getPlots();
				pw.showAsDropDown(ll_all);
				contentView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						plot.setText(plots_list.get(position).getName());
						plotId = plots_list.get(position).getId();
						pw.dismiss();
					}
				});
			}
			
		});
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	    MobclickAgent.onResume(this); 
	    MobclickAgent.onPageStart("Register");
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	    MobclickAgent.onPageEnd("Register"); 
	    MobclickAgent.onPause(this);
	}
	public void onEventMainThread(String type){}
   	
	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * 获取小区列表
	 */
	private void getPlots() {
		progressDialog.setMessage("获取小区列表中。。。");
		progressDialog.show();
		BaseRequestClient client=new BaseRequestClient(getApplicationContext());
		UserResult result = baseApplication.getUserResult();
		PlotsRequest request = new PlotsRequest();
		request.setRegionId(regionId); //目前固定写洪山区 id 1902
		
		client.httpPostByJson("Phoneplots", result, request, PlotsResult.class, new BaseRequestClient.RequestClientCallBack<PlotsResult>() {

			@Override
			public void callBack(PlotsResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						if (result.getPlots().size()==0) {
							Toast.makeText(getApplicationContext(),"暂时没有小区列表", Toast.LENGTH_SHORT).show();
							return;
						}
						plots_list.addAll(result.getPlots());
						myAdapter.notifyDataSetChanged();
						
					}else {
						Toast.makeText(getApplicationContext(),result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				progressDialog.dismiss();
			}

			@Override
			public void loading(long count, long current) {
				
			}
			
			@Override
			public void logOut(boolean isLogOut, PlotsResult result) {
				progressDialog.dismiss();
			}
		});}
	private int regionId=-1;
	private String regionName;
	/*
	 * 获取区域列表
	 */
	private void getData() {
		progressDialog.setMessage("正在获取区域列表。。。");
		progressDialog.show();
		BaseRequestClient client=new BaseRequestClient(getApplicationContext());
		UserResult result = baseApplication.getUserResult();
		NewAddressRequest request=new NewAddressRequest();
		
		client.httpPostByJson("Phoneregions", result, request, NewAddressResult.class, new BaseRequestClient.RequestClientCallBack<NewAddressResult>() {

			@Override
			public void callBack(NewAddressResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						if (result.getRegions().size()==0) {
							Toast.makeText(getApplicationContext(),"暂时没有地址列表", Toast.LENGTH_SHORT).show();
							return;
						}else if (result.getRegions().size()==1) {
							regionId=result.getRegions().get(0).getRegionId();
							regionName=result.getRegions().get(0).getName();
							area.setText(result.getRegions().get(0).getName());
						}
						list.clear();
						list.addAll(result.getRegions());
						adapter.notifyDataSetChanged();
						
					}else {
						Toast.makeText(getApplicationContext(),result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				progressDialog.dismiss();
			}

			@Override
			public void loading(long count, long current) {}
			@Override
			public void logOut(boolean isLogOut,NewAddressResult result) {
				progressDialog.dismiss();
			}
		});
		
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

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout view = (LinearLayout)View.inflate(getApplicationContext(), R.layout.pop_item, null);
			tv = (TextView) view.findViewById(R.id.tv);
			tv.setText(plots_list.get(position).getName());
			return view;
		}
		
	}
	private class MyBaseAdapter extends BaseAdapter{

		private TextView tv;

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

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout view = (LinearLayout)View.inflate(getApplicationContext(), R.layout.pop_item, null);
			tv = (TextView) view.findViewById(R.id.tv);
			tv.setText(list.get(position).getName());
			return view;
		}
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_get_smscode:
			phone = et_phone.getText().toString().trim();
				Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[678])|(14[57])|(18[0-9]))\\d{8}$");
				Matcher matcher = p.matcher(phone);
				if(!TextUtils.isEmpty(phone)){
					if(matcher.matches()){
						if (!(et_pwd.getText().toString().trim().length()<6||et_pwd.getText().toString().trim().length()>16)) {
							
							Pattern p1 = Pattern.compile("[a-zA-Z0-9~!@#$%^&*_+]{6,16}$");
							Matcher matcher1 = p1.matcher(et_pwd.getText().toString().trim());
							if (!matcher1.matches()) {
								Toast.makeText(getApplicationContext(), "密码不能包含非法字符", Toast.LENGTH_SHORT).show();
							}else {
								password = et_pwd.getText().toString().trim();
								if (cb.isChecked()) {
									Message msg = Message.obtain();
									msg.obj = phone;
									msg.what = Request_SmsCoe;
									registerHandler.sendMessage(msg);
								}
								else {
									Toast.makeText(getApplicationContext(), "请阅读并同意用户协议", Toast.LENGTH_SHORT).show();
								}
							}
							
						}else {
							Toast.makeText(getApplicationContext(), "密码为6-16位", Toast.LENGTH_SHORT).show();
						}
						
					}else{
						Toast.makeText(getApplicationContext(), "请输入正确格式的手机号码!", Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(getApplicationContext(), "手机号码不能为空!", Toast.LENGTH_SHORT).show();
				}
			
			//}
			break;
		case R.id.btn_complete: //本地校验短信验证码
			smscode = et_smscode.getText().toString().trim();	//从输入框中获取到输入的验证码值
			if(TextUtils.isEmpty(smscode)){
				Toast.makeText(getApplicationContext(), "短信验证码不能为空!", Toast.LENGTH_SHORT).show();
				return;
			}
			PhoneGetSystemDateTime();
			/*smscode = et_smscode.getText().toString().trim();
			if(!TextUtils.isEmpty(smscode)){
				if(smsCode.equals(smscode)){
					rl_two.setVisibility(View.GONE);
					rl_3.setVisibility(View.VISIBLE);
				}else{
					Toast.makeText(getApplicationContext(), "短信验证码错误,请重新输入!", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(getApplicationContext(), "短信验证码不能为空!", Toast.LENGTH_SHORT).show();
			}*/
		
			//PhoneGetSystemDateTime();
				
			break;
		case R.id.tv_time:
			registerHandler.sendEmptyMessage(Request_Sms_Again);
		
			break;
		case R.id.tv_agreement:
			Intent intent = new Intent(getApplicationContext(), HomeHeaderActivity.class);
			intent.putExtra("needLogin", false);
			intent.putExtra("type", HeaderActivityType.UserAgreement.ordinal());
			startActivity(intent);
			break;
		case R.id.com:
			if (plotId!=-1) {
				regiterRequest(password);
			}else {
				Toast.makeText(getApplication(), "请选择小区!", Toast.LENGTH_SHORT).show();
			}
			
			break;
		default:
			break;
		}
	}
	private String StartTime;
	private String EndTime;
	/**
	 * 获取服务器当前时间
	 */
	private void PhoneGetSystemDateTime() {
		BaseRequestClient client=new BaseRequestClient(this);
		progressDialog.setMessage("提交验证中。。。");
		progressDialog.show();
		client.httpPostByJsonNew("PhoneGetSystemDateTime", baseApplication.getUserResult(),new BaseRequest(), BaseResult.class, new BaseRequestClient.RequestClientNewCallBack<BaseResult>() {

			@Override
			public void callBack(BaseResult result) {
				if (result!=null) {
					EndTime=result.getTimestamp();
					if(Long.parseLong(EndTime)-Long.parseLong(StartTime)>600000){
						smsCode=null;
						Toast.makeText(getApplicationContext(), "验证码有效期已过,请重新请求", Toast.LENGTH_SHORT).show();
					}else{
						smscode = et_smscode.getText().toString().trim();
						if(!TextUtils.isEmpty(smscode)){
							if(smsCode.equals(smscode)){
								rl_two.setVisibility(View.GONE);
								getData();
								rl_3.setVisibility(View.VISIBLE);
							}else{
								Toast.makeText(getApplicationContext(), "短信验证码错误,请重新输入!", Toast.LENGTH_SHORT).show();
							}
						}else{
							Toast.makeText(getApplicationContext(), "短信验证码不能为空!", Toast.LENGTH_SHORT).show();
						}
					}
				}else {
					Toast.makeText(RegisterQuick.this, getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				progressDialog.dismiss();
			}

			@Override
			public void loading(long count, long current) {
				
			}

			@Override
			public void logOut(BaseResult result) {
				Toast.makeText(baseApplication, "请检查手机当前时间", 0).show();
				progressDialog.dismiss();
			}
		});
	}
	private Handler registerHandler = new Handler(){
				
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Request_SmsCoe:
			{
				phone_Number = (String)msg.obj;
				SmsRequest request=new SmsRequest();
				request.setMobileNo(phone_Number);

                MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(RegisterQuick.this,baseApplication);
                myWeiWeiRequestClient.smsCode(request, new MyWeiWeiRequestClient.RegisterRequestClientCallback() {
                    @Override
                    protected void smsCode(SmsResult result) {
                        smsCode= result.getNoteverify();
                        limitTime = 120;//服务器没有,暂时赋值.
                        rl_one.setVisibility(View.GONE);
                        mHandler.sendEmptyMessage(222);	//发消息给倒计时
                        StartTime=result.getTimestamp();
                        String replace = phone_Number.substring(0, 3)+"****"+phone_Number.substring(7, 11);
                        tv_phonenum.setText(replace);
                        rl_two.setVisibility(View.VISIBLE);
                    }
                    protected void logOut(BaseResult result) {
                    	Toast.makeText(baseApplication, "请检查手机当前时间", 0).show();
                    };
                });
			}
			
				break;
			
			case Request_Sms_Again:
				requestSmsAgain();
				break;
				
			default:
				break;
			}
		};
	};
		

	

	    	
	/**
	 * 注册
	 * @param password
	 */
	private void regiterRequest(String password){
		RegisterEntity request2=new RegisterEntity();
		request2.setUserAccount(phone_Number);
		;
		try {
			request2.setUserPassword(DESUtils.encrypt(password, DESUtils.DES_KEY_STRING));
			request2.setLat(0);
			request2.setLng(0);
			request2.setPlotId(plotId);
			request2.setRecomMander("");
			request2.setRegisterip(NetworkUtil.getLocalIpAddress());
            MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(RegisterQuick.this,baseApplication);
            myWeiWeiRequestClient.register(request2, new MyWeiWeiRequestClient.RegisterRequestClientCallback() {
                @Override
                protected void register(RegisterResult result) {
                    Toast.makeText(RegisterQuick.this, "恭喜你,注册成功!", Toast.LENGTH_SHORT).show();
                    
                    UserResult result2=new UserResult();
                    result2.setLogin(true);
                    result2.setTokenId(result.getTokenid());
                    result2.setUserAccount(result.getUseraccount());
                    result2.setTel(phone_Number);
                    result2.setAvatar(result.getAvatar());
                    result2.setNickName(result.getNickName());
                    result2.setPlotid(result.getPlotid());
                    result2.setMarketId(result.getMarket().getId());
                    //result2.setLevel(level);
                    baseApplication.setUserResult(result2);
                    //保存用户信息到数据库
                    saveResultToDB(result2);
                    setPushTagAndAlias(result);
                    setResult(200);
                    finish();
                }
            });	


		} catch (Exception e) {
			Log.d(RegisterQuick.class.getName(), "EX:"+e);
			  Toast.makeText(RegisterQuick.this, "注册失败!", Toast.LENGTH_SHORT).show();
		}
			
	}
	/*
	 * 给用户设置jpushTAG
	 */
	private void setPushTagAndAlias(RegisterResult result){
        if(result==null) return;
        // 根据用户等级标签
        String tag=result.getLevel()==null?"0":result.getLevel();
        HashSet<String> tags=new HashSet<String>();
        tags.add(tag);
        String alias=result.getTokenid();
        JPushInterface.setAliasAndTags(this, alias, tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if(i==0){
                    //成功
                    Log.d("jpush","success");
                }else{
                    //失败
                    Log.d("jpush","fail");
                }
            }
        });

    }
	/**
	 * 再次获取验证码
	 */
	private void requestSmsAgain(){
		
		SmsRequest request3=new SmsRequest();
		request3.setMobileNo(phone_Number);

        MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(RegisterQuick.this,baseApplication);
        myWeiWeiRequestClient.smsCode(request3, new MyWeiWeiRequestClient.RegisterRequestClientCallback() {
            @Override
            protected void smsCode(SmsResult result) {
                smsCode = result.getNoteverify();
                limitTime = 120;
                StartTime=result.getTimestamp();
                tv_time.setBackgroundResource(R.drawable.weiwei_gray_button);
            }
        });	

	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			mHandler.sendEmptyMessageDelayed(0, 1000);
			if (limitTime>=0) {
				tv_time.setText("再次获取\n   "+"（"+limitTime+"）");
				limitTime--;
			}else {
				 tv_time.setBackgroundResource(R.drawable.weiwei_red_button);
			}
		};
	};
	private TextView area;
	private TextView plot_tv;
	/*
	 * 保存用户信息到数据库
	 */
	private void saveResultToDB(UserResult result) {
		List<UserResult> list = db.findAllByWhere(UserResult.class, "userAccount='"+result.getUserAccount()+"'");
		if (list.size()==0) {
			db.save(result);
		}else {
			db.update(result,"userAccount='"+result.getUserAccount()+"'");
		}
		//EventBus.getDefault().post("login");
	}
	
}
