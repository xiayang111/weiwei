package com.dongwukj.weiwei.ui.fragment;

import java.util.List;

import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.ExtraDate;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.HomeActivity;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity.PaySucceedBack;
import com.dongwukj.weiwei.ui.activity.LoginActivity;


public class PaySuccessFragment extends AbstractHeaderFragment implements OnClickListener {

	private Button bn_check_order;
	private Button bn_continue_buy;
	private HomeHeaderActivity activity;
	
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pay_success_layout, container, false);
		return view;
	}

	@Override
	protected String setTitle() {
		return getResources().getString(R.string.pay_success);
	}

	@Override
	protected void findView(View v) {
		bn_check_order = (Button) v.findViewById(R.id.bn_check_order);
		bn_continue_buy = (Button) v.findViewById(R.id.bn_continue_buy);
		activity = (HomeHeaderActivity) getActivity();
		//activity.setIsPaysucceed(activity.getIntent().getBooleanExtra("isFromOrder", false));
		activity.setIsPaysucceed(true);
		((ImageView)activity.findViewById(R.id.list_header_leftbutton)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*openNewActivityWithHeader(HeaderActivityType.OrderList.ordinal(),true);
				activity.setIsPaysucceed(false);
				activity.setResult(Activity.RESULT_OK);
				activity.finish();*/
				Intent intent=new Intent(activity,HomeActivity.class);
				intent.putExtra("homeTab",2);
				activity.setIsPaysucceed(false);
				startActivity(intent);
			}
		});
		bn_check_order.setOnClickListener(this);
		bn_continue_buy.setOnClickListener(this);
		//getCartCount();
		activity.setPayBack(new PaySucceedBack() {
			
			@Override
			public void paySucceedBack() {
				Intent intent=new Intent(activity,HomeActivity.class);
				intent.putExtra("homeTab",2);
				activity.setIsPaysucceed(false);
				startActivity(intent);
			}
		});
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bn_check_order:   //查看订单
			
			openNewActivityWithHeader(HeaderActivityType.OrderList.ordinal(),true);
			activity.setIsPaysucceed(false);
			activity.setResult(Activity.RESULT_OK);
			activity.finish();
			break;
		case R.id.bn_continue_buy:  //回首页继续购物
			Intent intent=new Intent(activity,HomeActivity.class);
			intent.putExtra("homeTab",1);
			activity.setIsPaysucceed(false);
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}
	
	/*private void getUserCartCount(){
    	BaseRequestClient client=new BaseRequestClient(activity);
    	client.httpPostByJson("Phonecartlistcount", baseApplication.getUserResult(), new BaseRequest(), ExtraDate.class, new BaseRequestClient.RequestClientCallBack<ExtraDate>() {

			@Override
			public void callBack(ExtraDate result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						baseApplication.setCartCount(result.getTotalCount());
						Intent intent=new Intent(activity,HomeActivity.class);
						intent.putExtra("homeTab",0);
						activity.setIsPaysucceed(false);
						
						startActivity(intent);
						activity.setResult(Activity.RESULT_OK);
						activity.finish();
					}else {
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(activity, getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				   activity.finish();
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}
            @Override
            public void logOut(boolean isLogOut,ExtraDate result) {FinalDb finalDb=FinalDb.create(activity);
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
    }*/
    private void openNewActivityWithHeader(int type,Boolean needLogin){
		Intent intent=new Intent(activity,HomeHeaderActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("needLogin", needLogin);	
        startActivity(intent);
  }
    
}
