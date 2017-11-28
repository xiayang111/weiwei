package com.dongwukj.weiwei.ui.fragment;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.dongwukj.weiwei.ui.activity.FindPassword;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

public class ModifyLoginPasswordFragment extends AbstractHeaderFragment implements OnClickListener {
	private boolean isGetSmsCode=true;
	private String tel;
	private boolean isFirst=true;
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.modifyloginpassword, null);
		return view;
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return activity.getResources().getString(R.string.modifyloginpasswordfragment_title);
	}

	@Override
	protected void findView(View v) {
		tel = baseApplication.getUserResult().getTel();
		finalDb = FinalDb.create(activity);
		ll_step1 = (LinearLayout) v.findViewById(R.id.ll_step1);
		ll_step2 = (LinearLayout) v.findViewById(R.id.ll_step2);
		getsms = (Button) v.findViewById(R.id.getsms);
		tv_tel = (TextView) v.findViewById(R.id.tv_tel);
		et_sms = (EditText) v.findViewById(R.id.et_sms);
		tv_error_sms = (TextView) v.findViewById(R.id.tv_error_sms);
		bt_next = (Button) v.findViewById(R.id.bt_next);
		
		et_password = (EditText) v.findViewById(R.id.et_password);
		verify_password = (EditText) v.findViewById(R.id.verify_password);
		tv_error_verify_password = (TextView) v.findViewById(R.id.tv_error_verify_password);
		tv_error_password = (TextView) v.findViewById(R.id.tv_error_password);
		bt_commit = (Button) v.findViewById(R.id.bt_commit);
		
		tv_tel.setText(tel.replace(tel.subSequence(3, 7), "****"));
		getsms.setOnClickListener(this);
		bt_next.setOnClickListener(this);
		bt_commit.setOnClickListener(this);
	}
	private String StartTime;
	private String EndTime;
	/**
	 * 获取服务器当前时间
	 */
	private void PhoneGetSystemDateTime() {
		BaseRequestClient client=new BaseRequestClient(activity);
		progressDialog.setMessage("提交验证中。。。");
		progressDialog.show();
		client.httpPostByJsonNew("PhoneGetSystemDateTime", baseApplication.getUserResult(),new BaseRequest(), BaseResult.class, new BaseRequestClient.RequestClientNewCallBack<BaseResult>() {

			@Override
			public void callBack(BaseResult result) {
				if (result!=null) {
					EndTime=result.getTimestamp();
					if(Long.parseLong(EndTime)-Long.parseLong(StartTime)>600000){
						smsCode=null;
						Toast.makeText(activity, "验证码有效期已过,请重新请求", Toast.LENGTH_SHORT).show();
					}else{
						if (TextUtils.isEmpty(et_sms.getText().toString().trim())) {
							tv_error_sms.setVisibility(View.VISIBLE);
							tv_error_sms.setText("请输入验证码");
							progressDialog.dismiss();
							return;
						}
						else if (!et_sms.getText().toString().trim().equals(smsCode)) {
							tv_error_sms.setVisibility(View.VISIBLE);
							tv_error_sms.setText("验证码输入不正确");
							progressDialog.dismiss();
							return;
						}
						ll_step1.setVisibility(View.GONE);
						ll_step2.setVisibility(View.VISIBLE);
					}
				}else {
					Toast.makeText(activity, getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				progressDialog.dismiss();
			}

			@Override
			public void loading(long count, long current) {
				
			}

			@Override
			public void logOut(BaseResult result) {
				progressDialog.dismiss();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.getsms:
			if (isGetSmsCode) {
				getSmsCode();
				isGetSmsCode=false;
			}
			break;
		case R.id.bt_next:
			 if (TextUtils.isEmpty(et_sms.getText().toString().trim())) {
					tv_error_sms.setVisibility(View.VISIBLE);
					tv_error_sms.setText("请输入验证码");
					return;
				}
			 if (TextUtils.isEmpty(StartTime)) {
				 tv_error_sms.setVisibility(View.VISIBLE);
				 tv_error_sms.setText("请先获取验证码");
					return;
			}
			PhoneGetSystemDateTime();
			/*if (limitTime<=0) {
				tv_error_sms.setVisibility(View.VISIBLE);
				tv_error_sms.setText("请先获取验证码");
				return;
			}else if (TextUtils.isEmpty(et_sms.getText().toString().trim())) {
				tv_error_sms.setVisibility(View.VISIBLE);
				tv_error_sms.setText("请输入验证码");
				return;
			}
			else if (!et_sms.getText().toString().trim().equals(smsCode)) {
				tv_error_sms.setVisibility(View.VISIBLE);
				tv_error_sms.setText("验证码输入不正确");
				return;
			}*/
			/*ll_step1.setVisibility(View.GONE);
			ll_step2.setVisibility(View.VISIBLE);*/
			break;
		case R.id.bt_commit:
			Pattern p1 = Pattern.compile("[a-zA-Z0-9~!@#$%^&*_+]{6,16}$");
			Matcher matcher1 = p1.matcher(et_password.getText().toString().trim());
			if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
				tv_error_password.setVisibility(View.VISIBLE);
				tv_error_password.setText("登录密码不能为空");
				return;
			}else if ((et_password.getText().toString().trim().length()<6||et_password.getText().toString().trim().length()>16)) {
				tv_error_password.setVisibility(View.VISIBLE);
				tv_error_password.setText("登录密码长度为6-16位");
				return;
			}else if (!matcher1.matches()) {
				tv_error_password.setVisibility(View.VISIBLE);
				tv_error_password.setText("登录密码不能包含非法字符");
				return;
			}
			else if (TextUtils.isEmpty(verify_password.getText().toString().trim())) {
				tv_error_password.setVisibility(View.GONE);
				tv_error_verify_password.setVisibility(View.VISIBLE);
				tv_error_verify_password.setText("请再次输入登录密码");
				return;
			}else if (!et_password.getText().toString().trim().equals(verify_password.getText().toString().trim())) {
				tv_error_password.setVisibility(View.GONE);
				tv_error_verify_password.setVisibility(View.VISIBLE);
				tv_error_verify_password.setText("登录密码不一致");
				return;
			}
			findpassword(et_password.getText().toString().trim());
			break;

		default:
			break;
		}
		
	}
	private String smsCode;
	private int limitTime;
	private void getSmsCode(){
		SmsRequest request=new SmsRequest();
		request.setMobileNo(tel);
		MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(activity,baseApplication);
        myWeiWeiRequestClient.smsUpdatecode(request, new MyWeiWeiRequestClient.RegisterRequestClientCallback() {
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
	       
            @Override
            protected void smsCode(SmsResult result) {
            	if (getActivity()==null) {
					return;
				}
                //smsCode = result.getSmsCode();
                smsCode= result.getNoteverify();
                limitTime = result.getLimitTime();
                StartTime=result.getTimestamp();
                limitTime = 120;//服务器没有,暂时赋值.
               if (isFirst) {
                	   mHandler.sendEmptyMessage(222);	
                	   isFirst=false;
				}
             //发消息给倒计时
                
                
            }
        });
	}
	protected void findpassword(String password) {
		BaseRequestClient client=new BaseRequestClient(activity);
		final UserResult userResult = baseApplication.getUserResult();
		final String userId=userResult==null?null:userResult.getUserAccount();
		userResult.setUserAccount(tel);
		LoginEntity request=new LoginEntity();
		try {
			request.setUserPassword(DESUtils.encrypt(password, DESUtils.DES_KEY_STRING));
			client.httpPostByJson("Phonefindpasswd", userResult, request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

				@Override
				public void callBack(BaseResult result) {
					if (result!=null) {
						if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
							Toast.makeText(activity, "修改密码成功请登录", Toast.LENGTH_SHORT).show();
							List<UserResult> findAllByWhere = finalDb.findAllByWhere(UserResult.class, "isLogin=1");
							for(UserResult userResult:findAllByWhere){
								userResult.setLogin(false);
			                    finalDb.update(userResult);
							}
							//finalDb.deleteAll(UserResult.class);
							baseApplication.setUserResult(null);
							baseApplication.setCartCount(0);
							activity.finish();
							Intent intent=new Intent(activity, LoginActivity.class);
							intent.putExtra("isFromModifyLoginPassword", true);
							startActivity(intent);
						}else {
							Toast.makeText(activity,result.getMessage(), Toast.LENGTH_SHORT).show();
						}
					}else {
						Toast.makeText(activity, getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
					}
					userResult.setUserAccount(userId);
				}

				@Override
				public void loading(long count, long current) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void logOut(boolean isLogOut,BaseResult result) {FinalDb finalDb=FinalDb.create(activity);
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

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(FindPassword.class.getName(), "EX:"+e);
		}
		
	
	}

	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			mHandler.sendEmptyMessageDelayed(0, 1000);
			if (limitTime>=0) {
				getsms.setText("再次获取"+"（"+limitTime+"）");
				getsms.setBackgroundResource(R.drawable.weiwei_button_gary_gary);
				limitTime--;
			}else {
				getsms.setText("再次获取验证码");
				getsms.setBackgroundResource(R.drawable.weiwei_button_orange_fill);
				isGetSmsCode=true;
				//smsCode="";
			}
		};
	};
	private Button getsms;
	private TextView tv_error_sms;
	private EditText et_sms;
	private LinearLayout ll_step1;
	private LinearLayout ll_step2;
	private TextView tv_tel;
	private Button bt_next;
	private EditText et_password;
	private EditText verify_password;
	private TextView tv_error_verify_password;
	private TextView tv_error_password;
	private Button bt_commit;
	private FinalDb finalDb;

}
