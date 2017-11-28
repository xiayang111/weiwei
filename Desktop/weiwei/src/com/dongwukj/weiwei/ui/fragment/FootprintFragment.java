package com.dongwukj.weiwei.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.FootPrintRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.FootPrintEntity;
import com.dongwukj.weiwei.idea.result.FootPrintResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;

public class FootprintFragment extends AbstractHeaderFragment {
	protected static final int Load_FootPrint = 0;
	protected static final int Clear_FootPrint = 1;
	protected static final int PULL_DOWN_MODE = 100;
    protected static final int PULL_UP_MODE = 101;
    protected static final int PULL_EMPTY = 102;
    private int bigSize;
	private int pageIndext=1;
    private PullToRefreshListView lv_footprint;
	private MyAdapter adapter;
	private FinalBitmap fb;
	private List<FootPrintEntity> list=new ArrayList<FootPrintEntity>();
	
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.footprint, container,false);
		fb = FinalBitmap.create(activity);
		fb.configLoadfailImage(R.drawable.default_small);  //图片加载失败
		fb.configLoadingImage(R.drawable.default_small);   //图片正在加载
		return view;
	}

	@Override
	protected String setTitle() {
		String title=activity.getString(R.string.footprint_title);
		return title;
	}

	@Override
	protected void findView(View v) {

		ll_wrong = (LinearLayout) v.findViewById(R.id.ll_wrong);
		lv_footprint = (PullToRefreshListView) v.findViewById(R.id.lv_footprint);		
		adapter = new MyAdapter();
		lv_footprint.setAdapter(adapter);
		//footprintHandler.sendEmptyMessage(Load_FootPrint);
		//设置上拉下拉刷新监听
		/*lv_footprint.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				footprintHandler.sendEmptyMessage(PULL_DOWN_MODE);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				footprintHandler.sendEmptyMessage(PULL_UP_MODE);
				
			}
		});	
		footprintHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);*/
		PullToRefreshTest  test=new PullToRefreshTest(PULL_DOWN_MODE,PULL_UP_MODE,PULL_EMPTY, footprintHandler);
        test.initListView(lv_footprint);
		lv_footprint.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent=new Intent(activity, NewProductDetailActivity.class);
            	
            	if(list.size()<position-1){
            		return;
            	}
            	/**
            	 * 修改BUG 161 
            	 */
                FootPrintEntity footPrintEntity=list.get(position-1);
                //intent.putExtra("productId",footPrintEntity.getpId());
                //startActivity(intent);

                Intent intent=new Intent(activity, HomeHeaderActivity.class);
                intent.putExtra("productId",footPrintEntity.getpId());
                intent.putExtra("type", HeaderActivityType.ProductDetail.ordinal());
                intent.putExtra("hasHeader",false);
                startActivity(intent);
            }
        });
		
	}
	
	private void getFootPrintDate(final boolean isFirst){
		//联网获取我的足迹数据
		FootPrintRequest request = new FootPrintRequest();
		request.setAddNum(10);
		if(isFirst){
			request.setPageIndex(1);
		}else{
			request.setPageIndex(++pageIndext);
		}

        MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(activity,baseApplication);
        myWeiWeiRequestClient.footPrintList(request, new MyWeiWeiRequestClient.FootPrintRequestCallback() {
            @Override
            protected void list(FootPrintResult result) {
            	if (getActivity()==null) {
					return;
				}
                if(result.getProducts()==null||result.getProducts().size()==0){
                    Toast.makeText(activity, "暂时没有浏览信息", Toast.LENGTH_SHORT).show();
                    lv_footprint.onRefreshComplete();	 //将下拉收起
                    ll_wrong.setVisibility(View.VISIBLE);
                    return;
                }
                bigSize = result.getListnumber();
                if(isFirst){         //下拉刷新
                    list.clear();    //如果服务器的数据有更新的话,需要把集合里面的旧数据清空
                    adapter.notifyDataSetChanged();   	//刷新 , 可以在修改适配器绑定的集合或者数组后，不用重新刷新Activity，而通知Activity更新ListView
                    list.addAll(result.getProducts());  //再重新加载最新的数据到集合.
                    adapter.notifyDataSetChanged();
                    pageIndext = 1;
                   
                }else{
                    list.addAll(result.getProducts());
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            protected void logOut(BaseResult result) {

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
            @Override
            protected void listComplete(FootPrintResult result) {
            	if (getActivity()==null) {
					return;
				}
                lv_footprint.onRefreshComplete();
                if(bigSize<=list.size()){
                    lv_footprint.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_START); //只支持下拉Pulling Down
                }else {
                	 lv_footprint.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH);
				}
            }
        });
	}
	private Handler footprintHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Clear_FootPrint:  //清除记录
				
				break;
			case PULL_DOWN_MODE:  //加载浏览记录
				getFootPrintDate(true);
				break;
			case PULL_UP_MODE:
				getFootPrintDate(false);
				break;
			case PULL_EMPTY:
				lv_footprint.setRefreshing();
				break;
			default:
				break;
			}
		};
	};
	private LinearLayout ll_wrong;
private class MyAdapter extends BaseAdapter{

		
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
			// TODO Auto-generated method stub
			ViewHodler holder = null;
			if(convertView==null){//如果View为空,加载View找到控件
				convertView=View.inflate(activity, R.layout.footprint_item,null);
				holder=new ViewHodler();
				holder.iv_footprint_picture = (ImageView) convertView.findViewById(R.id.iv_footprint_picture);
				holder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
				holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
				holder.tv_weight = (TextView) convertView.findViewById(R.id.tv_weight);
				convertView.setTag(holder); //ViewHoldler将需要缓存的那些View封装, setTage将View缓存好以供下次调用.
				
			}else{//不为空直接使用
				holder = (ViewHodler) convertView.getTag();
			}
			holder.tv_weight.setText(list.get(position).getWeight()+"克");           //商品规格
			//holder.tv_price.setText("￥"+list.get(position).getShopPrice()); //商品价格
			holder.tv_price.setText(String.format("￥%.2f", list.get(position).getShopPrice())); //商品价格
			holder.tv_product_name.setText(list.get(position).getName());				//商品名称

            String imgUrl=list.get(position).getShowImg();
            if(!NetworkUtil.checkUrl(imgUrl)){
                imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
            }

			fb.display(holder.iv_footprint_picture, imgUrl);   //商品图片
						
			return convertView;
		}
		
	}
	
	class ViewHodler{
		ImageView iv_footprint_picture;
		TextView tv_product_name;
		TextView tv_price;
		TextView tv_weight;
		
	}

}
