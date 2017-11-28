package com.dongwukj.weiwei.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.FragmentTabAdapter;
import com.dongwukj.weiwei.adapter.FragmentTabAdapter.OnRgsExtraCheckedChangedListener;
import com.dongwukj.weiwei.idea.request.OrderStatisticRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.OrderStatisticsResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;

import java.util.ArrayList;
import java.util.List;

public class OldOrderListFragment extends AbstractHeaderFragment implements OnClickListener {
	public List<Fragment> list=new ArrayList<Fragment>();
	private int index=0;
	private RadioGroup main_radio;
	private FragmentTabAdapter adapter;
	private LinearLayout ll_state,ll_state_complete;
	private OrderUndoneFragment undoneFragment;
	private RadioButton state_all;
	private RadioButton state_2;
	private RadioButton state_3,state_1,state_5;
	private RadioButton state_4;
	private OrderCompleteFragment completeFragment;
	private int orderstate;
	@Override
	protected String setTitle() {
		  return getResources().getString(R.string.my_order_text);
	}

	@Override
	protected void findView(View v) {
		orderstate = activity.getIntent().getIntExtra("orderstate", 0);
		
		main_radio = (RadioGroup) v.findViewById(R.id.main_radio);
		completeFragment = new OrderCompleteFragment();
		undoneFragment = new OrderUndoneFragment();
		evaluate = new CompleteFragmentEvaluate();
		ll_state = (LinearLayout) v.findViewById(R.id.ll_state);
		ll_state_complete = (LinearLayout) v.findViewById(R.id.ll_state_complete);
		list.add(undoneFragment);
		list.add(completeFragment);
		list.add(evaluate);
		adapter = new FragmentTabAdapter(activity, list, R.id.fm, main_radio,0);

		
		  /*if(index!=-1 && adapter!=null){
			  adapter.showTabByIndex(index);
          }*/
		  adapter.setOnRgsExtraCheckedChangedListener(new OnRgsExtraCheckedChangedListener(){
			  @Override
			public void OnRgsExtraCheckedChanged(RadioGroup radioGroup,
					int checkedId, int index) {
				/*if (index==1) {
					ll_state.setVisibility(View.GONE);
					ll_state_complete.setVisibility(View.VISIBLE);
				}else {
					ll_state.setVisibility(View.VISIBLE);
					ll_state_complete.setVisibility(View.GONE);
				}*/
			}
		  });
		 state_all = (RadioButton) v.findViewById(R.id.state_all);
		 state_2 = (RadioButton) v.findViewById(R.id.state_2);
		 state_3 = (RadioButton) v.findViewById(R.id.state_3);
		 state_4 = (RadioButton) v.findViewById(R.id.state_4);
		 state_1 = (RadioButton) v.findViewById(R.id.state_1);
		 state_5 = (RadioButton) v.findViewById(R.id.state_5);
		 switch (orderstate) {
		case 0:
			state_all.setChecked(true);
			break;
		case 1:
			state_1.setChecked(true);
			break;
		case 2:
			state_2.setChecked(true);
			break;
		case 3:
			state_3.setChecked(true);
			break;
		case 4:
			state_4.setChecked(true);
			break;
		case 5:
			state_5.setChecked(true);
			break;

		default:
			break;
		}
		 state_all.setOnClickListener(this);
		 state_2.setOnClickListener(this);
		 state_3.setOnClickListener(this);
		 state_4.setOnClickListener(this);
		 state_1.setOnClickListener(this);
		 state_5.setOnClickListener(this);
		 
		 
		updateUiHandler.sendEmptyMessageDelayed(100,100);
		 
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		getOrderState();
		super.onResume();
	}
	private Handler updateUiHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (orderstate==5) {
				adapter.showTabByIndex(1);
				ll_state.setVisibility(View.GONE);
				ll_state_complete.setVisibility(View.VISIBLE);
				completeFragment.changeOrderState(orderstate);
			}else {
				adapter.showTabByIndex(0);
				undoneFragment.changeOrderState(orderstate);
				ll_state.setVisibility(View.VISIBLE);
				ll_state_complete.setVisibility(View.GONE);
			}
		}
	};
	private CompleteFragmentEvaluate evaluate;
	
	public void showTabByIndex(int index){
    	if(index!=-1 && adapter!=null){
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
		switch (v.getId()) {
		case R.id.state_all:
			undoneFragment.changeOrderState(0);
			break;
		case R.id.state_2:
			undoneFragment.changeOrderState(2);
			break;
		case R.id.state_3:
			undoneFragment.changeOrderState(3);
			break;
		case R.id.state_4:
			undoneFragment.changeOrderState(4);
			break;
		case R.id.state_1:
			completeFragment.changeOrderState(1);
			break;
		case R.id.state_5:
			completeFragment.changeOrderState(5);
			break;

		default:
			break;
		}
	}
	public void getOrderState(){	
		
		BaseRequestClient client = new BaseRequestClient(activity);
		UserResult userResult = baseApplication.getUserResult();
		OrderStatisticRequest Request = new OrderStatisticRequest();
		client.httpPostByJson("Phonewallet", userResult, Request, OrderStatisticsResult.class, new BaseRequestClient.RequestClientCallBack<OrderStatisticsResult>() {

			@Override
			public void callBack(OrderStatisticsResult result) {
				if(result!=null){
					if(result.getCode()==BaseResult.CodeState.Success.getCode()){
						int waitPaying = result.getWaitPaying();  			//待支付
						int orderCount = result.getOrderCount();  			//订单总数
						int preProducting = result.getPreProducting();		//待发货
						int sended = result.getSended();					//待收货
						int completed = result.getCompleted();				//评价
						//tv_order_count.setText(""+orderCount);
						state_2.setText("待付款 "+waitPaying);
						state_3.setText("待发货 "+preProducting);
						state_all.setText("全部 "+(result.getSended()+preProducting+waitPaying));
						state_4.setText("待收货 "+result.getSended());
						state_1.setText("全部 "+(result.getOrderCount()-result.getPreProducting()-result.getWaitPaying()-result.getSended()));
						state_5.setText("待评价 "+result.getCompleted());
						
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
			public void logOut(boolean isLogOut, OrderStatisticsResult result) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	
	}
}
