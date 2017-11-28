package com.dongwukj.weiwei.ui.fragment;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.PhoneGetAccountBalanceRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.PhoneGetAccountBalanceResult;
import com.dongwukj.weiwei.idea.result.PhoneGetAccountBalanceResult.BalanceLogs;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class AccountBalanceFragment extends AbstractHeaderFragment {

	protected static final int PULL_FORM_UP = 100;
	protected static final int PULL_FORM_DOWN =200;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.accountbalancefragment, null);
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "账户余额";
	}

	@Override
	protected void findView(View v) {
		tv_yue = (TextView) v.findViewById(R.id.yue);
		lv = (PullToRefreshListView) v.findViewById(R.id.lv);
		lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mHandler.sendEmptyMessage(PULL_FORM_UP);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mHandler.sendEmptyMessage(PULL_FORM_DOWN);
			}
		});
		adapter = new MyAdapter();
		lv.setAdapter(adapter);
		mHandler.sendEmptyMessageDelayed(300, 500);
	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PULL_FORM_UP:
				getPhoneGetAccountBalance(true);
				break;
			case PULL_FORM_DOWN:
				getPhoneGetAccountBalance(false);
				break;
			case 300:
				lv.setMode(Mode.PULL_FROM_START);
				lv.setRefreshing();
				break;
			default:
				break;
			}
		};
	};
	
	private ArrayList<BalanceLogs> balanceLogs=new ArrayList<BalanceLogs>();
	private  PullToRefreshListView lv;
	private TextView tv_yue;	
	private int pageIndex=1;
	private MyAdapter adapter;
	private void getPhoneGetAccountBalance(final boolean isFirst) {
		PhoneGetAccountBalanceRequest request=new PhoneGetAccountBalanceRequest();
		if (isFirst) {
			request.setPageIndex(1);
		}else {
			request.setPageIndex(++pageIndex);
		}
		BaseRequestClient client=new BaseRequestClient(activity);
		client.httpPostByJsonNew("PhoneGetAccountBalance", baseApplication.getUserResult(), request, PhoneGetAccountBalanceResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneGetAccountBalanceResult>() {

			@Override
			public void callBack(PhoneGetAccountBalanceResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						tv_yue.setText(String.format("%.2f",result.getBalance()));
						if (result.getBalanceLogs().size()>0) {
							if (isFirst) {
								balanceLogs.clear();
								adapter.notifyDataSetChanged();
								balanceLogs.addAll(result.getBalanceLogs());
								adapter.notifyDataSetChanged();
								pageIndex=1;
							}else {
								balanceLogs.addAll(result.getBalanceLogs());
								adapter.notifyDataSetChanged();
							}
							lv.onRefreshComplete();
			 				if (balanceLogs.size()>=result.getListNumber()) {
			 					lv.setMode(Mode.PULL_FROM_START);
							}else {
								lv.setMode(PullToRefreshBase.Mode.BOTH);
							}
						}else {
							showtoast("暂没有消费记录", activity);
						}
						
					}else {
						showtoast(result.getMessage(), activity);
					}
					lv.onRefreshComplete();
				}else {
					showtoast(activity.getResources().getString(R.string.data_error), activity);
					lv.onRefreshComplete();
				}
				
			}

			@Override
			public void loading(long count, long current) {
				
				
			}

			@Override
			public void logOut(PhoneGetAccountBalanceResult result) {
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

	class MyAdapter extends BaseAdapter{
		private ViewHolder holder;
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return balanceLogs.size();
		}

		@Override
		public Object getItem(int paramInt) {
			
			return balanceLogs.get(paramInt);
		}

		@Override
		public long getItemId(int paramInt) {
			return paramInt;
		}

		@Override
		public View getView(int paramInt, View paramView,
				ViewGroup paramViewGroup) {
			if (paramView==null) {
				paramView=View.inflate(activity, R.layout.accountbalancefragment_item, null);
				holder=new ViewHolder();
				holder.tv_osn=(TextView) paramView.findViewById(R.id.tv_osn);
				holder.tv_money=(TextView) paramView.findViewById(R.id.tv_money);
				holder.tv_date=(TextView) paramView.findViewById(R.id.tv_date);
				holder.tv_operate=(TextView) paramView.findViewById(R.id.tv_operate);
				paramView.setTag(holder);
			}else {
				holder=(ViewHolder) paramView.getTag();
			}
			
			BalanceLogs balanceLog = balanceLogs.get(paramInt);
			if (balanceLog.getMoratorium().startsWith("-")) {
				holder.tv_money.setTextColor(activity.getResources().getColor(R.color.red));
			}else {
				holder.tv_money.setTextColor(activity.getResources().getColor(R.color.green));
			}
			holder.tv_osn.setText(balanceLog.getOsn());
			holder.tv_money.setText(balanceLog.getMoratorium());
			holder.tv_date.setText(dateF(balanceLog.getLogtime()));
			holder.tv_operate.setText(balanceLog.getPaysystemname());
			return paramView;
		}
		
	}
	private String dateF(String time){
		SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sm.format(Long.parseLong(time));
		return format;
	}
	class ViewHolder{
		public TextView tv_osn; 
		public TextView tv_money; 
		public TextView tv_date; 
		public TextView tv_operate; 
	}
}
