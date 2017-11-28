package com.dongwukj.weiwei.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.OrderListAdapter;
import com.dongwukj.weiwei.adapter.OrderListAdapter.buttonClick;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.OrderAffirmRequset;
import com.dongwukj.weiwei.idea.request.OrderDetailRequest;
import com.dongwukj.weiwei.idea.request.OrderListRequest;
import com.dongwukj.weiwei.idea.request.PurseRequest;
import com.dongwukj.weiwei.idea.result.*;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;

import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2015/3/17.
 */
public class OrderUndoneFragment extends BaseFragment {
	protected static final int PULL_EMPTY = 0;
	protected static final int PULL_DOWN_MODE = 1;
	protected static final int PULL_UP_MODE = 2;
	protected static final int pay = 200;
	private PullToRefreshListView orderListView;
    private List<NewOrderEntity> orderList;
    private OrderListAdapter orderListAdapter;
   private int orderState=1;

	private String imgUrl;
	public boolean NoFirst=false;
	private String taocanImgUrl;
	@Override
	public View setView_parent(LayoutInflater inflater) {
		
		view_parent=inflater.
				inflate(R.layout.order_list_fragment1, null);
		return view_parent;
	}

	@Override
	public void setListener() {
		
	}
	@Override
	protected void onFragmentResume() {
		//loadDataHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);
	
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//loadDataHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);
		
		if (!NoFirst) {
		loadDataHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);
		NoFirst=true;
		}
	}
	public void changeOrderState(int orderState){
		this.orderState=orderState;
		orderListView.setMode(Mode.PULL_FROM_START);
		loadDataHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);
	}
	@Override
	public void initview() {
		orderListView=(PullToRefreshListView)view_parent.findViewById(R.id.order_list_view);
		fragment = (OrderListFragment) OrderUndoneFragment.this.getFragmentManager().getFragments().get(0);
        orderList=new ArrayList<NewOrderEntity>();                     //实例化接收的集合
        orderListAdapter=new OrderListAdapter(activity,orderList,true,new buttonClick() {
			
			@Override
			public void shouhuo(int orderId) {
				//orderAffirm(orderId);
			}
			
			@Override
			public void service() {
			
			}
			
			@Override
			public void evaluateOrder(NewOrderEntity entity) {
			
			}
			
			@Override
			public void deleteOrder(int orderId) {
				
			}

			@Override
			public void pay(int orderId) {
				//getdate(orderId);
				
			}
		});    //实例化adapter
        orderListView.setAdapter(orderListAdapter);						   //给listview添加适配器
        //设置上拉下拉刷新监听
        orderListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadDataHandler.sendEmptyMessage(PULL_DOWN_MODE);
				
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadDataHandler.sendEmptyMessage(PULL_UP_MODE);
				
			}
		});
        
        
        orderListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				NewOrderEntity item = (NewOrderEntity) orderListAdapter.getItem(position);
				openNewActivityWithHeader(HeaderActivityType.OrderListDetail.ordinal(), true,item,pay);
				/*if (entity.getOrderState().equals("等待付款")) {
					openNewActivityWithHeader(HeaderActivityType.OrderListDetail.ordinal(), true,entity,100);
				}else {
					openNewActivityWithHeader(HeaderActivityType.OrderListDetail.ordinal(), true,entity,pay);
				}
				*/
			}
		});
    	//loadDataHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);
		
	}
/*	protected void orderAffirm(int Oid) {
		final BaseApplication baseApplication=(BaseApplication) activity.getApplication();
		BaseRequestClient client=new BaseRequestClient(activity);
		OrderAffirmRequset request=new OrderAffirmRequset();
		request.setIp(NetworkUtil.getLocalIpAddress());
		request.setOid(Oid);
		client.httpPostByJson("Phoneordercommit", baseApplication.getUserResult(), request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

			@Override
			public void callBack(BaseResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						OrderListFragment orderListFragment = (com.dongwukj.weiwei.ui.fragment.OrderListFragment) activity.getSupportFragmentManager().findFragmentByTag("OrderListFragment");
						if (orderListFragment!=null) {
							orderListFragment.getOrderState();
						}
						showdialog()
						
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
			public void logOut(boolean isLogOut,BaseResult result) {
				if(isLogOut){
					FinalDb finalDb = FinalDb.create(activity);
					List<UserResult> findAllByWhere = finalDb.findAllByWhere(
							UserResult.class, "isLogin=1");
					for (UserResult userResult : findAllByWhere) {
						userResult.setLogin(false);
						finalDb.update(userResult);
					}
					// finalDb.deleteAll(UserResult.class);
					baseApplication.setUserResult(null);
					baseApplication.setCartCount(0);
					Toast.makeText(activity,"您的账号已经在其他地方登录！",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}*/
	private void showdialog() {

		final Dialog dialog=new Dialog(activity, R.style.Dialog);
		View view = View.inflate(activity, R.layout.ordercancle_dialog, null);
		dialog.setContentView(view);
		dialog.setCancelable(false);
		WindowManager m = activity.getWindowManager();
        Window dialogWindow = dialog.getWindow();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.36); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.80); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
		dialog.show();
		TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
		 TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
		 tv_ok.setText("取消");
		 tv_cancle.setText("立即去评价");
		 TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			tv_title.setText("订单已完成是否立即去评价?");
			tv_title.setTextSize(18);
		tv_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				getOrderListData(true);
				//NoFirst=false;
			}
		});
		tv_cancle.setOnClickListener(new OnClickListener() {
			 public void onClick(View v) {
				 //getOrderListData(true);
				// NoFirst=false;
				 fragment.showTabByIndex(1);
				 dialog.dismiss();
				
			}
		 });
	
		
	}
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode==100&&resultCode==Activity.RESULT_OK) {
    		if (data!=null) {
    			//NoFirst=false;
        		List<Fragment> list = activity.getSupportFragmentManager().getFragments();
        		OrderListFragment fragment = (OrderListFragment) list.get(0);
        		List<Fragment> fragments=fragment.list;
        		OrderCompleteFragment orderCompleteFragment = (OrderCompleteFragment) fragments.get(1);
        		//orderCompleteFragment.NoFirst=false;
        		loadDataHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);
			}else {
				activity.finish();
			}
    		/**/
    	}else if (requestCode==pay&&resultCode==Activity.RESULT_OK) {
			//activity.finish();
    		orderListView.setMode(Mode.PULL_FROM_START);
    		//NoFirst=false;
    		List<Fragment> list = activity.getSupportFragmentManager().getFragments();
    		OrderListFragment fragment = (OrderListFragment) list.get(0);
    		List<Fragment> fragments=fragment.list;
    		OrderCompleteFragment orderCompleteFragment = (OrderCompleteFragment) fragments.get(1);
    		//orderCompleteFragment.NoFirst=false;
			loadDataHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);
		}else if (requestCode==300&&resultCode==Activity.RESULT_OK) {
			activity.finish();
		}
    }
    private Handler loadDataHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
			case PULL_DOWN_MODE:
				getOrderListData(true);
				break;
			case PULL_UP_MODE:
				getOrderListData(false);
				break;
			case PULL_EMPTY:
				orderListView.setRefreshing();
				break;
			default:
				break;
			}
        }
    };

    private int page =1;
    private int bigSize;
	private OrderListFragment fragment;
    private void getOrderListData(final boolean isFirst){
		final BaseApplication baseApplication=(BaseApplication) activity.getApplication();
		OrderListRequest request = new OrderListRequest();
		if(isFirst){
			request.setPage(1);
		}else{
			request.setPage(++page);
		}
		request.setOrderState(orderState);
		request.setDay(-1);

        MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(activity,baseApplication);
        myWeiWeiRequestClient.orderList(request, new MyWeiWeiRequestClient.OrderRequestCallback() {
            @Override
            protected void list(OrderListResult result) {
            	if (getActivity()==null) {
					return;
				}
                if(result.getOrderList()==null || result.getOrderList().size()==0){
                	orderList.clear();
                	 orderListAdapter.notifyDataSetChanged();
                	//orderListAdapter.setOrderList(orderList);
                   Toast.makeText(activity, "暂时没有订单信息", Toast.LENGTH_SHORT).show();
                   orderListView.onRefreshComplete();
                   OrderListFragment orderListFragment = (com.dongwukj.weiwei.ui.fragment.OrderListFragment) activity.getSupportFragmentManager().findFragmentByTag("OrderListFragment");
					if (orderListFragment!=null) {
						orderListFragment.getStatistics();
					}
                }else{
                    bigSize = result.getListNumber();
                    if(isFirst){
                        orderList.clear();
                        orderListAdapter.notifyDataSetChanged();
                        //orderListAdapter.setOrderList(orderList);
                        orderList.addAll(result.getOrderList());
                        orderListAdapter.notifyDataSetChanged();
                        //orderListAdapter.setOrderList(orderList,result.getImgUrl(),result.getTaocanImgUrl());
                        page = 1;
                        orderListView.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH);
                        imgUrl=result.getImgUrl();
                        OrderListFragment orderListFragment = (com.dongwukj.weiwei.ui.fragment.OrderListFragment) activity.getSupportFragmentManager().findFragmentByTag("OrderListFragment");
    					if (orderListFragment!=null) {
    						orderListFragment.getStatistics();
    					}
                        //taocanImgUrl=result.getTaocanImgUrl();
                    }else{
                        orderList.addAll(result.getOrderList());
                        orderListAdapter.notifyDataSetChanged();
                       // orderListAdapter.setOrderList(orderList,result.getImgUrl(),result.getTaocanImgUrl());
                       
                    }
                   }

            }

            @Override
            protected void listComplete(OrderListResult result) {
            	if (getActivity()==null) {
					return;
				}
                orderListView.onRefreshComplete();
                if(bigSize<=orderList.size()){
                    orderListView.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_START);
                }
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
    private void openNewActivityWithHeader(int type,Boolean needLogin, NewOrderEntity entity,int code){
		Intent intent=new Intent(activity,HomeHeaderActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("needLogin", needLogin);
        DataBase db=LiteOrm.newCascadeInstance(activity, BaseApplication.DB_NAME);
        ArrayList<NewOrderEntity> all = db.queryAll(NewOrderEntity.class);
        if (all.size()>0) {
        	for (NewOrderEntity orderProductEntity : all) {
            	db.delete(orderProductEntity);
    		}
          
		}
       long l = db.insert(entity);
       startActivity(intent);
    }
	/*private void getdate(int orderId) {
		OrderDetailRequest request=new OrderDetailRequest(orderId);
		final BaseApplication baseApplication=(BaseApplication) activity.getApplication();
		MyWeiWeiRequestClient client=new MyWeiWeiRequestClient(activity,baseApplication);
		client.getOrderListDetail(request, new MyWeiWeiRequestClient.OrderListDetailRequestCallback() {
			@Override
			protected void list(OrderDetailResult result) {
				if (getActivity()==null) {
					return;
				}
				OrderDetailEntity order = result.getOrder();
				PhonecommitorderResult phonecommitorderResult = new PhonecommitorderResult();
				phonecommitorderResult.setOrderTime(order.getAddTime());
				phonecommitorderResult.setOrderAmount(order.getOrderAmount());
				phonecommitorderResult.setSurplusMoney(order.getSurplusMoney());
				phonecommitorderResult.setPaySystemName(order.getPaySystemName());
				phonecommitorderResult.setOsn(order.getOSN());
				phonecommitorderResult.setPayKey("余额支付");
				phonecommitorderResult.setPayMid("1");
				phonecommitorderResult.setContentId(order.getOid()+"");
				getData(phonecommitorderResult,baseApplication);
			}
		});
	}*/

/*	private void getData(final PhonecommitorderResult phonecommitorderResult, BaseApplication baseApplication){
		
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
	                startActivityForResult(intent, 300);
	        }
        });

	} */
}
