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
import android.widget.ImageView.ScaleType;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.FragmentTabAdapter;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.AddAttentionrequest;
import com.dongwukj.weiwei.idea.request.AddToCartRequest;
import com.dongwukj.weiwei.idea.request.NewAddressRequest;
import com.dongwukj.weiwei.idea.request.PhonegoodsdetailRequest;
import com.dongwukj.weiwei.idea.result.*;
import com.dongwukj.weiwei.idea.result.NewAddressResult.NewAddressEntity;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.net.ShoppingCartRequestClient;
import com.dongwukj.weiwei.net.ShoppingCartRequestClient.AddShoppingCartRequestClientCallback;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.widget.BadgeView;
import com.dongwukj.weiwei.ui.widget.SubListView;
import com.dongwukj.weiwei.ui.widget.TestPullLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.umeng.analytics.MobclickAgent;

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


public class OldNewProductDetailFragment extends AbstractLoadingFragment implements View.OnClickListener {

    PullToRefreshScrollView contentView;
    PullToRefreshScrollView detailView;
    TestPullLayout testPullLayout;
    RelativeLayout product_detail_content_rl;


    private SubListView productCommentListView;
    private CommentListAdapter commentListAdapter;
    private List<ProductReview> productReviewList;

    private ImageButton fragmentProductDetailImageButton;
    private BaseApplication baseApplication;

    private TextView productDetailTitle;
    private TextView productDetailShopPrice;
    private TextView productDetailMarketPrice;
  
    private View popUpView;
    private PopupWindow popupWindow;
    private View backgroundView;
    private RelativeLayout productDetailPanel;

    private View querySuyuanButton;
    private Button addToCartButton;

    public List<Fragment> fragments = new ArrayList<Fragment>();
    private FragmentProductDetailImageList fragmentProductDetailImageList;
    private FragmentProductDetailAttributes fragmentProductDetailAttributes;
    private RadioGroup rgs;
    private BannerFragment bannerFragment;
    private TextView productDetailSaleCountAndShip;
    private TextView productDetailStockNumber;
    private TextView productDetailCommentHeader;
    private int productId=0;
    private boolean clickable = true;
	private List<NewAddressEntity> lists=new ArrayList<NewAddressEntity>();

    private RelativeLayout cart_content;

    private int buyNum=0;//购买数量
    private BadgeView buyNumView;//显示购买数量的控件
    private ViewGroup anim_mask_layout;//动画层
    private ImageView buyImg;// 这是在界面上跑的小图片
    private ImageView cart_image;

    private int percent ;
   
    
    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_pull_layyout, container, false);
        productId = activity.getIntent().getIntExtra("productId", -1);     //商品ID, 上一个页面发送来的.
        return view;
    }

    @Override
    public void onResume() {
    	//MobclickAgent.onPageStart("NewProductDetailFragment");
    	buyNum=application.getCartCount();
    	setBadgeViewText(buyNum+"");
    	

/*    		需求更改,改代码暂时注释
 * 			if(addToCartButton!=null &&tv_address!=null){
    		SharedPreferences sp = activity.getSharedPreferences("Enable", Activity.MODE_PRIVATE);
        	boolean enable = sp.getBoolean("isAddress", false);
        	if(enable){//把配送地址的数据写入 enable
        		tv_address.setText(areaEntity.getProvince()+"-"+areaEntity.getCity()+"-"+areaEntity.getArea());
        		if(areaEntity.getArea().equals("其它")){
        			addToCartButton.setEnabled(false);
        			addToCartButton.setText("该区暂时无法配送");
        		}else{
        			addToCartButton.setOnClickListener(this);
        			addToCartButton.setEnabled(true);
        		}
        		
        	}else{
        		tv_address.setText("您还没有设置收货地址,请点此设置!");
        		addToCartButton.setBackgroundColor(Color.GRAY);
        	}
    	}*/
    	super.onResume();
    }
    
    @Override
    public void onPause() {
    	// TODO Auto-generated method stub
        //MobclickAgent.onPageEnd("NewProductDetailFragment");
    	super.onPause();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode==100&&resultCode==Activity.RESULT_OK) {
        	tv_address.setText("配送:"+areaEntity.getProvince()+"-"+areaEntity.getCity()+"-"+areaEntity.getArea());
		}
    }
    @Override
    protected void findViews(View v) {
    	
    	areaEntity = AreaEntity.getInstance();
        addToCartButton=(Button)v.findViewById(R.id.addCartButton);
        addToCartButton.setOnClickListener(this);
        bt_update = (Button) v.findViewById(R.id.bt_update);
    	tv_address = (TextView) v.findViewById(R.id.tv_address);    	
    	
    	bt_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//openNewActivityWithHeader(HeaderActivityType.Loucation.ordinal(), false, true);
				 Intent intent = new Intent(activity, HomeHeaderActivity.class);
			        intent.putExtra("type", HeaderActivityType.Loucation.ordinal());
			        intent.putExtra("needLogin", false);
			        intent.putExtra("hasHeader", true);
				startActivityForResult(intent, 100);
			}
		});    	
    	
    	
    	isFromShopcart = activity.getIntent().getBooleanExtra("isFromShopcart", false);
        ll_time = (LinearLayout) v.findViewById(R.id.ll_time);
    	tv_time = (TextView) v.findViewById(R.id.tv_time);
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
        
        cart_content=(RelativeLayout)v.findViewById(R.id.cart_content);

        /*addToCartButton=(Button)v.findViewById(R.id.addCartButton);
        addToCartButton.setOnClickListener(this);*/

        cart_image=(ImageView)v.findViewById(R.id.cart_image);
        cart_image.setOnClickListener(this);

        buyNumView = new BadgeView(activity, cart_image);
        buyNumView.setTextColor(Color.WHITE);
        //buyNumView.setBackgroundColor(Color.RED);
        buyNumView.setTextSize(10);
        application = (BaseApplication) activity.getApplication();
        buyNum=application.getCartCount();
        setBadgeViewText(buyNum+"");
        productDetailPanel = (RelativeLayout) v.findViewById(R.id.main_layout);
        productDetailSaleCountAndShip = (TextView) v.findViewById(R.id.product_detail_salecountandship);
        productDetailStockNumber = (TextView) v.findViewById(R.id.product_detail_stockNumber);
        productDetailCommentHeader = (TextView) v.findViewById(R.id.product_detail_comment_header);

        querySuyuanButton = (View) v.findViewById(R.id.product_detail_query_suyuan);
        querySuyuanButton.setOnClickListener(new QueryButtonOnClickLisenter());

        productDetailTitle = (TextView) v.findViewById(R.id.product_detail_title);
        productDetailShopPrice = (TextView) v.findViewById(R.id.product_detal_shopprice);
        productDetailMarketPrice = (TextView) v.findViewById(R.id.product_detail_marketprice);
        ll_evaluate = (LinearLayout) v.findViewById(R.id.ll_evaluate);
        ll_evaluate.setOnClickListener(this);
        fragmentProductDetailImageButton = (ImageButton) v.findViewById(R.id.fragment_product_detail_back_button);
        attention = (ImageButton) v.findViewById(R.id.attention);
        shared = (ImageButton) v.findViewById(R.id.shared);
        shared.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //showShare();
            	boolean login = isLogin();
                if (login) {
                	 showShare(false, null, true);
                } else {
                    showdialog();
                }
              
            }
        });
        attention.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (clickable) {
                    boolean login = isLogin();
                    if (login) {
                    	if (isExistFavoriteProduct) {
							Toast.makeText(activity, "商品在收藏夹中已经存在", Toast.LENGTH_SHORT).show();
						}
                        addAttention();
                    } else {
                        showdialog();
                    }
                }
            }
        });
        fragmentProductDetailImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        productCommentListView = (SubListView) v.findViewById(R.id.product_detail_comment_list);
        productReviewList = new ArrayList<ProductReview>();
        commentListAdapter = new CommentListAdapter(productReviewList);
        productCommentListView.setAdapter(commentListAdapter);
        initFragment(v);
        updateDataHanlder.sendEmptyMessageDelayed(UPDATE_UI,100);
       // updateDataHanlder.sendEmptyMessage(FETCH_DATA);
        reload();
        getDistrict();
       /* sp = activity.getSharedPreferences("config", 0);
        if (sp.getBoolean("shopcart", true)) {
        	 updateDataHanlder.sendEmptyMessageDelayed(500, 500);
		}
       */
        
    }
    public void showguide() {
    	final Dialog guidedialog=new Dialog(activity, R.style.Dialog);;
    	guidedialog.setCancelable(false);
    	RelativeLayout layout=new RelativeLayout(activity);
    	layout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT ));
    	int [] xy=new int[2];
    	addToCartButton.getLocationInWindow(xy);
    	int height = addToCartButton.getHeight();
    	int width = addToCartButton.getWidth();
    		android.widget.RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
    		params1.setMargins(0, 0, 0, height);
    		ImageView imageView1=new ImageView(activity);
        	imageView1.setImageResource(R.drawable.shopcart_guide_top);
        	imageView1.setLayoutParams(params1);
        	params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        	params1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        	layout.addView(imageView1);
        	ImageView imageView=new ImageView(activity);
        	imageView.setBackgroundResource(R.drawable.shopcart_guide_bottom);
        	android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(width*1.2),(int)(height*1.2));
        	params.setMargins(xy[0]-(int)(width*0.1),xy[1]-getTitleHeight(activity)-getStateHeight(activity)-(int)(height*0.1), 0, 0);
        	imageView.setLayoutParams(params);
        	layout.addView(imageView);
        	guidedialog.setContentView(layout);
    	
    	WindowManager m = activity.getWindowManager();
        Window dialogWindow = guidedialog.getWindow();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight()); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() ); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
		guidedialog.show();
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sp.edit().putBoolean("shopcart", false).commit();
				addToCartButton.setVisibility(View.VISIBLE);
				guidedialog.dismiss();
			}
		});
		
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
	private void getDistrict() {
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		NewAddressRequest request=new NewAddressRequest();
		
		MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(activity,baseApplication);
		myWeiWeiRequestClient.regionsList(request, new MyWeiWeiRequestClient.RegionsRequestCallback() {
			@Override
			protected void list(NewAddressResult result) {
				if (getActivity()==null) {
					return;
				}
				//lists = result.getRegions();
				lists.addAll(result.getRegions());
				 if(lists.size()<=1){
			        	if(lists.size()==0){
			        		tv_address.setText("地址请求失败,请检查网络!");
			        	}else{
			            	bt_update.setVisibility(View.GONE);
			            	areaEntity.setArea(lists.get(0).getName());
			            	tv_address.setText("配送:"+areaEntity.getProvince()+areaEntity.getCity()+areaEntity.getArea());
			        	}
			        }
			}
		},false);

		
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


    protected void addAttention() {
        BaseRequestClient client = new BaseRequestClient(activity);
        UserResult result = baseApplication.getUserResult();
        AddAttentionrequest request = new AddAttentionrequest();
        request.setpId(productId);
        client.httpPostByJson("Phoneattentionadd", result, request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

            @Override
            public void callBack(BaseResult result) {
            	if (getActivity()==null) {
					return;
				}
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        attention.setImageResource(R.drawable.weiwei_favorite_selected_button);
                       showtoast("收藏成功",activity);
                        clickable = false;
                    } else if (result.getCode() == 37) {
                    	attention.setImageResource(R.drawable.weiwei_favorite_selected_button);
                        showtoast("收藏成功",activity);
                         clickable = false;
					}else {
                        Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void loading(long count, long current) {

            }
            @Override
            public void logOut(boolean isLogOut,BaseResult result) {
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
            }
        });
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_evaluate:   //给商品评价的线性布局设置点击监听
                int pmId = productId;
                String title = "商品评价";
                
                openNewActivityWithHeader(HeaderActivityType.Evaluate.ordinal(), pmId, title,reviewCount1);
                break;

            case R.id.addCartButton:		//添加商品到购物车
                boolean login = isLogin();
                if (login) {
                    addToCart(v);

                } else {
                    showdialog();
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
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
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
                buyNum++;//让购买数量加1
                /*buyNumView.setText(buyNum+"");
                buyNumView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
                buyNumView.show();*/
                setBadgeViewText(buyNum+"");
                addToCartButton.setEnabled(true);
            	cart_image.setEnabled(true);
        		//addToCartButton.setBackgroundResource(R.drawable.add_cart_button_selector);
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

    private boolean isLogin() {
        UserResult result = baseApplication.getUserResult();
        if (result != null && result.getUserAccount() != null) {
            return true;
        } else {
            return false;
        }
    }


    //添加到购物车方法
    private void addToCart(final View v) {
    	addToCartButton.setEnabled(false);
    	cart_image.setEnabled(false);
		//addToCartButton.setBackgroundResource(R.drawable.weiwei_enabled_false);
       /* BaseRequestClient client = new BaseRequestClient(activity);
        UserResult userResult = baseApplication.getUserResult();
        AddToCartRequest request = new AddToCartRequest();
        request.setGoodsId(productId);
        request.setGoodsNum("1");
        client.httpPostByJson("PhoneAddProduct", userResult, request, PhoneAddProductResult.class, new BaseRequestClient.RequestClientCallBack<PhoneAddProductResult>() {

            @Override
            public void callBack(PhoneAddProductResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        Toast.makeText(activity, "添加购物车成功!", Toast.LENGTH_SHORT).show();
                        int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                        v.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                        buyImg = new ImageView(activity);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
                        if(buyNum%2==0){
                            buyImg.setImageResource(R.drawable.weiwei_add_cart_icon1);// 设置buyImg的图片
                        }else{
                            buyImg.setImageResource(R.drawable.weiwei_add_cart_icon2);// 设置buyImg的图片
                        }

                        setAnim(buyImg, start_location);// 开始执行动画

                    } else {
                        Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void loading(long count, long current) {
                addToCartButton.setEnabled(false);
    	cart_image.setEnabled(false);
            }
        });*/
    	 AddToCartRequest request = new AddToCartRequest();
         request.setGoodsId(productId);
         request.setGoodsNum("1");
         ShoppingCartRequestClient client=new ShoppingCartRequestClient(activity, baseApplication);
         client.addCart(request, new AddShoppingCartRequestClientCallback() {
        	 @Override
        	protected void listSuccess(PhoneAddProductResult result) {
        		 if (getActivity()==null) {
					return;
				}
        		 Toast.makeText(activity, "添加购物车成功!", Toast.LENGTH_SHORT).show();
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
        	 @Override
        	protected void listfaile(PhoneAddProductResult result) {
        		 if (getActivity()==null) {
 					return;
 				}
        		addToCartButton.setEnabled(true);
        	    cart_image.setEnabled(true);
        	}
		});
    }




    private class QueryButtonOnClickLisenter implements View.OnClickListener {
        @Override
        public void onClick(View v) {
          /*  setBackground();
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popUpView = layoutInflater.inflate(R.layout.suyuan_view, null);
            float height = getResources().getDimension(R.dimen.suyuan_popwindow_height);
            popupWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.MATCH_PARENT, (int) height, true);
            popupWindow.setAnimationStyle(R.style.item_sku_animation);//设置淡入淡出动画效果
            popupWindow.setFocusable(true);// 使其聚集
            popupWindow.setOutsideTouchable(true);// 设置允许在外点击消失
            popupWindow.setBackgroundDrawable(new BitmapDrawable()); // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景

            popupWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    productDetailPanel.removeView(backgroundView);
                }
            });*/
        	Toast.makeText(activity, "功能暂未开通", Toast.LENGTH_SHORT).show();
        }
    }

    private void setBackground() {
        backgroundView = new View(activity);
        backgroundView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        backgroundView.setBackgroundResource(R.color.sku_out_bg_shadow);
        productDetailPanel.addView(backgroundView);
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
                	addToCartButton.setVisibility(View.INVISIBLE);
                	showguide();
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

    private LinearLayout ll_evaluate;
    private ImageButton attention;
    private ImageButton shared;
	private LinearLayout ll_time;
	private TextView tv_time;

    private void fetchData() {
        BaseRequestClient client = new BaseRequestClient(activity);
        UserResult userResult = baseApplication.getUserResult();
        PhonegoodsdetailRequest phonegoodsdetailRequest = new PhonegoodsdetailRequest();
        phonegoodsdetailRequest.setGid(productId);
        client.httpPostByJson("PhoneGetGoodsDetail", userResult, phonegoodsdetailRequest, PhonegoodsdetailResult.class, new BaseRequestClient.RequestClientCallBack<PhonegoodsdetailResult>() {
            @Override
            public void callBack(PhonegoodsdetailResult result) {
            	if (getActivity()==null) {
					return;
				}
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
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
            public void logOut(boolean isLogOut,PhonegoodsdetailResult result) {
            	if (getActivity()==null) {
					return;
				}
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
                    failLoading();
                }
            }
        });
    }

    private long time;
    private Handler mHandler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            mHandler.sendEmptyMessageDelayed(0, 1000);
            if (time>=1000) {
                String format = format(time);
                time=time-1000;
                tv_time.setText(format);
            }else {
            	ll_time.setVisibility(View.GONE);
            	addToCartButton.setEnabled(false);
        		addToCartButton.setBackgroundResource(R.drawable.weiwei_enabled_false);
        		addToCartButton.setText("活动结束");
            }
        }

    };
    private boolean isOne=false;
    private SimpleDateFormat sm=new SimpleDateFormat("dd天 HH:mm:ss");
    private SimpleDateFormat sm1=new SimpleDateFormat("HH:mm:ss");
//	private HomeActivity activity;
	private BaseApplication application;
	private boolean isFromShopcart;
	private TextView tv_address;
	private AreaEntity areaEntity;
	private Button bt_update;
	private TextView tv_weight;
	private String reviewCount1;
	private SharedPreferences sp;
	private Boolean isExistFavoriteProduct;
    private String format(long tiem) {
    	Date date=new Date(tiem);
    	 String format ;
    	 sm1.setTimeZone(new SimpleTimeZone(0, "GMT"));
    	 if (tiem>86400000) {
    		 int d=(int) (tiem/86400000);
    		 format = d+"天 "+sm1.format(tiem%86400000);
		}else {
			format=sm1.format(tiem);
		}
         return format;
    };
    private void fillContent(PhonegoodsdetailResult result) {
    	/*
    	 * 判断库存<=0时,让加入购物车按钮不可点击.
    	 */
    	if(result.getStockNumber()<=0){
    		addToCartButton.setEnabled(false);
    		addToCartButton.setBackgroundResource(R.drawable.weiwei_enabled_false);
    	}
    	
        ProductDetailEntity productDetailEntity = result.getProduct();
        reviewCount1 = productDetailEntity.getReviewCount()+"";
        if (productDetailEntity != null) {
        	isExistFavoriteProduct = result.getIsExistFavoriteProduct();
        	if (result.getIsExistFavoriteProduct()) {
        		attention.setImageResource(R.drawable.weiwei_favorite_selected_button);
        		clickable=false;
			}else {
				attention.setImageResource(R.drawable.weiwei_favorite_button);
			
			}
       	if (result.getSinglePromotion()!=null) {
				ll_time.setVisibility(View.VISIBLE);
				addToCartButton.setText("马上抢");
				time = Long.parseLong( result.getSinglePromotion().getEndTime1())-Long.parseLong(result.getTimestamp());
				 if (!isOne) {
                     mHandler.sendEmptyMessage(0);
                     isOne=true;
                 }
					tv_weight.setText(productDetailEntity.getWeight()+"g"+" (每人限购"+result.getSinglePromotion().getAllowBuyCount()+"份)");
			}else {
				tv_weight.setText(productDetailEntity.getWeight()+"g");
				ll_time.setVisibility(View.GONE);
			}
            productDetailTitle.setText(productDetailEntity.getName());
            productDetailShopPrice.setText(String.format("￥%.1f", result.getDiscountPrice()));
            if(result.getSinglePromotion()!=null){
                productDetailMarketPrice.setText(String.format("￥%.1f", productDetailEntity.getShopPrice()));
            }else{
                productDetailMarketPrice.setText(String.format("￥%.1f", productDetailEntity.getMarketPrice()));
            }

            productDetailMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            String description = productDetailEntity.getDescriptions();
            if (!TextUtils.isEmpty(description)) {
                List<String> imageList = getImgStr(description);
                fragmentProductDetailImageList.setUrls(imageList);
                fragmentProductDetailImageList.updateUI();
            }

            productDetailSaleCountAndShip.setText(String.format("运费：%.1f元   月销%d笔", result.getShippingCosts(), productDetailEntity.getSaleCount()));
            //productDetailCommentHeader.setText(Html.fromHtml(String.format("商品评价(%d人评论<font color=\\\"red\\\">%s</font>好评)", productDetailEntity.getReviewCount(), (int) (productDetailEntity.getGoodReviewRate() * 100) + "%")));
            productDetailCommentHeader.setText(Html.fromHtml("商品评价(" + productDetailEntity.getReviewCount() + "人评论<font color='red'>" + (int) (productDetailEntity.getGoodReviewRate() * 1) + "%</font>好评)"));
            
        }
        if (result.getExtProductAttributeGroups() != null) {
            fragmentProductDetailAttributes.setProductAttributeValueList(result.getExtProductAttributeGroups());
            //fragmentProductDetailAttributes.updateUI();
        }
        if (result.getProductImages() != null) {
            List<AdEntity> adEntityList = new ArrayList<AdEntity>();
            for (ProductImage productImage : result.getProductImages()) {
                AdEntity adEntity = new AdEntity();
                String imgUrl=productImage.getShowImg();
                if(!checkUrl(imgUrl)){
                    adEntity.setBody(BaseApplication.BASE_IMAGE_HOST + imgUrl);
                }else{
                    adEntity.setBody(imgUrl);
                }


                adEntityList.add(adEntity);
            }
            bannerFragment.setBannerEntityList(adEntityList);
        }
        productDetailStockNumber.setText(String.format("库存：%d", result.getStockNumber()));

        if (result.getProductReviews() != null) {
            productReviewList.clear();
            productReviewList.addAll(result.getProductReviews());
            commentListAdapter.notifyDataSetChanged();
         }

    }

    private boolean checkUrl(String url){
        String scechm="http";
        return (url.startsWith(scechm));
    }


    /**
     * 得到网页中图片的地址
     */
    public  List<String> getImgStr(String htmlStr) {
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
                    pics.add(BaseApplication.BASE_IMAGE_HOST + imgUrl);
                }else{
                    pics.add(imgUrl);
                }

            }
        }
        return pics;
    }
//重点在于正则表达式 <img.*src=(.*?)[^>]*?>
//               src=\"?(.*?)(\"|>|\\s+)


    private class CommentListAdapter extends BaseAdapter {

        private List<ProductReview> productReviewList;
        private FinalBitmap finalBitmap;

        public CommentListAdapter(List<ProductReview> productReviewList) {
            this.productReviewList = productReviewList;
            finalBitmap = FinalBitmap.create(activity);
            finalBitmap.configLoadfailImage(R.drawable.weiwei_unlogin_icon);
            finalBitmap.configLoadingImage(R.drawable.weiwei_unlogin_icon);

        }

        @Override
        public int getCount() {
            return productReviewList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(activity, R.layout.fragment_comment_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.icon = (ImageView) convertView.findViewById(R.id.comment_list_item_icon);
                viewHolder.userName = (TextView) convertView.findViewById(R.id.comment_list_item_user);
                viewHolder.commentTime = (TextView) convertView.findViewById(R.id.comment_list_item_time);
                viewHolder.commentMessage = (TextView) convertView.findViewById(R.id.comment_list_item_message);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String imageUrl=productReviewList.get(position).getAvatar();
            /*if(!NetworkUtil.checkUrl(imageUrl)){
                imageUrl=BaseApplication.BASE_IMAGE_HOST+imageUrl;
            }
            finalBitmap.display(viewHolder.icon, imageUrl);*/
            String[] split = imageUrl.split("/");
            try {
				InputStream is = activity.getAssets().open(split[split.length-1].toString().trim());
				Bitmap b=BitmapFactory.decodeStream(is);
				viewHolder.icon.setImageBitmap(b);
			} catch (IOException e) {
				viewHolder.icon.setImageResource(R.drawable.weiwei_unlogin_icon);
			}
            viewHolder.userName.setText(productReviewList.get(position).getNickName());
            viewHolder.commentTime.setText(productReviewList.get(position).getReviewTime());
            viewHolder.commentMessage.setText(productReviewList.get(position).getMessage());
            return convertView;
        }

        private class ViewHolder {
            public ImageView icon;
            public TextView userName;
            public TextView commentTime;
            public TextView commentMessage;
        }
    }
}
