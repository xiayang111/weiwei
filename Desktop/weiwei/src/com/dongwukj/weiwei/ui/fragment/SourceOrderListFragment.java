package com.dongwukj.weiwei.ui.fragment;

import java.util.List;

import net.tsz.afinal.FinalDb;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.PhoneGetOrderTraceRequest;
import com.dongwukj.weiwei.idea.result.PhoneGetOrderTraceResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SourceOrderListFragment extends AbstractHeaderFragment {
	
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sourceorderlistfragment, null);
	
		return view;
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "溯源查询";
	}

	@Override
	protected void findView(View v) {
		String Oid = activity.getIntent().getStringExtra("orderId");
		((TextView)v.findViewById(R.id.tv_oid)).setText("订单号："+Oid);
		
		ListView lv=(ListView) v.findViewById(R.id.lv);
		MyBaseAdapter adapter=new MyBaseAdapter();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(activity, HomeHeaderActivity.class);
				intent.putExtra("type", HeaderActivityType.SourceProductFragment.ordinal());
				startActivity(intent);
				
			}
		});
		PhoneGetOrderTrace(Oid);
	}
	private void PhoneGetOrderTrace(String oid) {
		PhoneGetOrderTraceRequest request=new PhoneGetOrderTraceRequest();
		request.setOid(oid);
		BaseRequestClient client=new BaseRequestClient(activity);
		client.httpPostByJsonNew("PhoneGetOrderTrace", baseApplication.getUserResult(), request, PhoneGetOrderTraceResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneGetOrderTraceResult>() {

			@Override
			public void callBack(PhoneGetOrderTraceResult result) {
				
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void logOut(PhoneGetOrderTraceResult result) {
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
	private class MyBaseAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			
			return 3;
		}

		@Override
		public Object getItem(int position) {
			
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(activity, R.layout.sourceorder_product_item, null);
			return view;
		}
		
	}
}
