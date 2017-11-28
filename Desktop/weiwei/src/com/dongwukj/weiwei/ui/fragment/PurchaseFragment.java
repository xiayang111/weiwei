package com.dongwukj.weiwei.ui.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.PurchaseRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.ComboEntity;
import com.dongwukj.weiwei.idea.result.PurchaseResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sunjaly on 15/3/26.
 */
public  class PurchaseFragment extends AbstractHeaderFragment {
    private List<ComboEntity> list=new ArrayList<ComboEntity>();
    private FinalBitmap fb;
    protected static final int PULL_DOWN_MODE = 100;
    protected static final int PULL_UP_MODE = 101;
    protected static final int PULL_EMPTY = 102;
    private int Pagetype=1;
    private int bigSize;
    private MyBaseAdapter adapter;
    private PullToRefreshListView pf;
    SimpleDateFormat sm=new SimpleDateFormat("HH:mm:ss");

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_purchase,container,false);
        fb=FinalBitmap.create(activity);
        fb.configLoadingImage(R.drawable.default_small);
        fb.configLoadfailImage(R.drawable.default_small);
        return view;
    }

    @Override
    protected String setTitle() {
        return baseApplication.getString(R.string.purchase_title);
    }

    @Override
    protected void findView(View v) {
        pf = (PullToRefreshListView) v.findViewById(R.id.lv_purchase);
        adapter = new MyBaseAdapter(activity,list);
        tv_time = (TextView) v.findViewById(R.id.tv_time);
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
                if (list.size()>=bigSize) {
                    loadDataHandler.sendEmptyMessage(PULL_EMPTY);
                }else {
                    loadDataHandler.sendEmptyMessage(PULL_UP_MODE);
                }
            }
        });
        pf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ComboEntity comboEntity=list.get(position);
                /*Intent intent=new Intent(activity, NewProductDetailActivity.class);
                intent.putExtra("productId",comboEntity.getPmId());
                startActivity(intent);*/

                Intent intent=new Intent(activity, HomeHeaderActivity.class);
                intent.putExtra("productId",comboEntity.getPmId());
                intent.putExtra("type", HeaderActivityType.ProductDetail.ordinal());
                intent.putExtra("hasHeader",false);
                startActivity(intent);
            }
        });

        loadDataHandler.sendEmptyMessage(PULL_DOWN_MODE);
    }

    private long tiem;
    private Handler mHandler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            mHandler.sendEmptyMessageDelayed(0, 1000);
            if (tiem>=1000) {
                String format = format(tiem);
                tv_time.setText(format);
                tiem=tiem-1000;
            }else {
                tv_time.setText("00:00:00");
                adapter.notifyDataSetChanged();
            }
        }

        private String format(long tiem) {
            String format = sm.format(new Date(tiem));
            return format;
        };
    };
    private Handler loadDataHandler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case PULL_DOWN_MODE:
                    fetchPurchaseDate(true);
                    break;
                case PULL_UP_MODE:
                    fetchPurchaseDate(false);
                    break;
                case PULL_EMPTY:
                    pf.onRefreshComplete();
                    Toast.makeText(activity, "已经拉到最底部", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        };
    };
    private TextView tv_time;
    protected boolean isOne;
    private LinearLayout ll_empty;



    private void fetchPurchaseDate(final boolean isFirst){
        BaseRequestClient client=new BaseRequestClient(activity);
        UserResult result=baseApplication.getUserResult();
        PurchaseRequest request=new PurchaseRequest();
        if (isFirst) {
            request.setPageIndex(1);
        }else {
            request.setPageIndex(++Pagetype);
        }
        client.httpPostByJson("PhoneReferralsList", result, request, PurchaseResult.class, new BaseRequestClient.RequestClientCallBack<PurchaseResult>() {


            @Override
            public void callBack(PurchaseResult result) {
                if (result!=null) {
                    if (result.getCode()== BaseResult.CodeState.Success.getCode() && result.getListProduct()!=null) {

                        bigSize=result.getListNumber();
                        if (isFirst) {
                            tiem = result.getLimitTime();
                            if (!isOne) {
                                mHandler.sendEmptyMessage(0);
                                isOne=true;
                            }
                            list.clear();
                            adapter.notifyDataSetChanged();
                            list.addAll(result.getListProduct());
                            adapter.notifyDataSetChanged();
                            Pagetype=1;
                        }else {
                            list.addAll(result.getListProduct());
                            adapter.notifyDataSetChanged();
                        }
                    }else{
                        Toast.makeText(activity,result.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(activity,getResources().getString(R.string.data_error),Toast.LENGTH_SHORT).show();
                }
                pf.onRefreshComplete();
            }

            @Override
            public void loading(long count, long current) {

            }
            @Override
            public void logOut(boolean isLogOut,PurchaseResult result) {
            	 pf.onRefreshComplete();
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
			startActivity(intent);}

        });
    }

    private class MyBaseAdapter extends BaseAdapter{
        public Context context;
        public List<ComboEntity> list;
        View view;
        Holder holder;

        public MyBaseAdapter(Context context,List<ComboEntity> list) {
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
                view = View.inflate(context,R.layout.purchase_item,null);
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
            holder.tv_price.setText(list.get(position).getCruPrice()+"");
            holder.tv_oldprice.setText(list.get(position).getOldPrice()+"");
            holder.tv_repertoryCount.setText(list.get(position).getRepertoryCount());
            holder.tv_participatorCount.setText(list.get(position).getParticipatorCount());
            if (tiem>=1000) {
                holder.bt_buy.setBackgroundColor(Color.RED);
            }else {
                holder.bt_buy.setBackgroundColor(Color.DKGRAY);
            }
            holder.bt_buy.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (tiem>=1000) {
                        int code=getResultCode();
                        changeShowstate(code);
                    }

                }

            });
            return view;
        }

    }

    private void changeShowstate(int code) {
    	showAlert();
       
    }

    private void showAlert(){
        Dialog dialog=new Dialog(activity,R.style.Dialog);
        dialog.setContentView(R.layout.cus_alertdialog_layout);
        dialog.show();
    }

    private int getResultCode() {
        // TODO Auto-generated method stub
        return 0;
    }
    public class Holder{
        public ImageView iv_product;
        public TextView tv_name;
        public TextView tv_price;
        public TextView tv_oldprice;
        public TextView tv_repertoryCount;
        public TextView tv_participatorCount;
        public Button bt_buy;
        public Holder(View view){
            iv_product = (ImageView) view.findViewById(R.id.iv_product);
            tv_name = (TextView) view.findViewById(R.id.pro_name);
            tv_price = (TextView) view.findViewById(R.id.pro_price);
            tv_oldprice = (TextView) view.findViewById(R.id.old_price);
            tv_repertoryCount=(TextView) view.findViewById(R.id.tv_repertoryCount);
            tv_participatorCount=(TextView) view.findViewById(R.id.tv_participatorCount);
            bt_buy=(Button) view.findViewById(R.id.bt_buy);
        }
    }
}
