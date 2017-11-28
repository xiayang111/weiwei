package com.dongwukj.weiwei.ui.fragment;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.AddToCartRequest;
import com.dongwukj.weiwei.idea.result.PhoneAddProductResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.ShoppingCartRequestClient;
import com.dongwukj.weiwei.net.ShoppingCartRequestClient.AddShoppingCartRequestClientCallback;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.widget.BadgeView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public abstract class ShopCartMessageFragment extends AbstractHeaderFragment {
	private BadgeView buyNumView;
	private int buyNum;
	private TextView list_header_rightbutton;
	
	
	public void findView(View v,String title) {
		TextView list_header_title = (TextView) v.findViewById(R.id.list_header_title);
		list_header_title.setText(title);
		LinearLayout list_header_leftbutton = (LinearLayout) v.findViewById(R.id.ll_left);
    	list_header_leftbutton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				activity.finish();
			}
		});
    	RelativeLayout rl=(RelativeLayout) v.findViewById(R.id.rl);
    	list_header_rightbutton = (TextView) v.findViewById(R.id.list_header_rightbutton);
    	list_header_rightbutton.setVisibility(View.VISIBLE);
    	list_header_rightbutton.setBackgroundResource(R.drawable.weiwei_gouwuche_checked);
    	buyNumView=new BadgeView(activity, rl);
    	buyNumView.setTextColor(Color.WHITE);
        buyNumView.setTextSize(10);
        buyNum = baseApplication.getCartCount();
        setBadgeViewText(buyNum+"");
        list_header_rightbutton.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				openNewActivityWithHeader(HeaderActivityType.AddShopCart.ordinal(), true, false);
			}
		});
        
	}
	@Override
	public void onResume() {
		buyNum=baseApplication.getCartCount();
    	setBadgeViewText(buyNum+"");
		super.onResume();
	}
	private void openNewActivityWithHeader(int type,boolean isneedlogin,boolean hasheader) {
        Intent intent = new Intent(activity, HomeHeaderActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("needLogin", isneedlogin);
        intent.putExtra("hasHeader", hasheader);
        intent.putExtra("isFromDetails", true);
        startActivity(intent);
    }
	 public void showdialog() {
	        final Dialog dialog = new Dialog(activity, R.style.Dialog);
	        //View view = View.inflate(activity, R.layout.ordercancle_dialog, null);
	        dialog.setContentView(R.layout.ordercancle_dialog);
	        dialog.setCancelable(false);
	        TextView tv_cancle = (TextView) dialog.findViewById(R.id.tv_cancle);
	        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);
	        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
	        
			WindowManager m = activity.getWindowManager();
	        Window dialogWindow = dialog.getWindow();
	        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
	        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
	        p.height = (int) (d.getHeight() * 0.36); // 高度设置为屏幕的0.30
	        p.width = (int) (d.getWidth() * 0.80); // 宽度设置为屏幕的0.80
	        dialogWindow.setAttributes(p);
	        
	        tv_title.setText("确认登录");
	        tv_ok.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                dialog.dismiss();
	            }
	        });
	        tv_cancle.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                dialog.dismiss();
	                Intent intent = new Intent(activity, LoginActivity.class);
	                startActivity(intent);
	            }
	        });
	        dialog.show();

	    }
	 public void checkedIsloginToBuy(int pmid,boolean iscombo){
		 if (isLogin()) {
				addComBoToCart(pmid,iscombo);
			}else {
				showdialog();
			}
	 }
	 private void addComBoToCart(int pmid,boolean iscombo) {
		 	AddToCartRequest request = new AddToCartRequest();
		 	if (iscombo) {
		 		 request.setGoodsNum("1");
			      request.setPmId(pmid+"");
			}else {
				 request.setGoodsNum("1");
			       request.setGoodsId(pmid);
			}
	       
			ShoppingCartRequestClient client=new ShoppingCartRequestClient(activity, baseApplication);
			client.addCart(request, new AddShoppingCartRequestClientCallback() {
				@Override
				protected void listSuccess(PhoneAddProductResult result) {
					if (getActivity()==null) {
						return;
					}
					Toast.makeText(activity, "商品加到购物车成功!", Toast.LENGTH_SHORT).show();
					setBadgeViewText(baseApplication.getCartCount()+"");
	            }
			});
		}
	 private void setBadgeViewText(String buyNum) {
		 if (Integer.parseInt(buyNum)>=99) {
				buyNumView.setTextSize(9);
				buyNumView.setText("99+");
			}else {
				buyNumView.setTextSize(12);
				buyNumView.setText(buyNum);
			}
			//buyNumView.setText(buyNum);//
	        buyNumView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
	        if (!buyNum.equals("0")) {
	        	buyNumView.show();
			}else {
				buyNumView.hide();
			}
	        
		}
	  private boolean isLogin() {
	        UserResult result = baseApplication.getUserResult();
	        if (result != null && result.getUserAccount() != null) {
	            return true;
	        } else {
	            return false;
	        }
	    }
}
