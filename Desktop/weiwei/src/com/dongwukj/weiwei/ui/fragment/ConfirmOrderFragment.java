package com.dongwukj.weiwei.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.ParentCartListAdapter;
import com.dongwukj.weiwei.adapter.ShopCartAdapter;
import com.dongwukj.weiwei.adapter.ShopCartAdapter.CartClick;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.CouponRoleListRequest;
import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.dongwukj.weiwei.idea.request.NewPhonecommitorderRequest;
import com.dongwukj.weiwei.idea.request.PhoneConfirmOrderRequest;
import com.dongwukj.weiwei.idea.request.PhonecommitorderRequest;
import com.dongwukj.weiwei.idea.result.*;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.widget.ChildCartListView;
import com.dongwukj.weiwei.ui.widget.MyListView;
import com.dongwukj.weiwei.ui.widget.ParentCartListView;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalDb;

/**
 * Created by sunjaly on 15/4/13.
 */
public class ConfirmOrderFragment extends AbstractHeaderFragment {

    private LinearLayout confirm_order_address;

    private ParentCartListView parentCartListView;
    private TextView confirm_order_cart_total_price;
    private TextView confirm_order_payment_text;
    private TextView confirm_order_ship_amount;
    private TextView confirm_order_total_amount;
    private ArrayList<String> CouponIdList=new ArrayList<String>();                          //使用优惠卷id的拼接
    private RelativeLayout confirm_order_valid_container;

    private Button confirm_order_submit_button;
    private EditText confirm_order_buyer_remark;


    private final int ADD_ADDRESS_REQUEST_CODE=110;
    private final int CHECK_ADDRESS_REQUEST_CODE=111;
    private final int CHECK_PAYMENT_REQUEST_CODE=112;



    private DataBase db;

    private PaymentEntity paymentEntity;
    private PhoneConfirmOrderResult phoneConfirmOrderResult;

	protected boolean isAddAddress=false;
	private TextView tv_freight;

	private TextView tv_discount;

	private TextView tv_yue;

	private TextView tv_coupon,tv_fullcut,tv_fullcut_message,tv_payment_balance,tv_isshowfullcut;



	private RelativeLayout address;

	private SharedPreferences sp;

	private NewPhoneConfirmOrderResult newPhoneConfirmOrderResult;

	private ImageView img_empty;
	private AddressEntity addressEntity;

	private ImageView iv_more;

	private MyAdapter adapter;

	private LinearLayout ll_coupon;


 
    private String sendtime;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.new_confirm_order_layout,container,false);
        db = LiteOrm.newCascadeInstance(activity, BaseApplication.DB_NAME);
        newPhoneConfirmOrderResult = (NewPhoneConfirmOrderResult) activity.getIntent().getSerializableExtra("NewPhoneConfirmOrderResult");
//       pg=new ProgressDialog(activity);
//        pg.setTitle("提示");
//        pg.setMessage("加载中...");
//        sp = activity.getSharedPreferences("addressguide", 0);
        return view;
    }

    @Override
    protected String setTitle() {
        return baseApplication.getString(R.string.confirm_order_header_title);
    }
   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==ADD_ADDRESS_REQUEST_CODE && resultCode== Activity.RESULT_OK){
        	AddressEntity entity=(AddressEntity) data.getSerializableExtra("addressEntity");
        	if (entity!=null) {
				newPhoneConfirmOrderResult.getConfirmOrderObject().getShipAddressList().add(entity);
			}
            fetchAddress();
        }else if(requestCode==CHECK_ADDRESS_REQUEST_CODE && resultCode==Activity.RESULT_OK){
           /* addressEntity=(AddressEntity)data.getSerializableExtra("AddressEntity");
            if(addressEntity!=null){
                fillAddressContent(addressEntity);
            }else{
                fetchAddress();
            }*/
        }else if(requestCode==CHECK_PAYMENT_REQUEST_CODE && resultCode==Activity.RESULT_OK){
            paymentEntity=(PaymentEntity)data.getSerializableExtra("paymentEntity");
            if(paymentEntity!=null){
                fillPaymentContent(paymentEntity);
            }

        }else if (requestCode==100&&resultCode==Activity.RESULT_OK) {
        	DeliverEntity entity=(DeliverEntity) data.getSerializableExtra("DeliverEntity");
        	tv_sendtime.setText(entity.getDeliverDate()+entity.getDeliverTime());
        	tv_address.setText(entity.getName());
        	sinceid=entity.getSinceid();
        	deliverDate=entity.getDeliverDate();
        	sendtime=entity.getDeliverTime();
        	mealtimes=entity.getMealtimes();
        	
        	float a=Float.parseFloat(confirm_order_total_amount.getText().toString().trim().split("￥")[1])-(Float.parseFloat(tv_freight.getText().toString().trim().substring(0))-entity.getSincefee());
        	
        	confirm_order_total_amount.setText(String.format("￥%.2f", a));
        	float b=Float.parseFloat(tv_payment_balance.getText().toString().trim().split("￥")[1])-(Float.parseFloat(tv_freight.getText().toString().trim().substring(0))-entity.getSincefee());
        	tv_payment_balance.setText(String.format("￥%.2f", b));
        	tv_freight.setText(String.format("+%.2f", entity.getSincefee()));
        }else if (requestCode==200&&resultCode==Activity.RESULT_OK) {
			DeliverEntity entity=(DeliverEntity) data.getSerializableExtra("DeliverEntity");
			tv_sendtime.setText(entity.getDeliverDate()+entity.getDeliverTime());
			deliverDate=entity.getDeliverDate();
        	sendtime=entity.getDeliverTime();
        	mealtimes=entity.getMealtimes();
        	float a=Float.parseFloat(confirm_order_total_amount.getText().toString().trim().split("￥")[1])-(Float.parseFloat(tv_freight.getText().toString().trim().substring(0))-newPhoneConfirmOrderResult.getConfirmOrderObject().getShipFee());
        	
        	confirm_order_total_amount.setText(String.format("￥%.2f", a));
        	float b=Float.parseFloat(tv_payment_balance.getText().toString().trim().split("￥")[1])-(Float.parseFloat(tv_freight.getText().toString().trim().substring(0))-newPhoneConfirmOrderResult.getConfirmOrderObject().getShipFee());
        	tv_payment_balance.setText(String.format("￥%.2f", b));
        	tv_freight.setText(String.format("+%.2f", newPhoneConfirmOrderResult.getConfirmOrderObject().getShipFee()));
		}else{
            super.onActivityResult(requestCode,resultCode,data);
        }

    }

    private void fillPaymentContent(PaymentEntity paymentEntity) {
        confirm_order_payment_text.setText(paymentEntity.getKey());
    }

    @Override
    protected void findView(View v) {
    	address = (RelativeLayout) v.findViewById(R.id.address);
    	img_empty = (ImageView) v.findViewById(R.id.img_empty);
    	tv_coupon = (TextView) v.findViewById(R.id.tv_coupon);
    	//send_time = (TextView) v.findViewById(R.id.send_time);
    	tv_fullcut = (TextView) v.findViewById(R.id.tv_fullcut);
    	tv_isshowfullcut = (TextView) v.findViewById(R.id.tv_isshowfullcut);
    	tv_payment_balance = (TextView)  v.findViewById(R.id.tv_payment_balance);
    	tv_fullcut_message = (TextView) v.findViewById(R.id.tv_fullcut_message);
    	confirm_order_address=(LinearLayout)v.findViewById(R.id.confirm_order_address);
        iv_more = (ImageView) v.findViewById(R.id.iv_more);
        parentCartListView=(ParentCartListView)v.findViewById(R.id.parent_confirm_order_list);
        confirm_order_cart_total_price=(TextView)v.findViewById(R.id.confirm_order_cart_total_price);

        confirm_order_payment_text=(TextView)v.findViewById(R.id.confirm_order_payment_text);
        confirm_order_ship_amount=(TextView)v.findViewById(R.id.confirm_order_ship_amount);
       tv_freight =(TextView)v.findViewById(R.id.tv_freight);
       tv_discount = (TextView)v.findViewById(R.id.tv_discount);
        confirm_order_cart_total_price=(TextView)v.findViewById(R.id.confirm_order_cart_total_price);
        confirm_order_total_amount=(TextView)v.findViewById(R.id.confirm_order_total_amount);

        confirm_order_submit_button=(Button)v.findViewById(R.id.confirm_order_submit_button);
        
        confirm_order_valid_container=(RelativeLayout)v.findViewById(R.id.confirm_order_valid_container);
        ll_coupon = (LinearLayout) v.findViewById(R.id.ll_coupon);
    	MyListView lv_coupon=(MyListView) v.findViewById(R.id.lv_coupon);

    	adapter = new MyAdapter();
    	if (newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList()!=null&&newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList().size()>0) {
    		lv_coupon.setAdapter(adapter);
		}
    	
    	lv_coupon.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CouponEntity entity = newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList().get(position);
				
				entity.setIschecked(!entity.isIschecked());
				adapter.notifyDataSetChanged();
				for (CouponEntity entity1 : newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList()) {
					if (entity1.getCouponid()!=entity.getCouponid()) {
						if (entity1.isIschecked()) {
							confirm_order_total_amount.setText(String.format("￥%.2f", Float.parseFloat(confirm_order_total_amount.getText().toString().trim().split("￥")[1])+entity1.getMoney()));
							tv_payment_balance.setText(String.format("￥%.2f",Float.parseFloat(tv_payment_balance.getText().toString().trim().split("￥")[1])+entity1.getMoney()));
						}
						entity1.setIschecked(false);
					}
					tv_coupon.setText("优惠劵");
					tv_discount.setText(String.format("%.2f",0.00));
//					confirm_order_total_amount.setText(String.format("￥%.2f", Float.parseFloat(confirm_order_total_amount.getText().toString().trim().split("￥")[1])));
//					tv_payment_balance.setText(String.format("￥%.2f",Float.parseFloat(tv_payment_balance.getText().toString().trim().split("￥")[1])));
				}
				if (!entity.isIschecked()) {
					confirm_order_total_amount.setText(String.format("￥%.2f", Float.parseFloat(confirm_order_total_amount.getText().toString().trim().split("￥")[1])+entity.getMoney()));
					tv_payment_balance.setText(String.format("￥%.2f",Float.parseFloat(tv_payment_balance.getText().toString().trim().split("￥")[1])+entity.getMoney()));
				}
				for (CouponEntity entity1 : newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList()) {
					if (entity1.isIschecked()) {
						ll_coupon.setVisibility(View.GONE);
						tv_coupon.setText("优惠劵(可以抵现金"+String.format("%.2f", entity1.getMoney())+"元)");
						tv_discount.setText(String.format("-%.2f",entity1.getMoney()));
						confirm_order_total_amount.setText(String.format("￥%.2f", Float.parseFloat(confirm_order_total_amount.getText().toString().trim().split("￥")[1])-entity1.getMoney()));
						tv_payment_balance.setText(String.format("￥%.2f", Float.parseFloat(tv_payment_balance.getText().toString().trim().split("￥")[1])-entity1.getMoney()));
						
						return;
					}
				
				}
				
				}
		});
    	
        v.findViewById(R.id.confirm_order_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (newPhoneConfirmOrderResult.getConfirmOrderObject().getShipAddressList().size()>0) {
					return;
				}
            	PhoneGetPlot();
                
            }

            private void PhoneGetPlot() {
            	progressDialog.setMessage("获取您的地址信息。。。");
            	progressDialog.show();
				BaseRequestClient client=new BaseRequestClient(activity);
				PhoneGetPlotReqeuset request=new PhoneGetPlotReqeuset();
				request.setPlotId(baseApplication.getUserResult().getPlotid());
				client.httpPostByJsonNew("PhoneGetPlot", baseApplication.getUserResult(), request, PhoneGetPlotResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneGetPlotResult>() {

					@Override
					public void callBack(PhoneGetPlotResult result) {
						if (result!=null) {
							if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
								Intent intent=new Intent(activity, HomeHeaderActivity.class);
				                intent.putExtra("type", HeaderActivityType.NewAddress.ordinal());
				                intent.putExtra("fromorder", result);
				                startActivityForResult(intent, ADD_ADDRESS_REQUEST_CODE);
				                progressDialog.dismiss();
							}else {
								showtoast(result.getMessage(), activity);
								  progressDialog.dismiss();
							}
						}else {
							showtoast(activity.getResources().getString(R.string.data_error), activity);
							  progressDialog.dismiss();
						}
						
					}

					@Override
					public void loading(long count, long current) {
						
						
					}

					@Override
					public void logOut(PhoneGetPlotResult result) {
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
        });
       v.findViewById(R.id.confirm_order_change_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity,HomeHeaderActivity.class);
                intent.putExtra("type",HeaderActivityType.PaymentList.ordinal());
                intent.putExtra("list", (Serializable) newPhoneConfirmOrderResult.getConfirmOrderObject().getPayPluginList());
                startActivityForResult(intent,CHECK_PAYMENT_REQUEST_CODE);
            }
        });

        confirm_order_valid_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (ll_coupon.getVisibility()==View.GONE) {
            		if (newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList()!=null&&newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList().size()<=0) {
            			 Toast.makeText(activity,"没有可用优惠券",Toast.LENGTH_SHORT).show();
            			 return;
					}else {
						ll_coupon.setVisibility(View.VISIBLE);
					}
            	}else {
					ll_coupon.setVisibility(View.GONE);
				}
            }
        });
        tv_yue = (TextView) v.findViewById(R.id.tv_yue);
       
        confirm_order_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

        confirm_order_buyer_remark=(EditText)v.findViewById(R.id.confirm_order_buyer_remark);
        fetchAddress();
        updateUIHandler.sendEmptyMessage(1000);
        initsendtime(v);
    }	
    private int sinceid=-1;
    private int sendtype=-1;
    private int mealtimes=-1;
    
    private String deliverDate;
    private void initsendtime(View v) {
    	final RelativeLayout ll_am=(RelativeLayout) v.findViewById(R.id.ll_am);
		final RelativeLayout ll_pm=(RelativeLayout) v.findViewById(R.id.ll_pm);
		final RelativeLayout rl_sendAddress=(RelativeLayout) v.findViewById(R.id.rl_sendAddress);
		final RelativeLayout rl_send_time=(RelativeLayout) v.findViewById(R.id.rl_send_time);
		final LinearLayout ll_sendtype=(LinearLayout) v.findViewById(R.id.ll_sendtype);
		final ImageView select_image=(ImageView) v.findViewById(R.id.select_image);
		final ImageView select_image1=(ImageView) v.findViewById(R.id.select_image1);
		final ImageView image_selete_time=(ImageView) v.findViewById(R.id.image_selete_time);
		final TextView tv_send_type=(TextView) v.findViewById(R.id.send_type);
		tv_address = (TextView) v.findViewById(R.id.tv_address);
		tv_sendtime = (TextView) v.findViewById(R.id.tv_sendtime);
		if (newPhoneConfirmOrderResult.getConfirmOrderObject().getIsToDoor()==1) {
			sendtype=0;
			select_image1.setVisibility(View.GONE);
			rl_sendAddress.setVisibility(View.GONE);
			tv_send_type.setText("送货上门");
			if (newPhoneConfirmOrderResult.getConfirmOrderObject().getCanSince()==1) {
				ll_pm.setBackgroundResource(R.drawable.sendtype_one_two_true);
			}else {
				ll_pm.setBackgroundResource(R.drawable.sendtype_one_two_false);
			}
		}else {
			sendtype=1;
			ll_am.setBackgroundResource(R.drawable.sendtype_one_false);
			select_image1.setVisibility(View.VISIBLE);
			select_image.setVisibility(View.GONE);
			rl_sendAddress.setVisibility(View.VISIBLE);
			image_selete_time.setVisibility(View.GONE);
			tv_send_type.setText("自提");
			if (newPhoneConfirmOrderResult.getConfirmOrderObject().getCanSince()==1) {
				tv_sendtime.setText("请选择自提时间");
				tv_address.setText("请选择自提点");
				ll_pm.setBackgroundResource(R.drawable.sendtype_one_two_true);
			}else {
				sendtype=-1;
				ll_sendtype.setVisibility(View.GONE);
				select_image1.setVisibility(View.GONE);
				ll_pm.setBackgroundResource(R.drawable.sendtype_one_two_false);
			}
		}
			
		
		
		
		
		
		/*select_image1.setVisibility(View.GONE);
		rl_sendAddress.setVisibility(View.GONE);
		tv_send_type.setText("送货上门");
		if (newPhoneConfirmOrderResult.getConfirmOrderObject().getCanSince()==0) {
			ll_pm.setBackgroundResource(R.drawable.sendtype_one_two_false);
		}*/
		rl_sendAddress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (newPhoneConfirmOrderResult.getConfirmOrderObject().getShipAddressList()==null||newPhoneConfirmOrderResult.getConfirmOrderObject().getShipAddressList().size()<=0) {
					Toast.makeText(activity, "请先添加收货地址", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent=new Intent(activity,HomeHeaderActivity.class);
				  intent.putExtra("type",HeaderActivityType.SendTypeFragment.ordinal());
				  intent.putExtra("sendType",1);
				  intent.putExtra("plotId", newPhoneConfirmOrderResult.getConfirmOrderObject().getShipAddressList().get(0).getPlot().getId());
				  startActivityForResult(intent, 100);
			}
		});
		rl_send_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (sendtype==1) {
					return;
				}
				 Intent intent=new Intent(activity,HomeHeaderActivity.class);
				  intent.putExtra("type",HeaderActivityType.SendTypeFragment.ordinal());
				  intent.putExtra("sendType", 0);
				  startActivityForResult(intent, 200);
			}
		});
		ll_am.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				if (newPhoneConfirmOrderResult.getConfirmOrderObject().getIsToDoor()==0||sendtype==-1) {
					Toast.makeText(activity, "当前小区不支持送货上门", Toast.LENGTH_SHORT).show();
					return;
				}
				if (select_image.getVisibility()==0) {
					return;
				}
				sendtype=0;
				tv_send_type.setText("送货上门");
				image_selete_time.setVisibility(View.VISIBLE);
				rl_sendAddress.setVisibility(View.GONE);
				select_image1.setVisibility(View.GONE);
				select_image.setVisibility(View.VISIBLE);
				sinceid=-1;
				sendtime="";
				mealtimes=-1;
				deliverDate="";
				tv_sendtime.setText("请选择收货时间");
				}
		});
		ll_pm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				if (newPhoneConfirmOrderResult.getConfirmOrderObject().getCanSince()==0||sendtype==-1) {
					Toast.makeText(activity, "当前小区不支持自提", Toast.LENGTH_SHORT).show();
					return;
				}
				if (select_image1.getVisibility()==0) {
					return;
				}
				sendtype=1;
				rl_sendAddress.setVisibility(View.VISIBLE);
				tv_send_type.setText("自提");
				tv_sendtime.setText("请选择自提时间");
				tv_address.setText("请选择自提点");
				sinceid=-1;
				sendtime="";
				mealtimes=-1;
				deliverDate="";
				image_selete_time.setVisibility(View.GONE);
				select_image1.setVisibility(View.VISIBLE);
				select_image.setVisibility(View.GONE);
				}
		});
    	}

	private void submitOrder(){
        if(paymentEntity==null){
        	paymentEntity=new PaymentEntity();
        	paymentEntity.setKey("余额支付");
           paymentEntity.setMid("1");
        }
        if(addressEntity==null){
            Toast.makeText(activity,"地址未选择",Toast.LENGTH_SHORT).show();
            return;
        }
     if (TextUtils.isEmpty(sendtime)) {
    	 if (sendtype==-1) {
    		 Toast.makeText(activity,"当前小区不支持配送	",Toast.LENGTH_SHORT).show();
				return;
		}
    	 if (sendtype==0) {
			if (TextUtils.isEmpty(sendtime)) {
				Toast.makeText(activity,"请选择收货时间",Toast.LENGTH_SHORT).show();
				return;
			}
		}else {
			if (TextUtils.isEmpty(sendtime)) {
				Toast.makeText(activity,"请选择收货时间",Toast.LENGTH_SHORT).show();
				return;
			}	
		}
    	 
		}
        progressDialog.setMessage("订单提交中...");
        progressDialog.show();
        String buyerRemark=confirm_order_buyer_remark.getText().toString();
        NewPhonecommitorderRequest request=new NewPhonecommitorderRequest();
        if (newPhoneConfirmOrderResult.getConfirmOrderObject().getIsFullt()==0) {
        	request.setRuleObject(null);
        }else {
			request.setRuleObject(newPhoneConfirmOrderResult.getConfirmOrderObject().getCommissionRule());
	        
		}
        request.setBuyerRemark(buyerRemark);
        request.setMid(baseApplication.getUserResult().getMarketId());
        if (!tv_discount.getText().toString().trim().equals("0.00")) {
        	 for (CouponEntity couponTypeEntity : newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList()) {
             	if (couponTypeEntity.isIschecked()) {
             		CouponIdList.add(couponTypeEntity.getCouponid()+"");
     			}
     		}
		}
        request.setMealtimes(mealtimes);
        request.setSinceid(sinceid);
        request.setShiptype(sendtype+1);
        request.setGoodsList(list);
        request.setCouponIdList(CouponIdList);
        request.setSaId(addressEntity.getSaId());
        request.setDeliverTime(sendtime);
        request.setPlotId(baseApplication.getUserResult().getPlotid());
        request.setDeliverDate(deliverDate);
        BaseRequestClient client=new BaseRequestClient(activity);
        client.httpPostByJsonNew("PhoneNewOrder", baseApplication.getUserResult(), request, NewPhonecommitorderResult.class, new BaseRequestClient.RequestClientNewCallBack<NewPhonecommitorderResult>() {

			@Override
			public void callBack(NewPhonecommitorderResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						PhonecommitorderResult result2=new PhonecommitorderResult();
						result2.setContentId(result.getOid()+"");
						result2.setOrderAmount(result.getSurplusmoney());
						result2.setSurplusMoney(result.getSurplusmoney());
						result2.setOrderTime(result.getOrderTime());
						result2.setOsn(result.getOsn());
						result2.setPayKey(paymentEntity.getKey());
						result2.setPayMid(paymentEntity.getMid());
						result2.setPaydiscountamount(result.getPaydiscountamount());
						  Intent intent=new Intent(activity,HomeHeaderActivity.class);
						  intent.putExtra("type",HeaderActivityType.PayOrder.ordinal());
						  intent.putExtra("result",result2);
						  intent.putExtra("yue", newPhoneConfirmOrderResult.getConfirmOrderObject().getUserPayMoney());
						  startActivity(intent);
						  activity.finish();
						  progressDialog.dismiss();
					}else {
						  progressDialog.dismiss();
						showtoast(result.getMessage(), activity);
					}
				}else {
					  progressDialog.dismiss();
					showtoast(activity.getResources().getString(R.string.data_error), activity);
				}
			}

			@Override
			public void loading(long count, long current) {
				
			}

			@Override
			public void logOut(NewPhonecommitorderResult result) {
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


    private final int UPDATE_ORDER=101;
    private Handler updateUIHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_ORDER:
                    fillOrderList();
                 
                    break;
                case 1000:
                	filldata();
                	break;
                case 500:
                	showguide(address, new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							guidedialog.dismiss();
							sp.edit().putBoolean("addressguide", false).commit();
						}
					}, true,R.drawable.address_guide_top, R.drawable.address_guide_bottom);
                    break;    
            }

        }

		
    };



    private void filldata() {
    	
    	fillOrderList();
    	confirm_order_ship_amount.setText(String.format("￥%.2f", newPhoneConfirmOrderResult.getConfirmOrderObject().getShipFee()));
    	//tv_fullcut_message.setText(String.format("￥%.1f", newPhoneConfirmOrderResult.getConfirmOrderObject().getProductAmount()));
    	tv_fullcut_message.setText(Html.fromHtml("小计：<font color='red'>"+String.format("￥%.2f", newPhoneConfirmOrderResult.getConfirmOrderObject().getProductAmount())+"</font>"));
    	tv_payment_balance.setText(String.format("￥%.2f", newPhoneConfirmOrderResult.getConfirmOrderObject().getPaydiscountamount()));
    	if (newPhoneConfirmOrderResult.getConfirmOrderObject().getShipFee()>0) {
    		tv_freight.setText(String.format("+%.2f", newPhoneConfirmOrderResult.getConfirmOrderObject().getShipFee()));
		}else {
			tv_freight.setText("+0.00");
		}
    	
    	tv_yue.setText(String.format("￥%.2f", newPhoneConfirmOrderResult.getConfirmOrderObject().getUserPayMoney()));
    	confirm_order_cart_total_price.setText(String.format("￥%.2f", newPhoneConfirmOrderResult.getConfirmOrderObject().getProductAmount()));
    	confirm_order_total_amount.setText(String.format("￥%.2f", newPhoneConfirmOrderResult.getConfirmOrderObject().getOrderAmount()));
    	tv_discount.setText(String.format("%.2f",0.00));
    	if (newPhoneConfirmOrderResult.getConfirmOrderObject().getCommissionRule()!=null&&newPhoneConfirmOrderResult.getConfirmOrderObject().getIsFullt()==1) {
    		if (newPhoneConfirmOrderResult.getConfirmOrderObject().getCommissionRule().getFullcut()>0) {
    			tv_fullcut.setText(String.format("-%.2f",Float.parseFloat(newPhoneConfirmOrderResult.getConfirmOrderObject().getCommissionRule().getFullcut()+"")));
    		}else {
				tv_fullcut.setText("0.00");
			}
			}else{
    			tv_isshowfullcut.setVisibility(View.GONE);
    			tv_fullcut.setVisibility(View.GONE);
    			
			}
        	if (newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList().size()>0) {
		CouponEntity entity1 = newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList().get(0);
		 newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList().get(0).setIschecked(true);
		ll_coupon.setVisibility(View.GONE);
		tv_coupon.setText("优惠劵(可以抵现金"+String.format("%.2f", entity1.getMoney())+"元)");
		tv_discount.setText(String.format("-%.2f",entity1.getMoney()));
		confirm_order_total_amount.setText(String.format("￥%.2f", Float.parseFloat(confirm_order_total_amount.getText().toString().trim().split("￥")[1])-entity1.getMoney()));
		tv_payment_balance.setText(String.format("￥%.2f", Float.parseFloat(tv_payment_balance.getText().toString().trim().split("￥")[1])-entity1.getMoney()));
		adapter.notifyDataSetChanged();
        	}
	
    	
    }
    
    private int getweightBalance(NewHomeEntity entity){
		int weightBalance=0;
		if (entity.getIsmain()==1) {
			weightBalance=entity.getMaincourseminweight()-entity.getIncrementweight();
		}else {
			weightBalance=entity.getSidecourseminweight()-entity.getIncrementweight();
		}
		return weightBalance;
	}

 

    private void fillOrderList(){
    	QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
    	qb.where("userAccount=? and marketId=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId()});
		ArrayList<NewHomeEntity> arrayList = db.query(qb);
		list = new ArrayList<NewHomeEntity>();
		for (NewHomeEntity newHomeEntity : arrayList) {
			if (newHomeEntity.isChecked()) {
				newHomeEntity.setWeight(newHomeEntity.getCount()*newHomeEntity.getIncrementweight()+getweightBalance(newHomeEntity));
				list.add(newHomeEntity);
			}
		}
    	ShopCartAdapter adapter=new ShopCartAdapter(activity,true,list, new CartClick() {
			
			@Override
			public void jian(int goodId, int num) {
				
			}
			
			@Override
			public void jia(int goodId, int num) {
				
			}
			
			
			@Override
			public void checkboxCheck(boolean b, int position) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void click(int gid) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void delete(Integer id, boolean isShow) {
				// TODO Auto-generated method stub
				
			}
		});
    		
    	parentCartListView.setAdapter(adapter);
    	adapter.setExplainEntity(new ExplainEntity(newPhoneConfirmOrderResult.getDiscountprefix(), newPhoneConfirmOrderResult.getDiscountsuffix(), newPhoneConfirmOrderResult.getSpecialwords(), newPhoneConfirmOrderResult.getFulltextreduction()));
    }

    /**
     * 获取地址信息以及区域
     */
    private void fetchAddress(){
    	
    	TextView confirm_order_address_name = (TextView) confirm_order_address.findViewById(R.id.confirm_order_address_name);
    	TextView confirm_order_address_detail = (TextView) confirm_order_address.findViewById(R.id.confirm_order_address_detail);
    	ArrayList<AddressEntity> shipAddressList = newPhoneConfirmOrderResult.getConfirmOrderObject().getShipAddressList();
    	if (shipAddressList!=null&&shipAddressList.size()==0) {
    		iv_more.setVisibility(View.VISIBLE);
    		img_empty.setVisibility(View.VISIBLE);
    		confirm_order_address_name.setText("请您完善收货地址");
		}else {
			addressEntity = shipAddressList.get(0);
			iv_more.setVisibility(View.GONE);
    		img_empty.setVisibility(View.GONE);
    		confirm_order_address_name.setText(String.format("%s  %s",addressEntity.getConsignee(),addressEntity.getMobile()));
    		confirm_order_address_detail.setText("武汉市洪山区"+addressEntity.getPlot().getName()+addressEntity.getAddress());
		}

    }
    /**
     * 优惠Adapter
     */
    private class MyAdapter extends BaseAdapter{
    	
    	

		@Override
		public int getCount() {
			return newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList().size();
		}

		@Override
		public Object getItem(int position) {
			return newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView==null){
				holder =new ViewHolder();
				convertView = View.inflate(activity, R.layout.order_coupon_item_layout, null);
				holder.checkbox=(CheckBox) convertView.findViewById(R.id.checkbox);
				holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
				holder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			CouponEntity entity = newPhoneConfirmOrderResult.getConfirmOrderObject().getValidCouponList().get(position);
			if (entity!=null) {
				holder.checkbox.setChecked(entity.isIschecked());
				holder.tv_money.setText(String.format("￥%.2f", entity.getMoney()));
				holder.tv_product_name.setText(entity.getName());
			}
			return convertView;
		}
		
	}
    class ViewHolder{
    	CheckBox checkbox;
		TextView tv_money;
		TextView tv_product_name;

	}

	private ArrayList<String> timeList=new ArrayList<String>();

	private ArrayList<NewHomeEntity> list;

	private TextView tv_address;

	private TextView tv_sendtime;;
    private void showdialog() {
    	final Dialog dialog=new Dialog(activity, R.style.Dialog);
    	dialog.setCancelable(false);
    	dialog.setContentView(R.layout.select_sendtime);
    	WindowManager m = activity.getWindowManager();
        Window dialogWindow = dialog.getWindow();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.60); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.80); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
       ListView lv= (ListView) dialog.findViewById(R.id.lv);
       timeList.clear();
       ArrayList<String> timelist = newPhoneConfirmOrderResult.getConfirmOrderObject().getDeliverTime();
       String date = newPhoneConfirmOrderResult.getConfirmOrderObject().getDeliverDate();
       for (String time : timelist) {
    	   time=date+time;
       }
       timeList.addAll(timelist);
       MyBaseAdapter adapter=new MyBaseAdapter();
       lv.setAdapter(adapter);
       lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			sendtime=timeList.get(position);
			//send_time.setText(""+timeList.get(position));
			dialog.dismiss();
		}
	});
		dialog.show();
	}

	class MyBaseAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return timeList.size();
		}

		@Override
		public Object getItem(int position) {
			return timeList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(activity, R.layout.select_sendtime_item, null);
			TextView tv=(TextView) view.findViewById(R.id.tv);
			tv.setText(newPhoneConfirmOrderResult.getConfirmOrderObject().getDeliverDate()+" "+timeList.get(position));
			return view;
		}
		
	}
	
}
