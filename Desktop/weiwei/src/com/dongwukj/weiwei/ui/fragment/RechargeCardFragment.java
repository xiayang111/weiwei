package com.dongwukj.weiwei.ui.fragment;

import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.RechargeCardRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

public class RechargeCardFragment extends AbstractHeaderFragment{

	private EditText et_cardnum;
	private EditText et_password;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.rechargecard, null);
	}

	@Override
	protected String setTitle() {
		
		return "充值中心";
	}

	@Override
	protected void findView(View v) {
		et_cardnum = (EditText) v.findViewById(R.id.et_cardnum);
		et_password = (EditText) v.findViewById(R.id.et_password);
		Button bt_true=(Button) v.findViewById(R.id.bt_true);
		bt_true.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (TextUtils.isEmpty(et_cardnum.getText().toString().trim())) {
					Toast.makeText(activity, "请填写充值卡号", Toast.LENGTH_SHORT).show();
				}else if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
					Toast.makeText(activity, "请填写充值密码", Toast.LENGTH_SHORT).show();
				}else {
					recharge();
				}
			}
		});
	
		
		
	}

	private void recharge() {
		BaseRequestClient client=new BaseRequestClient(activity);
		RechargeCardRequest request=new RechargeCardRequest();
		progressDialog.setMessage("充值中。。。");
		progressDialog.show();
		request.setCardNo(et_cardnum.getText().toString().trim());
		request.setPassword(et_password.getText().toString().trim());
		client.httpPostByJson("PhonePrepaidcardPrepaid", baseApplication.getUserResult(), request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

			@Override
			public void callBack(BaseResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						Toast.makeText(activity,"恭喜您充值成功", Toast.LENGTH_SHORT).show();
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
			public void logOut(boolean isLogOut, BaseResult result) {FinalDb finalDb=FinalDb.create(activity);
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
