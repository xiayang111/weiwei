package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.tsz.afinal.FinalDb;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.RechargeGridViewAdapter;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.PhoneprepaidruleResult;
import com.dongwukj.weiwei.idea.result.PrepaidRuleEntity;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.widget.MyGridView;

/**
 * Created by pc on 2015/3/17.
 */
public class RechargeFragment extends AbstractHeaderFragment implements OnClickListener {
	private String item;
    //private BannerFragment bannerFragment;
    private GridView rechargeGridView;
 
   private Map<Float, Float> map=new HashMap<Float, Float>();
    private String[] rechargeList;
    private RechargeGridViewAdapter adapter;
	private RadioButton rb_net_recharge;
	private RadioButton rb_cart_recharge;
	private FrameLayout fl_recharge;
	private LinearLayout ll_net_recharge;
	private int height;
	private ScrollView sc;
	private TextView tv_present;
	private float money;
	private int Prid;
	private float total;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.recharge_center_activity, container, false);
        sp = activity.getSharedPreferences("config", 0);
        return view;
    }
    private Handler mhandler=new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		sc.fullScroll(ScrollView.FOCUS_DOWN);
    		 final Dialog dialog=new Dialog(activity, R.style.Dialog);
    		 dialog.setCancelable(false);
 			View view = View.inflate(activity, R.layout.recharge_guide, null);
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
 					 bt_recharge.setVisibility(View.VISIBLE);
 					 sp.edit().putBoolean("chongzhi", false).commit();
 					sc.scrollTo(0, 0);
 					dialog.dismiss();
 				}
 			});
    	};
    };
    @Override
    protected String setTitle() {
        return getResources().getString(R.string.recharge_center_text);
    }
 
    @Override
    protected void findView(View v) {
    	
    	rb_net_recharge = (RadioButton) v.findViewById(R.id.rb_net_recharge);
    	rb_net_recharge.setOnClickListener(this);
    	rb_cart_recharge = (RadioButton) v.findViewById(R.id.rb_cart_recharge);
    	rb_cart_recharge.setOnClickListener(this);
    	tv_present = (TextView) v.findViewById(R.id.tv_present);
    	fl_recharge = (FrameLayout) v.findViewById(R.id.fl_recharge);
    	ll_net_recharge = (LinearLayout) v.findViewById(R.id.ll_net_recharge);
    	
       // bannerFragment = (BannerFragment) activity.getSupportFragmentManager().findFragmentById(R.id.banner_view);
        rechargeGridView = (MyGridView) v.findViewById(R.id.recharge_gridview);
       
        sc = (ScrollView) v.findViewById(R.id.sc);
       rechargeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            

			@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter != null) {
                	for (PrepaidRuleEntity prepaidRuleEntity : list) {
						if (list.get(position)==prepaidRuleEntity) {
							prepaidRuleEntity.setIschecked(true);
						}else {
							prepaidRuleEntity.setIschecked(false);
						}
					}
                    adapter.changeState(position);
                    
                    item = list.get(position).getMinMoney()+"元";
                     Prid = list.get(position).getPrid();
                    money = Float.parseFloat(item.split("元")[0]);
                    total=map.get(money);
                    if (total>0) {
                    	 tv_present.setText("充"+(int)money+"送"+(int)total);
					}else {
						 tv_present.setText("");
					}
                   
                 }
            }
        });
        et_tel = (TextView) v.findViewById(R.id.et_tel);
        String tel = baseApplication.getUserResult().getTel();
        //et_tel.setHint(tel.replace(tel.subSequence(3, 7), " "+tel.subSequence(3, 7)+" ")+"  绑定手机号");
        et_tel.setHint(tel.substring(0, 3)+" "+tel.substring(3, 7)+" "+tel.substring(7, 11)+"  绑定手机号");
        bt_recharge = (Button) v.findViewById(R.id.bt_recharge);
        bt_recharge.setOnClickListener(this);
        getPhoneprepaidrule();
       /* if (sp.getBoolean("chongzhi", true)) {
        	 showloginguide();
		}*/
       
    }
    private ArrayList<PrepaidRuleEntity> list ;
private PhoneprepaidruleResult results;
private TextView et_tel;
private Button bt_recharge;
private SharedPreferences sp;
	private void getPhoneprepaidrule() {
		BaseRequestClient client=new BaseRequestClient(activity);
		progressDialog.setMessage("加载中...");
		showProgress(true);
		client.httpPostByJsonNew("Phoneprepaidrule", baseApplication.getUserResult(),new BaseRequest(), PhoneprepaidruleResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneprepaidruleResult>() {

			@Override
			public void callBack(PhoneprepaidruleResult result) {
				if (getActivity()==null) {
					return;
				}
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						results=result;
						 list = result.getPrepaidRuleList();
						if (list.size()!=0) {
							rechargeList=new String[list.size()];
							for (int i = 0; i < rechargeList.length; i++) {
								rechargeList[i]=list.get(i).getMinMoney()+"元";
								map.put(list.get(i).getMinMoney(), list.get(i).getSendMoney());
							}
							adapter = new RechargeGridViewAdapter(list, activity);
						     rechargeGridView.setAdapter(adapter);
						    // tv_present.setText("充"+(int)list.get(0).getMinMoney()+"送"+(int)list.get(0).getSendMoney());
						}
						
					}else {
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				showProgress(false);
			}

			@Override
			public void loading(long count, long current) {
				
				
			}

			@Override
			public void logOut(PhoneprepaidruleResult result) {
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_recharge:
			if (TextUtils.isEmpty(item)||item.equals("")) {
				Toast.makeText(activity, "请选择金额", Toast.LENGTH_SHORT).show();
				return;
			}
		/*	if (TextUtils.isEmpty(et_tel.getText().toString().trim())) {
				phone=baseApplication.getUserResult().getTel();
			}else {
				 Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
                 Matcher matcher = p.matcher(et_tel.getText().toString().trim());
                 boolean matches = matcher.matches();
                 if (matches) {
					phone=et_tel.getText().toString().trim();
				}else {
					Toast.makeText(activity, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
					return;
				}
			}*/
			openNewActivityWithHeader(HeaderActivityType.RechargeAffirmFragment.ordinal(), true);
			break;
		case R.id.rb_net_recharge:
			
			fl_recharge.setVisibility(View.GONE);   //卡号充值隐藏
			ll_net_recharge.setVisibility(View.VISIBLE); //在线支付显现
			break;
		case R.id.rb_cart_recharge:
			
			ll_net_recharge.setVisibility(View.GONE);	//在线支付隐藏
			fl_recharge.setVisibility(View.VISIBLE);
			FragmentManager manager = activity.getSupportFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.replace(R.id.fl_recharge, new RechargeCardFragment());
			transaction.commit();
			break;
		default:
			break;
		}
		
	}
	//private String phone;
	private void openNewActivityWithHeader(int type,Boolean needLogin){
		Intent intent=new Intent(activity,HomeHeaderActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("needLogin", needLogin);
        intent.putExtra("money", money);
        intent.putExtra("total", total);
        intent.putExtra("Prid", Prid);
        intent.putExtra("phone", baseApplication.getUserResult().getTel());
        intent.putExtra("paymentEntityArrayList", results.getPayPluginList());
        
        
        startActivity(intent);
	}
}
