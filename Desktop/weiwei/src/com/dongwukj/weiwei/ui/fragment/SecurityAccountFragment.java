package com.dongwukj.weiwei.ui.fragment;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.PayPasswordActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SecurityAccountFragment extends AbstractHeaderFragment implements OnClickListener {

	private RelativeLayout boundtel;
	private RelativeLayout modify_loginpassword;
	private RelativeLayout modify_paypassword;
	private TextView tv_tel;
	private TextView tv_3;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.securityaccount_fragment, null);
		return view;
	}

	@Override
	protected String setTitle() {
		return activity.getResources().getString(R.string.securityaccount_title);
	}

	@Override
	protected void findView(View v) {
		String tel = baseApplication.getUserResult().getTel();
		boundtel = (RelativeLayout) v.findViewById(R.id.boundtel);
		modify_loginpassword = (RelativeLayout) v.findViewById(R.id.modify_loginpassword);
		modify_paypassword = (RelativeLayout) v.findViewById(R.id.modify_paypassword);
		tv_tel = (TextView) v.findViewById(R.id.tv_tel);
		tv_tel.setText(tel.substring(0, 3)+"****"+tel.substring(7, 11));
		boundtel.setOnClickListener(this);
		modify_loginpassword.setOnClickListener(this);
		modify_paypassword.setOnClickListener(this);
		tv_3 = (TextView) v.findViewById(R.id.tv_3);
		
	}
	@Override
	public void onResume() {
		super.onResume();
		if (baseApplication.getUserResult().getIsByMoney()==0) {
			tv_3.setText("设置支付密码");
		}else {
			tv_3.setText("修改支付密码");
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.boundtel:
			
			break;
		case R.id.modify_loginpassword:
			openNewActivityWithHeader(HeaderActivityType.ModifyLoginPasswordFragment.ordinal(), true);
			break;
		case R.id.modify_paypassword:
			int i = baseApplication.getUserResult().getIsByMoney();
			if ( i==0) {
				Intent intent=new Intent(activity,PayPasswordActivity.class);
				activity.startActivity(intent);
				//Toast.makeText(activity, "您还没有设置支付密码", Toast.LENGTH_SHORT).show();
				
			}else {
				openNewActivityWithHeader(HeaderActivityType.ResetPayPwdQuestion.ordinal(), true);
				
			}
			break;

		default:
			break;
		}
		
	}
	private void openNewActivityWithHeader(int type,Boolean needLogin){
		Intent intent=new Intent(activity,HomeHeaderActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("needLogin", needLogin);
        startActivity(intent);
   
	}

}
