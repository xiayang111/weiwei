package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.FragmentTabAdapter;
import com.dongwukj.weiwei.adapter.FragmentTabAdapter.OnRgsExtraCheckedChangedListener;
import com.dongwukj.weiwei.idea.request.OrderStatisticRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.OrderStatisticsResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

public class OrderListFragment extends AbstractHeaderFragment implements OnClickListener {
	public List<Fragment> list=new ArrayList<Fragment>();
	private int index=0;
	private RadioGroup main_radio;
	private FragmentTabAdapter adapter;
	//private LinearLayout ll_state,ll_state_complete;
	private OrderUndoneFragment undoneFragment;
	//private RadioButton state_all;
	//private RadioButton state_2;
	//private RadioButton state_3,state_1,state_5;
	//private RadioButton state_4;
	private OrderCompleteFragment completeFragment;
	private int orderstate;
	@Override
	protected String setTitle() {
		  return getResources().getString(R.string.my_order_text);
	}

	@Override
	protected void findView(View v) {
		 getStatistics();
		 
		orderstate = activity.getIntent().getIntExtra("orderstate", 0);
		
		main_radio = (RadioGroup) v.findViewById(R.id.main_radio);
		rd_undone = (RadioButton) v.findViewById(R.id.rd_undone);
		rd_complete = (RadioButton) v.findViewById(R.id.rd_complete);
		rd_pingjia = (RadioButton) v.findViewById(R.id.rd_pingjia);
		completeFragment = new OrderCompleteFragment();
		undoneFragment = new OrderUndoneFragment();
		CompleteFragmentEvaluate evaluate = new CompleteFragmentEvaluate();
		
		list.add(undoneFragment);
		list.add(completeFragment);
		list.add(evaluate);
		
		adapter = new FragmentTabAdapter(activity, list, R.id.fm, main_radio,orderstate);
		
		  adapter.setOnRgsExtraCheckedChangedListener(new OnRgsExtraCheckedChangedListener(){
			  @Override
			public void OnRgsExtraCheckedChanged(RadioGroup radioGroup,
					int checkedId, int index) {
				
			}
		  });
		 mhander.sendEmptyMessageDelayed(100, 50);
		 
	}
	private Handler mhander=new Handler(){
		public void handleMessage(Message msg) {
			 showTabByIndex(orderstate);
		};
	};
	private RadioButton rd_undone;
	private RadioButton rd_complete;
	private RadioButton rd_pingjia;
	@Override
	public void onResume() {
		super.onResume();
	}
	
	public void showTabByIndex(int index){
    	if(index!=-1 && adapter!=null){
    		if (index>0) {
    			undoneFragment.NoFirst=false;
			}
    		adapter.showTabByIndex(index);
        }
    }
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View view=inflater.inflate(R.layout.order_list_fragment,container,false);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		
		
	}
	public void getStatistics() {
		BaseRequestClient client = new BaseRequestClient(activity);
		UserResult userResult = baseApplication.getUserResult();
		OrderStatisticRequest Request = new OrderStatisticRequest();
		client.httpPostByJson("Phonewallet", userResult, Request, OrderStatisticsResult.class, new BaseRequestClient.RequestClientCallBack<OrderStatisticsResult>() {

			@Override
			public void callBack(OrderStatisticsResult result) {
				if(result!=null){
					if(result.getCode()==BaseResult.CodeState.Success.getCode()){
						int waitPaying = result.getUnfinishedOrderCount();  			//待支付
						int orderCount = result.getOrderCount();  			//订单总数
						int preProducting = result.getPreProducting();		//待发货
						int sended = result.getFinishedOrderCount();					//待收货
						int completed = result.getUnReviewOrderCount();				//评价
						//tv_order_count.setText(""+orderCount);
						rd_undone.setText("未完成 "+waitPaying);
						//tv_waitsend.setText("待发货\n"+preProducting);
						rd_complete.setText("已完成 "+sended);
						rd_pingjia.setText("待评价 "+completed);
						
					}else{
						if (getActivity()==null) {
							return;
						}
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else{
					if (getActivity()==null) {
						return;
					}
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void logOut(boolean isLogOut,OrderStatisticsResult result) {FinalDb finalDb=FinalDb.create(activity);
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
			
			activity.finish();
			}
	
		});
		
		
	};

}
