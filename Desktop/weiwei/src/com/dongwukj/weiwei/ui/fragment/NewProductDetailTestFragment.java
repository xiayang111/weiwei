package com.dongwukj.weiwei.ui.fragment;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.FragmentTabAdapter;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.AddAttentionrequest;
import com.dongwukj.weiwei.idea.request.AddToCartRequest;
import com.dongwukj.weiwei.idea.request.PhonegoodsdetailRequest;
import com.dongwukj.weiwei.idea.result.*;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.widget.*;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunjaly on 15/4/20.
 */
public class NewProductDetailTestFragment extends AbstractLoadingFragment implements View.OnClickListener {

    private Point addBagButtonPoint = new Point();
    private Point bagNumPoint = new Point();
    private LinearLayout bottomBarLayout;
    // private DetailBottomBarPanel bottomBarPanel;
    private View bottomBarPanel;
    private TextView brandName;
    private Animator cartPopAnimation;
    private View footer;
    private int frame_height;
    private ExpandableScrollView framework;
    private View goTop;
    private Handler handler = new Handler();
    private ImageView ivRedPop;
    private View mFaushLayout;
    private BorderScrollView mScrollProduct;
    private int max_item = 0;
    private BorderScrollView more_detail;
    private ImageView product_detail_btn_titletop;
    private int threshold = 0;
    private boolean clickable = true;


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
    private int productId = 0;


    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_detail, container, false);
        productId = getActivity().getIntent().getIntExtra("productId", -1);     //商品ID, 上一个页面发送来的.
        //this.initUI();
        return view;
    }

    @Override
    protected void findViews(View v) {
        baseApplication = (BaseApplication) getActivity().getApplication();

        bottomBarPanel = View.inflate(getActivity(), R.layout.product_detail_foot, null);
        this.ivRedPop = (ImageView) v.findViewById(R.id.ivRedPop);
        // Configure.init(this);
        this.brandName = (TextView) v.findViewById(R.id.brandName);
        this.mScrollProduct = (BorderScrollView) v.findViewById(R.id.detail_layout);
        this.footer = new View(getActivity());
        LinearLayout viewGroup = (LinearLayout) this.mScrollProduct.getChildAt(0);
        if (viewGroup != null) {
            viewGroup.addView(this.footer);
        }
        //this.mScrollProduct.addFooterView(this.footer);
        if (Build.VERSION.SDK_INT <= 10) {
            this.footer.setBackgroundColor(-1);
        }

        this.more_detail = (BorderScrollView) v.findViewById(R.id.more_detail);
        this.framework = (ExpandableScrollView) v.findViewById(R.id.detail_framework);
        this.framework.setBackgroundColor(-1);
        this.product_detail_btn_titletop = (ImageView) v.findViewById(R.id.product_detail_btn_titletop);
        this.product_detail_btn_titletop.setClickable(true);
        this.product_detail_btn_titletop.setOnClickListener(this);
        this.goTop = v.findViewById(R.id.go_top);
        this.goTop.setOnClickListener(this);
        this.resetFakeFooterHeight(v);
        this.mFaushLayout = v.findViewById(R.id.faush_layout);
        this.removeScrollViewShadow();
        this.framework.setNormalView(this.mScrollProduct);
        this.bottomBarLayout = (LinearLayout) v.findViewById(R.id.product_foot_layout);
        bottomBarLayout.addView(bottomBarPanel);
        performDetailHtml(v);


        productDetailSaleCountAndShip = (TextView) v.findViewById(R.id.product_detail_salecountandship);
        productDetailStockNumber = (TextView) v.findViewById(R.id.product_detail_stockNumber);
        productDetailCommentHeader = (TextView) v.findViewById(R.id.product_detail_comment_header);

        productDetailPanel = (RelativeLayout) v.findViewById(R.id.main_layout);

        addToCartButton = (Button) bottomBarPanel.findViewById(R.id.product_detail_add_cart);//添加到购物车
        addToCartButton.setOnClickListener(this);                                         //添加到购物车监听

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
                showShare(false, null, true);
			}
		});
        attention.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (clickable) {
                    boolean login = isLogin();
                    if (login) {
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
                getActivity().finish();
            }
        });
        productCommentListView = (SubListView) v.findViewById(R.id.product_detail_comment_list);
        productReviewList = new ArrayList<ProductReview>();
        commentListAdapter = new CommentListAdapter(productReviewList);
        productCommentListView.setAdapter(commentListAdapter);


        initFragment(v);

        updateDataHanlder.sendEmptyMessage(100);
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
        Context context = getActivity();
        final OnekeyShare oks = new OnekeyShare();

        //oks.setAddress("12345678901");
        oks.setTitle("分享标题");
        oks.setTitleUrl( "http://www.baidu.com");
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

        oks.setUrl("http://www.baidu.com");
        //oks.setFilePath(CustomShareFieldsPage.getString("filePath", MainActivity.TEST_IMAGE));
        oks.setComment("");
        oks.setSite( context.getString(R.string.app_name));
        oks.setSiteUrl("http://www.baidu.com");
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
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
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
		 ShareSDK.initSDK(getActivity());
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 
		 
		 
		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle(getString(R.string.share));
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
		 oks.show(getActivity());
		 }
    private void initFragment(View v) {
        bannerFragment = (BannerFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.product_detail_banner);
        fragmentProductDetailImageList = new FragmentProductDetailImageList(getActivity());
        fragmentProductDetailAttributes = new FragmentProductDetailAttributes();
        fragments.add(fragmentProductDetailImageList);
        fragments.add(fragmentProductDetailAttributes);
        rgs = (RadioGroup) v.findViewById(R.id.tabs_rg);

        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(getActivity(), fragments, R.id.product_detail_tab_content, rgs,0);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                System.out.println("Extra---- " + index + " checked!!! ");
            }
        });

    }


    private void initScrollFramework(View v) {
        this.framework.setExpandedView(this.more_detail);
        this.framework.setDivider(v.findViewById(R.id.expandable_scroll_divider));
        final DetailScrollTopIndicator detailScrollTopIndicator = (DetailScrollTopIndicator) v.findViewById(R.id.top_indicator);
        final DetailScrollBottomIndicator detailScrollBottomIndicator = (DetailScrollBottomIndicator) v.findViewById(R.id.bottom_indicator);
        this.framework.setOnPullListener(new ExpandableScrollView.OnPullListener() {
            public void onPulled(boolean var1x, double var2x) {
                if (NewProductDetailTestFragment.this.framework.isEnabled()) {
                    if (var1x) {
                        detailScrollBottomIndicator.setVisibility(View.VISIBLE);
                        detailScrollTopIndicator.setVisibility(View.GONE);
                        detailScrollBottomIndicator.onPulled(var2x);
                    } else {
                        detailScrollBottomIndicator.setVisibility(View.GONE);
                        detailScrollTopIndicator.setVisibility(View.VISIBLE);
                        detailScrollTopIndicator.onPulled(var2x);
                    }
                }
            }

            public void onReleased(boolean var1x) {
                if (NewProductDetailTestFragment.this.framework.isEnabled()) {
                    if (var1x) {
                        detailScrollBottomIndicator.setVisibility(View.VISIBLE);
                        detailScrollTopIndicator.setVisibility(View.GONE);
                        detailScrollBottomIndicator.onReleased();
                        NewProductDetailTestFragment.this.goTop.setVisibility(View.INVISIBLE);
                    } else {
                        detailScrollBottomIndicator.setVisibility(View.GONE);
                        detailScrollTopIndicator.setVisibility(View.VISIBLE);
                        detailScrollTopIndicator.onReleased();
                        NewProductDetailTestFragment.this.goTop.setVisibility(View.VISIBLE);
                        //NewProductDetailActivity.this.cpMoreDetailPage();
                    }
                }
            }

            public void onSilentReset() {
                if (NewProductDetailTestFragment.this.framework.isEnabled()) {
                    detailScrollBottomIndicator.setVisibility(View.GONE);
                    detailScrollBottomIndicator.onReleased();
                    detailScrollTopIndicator.setVisibility(View.GONE);
                    detailScrollTopIndicator.onReleased();
                }
            }
        });
        this.mScrollProduct.setOnBorderListener(new BorderScrollView.OnBorderListener() {
            @Override
            public void onBottom(boolean isBottom) {
                if (NewProductDetailTestFragment.this.framework.isEnabled()) {
                    byte var2x;
                    if (isBottom) {
                        var2x = 0;
                    } else {
                        var2x = 8;
                    }

                    detailScrollBottomIndicator.setVisibility(var2x);
                    detailScrollBottomIndicator.onReleased();
                }
            }

            @Override
            public void onTop(boolean isStart) {

            }
        });

        this.more_detail.setOnBorderListener(new BorderScrollView.OnBorderListener() {
            @Override
            public void onBottom(boolean isBottom) {

            }

            @Override
            public void onTop(boolean isStart) {
                byte var2;
                if (isStart) {
                    var2 = 0;
                } else {
                    var2 = 8;
                }

                detailScrollTopIndicator.setVisibility(var2);
                detailScrollTopIndicator.onReleased();
            }
        });
    }

    @Override
    protected void reload() {
        startLoading();
        updateDataHanlder.sendEmptyMessage(100);
    }

    protected void showdialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
        View view = View.inflate(getActivity(), R.layout.ordercancle_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("是否立即登录？");
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();

    }


    protected void addAttention() {
        BaseRequestClient client = new BaseRequestClient(getActivity());
        UserResult result = baseApplication.getUserResult();
        AddAttentionrequest request = new AddAttentionrequest();
        request.setpId(productId);
        client.httpPostByJson("Phoneattentionadd", result, request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

            @Override
            public void callBack(BaseResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        attention.setImageResource(R.drawable.weiwei_favorite_selected_button);
                        clickable = false;
                    } else {
                        Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void loading(long count, long current) {

            }
            @Override
            public void logOut(boolean isLogOut,BaseResult result) {FinalDb finalDb=FinalDb.create(activity);
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


    private void performDetailHtml(View v) {

        //this.more_detail.setData("1", "2", null);
        this.initScrollFramework(v);
        this.framework.setEnabled(true);
    }


    @TargetApi(9)
    private void removeShadow() {
        this.mScrollProduct.setOverScrollMode(2);
    }

    private void resetFakeFooterHeight(View v) {
        this.mScrollProduct.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (NewProductDetailTestFragment.this.bottomBarPanel != null) {
                    View var1 = NewProductDetailTestFragment.this.bottomBarPanel;
                    if (var1 != null && var1.getHeight() > 0) {
                        NewProductDetailTestFragment.this.frame_height = getActivity().findViewById(R.id.main_layout).getHeight() - var1.getHeight();
                        NewProductDetailTestFragment.this.framework.setVisibleHeight(NewProductDetailTestFragment.this.frame_height, 0);
//                        if(NewProductDetailActivity.this.presenter.needShowCartLayout()) {
//                            NewProductDetailActivity.this.showFloatWindow(var1.getHeight());
//                        }

                        int var2;
                        if (NewProductDetailTestFragment.this.framework.isEnabled()) {
                            int var3 = NewProductDetailTestFragment.this.getResources().getDimensionPixelSize(R.dimen.detail_indicator_normal_height);
                            var2 = 0 + var3;
                            //NewProductDetailTestActivity.this.more_detail.setVerticalOffset(NewProductDetailTestActivity.this.getResources().getDimensionPixelSize(R.dimen.detail_indicator_total_height), var2);
                        } else {
                            var2 = 0 + NewProductDetailTestFragment.this.goTop.getHeight();
                        }

                        NewProductDetailTestFragment.this.footer.setLayoutParams(new LinearLayout.LayoutParams(-1, var2));
                        NewProductDetailTestFragment.this.mScrollProduct.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_evaluate:
                int pmId = 1;
                String title = "商品评价";
                openNewActivityWithHeader(HeaderActivityType.Evaluate.ordinal(), pmId, title);
                break;

            case R.id.product_detail_add_cart:
                boolean login = isLogin();
                if (login) {
                    addToCart();
                } else {
                    showdialog();
                }

                break;
            default:
                break;
        }
    }

    private void openNewActivityWithHeader(int type, int pmId, String title) {
        Intent intent = new Intent(getActivity(), HomeHeaderActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("pmId", pmId);
        intent.putExtra("title", title);
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
    private void addToCart() {
        BaseRequestClient client = new BaseRequestClient(getActivity());
        UserResult userResult = baseApplication.getUserResult();
        AddToCartRequest request = new AddToCartRequest();
        request.setGoodsId(productId);
        request.setGoodsNum("1");
        client.httpPostByJson("PhoneAddProduct", userResult, request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

            @Override
            public void callBack(BaseResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        Toast.makeText(getActivity(), "添加购物车成功!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void loading(long count, long current) {
                // TODO Auto-generated method stub

            }
            @Override
            public void logOut(boolean isLogOut,BaseResult result) {FinalDb finalDb=FinalDb.create(activity);
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


    public boolean onKeyDown(int var1, KeyEvent var2) {
        if (var1 == 4) {
            getActivity().finish();
        }

        return true;
    }


    public void removeScrollViewShadow() {
        try {
            if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
                this.removeShadow();
            }

        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }


    private class QueryButtonOnClickLisenter implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            setBackground();
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            });
        }
    }

    private void setBackground() {
        backgroundView = new View(getActivity());
        backgroundView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        backgroundView.setBackgroundResource(R.color.sku_out_bg_shadow);
        productDetailPanel.addView(backgroundView);
    }


    private Handler updateDataHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            fetchData();
        }
    };
    private LinearLayout ll_evaluate;
    private ImageButton attention;
	private ImageButton shared;

    private void fetchData() {
        BaseRequestClient client = new BaseRequestClient(getActivity());
        UserResult userResult = baseApplication.getUserResult();
        PhonegoodsdetailRequest phonegoodsdetailRequest = new PhonegoodsdetailRequest();
        phonegoodsdetailRequest.setGid(productId);
        client.httpPostByJson("Phonegoodsdetail", userResult, phonegoodsdetailRequest, PhonegoodsdetailResult.class, new BaseRequestClient.RequestClientCallBack<PhonegoodsdetailResult>() {
            @Override
            public void callBack(PhonegoodsdetailResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        endLoading();
                        fillContent(result);


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
            public void logOut(boolean isLogOut,PhonegoodsdetailResult result) {FinalDb finalDb=FinalDb.create(activity);
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


    private void fillContent(PhonegoodsdetailResult result) {
        ProductDetailEntity productDetailEntity = result.getProduct();
        if (productDetailEntity != null) {
            productDetailTitle.setText(productDetailEntity.getName());
            productDetailShopPrice.setText(String.format("￥%.2f", productDetailEntity.getShopPrice()));
            productDetailMarketPrice.setText(String.format("￥%.2f", productDetailEntity.getMarketPrice()));
            productDetailMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            String description = productDetailEntity.getDescriptions();
            if (!TextUtils.isEmpty(description)) {
                List<String> imageList = getImgStr(description);
                fragmentProductDetailImageList.setUrls(imageList);
                fragmentProductDetailImageList.updateUI();
            }

            productDetailSaleCountAndShip.setText(String.format("运费：%.2f元   月销%d笔", result.getShippingCosts(), productDetailEntity.getSaleCount()));
            //productDetailCommentHeader.setText(Html.fromHtml(String.format("商品评价(%d人评论<font color=\\\"red\\\">%s</font>好评)", productDetailEntity.getReviewCount(), (int) (productDetailEntity.getGoodReviewRate() * 100) + "%")));
            productDetailCommentHeader.setText(Html.fromHtml("商品评价(" + productDetailEntity.getReviewCount() + "人评论<font color='red'>" + (int) (productDetailEntity.getGoodReviewRate() * 100) + "%</font>好评)"));

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
            finalBitmap = FinalBitmap.create(getActivity());
            finalBitmap.configLoadfailImage(R.drawable.default_small);
            finalBitmap.configLoadingImage(R.drawable.default_small);

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
                convertView = View.inflate(getActivity(), R.layout.fragment_comment_list_item, null);
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
            if(!NetworkUtil.checkUrl(imageUrl)){
                imageUrl=BaseApplication.BASE_IMAGE_HOST+imageUrl;
            }
            finalBitmap.display(viewHolder.icon, imageUrl);
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
