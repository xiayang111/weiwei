package com.dongwukj.weiwei.ui.fragment;


import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.RequestTraceInfoRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.RequestTraceInfoResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;













import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.widget.DragImageView;
import com.dongwukj.weiwei.ui.widget.ZoomImageView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class SourceFragment extends AbstractHeaderFragment implements OnClickListener{
	private TextView businessname,businessidcard,gname,price,realweight,osn,ordertime,paytime,businessconfirmtime,sortingtime,sendtime,receivetime,sendname,paymode;
	private ImageView businessimg;
	private LinearLayout loading_view_container;
	private LinearLayout loadfail_view_container;
	private RequestTraceInfoResult requestTraceInfoResult;
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sourcefragment, null);
		fbBitmap = FinalBitmap.create(activity);
		fbBitmap.configLoadfailImage(R.drawable.default_middle);
		fbBitmap.configLoadingImage(R.drawable.default_middle);
		return view;
	}

	@Override
	protected String setTitle() {
		return "溯源查询";
	}

	@Override
	protected void findView(View v) {
		result = activity.getIntent().getStringExtra("result");
		ll_sendtime = (LinearLayout) v.findViewById(R.id.ll_sendtime);
		ll_paytime = (LinearLayout) v.findViewById(R.id.ll_paytime);
		ll_receivetime = (LinearLayout) v.findViewById(R.id.ll_receivetime);
		
		
		businessname=(TextView) v.findViewById(R.id.businessname);
		businessidcard=(TextView) v.findViewById(R.id.businessidcard);
		gname=(TextView) v.findViewById(R.id.gname);
		price=(TextView) v.findViewById(R.id.price);
		realweight=(TextView) v.findViewById(R.id.realweight);
		 osn=(TextView) v.findViewById(R.id.osn);
		ordertime=(TextView) v.findViewById(R.id.ordertime);
		paytime=(TextView) v.findViewById(R.id.paytime);
		 paymode=(TextView) v.findViewById(R.id.paymode);
		 businessconfirmtime=(TextView) v.findViewById(R.id.businessconfirmtime);
		 sortingtime=(TextView) v.findViewById(R.id.sortingtime);
		 sendtime=(TextView) v.findViewById(R.id.sendtime);
		 receivetime=(TextView) v.findViewById(R.id.receivetime);
		 sendname=(TextView) v.findViewById(R.id.sendname);
		health_img = (TextView) v.findViewById(R.id.health_img);
		business_img = (TextView) v.findViewById(R.id.business_img);
		sanitation_img = (TextView) v.findViewById(R.id.sanitation_img);
		loading_view_container = (LinearLayout) v.findViewById(R.id.loading_view_container);
		loadfail_view_container = (LinearLayout) v.findViewById(R.id.loadfail_view_container);
		businessimg = (ImageView) v.findViewById(R.id.businessimg);
		health_img.setOnClickListener(this);
		business_img.setOnClickListener(this);
		sanitation_img.setOnClickListener(this);
		Button loading_repeat_button=(Button) v.findViewById(R.id.loading_repeat_button);
		loading_repeat_button.setOnClickListener(this);
		RequestTraceInfo(result);
	}

	private void RequestTraceInfo(String result) {
		loading_view_container.setVisibility(View.VISIBLE);
		loadfail_view_container.setVisibility(View.GONE);
		
		RequestTraceInfoRequest requset=new RequestTraceInfoRequest();
		requset.setTracecode(result);
		//requset.setTracecode("000114460903388046");
		BaseRequestClient clien=new BaseRequestClient(activity);
		clien.httpPostByJsonNew("RequestTraceInfo", baseApplication.getUserResult(), requset, RequestTraceInfoResult.class, new BaseRequestClient.RequestClientNewCallBack<RequestTraceInfoResult>() {

			@Override
			public void callBack(RequestTraceInfoResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						requestTraceInfoResult=result;
						mhandler.sendEmptyMessage(100);
					}else {
						loadfail_view_container.setVisibility(View.VISIBLE);
						
					}
				}else {
					loadfail_view_container.setVisibility(View.VISIBLE);
					
				}
			}

			@Override
			public void loading(long count, long current) {
				
			}

			@Override
			public void logOut(RequestTraceInfoResult result) {
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

	private Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (!TextUtils.isEmpty(requestTraceInfoResult.getHealthcertificate())) {
				health_img.setBackgroundResource(R.drawable.button_source);
				health_img.setEnabled(true);
			}else {
				health_img.setBackgroundResource(R.drawable.button_source_disable);
				health_img.setEnabled(false);
			}
			if (!TextUtils.isEmpty(requestTraceInfoResult.getBusinesslicense())) {
				business_img.setBackgroundResource(R.drawable.button_source);
				business_img.setEnabled(true);
			}else {
				business_img.setBackgroundResource(R.drawable.button_source_disable);
				business_img.setEnabled(false);
			}
			if (!TextUtils.isEmpty(requestTraceInfoResult.getHygienelicense())) {
				sanitation_img.setBackgroundResource(R.drawable.button_source);
				sanitation_img.setEnabled(true);
			}else {
				sanitation_img.setBackgroundResource(R.drawable.button_source_disable);
				sanitation_img.setEnabled(false);
			}
			if (TextUtils.isEmpty(requestTraceInfoResult.getReceivetime())) {
				ll_receivetime.setVisibility(View.GONE);
			}else {
				ll_receivetime.setVisibility(View.VISIBLE);
			}
			if (TextUtils.isEmpty(requestTraceInfoResult.getPaytime())) {
				ll_paytime.setVisibility(View.GONE);
			}else {
				ll_paytime.setVisibility(View.VISIBLE);
			}
			if (TextUtils.isEmpty(requestTraceInfoResult.getSendtime())) {
				ll_sendtime.setVisibility(View.GONE);
			}else {
				ll_sendtime.setVisibility(View.VISIBLE);
			}
			businessname.setText(requestTraceInfoResult.getBusinessname());
			businessidcard.setText(requestTraceInfoResult.getBusinessidcard());
			businessidcard.setText(requestTraceInfoResult.getBusinessidcard().substring(0, 3)+"************"+requestTraceInfoResult.getBusinessidcard().substring(requestTraceInfoResult.getBusinessidcard().length()-3, requestTraceInfoResult.getBusinessidcard().length()));
			gname.setText(requestTraceInfoResult.getGname());
			price.setText(requestTraceInfoResult.getPrice()+"元/"+requestTraceInfoResult.getWeight()+"克");
			realweight.setText(requestTraceInfoResult.getRealweight());
			osn.setText(requestTraceInfoResult.getOsn());
			ordertime.setText(requestTraceInfoResult.getOrdertime());
			paytime.setText(requestTraceInfoResult.getPaytime());
			businessconfirmtime.setText(requestTraceInfoResult.getBusinessconfirmtime());
			sortingtime.setText(requestTraceInfoResult.getSortingtime());
			sendtime.setText(requestTraceInfoResult.getSendtime());
			receivetime.setText(requestTraceInfoResult.getReceivetime());
			sendname.setText(requestTraceInfoResult.getSendname());
			paymode.setText(requestTraceInfoResult.getPaymode());
			loading_view_container.setVisibility(View.GONE);
			loadfail_view_container.setVisibility(View.GONE);
			fbBitmap.display(businessimg, requestTraceInfoResult.getBusinessimg());
		};
	};
	
	 protected void showDialogs(String url){
	    	final Dialog dialog = new Dialog(activity, R.style.Dialog);
			LinearLayout view = (LinearLayout) View.inflate(activity, R.layout.source_img, null);
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					  dialog.dismiss();
				}
			});
			ZoomImageView img=(ZoomImageView) view.findViewById(R.id.img);
		    dialog.setContentView(view);
			//dialog.setCancelable(true); //为false 按返回键不能 dismiss Dialog
			WindowManager m = activity.getWindowManager();
		     Window dialogWindow = dialog.getWindow();
		     Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		    WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		     p.height = (int) (d.getHeight() *0.6); // 高度设置为屏幕的0.6
		     p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
		     dialogWindow.setAttributes(p);
		     
		     fbBitmap.display(img,  url);
		     
		     //img.setBackgroundResource(R.drawable.tuxiang);
		    dialog.show();
		    
	    }
	private String result;
	private FinalBitmap fbBitmap;
	private TextView health_img;
	private TextView business_img;
	private TextView sanitation_img;
	private LinearLayout ll_sendtime;
	private LinearLayout ll_paytime;
	private LinearLayout ll_receivetime;
	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.health_img:
			if (!TextUtils.isEmpty(requestTraceInfoResult.getHealthcertificate())) {
				showDialogs(requestTraceInfoResult.getHealthcertificate());
			}
			
			break;
		case R.id.business_img:
			if (!TextUtils.isEmpty(requestTraceInfoResult.getBusinesslicense())) {
				showDialogs(requestTraceInfoResult.getBusinesslicense());
			}
			break;
		case R.id.sanitation_img:
			if (!TextUtils.isEmpty(requestTraceInfoResult.getHygienelicense())) {
				showDialogs(requestTraceInfoResult.getHygienelicense());
			}
			break;
		case R.id.loading_repeat_button:
			RequestTraceInfo(result);
			break;

		default:
			break;
		}
	}

	


}
