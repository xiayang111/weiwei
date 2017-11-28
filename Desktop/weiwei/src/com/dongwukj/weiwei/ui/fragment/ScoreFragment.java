package com.dongwukj.weiwei.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.RaffleResult;
import com.dongwukj.weiwei.idea.result.SinginResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

import net.tsz.afinal.FinalDb;

import java.util.List;

public class ScoreFragment extends AbstractHeaderFragment implements OnClickListener {

	private ImageView iv_lottery;
	private ImageView iv_sign;
	private String lotteryUrl;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.score_layout, container, false);
		return view;
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return getResources().getString(R.string.score_title);
	}

	@Override
	protected void findView(View v) {
		iv_lottery = (ImageView) v.findViewById(R.id.iv_lottery);
		iv_sign = (ImageView) v.findViewById(R.id.iv_sign);
		iv_lottery.setOnClickListener(this);
		iv_sign.setOnClickListener(this);
		
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_lottery:
			raffle();
			break;

		case R.id.iv_sign:
			sign_in();
			break;
		default:
			break;
		}
	}

	private void sign_in() {
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		client.httpPostByJson("PhoneUserSingin", result,new BaseRequest(), SinginResult.class, new BaseRequestClient.RequestClientCallBack<SinginResult>() {

			@Override
			public void callBack(SinginResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						showdialog(result);
					}else {
						//showdialog( result);
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(activity,activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void logOut(boolean isLogOut,SinginResult result) {FinalDb finalDb=FinalDb.create(activity);
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
	
	private int raffle_count;
	
	private void raffle(){
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult userResult = baseApplication.getUserResult();
		BaseRequest request = new BaseRequest();
		client.httpPostByJson("PhoneGetlottery", userResult, request, RaffleResult.class, new BaseRequestClient.RequestClientCallBack<RaffleResult>() {



			@Override
			public void callBack(RaffleResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						lotteryUrl = result.getLotteryUrl();
						raffle_count = result.getRemainingLotteryCount();
						String userAccount = baseApplication.getUserResult().getUserAccount();
						lotteryUrl = lotteryUrl+userAccount;
						//Toast.makeText(activity, lotteryUrl, Toast.LENGTH_SHORT).show();
						if(raffle_count<=0){
							Toast.makeText(activity, "您今天的抽奖次数已经用完,请明天再试!", Toast.LENGTH_SHORT).show();
						}else{
							Intent intent = new Intent(activity,HomeHeaderActivity.class);
							intent.putExtra("needLogin", true);
							intent.putExtra("hasHeader", false);
							intent.putExtra("lotteryUrl",lotteryUrl);
							intent.putExtra("type", HeaderActivityType.RaffleWebViewFragment.ordinal());
							startActivity(intent);
						}
						
					}else {
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(activity,activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void logOut(boolean isLogOut,RaffleResult result) {FinalDb finalDb=FinalDb.create(activity);
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

	protected void showdialog(SinginResult result) {
		final Dialog dialog=new Dialog(activity, R.style.Dialog);
		RelativeLayout view=(RelativeLayout) View.inflate(activity, R.layout.sign_dialog, null);
		dialog.setContentView(view);
		dialog.setCancelable(false);
		TextView tv_payCredits=(TextView) view.findViewById(R.id.tv_payCredits);
		TextView tv_sumpayCredits=(TextView) view.findViewById(R.id.tv_sumpayCredits);
		tv_payCredits.setText("今日已领取"+result.getPayCredits()+"积分,明日签到可领取"+result.getPayCredits()+"积分");
		tv_sumpayCredits.setText(""+result.getSumpayCredits());
		ImageButton bt=(ImageButton) view.findViewById(R.id.bt);
		bt.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		 WindowManager m = activity.getWindowManager();
	        Window dialogWindow = dialog.getWindow();
	        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
	        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
	        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
	        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
	        dialogWindow.setAttributes(p);
		dialog.show();
	}

}
