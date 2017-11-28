package com.dongwukj.weiwei.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.jpush.android.api.JPushInterface;

import com.baidu.location.LLSInterface;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.ui.fragment.*;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by pc on 2015/3/17.
 */
public class HomeHeaderActivity extends BaseActivity {

	public ImageButton leftButton;
	private int type;
	private boolean isNeedRefresh=false;
    private View list_header;
    private boolean isPaysucceed=false;
    public RelativeLayout rl_right;
    public ImageView img_cart,ima_cart_new;
    
    @Override
    protected void findViewById() {
    	ll_left = (LinearLayout) findViewById(R.id.ll_left);
    	rl_right = (RelativeLayout) findViewById(R.id.rl_right);
    	img_cart = (ImageView) findViewById(R.id.img_cart);
    	ima_cart_new = (ImageView) findViewById(R.id.ima_cart_new);
    	
        list_header=findViewById(R.id.list_header);
        Boolean hasHeader=getIntent().getBooleanExtra("hasHeader", true);
        if(!hasHeader){
            list_header.setVisibility(View.GONE);
        }

    }
  private void openNewActivityWithHeader(int type,Boolean needLogin){
		Intent intent=new Intent(this,HomeHeaderActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("needLogin", needLogin);
        startActivity(intent);
    }
    @Override
    public void finish() {
    	if (isNeedRefresh) {
    		setResult(Activity.RESULT_OK);
			isNeedRefresh=false;
		}else if (isPaysucceed) {
			setResult(Activity.RESULT_OK);
			openNewActivityWithHeader(HeaderActivityType.OrderList.ordinal(),true);
		}
        super.finish();
    }
    public void setIsNeedRefresh(boolean isNeedRefresh){
    	this.isNeedRefresh=isNeedRefresh;
    }
    public void setIsPaysucceed(boolean isPaysucceed){
    	this.isPaysucceed=isPaysucceed;
    }
    @Override
    protected void initView() {
    	
        setContentView(R.layout.home_header_activity);
        type = getIntent().getIntExtra("type",-1);
        boolean needLogin=getIntent().getBooleanExtra("needLogin", false);
        if(baseApplication.getUserResult()!=null||!needLogin){
        	updateUI();
        }else {
        	Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
			startActivityForResult(intent, 100);
		}

    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==100&&resultCode==200) {
			 updateUI();
		}else if(requestCode==100 && resultCode!=200) {
			finish();
		}else{
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		

	}
	@Override
	protected void onPause() {
		super.onPause();
	

	}
    private void updateUI(){
//    	if(intent==null) return;
//    	int type=intent.getIntExtra("type",-1);
        if(type==HeaderActivityType.Recharge.ordinal()){
            getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new RechargeFragment()).commitAllowingStateLoss();

        }else if(type==HeaderActivityType.OrderList.ordinal()){
            getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new OrderListFragment(),"OrderListFragment").commitAllowingStateLoss();

        }else if(type==HeaderActivityType.Attention.ordinal()){
	            getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new AttentionFragment()).commitAllowingStateLoss();

        }else if(type==HeaderActivityType.Present.ordinal()){
            getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new PresentFragment()).commitAllowingStateLoss();

        }else if(type==HeaderActivityType.Purchase.ordinal()){
            getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new PurchaseFragment()).commitAllowingStateLoss();

        }else if(type==HeaderActivityType.Recommend.ordinal()){
            getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new RecommendFragment()).commitAllowingStateLoss();

        }else if (type==HeaderActivityType.Purse.ordinal()) {
        	 getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new PurseFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.CategoryDetails.ordinal()){
            getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new CategoryDetailListFragment()).commitAllowingStateLoss();

        }else if (type==HeaderActivityType.ComboDetail.ordinal()) {
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new ComboDetailFragment()).commitAllowingStateLoss();
		}else if (type==HeaderActivityType.Evaluate.ordinal()) {
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new EvaluateFragment()).commitAllowingStateLoss();
		}else if (type==HeaderActivityType.Address.ordinal()) {
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new AddressFragment()).commitAllowingStateLoss();
		}else if (type==HeaderActivityType.Footprint.ordinal()) {
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new FootprintFragment()).commitAllowingStateLoss();
		}else if (type==HeaderActivityType.NewAddress.ordinal()) {
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new NewAddressFragment()).commitAllowingStateLoss();
		}else if (type==HeaderActivityType.Combo.ordinal()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new ComboFragment()).commitAllowingStateLoss();
        }else if (type==HeaderActivityType.OrderListDetail.ordinal()) {
        	 getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new OrderListDetailFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.MySetting.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view, new SettingFragment()).commitAllowingStateLoss();
		}else if (type==HeaderActivityType.ConfirmOrder.ordinal()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new ConfirmOrderFragment()).commitAllowingStateLoss();
        }else if (type==HeaderActivityType.PaymentList.ordinal()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new PaymentListFragment()).commitAllowingStateLoss();
        }else if(type==HeaderActivityType.RecommendJoin.ordinal()){
        	getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new RecommendedJoinFragment()).commitAllowingStateLoss();
        }else if (type==HeaderActivityType.OrderEvaluate.ordinal()) {
        	getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new OrderEvaluateFragment()).commitAllowingStateLoss();
		}else if (type==HeaderActivityType.GiveScore.ordinal()) {
        	getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new ScoreFragment()).commitAllowingStateLoss();
		}else if (type==HeaderActivityType.UserLevel.ordinal()) {
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new UserLevelFragment()).commitAllowingStateLoss();
		}else if (type==HeaderActivityType.PayOrder.ordinal()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new PayOrderFragment()).commitAllowingStateLoss();
        }
//		else if (type==HeaderActivityType.findpassword.ordinal()) {
//        	  getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new FindPasswordFragment()).commitAllowingStateLoss();
//		}
        else if (type==HeaderActivityType.ProductDetail.ordinal()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new NewProductDetailFragment()).commitAllowingStateLoss();
        }else if (type==HeaderActivityType.MessageCenter.ordinal()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new MessageCenterFragment()).commitAllowingStateLoss();
        }else if(type==HeaderActivityType.PaySuccess.ordinal()){
        	getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new PaySuccessFragment()).commitAllowingStateLoss();
        }else if (type==HeaderActivityType.ResetPayPwdQuestion.ordinal()) {
        	getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new ResetPayPwdQuestion()).commitAllowingStateLoss();
		}else if (type==HeaderActivityType.ResetPayPwdFragment.ordinal()) {
        	getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new ResetPayPwdFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.CouPon.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new CouponFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.Loucation.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new LoucationFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.AboutUs.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new AboutUsFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.ClassifySecondFragment.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new ClassifySecondFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.AddShopCart.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new ShoppingCarFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.BannerWebViewFragment.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new BannerWebViewFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.SecurityAccountFragment.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new SecurityAccountFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.ModifyLoginPasswordFragment.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new ModifyLoginPasswordFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.RaffleWebViewFragment.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new RaffleWebViewFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.RechargeCardFragment.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new RechargeCardFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.UserAgreement.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new UserAgreementFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.RechargeAffirmFragment.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new RechargeAffirmFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.SourceFragment.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new SourceFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.SourceProductFragment.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new SourceProductFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.SourceWeiweiFragment.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new SourceWeiweiFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.SourceSupplierFragment.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new SourceSupplierFragment()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.SourceOrderListFragment.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new SourceOrderListFragment()).commitAllowingStateLoss();
		}
		else if(type==HeaderActivityType.AccountBalance.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new AccountBalanceFragment()).commitAllowingStateLoss();
		}
		else if(type==HeaderActivityType.AddDistrict.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new AddDistrict()).commitAllowingStateLoss();
		}else if(type==HeaderActivityType.SendTypeFragment.ordinal()){
			getSupportFragmentManager().beginTransaction().replace(R.id.home_header_view,new SendTypeFragment()).commitAllowingStateLoss();
		}
        
        
    }
 
    public void setOrderCanclePay(OrderCanclePay orderCanclePay){
    	this.orderCanclePay=orderCanclePay;
    }
    private OrderCanclePay orderCanclePay;
    private CancleAddAddress cancleAddAddress;
    private PaySucceedBack payBack;
    
	public PaySucceedBack getPayBack() {
		return payBack;
	}
	public void setPayBack(PaySucceedBack payBack) {
		this.payBack = payBack;
	}
	public LinearLayout ll_left;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode==KeyEvent.KEYCODE_BACK) {
			if (orderCanclePay!=null) {
				orderCanclePay.canclePay();
			}else if (cancleAddAddress!=null) {
				cancleAddAddress.cancleAddAddress();
			}else if (payBack!=null) {
				payBack.paySucceedBack();
			}
		}
    	return super.onKeyDown(keyCode, event);
    }
    public interface OrderCanclePay{
        public void canclePay();
    }
    
    public CancleAddAddress getCancleAddAddress() {
		return cancleAddAddress;
	}
	public void setCancleAddAddress(CancleAddAddress cancleAddAddress) {
		this.cancleAddAddress = cancleAddAddress;
	}
	public interface CancleAddAddress{
        public void cancleAddAddress();
    }
	
	public interface PaySucceedBack{
        public void paySucceedBack();
    }
}
