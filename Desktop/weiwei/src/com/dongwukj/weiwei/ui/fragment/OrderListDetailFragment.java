package com.dongwukj.weiwei.ui.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.OrderDetailItemAdapter;
import com.dongwukj.weiwei.adapter.ParentCartListAdapter;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.OrderAffirmRequset;
import com.dongwukj.weiwei.idea.request.OrderCancleRequest;
import com.dongwukj.weiwei.idea.request.OrderDetailRequest;
import com.dongwukj.weiwei.idea.request.PhoneOrderExtendReceivingRequest;
import com.dongwukj.weiwei.idea.request.PurseRequest;
import com.dongwukj.weiwei.idea.result.*;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.widget.ChildCartListView;
import com.dongwukj.weiwei.ui.widget.MaxListView;
import com.dongwukj.weiwei.ui.widget.MyListView;
import com.dongwukj.weiwei.ui.widget.ParentCartListView;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;


public class OrderListDetailFragment extends AbstractHeaderFragment {

	protected static final int PAY = 200;
	private TextView tv_order_state;
//	private TextView tv_order_sum;
	private TextView tv_orderNum;
	private TextView tv_time;
	private TextView tv_address;
	private TextView tv_phone;
	private Button bt_right;
	private List<OrderProductInfo> list;
	private int IsReview=0; 
	private ArrayList<NewOrderProductEntity> showList;
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.orderlistdetail_fragment, null);
		return view;
	}

	@Override
	protected String setTitle() {
		return activity.getResources().getString(R.string.orderlistdetails_title);
	}

	@Override
	protected void findView(View v) {
		fb = FinalBitmap.create(activity);
        fb.configLoadingImage(R.drawable.default_small);
        fb.configLoadfailImage(R.drawable.default_small);
		tv_order_state = (TextView) v.findViewById(R.id.tv_order_state);
		//tv_order_sum = (TextView) v.findViewById(R.id.tv_order_sum);
		tv_orderNum = (TextView) v.findViewById(R.id.tv_ordernum);
		tv_name = (TextView) v.findViewById(R.id.tv_name);
		tv_sendtype = (TextView) v.findViewById(R.id.tv_sendtype);
		tv_time = (TextView) v.findViewById(R.id.tv_time);
		tv_address = (TextView) v.findViewById(R.id.tv_address);
		tv_phone = (TextView) v.findViewById(R.id.tv_phone);
		bt_right = (Button) v.findViewById(R.id.bt_right);
	
		lv = (MyListView) v.findViewById(R.id.lv);
		tv_shopprice = (TextView) v.findViewById(R.id.tv_shopprice);
		goodsCount = (TextView) v.findViewById(R.id.goodsCount);
		tv_discount = (TextView) v.findViewById(R.id.tv_discount);
		tv_full_cut = (TextView) v.findViewById(R.id.tv_full_cut);
		tv_freight = (TextView) v.findViewById(R.id.tv_freight);
		tv_discount_balance = (TextView) v.findViewById(R.id.tv_discount_balance);
		tv_realprice = (TextView) v.findViewById(R.id.tv_realprice);
		tv_payment = (TextView) v.findViewById(R.id.tv_payment);
		rl_rebate = (RelativeLayout) v.findViewById(R.id.rl_rebate);
		ll_rebate = (LinearLayout) v.findViewById(R.id.ll_rebate);
		ll_discount_balance = (LinearLayout) v.findViewById(R.id.ll_discount_balance);
		tv_fankuan = (TextView) v.findViewById(R.id.tv_fankuan);
		lv_action = (MaxListView) v.findViewById(R.id.lv_action);
		 DataBase db=LiteOrm.newCascadeInstance(activity, BaseApplication.DB_NAME);
		 entityForlist = db.queryAll(NewOrderEntity.class).get(0);
		 
		 showList=entityForlist.getProducts();
		IsReview=entityForlist.getIsreview();
		fillOrderList();
		
		
		activity = (HomeHeaderActivity) activity;
		changeView.sendEmptyMessage(entityForlist.getOrderstate());
	}
	private void openNewActivityWithHeader(int type,Boolean needLogin){
			Intent intent=new Intent(activity,HomeHeaderActivity.class);
	        intent.putExtra("type",type);
	        intent.putExtra("needLogin", needLogin);
	        DataBase db=LiteOrm.newCascadeInstance(activity, BaseApplication.DB_NAME);
	        ArrayList<OrderProductEntity> all = db.queryAll(OrderProductEntity.class);
	        for (OrderProductEntity orderProductEntity : all) {
	        	db.delete(orderProductEntity);
			}
	        db.save(entityForlist);
	       // intent.putExtra("detail_entity", entityForlist);
	       startActivityForResult(intent, 100);
	    }
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==100&&resultCode==Activity.RESULT_OK) {
			IsReview=1;
			((HomeHeaderActivity)activity).setIsNeedRefresh(true);
			changeView.sendEmptyMessage(140);
		}else if (requestCode==PAY&&resultCode==Activity.RESULT_OK) {
			activity.setResult(Activity.RESULT_OK);
			activity.finish();
		}
	}
	  private void fillOrderList(){
		  goodsCount.setText("("+entityForlist.getGoodsCount()+")");
		  OrderDetailItemAdapter adapter=new OrderDetailItemAdapter(activity, entityForlist.getProducts(),entityForlist.getOrderstate());
		  lv.setAdapter(adapter);
		  ActionAdapter actionAdapter = new ActionAdapter();
		  orderactionList = entityForlist.getOrderactionList();
		  if (orderactionList!=null&&orderactionList.size()>0) {
			  lv_action.setAdapter(actionAdapter);
		}
		  
		 }
	  class ActionAdapter extends BaseAdapter{
		  private ViewHolder holder;
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return orderactionList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView==null) {
				convertView=View.inflate(activity, R.layout.action_item, null);
				holder=new ViewHolder();
				holder.tv_actionname=(TextView) convertView.findViewById(R.id.tv_actionname);
				holder.tv_date=(TextView) convertView.findViewById(R.id.tv_date);
				convertView.setTag(holder);
			}else {
				holder=(ViewHolder) convertView.getTag();
			}
		
				holder.tv_actionname.setText(orderactionList.get(position).getActiondes());
				holder.tv_date.setText(formatTime(orderactionList.get(position).getActiontime()));
			return convertView;
			
		}
		  
	  }
	  class ViewHolder{
		  private TextView tv_date;
		  private TextView tv_actionname;
		 
	  }
	private void getData(){
		
		PurseRequest request=new PurseRequest();

        MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(activity,baseApplication);
        myWeiWeiRequestClient.purseList(request, new MyWeiWeiRequestClient.PurseRequestClientCallback() {
            @Override
            protected void list(PurseResult result) {
            	if (getActivity()==null) {
					return;
				}
            	  Intent intent=new Intent(activity,HomeHeaderActivity.class);
				  intent.putExtra("type",HeaderActivityType.PayOrder.ordinal());
	                intent.putExtra("result",phonecommitorderResult);
	                intent.putExtra("yue",Float.parseFloat(result.getAccount()));
	                intent.putExtra("payList", result.getPayPluginList());
	                intent.putExtra("isFromOrder", true);
	                startActivityForResult(intent, PAY);
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
	private PhonecommitorderResult phonecommitorderResult;
	private void changeButton(int orderState) {
		switch (orderState) {
		case 70:
			ll_rebate.setVisibility(View.GONE);
			bt_right.setVisibility(View.GONE);
			break;
		case 80:
			ll_rebate.setVisibility(View.GONE);
			bt_right.setVisibility(View.GONE);
			break;
		case 85:
			
	
			break;
		case 90:
			bt_right.setVisibility(View.VISIBLE);
			bt_right.setText("收货码:"+entityForlist.getRecode());
			
		
			break;
		case 120:
			bt_right.setVisibility(View.VISIBLE);
			bt_right.setText("收货码:"+entityForlist.getRecode());
			
		
			break;
		case 110:
			bt_right.setVisibility(View.VISIBLE);
			bt_right.setText("收货码:"+entityForlist.getRecode());
		
			break;
		case 140:
//			bt_right.setVisibility(View.VISIBLE);
//			bt_right.setText("去评价");
			
			break;
		case 145:
//			bt_right.setVisibility(View.VISIBLE);
//			bt_right.setText("去评价");
			
			break;
		case 150:
		
			break;
			case 160:
				break;
			case 200:
				bt_right.setVisibility(View.GONE);
				break;
			case 210:
				bt_right.setVisibility(View.GONE);
				break;
		default:
			break;
		}
		
	}
	private RadioButton tomorrow_pm;
	private RadioButton after_tomorrow_am;
	private RadioButton tomorrow_am;
    private int index=0;
    private String DeliveryEndTime;
    private ArrayList<TimeConfigLis> deliveryTimeConfigList;
	




	/*protected void orderAffirm() {
		BaseRequestClient client=new BaseRequestClient(activity);
		OrderAffirmRequset request=new OrderAffirmRequset();
		request.setIp(NetworkUtil.getLocalIpAddress());
		request.setOid(entityForlist.getOid());
		client.httpPostByJson("Phoneordercommit", baseApplication.getUserResult(), request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

			@Override
			public void callBack(BaseResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						changeView.sendEmptyMessage(140);
						((HomeHeaderActivity)activity).setIsNeedRefresh(true);
					}else {
						Toast.makeText(activity,result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(activity, getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
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
	}*/

	protected void orderCancel() {
		MyWeiWeiRequestClient client=new MyWeiWeiRequestClient(activity, baseApplication);
		OrderCancleRequest request=new OrderCancleRequest();
		request.setOid(entityForlist.getOid());
		request.setIp(NetworkUtil.getLocalIpAddress());
		client.orderCancle(request, new MyWeiWeiRequestClient.OrderListDetailRequestCallback() {
			@Override
			protected void cancle(BaseResult result) {
				if (getActivity()==null) {
					return;
				}
				//changeView.sendEmptyMessage(200);
				activity.setResult(Activity.RESULT_OK, new Intent());
				activity.finish();
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
	protected void orderDelete() {
		MyWeiWeiRequestClient client=new MyWeiWeiRequestClient(activity, baseApplication);
		OrderCancleRequest request=new OrderCancleRequest();
		request.setOid(entityForlist.getOid());
		//request.setIp(NetworkUtil.getLocalIpAddress());
		client.orderDelete(request, new MyWeiWeiRequestClient.OrderListDetailRequestCallback() {
			protected void delete(BaseResult result) {
				if (getActivity()==null) {
					return;
				}
				activity.setResult(Activity.RESULT_OK);
				activity.finish();
				Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
			};
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
	

	private String formatTime(String time){
		
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String data=simpleDateFormat.format(Long.parseLong(time));
		return data;
	}

	private Handler changeView=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			float rebateamount=0;
			ArrayList<NewOrderProductEntity> products = entityForlist.getProducts();
			for (NewOrderProductEntity newOrderProductEntity : products) {
				rebateamount=rebateamount+newOrderProductEntity.getRebateamount();
			}
			if (entityForlist.getOrderstate()==210) {
				tv_fankuan.setText(String.format("￥%.2f", entityForlist.getCancelbackamount()));
			}else {
				tv_fankuan.setText(String.format("￥%.2f", entityForlist.getCashbackamount()));
			}
			if (entityForlist.getDiscount()>0) {
				ll_discount_balance.setVisibility(View.VISIBLE);
				tv_discount_balance.setText(String.format("-%.2f", entityForlist.getDiscount()));
			}else {
				ll_discount_balance.setVisibility(View.GONE);
			}
			tv_orderNum.setText(entityForlist.getOsn());
			tv_time.setText(entityForlist.getBesttimestr());
			tv_address.setText("武汉市"+entityForlist.getPlot().getRegionName().trim()+entityForlist.getAddress());
			tv_phone.setText(entityForlist.getMobile());
			tv_name.setText(entityForlist.getConsignee());
			if (entityForlist.getShiptype()==1) {
				tv_sendtype.setText("送货上门");
			}else {
				tv_sendtype.setText("自提（"+entityForlist.getSinceaddress()+"）");
			}
			
				
				if (entityForlist.getShipfee()>0) {
					tv_freight.setText(String.format("+%.2f", entityForlist.getShipfee()));
				}else {
					tv_freight.setText("0.00");
				}
				
				tv_realprice.setText(String.format("￥%.2f", entityForlist.getSurplusmoney()));
				if (entityForlist.getCouponmoney()>0) {
					tv_discount.setText(String.format("-%.2f",entityForlist.getCouponmoney()));
				}else {
					tv_discount.setText("0.00");
				}
				if (entityForlist.getFullcut()>0) {
					tv_full_cut.setText(String.format("-%.2f",entityForlist.getFullcut()));
				}else {
					tv_full_cut.setText("0.00");
				}
				
				
				
				
			tv_shopprice.setText(String.format("￥%.2f",entityForlist.getDiscountbeforeproductprice()));
			
			switch (msg.what) {
			case 20:
				tv_order_state.setText("订单支付中");
				tv_order_state.setTextColor(activity.getResources().getColor(R.color.left_category_select_text_color));
				break;
			case 70:
				tv_order_state.setText("已付款");
				tv_order_state.setTextColor(activity.getResources().getColor(R.color.left_category_select_text_color));
				break;
			case 75:
				tv_order_state.setText("申请平台服务");
				tv_order_state.setTextColor(activity.getResources().getColor(R.color.red));
				break;
			case 80:
				tv_order_state.setText("订单已确认");
				tv_order_state.setTextColor(activity.getResources().getColor(R.color.left_category_select_text_color));
				break;
			case 85:
				tv_order_state.setText("完成分拣");
				tv_order_state.setTextColor(activity.getResources().getColor(R.color.left_category_select_text_color));
				break;
			case 90:
				tv_order_state.setText("配送中");
				tv_order_state.setTextColor(activity.getResources().getColor(R.color.left_category_select_text_color));
				break;
			case 120:
				tv_order_state.setText("已经到达自提点");
				tv_order_state.setTextColor(activity.getResources().getColor(R.color.left_category_select_text_color));
				break;
			case 110:
				tv_order_state.setText("已配送");
				tv_order_state.setTextColor(activity.getResources().getColor(R.color.red));
				break;
			case 150:
				tv_order_state.setText("换货 ");
				tv_order_state.setTextColor(activity.getResources().getColor(R.color.red));
				break;
			case 160:
				tv_order_state.setText("退货");
				tv_order_state.setTextColor(activity.getResources().getColor(R.color.red));
				break;
			case 145:
				if (IsReview==0) {
					tv_order_state.setText("交易完成");
				}else {
					tv_order_state.setText("交易完成(已评价)");
				}
				tv_order_state.setTextColor(activity.getResources().getColor(R.color.red));
				break;
			case 200:
				tv_order_state.setText("订单已取消（未退款）");
				break;
			case 210:
				tv_order_state.setText("订单已取消（已退款）");
				break;

			default:
				break;
			}
			changeButton(msg.what);
		};
	};
	private FinalBitmap fb;
	private HomeHeaderActivity activity2;

	private TextView tv_name,tv_sendtype;
	private TextView tv_shopprice,goodsCount;
	private TextView tv_discount,tv_full_cut;
	private TextView tv_freight,tv_discount_balance;
	private TextView tv_realprice;
	private TextView tv_payment,tv_fankuan;
	private MyListView lv;
	private NewOrderEntity entityForlist;
	private RelativeLayout rl_rebate;
	private LinearLayout ll_rebate,ll_discount_balance;
	private MaxListView lv_action;
	private ArrayList<Orderaction> orderactionList;
	
	/* public class ViewHolder{
	        public TextView tv_ordername;
	        public ImageView iv_ordericon;
	        public TextView tv_orderprice;
	        public TextView tv_buycount;
	        public TextView tv_weight;
	        
			public ViewHolder(View view) {
				iv_ordericon=(ImageView) view.findViewById(R.id.iv_ordericon);
				tv_ordername=(TextView) view.findViewById(R.id.tv_ordername);
				tv_orderprice=(TextView) view.findViewById(R.id.tv_orderprice);
				tv_buycount=(TextView) view.findViewById(R.id.tv_buycount);
				tv_weight=(TextView) view.findViewById(R.id.tv_weight);
			}
	        
	    }*/
}
