package com.dongwukj.weiwei.ui.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.LoginEntity;
import com.dongwukj.weiwei.idea.request.SmsRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.SmsResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.net.utils.DESUtils;

import net.tsz.afinal.FinalDb;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FindPassword extends BaseActivity {

	private static final int Request_SmsCoe = 0;
	protected static final int Validate_SmsCoe = 1;
	protected static final int Request_Register = 2;
	private static final int Request_Sms_Again = 3;
	private static final int ChangeThree = 4;
	private BaseApplication baseApplication;
	private Button btn_get_smscode;
	private EditText et_smscode;
	private String smsCode;  //服务器返回的短信验证码
	private int limitTime;
	private String phone_Number;
	private String smscode;  //本地输入的短信验证码
	private LinearLayout ll_left;
	private TextView tv_time;
	private RelativeLayout rl_one;
	private RelativeLayout rl_two;
	private EditText et_phone;
	private TextView tv_phonenum;
	private Button bt_complete;
	private RelativeLayout rl_three;
	private Button btn_verify_smscode;
	private EditText et_password1;
	private EditText et_password2;
	private String StartTime;
	private String EndTime;
	
	@Override
	protected void findViewById() {
		//----------------------------------跟新部分--------------------------
		btn_get_smscode = (Button) findViewById(R.id.btn_get_smscode);//下一步
		et_smscode = (EditText) findViewById(R.id.et_smscode);//验证码编辑框
		tv_time = (TextView) findViewById(R.id.tv_time);//倒计时时间
		ll_left = (LinearLayout) findViewById(R.id.ll_left);//返回按钮
		rl_one = (RelativeLayout) findViewById(R.id.rl_one);
		rl_two = (RelativeLayout) findViewById(R.id.rl_two);
		rl_three = (RelativeLayout) findViewById(R.id.rl_three);
		et_phone = (EditText) findViewById(R.id.et_phone);
		tv_phonenum = (TextView) findViewById(R.id.tv_phonenum);
		btn_verify_smscode = (Button) findViewById(R.id.btn_verify_smscode);
		bt_complete = (Button) findViewById(R.id.btn_complete);
		et_password1 = (EditText) findViewById(R.id.et_password1);
		et_password2 = (EditText) findViewById(R.id.et_password2);
		btn_get_smscode.setOnClickListener(this);
		ll_left.setOnClickListener(this);
		tv_time.setOnClickListener(this);
		bt_complete.setOnClickListener(this);
		btn_verify_smscode.setOnClickListener(this);

	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_findpassword);
		baseApplication = (BaseApplication) getApplication();
		progressDialog = new ProgressDialog(this);
    	progressDialog.setCancelable(false);
    }
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_get_smscode:
			phone_Number = et_phone.getText().toString().trim();
			Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[678])|(14[57])|(18[0-9]))\\d{8}$");
			Matcher matcher = p.matcher(phone_Number);
			if(!TextUtils.isEmpty(phone_Number)){
				if(matcher.matches()){
					registerHandler.sendEmptyMessage(Request_SmsCoe);
				}else{
					Toast.makeText(getApplicationContext(), "请输入正确格式的手机号码!", Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(getApplicationContext(), "手机号码不能为空!", Toast.LENGTH_SHORT).show();
			}
			break; 
		case R.id.btn_verify_smscode: //本地校验短信验证码
			smscode = et_smscode.getText().toString().trim();
			if(TextUtils.isEmpty(smscode)){
				Toast.makeText(getApplicationContext(), "短信验证码不能为空!", Toast.LENGTH_SHORT).show();
				return;
			}
			
			smscode = et_smscode.getText().toString().trim();
			if(!TextUtils.isEmpty(smscode)){
				PhoneGetSystemDateTime();
			}else{
				Toast.makeText(getApplicationContext(), "短信验证码不能为空!", Toast.LENGTH_SHORT).show();
			}
		
			//PhoneGetSystemDateTime();
			break;
		case R.id.btn_complete:
			String pwd = et_password1.getText().toString().trim();
			String pwd2 = et_password2.getText().toString().trim();
			if(!TextUtils.isEmpty(pwd)&&!TextUtils.isEmpty(pwd2)){
				if ((pwd.length()<6||pwd.length()>16)) {
					Toast.makeText(getApplicationContext(), "密码为6-16位", Toast.LENGTH_SHORT).show();
					return;
				}else {
					Pattern p1 = Pattern.compile("[a-zA-Z0-9~!@#$%^&*_+]{6,16}$");
					Matcher matcher1 = p1.matcher(pwd);
					if (!matcher1.matches()) {
						Toast.makeText(getApplicationContext(), "密码不能包含非法字符", Toast.LENGTH_SHORT).show();
						return;
					}
					if(pwd.equals(pwd2)){
						fingpassword(pwd);
					}else{
						Toast.makeText(getApplicationContext(), "两次输入的密码不一致!", Toast.LENGTH_SHORT).show();
					}
				}
			}else{
				Toast.makeText(getApplicationContext(), "输入的密码不能为空!", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.tv_time:
			if (limitTime > 0) {
				Toast.makeText(getApplicationContext(), "验证码有效期没有过,请稍后再试!",
						Toast.LENGTH_SHORT).show();
			} else {
				registerHandler.sendEmptyMessage(Request_Sms_Again);
			}
			
			break;
		case R.id.ll_left:
			finish();
			break;
		default:
			break;
		}
	}
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
								rl_three.setVisibility(View.VISIBLE);
							}else{
								Toast.makeText(getApplicationContext(), "短信验证码错误,请重新输入!", Toast.LENGTH_SHORT).show();
							}
						}else{
							Toast.makeText(getApplicationContext(), "短信验证码不能为空!", Toast.LENGTH_SHORT).show();
						}
					}
				}else {
					Toast.makeText(FindPassword.this, getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
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
				SmsRequest request=new SmsRequest();
				request.setMobileNo(phone_Number);
				MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(FindPassword.this,baseApplication);
                myWeiWeiRequestClient.smsUpdatecode(request, new MyWeiWeiRequestClient.RegisterRequestClientCallback() {
                    @Override
                    protected void smsCode(SmsResult result) {
                        //smsCode = result.getSmsCode();
                        smsCode= result.getNoteverify();
                        //limitTime = result.getLimitTime();
                        limitTime = 120;//服务器没有,暂时赋值.
                        rl_one.setVisibility(View.GONE);
                        mHandler.sendEmptyMessage(222);	//发消息给倒计时
                        StartTime=result.getTimestamp();
                        //对验证的手机号码中间4位数字进行*号替换处理
                        String substring = phone_Number.substring(3, 7);
                        String replace = phone_Number.replace(substring, "****");
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
			case ChangeThree:
				
				break;
			default:
				break;
			}
		};
	};
	
	 
	
	
	
	protected void fingpassword(String password) {
		progressDialog.setMessage("正在提交中。。。");
		progressDialog.show();
		BaseRequestClient client=new BaseRequestClient(getApplicationContext());
		UserResult result = baseApplication.getUserResult();
		LoginEntity request=new LoginEntity();
		request.setUserAccount(phone_Number);
		try {
			request.setUserPassword(DESUtils.encrypt(password, DESUtils.DES_KEY_STRING));
			client.httpPostByJson("Phonefindpasswd", result, request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

				@Override
				public void callBack(BaseResult result) {
					if (result!=null) {
						if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
							Toast.makeText(getApplicationContext(), "找回密码成功请登录!", Toast.LENGTH_LONG).show();
							finish();
						}else {
							Toast.makeText(getApplicationContext(),result.getMessage(), Toast.LENGTH_SHORT).show();
						}
					}else {
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
					}
					progressDialog.dismiss();
				}

				@Override
				public void loading(long count, long current) {
					
				}
				@Override
				public void logOut(boolean isLogOut,BaseResult result) {
					progressDialog.dismiss();
					}
			});

		} catch (Exception e) {
			progressDialog.show();
			Toast.makeText(getApplicationContext(),"密码不能包含非法字符", Toast.LENGTH_SHORT).show();
			Log.d(FindPassword.class.getName(), "EX:"+e);
		}
		
	
	}

	/***
	 * 再次获取验证码
	 */
	private void requestSmsAgain(){
		
		SmsRequest request3=new SmsRequest();
		request3.setMobileNo(phone_Number);

        MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(FindPassword.this,baseApplication);
        myWeiWeiRequestClient.smsUpdatecode(request3, new MyWeiWeiRequestClient.RegisterRequestClientCallback() {
            @Override
            protected void smsCode(SmsResult result) {
                smsCode = result.getNoteverify();
                //limitTime = result.getLimitTime();
                limitTime = 120;
                mHandler.sendEmptyMessage(222);
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
				 mHandler.removeCallbacksAndMessages(null);
			}
		};
	};
	private ProgressDialog progressDialog;


}
