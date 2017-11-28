package com.dongwukj.weiwei.ui.fragment;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.PhoneUpdateRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.utils.DESUtils;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

public class ResetPayPwdFragment extends AbstractHeaderFragment {

	private Button bt_reset;
	private EditText et_password;
	private EditText verify_password;
	private PhoneUpdateRequest request;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.modify_pay_password_layout, null);
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return activity.getResources().getString(R.string.resetpaypassword);
	}
	private  String md5Pwd;
	
	@Override
	protected void findView(View v) {
		et_password = (EditText) v.findViewById(R.id.et_password);
		verify_password = (EditText) v.findViewById(R.id.verify_password);
		bt_reset = (Button) v.findViewById(R.id.setpassword);
		request = (PhoneUpdateRequest) activity.getIntent().getSerializableExtra("request");
		bt_reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(et_password.getText().toString().trim())&&!TextUtils.isEmpty(verify_password.getText().toString().trim())) {
					Pattern p = Pattern.compile("^[0-9]*[1-9][0-9]*$");
		            Matcher matcher = p.matcher(et_password.getText().toString().trim());
		            boolean matches = matcher.matches();
					if (et_password.getText().toString().length()==6&&matches) {
						if (et_password.getText().toString().trim().equals(verify_password.getText().toString().trim())) {
							try {
								md5Pwd = DESUtils.encrypt(et_password.getText().toString().trim(), DESUtils.DES_KEY_STRING);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								Log.e("error", "error", e);
							}
							request.setPayPassword(md5Pwd);
							
							resetPassword();
						}else {
							Toast.makeText(activity, "密码输入不一致", Toast.LENGTH_SHORT).show();
						}
					}else {
						Toast.makeText(activity, "支付密码必须为6位纯数字", Toast.LENGTH_SHORT).show();
					}
					
				}else {
					Toast.makeText(activity, "支付密码不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}

	private void resetPassword() {
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		progressDialog.setMessage("提交中。。。");
		progressDialog.show();
		client.httpPostByJson("PhoneUpdatePassword", result, request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

			@Override
			public void callBack(BaseResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
                        //重置
						updateUser();
                        Toast.makeText(activity, "支付密码修改成功", Toast.LENGTH_SHORT).show();
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
	}

    private FinalDb finalDb;
    private void updateUser(){
        finalDb = FinalDb.create(activity);
        List<UserResult> findAllByWhere = finalDb.findAllByWhere(UserResult.class, "isLogin=1");
        for(UserResult userResult:findAllByWhere){
           // userResult.setLogin(true);
            userResult.setIsByMoney(1);
            finalDb.update(userResult);
        }
    }

}
