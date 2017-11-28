package com.dongwukj.weiwei.ui.fragment;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.dongwukj.weiwei.R.id;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.AddToCartRequest;
import com.dongwukj.weiwei.idea.request.EvaluateRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.EvaluateResult;
import com.dongwukj.weiwei.idea.result.EvaluateResult.EvaluateEntity;
import com.dongwukj.weiwei.idea.result.PhoneAddProductResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.ShoppingCartRequestClient;
import com.dongwukj.weiwei.net.ShoppingCartRequestClient.AddShoppingCartRequestClientCallback;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.widget.BadgeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EvaluateFragment extends AbstractHeaderFragment  {
	protected static final int PULL_DOWN_MODE = 101;
	protected static final int PULL_UP_MODE = 100;
	private View view;
	private String title;
	private int pmId;
	private List<EvaluateEntity> list=new ArrayList<EvaluateResult.EvaluateEntity>();
	private PullToRefreshListView pf;
	private MyBaseAdapter adapter;
	private int totalReview;    //商品评价总计总计
	private FinalBitmap fb;
	
	
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_evaluate, null);
		fb = FinalBitmap.create(activity);
        fb.configLoadingImage(R.drawable.default_small);
        fb.configLoadfailImage(R.drawable.default_small);
		return view;
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
	@Override
	public void onResume() {
		buyNum=baseApplication.getCartCount();
    	setBadgeViewText(buyNum+"");
		super.onResume();
	}
	@Override
	protected String setTitle() {
		title = activity.getIntent().getStringExtra("title");
		pmId = activity.getIntent().getIntExtra("pmId", 12);
		reviewCount = activity.getIntent().getStringExtra("reviewCount"); //商品详情 界面传递过来的  该商品的评价总数
		return title;
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
		tv_total_review = (TextView) v.findViewById(R.id.tv_total_review);
		
		cart_image=(ImageView)v.findViewById(R.id.iv_cart);
		 cart_image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					openNewActivityWithHeader(HeaderActivityType.AddShopCart.ordinal(), true, false);
					
				}
			});
        buyNumView = new BadgeView(activity, cart_image);
        buyNumView.setTextColor(Color.WHITE);
        buyNumView.setTextSize(10);
        buyNum=baseApplication.getCartCount();
        setBadgeViewText(buyNum+"");
		bt_addCart = (Button) v.findViewById(R.id.bt_addCart);
		bt_addCart.setOnClickListener(new OnClickListener() {
			
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
		pf = (PullToRefreshListView) view.findViewById(id.lv);
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
		adapter = new MyBaseAdapter();
		pf.setAdapter(adapter);
		pf.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadDataHandler.sendEmptyMessage(PULL_DOWN_MODE);
				
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadDataHandler.sendEmptyMessage(PULL_UP_MODE);
				
			}
		});
		loadDataHandler.sendEmptyMessageDelayed(102, 500);
        /*pf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EvaluateEntity evaluateEntity=list.get(position);
                Intent intent=new Intent(activity, NewProductDetailActivity.class);
                intent.putExtra("productId",evaluateEntity.getpId());
                startActivity(intent);

                Intent intent=new Intent(activity, HomeHeaderActivity.class);
                intent.putExtra("productId",evaluateEntity.getpId());
                intent.putExtra("type", HeaderActivityType.ProductDetail.ordinal());
                intent.putExtra("hasHeader",false);
                startActivity(intent);  		//跳转到商品详情
            }
        });*/
	}
	private Handler loadDataHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PULL_DOWN_MODE:
				getData(true);
				break;
			case PULL_UP_MODE:
				getData(false);
				break;
			case 102:
				pf.setRefreshing();
				break;

			default:
				break;
			}
		};
	};
	private int Pagetype=1;
	protected int bigSize;
	private TextView tv_total_review;
	private Button bt_addCart;
	private void getData(final boolean isFirst) {
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult userResult=baseApplication.getUserResult();
		EvaluateRequest request=new EvaluateRequest();
		request.setAddNum(10);
		progressDialog.setMessage("获取商品评价...");
		showProgress(true);
		request.setpId(pmId);
		if (isFirst) {
			request.setPageIndex(1);
		}else {
			request.setPageIndex(++Pagetype);
		}
		client.httpPostByJson("Phonecomments", userResult, request, EvaluateResult.class, new BaseRequestClient.RequestClientCallBack<EvaluateResult>() {
			
			

			@Override
			public void callBack(EvaluateResult result) {
				if (result!=null) {
					if (BaseResult.CodeState.Success.getCode()==result.getCode()) {
						if (result.getProductReviews()==null||result.getProductReviews().size()==0) {
							tv_total_review.setText("商品暂无评价!");
							pf.onRefreshComplete();
							showProgress(false);
							return;
						}else {
							/*
							 * 
							int size = result.getProductReviews().size();
							if( size <10){
								totalReview = size ;
							}else{
								totalReview = 10 * result.getListNumber();
							}
							tv_total_review.setText("全部评价:("+totalReview+")");*/
							
							//将从商品详情界面传递来的 评价总数写入
							tv_total_review.setText("全部评价:("+reviewCount+")");
							if (isFirst) {
								bigSize=result.getListNumber();
								list.clear();
								adapter.notifyDataSetChanged();
								list.addAll(result.getProductReviews());
								adapter.notifyDataSetChanged();
								Pagetype=1;
								pf.setMode(Mode.BOTH);
							}else {
								list.addAll(result.getProductReviews());
								adapter.notifyDataSetChanged();
							}
						}
					}else {
						 Toast.makeText(activity,result.getMessage(),Toast.LENGTH_SHORT).show();
					}
				}else {
					 Toast.makeText(activity,getResources().getString(R.string.data_error),Toast.LENGTH_SHORT).show();
				}
				pf.onRefreshComplete();
				if (bigSize<=list.size()) {
					pf.setMode(Mode.PULL_FROM_START);
				}
				showProgress(false);
			}

			@Override
			public void loading(long count, long current) {
				
			}
			@Override
			public void logOut(boolean isLogOut,EvaluateResult result) {FinalDb finalDb=FinalDb.create(activity);
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
	private class MyBaseAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder =null;
			if(convertView==null){
				holder = new ViewHolder();
				convertView = View.inflate(activity, R.layout.evaluate_item, null);
				
				holder.iv_user = (ImageView) convertView.findViewById(R.id.iv_user);
				holder.tv_pname = (TextView) convertView.findViewById(R.id.tv_pname);
				holder.tv_reviewtime = (TextView) convertView.findViewById(R.id.tv_reviewtime);
				holder.tv_review = (TextView) convertView.findViewById(R.id.tv_review);
				holder.rb_star = (RatingBar) convertView.findViewById(R.id.rb_star);
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			String avatar = list.get(position).getAvatar();
			/*if(!NetworkUtil.checkUrl(avatar)){
				avatar= BaseApplication.BASE_IMAGE_HOST+avatar;
			}
			fb.display(holder.iv_user, avatar);	*/								//用户头像
			  String[] split = avatar.split("/");
	            try {
					InputStream is = activity.getAssets().open(split[split.length-1].toString().trim());
					Bitmap b=BitmapFactory.decodeStream(is);
					holder.iv_user.setImageBitmap(b);
				} catch (IOException e) {
					holder.iv_user.setImageResource(R.drawable.weiwei_unlogin_icon);
				}
			holder.tv_pname.setText(list.get(position).getNickName());   		//用户名
			holder.tv_reviewtime.setTag(list.get(position).getReviewTime());	//评价时间
			holder.tv_review.setText(list.get(position).getMessage());			//评价内容
			
			float star = list.get(position).getStar();   						   //评价星级
			holder.rb_star.setRating(star);
			return convertView;
		}
		
	}
	
	class ViewHolder{
		ImageView iv_user;
		TextView tv_pname;
		TextView tv_reviewtime;
		RatingBar rb_star;
		TextView tv_review;
		
	}

	
	private int buyNum = 0;//购买数量
    private BadgeView buyNumView;//显示购买数量的控件
    private ViewGroup anim_mask_layout;//动画层
    private ImageView buyImg;// 这是在界面上跑的小图片
    private ImageView cart_image;
	
	private void addComBoToCart(final View v) {

			AddToCartRequest request = new AddToCartRequest();
	        request.setGoodsNum("1");
	        request.setGoodsId(pmId);
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

	    final float a = -1f / 75f;
		private String reviewCount;

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
	               /* buyNumView.setText(buyNum + "");//
	                buyNumView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
	                buyNumView.show();*/
	                setBadgeViewText(buyNum+"");
	            }
	        });

	    }
}
