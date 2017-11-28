package com.dongwukj.weiwei.ui.fragment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.ShopCartAdapter;
import com.dongwukj.weiwei.adapter.ShopCartAdapter.CartClick;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.dongwukj.weiwei.idea.request.NewPhoneConfirmOrderRequest;
import com.dongwukj.weiwei.idea.request.PhoneGetCommissionRuleObjectListRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.CommissionRule;
import com.dongwukj.weiwei.idea.result.ExplainEntity;
import com.dongwukj.weiwei.idea.result.NewPhoneConfirmOrderResult;
import com.dongwukj.weiwei.idea.result.PhoneGetCommissionRuleObjectListResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.HomeActivity;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.ta.utdid2.android.utils.SystemUtils;

import de.greenrobot.event.EventBus;

public class ShoppingCarFragment extends BaseFragment {



    private RelativeLayout parent_cart_empty;
    private boolean isFromDetails;
	private ArrayList<NewHomeEntity> newHomelists=new ArrayList<NewHomeEntity>();
	 DataBase db;
	private ShopCartAdapter shopcartadapter;
	private SwipeListView lv;
	private LinearLayout bt_back;
	private LinearLayout cart_pay_container;
	private LinearLayout cart_delete_container,ll_fullcut_message,jiazai;
	private CheckBox cb_select;
	private CheckBox cb_delete;
	private TextView parent_cart_total_amount;
	private Button parent_cart_submit_button;
	private ArrayList<CommissionRule> ruleList =new ArrayList<CommissionRule>();


	@Override
	public View setView_parent(LayoutInflater inflater) {
        view_parent=inflater.inflate(R.layout.cart_list,null);
        baseApplication = (BaseApplication) activity.getApplication();
		return view_parent;
	}
	
	/*
	 * #198 暴露方法,跳转到首页方法
	 */
	private void showHome(){
		if (isFromDetails) {
			Intent intent=new Intent(activity,HomeActivity.class);
			intent.putExtra("homeTab",0);
			startActivity(intent);
			activity.finish();
		}else {
			((HomeActivity)activity).showTabByIndex(0);
		}
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	  public void onEventMainThread(Boolean item){
		
	}
	  public void onEventMainThread(String type){
			if (type.equals("login")) {
				PhoneGetCommissionRuleObjectList();
			}
		}
	@Override
	public void onPause() {
		EventBus.getDefault().post(true);
		super.onPause();
	}
	@Override
	public void initview() {
		isFromDetails = activity.getIntent().getBooleanExtra("isFromDetails", false);
		if (!isFromDetails) {
			activity = (HomeActivity) activity;
		}
		rl_fullcut = (RelativeLayout) view_parent.findViewById(R.id.rl_fullcut);
		bt_back = (LinearLayout) view_parent.findViewById(R.id.ll_left);
		cart_pay_container = (LinearLayout) view_parent.findViewById(R.id.cart_pay_container);
		cart_delete_container = (LinearLayout) view_parent.findViewById(R.id.cart_delete_container);
		jiazai = (LinearLayout) view_parent.findViewById(R.id.jiazai);
		ll_fullcut_message = (LinearLayout) view_parent.findViewById(R.id.ll_fullcut_message);
		cb_select = (CheckBox) view_parent.findViewById(R.id.cb_select);
		tv_fullcut_one = (TextView) view_parent.findViewById(R.id.tv_fullcut_one);
		tv_fullcut_two = (TextView) view_parent.findViewById(R.id.tv_fullcut_two);
		rl_fullcut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				final Dialog dialog=new Dialog(activity, R.style.Dialog);
				View view = View.inflate(activity, R.layout.fullcut_explain, null);
				LinearLayout ll=(LinearLayout) view.findViewById(R.id.ll);
				ll.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				ListView lv=(ListView) view.findViewById(R.id.lv);
				FullcutAdapter adapter=new FullcutAdapter();
				lv.setAdapter(adapter);
				dialog.setContentView(view);
				dialog.setCancelable(true);
				WindowManager m = activity.getWindowManager();
		        Window dialogWindow = dialog.getWindow();
		        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		        p.height = (int) (d.getHeight() * 1); // 高度设置为屏幕的0.6
		        p.width = (int) (d.getWidth() * 1); // 宽度设置为屏幕的0.65
		        dialogWindow.setAttributes(p);
				dialog.show();
				
				
				
			}
		});
		tv_fullcut_message = (TextView) view_parent.findViewById(R.id.tv_fullcut_message);
		
		balance_total_amount = (TextView) view_parent.findViewById(R.id.balance_total_amount);
		cb_select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cb_select.setChecked(cb_select.isChecked());
				if (cb_select.isChecked()) {
					for (NewHomeEntity newHomeEntity : newHomelists) {
						newHomeEntity.setChecked(true);
						QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
						qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId(),newHomeEntity.getBusinessgoodsid()});
						ArrayList<NewHomeEntity> arrayList = db.query(qb);
						if (arrayList!=null&&arrayList.size()>0) {
							arrayList.get(0).setChecked(true);;
							db.update(arrayList.get(0));
						}
					}
					countTotal();
				}
				shopcartadapter.notifyDataSetChanged();
			}
		});
		cb_delete = (CheckBox) view_parent.findViewById(R.id.cb_delete);
		parent_cart_total_amount = (TextView) view_parent.findViewById(R.id.parent_cart_total_amount);
		parent_cart_submit_button = (Button) view_parent.findViewById(R.id.parent_cart_submit_button);
		parent_cart_submit_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (totalnum>0) {
					PhoneConfirmOrder();
				}else {
					Toast.makeText(activity, "请选择需要结算的商品", Toast.LENGTH_SHORT).show();
				}
				
			}

			
		});
		bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
		if (isFromDetails) {
			bt_back.setVisibility(View.VISIBLE);
		}else {
			bt_back.setVisibility(View.GONE);
		}
	    parent_cart_empty=(RelativeLayout)view_parent.findViewById(R.id.parent_cart_empty);
		view_parent.findViewById(R.id.bt_food).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showHome();
			}
		});
		lv = (SwipeListView) view_parent.findViewById(R.id.lv);
		
		shopcartadapter=new ShopCartAdapter(activity,false, newHomelists,new CartClick() {
			
			@Override
			public void jian(int goodId, int num) {
				QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
				qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId(),goodId});
				ArrayList<NewHomeEntity> arrayList = db.query(qb);
				if (arrayList!=null&&arrayList.size()>0) {
					arrayList.get(0).setCount(num);
					db.update(arrayList.get(0));
				}
				updateUiHandler.sendEmptyMessage(100);
				if (!isFromDetails) {
					((HomeActivity)activity).setBuycount(((HomeActivity)activity).getCount());
				}
			}
			
			@Override
			public void jia(int goodId, int num) {
				QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
				qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId(),goodId});
				ArrayList<NewHomeEntity> arrayList = db.query(qb);
				if (arrayList!=null&&arrayList.size()>0) {
					long millis = System.currentTimeMillis();
					arrayList.get(0).setAddTime(millis);
					arrayList.get(0).setCount(num);
					db.update(arrayList.get(0));
				}
				updateUiHandler.sendEmptyMessage(100);
				if (!isFromDetails) {
					((HomeActivity)activity).setBuycount(((HomeActivity)activity).getCount());
				}
			}
			
			@Override
			public void delete(Integer id,boolean isShow) {
				if (isShow) {
					showDialogs(id);
				}else {
					deleteProduct(id);
				}
				
				
			}

		

			@Override
			public void checkboxCheck(boolean b, int id) {
				if (!b) {
					cb_select.setChecked(false);
				}
				QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
				qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId(),id});
				ArrayList<NewHomeEntity> arrayList = db.query(qb);
				if (arrayList!=null&&arrayList.size()>0) {
					arrayList.get(0).setChecked(b);;
					db.update(arrayList.get(0));
				}
				updateUiHandler.sendEmptyMessage(100);
			}

			@Override
			public void click(int gid) {
				Intent intent=new Intent(activity, HomeHeaderActivity.class);
                intent.putExtra("productId",gid);
                intent.putExtra("type",HeaderActivityType.ProductDetail.ordinal());
                //intent.putExtra("hasHeader",false);
                intent.putExtra("isFromShopcart", true);
                startActivity(intent);
			}
		});
		lv.setAdapter(shopcartadapter);
		if (newHomelists==null||newHomelists.size()==0) {
			parent_cart_empty.setVisibility(View.VISIBLE);
		}
		PhoneGetCommissionRuleObjectList();
	}
	 protected void showDialogs(final int id){
	    	final Dialog dialog = new Dialog(activity, R.style.Dialog);
			LinearLayout view = (LinearLayout) View.inflate(activity, R.layout.is_delete_address_dialog, null);
			dialog.setContentView(view);
			dialog.setCancelable(false); //为false 按返回键不能 dismiss Dialog
			
			TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
			tv_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View paramView) {
					deleteProduct(id);
					dialog.dismiss();
					
				}
			});
			TextView title = (TextView) view.findViewById(R.id.title);
			title.setText("是否删除该商品？");
			
			TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
			tv_cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View paramView) {
					dialog.dismiss();
				}
			});
				WindowManager m = activity.getWindowManager();
				Window dialogWindow = dialog.getWindow();
		        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		        p.height = (int) (d.getHeight() * 0.18); // 高度设置为屏幕的0.6
		        p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕的0.65
		        dialogWindow.setAttributes(p);
			
			dialog.show();
			
	    }
	class FullcutAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			
			return ruleDescribes.size()+1;
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
			View view = View.inflate(activity, R.layout.fullcut_explain_item, null);
			TextView tv=(TextView) view.findViewById(R.id.tv);
			
			if (position==ruleDescribes.size()) {
				tv.setText("特价商品不参加其他折扣");
				tv.setTextColor(activity.getResources().getColor(R.color.gray_button_text_color_t));
			}else {
				CommissionRule rule = ruleDescribes.get(position);
				tv.setTextColor(activity.getResources().getColor(R.color.black));
				//tv.setText("满"+rule.getStartamount()+"元减"+rule.getFullcut()+"元");
				tv.setText(Html.fromHtml("满"+rule.getStartamount()+"元减"+"<font color='red'>"+rule.getFullcut()+"</font>元"));
				
			}
			
			
			return view;
		}
		
	}
	
	
	
	private ArrayList<CommissionRule> ruleDescribes=new ArrayList<CommissionRule>();
	/**
	 * 获取满减规则
	 */
	private void PhoneGetCommissionRuleObjectList() {
		
		PhoneGetCommissionRuleObjectListRequest request=new PhoneGetCommissionRuleObjectListRequest();
		BaseRequestClient client=new BaseRequestClient(activity);
		//progressDialog.setMessage("获取满减信息中。。。");
		//progressDialog.show();
		client.httpPostByJsonNew("PhoneGetCommissionRuleObjectList", baseApplication.getUserResult(), request, PhoneGetCommissionRuleObjectListResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneGetCommissionRuleObjectListResult>() {

			
			@Override
			public void callBack(PhoneGetCommissionRuleObjectListResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						shopcartadapter.setExplainEntity(new ExplainEntity(result.getDiscountprefix(), result.getDiscountsuffix(), result.getSpecialwords(), result.getFulltextreduction()));
						if (result.getCommissionRuleList().size()>0) {
							if (result.getIsFullt()==0) {
								rl_fullcut.setVisibility(View.GONE);
								return;
							}
						ruleList.clear();
						ruleList.addAll(result.getCommissionRuleList());
						rl_fullcut.setVisibility(View.VISIBLE);
						countTotal();
						changeFullCut();
						ruleDescribes=result.getRuleDescribes();
						}else {
							rl_fullcut.setVisibility(View.GONE);
						}
						
					}else {
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();	
					}
				}else {
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				progressDialog.dismiss();
			}

			@Override
			public void loading(long count, long current) {}

			@Override
			public void logOut(PhoneGetCommissionRuleObjectListResult result) {
				progressDialog.dismiss();
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
	private CommissionRule ruleObject;
	private int endamount;
	protected void changeFullCut() {
		for (CommissionRule rule : ruleList) {
			if (fullcuttotal>=rule.getStartamount()&&fullcuttotal<rule.getEndamount()) {
				ruleObject=rule;
				if (rule.getFullcut()>0) {
					tv_fullcut_one.setText("满"+rule.getStartamount()+"元减"+rule.getFullcut()+"元");
				}else {
					tv_fullcut_one.setText("");
				}
			}
		}
		if (fullcuttotal<=0) {
			for (int i = 0; i < ruleList.size(); i++) {
				if (ruleList.get(i).getFullcut()>0) {
					tv_fullcut_one.setText("");
					return;
				}
			}
			return;
		}
		if (ruleObject!=null) {
			//parent_cart_total_amount.setText(String.format("￥%.2f", total-ruleObject.getFullcut()));
			parent_cart_total_amount.setText(String.format("￥%.2f", total));
			balance_total_amount.setText(String.format("￥%.2f", balancetotal-ruleObject.getFullcut()));
		}else {
			parent_cart_total_amount.setText(String.format("￥%.2f", total));
			balance_total_amount.setText(String.format("￥%.2f", balancetotal));
		}
		
	}

	private void deleteProduct(Integer id) {
		QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
		qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId(),id});
		ArrayList<NewHomeEntity> arrayList = db.query(qb);
		if (arrayList!=null&&arrayList.size()>0) {
			db.delete(arrayList.get(0));
		}
		updateUiHandler.sendEmptyMessage(100);
		lv.closeOpenedItems();
		EventBus.getDefault().post(id);
		if (!isFromDetails) {
			((HomeActivity)activity).setBuycount(((HomeActivity)activity).getCount());
		}
	}
	@Override
	public void setListener() {
        setUserVisibleHint(true);
	}
	private void PhoneConfirmOrder() {
		progressDialog.setMessage("订单生成中。。。");
		progressDialog.show();
		BaseRequestClient client=new BaseRequestClient(activity);
		NewPhoneConfirmOrderRequest request=new NewPhoneConfirmOrderRequest();
		request.setMid(baseApplication.getUserResult().getMarketId());
		QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
		qb.where("userAccount=? and marketId=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId()});
		ArrayList<NewHomeEntity> confirmlist=new ArrayList<NewHomeEntity>();
		ArrayList<NewHomeEntity> arrayList = db.query(qb);
		for (NewHomeEntity entity : arrayList) {
			if (entity.isChecked()==true) {
				entity.setWeight(entity.getCount()*entity.getIncrementweight()+getweightBalance(entity));
				confirmlist.add(entity);
			}
		}
		request.setRuleObject(ruleObject);
		request.setGoodsList(confirmlist);
		request.setTotalamount(totalnum);
		client.httpPostByJsonNew("PhoneConfirmOrder",baseApplication.getUserResult(), request, NewPhoneConfirmOrderResult.class, new BaseRequestClient.RequestClientNewCallBack<NewPhoneConfirmOrderResult>() {

			@Override
			public void callBack(NewPhoneConfirmOrderResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						if (result.getConfirmOrderObject()==null) {
							Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
							return;
						}
						Intent intent=new Intent(activity, HomeHeaderActivity.class);
						intent.putExtra("NewPhoneConfirmOrderResult", result);
						intent.putExtra("type", HeaderActivityType.ConfirmOrder.ordinal());
						startActivity(intent);
						
					}else if (result.getCode()==1100) {
						rl_fullcut.setVisibility(View.GONE);
						ruleList.clear();
						ruleObject=null;
						//parent_cart_total_amount.setText(String.format("￥%.2f", Float.parseFloat(parent_cart_total_amount.getText().toString().trim().split("￥")[1])+result.getConfirmOrderObject().getCommissionRule().getFullcut()));
						balance_total_amount.setText(String.format("￥%.2f", Float.parseFloat(balance_total_amount.getText().toString().trim().split("￥")[1])+result.getConfirmOrderObject().getCommissionRule().getFullcut()));
						progressDialog.dismiss();
						return;
					}
					else if (result.getCode()==1002) {
						
						if (result.getConfirmOrderObject().getReturnBusinessGoodsList()!=null&&result.getConfirmOrderObject().getReturnBusinessGoodsList().size()>0) {
							ArrayList<NewHomeEntity> returnBusinessGoodsList = result.getConfirmOrderObject().getReturnBusinessGoodsList();
							for (NewHomeEntity newHomeEntity : returnBusinessGoodsList) {
								deleteProduct(newHomeEntity.getBusinessgoodsid());
							}	
							newHomelists.removeAll(returnBusinessGoodsList);
							shopcartadapter.notifyDataSetChanged();							}
						progressDialog.dismiss();
						Toast.makeText(activity, "部分过期商品已从购物车移除", Toast.LENGTH_SHORT).show();
					}else if (result.getCode()==1013) {
						progressDialog.dismiss();
						PhoneGetCommissionRuleObjectList();
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}else {
						progressDialog.dismiss();
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					progressDialog.dismiss();
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void loading(long count, long current) {
				}

			@Override
			public void logOut(NewPhoneConfirmOrderResult result) {
				progressDialog.dismiss();
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
	double total=0;
	int totalnum=0;
	double fullcuttotal=0;
	double balancetotal=0;
	private int getweightBalance(NewHomeEntity entity){
		int weightBalance=0;
		if (entity.getIsmain()==1) {
			weightBalance=entity.getMaincourseminweight()-entity.getIncrementweight();
		}else {
			weightBalance=entity.getSidecourseminweight()-entity.getIncrementweight();
		}
		return weightBalance;
	}
	public void countTotal(){
		total=0;
		totalnum=0;
		fullcuttotal=0;
		balancetotal=0;
		db=LiteOrm.newCascadeInstance(activity, BaseApplication.DB_NAME);
		QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
		qb.where("userAccount=? and marketId=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId()});
		ArrayList<NewHomeEntity> arrayList = db.query(qb);
		if (arrayList.size()<=0) {
			return;
		}
		
		for (NewHomeEntity newHomeEntity : arrayList) {
			if (newHomeEntity.isChecked()) {
				if (newHomeEntity.getIsFullcut()==1) {
					if (newHomeEntity.getBusinessGoodsDiscountObject().getDiscountType()==1) {
						fullcuttotal=(newHomeEntity.getCount()*newHomeEntity.getIncrementweight()+getweightBalance(newHomeEntity))*newHomeEntity.getGramdiscountprice()+fullcuttotal;
					}else {
						fullcuttotal=(newHomeEntity.getCount()*newHomeEntity.getIncrementweight()+getweightBalance(newHomeEntity))*newHomeEntity.getGramprice()+fullcuttotal;
					}
					
				}
				if (newHomeEntity.getBusinessGoodsDiscountObject().getDiscountType()==1) {
					total=(newHomeEntity.getCount()*newHomeEntity.getIncrementweight()+getweightBalance(newHomeEntity))*newHomeEntity.getGramdiscountprice()+total;
					balancetotal=balancetotal+(newHomeEntity.getCount()*newHomeEntity.getIncrementweight()+getweightBalance(newHomeEntity))*newHomeEntity.getGramdiscountprice();
				}else {
					if (newHomeEntity.getBusinessGoodsDiscountObject().getDiscountType()==0) {
						balancetotal=balancetotal+(newHomeEntity.getCount()*newHomeEntity.getIncrementweight()+getweightBalance(newHomeEntity))*newHomeEntity.getGramprice();
					}else {
						balancetotal=balancetotal+(newHomeEntity.getCount()*newHomeEntity.getIncrementweight()+getweightBalance(newHomeEntity))*newHomeEntity.getGramprice()*newHomeEntity.getBusinessGoodsDiscountObject().getDiscount()/10;
					}
					total=(newHomeEntity.getCount()*newHomeEntity.getIncrementweight()+getweightBalance(newHomeEntity))*newHomeEntity.getGramprice()+total;
				}	
				totalnum++;
				//totalnum=newHomeEntity.getCount()+totalnum;
			}
		}
		
		parent_cart_submit_button.setText("结算("+totalnum+")");
		if (ruleObject!=null) {
			//parent_cart_total_amount.setText(String.format("￥%.2f", total-ruleObject.getFullcut()));
			parent_cart_total_amount.setText(String.format("￥%.2f", total));
			balance_total_amount.setText(String.format("￥%.2f", balancetotal-ruleObject.getFullcut()));
			
		}else {
			parent_cart_total_amount.setText(String.format("￥%.2f", total));
			balance_total_amount.setText(String.format("￥%.2f", balancetotal));
		}
		
	}
	
	private Handler updateUiHandler=new Handler(){
		public void handleMessage(Message msg) {
			db=LiteOrm.newCascadeInstance(activity, BaseApplication.DB_NAME);
			QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
			if (baseApplication.getUserResult()==null) {
				return;
			}
			qb.where("userAccount=? and marketId=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId()});
			ArrayList<NewHomeEntity> arrayList = db.query(qb);
			
			if (arrayList.size()>0) {
				parent_cart_empty.setVisibility(View.GONE);
				newHomelists.clear();
				shopcartadapter.notifyDataSetChanged();
				for (NewHomeEntity newHomeEntity : arrayList) {
					if (newHomeEntity.getAddTime()<baseApplication.getWeeTimew()) {
						db.delete(newHomeEntity);
						EventBus.getDefault().post("shuaxin");
						newHomelists.clear();
						parent_cart_empty.setVisibility(View.VISIBLE);
						if (!isFromDetails) {
							((HomeActivity)activity).setBuycount(((HomeActivity)activity).getCount());
						}
						
					}else {
						newHomelists.add(newHomeEntity);
						if (!isFromDetails) {
							((HomeActivity)activity).setBuycount(((HomeActivity)activity).getCount());
							
						}
					}
				}
				//newHomelists.addAll(arrayList);
				shopcartadapter.notifyDataSetChanged();
				
				changeCheckedBox();
				
			}else {
				newHomelists.clear();
				parent_cart_empty.setVisibility(View.VISIBLE);
			}
			countTotal();	
			changeFullCut();
			jiazai.setVisibility(View.GONE);
		};
	};
	public void changeCheckedBox(){
		cb_select.setChecked(true);
		for (NewHomeEntity newHomeEntity : newHomelists) {
			if (!newHomeEntity.isChecked()) {
				cb_select.setChecked(false);
			}
		}
	}
	private BaseApplication baseApplication;
	private RelativeLayout rl_fullcut;
	private TextView tv_fullcut_one;
	private TextView balance_total_amount;
	private TextView tv_fullcut_two,tv_fullcut_message;
	@Override
	public void onResume() {
		progressDialog.dismiss();
		//updateUiHandler.sendEmptyMessage(100);
		jiazai.setVisibility(View.VISIBLE);
		getProductList();
		
		super.onResume();
	}
	public void getProductList(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SystemClock.sleep(500);
				updateUiHandler.sendEmptyMessage(100);
				
			}
		}).start();
	}
	@Override
	public void onClick(View v) {
		
	}


	


    

}
