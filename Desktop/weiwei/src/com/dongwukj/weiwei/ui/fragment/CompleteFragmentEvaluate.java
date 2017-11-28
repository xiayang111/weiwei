package com.dongwukj.weiwei.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.*;
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
import com.dongwukj.weiwei.idea.request.OrderCancleRequest;
import com.dongwukj.weiwei.idea.request.OrderListRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.NewOrderEntity;
import com.dongwukj.weiwei.idea.result.OrderListResult;
import com.dongwukj.weiwei.idea.result.OrderProductEntity;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;

/**
 * Created by pc on 2015/3/17.
 */
public class CompleteFragmentEvaluate extends BaseFragment {
	protected static final int PULL_EMPTY = 0;
	protected static final int PULL_DOWN_MODE = 1;
	protected static final int PULL_UP_MODE = 2;
	private PullToRefreshListView orderListView;
    private List<NewOrderEntity> orderList;
    private OrderListAdapter orderListAdapter;
   public boolean NoFirst=false;

	private String imgUrl;

	private String taocanImgUrl;
    @Override
	public View setView_parent(LayoutInflater inflater) {
		
		view_parent=inflater.inflate(R.layout.order_list_fragment1, null);
		return view_parent;
	}

	@Override
	public void setListener() {
		
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!NoFirst) {
			loadDataHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);
			NoFirst=true;	
		}
	}
	@Override
	public void initview() {

        orderListView=(PullToRefreshListView)view_parent.findViewById(R.id.order_list_view);
        orderList=new ArrayList<NewOrderEntity>();                     //实例化接收的集合
        orderListAdapter=new OrderListAdapter(activity,orderList,false,new buttonClick() {
			
			@Override
			public void shouhuo(int orderId) {
				
			}
			
			@Override
			public void service() {
				/*  Intent intent = new Intent();
				   intent.setAction("android.intent.action.CALL");
				   intent.setData(Uri.parse("tel:"+"10086"));
				   startActivity(intent);*/
			}
			
			@Override
			public void evaluateOrder(NewOrderEntity entity) {
				
				//openNewActivityWithHeader(HeaderActivityType.OrderEvaluate.ordinal(), true,entity);
			}
			
			@Override
			public void deleteOrder(final int orderId) {/*


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
				TextView tv_title=(TextView) view.findViewById(R.id.tv_title);
				tv_title.setText("是否删除订单？");
				 TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
				 TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
				 tv_ok.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				 tv_cancle.setOnClickListener(new OnClickListener() {
					 public void onClick(View v) {
						 dialog.dismiss();
						// orderDelete(orderId);
					 }
				 });
			
			
				//Toast.makeText(activity, "deleteOrder", Toast.LENGTH_SHORT).show();
			*/}

			@Override
			public void pay(int orderId) {
				Toast.makeText(activity, "pay", Toast.LENGTH_SHORT).show();
				
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
				NewOrderEntity entity=(NewOrderEntity) orderListAdapter.getItem(position);
				openNewActivityWithHeader(HeaderActivityType.OrderListDetail.ordinal(), true,entity);
			}
		});
        
       // loadDataHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);
    }
	
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode==100&&resultCode==Activity.RESULT_OK) {
    		//NoFirst=false;
		}else if (requestCode==200&&resultCode==Activity.RESULT_OK) {
			getOrderListData(true);
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
	private int orderState=3;
    private void getOrderListData(final boolean isFirst){
		BaseRequestClient client = new BaseRequestClient(activity);
		final BaseApplication baseApplication=(BaseApplication) activity.getApplication();
		UserResult userResult =baseApplication.getUserResult(); 
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
                	//orderListAdapter.setOrderList(orderList);
                	 orderListAdapter.notifyDataSetChanged();
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
                        //orderListAdapter.setOrderList(orderList);
                        orderListAdapter.notifyDataSetChanged();
                        orderList.addAll(result.getOrderList());
                        orderListAdapter.notifyDataSetChanged();
                       // orderListAdapter.setOrderList(orderList,result.getImgUrl(),result.getTaocanImgUrl());
                        page = 1;

                    	imgUrl=result.getImgUrl();
                    	  OrderListFragment orderListFragment = (com.dongwukj.weiwei.ui.fragment.OrderListFragment) activity.getSupportFragmentManager().findFragmentByTag("OrderListFragment");
      					if (orderListFragment!=null) {
      						orderListFragment.getStatistics();
      					}
                   // taocanImgUrl=result.getTaocanImgUrl();
                        orderListView.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH);
                    }else{
                        orderList.addAll(result.getOrderList());	
                        orderListAdapter.notifyDataSetChanged();
                      //  orderListAdapter.setOrderList(orderList,result.getImgUrl(),result.getTaocanImgUrl());
                        
                    }
                    
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
        });


    }
 /*   protected void orderDelete(int Oid) {
    	BaseApplication baseApplication=(BaseApplication) activity.getApplication();
		MyWeiWeiRequestClient client=new MyWeiWeiRequestClient(activity, baseApplication);
		OrderCancleRequest request=new OrderCancleRequest();
		request.setOid(Oid);
		//request.setIp(NetworkUtil.getLocalIpAddress());
		client.orderDelete(request, new MyWeiWeiRequestClient.OrderListDetailRequestCallback() {
			protected void delete(BaseResult result) {
				if (getActivity()==null) {
					return;
				}
				OrderListFragment orderListFragment = (com.dongwukj.weiwei.ui.fragment.OrderListFragment) activity.getSupportFragmentManager().findFragmentByTag("OrderListFragment");
				if (orderListFragment!=null) {
					orderListFragment.getOrderState();
				}
				Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
				getOrderListData(true);
			};
		});
    }*/
    private void openNewActivityWithHeader(int type,Boolean needLogin, NewOrderEntity entity){
		Intent intent=new Intent(activity,HomeHeaderActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("needLogin", needLogin);
        DataBase db=LiteOrm.newCascadeInstance(activity, BaseApplication.DB_NAME);
        ArrayList<NewOrderEntity> all = db.queryAll(NewOrderEntity.class);
        for (NewOrderEntity orderProductEntity : all) {
        	db.delete(orderProductEntity);
		}
        db.save(entity);
       // intent.putExtra("detail_entity", entity);
        intent.putExtra("imgUrl", imgUrl);
        intent.putExtra("taocanImgUrl", taocanImgUrl);
        
       startActivityForResult(intent, 100);
    }

	public void changeOrderState(int orderState) {
		this.orderState=orderState;
		if (orderListView!=null) {
			orderListView.setMode(Mode.PULL_FROM_START);
		}
	
		loadDataHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);
	}

}
