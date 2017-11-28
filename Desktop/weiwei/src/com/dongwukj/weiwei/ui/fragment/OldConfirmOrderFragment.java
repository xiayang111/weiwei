package com.dongwukj.weiwei.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
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
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.CouponRoleListRequest;
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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalDb;

/**
 * Created by sunjaly on 15/4/13.
 */
public class OldConfirmOrderFragment extends AbstractHeaderFragment {

    private AddressEntity addressEntity;

    private ProgressDialog pg;
    private LinearLayout confirm_order_address;
    private LinearLayout confirm_order_add_address;
    private ParentCartListView parentCartListView;
    private TextView confirm_order_cart_total_price;
    private TextView confirm_order_payment_text;
    private TextView confirm_order_ship_amount;
    private TextView confirm_order_total_amount;
    private ArrayList<String> CouponIdList=new ArrayList<String>();                          //使用优惠劵id的拼接
    private RelativeLayout confirm_order_valid_container;
    //private TextView confirm_order_user_credits_text;
   // private EditText confirm_order_user_credits_used_edit;
    private Button confirm_order_submit_button;
    private EditText confirm_order_buyer_remark;

	private ArrayList<CouponTypeEntity> couponTypeEntitys = new ArrayList<CouponTypeEntity>();
    private final int ADD_ADDRESS_REQUEST_CODE=110;
    private final int CHECK_ADDRESS_REQUEST_CODE=111;
    private final int CHECK_PAYMENT_REQUEST_CODE=112;


    private ArrayList<CartItemEntity> list;
    private ArrayList<CartItemEntity> showList;
    private DataBase db;

    private PaymentEntity paymentEntity;
    private PhoneConfirmOrderResult phoneConfirmOrderResult;
    private float totalAmount;
    private final int creditsConst=100;
    private PriceAndAmount priceAndAmount;

	protected boolean isAddAddress=false;
	private TextView tv_freight;

	private TextView tv_discount;

	private TextView tv_yue;

	private String imgUrl;

	private String taocanImgUrl;

	private TextView tv_loucation_order;

	private ImageView iv_more;

	private MyAdapter adapter;

	private LinearLayout ll_coupon;
    private float limitMoney;//订单可使用最多的优惠劵
    private float total;
    private ArrayList<TimeConfigLis> deliveryTimeConfigList;//封装收货时间的集合
    private String DeliveryEndTime;
    private int index=0;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.confirm_order_layout,container,false);
        imgUrl = activity.getIntent().getStringExtra("imgUrl");
        taocanImgUrl = activity.getIntent().getStringExtra("taocanImgUrl");
       pg=new ProgressDialog(activity);
        pg.setTitle("提示");
        pg.setMessage("加载中...");
        sp = activity.getSharedPreferences("addressguide", 0);
        return view;
    }

    @Override
    protected String setTitle() {
        return baseApplication.getString(R.string.confirm_order_header_title)
                ;
    }
    
    @Override
    public void onResume() {
    	
    	super.onResume();
    	//fetchAddress();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==ADD_ADDRESS_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            fetchAddress();
        }else if(requestCode==CHECK_ADDRESS_REQUEST_CODE && resultCode==Activity.RESULT_OK){
            addressEntity=(AddressEntity)data.getSerializableExtra("AddressEntity");
            if(addressEntity!=null){
                fillAddressContent(addressEntity);
            }else{
                fetchAddress();
            }
        }else if(requestCode==CHECK_PAYMENT_REQUEST_CODE && resultCode==Activity.RESULT_OK){
            paymentEntity=(PaymentEntity)data.getSerializableExtra("paymentEntity");
            if(paymentEntity!=null){
                fillPaymentContent(paymentEntity);
            }

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
    	 
    	address_progress = (RelativeLayout) v.findViewById(R.id.address_progress);
    	tv_coupon = (TextView) v.findViewById(R.id.tv_coupon);
    	send_time = (TextView) v.findViewById(R.id.send_time);
    	send_time.setOnClickListener(new OnClickListener() {
			
			private RadioButton tomorrow_pm;
			private RadioButton after_tomorrow_am;
			private RadioButton tomorrow_am;

			@Override
			public void onClick(View v) {
				  final Dialog dialog=new Dialog(activity,R.style.Dialog);
                  dialog.setContentView(R.layout.select_send_time);
           tomorrow_am = (RadioButton) dialog.findViewById(R.id.tomorrow_am);
                  tomorrow_am.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						 send_time.setText(deliveryTimeConfigList.get(0).getDeliveryEndTime().replace("/", "-").replace(" ", "(明天)/").replace("/", deliveryTimeConfigList.get(0).getDeliveryStartTime()+"-"));
						 tomorrow_am.setChecked(true);
						 tomorrow_pm.setChecked(false);
						 after_tomorrow_am.setChecked(false);
						 index=0;
						 DeliveryEndTime=deliveryTimeConfigList.get(0).getDeliveryEndTime();
						 dialog.dismiss();
					}
				});
                  tomorrow_pm = (RadioButton) dialog.findViewById(R.id.tomorrow_pm);
                  tomorrow_pm.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						send_time.setText(deliveryTimeConfigList.get(1).getDeliveryEndTime().replace("/", "-").replace(" ", "(明天)/").replace("/", deliveryTimeConfigList.get(1).getDeliveryStartTime()+"-"));
						 tomorrow_am.setChecked(false);
						 tomorrow_pm.setChecked(true);
						 after_tomorrow_am.setChecked(false);   
						 index=1;
						 DeliveryEndTime=deliveryTimeConfigList.get(1).getDeliveryEndTime();
						dialog.dismiss();
					}
				});
                  after_tomorrow_am = (RadioButton) dialog.findViewById(R.id.after_tomorrow_am);
                  after_tomorrow_am.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						send_time.setText(deliveryTimeConfigList.get(2).getDeliveryEndTime().replace("/", "-").replace(" ", "(后天)/").replace("/", deliveryTimeConfigList.get(2).getDeliveryStartTime()+"-"));
						tomorrow_am.setChecked(false);
						 tomorrow_pm.setChecked(false);
						 after_tomorrow_am.setChecked(true);   
						 index=2;
						 DeliveryEndTime=deliveryTimeConfigList.get(2).getDeliveryEndTime();
						dialog.dismiss();
					}
				});
                  ImageView delete=(ImageView) dialog.findViewById(R.id.delete);
                  delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						   dialog.dismiss();
						
					}
				});
                 tomorrow_am.setText(deliveryTimeConfigList.get(0).getDeliveryEndTime().replace("/", "-").replace(" ", "(明天)\n/").replace("/", deliveryTimeConfigList.get(0).getDeliveryStartTime()+"-"));
                 
                 tomorrow_pm.setText(deliveryTimeConfigList.get(1).getDeliveryEndTime().replace("/", "-").replace(" ", "(明天)\n/").replace("/", deliveryTimeConfigList.get(1).getDeliveryStartTime()+"-"));
                
                 after_tomorrow_am.setText(deliveryTimeConfigList.get(2).getDeliveryEndTime().replace("/", "-").replace(" ", "(后天)\n/").replace("/", deliveryTimeConfigList.get(2).getDeliveryStartTime()+"-"));
                switch (index) {
				case 0:
					tomorrow_am.setChecked(true);
					 tomorrow_pm.setChecked(false);
					 after_tomorrow_am.setChecked(false);   
					break;
				case 1:
					tomorrow_am.setChecked(false);
					 tomorrow_pm.setChecked(true);
					 after_tomorrow_am.setChecked(false);   
					break;
				case 2:
					tomorrow_am.setChecked(false);
					 tomorrow_pm.setChecked(false);
					 after_tomorrow_am.setChecked(true);   
					break;

				default:
					break;
				}
                 dialog.setCancelable(false);
                 dialog.show();
			}
		});
    	ll_coupon = (LinearLayout) v.findViewById(R.id.ll_coupon);
    	MyListView lv_coupon=(MyListView) v.findViewById(R.id.lv_coupon);
    	adapter = new MyAdapter();
    	lv_coupon.setAdapter(adapter);
    	lv_coupon.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//boolean isUseCoupon=false;
				/*if (total>limitMoney) {
					Toast.makeText(activity, "超过最大使用额度", 0).show();
					adapter.setChangeBackground(true);
					return;
				}*/
				adapter.getView(position, null, null);
				CouponTypeEntity couponTypeEntity = couponTypeEntitys.get(position);
				if (couponTypeEntity.getCouponType().getUseMode()==0) {
					for (CouponTypeEntity couponTypeEntity2 : couponTypeEntitys) {
						if (couponTypeEntity2.getCouponType().getUseMode()==1) {
							if (couponTypeEntity2.isIschecked()) {
								Toast.makeText(activity, "此优惠劵不可叠加", 0).show();
								return;
							}
						}
					}
				}else {
					if (!couponTypeEntity.isIschecked()) {
						for (CouponTypeEntity couponTypeEntity2 : couponTypeEntitys) {
							if (couponTypeEntity2.isIschecked()) {
								Toast.makeText(activity, "此优惠劵不可叠加", 0).show();
								return;
							}
						}
						total=total+couponTypeEntity.getCouponType().getMoney();
						adapter.setChangeBackground(true);
						tv_coupon.setText("优惠劵("+String.format("￥%.1f",total)+")");
						confirm_order_total_amount.setText(String.format("￥%.1f",((totalAmount-total)>=0?(totalAmount-total):0)));
						tv_discount.setText(String.format("￥%.1f",(total<=totalAmount)?(phoneConfirmOrderResult.getFullCut()+total):(phoneConfirmOrderResult.getFullCut()+totalAmount)));
						couponTypeEntitys.get(position).setIschecked(!couponTypeEntitys.get(position).isIschecked());
						return;
					}
				}
				couponTypeEntitys.get(position).setIschecked(!couponTypeEntitys.get(position).isIschecked());
				if (couponTypeEntity.isIschecked()) {
					if (total+couponTypeEntity.getCouponType().getMoney()==limitMoney) {
						total=total+couponTypeEntity.getCouponType().getMoney();
						adapter.setChangeBackground(true);
						tv_coupon.setText("优惠劵("+String.format("￥%.1f",total)+")");
						confirm_order_total_amount.setText(String.format("￥%.1f",((totalAmount-total)>=0?(totalAmount-total):0)));
						tv_discount.setText(String.format("￥%.1f",(total<=totalAmount)?(phoneConfirmOrderResult.getFullCut()+total):(phoneConfirmOrderResult.getFullCut()+totalAmount)));
						return;
					}
					if (total+couponTypeEntity.getCouponType().getMoney()>limitMoney) {
						Toast.makeText(activity, "超过最大使用额度", 0).show();
						couponTypeEntitys.get(position).setIschecked(!couponTypeEntitys.get(position).isIschecked());
						//adapter.setChangeBackground(true);
						return;
					}else {
						total=total+couponTypeEntity.getCouponType().getMoney();
					}
				}else {
					total=total-couponTypeEntity.getCouponType().getMoney();
				}
				tv_coupon.setText("优惠劵("+String.format("￥%.1f",total)+")");
				confirm_order_total_amount.setText(String.format("￥%.1f",((totalAmount-total)>=0?(totalAmount-total):0)));
				tv_discount.setText(String.format("￥%.1f",(total<=totalAmount)?(phoneConfirmOrderResult.getFullCut()+total):(phoneConfirmOrderResult.getFullCut()+totalAmount)));
				
				/*for (CouponTypeEntity couponTypeEntity : couponTypeEntitys) {
					if (couponTypeEntity.equals(couponTypeEntitys.get(position))) {
						couponTypeEntitys.get(position).setIschecked(!couponTypeEntitys.get(position).isIschecked());
					}
					if (couponTypeEntity.isIschecked()) {
						total=total+couponTypeEntity.getCouponType().getMoney();
					}else {
						total=total-couponTypeEntity.getCouponType().getMoney();
					}
					tv_coupon.setText("优惠劵("+String.format("￥%.2f",total)+")");
					else {
						couponTypeEntity.setIschecked(false);
					}
				}*/
				adapter.setChangeBackground(false);
				//adapter.notifyDataSetChanged();
				/*ll_coupon.setVisibility(View.GONE);
				CouponIdList=couponTypeEntitys.get(position).getCouponid()+"";
				float money =0;
				for (CouponTypeEntity couponTypeEntity : couponTypeEntitys) {
					if (couponTypeEntity.isIschecked()) {
						money=couponTypeEntity.getCouponType().getMoney()+money;
						isUseCoupon=true;
					}
				}if (isUseCoupon) {
					confirm_order_total_amount.setText(String.format("￥%.2f",totalAmount-money));
					tv_discount.setText(String.format("￥%.2f",phoneConfirmOrderResult.getFullCut()+money));
					tv_coupon.setText("优惠劵("+String.format("￥%.2f",money)+")");
				}else {
					confirm_order_total_amount.setText(String.format("￥%.2f",totalAmount));
					 tv_discount.setText(String.format("￥%.2f",phoneConfirmOrderResult.getFullCut()));
					 tv_coupon.setText("优惠劵");
				}*/
				
			}
		});
    	tv_loucation_order = (TextView) v.findViewById(R.id.tv_loucation_order);
    	db = LiteOrm.newCascadeInstance(activity, BaseApplication.DB_NAME);
        confirm_order_address=(LinearLayout)v.findViewById(R.id.confirm_order_address);
        confirm_order_add_address=(LinearLayout)v.findViewById(R.id.confirm_order_add_address);
        iv_more = (ImageView) v.findViewById(R.id.iv_more);
        v.findViewById(R.id.confirm_order_add_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, HomeHeaderActivity.class);
                intent.putExtra("type", HeaderActivityType.NewAddress.ordinal());
                startActivityForResult(intent, ADD_ADDRESS_REQUEST_CODE);
            }
        });
        confirm_order_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, HomeHeaderActivity.class);
                intent.putExtra("type", HeaderActivityType.Address.ordinal());
                intent.putExtra("mode",2);
                startActivityForResult(intent, CHECK_ADDRESS_REQUEST_CODE);
                
            }
        });

        parentCartListView=(ParentCartListView)v.findViewById(R.id.parent_confirm_order_list);

        confirm_order_cart_total_price=(TextView)v.findViewById(R.id.confirm_order_cart_total_price);
        v.findViewById(R.id.confirm_order_change_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity,HomeHeaderActivity.class);
                intent.putExtra("type",HeaderActivityType.PaymentList.ordinal());
                intent.putExtra("list", (Serializable) phoneConfirmOrderResult.getPayPluginList());
                startActivityForResult(intent,CHECK_PAYMENT_REQUEST_CODE);
            }
        });

        confirm_order_payment_text=(TextView)v.findViewById(R.id.confirm_order_payment_text);
        confirm_order_ship_amount=(TextView)v.findViewById(R.id.confirm_order_ship_amount);
       tv_freight =(TextView)v.findViewById(R.id.tv_freight);
       tv_discount = (TextView)v.findViewById(R.id.tv_discount);
        confirm_order_cart_total_price=(TextView)v.findViewById(R.id.confirm_order_cart_total_price);
        confirm_order_total_amount=(TextView)v.findViewById(R.id.confirm_order_total_amount);


        confirm_order_valid_container=(RelativeLayout)v.findViewById(R.id.confirm_order_valid_container);
        confirm_order_valid_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(phoneConfirmOrderResult==null || phoneConfirmOrderResult.getValidCouponList()==null || phoneConfirmOrderResult.getValidCouponList().size()<1){
                    Toast.makeText(activity,"没有可用优惠券",Toast.LENGTH_SHORT).show();
                }*/
            	if (ll_coupon.getVisibility()==View.GONE) {
            		if (couponTypeEntitys.size()==0) {
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
        /*confirm_order_user_credits_text=(TextView)v.findViewById(R.id.confirm_order_user_credits_text);
        confirm_order_user_credits_used_edit=(EditText)v.findViewById(R.id.confirm_order_user_credits_used_edit);

        confirm_order_user_credits_used_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String editString=s.toString();
                if(TextUtils.isEmpty(editString)){
                	tv_discount.setText("￥0.00");
                	 confirm_order_total_amount.setText(String.format("￥%.2f",totalAmount));
                	 return;
                };
                try{
                    float editInt=Float.parseFloat(editString);
                    if(editInt>phoneConfirmOrderResult.getMaxUsePayCredits()){
                        Toast.makeText(activity,"积分数要小于"+phoneConfirmOrderResult.getMaxUsePayCredits(),Toast.LENGTH_SHORT).show();
                        confirm_order_user_credits_used_edit.setText("");
                    }else if(editInt>totalAmount*creditsConst){
                        Toast.makeText(activity,"积分数要小于"+totalAmount*creditsConst,Toast.LENGTH_SHORT).show();
                        confirm_order_user_credits_used_edit.setText("");
                    }else{
                        float newTotalAMount=0;
                        newTotalAMount=(totalAmount-editInt/100);
                        confirm_order_total_amount.setText(String.format("￥%.2f",newTotalAMount));
                        
                        tv_discount.setText(String.format("￥%.2f",editInt/100));
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(activity,"请输入数字",Toast.LENGTH_SHORT).show();
                    confirm_order_user_credits_used_edit.setText("");

                }


            }
        });
*/
        confirm_order_submit_button=(Button)v.findViewById(R.id.confirm_order_submit_button);
        confirm_order_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

        confirm_order_buyer_remark=(EditText)v.findViewById(R.id.confirm_order_buyer_remark);
        fetchAddress();
        fetchOrderData();
       if (sp.getBoolean("addressguide", true)) {

           updateUIHandler.sendEmptyMessageDelayed(500, 500);
	}
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
        if (Float.parseFloat(tv_yue.getText().toString().trim().split("￥")[1])<Float.parseFloat(confirm_order_total_amount.getText().toString().trim().split("￥")[1])&&paymentEntity.getMid().equals("1")) {
        	Toast.makeText(activity,"余额不足,请选择别的支付方式",Toast.LENGTH_SHORT).show();
        	return;
		}
        String buyerRemark=confirm_order_buyer_remark.getText().toString();
       /* if(TextUtils.isEmpty(buyerRemark)){
            Toast.makeText(activity,"备注信息未填写",Toast.LENGTH_SHORT).show();
            return;
        }*/

        PhonecommitorderRequest request=new PhonecommitorderRequest();
   /*     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        request.setBestTime(sdf.format(new Date()));*/
        request.setBestTime(DeliveryEndTime);
        request.setBuyerRemark(buyerRemark);
        for (CouponTypeEntity couponTypeEntity : couponTypeEntitys) {
        	if (couponTypeEntity.isIschecked()) {
        		CouponIdList.add(couponTypeEntity.getCouponid()+"");
			}
		}
        request.setCouponIdList(CouponIdList);
       // String creditAmount=confirm_order_user_credits_used_edit.getText().toString();
       /* if(TextUtils.isEmpty(creditAmount)){
            request.setPayCreditCount(0);
        }else{
            request.setPayCreditCount(Integer.parseInt(creditAmount));
        }*/
        request.setIp(NetworkUtil.getLocalIpAddress());
        request.setPayName("1");
        request.setSaId(addressEntity.getSaId());
        request.setSelectedCartItemKeyList(priceAndAmount.orderIdList);
        request.setDeliveryEndTime(DeliveryEndTime);
        MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(activity,baseApplication);
        myWeiWeiRequestClient.submitOrder(request, new MyWeiWeiRequestClient.SubmitOrderRequestClientCallback() {
            @Override
            protected void result(PhonecommitorderResult result) {
            	if (getActivity()==null) {
					return;
				}
                Intent intent=new Intent(activity,HomeHeaderActivity.class);
               /* if (result.getCode()==959) {
                	 intent.putExtra("isBalanceLack",true);
                }*/
                ArrayList<PhoneConfirmOrderResult> arrayList = db.queryAll(PhoneConfirmOrderResult.class);
                if (arrayList.size()>0&&arrayList!=null) {
					for (PhoneConfirmOrderResult phoneConfirmOrderResult : arrayList) {
						db.delete(phoneConfirmOrderResult);
					}
				}
                phoneConfirmOrderResult.setOrderAmount(Float.parseFloat(confirm_order_total_amount.getText().toString().trim().split("￥")[1]));
                db.save(phoneConfirmOrderResult);
                result.setOrderAmount(Float.parseFloat(confirm_order_total_amount.getText().toString().trim().split("￥")[1]));
                result.setPayKey(paymentEntity.getKey());
                result.setPayMid(paymentEntity.getMid());
                intent.putExtra("type",HeaderActivityType.PayOrder.ordinal());
                intent.putExtra("result",result);
                intent.putExtra("yue", Float.parseFloat(tv_yue.getText().toString().trim().split("￥")[1]));
                startActivity(intent);
                activity.finish();
            }

            @Override
            protected void resultComplete(PhonecommitorderResult result) {
            	if (getActivity()==null) {
					return;
				}
                super.resultComplete(result);
            }
            @Override
            protected void resultFalied(PhonecommitorderResult result) {
            	if (getActivity()==null) {
					return;
				}
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


    private final int UPDATE_ORDER=101;
    private Handler updateUIHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_ORDER:
                    fillOrderList();
                    priceAndAmount=(PriceAndAmount)msg.obj;
                   // fillTotalPrice(priceAndAmount);
                    fetchOrderExt(priceAndAmount);
                    
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



    private void fetchOrderExt(final PriceAndAmount priceAndAmount){
        final PhoneConfirmOrderRequest phoneConfirmOrderRequest=new PhoneConfirmOrderRequest();
        phoneConfirmOrderRequest.setGoodsId(priceAndAmount.orderIdList);
        phoneConfirmOrderRequest.setSelectedCartItemKeyList(priceAndAmount.orderIdList);
        MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(activity,baseApplication);
        myWeiWeiRequestClient.confirmOrder(phoneConfirmOrderRequest, new MyWeiWeiRequestClient.ConfirmRequestClientCallback() {
            @Override
            protected void result(PhoneConfirmOrderResult result) {
            	if (getActivity()==null) {
					return;
				}
                phoneConfirmOrderResult=result;
                confirm_order_ship_amount.setText(String.format("￥%.1f",phoneConfirmOrderResult.getShipFee()));
                tv_freight.setText(String.format("￥%.1f",phoneConfirmOrderResult.getShipFee()));
                Float creditsAmount=phoneConfirmOrderResult.getUserPayCredits()*phoneConfirmOrderResult.getPayCreditName();
                deliveryTimeConfigList=result.getDeliveryTimeConfigList();		
                TimeConfigLis configLis = deliveryTimeConfigList.get(0);
                String deliveryEndTime = configLis.getDeliveryEndTime();
                String deliveryStartTime = configLis.getDeliveryStartTime();
                DeliveryEndTime=deliveryEndTime;
                send_time.setText( deliveryEndTime.replace("/", "-").replace(" ", "(明天)/").replace("/", deliveryStartTime+"-"));
              //  confirm_order_user_credits_text.setText(String.format("可用积分%d，本次可用%d",phoneConfirmOrderResult.getUserPayCredits(),phoneConfirmOrderResult.getMaxUsePayCredits()));
               float usableIntegral =(float)(phoneConfirmOrderResult.getMaxUsePayCredits())/100;
               // confirm_order_user_credits_text.setText(String.format("可用积分%d，可抵押￥%.2f元",phoneConfirmOrderResult.getMaxUsePayCredits(),usableIntegral));
               tv_discount.setText(String.format("￥%.1f",result.getFullCut()));
               totalAmount=result.getOrderAmount();
                confirm_order_total_amount.setText(String.format("￥%.1f",totalAmount));
                tv_yue.setText(String.format("￥%.2f", phoneConfirmOrderResult.getUserPayMoney()));
                confirm_order_cart_total_price.setText(String.format("￥%.1f",(result.getProductAmount()+result.getFullCut())));
                fetchcouponRule();
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
            protected void resultComplete(PhoneConfirmOrderResult result) {
            	if (getActivity()==null) {
					return;
				}
                super.resultComplete(result);
            }
        });
    }

   /* private void fillTotalPrice(PriceAndAmount priceAndAmount){
       // confirm_order_cart_total_price.setText(String.format("共%d件商品  合计：￥%.2f",priceAndAmount.amount,priceAndAmount.price));
        confirm_order_cart_total_price.setText(String.format("￥%.2f",priceAndAmount.price));
    }*/

    private class PriceAndAmount{
        public int amount;
        private float price;
        private String orderIdList;
    }

    private void fetchOrderData(){
        showList=new ArrayList<CartItemEntity>();
        updateUIHandler.post(new Runnable() {
            @Override
            public void run() {
                float totalPrice=0;
                int amount=0;
                StringBuilder stringBuilder=new StringBuilder();
               // db= LiteOrm.newCascadeInstance(activity, BaseApplication.DB_NAME);
                list= db.queryAll(CartItemEntity.class);
                if(list!=null && list.size()>0) {

                    for (CartItemEntity cartItemEntity : list) {
                        if (cartItemEntity.getType() == 0 && cartItemEntity.getCartProduct() != null) {
                            if (cartItemEntity.getCartProduct().getSelected()) {
                                showList.add(cartItemEntity);
                                totalPrice+=cartItemEntity.getCartProduct().getOrderProductInfo().getDiscountPrice()*cartItemEntity.getCartProduct().getOrderProductInfo().getBuyCount();
                                amount++;
                                stringBuilder.append("0_"+cartItemEntity.getCartProduct().getOrderProductInfo().getPid()+",");
                            }
                        } else if (cartItemEntity.getType() == 1 && cartItemEntity.getCartSuit() != null) {
                            if (cartItemEntity.getCartSuit().getChecked()) {
                                showList.add(cartItemEntity);
                                totalPrice+=cartItemEntity.getCartSuit().getSuitPrice()*cartItemEntity.getCartSuit().getBuyCount();
                                amount++;
                                stringBuilder.append("1_"+cartItemEntity.getCartSuit().getPmId()+",");
                            }
                        } else if (cartItemEntity.getType() == 2 && cartItemEntity.getCartFullSend() != null) {
                            if (cartItemEntity.getCartFullSend().getFullSendMainCartProductList() != null && cartItemEntity.getCartFullSend().getFullSendMainCartProductList().size() > 0) {
                                ArrayList<CartProductEntity> productEntityArrayList = cartItemEntity.getCartFullSend().getFullSendMainCartProductList();
                                ArrayList<CartProductEntity> newProductEntityArrayList = new ArrayList<CartProductEntity>();
                                for (CartProductEntity cartProductEntity : productEntityArrayList) {
                                    if (cartProductEntity.getSelected()) {
                                        newProductEntityArrayList.add(cartProductEntity);
                                        totalPrice+=cartProductEntity.getOrderProductInfo().getDiscountPrice()*cartProductEntity.getOrderProductInfo().getBuyCount();
                                        amount++;
                                        stringBuilder.append("0_"+cartProductEntity.getOrderProductInfo().getPid()+",");
                                    }
                                }

                                cartItemEntity.getCartFullSend().setFullSendMainCartProductList(newProductEntityArrayList);
                                if (newProductEntityArrayList.size() > 0) {
                                    showList.add(cartItemEntity);
                                }
                            }
                        } else if (cartItemEntity.getType() == 3 && cartItemEntity.getCartFullCut() != null) {
                            if (cartItemEntity.getCartFullCut().getFullCutCartProductList() != null && cartItemEntity.getCartFullCut().getFullCutCartProductList().size() > 0) {
                                ArrayList<CartProductEntity> productEntityArrayList = cartItemEntity.getCartFullCut().getFullCutCartProductList();
                                ArrayList<CartProductEntity> newProductEntityArrayList = new ArrayList<CartProductEntity>();
                                for (CartProductEntity cartProductEntity : productEntityArrayList) {
                                    if (cartProductEntity.getSelected()) {
                                        newProductEntityArrayList.add(cartProductEntity);
                                        totalPrice+=cartProductEntity.getOrderProductInfo().getDiscountPrice()*cartProductEntity.getOrderProductInfo().getBuyCount();
                                        amount++;
                                        stringBuilder.append("0_"+cartProductEntity.getOrderProductInfo().getPid()+",");
                                    }
                                }
                                cartItemEntity.getCartFullCut().setFullCutCartProductList(newProductEntityArrayList);
                                if (newProductEntityArrayList.size() > 0) {
                                    showList.add(cartItemEntity);
                                }
                            }
                        }
                    }
                }
                PriceAndAmount priceAndAmount=new PriceAndAmount();
                priceAndAmount.amount=amount;
                priceAndAmount.price=totalPrice;
                priceAndAmount.orderIdList=stringBuilder.toString();
                updateUIHandler.obtainMessage(UPDATE_ORDER,priceAndAmount).sendToTarget();
            }
        });
    }

    private void fillOrderList(){

    		
            ParentCartListAdapter parentCartListAdapter=new ParentCartListAdapter(activity, showList, new ChildCartListView.ChildCartListChangeListener() {
                @Override
                public void itemCheck(boolean checked, int groupId, int position, int mode) {

                }

                
                @Override
                public void itemClick(int groupId, int position) {

                }

				@Override
				public void upOrDownAmount(int groupId, int position,
						boolean isPlus, String amount) {
					// TODO Auto-generated method stub
					
				}
            },true);
            parentCartListAdapter.setImageUrl(imgUrl, taocanImgUrl);
            parentCartListView.setAdapter(parentCartListAdapter);

    }

    /**
     * 获取地址信息以及区域
     */
    private void fetchAddress(){
    	address_progress.setVisibility(View.VISIBLE);
        MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(activity,baseApplication);
        myWeiWeiRequestClient.fetchAddressList(new MyWeiWeiRequestClient.AddressRequestCallback() {
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
            protected void list(AddressResult result) {
            	if (getActivity()==null) {
					return;
				}
            	address_progress.setVisibility(View.GONE);
                if(result.getShipAddresses()==null || result.getShipAddresses().size()<1){
                	AreaEntity areaEntity = AreaEntity.getInstance();
                	if(areaEntity==null){
                		tv_loucation_order.setText("目前无法定位到您的位置!");
                	}else{
                		if(TextUtils.isEmpty(areaEntity.getCity())||TextUtils.isEmpty(areaEntity.getArea())||TextUtils.isEmpty(areaEntity.getArea())||TextUtils.isEmpty(areaEntity.getStreetnumber())){
                			tv_loucation_order.setText("目前无法定位到您的位置!");
                		}else {
                			tv_loucation_order.setText(areaEntity.getCity()+areaEntity.getArea()+areaEntity.getStreet()+areaEntity.getStreetnumber());
						}
                	}
                	
                	confirm_order_address.setVisibility(View.GONE);
                    confirm_order_add_address.setVisibility(View.VISIBLE);
                    iv_more.setVisibility(View.VISIBLE);
                }else{
                    confirm_order_address.setVisibility(View.VISIBLE);
                    confirm_order_add_address.setVisibility(View.GONE);
                    for (AddressEntity entity : result.getShipAddresses()) {
						if (entity.getIsDefault()==1) {
							addressEntity=entity;
						}
					}
                    if (addressEntity==null) {
                    	addressEntity=result.getShipAddresses().get(0);
                    }
                    fillAddressContent(addressEntity);
                }
            }
        });

    }
    /**
     * 优惠Adapter
     */
    private class MyAdapter extends BaseAdapter{
    	private boolean isChangeBackground;
    	

		public void setChangeBackground(boolean isChangeBackground) {
			this.isChangeBackground = isChangeBackground;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return couponTypeEntitys.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return couponTypeEntitys.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView==null){
				holder =new ViewHolder();
				convertView = View.inflate(activity, R.layout.order_coupon_item_layout, null);
//				holder.tv_claim_coupon = (TextView) convertView.findViewById(R.id.tv_claim_coupon);
//				
//				holder.tv_claim_coupon.setVisibility(View.GONE);
				holder.checkbox=(CheckBox) convertView.findViewById(R.id.checkbox);
				holder.ll=(LinearLayout) convertView.findViewById(R.id.ll);
				holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
				holder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
				holder.tv_overlay = (TextView) convertView.findViewById(R.id.tv_overlay);
				holder.tv_order_amountlower = (TextView) convertView.findViewById(R.id.tv_order_amountlower);
				/*holder.tv_UseStartTime = (TextView) convertView.findViewById(R.id.tv_UseStartTime);
				holder.tv_UseEndTime = (TextView) convertView.findViewById(R.id.tv_UseEndTime);*/
				
				convertView.setTag(holder);
				
			}else{
				
				holder = (ViewHolder) convertView.getTag();
			}
			CouponListEntity entity = couponTypeEntitys.get(position).getCouponType();
			if (TextUtils.isEmpty(entity.getLimitCateName())&&entity.getLimitProducts().size()==0) {
				holder.ll.setBackgroundResource(R.drawable.weiwei_coupon_green);
				holder.tv_order_amountlower.setText("消费满"+entity.getOrderAmountLower()+"元使用(所有商品可用)");
				holder.tv_product_name.setVisibility(View.GONE);
			}else if (TextUtils.isEmpty(entity.getLimitCateName())&&entity.getLimitProducts().size()!=0) {
				holder.ll.setBackgroundResource(R.drawable.weiwei_coupon_yellow);
				holder.tv_order_amountlower.setText("消费满"+entity.getOrderAmountLower()+"元使用(以下商品可用)");
				holder.tv_product_name.setVisibility(View.VISIBLE);
				StringBuffer name=new StringBuffer();
				ArrayList<LimitProducts> products = entity.getLimitProducts();
				for (LimitProducts limitProducts : products) {
					name=name.append(limitProducts.getName());
				}
				holder.tv_product_name.setText(name.toString());
			}else if (!TextUtils.isEmpty(entity.getLimitCateName())&&entity.getLimitProducts().size()==0) {
				holder.ll.setBackgroundResource(R.drawable.weiwei_coupon_red);
				holder.tv_order_amountlower.setText("消费满"+entity.getOrderAmountLower()+"元使用("+entity.getLimitCateName()+"类别可用)");
				holder.tv_product_name.setVisibility(View.GONE);
			}
			holder.checkbox.setChecked(couponTypeEntitys.get(position).isIschecked());
			//holder.tv_money.setText("￥"+couponTypeEntitys.get(position).getCouponType().getMoney()+"");
			holder.tv_money.setText(String.format("￥%.1f", couponTypeEntitys.get(position).getCouponType().getMoney()));
			//holder.tv_order_amountlower.setText("消费满"+couponTypeEntitys.get(position).getCouponType().getOrderAmountLower()+"元使用");
			String text="";
			if (entity.getUseMode()==0) {	
				text="可叠加";
				}else {
				text="不可叠加";
			}
			if (isChangeBackground) {
				if (!couponTypeEntitys.get(position).isIschecked()) {
					holder.ll.setBackgroundResource(R.drawable.weiwei_coupon_outdate);
				}
			}
			holder.tv_overlay.setText(text);
//			holder.tv_UseStartTime.setText(sm.format(new Date(Long.parseLong(list.get(position).getCouponType().getUseStartTime()))));
//			holder.tv_UseEndTime.setText(sm.format(new Date(Long.parseLong(list.get(position).getCouponType().getUseEndTime()))));
			return convertView;
		}
		
	}
    class ViewHolder{
    	CheckBox checkbox;
		TextView tv_money;
		TextView tv_order_amountlower;
		TextView tv_claim_coupon;
		TextView tv_product_name;
		TextView tv_overlay;
		LinearLayout ll;
	}
    /**
     * 获取已经领取的优惠劵
     */
    private void getUserCouponList() {
		BaseRequestClient client = new BaseRequestClient(activity);
		UserResult userResult = baseApplication.getUserResult();
		BaseRequest request = new BaseRequest();
		progressDialog.setMessage("优惠劵获取中...");
		showProgress(true);
		client.httpPostByJson("PhoneGetUserCouponList", userResult, request, MyCouponListResult.class, new BaseRequestClient.RequestClientCallBack<MyCouponListResult>() {

			@Override
			public void callBack(MyCouponListResult result) {
				if(result!=null){
					if(result.getCode()==BaseResult.CodeState.Success.getCode()){
						
						if(result.getCouponList()==null){
							//Toast.makeText(activity, "暂时没有可用优惠券", Toast.LENGTH_SHORT).show();
//							lv_coupon.setVisibility(View.GONE);
//							ll_null_coupon.setVisibility(View.VISIBLE);
							ll_coupon.setVisibility(View.GONE);
							tv_coupon.setText("没有可用优惠劵");
						}else{
							//Toast.makeText(activity, "用户优惠券列表获取成功", Toast.LENGTH_SHORT).show();
							
							couponTypeEntitys.clear();
							adapter.notifyDataSetChanged();
							ArrayList<CouponTypeEntity> list2 = result.getCouponList();
							ArrayList<CouponTypeEntity> list3 = new ArrayList<CouponTypeEntity>();
							for (CouponTypeEntity couponTypeEntity : list2) {
								if (Long.parseLong(couponTypeEntity.getCouponType().getUseEndTime())>Long.parseLong(result.getTimestamp())&&couponTypeEntity.getCouponType().getMoney()<=limitMoney) {
									if (couponTypeEntity.getCouponType().getOrderAmountLower()<=totalAmount) {
										CouponListEntity entity = couponTypeEntity.getCouponType();
										if (TextUtils.isEmpty(entity.getLimitCateName())&&entity.getLimitProducts().size()==0) {
											//全部可以使用
											list3.add(couponTypeEntity);
										}else if (TextUtils.isEmpty(entity.getLimitCateName())&&entity.getLimitProducts().size()!=0) {
											float productsPrice=0;
											//以下商品可以用
											for (CartItemEntity cartItemEntity : showList) {
												if (cartItemEntity.getCartProduct()!=null) {
													int pid = cartItemEntity.getCartProduct().getOrderProductInfo().getPid();
													ArrayList<LimitProducts> limitProducts = entity.getLimitProducts();
													for (LimitProducts limitProducts2 : limitProducts) {
														if (limitProducts2.getpId()==pid) {
															//list3.add(couponTypeEntity);
															productsPrice=productsPrice+cartItemEntity.getCartProduct().getOrderProductInfo().getShopPrice()*cartItemEntity.getCartProduct().getOrderProductInfo().getBuyCount();
														}
														
													}
												}
											}
											if (productsPrice>=entity.getOrderAmountLower()) {
												list3.add(couponTypeEntity);
											}
										}else if (!TextUtils.isEmpty(entity.getLimitCateName())&&entity.getLimitProducts().size()==0) {
											float productsPrice=0;
											for (CartItemEntity cartItemEntity : showList) {
												if (cartItemEntity.getCartProduct()!=null) {
													int pid = cartItemEntity.getCartProduct().getOrderProductInfo().getCateId();
													 int cateId = entity.getLimitCateId();
													if (pid==cateId) {
														//list3.add(couponTypeEntity);
														productsPrice=productsPrice+cartItemEntity.getCartProduct().getOrderProductInfo().getShopPrice()*cartItemEntity.getCartProduct().getOrderProductInfo().getBuyCount();
													}
												}
											}
											if (productsPrice>entity.getOrderAmountLower()) {
												list3.add(couponTypeEntity);
											}
										}
									}
								}
							}
							if (list3.size()==0) {
								ll_coupon.setVisibility(View.GONE);
								tv_coupon.setText("没有可用优惠劵");
							}else {
								couponTypeEntitys.addAll(list3);
								adapter.notifyDataSetChanged();
							}
							
						}

					}else{
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				showProgress(false);
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void logOut(boolean isLogOut,MyCouponListResult result) {
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
				showProgress(false);
			}
		});
	}
    /**
     * 获取促销规则
     */
    private void fetchPromoteRule(){
    	
    }
    /**
     * 获取促销规则
     */
    private void fetchcouponRule(){
    	BaseRequestClient client=new BaseRequestClient(activity);
    	client.httpPostByJsonNew("PhoneGetCouponRoleList", baseApplication.getUserResult(),new CouponRoleListRequest(), CouponRoleListResult.class, new BaseRequestClient.RequestClientNewCallBack<CouponRoleListResult>() {

			@Override
			public void callBack(CouponRoleListResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						getlimitMoney(result.getCouponRules());
					}else {
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
			}

			@Override
			public void logOut(CouponRoleListResult result) {
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


	private TextView tv_coupon,send_time;

	private RelativeLayout address_progress;

	private RelativeLayout address;

	private SharedPreferences sp;
	/**
	 * 
	 * 判断是否有可用优惠劵
	 */
    private void getlimitMoney(ArrayList<CouponRule> couponRules){
    	for (CouponRule couponRule : couponRules) {
			if (couponRule.getMaxMoney()>=totalAmount&&couponRule.getMinMoney()<=totalAmount) {
				limitMoney=couponRule.getLimitMoney();
			}
		}
    	if (limitMoney>=0) {
    		 getUserCouponList();
    	}else {
			tv_coupon.setText("没有可用优惠劵");
			ll_coupon.setVisibility(View.GONE);
		}
	 }
    /**
     * 填充地址 
     * @param addressEntity
     */
    private void fillAddressContent(AddressEntity addressEntity){
        ((TextView)confirm_order_address.findViewById(R.id.confirm_order_address_name)).setText(String.format("%s  %s",addressEntity.getConsignee(),addressEntity.getMobile()));
        ((TextView)confirm_order_address.findViewById(R.id.confirm_order_address_detail)).setText(addressEntity.getAddress());
    }

}
