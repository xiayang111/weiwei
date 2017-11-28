package com.dongwukj.weiwei.ui.fragment;

import java.util.List;

import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.PhoneUpdateRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.idea.result.VerifyQuestionResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

public class ResetPayPwdQuestion extends AbstractHeaderFragment {
	private PhoneUpdateRequest request;

	private TextView tv_question1;
	private TextView tv_question2;
	//private TextView tv_question3;
	private EditText et_question1;
	private EditText et_question2;
	//private EditText et_question3;
	private VerifyQuestionResult result;
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.verify_password_question_layout, null);
	}

	@Override
	protected String setTitle() {
		return activity.getResources().getString(R.string.resetpaypwdquestion);
	}

	@Override
	protected void findView(View v) {
		tv_question1 = (TextView) v.findViewById(R.id.tv_question1);
		tv_question2 = (TextView) v.findViewById(R.id.tv_question2);
	//	tv_question3 = (TextView) v.findViewById(R.id.tv_question3);
		et_question1 = (EditText) v.findViewById(R.id.et_question1);
		et_question2 = (EditText) v.findViewById(R.id.et_question2);
		//et_question3 = (EditText) v.findViewById(R.id.et_question3);
		Button bt_next=(Button) v.findViewById(R.id.bt_next);
		bt_next.setOnClickListener(new OnClickListener() {
			

			
			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(et_question1.getText().toString().trim())&&!TextUtils.isEmpty(et_question2.getText().toString().trim())) {
					request = new PhoneUpdateRequest();
					request.setQuestionOne(result.getQuestionOne());
					request.setQuestionTwo(result.getQuestionOne());
					//request.setQuestionThree(result.getQuestionOne());
					request.setAnswerOne(et_question1.getText().toString().trim());
					request.setAnswerTwo(et_question2.getText().toString().trim());
					//request.setAnswerThree(et_question3.getText().toString().trim());
					resetPassword();
					
				}else {
					Toast.makeText(activity,"请完成问题", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		getdate();
	}
	private void resetPassword() {
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		client.httpPostByJson("PhoneVerifyAnswer", result, request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

			@Override
			public void callBack(BaseResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						openNewActivityWithHeader(HeaderActivityType.ResetPayPwdFragment.ordinal(), true,request);
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
	private void getdate(){
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		client.httpPostByJson("PhoneVerifyQuestion", result, new BaseRequest(), VerifyQuestionResult.class, new BaseRequestClient.RequestClientCallBack<VerifyQuestionResult>() {

			@Override
			public void callBack(VerifyQuestionResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
                       // updateUser();
						ResetPayPwdQuestion.this.result=result;
						updateHandler.sendEmptyMessage(0);
					}else {
						Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void logOut(boolean isLogOut,VerifyQuestionResult result) {FinalDb finalDb=FinalDb.create(activity);
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
            userResult.setLogin(false);
            userResult.setIsByMoney(1);
            finalDb.update(userResult);
        }
    }

	private void openNewActivityWithHeader(int type,Boolean needLogin,PhoneUpdateRequest request){
		Intent intent=new Intent(activity,HomeHeaderActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("needLogin", needLogin);
        intent.putExtra("request", request);
        startActivityForResult(intent,100);
   
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==100&&resultCode==Activity.RESULT_OK) {
			activity.finish();
		}
	}
	private Handler updateHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			tv_question1.setText(result.getQuestionOne());
			//tv_question2.setText(result.getQuestionTwo());
			//tv_question3.setText(result.getQuestionThree());
		};
	};
}
