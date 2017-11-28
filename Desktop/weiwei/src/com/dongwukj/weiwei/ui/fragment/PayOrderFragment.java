package com.dongwukj.weiwei.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.AlipayRequest;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.dongwukj.weiwei.idea.request.OrderStatisticRequest;
import com.dongwukj.weiwei.idea.request.PhoneDownPayRequest;
import com.dongwukj.weiwei.idea.request.PhoneReturnPayRequest;
import com.dongwukj.weiwei.idea.request.PhoneorderpayeditstateRequest;
import com.dongwukj.weiwei.idea.request.PhonepayRequest;
import com.dongwukj.weiwei.idea.request.PhonepayResult;
import com.dongwukj.weiwei.idea.result.AlipayResult;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.PaymentEntity;
import com.dongwukj.weiwei.idea.result.PhoneDownPayResult;
import com.dongwukj.weiwei.idea.result.PhonecommitorderResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.idea.result.WeixinResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.BaseRequestClient.RequestClientNewCallBack;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.net.utils.DESUtils;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity.OrderCanclePay;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.activity.PayPasswordActivity;
import com.dongwukj.weiwei.ui.activity.PaymentActivity;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;

import de.greenrobot.event.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalDb;

/**
 * Created by sunjaly on 15/4/16.
 */
public class PayOrderFragment extends AbstractHeaderFragment {
	private PaymentListAdapter paymentListAdapter;
    private PhonecommitorderResult phonecommitorderResult;
    private int[] images={R.drawable.weiwei_pay_yue,R.drawable.weiwei_pay_zhifubao,R.drawable.weiwei_pay_weixin};

    private TextView pay_order_order_state,pay_order_order_no,pay_order_order_time,pay_order_order_amount,pay_order_balance,pay_order_pay_type,pay_order_surplus_money;
    private Button pay_order_submit;
	 private ArrayList<PaymentEntity> paymentEntityArrayList;

	private View view;
	private Map<Integer,Boolean> checkList;

	private LinearLayout ll_yue,ll_select_pay_style;


	private TextView pay_style;


	//private List<PaymentEntity> list;


	private float yue;


	private boolean isFromOrder;
	private boolean IsUnline;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pay_order_layout,container,false);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("请稍后，正在准备支付...");
        progressDialog.setCancelable(false);
        IsUnline= getActivity().getIntent().getBooleanExtra("IsUnline", false);
        return view;
    }

    @Override
    protected String setTitle() {
        return "支付";
    }
    
    @Override
    protected void findView(View v) {
    	DataBase db = LiteOrm.newCascadeInstance(activity, BaseApplication.DB_NAME);
    	phonecommitorderResult=(PhonecommitorderResult)activity.getIntent().getSerializableExtra("result");
        yue = activity.getIntent().getFloatExtra("yue", 0);
        isFromOrder = activity.getIntent().getBooleanExtra("isFromOrder", false);
        pay_order_order_state=(TextView)v.findViewById(R.id.pay_order_order_state);
        pay_order_order_no=(TextView)v.findViewById(R.id.pay_order_order_no);
        pay_style = (TextView)v.findViewById(R.id.pay_style);
        pay_order_order_time=(TextView)v.findViewById(R.id.pay_order_order_time);
        pay_order_order_amount=(TextView)v.findViewById(R.id.pay_order_order_amount);
        pay_order_surplus_money=(TextView)v.findViewById(R.id.pay_order_surplus_money);
        pay_order_pay_type=(TextView)v.findViewById(R.id.pay_order_pay_type);
        view = v.findViewById(R.id.view);
        ll_yue = (LinearLayout) v.findViewById(R.id.ll_yue);
        ll_select_pay_style = (LinearLayout) v.findViewById(R.id.ll_select_pay_style);
       pay_order_submit=(Button)v.findViewById(R.id.pay_order_submit);
        payment_list_view = (ListView)v.findViewById(R.id.payment_list_view);
        paymentEntityArrayList=new ArrayList<PaymentEntity>();
        pay_order_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });
         /*
         * 判断 是否限制支付
         */
       
       HomeHeaderActivity activity1=(HomeHeaderActivity)getActivity();
		activity1.setOrderCanclePay(new OrderCanclePay() {
			@Override
			public void canclePay() {
				showDialogs();
			}

			
		});
		   payment_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                for(Integer index:checkList.keySet()){
	                    checkList.put(index,false);
	                }
	                checkList.put(position,true);
	                paymentListAdapter.notifyDataSetChanged();
	                for(int i=0;i<paymentEntityArrayList.size();i++){
	                    if(checkList.get(i)){
	                        PaymentEntity paymentEntity=paymentEntityArrayList.get(i);
	                        phonecommitorderResult.setPayKey(paymentEntity.getKey());
	                		phonecommitorderResult.setPayMid(paymentEntity.getMid());
	                				pay_order_pay_type.setText(paymentEntity.getKey());
	                				if (paymentEntity.getMid().equals("1")) {
	                					  pay_order_order_amount.setText(String.format("￥%.2f",phonecommitorderResult.getPaydiscountamount()));
									}else {
										  pay_order_order_amount.setText(String.format("￥%.2f",phonecommitorderResult.getOrderAmount()));
										
									}
	                				changeButton();
	                        break;

	                    }
	                }
	            }
	        });
		 if (IsUnline) {
			 	PhoneDownPay(getActivity().getIntent().getStringExtra("OSN"));
			 //PhoneDownPay(getActivity().getIntent().getStringExtra("00000200000300003455"));
		}else {
			fillContent();
			changeButton();
			getStatistics();
		}
	}
    /**
     * 初始化线下支付
     * @param osn 
     */
	 private void PhoneDownPay(String osn) {
		 PhoneDownPayRequest request=new PhoneDownPayRequest();
		 request.setOsn(osn);
		 BaseRequestClient client=new BaseRequestClient(activity);
		 client.httpPostByJsonNew("PhoneDownPay", baseApplication.getUserResult(), request, PhoneDownPayResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneDownPayResult>() {

			@Override
			public void callBack(PhoneDownPayResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						phonecommitorderResult=new PhonecommitorderResult();
						phonecommitorderResult.setContentId(result.getOid()+"");
						phonecommitorderResult.setOrderAmount(result.getOrderamount());
						phonecommitorderResult.setSurplusMoney(result.getUsermoney());
						phonecommitorderResult.setOrderTime(result.getAddtime());
						phonecommitorderResult.setOsn(result.getOsn());
						phonecommitorderResult.setPayKey("余额支付");
						phonecommitorderResult.setPayMid("1");
						phonecommitorderResult.setPaydiscountamount(result.getDiscountafteramount());
						yue=result.getUsermoney();
						fillContent();
						 changeButton();
						getStatistics();
					}else {
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
				else {
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void logOut(PhoneDownPayResult result) {
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
				progressDialog.dismiss();
			}
		});
	}

	protected void showDialogs(){
	    	final Dialog dialog = new Dialog(activity, R.style.Dialog);
			LinearLayout view = (LinearLayout) View.inflate(activity, R.layout.is_delete_address_dialog, null);
			dialog.setContentView(view);
			dialog.setCancelable(false); //为false 按返回键不能 dismiss Dialog
			
			TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
			tv_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View paramView) {
					cancleOrder();
					dialog.dismiss();
					//activity.finish();
				}
			});
			TextView title = (TextView) view.findViewById(R.id.title);
			title.setText("是否取消此订单？");
			
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
    @Override
    protected void setTitleAndLeftButton() {
    	 View view1=activity.findViewById(R.id.list_header_title);
         if(view1!=null){
             ((TextView)view1).setText("支付");
         }
         View view2=activity.findViewById(R.id.ll_left);
         
         
         if(view2!=null){
             ((LinearLayout)view2).setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                	// cancleOrder();
                	// activity.finish();
                	 showDialogs();
                 }
             });
         }
    }
    /**
     * 获取支付方式
     */
    private void getStatistics() {
		BaseRequestClient client = new BaseRequestClient(activity);
		UserResult userResult = baseApplication.getUserResult();
		OrderStatisticRequest Request = new OrderStatisticRequest();
		progressDialog.setMessage("获取订单信息中。。。");
		progressDialog.show();
		client.httpPostByJson("Phonewallet", userResult, Request, PaymentEntitysResult.class, new BaseRequestClient.RequestClientCallBack<PaymentEntitysResult>() {

			@Override
			public void callBack(PaymentEntitysResult result) {
				if(result!=null){
					if(result.getCode()==BaseResult.CodeState.Success.getCode()){
						 if (IsUnline) {
							 paymentEntityArrayList=new ArrayList<PaymentEntity>();
							 
							paymentEntityArrayList.add(result.getPayPluginList().get(0));
							}else {
								 paymentEntityArrayList=result.getPayPluginList();
							}
						
						 checkList=new HashMap<Integer, Boolean>();
					        for(int i=0;i<paymentEntityArrayList.size();i++){
					            checkList.put(i,false);
					        }
					        checkList.put(0,true);
					        paymentListAdapter=new PaymentListAdapter();
					       
					        payment_list_view.setAdapter(paymentListAdapter);
					}else{
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				progressDialog.dismiss();
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void logOut(boolean isLogOut,PaymentEntitysResult result) {FinalDb finalDb=FinalDb.create(activity);
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
			progressDialog.dismiss();}

			
		});
		
		
	};

    private void cancleOrder() {
		PhoneReturnPayRequest request=new PhoneReturnPayRequest();
		if (IsUnline) {
			request.setIsoffline(1);
		}else {
			request.setIsoffline(0);
		}
		request.setOrderId(Integer.parseInt(phonecommitorderResult.getContentId()));
		BaseRequestClient client=new BaseRequestClient(activity);
		
		progressDialog.setMessage("订单取消中。。。");
		progressDialog.show();
		client.httpPostByJsonNew("PhoneReturnPay", baseApplication.getUserResult(), request, BaseResult.class, new BaseRequestClient.RequestClientNewCallBack<BaseResult>() {

			@Override
			public void callBack(BaseResult result) {
				progressDialog.dismiss();
				activity.finish();
				
			}

			@Override
			public void loading(long count, long current) {
				
			}

			@Override
			public void logOut(BaseResult result) {
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
	private void changeButton() {
		 if (phonecommitorderResult.getPayMid().equals("1")) {
		SharedPreferences sp = activity.getSharedPreferences("PayFail", Activity.MODE_PRIVATE);
        boolean isConFine = sp.getBoolean("isConFine", false);   
        edit = sp.edit();
        if(isConFine){
        	long startTime = sp.getLong("startTime", 0);
        	long endTime = System.currentTimeMillis();
        	long diff = endTime - startTime;
        	if(diff<600000){ //时间差小于10分钟,"去支付"按钮不可点击
        		pay_order_submit.setEnabled(false);
        		pay_order_submit.setBackgroundResource(R.drawable.weiwei_enabled_false);
        		Toast.makeText(activity, "余额支付限制中,请稍后!", Toast.LENGTH_SHORT).show();
        	}else{//10分钟以后,只有一次 支付密码输入错误的机会,如果再次输入错误,将会再次被锁定10分钟
        		pay_order_submit.setEnabled(true);
        		edit.putBoolean("isConFine", false);
        		pay_order_submit.setBackgroundResource(R.drawable.weiwei_yellow_button);
        	}
        	if(diff>86400000){//一天以后重新获得5次机会        		
        		edit.putInt("count", 0); //如果超过了一天,将统计的支付密码输入错误次数置0
        	}
        	edit.commit();
        }
	}else {
		pay_order_submit.setEnabled(true);
		pay_order_submit.setBackgroundResource(R.drawable.weiwei_yellow_button);
	}
	}

    private void pay(){
    	if (phonecommitorderResult.getSurplusMoney()<=0) {
    		Toast.makeText(activity, "当前订单只支持余额支付", Toast.LENGTH_SHORT).show();
    		 payYuE();
		}else {
			if (baseApplication.getUserResult().getIsByMoney()!=1&&"1".equals(phonecommitorderResult.getPayMid())) {

				//Toast.makeText(activity, "您还没有设置支付密码", Toast.LENGTH_SHORT).show();

				final Dialog dialog=new Dialog(activity, R.style.Dialog);
				View view = View.inflate(activity, R.layout.is_set_paypassowrd, null);
				dialog.setContentView(view);
				dialog.setCancelable(false);
				WindowManager m = activity.getWindowManager();
		        Window dialogWindow = dialog.getWindow();
		        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		        p.height = (int) (d.getHeight() * 0.2); // 高度设置为屏幕的0.6
		        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
		        dialogWindow.setAttributes(p);
				dialog.show();
				 TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
				 TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
				 tv_cancle.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				 tv_ok.setOnClickListener(new OnClickListener() {
					 public void onClick(View v) {
						 Intent intent=new Intent(activity, PayPasswordActivity.class);
						 startActivityForResult(intent, 200);
						 dialog.dismiss();
					 }
				 });
				 return;
			
			}
			if("1".equals(phonecommitorderResult.getPayMid())){
	        	 
	        	 if (Float.parseFloat(pay_order_order_amount.getText().toString().trim().split("￥")[1])>Float.parseFloat(pay_order_surplus_money.getText().toString().trim().split("￥")[1])) {
	        		 Toast.makeText(activity,"余额不足,请选择别的支付方式",Toast.LENGTH_SHORT).show();
	             	return;
				}
	        	 
	        	 payYuE();
	        }else if ("2".equals(phonecommitorderResult.getPayMid())) {
	        	progressDialog.show();
	        	 payZhifubao();
			}
	        else{
	        	progressDialog.show();
	       	 payWeixin();
	        }
		}
        
    }
    @Override
    public void onResume() {
    	if (progressDialog!=null&&progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
    	super.onResume();
    }

    private void payWeixin() {
    	BaseRequestClient client=new BaseRequestClient(activity);
		AlipayRequest request=new AlipayRequest();
		request.setLogType(10);
		
		request.setOrderId(Integer.parseInt(phonecommitorderResult.getContentId()));
		client.httpPostByJsonNew("Phonepay", baseApplication.getUserResult(), request, WeixinResult.class, new RequestClientNewCallBack<WeixinResult>() {

			@Override
			public void callBack(WeixinResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
//						Intent intent=new Intent(activity, PaymentActivity.class);
//						intent.putExtra("type", 10);
//						intent.putExtra("orderInfo", result);
//						startActivity(intent);
					//	osn=result.getOsn();
						type=1;
						Intent intent1 = new Intent();
						String packageName = activity.getPackageName();
						ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
						intent1.setComponent(componentName);
						intent1.putExtra("type", 10);
						intent1.putExtra("orderInfo", result);
						
						//intent1.putExtra(PaymentActivity.EXTRA_CHARGE, data);
						startActivityForResult(intent1, 500);
					}else {
						if (progressDialog!=null&&progressDialog.isShowing()) {
							progressDialog.dismiss();
						}
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					if (progressDialog!=null&&progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void loading(long count, long current) {
				
			}

			@Override
			public void logOut(WeixinResult result) {
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
    
	private void payZhifubao() {
		BaseRequestClient client=new BaseRequestClient(activity);
		AlipayRequest request=new AlipayRequest();
		request.setLogType(8);
		request.setOrderId(Integer.parseInt(phonecommitorderResult.getContentId()));
		client.httpPostByJsonNew("Phonepay", baseApplication.getUserResult(), request, AlipayResult.class, new RequestClientNewCallBack<AlipayResult>() {

			@Override
			public void callBack(AlipayResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
					//	osn=result.getOsn();
						type=0;
						Intent intent=new Intent(activity, PaymentActivity.class);
						intent.putExtra("type", 8);
						intent.putExtra("orderInfo", result.getOrderInfotext()+"&sign="+"\""+result.getMysign()+"\""+"&sign_type=\"RSA\"");
						startActivityForResult(intent, 500);
					}else {
						if (progressDialog!=null&&progressDialog.isShowing()) {
							progressDialog.dismiss();
						}
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					if (progressDialog!=null&&progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				/*if (progressDialog!=null&&progressDialog.isShowing()) {
					progressDialog.dismiss();
				}*/
			}

			@Override
			public void loading(long count, long current) {
				
			}

			@Override
			public void logOut(AlipayResult result) {
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

	private void payYuE(){
		if(baseApplication==null || baseApplication.getUserResult()==null){
			//todo:修改余额支付bug
			Toast.makeText(activity,"您的登录状态异常，请重新登录后支付",Toast.LENGTH_SHORT).show();
			return;
		}
    	if (baseApplication.getUserResult().getIsByMoney()==1) {
    		 final Dialog dialog=new Dialog(activity,R.style.Dialog);
    	        dialog.setContentView(R.layout.input_pay_password_layout);
    	        final EditText input=(EditText)dialog.findViewById(R.id.edit_dialog_input);
    	        final TextView tv_1=(TextView)dialog.findViewById(R.id.pw1);
    	        final TextView tv_2=(TextView)dialog.findViewById(R.id.pw2);
    	        final TextView tv_3=(TextView)dialog.findViewById(R.id.pw3);
    	        final TextView tv_4=(TextView)dialog.findViewById(R.id.pw4);
    	        final TextView tv_5=(TextView)dialog.findViewById(R.id.pw5);
    	        final TextView tv_6=(TextView)dialog.findViewById(R.id.pw6);
    	        final Button submitButton=(Button)dialog.findViewById(R.id.edit_dialog_cancel);
    	        input.addTextChangedListener(new TextWatcher() {
					@Override
					public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

					}

					@Override
					public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						submitButton.setEnabled(false);
						switch (s.length()) {
						case 0:
							tv_1.setText("");
							break;
						case 1:
							tv_1.setText(s.charAt(0)+"");
							tv_2.setText("");
							break;
						case 2:
							tv_2.setText(s.charAt(1)+"");
							tv_3.setText("");
							break;
						case 3:
							tv_3.setText(s.charAt(2)+"");
							tv_4.setText("");
							break;
						case 4:
							tv_4.setText(s.charAt(3)+"");
							tv_5.setText("");
							break;
						case 5:
							tv_5.setText(s.charAt(4)+"");
							tv_6.setText("");
							break;
						case 6:
							tv_6.setText(s.charAt(5)+"");
							submitButton.setEnabled(true);
							break;

						default:
							break;
						}
					
					}
				});
				submitButton.setEnabled(false);

				submitButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						PhonepayRequest request = new PhonepayRequest();
						request.setLogType(5);
						request.setOrderId(phonecommitorderResult.getContentId()+"");
						//String password = input.getText().toString();
						String password = tv_1.getText().toString().trim()+tv_2.getText().toString().trim()+tv_3.getText().toString().trim()+tv_4.getText().toString().trim()+tv_5.getText().toString().trim()+tv_6.getText().toString().trim();
						try {
							request.setPaymentPassword(DESUtils.encrypt(password, DESUtils.DES_KEY_STRING));
							payByYueRequest(request);
						} catch (Exception e) {
							Log.e("error", "error", e);
						}


					}
				});
    	        dialog.findViewById(R.id.edit_dialog_ok).setOnClickListener(new View.OnClickListener() {
    	            @Override
    	            public void onClick(View v) {
    	                dialog.dismiss();
    	            }
    	        });
    	        dialog.setCancelable(false);
    	      WindowManager m = activity.getWindowManager();
    	        Window dialogWindow = dialog.getWindow();
    	        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
    	        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
    	        p.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的0.6
    	        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
    	        dialogWindow.setAttributes(p);
    	        dialog.show();
		}else {
			Toast.makeText(activity, "您还没有设置支付密码", Toast.LENGTH_SHORT).show();

			final Dialog dialog=new Dialog(activity, R.style.Dialog);
			View view = View.inflate(activity, R.layout.is_set_paypassowrd, null);
			dialog.setContentView(view);
			dialog.setCancelable(false);
			WindowManager m = activity.getWindowManager();
	        Window dialogWindow = dialog.getWindow();
	        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
	        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
	        p.height = (int) (d.getHeight() * 0.2); // 高度设置为屏幕的0.6
	        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
	        dialogWindow.setAttributes(p);
			dialog.show();
			 TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
			 TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
			 tv_cancle.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			 tv_ok.setOnClickListener(new OnClickListener() {
				 public void onClick(View v) {
					 Intent intent=new Intent(activity, PayPasswordActivity.class);
					 startActivityForResult(intent, 200);
					 dialog.dismiss();
				 }
			 });
		
		}
       
    }
    
    
    private void payByYueRequest(final PhonepayRequest request){
    	if (IsUnline) {
			request.setIsoffline(1);
		}else {
			request.setIsoffline(0);
		}
        MyWeiWeiRequestClient client=new MyWeiWeiRequestClient(activity,baseApplication);
        client.payOrderByYue(request, new MyWeiWeiRequestClient.PhonepayRequestClientCallback() {
            @Override
            protected void result(PhonepayResult result) {
            	if (getActivity()==null) {
					return;
				}
                super.result(result);
                edit.putInt("count", 0);
                pay_order_submit.setEnabled(true);
                edit.putBoolean("isConFine", false);
                updateDb();
                //支付成功, 跳转到支付成功的 PaySuccessFragment 界面
                openNewActivityWithHeader(HeaderActivityType.PaySuccess.ordinal(),true);
              //  activity.setResult(Activity.RESULT_OK);
              //  activity.finish();
            }
            
            @Override
            protected void resultComplete(PhonepayResult result) {
            	if (getActivity()==null) {
					return;
				}
                super.resultComplete(result);
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
            protected void resultFail(PhonepayResult result) {
            	if (getActivity()==null) {
					return;
				}
                if(result!=null&&result.getCode()==304){
                	Toast.makeText(activity, "余额不足请选择别的支付方式", Toast.LENGTH_SHORT).show();
                }else if (result!=null&&result.getCode()==302) {
    				SharedPreferences sp = activity.getSharedPreferences("PayFail", Activity.MODE_PRIVATE);
    				int count = sp.getInt("count", 0);
    				Editor edit = sp.edit(); 
    				count++;
					/*if(count==3){
    					//Toast.makeText(activity, "您已经3次密码输入错误,超过5次将被限10分钟", Toast.LENGTH_LONG).show();
    				}else if(count>=5){
    					//Toast.makeText(activity, "支付密码错误过多,10分钟后再试!", Toast.LENGTH_LONG).show();
    					//Date startTime = new Date(System.currentTimeMillis()); //限制支付开始时间
    					edit.putBoolean("isConFine", true);
    					edit.putLong("startTime", System.currentTimeMillis());
    					pay_order_submit.setEnabled(false);
    				}*/
    				
    		/*		if(count<=0){
    					count= 1;
        				edit.putInt("count", count);
    				}else{
    					if(count==3){
        					Toast.makeText(activity, "您已经3次密码输入错误,超过5次将被限10分钟", Toast.LENGTH_LONG).show();
        				}else if(count>=5){
        					Toast.makeText(activity, "支付密码错误过多,10分钟后再试!", Toast.LENGTH_LONG).show();
        					//Date startTime = new Date(System.currentTimeMillis()); //限制支付开始时间
        					edit.putBoolean("isConFine", true);
        					edit.putLong("startTime", System.currentTimeMillis());
        					pay_order_submit.setEnabled(false);
        					pay_order_submit.setBackgroundResource(R.drawable.weiwei_enabled_false);
        				}
    					edit.putInt("count", count);
    				}*/
    				edit.putInt("count", count);
    				edit.commit();
				}else if (result!=null&&result.getCode()==313) {
					edit.putBoolean("isConFine", true);
					edit.putLong("startTime", System.currentTimeMillis());
					pay_order_submit.setEnabled(false);
					pay_order_submit.setBackgroundResource(R.drawable.weiwei_enabled_false);
					edit.putInt("count", 5).commit();
				}
            }
        });
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
    public void onEventMainThread(String type){};
   private void openNewActivityWithHeader(int type,Boolean needLogin){
	   
		Intent intent=new Intent(activity,HomeHeaderActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("needLogin", needLogin);
        intent.putExtra("isFromOrder", isFromOrder);	
        startActivity(intent);
        activity.finish();
      //  startActivityForResult(intent, 300);
   
}
    
    private void updateDb() {
    	if (IsUnline) {
			return;
		}
	DataBase db=LiteOrm.newCascadeInstance(activity, baseApplication.DB_NAME);
	QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
	qb.where("userAccount=? and marketId=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId()});
	ArrayList<NewHomeEntity> arrayList = db.query(qb);
	for (NewHomeEntity newHomeEntity : arrayList) {
		if (newHomeEntity.isChecked()) {
			db.delete(newHomeEntity);
		}
	}
	ArrayList<NewHomeEntity> query = db.query(qb);
	if (query.size()>0) {
		for (NewHomeEntity newHomeEntity : query) {
			newHomeEntity.setChecked(true);
			db.update(newHomeEntity);
		}
	}
	EventBus.getDefault().post("login");
}
    
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode==100&&resultCode==Activity.RESULT_OK) {
    		PaymentEntity paymentEntity=(PaymentEntity) data.getSerializableExtra("paymentEntity");	
    		phonecommitorderResult.setPayKey(paymentEntity.getKey());
    		phonecommitorderResult.setPayMid(paymentEntity.getMid());
    				pay_order_pay_type.setText(paymentEntity.getKey());
    				changeButton();
			}else if (requestCode==200&&resultCode==Activity.RESULT_OK) {
				baseApplication.getUserResult().setIsByMoney(1);
				payYuE();
			}else if (requestCode==300&&resultCode==Activity.RESULT_OK) {
				activity.setResult(Activity.RESULT_OK);
				activity.finish();
			}else if (requestCode==500&&resultCode==-1&&data!=null) {
				if (progressDialog!=null&&progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				String extra = data.getStringExtra("pay_result");
				if (extra.equals("success")) {
					updateDb();
					Toast.makeText(activity, "success", 0).show();
					Phoneorderpayeditstate();
				}else if (extra.equals("cancel")) {
					//Toast.makeText(activity, "user_cancelled", 0).show();
				}else if (extra.equals("fail")) {
					if (progressDialog!=null&&progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					Toast.makeText(activity, "支付失败", 0).show();
				}
			}
    }
    private int type;


	private ProgressDialog progressDialog;


	private Editor edit;
	private ListView payment_list_view;
   // private String osn;
    private void Phoneorderpayeditstate() {
    	openNewActivityWithHeader(HeaderActivityType.PaySuccess.ordinal(),true);
    	PhoneorderpayeditstateRequest request=new PhoneorderpayeditstateRequest();
		request.setOrdertype(0);
		request.setType(type);
		request.setOsn(phonecommitorderResult.getOsn());
		BaseRequestClient client=new BaseRequestClient(activity);
		client.httpPostByJsonNew("Phoneorderpayeditstate", baseApplication.getUserResult(), request, BaseResult.class, new BaseRequestClient.RequestClientNewCallBack<BaseResult>() {

			@Override
			public void callBack(BaseResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
					    
					}else {
					//	Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					//Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void logOut(BaseResult result) {
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
    private class PaymentListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return paymentEntityArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return paymentEntityArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            if(convertView==null){
                convertView=View.inflate(activity,R.layout.payment_list_item,null);
                viewHolder=new ViewHolder();
                viewHolder.imageView=(ImageView)convertView.findViewById(R.id.payment_list_item_img);
                viewHolder.textView=(TextView)convertView.findViewById(R.id.payment_list_item_text);
                viewHolder.tv_money=(TextView)convertView.findViewById(R.id.tv_money);
                viewHolder.checkBox=(CheckBox)convertView.findViewById(R.id.payment_list_item_check);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder)convertView.getTag();
            }
            viewHolder.tv_money.setVisibility(View.VISIBLE);
            if (position!=0) {
            	 viewHolder. tv_money.setText(String.format("￥%.2f",phonecommitorderResult.getOrderAmount()));
			}else {
				 viewHolder. tv_money.setText(String.format("￥%.2f",phonecommitorderResult.getPaydiscountamount()));
			}
            viewHolder.textView.setText(paymentEntityArrayList.get(position).getKey());

            viewHolder.checkBox.setChecked(checkList.get(position));
            viewHolder.imageView.setImageResource(images[position]);
            /*viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                       for(Integer index:checkList.keySet()){
                           checkList.put(index,false);
                       }
                    checkList.put(position,isChecked);
                    notifyDataSetChanged();

                }
            });*/

            return convertView;
        }

        private class ViewHolder{
            public ImageView imageView;
            public TextView textView;
            public TextView tv_money;
            public CheckBox checkBox;
        }
    }
    
	/**
     * 填充数据
     */
    private void fillContent(){
        pay_order_order_no.setText(phonecommitorderResult.getOsn());
        Date date=new Date();
        try {
            long time=Long.parseLong(phonecommitorderResult.getOrderTime());
            date=new Date(time);

        }catch (Exception e){
        	
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        pay_order_order_time.setText(simpleDateFormat.format(date));
       pay_order_order_amount.setText(String.format("￥%.2f",phonecommitorderResult.getPaydiscountamount()));
       pay_order_surplus_money.setText(String.format("￥%.2f", yue));
      
    }

}
