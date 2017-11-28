package com.dongwukj.weiwei.ui.fragment;


//import android.app.Dialog;
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
import android.widget.AdapterView.OnItemClickListener;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
//import com.dongwukj.weiwei.idea.request.AddToCartRequest;
import com.dongwukj.weiwei.idea.request.ComboRequest;
import com.dongwukj.weiwei.idea.result.ComboEntity;
import com.dongwukj.weiwei.idea.result.ComboResult;
//import com.dongwukj.weiwei.idea.result.PhoneAddProductResult;
//import com.dongwukj.weiwei.idea.result.UserResult;
//import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.HomeRequestClient;
///import com.dongwukj.weiwei.net.ShoppingCartRequestClient;
//import com.dongwukj.weiwei.net.ShoppingCartRequestClient.AddShoppingCartRequestClientCallback;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
//import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

public class ComboFragment extends ShopCartMessageFragment {

	
	protected static final int PULL_DOWN_MODE = 100;
	protected static final int PULL_UP_MODE = 101;
	protected static final int PULL_EMPTY = 102;
	private PullToRefreshGridView pg;
	List <ComboEntity> list=new ArrayList<ComboEntity>();
	FinalBitmap fb;
	private int Pagetype=1;
	private int bigSize;
	private MyAdapter adapter;
	private BaseApplication baseApplication;
    private View view_parent;

	private Handler loadDataHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PULL_DOWN_MODE:
				fetchComboDate(true);
				break;
			case PULL_UP_MODE:
				fetchComboDate(false);
				break;
			case PULL_EMPTY:
				pg.setRefreshing();
				break;

			default:
				break;
			}
		};
	};
	private double total;
	  private void openNewActivityWithHeader(int type,int pmId,String title){
	        Intent intent=new Intent(activity,HomeHeaderActivity.class);
	            intent.putExtra("type",type);
	            intent.putExtra("pmId", pmId);
	            intent.putExtra("title", title);
	            intent.putExtra("total", total);
	            startActivity(intent);
	       
	    }
	private void fetchComboDate(final boolean isFirst){
		ComboRequest request=new ComboRequest();
		request.setAddNum(10);
		if (isFirst) {
			request.setPageIndex(1);
		}else {
			request.setPageIndex(++Pagetype);
		}

        HomeRequestClient homeRequestClient=new HomeRequestClient(activity,baseApplication);
        homeRequestClient.comboList(request, new HomeRequestClient.ComboRequestClientCallback() {
            @Override
            protected void list(ComboResult result) {
            	if (getActivity()==null) {
					return;
				}
            	 bigSize=result.getListNumber();
                if (result.getPackages()==null||result.getPackages().size()==0) {
                    Toast.makeText(activity,getResources().getString(R.string.combo_pro_isempty),Toast.LENGTH_SHORT).show();
                    pg.onRefreshComplete();
                    return;
                }
                if (isFirst) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                    list.addAll(result.getPackages());
                    adapter.notifyDataSetChanged();
                    Pagetype=1;
                   }else {
                    list.addAll(result.getPackages());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void listComplete(ComboResult result) {
            	if (getActivity()==null) {
					return;
				}
                pg.onRefreshComplete();
                if (result!=null&&result.getPackages().size()>0) {
                	if (bigSize<=list.size()) {
                        pg.setMode(Mode.PULL_FROM_START);
                    }else {
                    	  pg.setMode(Mode.BOTH);
                    }
	
				}
             }
        });


	}
/*	private void addComBoToCart(int pmId) {
	       AddToCartRequest request = new AddToCartRequest();
	        request.setGoodsNum("1");
	        request.setPmId(pmId+"");
			ShoppingCartRequestClient client=new ShoppingCartRequestClient(activity, baseApplication);
			client.addCart(request, new AddShoppingCartRequestClientCallback() {
				@Override
				protected void listSuccess(PhoneAddProductResult result) {
					Toast.makeText(activity, "套餐添加到购物车成功!", Toast.LENGTH_SHORT).show();
				}
			});
		}*/
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_parent = inflater.inflate(R.layout.combo_fragment, null);
        baseApplication = (BaseApplication) activity.getApplication();
        fb = FinalBitmap.create(activity);
        fb.configLoadfailImage(R.drawable.default_middle);
        fb.configLoadingImage(R.drawable.default_middle);
        return view_parent;
    }

    @Override
    protected String setTitle() {
        return "套餐";
    }

    @Override
    protected void findView(View v) {
        super.findView(v,getResources().getString(R.string.combo_title));
        pg = (PullToRefreshGridView) view_parent.findViewById(R.id.layout_content);
        adapter = new MyAdapter(activity,list);
        pg.setAdapter(adapter);
        /*pg.setOnRefreshListener(new OnRefreshListener2<GridView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                loadDataHandler.sendEmptyMessage(PULL_DOWN_MODE);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                loadDataHandler.sendEmptyMessage(PULL_UP_MODE);
            }
        });
        loadDataHandler.sendEmptyMessageDelayed(110, 500);*/
        PullToRefreshTest test=new PullToRefreshTest(PULL_DOWN_MODE, PULL_UP_MODE, PULL_EMPTY, loadDataHandler);
        test.initGridView(pg);
        pg.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ComboEntity entity = (ComboEntity) adapter.getItem(position);
                 total = entity.getCruPrice();
                openNewActivityWithHeader(HeaderActivityType.ComboDetail.ordinal(),entity.getPmId(),entity.getName());
            }
        });
    }
   /* private boolean isLogin() {
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
    private class MyAdapter extends BaseAdapter{
		public Context context;
		public List<ComboEntity> list;
		View view;
		Holder holder;

		public MyAdapter(Context context,List<ComboEntity> list) {
			this.context = context;
			this.list = list;
		}

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
		public View getView(final int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				view = View.inflate(context,R.layout.comboitem,null);
				holder = new Holder(view);
				view.setTag(holder);
			}else{
				view = convertView;
				holder = (Holder) view.getTag();
			}
            String imgUrl=list.get(position).getPhotoUrl();
            if(!NetworkUtil.checkUrl(imgUrl)){
                imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
            }
			fb.display(holder.iv_product, imgUrl);
			holder.tv_name.setText(list.get(position).getName());
			//holder.tv_des.setText(list.get(position).getDescribe());
			//holder.tv_price.setText("￥"+list.get(position).getCruPrice());
			holder.tv_price.setText(String.format("￥%.2f", list.get(position).getCruPrice()));
			//holder.tv_oldprice.setText("￥"+list.get(position).getOldPrice()+"");
			holder.tv_oldprice.setText(String.format("￥%.2f", list.get(position).getOldPrice()));
			holder.tv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			holder.img_buy.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//boolean login = isLogin();
					/*if (login) {
						addComBoToCart(list.get(position).getPmId());
					}else {
						showdialog();
					}*/
				checkedIsloginToBuy(list.get(position).getPmId(),true);
				}
			});
			return view;
		}
		 
		public class Holder{
			public ImageView iv_product;
			public ImageView img_buy;
			public TextView tv_name;
			//public TextView tv_des;
			public TextView tv_price;
			public TextView tv_oldprice;
			public Holder(View view){
				iv_product = (ImageView) view.findViewById(R.id.iv_product);
				img_buy = (ImageView) view.findViewById(R.id.img_buy);
				tv_name = (TextView) view.findViewById(R.id.pro_name);
				//tv_des = (TextView) view.findViewById(R.id.pro_describe);
				tv_price = (TextView) view.findViewById(R.id.pro_price);
				tv_oldprice = (TextView) view.findViewById(R.id.old_price);
			}
		}
		
	}
   
}
