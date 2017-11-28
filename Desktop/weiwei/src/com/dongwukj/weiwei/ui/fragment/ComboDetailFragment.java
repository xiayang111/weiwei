package com.dongwukj.weiwei.ui.fragment;


import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.*;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.AddToCartRequest;
import com.dongwukj.weiwei.idea.request.ComboDetailRequest;
import com.dongwukj.weiwei.idea.result.ComboDetailResult;
import com.dongwukj.weiwei.idea.result.ComboDetailResult.ComboDetailEntity;
import com.dongwukj.weiwei.idea.result.PhoneAddProductResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.HomeRequestClient;
import com.dongwukj.weiwei.net.ShoppingCartRequestClient;
import com.dongwukj.weiwei.net.ShoppingCartRequestClient.AddShoppingCartRequestClientCallback;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.widget.BadgeView;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;


public class ComboDetailFragment extends AbstractHeaderFragment {
	private View view;
	private ListView lv;
	
	private FinalBitmap fb;
	private List<ComboDetailEntity> list=new ArrayList<ComboDetailResult.ComboDetailEntity>();
	private MyBaseAdapter adapter;
	private int pmId;
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.fragment_combodetail, null);
		return view;
	}

	@Override
	protected String setTitle() {
		return activity.getIntent().getStringExtra("title");
	}
	private void setBadgeViewText(String buyNum) {
		if (Integer.parseInt(buyNum)>=99) {
			buyNumView.setTextSize(9);
			buyNumView.setText("99+");
		}else {
			buyNumView.setTextSize(12);
			buyNumView.setText(buyNum);
		}
		buyNumView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        if (!buyNum.equals("0")) {
        	buyNumView.show();
		}else {
			buyNumView.hide();
		}
        
	}
	 private void openNewActivityWithHeader(int type,boolean isneedlogin,boolean hasheader) {
	        Intent intent = new Intent(activity, HomeHeaderActivity.class);
	        intent.putExtra("type", type);
	        intent.putExtra("needLogin", isneedlogin);
	        intent.putExtra("hasHeader", hasheader);
	        intent.putExtra("isFromDetails", true);
	        startActivity(intent);
	    }
	@Override
	protected void findView(View v) {
		//double total = activity.getIntent().getDoubleExtra("total", 0);
		tv_total = (TextView) v.findViewById(R.id.tv_total);
		
		cart_image=(ImageView)v.findViewById(R.id.iv_cart);
		isFromShopcart = activity.getIntent().getBooleanExtra("isFromShopcart", false);
        
		 cart_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isFromShopcart) {
					activity.finish();
				}else {
					openNewActivityWithHeader(HeaderActivityType.AddShopCart.ordinal(), true, false);
				}
				
			}
		});
	        buyNumView = new BadgeView(activity, cart_image);
	        buyNumView.setTextColor(Color.WHITE);
	        //buyNumView.setBackgroundColor(Color.RED);
	        buyNumView.setTextSize(10);
	        buyNum=baseApplication.getCartCount();
	        setBadgeViewText(buyNum+"");
		lv = (ListView) view.findViewById(R.id.lv);
		bt_addCart = (Button) view.findViewById(R.id.bt_addCart);
		view.findViewById(R.id.bt_addCart).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 boolean login = isLogin();
				if(login){
					addComBoToCart(v);
				}else{
					showdialog();
				}
				
			}

		});
		adapter = new MyBaseAdapter();
		lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ComboDetailEntity comboDetailEntity=list.get(position);
                /*Intent intent=new Intent(activity, NewProductDetailActivity.class);
                intent.putExtra("productId",comboDetailEntity.getpId());
                startActivity(intent);*/

                Intent intent=new Intent(activity, HomeHeaderActivity.class);
                intent.putExtra("productId",comboDetailEntity.getpId());
                intent.putExtra("type", HeaderActivityType.ProductDetail.ordinal());
                intent.putExtra("hasHeader",false);
                startActivity(intent);
            }
        });
		init();
		
	}
	
    private boolean isLogin() {
        UserResult result = baseApplication.getUserResult();
        if (result != null && result.getUserAccount() != null) {
            return true;
        } else {
            return false;
        }
    }
    protected void showdialog() {
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
	
	private void init() {
		
		
		fb = FinalBitmap.create(activity);
		fb.configLoadfailImage(R.drawable.default_small);
		fb.configLoadingImage(R.drawable.default_small);
		pmId = activity.getIntent().getIntExtra("pmId", 0);
		getData(pmId);
		
	}

	private void getData(int pmId){
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result=baseApplication.getUserResult();
		ComboDetailRequest request=new ComboDetailRequest();
		request.setPackageId(pmId);

        HomeRequestClient homeRequestClient=new HomeRequestClient(activity,baseApplication);
        homeRequestClient.comboDetailList(request, new HomeRequestClient.ComboRequestClientCallback() {
            @Override
            protected void detailList(ComboDetailResult result) {
            	if (getActivity()==null) {
					return;
				}
                if (result.getProducts()==null||result.getProducts().size()==0) {
                    Toast.makeText(activity,"套餐详情界面暂无",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                	float total=0;
                    list.addAll(result.getProducts());
                    for (ComboDetailEntity comboDetailEntity : list) {
                    	 total=total+comboDetailEntity.getShopPrice()*comboDetailEntity.getNumber();
					}
                    tv_total.setText(Html.fromHtml("总计:<font color='red'>"+String.format("￥%.2f",total)+"</font>"));
                    adapter.notifyDataSetChanged();
                }
            }
        });


	}
	private class MyBaseAdapter extends BaseAdapter{

		private View view;
		private Viewhodler viewhodler;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView==null) {
				view = View.inflate(activity, R.layout.combodetail_item, null);
				viewhodler = new Viewhodler(view);
				view.setTag(viewhodler);
			}else {
				view=convertView;
				viewhodler=(Viewhodler) view.getTag();
			}
            String imgUrl=list.get(position).getShowImg();
            if(!NetworkUtil.checkUrl(imgUrl)){
                imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
            }
			fb.display(viewhodler.im_showImg, imgUrl);
			viewhodler.pro_name.setText(list.get(position).getName());
			//viewhodler.tv_saleCount.setText(list.get(position).getSaleCount()+""); //销售数量
			viewhodler.tv_weight.setText(list.get(position).getWeight()+"克");			 //商品规格
			//viewhodler.shopPrice.setText("¥"+list.get(position).getShopPrice());	     //商品商城价
			viewhodler.shopPrice.setText(String.format("￥%.2f", list.get(position).getShopPrice()));	     //商品商城价
			//viewhodler.marketPrice.setText("¥"+list.get(position).getMarketPrice());     //商品市场价
			viewhodler.marketPrice.setText(String.format("￥%.2f", list.get(position).getMarketPrice()));	
			viewhodler.marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			viewhodler.tv_num.setText("每份套餐包含"+list.get(position).getNumber()+"份该商品");
			
			return view;
		} 
		
	}
	private class Viewhodler{
		public ImageView im_showImg;
		public TextView pro_name;
		public TextView tv_saleCount;
		public TextView shopPrice;
		public TextView marketPrice;
		public TextView tv_weight;
		public TextView tv_num;
		public Viewhodler(View view) {
			im_showImg=(ImageView) view.findViewById(R.id.iv_product);
			pro_name=(TextView) view.findViewById(R.id.pro_name);
			//tv_saleCount=(TextView) view.findViewById(R.id.tv_saleCount);
			tv_weight = (TextView) view.findViewById(R.id.tv_weight);
			shopPrice=(TextView) view.findViewById(R.id.tv_shopPrice);
			marketPrice=(TextView) view.findViewById(R.id.tv_marketPrice);
			tv_num=(TextView) view.findViewById(R.id.tv_num);
			
		}
	}
	
	private int buyNum = 0;//购买数量
    private BadgeView buyNumView;//显示购买数量的控件
    private ViewGroup anim_mask_layout;//动画层
    private ImageView buyImg;// 这是在界面上跑的小图片
    private ImageView cart_image;
    
/*    private int buyNum = 0;//购买数量
    private BadgeView buyNumView;//显示购买数量的控件
    private ViewGroup anim_mask_layout;//动画层
*/   // private ImageView buyImg;// 这是在界面上跑的小图片
	private void addComBoToCart(final View v) {
       /* BaseRequestClient client = new BaseRequestClient(activity);
        UserResult userResult = baseApplication.getUserResult();
        AddToCartRequest request = new AddToCartRequest();
        request.setGoodsNum("1");
        request.setPmId(pmId+"");
		client.httpPostByJson("PhoneAddProduct", userResult, request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

			@Override
			public void callBack(BaseResult result) {
				if(result!=null){
					if(result.getCode()==BaseResult.CodeState.Success.getCode()){
						Toast.makeText(activity, "套餐添加到购物车成功!", Toast.LENGTH_SHORT).show();
						
                        int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                        v.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                        buyImg = new ImageView(activity);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
                        if(buyNum%2==0){
                            buyImg.setImageResource(R.drawable.weiwei_add_cart_icon1);// 设置buyImg的图片
                        }else{
                            buyImg.setImageResource(R.drawable.weiwei_add_cart_icon2);// 设置buyImg的图片
                        }
                        setAnim(buyImg, start_location);// 开始执行动画
						
					}else{
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(activity, getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}
		});*/
		cart_image.setEnabled(false);
		bt_addCart.setEnabled(false);
		AddToCartRequest request = new AddToCartRequest();
        request.setGoodsNum("1");
        request.setPmId(pmId+"");
		ShoppingCartRequestClient client=new ShoppingCartRequestClient(activity, baseApplication);
		client.addCart(request, new AddShoppingCartRequestClientCallback() {
			@Override
			protected void listSuccess(PhoneAddProductResult result) {
				if (getActivity()==null) {
					return;
				}
				Toast.makeText(activity, "套餐添加到购物车成功!", Toast.LENGTH_SHORT).show();
				
                int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                v.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                buyImg = new ImageView(activity);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
                if(buyNum%2==0){
                    buyImg.setImageResource(R.drawable.weiwei_add_cart_icon1);// 设置buyImg的图片
                }else{
                    buyImg.setImageResource(R.drawable.weiwei_add_cart_icon2);// 设置buyImg的图片
                }
                setAnim(buyImg, start_location);// 开始执行动画
			}
		});
	}
	
	
	/**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup vg, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    final int count = 300;
    /**
     * 要start 动画的那张图片的ImageView
     * @param imageView
     */
    @SuppressLint("NewApi")
    private void startAnimation(final ImageView imageView) {

        Keyframe[] keyframes = new Keyframe[count];
        final float keyStep = 1f / (float) count;
        float key = keyStep;
        for (int i = 0; i < count; ++i) {
            keyframes[i] = Keyframe.ofFloat(key, i + 1);
            key += keyStep;
        }

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofKeyframe("translationX", keyframes);
        key = keyStep;
        for (int i = 0; i < count; ++i) {
            keyframes[i] = Keyframe.ofFloat(key, -getY(i + 1));
            key += keyStep;
        }

        PropertyValuesHolder pvhY = PropertyValuesHolder.ofKeyframe("translationY", keyframes);
        ObjectAnimator yxBouncer = ObjectAnimator.ofPropertyValuesHolder(imageView, pvhY, pvhX).setDuration(1500);
        yxBouncer.setInterpolator(new BounceInterpolator());
        yxBouncer.start();
    }
    @Override
    public void onResume() {
    	buyNum=baseApplication.getCartCount();
    	setBadgeViewText(buyNum+"");
    	super.onResume();
    }
    final float a = -1f / 75f;
	private boolean isFromShopcart;
	private Button bt_addCart;
	private TextView tv_total;

    /**
     * 这里是根据三个坐标点{（0,0），（300,0），（150,300）}计算出来的抛物线方程
     *
     * @param x
     * @return
     */
    private float getY(float x) {
        return a * x * x + 4 * x;
    }

    private void setAnim(final View v, int[] start_location) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v,
                start_location);
        int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
        cart_image.getLocationInWindow(end_location);// shopCart是那个购物车

        // 计算位移
        //int endX = 0 - start_location[0] + 40;// 动画位移的X坐标
        int endX=end_location[0]-start_location[0]+10;
        int endY = end_location[1] - start_location[1]-200;// 动画位移的y坐标

        /*TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX/2, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);
        translateAnimationX.setDuration(200);



        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);
        translateAnimationY.setDuration(800);


        TranslateAnimation translateAnimationX1 = new TranslateAnimation(0,
                endX/2, 0, 0);
        translateAnimationX1.setInterpolator(new LinearInterpolator());
        translateAnimationX1.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX1.setFillAfter(true);
        translateAnimationX1.setDuration(500);

        TranslateAnimation translateAnimationY1 = new TranslateAnimation(0, 0,
                0, 0);
        translateAnimationY1.setInterpolator(new AccelerateInterpolator());
        translateAnimationY1.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX1.setFillAfter(true);
        translateAnimationY1.setDuration(300);
*/


        TranslateAnimation translateAnimationOne = new TranslateAnimation(0, endX/2,
                0, endY);
        translateAnimationOne.setInterpolator(new AccelerateInterpolator());
        translateAnimationOne.setRepeatCount(0);// 动画重复执行的次数
        //translateAnimationOne.setFillAfter(true);
        translateAnimationOne.setDuration(500);

        ScaleAnimation animation_scale=new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_scale.setDuration(500);
        //animation_scale.setFillAfter(true);


        TranslateAnimation translateAnimationTwo = new TranslateAnimation(0, endX/2,
                0, -endY);
        translateAnimationTwo.setInterpolator(new AccelerateInterpolator());
        translateAnimationTwo.setRepeatCount(0);// 动画重复执行的次数
        //translateAnimationOne.setFillAfter(true);
        translateAnimationTwo.setDuration(500);
        translateAnimationTwo.setStartOffset(500);


        ScaleAnimation animation_scaleTwo=new ScaleAnimation(1.0f, 0.2f, 1.0f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_scaleTwo.setDuration(500);
        animation_scaleTwo.setStartOffset(500);
        //animation_scaleTwo.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);


        set.addAnimation(animation_scaleTwo);
        set.addAnimation(translateAnimationTwo);
        set.addAnimation(animation_scale);
        set.addAnimation(translateAnimationOne);


        //set.setFillAfter(true);






        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
                int num=0;
                for (ComboDetailEntity entity : list) {
                	num=num+entity.getNumber();
				}
                buyNum=buyNum+num;//让购买数量加1
               /* buyNumView.setText(buyNum + "");//
                buyNumView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
                buyNumView.show();*/
                setBadgeViewText(buyNum+"");
                bt_addCart.setEnabled(true);
                cart_image.setEnabled(true);
            }
        });

    }
}
