package com.dongwukj.weiwei.ui.fragment;

//import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
//import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
//import com.dongwukj.weiwei.idea.request.AddToCartRequest;
import com.dongwukj.weiwei.idea.request.RecommendRequest;
import com.dongwukj.weiwei.idea.result.ComboEntity;
//import com.dongwukj.weiwei.idea.result.PhoneAddProductResult;
import com.dongwukj.weiwei.idea.result.RecommendResult;
//import com.dongwukj.weiwei.idea.result.UserResult;
//import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.HomeRequestClient;
//import com.dongwukj.weiwei.net.ShoppingCartRequestClient;
//import com.dongwukj.weiwei.net.ShoppingCartRequestClient.AddShoppingCartRequestClientCallback;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
//import com.dongwukj.weiwei.ui.activity.LoginActivity;
//import com.dongwukj.weiwei.ui.widget.BadgeView;
import com.dongwukj.weiwei.ui.widget.MyGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunjaly on 15/3/26.
 */
public class RecommendFragment extends ShopCartMessageFragment {
	//private BadgeView buyNumView;
    protected static final int PULL_DOWN_MODE = 100;
    protected static final int PULL_UP_MODE = 101;
    private PullToRefreshScrollView pw;
    private int pagetype=1;
//    private List<AdEntity> list_vp=new ArrayList<AdEntity>();
    private List<ComboEntity> list=new ArrayList<ComboEntity>();
   // private BannerFragment bf;
    private int bigSize;
    private MyBaseAdapter adapter;
    private FinalBitmap fb;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.recommend_fragment,container,false);
        fb=FinalBitmap.create(activity);
        fb.configLoadingImage(R.drawable.default_middle);
        fb.configLoadfailImage(R.drawable.default_middle);
        advertsType = activity.getIntent().getIntExtra("advertsType", 39);
        return view;
    }

    @Override
    protected String setTitle() {
        return getResources().getString(R.string.recommend_title);
    }
    /*private void setBadgeViewText(String buyNum) {
		buyNumView.setText(buyNum);//
        buyNumView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        if (!buyNum.equals("0")) {
        	buyNumView.show();
		}else {
			buyNumView.hide();
		}
        
	}*/
  /*  private boolean isLogin() {
        UserResult result = baseApplication.getUserResult();
        if (result != null && result.getUserAccount() != null) {
            return true;
        } else {
            return false;
        }
    }*/
    /*protected void showdialog() {
        final Dialog dialog = new Dialog(activity, R.style.Dialog);
        //View view = View.inflate(activity, R.layout.ordercancle_dialog, null);
        dialog.setContentView(R.layout.ordercancle_dialog);
        dialog.setCancelable(false);
        TextView tv_cancle = (TextView) dialog.findViewById(R.id.tv_cancle);
        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        tv_title.setText("是否立即登录？");
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();

    }*/
    @Override
    public void onResume() {
    	/*buyNum=baseApplication.getCartCount();
    	setBadgeViewText(buyNum+"");*/
    	super.onResume();
    }
    /*private void openNewActivityWithHeader(int type,boolean isneedlogin,boolean hasheader) {
        Intent intent = new Intent(activity, HomeHeaderActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("needLogin", isneedlogin);
        intent.putExtra("hasHeader", hasheader);
        intent.putExtra("isFromDetails", true);
        startActivity(intent);
    }*/
    @Override
    protected void findView(View v) {
//    	TextView list_header_title = (TextView) v.findViewById(R.id.list_header_title);
//    	list_header_title.setText(getResources().getString(R.string.recommend_title));
    	super.findView(v,getResources().getString(R.string.recommend_title));
    	/*list_header_leftbutton = (LinearLayout) v.findViewById(R.id.ll_left);
    	list_header_leftbutton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				activity.finish();
			}
		});*/
    	/*RelativeLayout rl=(RelativeLayout) v.findViewById(R.id.rl);
    	list_header_rightbutton = (TextView) v.findViewById(R.id.list_header_rightbutton);
    	list_header_rightbutton.setVisibility(View.VISIBLE);
    	list_header_rightbutton.setBackgroundResource(R.drawable.weiwei_gouwuche_checked);
    	buyNumView=new BadgeView(activity, rl);
    	buyNumView.setTextColor(Color.WHITE);
        buyNumView.setTextSize(12);
        buyNum = baseApplication.getCartCount();
        setBadgeViewText(buyNum+"");*/
       /* list_header_rightbutton.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				openNewActivityWithHeader(HeaderActivityType.AddShopCart.ordinal(), true, false);
			}
		});*/
        MyGridView gd=(MyGridView) v.findViewById(R.id.gd);
        adapter = new MyBaseAdapter(activity, list,new BuyClick() {
			
			@Override
			public void buyClick(int pid) {
				checkedIsloginToBuy(pid,false);
			}
		});
        gd.setAdapter(adapter);
//		gd.setFocusable(false);
        pw = (PullToRefreshScrollView) v.findViewById(R.id.sl_recommend);
        pw.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ScrollView> refreshView) {
                loadDataHandler.sendEmptyMessage(PULL_DOWN_MODE);

            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ScrollView> refreshView) {
                loadDataHandler.sendEmptyMessage(PULL_UP_MODE);
            }
        });
        gd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ComboEntity comboEntity=list.get(position);
                /*Intent intent=new Intent(activity, NewProductDetailActivity.class);
                intent.putExtra("productId",comboEntity.getPmId());
                startActivity(intent);*/

                Intent intent=new Intent(activity, HomeHeaderActivity.class);
                intent.putExtra("productId",comboEntity.getUrl());
                intent.putExtra("type", HeaderActivityType.ProductDetail.ordinal());
                intent.putExtra("hasHeader",false);
                startActivity(intent);
            }
        });

        loadDataHandler.sendEmptyMessageDelayed(103, 500);
    }
   /* private void addComBoToCart(int pmid) {

		AddToCartRequest request = new AddToCartRequest();
        request.setGoodsNum("1");
        request.setGoodsId(pmid);
		ShoppingCartRequestClient client=new ShoppingCartRequestClient(activity, baseApplication);
		client.addCart(request, new AddShoppingCartRequestClientCallback() {
			@Override
			protected void listSuccess(PhoneAddProductResult result) {
				Toast.makeText(activity, "商品加到购物车成功!", Toast.LENGTH_SHORT).show();
				setBadgeViewText(baseApplication.getCartCount()+"");
            }
		});
	}*/
    private Handler loadDataHandler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case PULL_DOWN_MODE:
                    fetchRecommendDate(true);
                    break;
                case PULL_UP_MODE:
                    fetchRecommendDate(false);
                    break;
                case 103:
                	pw.setRefreshing();
                	break;
                default:
                    break;
            }
        };
    };
    
   
    
	private int advertsType;
	//private TextView list_header_rightbutton;
	//private int buyNum;
	//private LinearLayout list_header_leftbutton;
    private void fetchRecommendDate(final boolean isFirst) {
       // BaseRequestClient client=new BaseRequestClient(activity);
       // UserResult result=baseApplication.getUserResult();
        RecommendRequest request=new RecommendRequest();
        request.setAdvertsType(advertsType);
        if (isFirst) {
            request.setPageIndex(1);
        }else {
            request.setPageIndex(++pagetype);
        }

        HomeRequestClient homeRequestClient=new HomeRequestClient(activity,baseApplication);
        homeRequestClient.recommendList(request, new HomeRequestClient.RecommendRequestClientCallback() {
            @Override
            protected void list(RecommendResult result) {
            	if (getActivity()==null) {
					return;
				}
            	 bigSize=result.getListCount();
                if (result.getBannersList()==null||result.getBannersList().size()==0) {
                    Toast.makeText(activity, "暂无商品信息", Toast.LENGTH_SHORT).show();
                    pw.onRefreshComplete();
                    return;
                }
               
                if (isFirst) {
//                            list_vp.clear();
                    list.clear();
                    adapter.notifyDataSetChanged();
//                            list_vp.addAll(result.getListVp());
//                            bf.setBannerEntityList(list_vp)	;
                    list.addAll(result.getBannersList());
                    adapter.notifyDataSetChanged();
                    pagetype=1;
                 /*   pw.setMode(Mode.BOTH);*/
                }else {
                    list.addAll(result.getBannersList());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void listComplete(RecommendResult result) {
            	if (getActivity()==null) {
					return;
				}
                pw.onRefreshComplete();
                if (result!=null&&result.getBannersList().size()>0) {
                	if (bigSize<=list.size()) {
                        pw.setMode(Mode.PULL_FROM_START);
                    }else {
                    	 pw.setMode(Mode.BOTH);
					}
				}
                
            }
        });


    }


    private class MyBaseAdapter extends BaseAdapter {
        public Context context;
        public List<ComboEntity> list;
        View view;
        Holder holder;
        private BuyClick buyClick;
        public MyBaseAdapter(Context context,List<ComboEntity> list,BuyClick buyClick) {
            this.context = context;
            this.list = list;
            this.buyClick=buyClick;
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        	//return 5;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        	//return 0;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                view = View.inflate(context,R.layout.recommend_item,null);
                holder = new Holder(view);
                view.setTag(holder);
                holder.tv_buy.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						buyClick.buyClick(list.get(position).getUrl());
					}
				});
            }else{
                view = convertView;
                holder = (Holder) view.getTag();
            }
            String imgUrl=list.get(position).getBody();
            if(!NetworkUtil.checkUrl(imgUrl)){
                imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
            }
            
            fb.display(holder.iv_product, imgUrl);
            holder.tv_name.setText(list.get(position).getExtField1());
            holder.tv_price.setText(String.format("￥%.2f", list.get(position).getExtField3()));
            holder.tv_oldprice.setText(String.format("￥%.2f", list.get(position).getExtField2()));
            holder.tv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            
            return view;
        }

    }
    public class Holder{
        public ImageView iv_product;
        public TextView tv_name;
        public TextView tv_price;
        public TextView tv_oldprice;
        public TextView tv_buy;
        
        public Holder(View view){
            iv_product = (ImageView) view.findViewById(R.id.iv_product);
            tv_name = (TextView) view.findViewById(R.id.pro_name);
            tv_price = (TextView) view.findViewById(R.id.pro_price);
            tv_oldprice = (TextView) view.findViewById(R.id.old_price);
            tv_buy = (TextView) view.findViewById(R.id.tv_buy);
        }
    }
    private interface BuyClick{
    	public void buyClick(int pid);
    }
}
