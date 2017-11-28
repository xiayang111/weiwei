package com.dongwukj.weiwei.ui.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.animation.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import cn.jpush.android.data.s;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

import com.alibaba.fastjson.parser.deserializer.StringFieldDeserializer;
import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.FragmentTabAdapter;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.AddAttentionrequest;
import com.dongwukj.weiwei.idea.request.AddToCartRequest;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.NewAddressRequest;
import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.dongwukj.weiwei.idea.request.PhonegoodsdetailRequest;
import com.dongwukj.weiwei.idea.request.Speciality;
import com.dongwukj.weiwei.idea.result.*;
import com.dongwukj.weiwei.idea.result.NewAddressResult.NewAddressEntity;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.net.ShoppingCartRequestClient;
import com.dongwukj.weiwei.net.ShoppingCartRequestClient.AddShoppingCartRequestClientCallback;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeActivity;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.widget.BadgeView;
import com.dongwukj.weiwei.ui.widget.MyGridView;
import com.dongwukj.weiwei.ui.widget.SubListView;
import com.dongwukj.weiwei.ui.widget.TestPullLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunjaly on 15/4/28.
 */


public class NewProductDetailFragment extends AbstractLoadingFragment implements View.OnClickListener {

    PullToRefreshScrollView contentView;
    PullToRefreshScrollView detailView;
    TestPullLayout testPullLayout;
    RelativeLayout product_detail_content_rl;
    private FinalBitmap finalBitmap;



    private ImageButton fragmentProductDetailImageButton;
    private BaseApplication baseApplication;
 
    private TextView productDetailTitle;
    private TextView productDetailShopPrice;
    private TextView productDetailMarketPrice,weight_unittwo;
  

    private View backgroundView;
    private RelativeLayout productDetailPanel;

    //private Button addToCartButton;

    public List<Fragment> fragments = new ArrayList<Fragment>();
    private FragmentProductDetailImageList fragmentProductDetailImageList;
    private FragmentProductDetailAttributes fragmentProductDetailAttributes;
    private RadioGroup rgs;
    private BannerFragment bannerFragment;
    private TextView productDetailSaleCountAndShip;
    private TextView productDetailStockNumber;

    private int productId=0;


    private LinearLayout cart_content;


    private BadgeView buyNumView;//显示购买数量的控件
    private ViewGroup anim_mask_layout;//动画层
    private ImageView buyImg;// 这是在界面上跑的小图片
    private ImageView cart_image;


   
    
    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_pull_layyout, container, false);
        productId = activity.getIntent().getIntExtra("productId", -1);     //商品ID, 上一个页面发送来的.
        db = LiteOrm.newCascadeInstance(activity, application.DB_NAME);
        finalBitmap = FinalBitmap.create(activity);
        finalBitmap.configLoadfailImage(R.drawable.speciality);
        finalBitmap.configLoadingImage(R.drawable.speciality);
        return view;
    }

    @Override
    public void onResume() {
    	if (newPhonegoodsdetailResult!=null) {
    		
    		int weightBalance = getWeightBalance();
    		 QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
 			qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId(),newPhonegoodsdetailResult.getGoodsId()});
 			ArrayList<NewHomeEntity> arrayList = db.query(qb);
 			if (arrayList.size()>0) {
 				if (arrayList.get(0).getAddTime()<baseApplication.getWeeTimew()) {
					db.delete(arrayList.get(0));
					EventBus.getDefault().post("shuaxin");
					tv_total_weigth.setVisibility(View.GONE);
	 				tv_jian.setVisibility(View.GONE);
	 				ll_total.setVisibility(View.GONE);
				}else {
					tv_total_weigth.setVisibility(View.VISIBLE);
	 				tv_jian.setVisibility(View.VISIBLE);
	 				ll_total.setVisibility(View.VISIBLE);
	 				if (newPhonegoodsdetailResult.getBusinessGoodsDiscountObject().getDiscountType()==1) {
						total_price.setText(String.format("￥%.2f", (int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramdiscountprice()));
						
					}else {
						total_price.setText(String.format("￥%.2f", (int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramprice()));
					}
	 				tv_total_weigth.setText((int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight())+weightBalance+"");
				}
 				
 			}else {
 				tv_total_weigth.setVisibility(View.GONE);
 				tv_jian.setVisibility(View.GONE);
 				ll_total.setVisibility(View.GONE);
 			}
		}
    	
    	setBuycount(getCount());
    	super.onResume();
    }
    
    @Override
    public void onPause() {
    	EventBus.getDefault().post(true);
		super.onPause();
  
    }
    public void onEventMainThread(Boolean item){}
    @Override
    protected void findViews(View v) {
    ll_total = (LinearLayout) v.findViewById(R.id.ll_total);
    total_price = (TextView) v.findViewById(R.id.total_price);
    weight_unittwo = (TextView) v.findViewById(R.id.weight_unittwo);
    nomain_weight = (CheckBox) v.findViewById(R.id.nomain_weight);
    main_weight = (CheckBox) v.findViewById(R.id.main_weight);
   nomain_weight.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (!nomain_weight.isChecked()) {
				nomain_weight.setChecked(true);
				return;
			}
			isCheckedMain=0;
			main_weight.setChecked(false);
			NewHomeEntity entity = findenity();
			if (entity!=null) {
				entity.setIsmain(0);
				entity.setCount(1);
				db.update(entity);
				tv_total_weigth.setText(newPhonegoodsdetailResult.getSidecourseminweight()+"");
				if (newPhonegoodsdetailResult.getBusinessGoodsDiscountObject().getDiscountType()==1) {
					total_price.setText(String.format("￥%.2f", (int)(entity.getCount()*entity.getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramdiscountprice()));
					
				}else {
					total_price.setText(String.format("￥%.2f", (int)(entity.getCount()*entity.getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramprice()));
				}
			}else {
				addToCart(v);
			}
		}

		
	});
    main_weight.setOnClickListener(new OnClickListener() {
    			
    	@Override
    	public void onClick(View v) {
    		if (!main_weight.isChecked()) {
    			main_weight.setChecked(true);
				return;
			}
    		isCheckedMain=1;
    		nomain_weight.setChecked(false);
    		NewHomeEntity entity = findenity();
			if (entity!=null) {
				entity.setIsmain(1);
				entity.setCount(1);
				db.update(entity);
				tv_total_weigth.setText(newPhonegoodsdetailResult.getMaincourseminweight()+"");
				if (newPhonegoodsdetailResult.getBusinessGoodsDiscountObject().getDiscountType()==1) {
					total_price.setText(String.format("￥%.2f", (int)(entity.getCount()*entity.getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramdiscountprice()));
					
				}else {
					total_price.setText(String.format("￥%.2f", (int)(entity.getCount()*entity.getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramprice()));
				}
			}else {
				addToCart(v);
			}
    	}
    });
    
    
    
    
    
    
    
    
    
    
    image = (ImageView) v.findViewById(R.id.image);
    	back = (ImageView) v.findViewById(R.id.back);
    	
    	goodsdescription = (TextView) v.findViewById(R.id.goodsdescription);
    	tv_pricetype = (TextView) v.findViewById(R.id.tv_pricetype);
    	tv_jian = (TextView) v.findViewById(R.id.tv_jian);
    	tv_jian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int weightBalance = getWeightBalance();
				QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
				qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId(),newPhonegoodsdetailResult.getGoodsId()});
				ArrayList<NewHomeEntity> arrayList = db.query(qb);
				if (arrayList!=null&&arrayList.size()>0) {
					if (arrayList.get(0).getCount()==1) {
						db.delete(arrayList.get(0));
						EventBus.getDefault().post(newPhonegoodsdetailResult.getGoodsId());
						tv_total_weigth.setVisibility(View.GONE);
						tv_jian.setVisibility(View.GONE);
						ll_total.setVisibility(View.GONE);
						
					}else {
						arrayList.get(0).setCount(arrayList.get(0).getCount()-1);
						db.update(arrayList.get(0));
						tv_total_weigth.setText((int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight())+weightBalance+"");
						if (newPhonegoodsdetailResult.getBusinessGoodsDiscountObject().getDiscountType()==1) {
							total_price.setText(String.format("￥%.2f", (int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramdiscountprice()));
							
						}else {
							total_price.setText(String.format("￥%.2f", (int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramprice()));
						}
					}
				}
				setBuycount(getCount());
			
			}
		});
    	tv_total_weigth = (TextView) v.findViewById(R.id.tv_total_weigth);
    	tv_jia = (TextView) v.findViewById(R.id.tv_jia);
    	tv_jia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int marketId = baseApplication.getUserResult().getMarketId();
        		
            	QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
        		qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),marketId,newPhonegoodsdetailResult.getGoodsId()});
        		ArrayList<NewHomeEntity> arrayList = db.query(qb);
        		if (arrayList!=null&&arrayList.size()>0) {
        			if (arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+getWeightBalance()+arrayList.get(0).getIncrementweight()>arrayList.get(0).getStockNum()) {
        				Toast.makeText(activity, "库存不足", Toast.LENGTH_SHORT).show();
						return;
					}
        			if (arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+getWeightBalance()+arrayList.get(0).getIncrementweight()>arrayList.get(0).getLimitamount()&&arrayList.get(0).getLimitamount()>0) {
						Toast.makeText(activity, "超过商品单次最大购买数量", Toast.LENGTH_SHORT).show();
					}
        			else {
        				 addToCart(v);
					}
        			
        		}else {
        			 addToCart(v);
				}
                   
				
			}
		});
    	address = (TextView) v.findViewById(R.id.address);
    	((HomeHeaderActivity)getActivity()).ll_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});;
		((HomeHeaderActivity)activity).rl_right.setVisibility(View.VISIBLE);
    	ll_speciality = (LinearLayout) v.findViewById(R.id.ll_speciality);
    	        
    	gd = (MyGridView) v.findViewById(R.id.gd);
    	//addToCartButton=(Button)v.findViewById(R.id.addCartButton);
      //  addToCartButton.setOnClickListener(this);
       isFromShopcart = activity.getIntent().getBooleanExtra("isFromShopcart", false);
    	baseApplication = (BaseApplication) activity.getApplication();
        testPullLayout=(TestPullLayout)v.findViewById(R.id.testPullLayout);
        contentView=(PullToRefreshScrollView)v.findViewById(R.id.contentView);
        detailView=(PullToRefreshScrollView)v.findViewById(R.id.detailView);
        product_detail_content_rl=(RelativeLayout)v.findViewById(R.id.product_detail_content_rl);
        tv_weight = (TextView) v.findViewById(R.id.tv_weight);
        contentView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        detailView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        testPullLayout.setContentView(contentView);
        testPullLayout.setDetailView(detailView);
        
        cart_content=(LinearLayout)v.findViewById(R.id.cart_content);

        
        cart_image=((HomeHeaderActivity)activity).ima_cart_new;
       ((HomeHeaderActivity)activity).rl_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isFromShopcart) {
					activity.finish();
				}else {
					openNewActivityWithHeader(HeaderActivityType.AddShopCart.ordinal(), true, false);
		        }
			}
		});
        buyNumView = new BadgeView(activity, ((HomeHeaderActivity)activity).img_cart);
        buyNumView.setTextColor(Color.WHITE);
        buyNumView.setTextSize(10);
        application = (BaseApplication) activity.getApplication();
       productDetailPanel = (RelativeLayout) v.findViewById(R.id.main_layout);
        productDetailSaleCountAndShip = (TextView) v.findViewById(R.id.product_detail_salecountandship);
        productDetailStockNumber = (TextView) v.findViewById(R.id.product_detail_stockNumber);
      productDetailTitle = (TextView) v.findViewById(R.id.product_detail_title);
        productDetailShopPrice = (TextView) v.findViewById(R.id.product_detal_shopprice);
        productDetailMarketPrice = (TextView) v.findViewById(R.id.product_detail_marketprice);
        weight_unit = (TextView) v.findViewById(R.id.weight_unit);
       fragmentProductDetailImageButton = (ImageButton) v.findViewById(R.id.fragment_product_detail_back_button);
     shared = (ImageButton) v.findViewById(R.id.shared);
        shared.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
                	 showShare(false, null, true);
              
            }
        });
   
        fragmentProductDetailImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        initFragment(v);
        updateDataHanlder.sendEmptyMessageDelayed(UPDATE_UI,100);
     
        reload();
     
        
    }

    public int getTitleHeight(Activity activity) {  
        Rect rect = new Rect();  
          Window window = activity.getWindow();  
         window.getDecorView().getWindowVisibleDisplayFrame(rect);  
         int statusBarHeight = rect.top;  
      int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();  
         int titleBarHeight = contentViewTop - statusBarHeight;  

         return titleBarHeight;  
     } 
    public int getStateHeight(Activity activity ) {  
         Rect rect = new Rect();  
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);  
         return rect.top;  
    } 
    /**
     * 
     */
    private NewHomeEntity findenity() {


		QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
		qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId(),newPhonegoodsdetailResult.getGoodsId()});
		ArrayList<NewHomeEntity> arrayList = db.query(qb);
		if (arrayList!=null&&arrayList.size()>0) {
			
				return arrayList.get(0);
				
		}else {
			return null;
		}
	}
    private void initFragment(View v) {
        bannerFragment = (BannerFragment) activity.getSupportFragmentManager().findFragmentById(R.id.product_detail_banner);
        fragmentProductDetailImageList = new FragmentProductDetailImageList(activity);
        fragmentProductDetailAttributes = new FragmentProductDetailAttributes();
        fragments.add(fragmentProductDetailImageList);
        fragments.add(fragmentProductDetailAttributes);
        rgs = (RadioGroup) v.findViewById(R.id.tabs_rg);

        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(activity, fragments, R.id.product_detail_tab_content, rgs,0);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                System.out.println("Extra---- " + index + " checked!!! ");
            }
        });

    }

    // 使用快捷分享完成分享（请务必仔细阅读位于SDK解压目录下Docs文件夹中OnekeyShare类的JavaDoc）
    /**ShareSDK集成方法有两种</br>
     * 1、第一种是引用方式，例如引用onekeyshare项目，onekeyshare项目再引用mainlibs库</br>
     * 2、第二种是把onekeyshare和mainlibs集成到项目中，本例子就是用第二种方式</br>
     * 请看“ShareSDK 使用说明文档”，SDK下载目录中 </br>
     * 或者看网络集成文档 http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97
     * 3、混淆时，把sample或者本例子的混淆代码copy过去，在proguard-project.txt文件中
     *
     *
     * 平台配置信息有三种方式：
     * 1、在我们后台配置各个微博平台的key
     * 2、在代码中配置各个微博平台的key，http://mob.com/androidDoc/cn/sharesdk/framework/ShareSDK.html
     * 3、在配置文件中配置，本例子里面的assets/ShareSDK.conf,
     */
    private void showShare(boolean silent, String platform, boolean captureView) {
        Context context = activity;
        final OnekeyShare oks = new OnekeyShare();
        String url="http://www.vvlife.com/help/download";
        //oks.setAddress("12345678901");
        oks.setTitle("分享标题");
        oks.setTitleUrl(url);
        String customText = "欢迎使用";
        if (customText != null) {
            oks.setText(customText);
        }
        if (captureView) {
            oks.setViewToShare(getView());
        } else {
            //oks.setImagePath(CustomShareFieldsPage.getString("imagePath", MainActivity.TEST_IMAGE));
            oks.setImageUrl("https://www.baidu.com/img/baidu_jgylogo3.gif?v=29766842.gif");
//			oks.setImageArray(new String[]{MainActivity.TEST_IMAGE, MainActivity.TEST_IMAGE_URL});
        }

        oks.setUrl(url);
        //oks.setFilePath(CustomShareFieldsPage.getString("filePath", MainActivity.TEST_IMAGE));
        oks.setComment("");
        oks.setSite( context.getString(R.string.app_name));
        oks.setSiteUrl(url);
        //oks.setVenueName(CustomShareFieldsPage.getString("venueName", "ShareSDK"));
        //oks.setVenueDescription(CustomShareFieldsPage.getString("venueDescription", "This is a beautiful place!"));

        oks.setSilent(silent);
        oks.setShareFromQQAuthSupport(false);
        String theme = "classic";
        if(OnekeyShareTheme.SKYBLUE.toString().toLowerCase().equals(theme)){
            oks.setTheme(OnekeyShareTheme.SKYBLUE);
        }else{
            oks.setTheme(OnekeyShareTheme.CLASSIC);
        }

        if (platform != null) {
            oks.setPlatform(platform);
        }


        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();

        // 在自动授权时可以禁用SSO方式
        //if(!CustomShareFieldsPage.getBoolean("enableSSO", true))
        oks.disableSSOWhenAuthorize();

        // 去除注释，则快捷分享的操作结果将通过OneKeyShareCallback回调
        //oks.setCallback(new OneKeyShareCallback());

        // 去自定义不同平台的字段内容
        //oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

        // 去除注释，演示在九宫格设置自定义的图标
        Bitmap enableLogo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        Bitmap disableLogo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        String label = getResources().getString(R.string.app_name);
        OnClickListener listener = new OnClickListener() {
            public void onClick(View v) {
                String text = "Customer Logo -- ShareSDK " + ShareSDK.getSDKVersionName();
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
            }
        };
        //oks.setCustomerLogo(enableLogo, disableLogo, label, listener);

        // 去除注释，则快捷分享九宫格中将隐藏新浪微博和腾讯微博
//		oks.addHiddenPlatform(SinaWeibo.NAME);
//		oks.addHiddenPlatform(TencentWeibo.NAME);

        // 为EditPage设置一个背景的View
        oks.setEditPageBackground(getView());
        oks.show(context);
    }


    private void showShare() {
        ShareSDK.initSDK(activity);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();


        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(activity.getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        oks.setSilent(true);
        // 启动分享GUI
        oks.show(activity);
    }


    @Override
    protected void reload() {
        startLoading();
        updateDataHanlder.sendEmptyMessage(FETCH_DATA);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
         

            case R.id.addCartButton:		//添加商品到购物车

            	int marketId = baseApplication.getUserResult().getMarketId();
        		
            	QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
        		qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),marketId,newPhonegoodsdetailResult.getGoodsId()});
        		ArrayList<NewHomeEntity> arrayList = db.query(qb);
        		if (arrayList!=null&&arrayList.size()>0) {
        			if (arrayList.get(0).getCount()==arrayList.get(0).getLimitamount()&&arrayList.get(0).getLimitamount()>0) {
						Toast.makeText(activity, "超过商品单次最大购买数量", Toast.LENGTH_SHORT).show();
					}
        			else {
        				 addToCart(v);
					}
        			
        		}else {
        			 addToCart(v);
				}
                   


                break;
            case R.id.cart_image:  //到购物车里面查看商品
            	if (isFromShopcart) {
					activity.finish();
				}else {
					openNewActivityWithHeader(HeaderActivityType.AddShopCart.ordinal(), true, false);
		        }
            	 break;
            default:
                break;
        }
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
        		100,
        		100);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    final int count = 300;
     

    final float a = -1f / 75f;

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
        int endX=end_location[0]-start_location[0]-10;
        int endY = end_location[1] - start_location[1]-30;// 动画位移的y坐标



        TranslateAnimation translateAnimationOne = new TranslateAnimation(0, endX,
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
        RotateAnimation animation=new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);

        ScaleAnimation animation_scaleTwo=new ScaleAnimation(1.0f, 0.2f, 1.0f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_scaleTwo.setDuration(500);
        animation_scaleTwo.setStartOffset(500);
        //animation_scaleTwo.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);


        set.addAnimation(animation_scaleTwo);
        set.addAnimation(animation);
        //set.addAnimation(translateAnimationTwo);
       // set.addAnimation(animation_scale);
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
      
             
                setBuycount(getCount());
                tv_jia.setEnabled(true);
            	
                ((HomeHeaderActivity)activity).rl_right.setEnabled(true);
        		
            }

			
        });

    }
	public void setBuycount(int count){
		setBadgeViewText(count+"");
	}
	public int getCount(){
		int num=0;
    	QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
    	qb.where("userAccount=? and marketId=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId()});
		ArrayList<NewHomeEntity> arrayList = db.query(qb);
		for (NewHomeEntity newHomeEntity : arrayList) {
			num=num+newHomeEntity.getCount();
		}
		return arrayList.size();
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
    private void openNewActivityWithHeader(int type, int pmId, String title,String reviewCount1) {
        Intent intent = new Intent(activity, HomeHeaderActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("pmId", pmId);
        intent.putExtra("title", title);
        intent.putExtra("reviewCount", reviewCount1);
        startActivity(intent);
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
	public void onCreate(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	 public int getWeightBalance(){
		 int weightBalance=0;
			if (isCheckedMain==1) {
				weightBalance=newPhonegoodsdetailResult.getMaincourseminweight()-newPhonegoodsdetailResult.getIncrementweight();
			}else {
				weightBalance=newPhonegoodsdetailResult.getSidecourseminweight()-newPhonegoodsdetailResult.getIncrementweight();
			}
			
			return weightBalance;
	};

    //添加到购物车方法
    private void addToCart(final View v) {
    	
    	tv_jia.setEnabled(false);
    	 ((HomeHeaderActivity)activity).rl_right.setEnabled(false);
    	NewHomeEntity entity=new NewHomeEntity();
    	entity.setBusinessgoodsid(newPhonegoodsdetailResult.getGoodsId());
    	entity.setName(newPhonegoodsdetailResult.getGoodsName());
    	entity.setWeight(newPhonegoodsdetailResult.getWeight());
    	entity.setPrice(newPhonegoodsdetailResult.getShopPrice());
    	entity.setStockNum(newPhonegoodsdetailResult.getStockNum());
    	entity.setIcon(newPhonegoodsdetailResult.getIcon());
    	//entity.setGid(newPhonegoodsdetailResult.getDefaultGoods().getAttrvalueid());
    	entity.setIsback(newPhonegoodsdetailResult.getIsback());
    	entity.setIsFullcut(newPhonegoodsdetailResult.getIsFullcut());
    	entity.setIsgift(newPhonegoodsdetailResult.getIsgift());
    	entity.setIshot(newPhonegoodsdetailResult.getIshot());
    	entity.setIsnew(newPhonegoodsdetailResult.getIsnew());
    	entity.setBusinessGoodsDiscountObject(newPhonegoodsdetailResult.getBusinessGoodsDiscountObject());
    	entity.setLimitamount(newPhonegoodsdetailResult.getLimitamount());
    	entity.setGid(newPhonegoodsdetailResult.getGid());
    	entity.setGramdiscountprice(newPhonegoodsdetailResult.getGramdiscountprice());
    	entity.setGramprice(newPhonegoodsdetailResult.getGramprice());
    	entity.setIncrementweight(newPhonegoodsdetailResult.getIncrementweight());
    	entity.setIsmaincourse(newPhonegoodsdetailResult.getIsmaincourse());
    	entity.setMaincourseminweight(newPhonegoodsdetailResult.getMaincourseminweight());
    	entity.setSidecourseminweight(newPhonegoodsdetailResult.getSidecourseminweight());
    	entity.setUnit(newPhonegoodsdetailResult.getUnit());
    	QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
    	int weightBalance = getWeightBalance();
		String account = baseApplication.getUserResult().getUserAccount();
		int marketId = baseApplication.getUserResult().getMarketId();
		qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(account),marketId,newPhonegoodsdetailResult.getGoodsId()});
		ArrayList<NewHomeEntity> arrayList = db.query(qb);
		if (arrayList!=null&&arrayList.size()>0) {
			arrayList.get(0).setCount(arrayList.get(0).getCount()+1);
			long millis = System.currentTimeMillis();
			arrayList.get(0).setAddTime(millis);
			db.update(arrayList.get(0));
			tv_total_weigth.setText((int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight())+weightBalance+"");
			if (newPhonegoodsdetailResult.getBusinessGoodsDiscountObject().getDiscountType()==1) {
				total_price.setText(String.format("￥%.2f", (int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+weightBalance)*newPhonegoodsdetailResult.getGramdiscountprice()));
				
			}else {
				total_price.setText(String.format("￥%.2f", (int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+weightBalance)*newPhonegoodsdetailResult.getGramprice()));
			}
		}else {
			entity.setCount(1);
			long millis = System.currentTimeMillis();
			entity.setAddTime(millis);
			entity.setMarketId(marketId);
			entity.setIsmain(isCheckedMain);
			entity.setUserAccount(Integer.parseInt(account));
			db.insert(entity);
			tv_total_weigth.setText((int)(entity.getCount()*entity.getIncrementweight())+weightBalance+"");
			if (newPhonegoodsdetailResult.getBusinessGoodsDiscountObject().getDiscountType()==1) {
				total_price.setText(String.format("￥%.2f", (int)(entity.getCount()*entity.getIncrementweight()+weightBalance)*newPhonegoodsdetailResult.getGramdiscountprice()));
				
			}else {
				total_price.setText(String.format("￥%.2f", (int)(entity.getCount()*entity.getIncrementweight()+weightBalance)*newPhonegoodsdetailResult.getGramprice()));
			}
			
		}
		
		tv_total_weigth.setVisibility(View.VISIBLE);
		tv_jian.setVisibility(View.VISIBLE);
		ll_total.setVisibility(View.VISIBLE);
		
		 int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
         image.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
         buyImg = new ImageView(activity);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
        finalBitmap.display(buyImg, imagelist.get(0).getIcon());
        

         setAnim(buyImg, start_location);// 开始执行动画
         
		
    }




  



    private final int FETCH_DATA=100;
    private final int UPDATE_UI=101;
    private Handler updateDataHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FETCH_DATA:
                    fetchData();
                    break;
                case UPDATE_UI:
                    resetUI();
               
                    break;
                case 500:
//                	addToCartButton.setVisibility(View.INVISIBLE);
//                	showguide();
                    break;
            }

        }
    };

    private void resetUI(){
        int visibleHeight= productDetailPanel.getHeight()- cart_content.getHeight()-10;
        testPullLayout.updateUI(visibleHeight);
        if(product_detail_content_rl!=null && testPullLayout!=null){
            if(product_detail_content_rl.getHeight()<testPullLayout.getHeight()){
                ViewGroup.LayoutParams layoutParams=product_detail_content_rl.getLayoutParams();
                WindowManager windowManager=activity.getWindowManager();
                DisplayMetrics displayMetrics=new DisplayMetrics();

                windowManager.getDefaultDisplay().getMetrics(displayMetrics);

                layoutParams.height=testPullLayout.getHeight()-(int)(displayMetrics.density*20);

                ViewCompat.postInvalidateOnAnimation(product_detail_content_rl);
            }
        }
    }


    private ImageButton shared;
    
    private ArrayList<Icon> imagelist;
private NewPhonegoodsdetailResult newPhonegoodsdetailResult;
    private void fetchData() {
        BaseRequestClient client = new BaseRequestClient(activity);
        UserResult userResult = baseApplication.getUserResult();
        PhonegoodsdetailRequest phonegoodsdetailRequest = new PhonegoodsdetailRequest();
        phonegoodsdetailRequest.setGid(productId);
        client.httpPostByJson("PhoneGetGoodsDetail", userResult, phonegoodsdetailRequest, NewPhonegoodsdetailResult.class, new BaseRequestClient.RequestClientCallBack<NewPhonegoodsdetailResult>() {
           
			@Override
            public void callBack(NewPhonegoodsdetailResult result) {
            	if (getActivity()==null) {
					return;
				}
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                    	imagelist = result.getImagelist();
                    	newPhonegoodsdetailResult=result;
                        endLoading();
                        fillContent(result);
                        updateDataHanlder.sendEmptyMessageDelayed(UPDATE_UI,100);

                    } else {
                        failLoading();
                    }
                } else {
                    failLoading();
                }
            }

            @Override
            public void loading(long count, long current) {

            }
            @Override
            public void logOut(boolean isLogOut,NewPhonegoodsdetailResult result) {FinalDb finalDb=FinalDb.create(activity);
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
    }


    
	private BaseApplication application;
	private boolean isFromShopcart;

	private TextView tv_weight;
	private MyGridView gd;
//	private Myadapter myadapter;
	private DataBase db;
	private LinearLayout ll_speciality;
	private TextView goodsdescription,address,tv_pricetype,tv_jian,tv_total_weigth,tv_jia;
	private ImageView image,back;
	private CheckBox nomain_weight;
	private CheckBox main_weight;
	/*
	 * 获取默认地址
	 */
	private void checkAddress() {
		BaseRequestClient client=new BaseRequestClient(activity);
		BaseRequest request=new BaseRequest();
		progressDialog.setMessage("获取地址信息中。。。");
		progressDialog.show();
		client.httpPostByJsonNew("PhoneGetDefaultAddress", baseApplication.getUserResult(), request, PhoneGetDefaultAddressResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneGetDefaultAddressResult>() {

			@Override
			public void callBack(PhoneGetDefaultAddressResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						if (result.getShipAddress()!=null) {
							address.setText(Html.fromHtml(
									"<font color=\"#33333\">您当前的收货地址：武汉市"+result.getPlot().getRegionName()+result.getPlot().getName()+result.getShipAddress().getAddress()+"</font><br/><font color='red'>商品加入购物车后地址不能修改"  
							                +"</font>"));
						}else {
							
							address.setText(Html.fromHtml(
									"<font color=\"#33333\">您当前的收货地址：武汉市"+result.getPlot().getRegionName()+result.getPlot().getName()+"</font><br/><font color='red'>商品加入购物车后地址不能修改"  
							                +"</font>"));
						}
						
		            }else {
						showtoast(result.getMessage(), activity);
					}
				}else {
					showtoast(activity.getResources().getString(R.string.data_error), activity);
				}
				progressDialog.dismiss();;
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
	private int isCheckedMain=1;
	private LinearLayout ll_total;
	private TextView total_price;
	private TextView weight_unit;
	 private void fillContent(final NewPhonegoodsdetailResult result) {
    	/*
    	 * 判断库存<=0时,让加入购物车按钮不可点击.
    	 */
		 weight_unittwo.setText(result.getUnit().getName());
		 main_weight.setText(result.getMaincourseminweight()+result.getUnit().getName());
		nomain_weight.setText(result.getSidecourseminweight()+result.getUnit().getName());
		 QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
			qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId(),result.getGoodsId()});
			ArrayList<NewHomeEntity> arrayList = db.query(qb);
			if (arrayList.size()>0) {
				tv_total_weigth.setVisibility(View.VISIBLE);
				
				tv_jian.setVisibility(View.VISIBLE);
				ll_total.setVisibility(View.VISIBLE);
				if (arrayList.get(0).getIsmain()==1) {
					if (result.getIsmaincourse()==0) {
						isCheckedMain=1;
						if (newPhonegoodsdetailResult.getBusinessGoodsDiscountObject().getDiscountType()==1) {
							total_price.setText(String.format("￥%.2f", (int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramdiscountprice()));
							
						}else {
							total_price.setText(String.format("￥%.2f", (int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramprice()));
						}
						main_weight.setChecked(true);
						nomain_weight.setChecked(false);
					}else {
						nomain_weight.setVisibility(View.INVISIBLE);
						if (newPhonegoodsdetailResult.getBusinessGoodsDiscountObject().getDiscountType()==1) {
							total_price.setText(String.format("￥%.2f", (int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramdiscountprice()));
							
						}else {
							total_price.setText(String.format("￥%.2f", (int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramprice()));
						}
					}
					tv_total_weigth.setText((int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+arrayList.get(0).getMaincourseminweight()-arrayList.get(0).getIncrementweight())+"");
					
				}else {
					isCheckedMain=0;
					if (newPhonegoodsdetailResult.getBusinessGoodsDiscountObject().getDiscountType()==1) {
						total_price.setText(String.format("￥%.2f", (int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramdiscountprice()));
						
					}else {
						total_price.setText(String.format("￥%.2f", (int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+getWeightBalance())*newPhonegoodsdetailResult.getGramprice()));
					}
					main_weight.setChecked(false);
					nomain_weight.setChecked(true);
					tv_total_weigth.setText((int)(arrayList.get(0).getCount()*arrayList.get(0).getIncrementweight()+arrayList.get(0).getSidecourseminweight()-arrayList.get(0).getIncrementweight())+"");
				}
			}else {
				if (result.getIsmaincourse()==1) {
					nomain_weight.setVisibility(View.INVISIBLE);
				}else {
					nomain_weight.setChecked(false);
				}
				isCheckedMain=1;
				main_weight.setChecked(true);
				tv_total_weigth.setVisibility(View.GONE);
				tv_jian.setVisibility(View.GONE);
				ll_total.setVisibility(View.GONE);
			}
    	if(result.getStockNum()<=0){
    		tv_jia.setEnabled(false);
    		//addToCartButton.setBackgroundResource(R.drawable.weiwei_enabled_false);
    	}
    	
				tv_weight.setText(Html.fromHtml("规格：<font color='#333333'>"+(int)result.getWeight()+"g/份</font>"));
			
            productDetailTitle.setText(result.getGoodsName());
            String name="";
            if (result.getUnit().getIsweightunit()==1) {
            	name="/500"+result.getUnit().getName();
			}else {
				name="/"+result.getUnit().getName();
			}
            weight_unit.setText(name);
            if (result.getBusinessGoodsDiscountObject().getDiscountType()==1) {
            	productDetailMarketPrice.setVisibility(View.VISIBLE);
            	productDetailMarketPrice.setText(String.format("原价：￥%.2f",result.getShopPrice())+name);
            	productDetailMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        		productDetailShopPrice.setText(String.format("%.2f", result.getBusinessGoodsDiscountObject().getDiscountprice()));
        		tv_pricetype.setText("特价：");
			}else if (result.getBusinessGoodsDiscountObject().getDiscountType()==2) {
				productDetailMarketPrice.setVisibility(View.VISIBLE);
            	productDetailMarketPrice.setText(String.format("原价：￥%.2f",result.getShopPrice())+name);
            	productDetailShopPrice.setText(String.format("%.2f", result.getBusinessGoodsDiscountObject().getDiscountprice()));
        		tv_pricetype.setText("余额支付价：");
			}
            else {
		   		tv_pricetype.setText("价格：");
				productDetailMarketPrice.setVisibility(View.GONE);
				productDetailShopPrice.setText(String.format("%.2f", result.getShopPrice()));
			}
            
          
            String description = result.getDescriptionimage();
            if (!TextUtils.isEmpty(description)) {
                List<String> imageList = getImgStr(description,result.getImageUrl());
                fragmentProductDetailImageList.setUrls(imageList);
                fragmentProductDetailImageList.updateUI();
            }

           
            productDetailSaleCountAndShip.setText(Html.fromHtml("销量：<font color='#333333'>"+ result.getSalecount()+"次</font>"));
		
        if (result.getImagelist()!= null) {
            List<AdEntity> adEntityList = new ArrayList<AdEntity>();
            for (Icon productImage : result.getImagelist()) {
                AdEntity adEntity = new AdEntity();	
                String imgUrl=productImage.getIcon();
                
             
                adEntity.setBody(imgUrl);

                adEntityList.add(adEntity);
            }
            bannerFragment.setBannerEntityList(adEntityList);
        }
        goodsdescription.setText(result.getGoodsdescription());
        
        if (result.getIsback()==0) {
			back.setVisibility(View.VISIBLE);
		}else {
			back.setVisibility(View.GONE);
		}
       for (int i = 0; i < result.getIconList().size(); i++) {
    	   Speciality speciality = result.getIconList().get(i);
    	   if (!speciality.getName().equals("不可退货")) {
    		   View view = View.inflate(activity, R.layout.product_speciality_item, null);
               ImageView img= (ImageView) view.findViewById(R.id.img);
               finalBitmap.display(img, speciality.getIcon());
               TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
               tv_name.setText(speciality.getName());
               ll_speciality.addView(view);
		}
    	  
           
       }
        
      
       // productDetailStockNumber.setText(Html.fromHtml("库存：<font color='#333333'>"+ result.getStockNum()/1000+"公斤"+"</font>"));
       double num= (float)result.getStockNum()/1000;
       // productDetailStockNumber.setText(Html.fromHtml("库存：<font color='#333333'>"+ result.getStockNum()/1000+"公斤"+"</font>"));
       if (result.getUnit().getIsweightunit()==0) {
    	   productDetailStockNumber.setText("库存："+result.getStockNum()+result.getUnit().getName());
	}else {
		 productDetailStockNumber.setText("库存："+String.format("%.2f", num)+"公斤");
	}
       
     /*   myadapter = new Myadapter(result.getAttrModelList(), activity,result.getDefaultGoods().getAttrvalueid());
        gd.setAdapter(myadapter);
        gd.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int gid=(Integer) myadapter.getItem(position);
				if (result.getDefaultGoods().getGid()==gid) {
					return;
				}
				Intent intent=new Intent(activity, HomeHeaderActivity.class);
                 intent.putExtra("productId",gid);
                intent.putExtra("type",HeaderActivityType.ProductDetail.ordinal());
                startActivity(intent);
                 activity.finish();
			}
		});*/
    
        checkAddress();
    }

    private boolean checkUrl(String url){
        String scechm="http";
        return (url.startsWith(scechm));
    }

/*    class Myadapter extends BaseAdapter{
    	private ArrayList<Goodsattr> list;
    	private Context contex;
    	private int gid;
		public Myadapter(ArrayList<Goodsattr> list, Context contex, int gid) {
			super();
			this.list = list;
			this.contex = contex;
			this.gid=gid;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position).getGid();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = View.inflate(contex, R.layout.attr_item, null);
			TextView tv = (TextView) convertView.findViewById(R.id.tv_name);
			tv.setText(list.get(position).getGoodsAttrValue());
			if (list.get(position).getGid()==gid) {
				tv.setBackgroundResource(R.drawable.weiwei_attr_bg);
				tv.setTextColor(getResources().getColor(R.color.white));
			}else {
				tv.setTextColor(getResources().getColor(R.color.weiwei_shenlvse));
				tv.setBackgroundResource(R.drawable.weiwei_attr_bg_unchecked);
			}
			return convertView;
		}
    	
    }*/
    /**
     * 得到网页中图片的地址
     * @param img 
     */
    public  List<String> getImgStr(String htmlStr, String imgu) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList<String>();

        String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src
            while (m.find()) {
                String imgUrl=m.group(1);
                if(!checkUrl(imgUrl)){
                    pics.add(imgu + imgUrl);
                }else{
                    pics.add(imgUrl);
                }
               //pics.add(imgu+imgUrl);
            }
        }
        return pics;
    }

}
