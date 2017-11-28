package com.dongwukj.weiwei.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.PresentRequest;
import com.dongwukj.weiwei.idea.result.PresentEntity;
import com.dongwukj.weiwei.idea.result.PresentResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.HomeRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunjaly on 15/3/26.
 */
public class PresentFragment extends AbstractHeaderFragment {
    private PullToRefreshListView pf;
    private FinalBitmap fb;
    private int pagetype=1;
    private List<PresentEntity> list=new ArrayList<PresentEntity>();
    //private BannerFragment bf;
    private int bigSize;
    protected static final int PULL_DOWN_MODE = 100;
    protected static final int PULL_UP_MODE = 101;
    protected static final int PULL_EMPTY = 102;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_present,container,false);
        type = activity.getIntent().getIntExtra("advertsType", 0);
        fb = FinalBitmap.create(activity);
        fb.configLoadingImage(R.drawable.default_small);
        fb.configLoadfailImage(R.drawable.default_small);
        return view;
    }

    @Override
    protected String setTitle() {
        return getResources().getString(R.string.present_title);
    }

    @Override
    protected void findView(View v) {
        //ll = (LinearLayout) View.inflate(activity, R.layout.present_header, null);
        //bf = (BannerFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fg_recommend);
        pf= (PullToRefreshListView) v.findViewById(R.id.lv_present);
        //v.findViewById(R.id.bt)
        //pf.getRefreshableView().addHeaderView(ll);
        adapter = new MyBaseAdapter(activity, list);
        pf.setAdapter(adapter);
        pf.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

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
        pf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PresentEntity presentEntity=list.get((int)id);
//                Intent intent=new Intent(activity, NewProductDetailActivity.class);
//                intent.putExtra("productId",presentEntity.getUrl());
//                startActivity(intent);


                Intent intent=new Intent(activity, HomeHeaderActivity.class);
                intent.putExtra("productId",presentEntity.getUrl());
                intent.putExtra("type", HeaderActivityType.ProductDetail.ordinal());
                intent.putExtra("hasHeader",false);
                startActivity(intent);
            }
        });

        loadDataHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);
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
                  pf.setRefreshing();
                    break;

                default:
                    break;
            }
        };
    };
    private MyBaseAdapter adapter;
	private int type;
    //private LinearLayout ll;
    private void fetchRecommendDate(final boolean isFirst) {
        BaseRequestClient client=new BaseRequestClient(activity);
        UserResult result=baseApplication.getUserResult();
        PresentRequest request=new PresentRequest();
        request.setAdvertsType(type);
        if (isFirst) {
            request.setPageType(1);
        }else {
            request.setPageType(++pagetype);
        }

        HomeRequestClient homeRequestClient=new HomeRequestClient(activity,baseApplication);
        homeRequestClient.presentList(request, new HomeRequestClient.PresentRequestClientCallback() {
            @Override
            protected void list(PresentResult result) {
            	if (getActivity()==null) {
					return;
				}
                if (result.getBannersList() == null && result.getBannersList().size() == 0) {
                    Toast.makeText(activity, "暂时没有满赠信息", Toast.LENGTH_SHORT).show();
                    pf.onRefreshComplete();
                    return;
                }
                bigSize = result.getListNumber();  //商品总数
                if (isFirst) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                    //bf.setBannerEntityList(list_vp);
                    list.addAll(result.getBannersList());
                    adapter.notifyDataSetChanged();
                    pagetype = 1;
                    pf.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH);
                } else {
                    list.addAll(result.getBannersList());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void listComplete(PresentResult result) {
            	if (getActivity()==null) {
					return;
				}
                pf.onRefreshComplete();
                if (bigSize <= list.size()) {
                    pf.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_START);
                }
            }
        });


    }
    
    private void showAlert(){
    	Dialog dialog = new Dialog(activity,R.style.Dialog); 
    	dialog.setContentView(R.layout.present_dialog);
    	dialog.show();
    }

    private class MyBaseAdapter extends BaseAdapter {
        public Context context;
        public List<PresentEntity> list;
        View view;
        Holder holder;

        public MyBaseAdapter(Context context,List<PresentEntity> list) {
            this.context = context;
            this.list = list;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                view = View.inflate(context,R.layout.present_item,null);
                holder = new Holder(view);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (Holder) view.getTag();
            }
            String imgUrl=list.get(position).getBody();
            if(!NetworkUtil.checkUrl(imgUrl)){
                imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
            }
            fb.display(holder.iv_product, imgUrl);       //图片
            holder.tv_name.setText(list.get(position).getExtfield1());         //商品名称
            holder.tv_price.setText(list.get(position).getExtfield3());		   //现价
            holder.tv_oldprice.setText(list.get(position).getExtfield2());     //原价
            holder.tv_describe.setText(list.get(position).getExtfield4());     //规格
            return view;
        }

    }
    public class Holder{
        public ImageView iv_product;
        public TextView tv_name;
        public TextView tv_price;
        public TextView tv_oldprice;
        public TextView tv_describe;
        public Holder(View view){
            iv_product = (ImageView) view.findViewById(R.id.iv_product);
            tv_name = (TextView) view.findViewById(R.id.pro_name);
            tv_price = (TextView) view.findViewById(R.id.pro_price);
            tv_oldprice = (TextView) view.findViewById(R.id.old_price);
            tv_describe=(TextView) view.findViewById(R.id.pro_desc);
        }
    }
}
