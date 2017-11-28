package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.PurseRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.CouponListEntity;
import com.dongwukj.weiwei.idea.result.CouponListResult;
import com.dongwukj.weiwei.idea.result.PurseResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.activity.PayPasswordActivity;

import net.tsz.afinal.FinalDb;

public class PurseFragment extends AbstractHeaderFragment implements OnClickListener {
	private View view;
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.fragment_purse, null);
		 
		return view;
	}

	@Override
	protected String setTitle() {
		String name=activity.getResources().getString(R.string.purse_title);
		return name;
	}

	@Override
	protected void findView(View v) {
		tv_balance = (TextView) view.findViewById(R.id.tv_balance);
		tv_coupon = (TextView) view.findViewById(R.id.tv_coupon);
		tv_integral = (TextView) view.findViewById(R.id.tv_integral);
		ll_safe_question = (LinearLayout) view.findViewById(R.id.ll_safe_question);
	
		rl_coupon = (RelativeLayout) view.findViewById(R.id.rl_coupon);//优惠券
		rl_yue = (RelativeLayout) view.findViewById(R.id.rl_yue);//优惠券
		rl_coupon.setOnClickListener(this);
		ll_ll_recharge=(LinearLayout) view.findViewById(R.id.ll_recharge);
		ll_safe_question.setOnClickListener(this);
		ll_ll_recharge.setOnClickListener(this);
		rl_yue.setOnClickListener(this);
/*		Button bt=(Button) view.findViewById(R.id.resetpwd);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openNewActivityWithHeader(HeaderActivityType.ResetPayPwdQuestion.ordinal(), true);
			}
		});*/
		getData();
	
	}
	private void openNewActivityWithHeader(int type,Boolean needLogin){
		Intent intent=new Intent(activity,HomeHeaderActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("needLogin", needLogin);
        startActivity(intent);
   
	}
	@Override
	public void onResume() {
		//如果已经设置了支付密码,把设置支付密码的条目隐藏
		super.onResume();
		if(baseApplication.getUserResult().getIsByMoney()==1){
			ll_safe_question.setVisibility(View.GONE);
		}
		updateDataHandler.sendEmptyMessage(102);
	}
	private Handler updateDataHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 101:
				PurseResult result=(PurseResult)msg.obj;
				tv_balance.setText("¥"+result.getAccount());
				
				//tv_coupon.setText(result.getCoupons());
				tv_integral.setText(result.getPayCredits());
				break;

			case 102:
				getCouponList();
				
				break;
			case 103:
				int size = (Integer) msg.obj;
				tv_coupon.setText(size+"");
				break;
			default:
				break;
			}

		};
	};
	private TextView tv_balance;
	private TextView tv_coupon;
	private TextView tv_integral;
	private LinearLayout ll_safe_question,ll_ll_recharge;
	private RelativeLayout rl_coupon,rl_yue;
	private SharedPreferences sp;
	private void getData(){
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult userResult=baseApplication.getUserResult();
		PurseRequest request=new PurseRequest();

        MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(activity,baseApplication);
        myWeiWeiRequestClient.purseList(request, new MyWeiWeiRequestClient.PurseRequestClientCallback() {
            @Override
            protected void list(PurseResult result) {
            	if (getActivity()==null) {
					return;
				}
                Message msg=Message.obtain();
                msg.obj=result;
                msg.what = 101;
                updateDataHandler.sendMessage(msg);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_safe_question:
			if (baseApplication.getUserResult().getIsByMoney()==1){
				Toast.makeText(activity, "您已设置支付密码", Toast.LENGTH_SHORT).show();
				
			}else {
				Intent intent = new Intent(activity,PayPasswordActivity.class);
				startActivity(intent);		
			}
				
			break;

		case R.id.rl_coupon:
			openNewActivityWithHeader(HeaderActivityType.CouPon.ordinal(), true);
			break;
		case R.id.rl_yue:
			openNewActivityWithHeader(HeaderActivityType.AccountBalance.ordinal(), true);
			break;
			
		case R.id.ll_recharge:
			openNewActivityWithHeader(HeaderActivityType.Recharge.ordinal(), true);
			break;
		default:
			break;
		}
		
	}
	private void getCouponList() {
		BaseRequestClient client = new BaseRequestClient(activity);
		UserResult userResult = baseApplication.getUserResult();
		BaseRequest request = new BaseRequest();
		client.httpPostByJson("PhoneGetUserCouponList", userResult, request, CouponListResult.class, new BaseRequestClient.RequestClientCallBack<CouponListResult>() {

			

			@Override
			public void callBack(CouponListResult result) {
				if(result!=null){
					if(result.getCode()==BaseResult.CodeState.Success.getCode()){
						if (result.getCouponList()!=null&&result.getCouponList().size()!=0) {
							int size = result.getCouponList().size();
							Message msg = Message.obtain();
							msg.what = 103;
							msg.obj = size;
							updateDataHandler.sendMessage(msg);
							//tv_coupon.setText(size+"");s
							
						}else {
							tv_coupon.setText("");
						}
						
					}else{
                        Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
				}else{
                    Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
				
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void logOut(boolean isLogOut,CouponListResult result) {FinalDb finalDb=FinalDb.create(activity);
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
