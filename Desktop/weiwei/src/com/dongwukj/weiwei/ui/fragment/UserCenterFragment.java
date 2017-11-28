package com.dongwukj.weiwei.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.*;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.data.PushMessageData;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.MessageIsChecked;
import com.dongwukj.weiwei.idea.request.OrderStatisticRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.OrderStatisticsResult;
import com.dongwukj.weiwei.idea.result.PhoneGetDefaultAddressResult;
import com.dongwukj.weiwei.idea.result.Plot;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.AppManager;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.activity.MipcaActivityCapture;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.umeng.analytics.MobclickAgent;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UserCenterFragment extends BaseFragment {

	private RelativeLayout rl_attention,rl_chaxun;
	private boolean islogin=false;
	private TextView tv_title;
	private TextView tv_username;
	//private TextView tv_level;
	private ImageView img_icon;
	private Button bt_login;
	private UserResult result;
	private RelativeLayout rl_footprint;
	private RelativeLayout rl_purse;
	private RelativeLayout rl_address,rl_usersafety;

    private FinalDb finalDb;
    private BaseApplication baseApplication;
	private RelativeLayout rl_orderlist;
	private TextView tv_waitpay;
	//private TextView tv_waitsend;
	private TextView tv_waitreceive;
	private TextView tv_evaluate;
	private LinearLayout ll_bottom,ll_unline_pay;
	private TextView tv_waitsend2,has_message;
	@Override
	public View setView_parent(LayoutInflater inflater) {
		view_parent=inflater.inflate(R.layout.user_fragment, null);
		return view_parent;
	}
	
	@Override
	public void setListener() {
		rl_attention.setOnClickListener(this);
		bt_login.setOnClickListener(this);
		rl_footprint.setOnClickListener(this);
		rl_purse.setOnClickListener(this);
		rl_address.setOnClickListener(this);
		rl_orderlist.setOnClickListener(this);
		ll_bottom.setOnClickListener(this);
		rl_recharge.setOnClickListener(this);
		rl_usersafety.setOnClickListener(this);
		rl_chaxun.setOnClickListener(this);
		rl_lianxikefu.setOnClickListener(this);
		ll_unline_pay.setOnClickListener(this);
	}

	@Override
	public void initview() {
		Button bt = (Button) view_parent.findViewById(R.id.bt);
		dataBase = LiteOrm.newCascadeInstance(activity, baseApplication.DB_NAME);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//AppManager.getInstance().killActivity(activity);
				Intent intent=new Intent(activity, LoginActivity.class);
				startActivity(intent);
			}
		});
		sp = activity.getSharedPreferences("config", 0);
        finalDb=FinalDb.create(activity);
        baseApplication=(BaseApplication)activity.getApplication();
        ll_bottom = (LinearLayout) view_parent.findViewById(R.id.ll_bottom);
        ll_unline_pay = (LinearLayout) view_parent.findViewById(R.id.ll_unline_pay);
        tv_unline_pay = (TextView) view_parent.findViewById(R.id.tv_unline_pay);
		rl_attention = (RelativeLayout) view_parent.findViewById(R.id.rl_attention);
		rl_chaxun = (RelativeLayout) view_parent.findViewById(R.id.rl_chaxun);
		tv_title = (TextView) view_parent.findViewById(R.id.tv_title);
		has_message = (TextView) view_parent.findViewById(R.id.has_message);
		tv_username = (TextView) view_parent.findViewById(R.id.tv_username);
		tv_waitpay = (TextView) view_parent.findViewById(R.id.tv_waitpay);
		//tv_waitsend = (TextView) view_parent.findViewById(R.id.tv_waitsend);
		tv_waitreceive = (TextView) view_parent.findViewById(R.id.tv_waitreceive);
		tv_evaluate = (TextView) view_parent.findViewById(R.id.tv_evaluate);
		purse = (ImageView) view_parent.findViewById(R.id.purse);
		tv_waitpay.setOnClickListener(this);
		//tv_waitsend.setOnClickListener(this);
		tv_waitreceive.setOnClickListener(this);
		tv_evaluate.setOnClickListener(this);
		
		//tv_level = (TextView) view_parent.findViewById(R.id.tv_level);
		img_icon = (ImageView) view_parent.findViewById(R.id.img_icon);
		bt_login = (Button) view_parent.findViewById(R.id.bt_login);
		rl_address = (RelativeLayout) view_parent.findViewById(R.id.rl_address);
		rl_usersafety = (RelativeLayout) view_parent.findViewById(R.id.rl_usersafety);
		rl_footprint = (RelativeLayout) view_parent.findViewById(R.id.rl_footprint);
		rl_purse = (RelativeLayout) view_parent.findViewById(R.id.rl_purse);
		rl_orderlist = (RelativeLayout) view_parent.findViewById(R.id.rl_orderlist);
		rl_recharge = (RelativeLayout) view_parent.findViewById(R.id.rl_recharge);
		rl_lianxikefu = (RelativeLayout) view_parent.findViewById(R.id.rl_lianxikefu);
		tv_order_count = (TextView) view_parent.findViewById(R.id.tv_order_count);
		sc = (ScrollView) view_parent.findViewById(R.id.sc);

        view_parent.findViewById(R.id.my_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                finalDb.deleteAll(UserResult.class);
                baseApplication.setUserResult(null);
                islogin=false;
                // 切换是否为登录模式
                changeloginmode();*/
            	openNewActivityWithHeader(HeaderActivityType.MySetting.ordinal(),false);
            }
        });
        view_parent.findViewById(R.id.rl_message).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	openNewActivityWithHeader(HeaderActivityType.MessageCenter.ordinal(), true);
            }
        });
        if (sp.getBoolean("login", true)&&baseApplication.getUserResult()==null) {
        	 mHandler.sendEmptyMessageDelayed(500,500);
		}
       
	}

  /*  private void showloginguide() {
    	final Dialog dialog=new Dialog(activity, R.style.Dialog);
		View view = View.inflate(activity, R.layout.login_guide, null);
		LinearLayout ll=(LinearLayout) view.findViewById(R.id.ll);
		//ImageView  img = (ImageView) view.findViewById(R.id.img);
		dialog.setContentView(view);
		WindowManager m = activity.getWindowManager();
        Window dialogWindow = dialog.getWindow();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight()); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() ); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
		dialog.show();
		ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				 showpurseguide();
			}
		});
	}*/
    private void showpurseguide() {
    	final Dialog dialog=new Dialog(activity, R.style.Dialog);
		View view = View.inflate(activity, R.layout.purse_guide, null);
		LinearLayout ll=(LinearLayout) view.findViewById(R.id.ll);
		//ImageView  img = (ImageView) view.findViewById(R.id.img);
		dialog.setContentView(view);
		WindowManager m = activity.getWindowManager();
        Window dialogWindow = dialog.getWindow();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight()); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() ); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
		dialog.show();
		ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
    @Override
    public void onResume() {
    	super.onResume();
    	 MobclickAgent.onPageStart("UserCenter");
        sc.scrollTo(0, 0);
        changeloginmode();
        //判断是否登录, 只有登录状态下 请求订单统计
        if(islogin){
        	mHandler.sendEmptyMessage(100);
       }
        QueryBuilder qb = new QueryBuilder(MessageIsChecked.class);
 		qb.where("userAccount=? and isChecked=?",new Integer[] {Integer.parseInt(baseApplication.getUserResult().getUserAccount()),0});
 		ArrayList<MessageIsChecked> query = dataBase.query(qb);
        if (query.size()>0) {
			has_message.setVisibility(View.VISIBLE);
		}else {
			has_message.setVisibility(View.GONE);
		}
    }
    @Override
    public void onPause() {
    	// TODO Auto-generated method stub
    	 MobclickAgent.onPageEnd("UserCenter");
    	super.onPause();
    	
    }
    public void ChangeHasMessage(){
    	has_message.setVisibility(View.VISIBLE);
    }
    private boolean  isNeedShow(){
    	boolean isNeedShow=false;
    	List<PushMessageData> list = finalDb.findAll(PushMessageData.class);
    	if (list!=null&&list.size()>0) {
			for (PushMessageData pushMessageData : list) {
				if (!pushMessageData.isReaded()) {
					isNeedShow=true;
				}
			}
		}
    	return isNeedShow;
    };
    @Override
    protected void onFragmentResume() {
    	/*if(islogin){
        	mHandler.sendEmptyMessage(100);
        }*/
    }

    private void changeloginmode() {
        result = baseApplication.getUserResult();
        if (result!=null) {
            islogin = result.isLogin();
        }else{
        	islogin=false;
        }
		if (islogin) {tv_order_count.setVisibility(View.VISIBLE); 
			ll_bottom.setVisibility(View.VISIBLE);
			tv_title.setVisibility(View.VISIBLE);
			tv_username.setVisibility(View.VISIBLE);
			tv_username.setText(result.getTel());
		//	tv_level.setVisibility(View.VISIBLE);
		/*	tv_level.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					openNewActivityWithHeader(HeaderActivityType.UserLevel.ordinal(), true);
				}
			});*/
			
			//img_icon.setImageDrawable((BitmapDrawable)getResources().getDrawable(R.drawable.tx_5));
			try {
				InputStream open = activity.getAssets().open(baseApplication.getUserResult().getAvatar());
				Bitmap b=BitmapFactory.decodeStream(open);
				img_icon.setImageBitmap(b);
			} catch (IOException e) {
				//e.printStackTrace();
				//Log.e("error","error",e);
			}
			bt_login.setVisibility(View.GONE);
			
		}else {
			tv_order_count.setVisibility(View.GONE);
			ll_bottom.setVisibility(View.INVISIBLE);
			//tv_title.setVisibility(View.INVISIBLE);
			tv_username.setVisibility(View.GONE);
			//tv_level.setVisibility(View.GONE);
			img_icon.setImageDrawable(getResources().getDrawable(R.drawable.weiwei_unlogin_icon));
			bt_login.setVisibility(View.VISIBLE);
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_attention:
			openNewActivityWithHeader(HeaderActivityType.Attention.ordinal(),true);
			break;
		case R.id.bt_login:
			Intent login=new Intent(activity,LoginActivity.class);
    		startActivityForResult(login, 100);
			break;
		case R.id.rl_purse:
			openNewActivityWithHeader(HeaderActivityType.Purse.ordinal(),true);
			break;
		case R.id.rl_recharge:
			openNewActivityWithHeader(HeaderActivityType.Recharge.ordinal(), true);
			break;

		case R.id.rl_footprint:
			openNewActivityWithHeader(HeaderActivityType.Footprint.ordinal(),true);
			break;
		case R.id.rl_address:
			checkAddress();
			/*if (isAdd) {
				//PhoneGetPlot();
				PhoneGetPlotResult result=new PhoneGetPlotResult();
				result.setPlot(plot);
				Intent intent=new Intent(activity, HomeHeaderActivity.class);
                intent.putExtra("type", HeaderActivityType.NewAddress.ordinal());
                intent.putExtra("fromorder", result);
                startActivityForResult(intent, 300);
			}else {
				Intent intent=new Intent(activity,HomeHeaderActivity.class);
	            intent.putExtra("type",HeaderActivityType.Address.ordinal());
	            intent.putExtra("needLogin",true);
	            intent.putExtra("fromHome", true);
	            startActivityForResult(intent, 200);
			}*/
			//openNewActivityWithHeader(HeaderActivityType.Address.ordinal(),true);
			break;
		case R.id.rl_orderlist:
			openNewActivityWithHeader(HeaderActivityType.OrderList.ordinal(),true);
			break;
		case R.id.tv_waitpay:
			openNewActivityWithHeader(HeaderActivityType.OrderList.ordinal(),true,0);
			break;
		/*case R.id.tv_waitsend:
			openNewActivityWithHeader(HeaderActivityType.OrderList.ordinal(),true,1);
			break;*/
		case R.id.tv_waitreceive:
			
			openNewActivityWithHeader(HeaderActivityType.OrderList.ordinal(),true,1);
			break;
		case R.id.tv_evaluate:
			openNewActivityWithHeader(HeaderActivityType.OrderList.ordinal(),true,2);
				break;
		case R.id.rl_lianxikefu:
			 Intent intent = new Intent();
			   intent.setAction("android.intent.action.CALL");
			   intent.setData(Uri.parse("tel:"+"027-87776107"));
			   startActivity(intent);
			break;
		case R.id.rl_usersafety:
			openNewActivityWithHeader(
					HeaderActivityType.SecurityAccountFragment.ordinal(), true);
			break;
		case R.id.rl_chaxun:
				
			 Intent intent1=new Intent(activity, MipcaActivityCapture.class);
			 intent1.putExtra("title", "溯源查询");
		     startActivity(intent1);
		     break;
		case R.id.ll_unline_pay:
			Intent intent2=new Intent(activity, MipcaActivityCapture.class);
			 intent2.putExtra("title",unline_title);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}
    private void openNewActivityWithHeader(int type,Boolean needLogin){
    		Intent intent=new Intent(activity,HomeHeaderActivity.class);
            intent.putExtra("type",type);
            intent.putExtra("needLogin", needLogin);
            startActivity(intent);
       
    }
    private Plot plot=null;
    private boolean isAdd;
    private void checkAddress() {
 		BaseRequestClient client=new BaseRequestClient(activity);
 		BaseRequest request=new BaseRequest();
 		client.httpPostByJsonNew("PhoneGetDefaultAddress", baseApplication.getUserResult(), request, PhoneGetDefaultAddressResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneGetDefaultAddressResult>() {

 			@Override
 			public void callBack(PhoneGetDefaultAddressResult result) {
 				if (result!=null) {
 					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
 						if (result.getShipAddress()!=null) {
 							isAdd=false;
 							Intent intent=new Intent(activity,HomeHeaderActivity.class);
 				            intent.putExtra("type",HeaderActivityType.Address.ordinal());
 				            intent.putExtra("needLogin",true);
 				            startActivity(intent);
 						}else {
 							plot = result.getPlot();
 							isAdd=true;
 							PhoneGetPlotResult result1=new PhoneGetPlotResult();
 							result1.setPlot(plot);
 							Intent intent=new Intent(activity, HomeHeaderActivity.class);
 			                intent.putExtra("type", HeaderActivityType.NewAddress.ordinal());
 			                intent.putExtra("fromorder", result1);
 			                startActivity(intent);
 						}
 					}else {
 						
 					}
 				}else {
 					
 				}
 			}

 			@Override
 			public void loading(long count, long current) {
 				
 			}

 			@Override
 			public void logOut(PhoneGetDefaultAddressResult result) {
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
	/*private void openNewActivityWithHeader(int type,Boolean needLogin,int needFinish){
		Intent intent=new Intent(activity,HomeHeaderActivity.class);
		intent.putExtra("type",type);
		intent.putExtra("needLogin", needLogin);
		startActivity(intent);

	}*/
    private void openNewActivityWithHeader(int type,Boolean needLogin,int orderstate){
		Intent intent=new Intent(activity,HomeHeaderActivity.class);
		intent.putExtra("type",type);
		intent.putExtra("needLogin", needLogin);
		intent.putExtra("orderstate", orderstate);
		startActivity(intent);

	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==100&&resultCode==200) {
			 result = baseApplication.getUserResult();
			 if (result!=null) {
				 islogin=result.isLogin();
			}
		}
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 100:
				
				getStatistics();  //获取订单统计数据
				break;
			case 500:
				showguide(bt_login, new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						guidedialog.dismiss();
						sp.edit().putBoolean("login", false).commit();
						if (sp.getBoolean("purse", true)) {
							showguide(purse, new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									sp.edit().putBoolean("purse", false).commit();
								guidedialog.dismiss();
								}
							}, true,  R.drawable.purse_guide_right, R.drawable.purse_guide_left,RelativeLayout.ALIGN_PARENT_LEFT);
						}
						
					}
				}, true, R.drawable.login_guide_top, R.drawable.login_guide_bottom,RelativeLayout.CENTER_HORIZONTAL);
				break;

			default:
				break;
			}
		}


	};
	private TextView tv_order_count;
	private ScrollView sc;
	private ImageView purse;
	private SharedPreferences sp;
	private RelativeLayout rl_recharge,rl_lianxikefu;
	private DataBase dataBase;
	private TextView tv_unline_pay;
	private String unline_title;
	private void getStatistics() {
		BaseRequestClient client = new BaseRequestClient(activity);
		UserResult userResult = baseApplication.getUserResult();
		OrderStatisticRequest Request = new OrderStatisticRequest();
		client.httpPostByJson("Phonewallet", userResult, Request, OrderStatisticsResult.class, new BaseRequestClient.RequestClientCallBack<OrderStatisticsResult>() {

			@Override
			public void callBack(OrderStatisticsResult result) {
				if(result!=null){
					if(result.getCode()==BaseResult.CodeState.Success.getCode()){
						if (result.getIsOpenDownPay()==1) {
							tv_unline_pay.setText(result.getDownPayName());
							unline_title=result.getDownPayName();
							ll_unline_pay.setVisibility(View.VISIBLE);
						}else {
							tv_unline_pay.setText("");
							ll_unline_pay.setVisibility(View.GONE);
						}
						int waitPaying = result.getUnfinishedOrderCount();  			//待支付
						int orderCount = result.getOrderCount();  			//订单总数
						int preProducting = result.getPreProducting();		//待发货
						int sended = result.getFinishedOrderCount();				//待收货
						int completed = result.getUnReviewOrderCount();				//评价
						tv_order_count.setText(""+orderCount);
						tv_waitpay.setText("未完成订单\n"+waitPaying);
						//tv_waitsend.setText("待发货\n"+preProducting);
						tv_waitreceive.setText("已完成订单\n"+sended);
						tv_evaluate.setText("待评价订单\n"+completed);
						
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
			startActivity(intent);}
		});
		
		
	};
}
