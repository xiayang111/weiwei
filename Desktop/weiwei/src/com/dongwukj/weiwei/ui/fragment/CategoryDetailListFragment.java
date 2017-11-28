package com.dongwukj.weiwei.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.PhoneGetProductsRequest;
import com.dongwukj.weiwei.idea.result.PhoneGetProductsResult;
import com.dongwukj.weiwei.idea.result.ProductInfoEntity;
import com.dongwukj.weiwei.net.CategoryRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunjaly on 15/3/26.
 */
public class CategoryDetailListFragment extends ShopCartMessageFragment {

    protected static final int PULL_DOWN_MODE = 100;
    protected static final int PULL_UP_MODE = 101;
    protected static final int PULL_EMPTY = 102;
    private PullToRefreshGridView gv;
    private int pagetype=1;
    private List<ProductInfoEntity> list=new ArrayList<ProductInfoEntity>();
    private int bigSize;
    private MyBaseAdapter adapter;
    private FinalBitmap fb;
    private static final int pageSize=10;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.product_list_fragment,container,false);
        bundle =activity.getIntent().getExtras();
         fb=FinalBitmap.create(activity);
        fb.configLoadfailImage(R.drawable.default_small);
        fb.configLoadingImage(R.drawable.default_small);
        return view;
    }

    @Override
    protected String setTitle() {
       
        return null;
    }
    @Override
    public void onResume() {
    
    	super.onResume();
    }

    @Override
    protected void findView(View v) {
    	String title=activity.getIntent().getStringExtra("title");

    	super.findView(v,title);
    	ll_wrong = (LinearLayout) v.findViewById(R.id.ll_wrong);
    	gv=(PullToRefreshGridView) v.findViewById(R.id.product_list_gv);
        adapter = new MyBaseAdapter(activity, list,new BuyClick() {
			
			@Override
			public void buyClick(int pid) {
				checkedIsloginToBuy(pid,false);
			}
		});
        gv.setAdapter(adapter);
        PullToRefreshTest test=new PullToRefreshTest(PULL_DOWN_MODE, PULL_UP_MODE, PULL_EMPTY, loadDataHandler);
        test.initGridView(gv);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductInfoEntity productInfoEntity=list.get(position);
                Intent intent=new Intent(activity, HomeHeaderActivity.class);
                intent.putExtra("productId",productInfoEntity.getpId());
                intent.putExtra("type", HeaderActivityType.ProductDetail.ordinal());
                intent.putExtra("hasHeader",false);
                startActivity(intent);
            }
        });
    }

  
    private Handler loadDataHandler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case PULL_DOWN_MODE:
                    fetchRecommendDate(true);
                    break;
                case PULL_UP_MODE:
                    fetchRecommendDate(false);
                    break;
                case PULL_EMPTY:
                   gv.setRefreshing();
                    break ;
                default:
                    break;
            }
        };
    };
	private LinearLayout ll_wrong;
	private Bundle bundle;
	//private int buyNum;
    private void fetchRecommendDate(final boolean isFirst) {/*

        PhoneGetProductsRequest request=new PhoneGetProductsRequest();
        request.setCategoryId(bundle.getInt("categoryId",-1));
        request.setAddNum(pageSize);
        if (isFirst) {
            request.setPageIndex(1);
        }else {
            request.setPageIndex(++pagetype);
        }

        CategoryRequestClient categoryRequestClient=new CategoryRequestClient(activity,baseApplication);
        categoryRequestClient.searchOrListDetailCategory(request, new CategoryRequestClient.CategoryRequestCallback() {
            @Override
            protected void detailList(PhoneGetProductsResult result) {
            	if (getActivity()==null) {
					return;
				}
                bigSize=result.getListNumber();
                if (result.getProducts().size()==0) {
					ll_wrong.setVisibility(View.VISIBLE);
					gv.setVisibility(View.GONE);
					return;
				}
                if (isFirst) {
                	list.clear();
                    adapter.notifyDataSetChanged();
                    list.addAll(result.getProducts());
                    adapter.notifyDataSetChanged();
                    pagetype=1;
                }else {
                    list.addAll(result.getProducts());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void detailListComplete(PhoneGetProductsResult result) {
            	if (getActivity()==null) {
					return;
				}
                gv.onRefreshComplete();
                if (result!=null&&result.getProducts().size()>0) {
                	if (bigSize<=list.size()) {
                        gv.setMode(Mode.PULL_FROM_START);
                    }else {
                    	 gv.setMode(Mode.BOTH);
					}
				}
                
            }
        });

    */}


    private class MyBaseAdapter extends BaseAdapter {
        public Context context;
        public List<ProductInfoEntity> list;
        View view;
        Holder holder;
        private BuyClick buyClick;
        public MyBaseAdapter(Context context,List<ProductInfoEntity> list,BuyClick buyClick) {
            this.context = context;
            this.list = list;
            this.buyClick=buyClick;
        }
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                view = View.inflate(context,R.layout.recommend_item,null);
                holder = new Holder(view);
                view.setTag(holder);
                holder.tv_buy.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						buyClick.buyClick(list.get(position).getpId());
					}
				});
            }else{
                view = convertView;
                holder = (Holder) view.getTag();
            }
            String imgUrl=list.get(position).getShowImg();
            if(!NetworkUtil.checkUrl(imgUrl)){
                imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
            }
            fb.display(holder.iv_product, imgUrl);
            holder.tv_name.setText(list.get(position).getName());
            if (list.get(position).getShopPrice().equals(list.get(position).getDicountPrice())) {
            	holder.tv_price.setText(String.format("￥%.2f",list.get(position).getShopPrice()));
                holder.tv_oldprice.setText(String.format("￥%.2f",list.get(position).getMarketPrice()));
			}else {
				holder.tv_price.setText(String.format("￥%.2f",list.get(position).getDicountPrice()));
                holder.tv_oldprice.setText(String.format("￥%.2f",list.get(position).getShopPrice()));
			}
            
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
