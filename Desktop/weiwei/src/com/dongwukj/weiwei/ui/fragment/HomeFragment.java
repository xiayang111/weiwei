package com.dongwukj.weiwei.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.HomeListAdapter;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.HomeRequest;
import com.dongwukj.weiwei.idea.result.FullGiftEntity;
import com.dongwukj.weiwei.idea.result.HomeResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.HomeRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.MipcaActivityCapture;
import com.dongwukj.weiwei.ui.activity.SearchActivity;
import com.dongwukj.weiwei.ui.fragment.BannerFragment.BannerCliclkListner;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import net.tsz.afinal.FinalBitmap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by pc on 2015/3/10.
 */
public class HomeFragment extends Fragment {

    private LinearLayout homeHeaderView;
    private RelativeLayout homeListView;
    private PullToRefreshListView refreshListView;
    private BannerFragment bannerFragment;
    private HomeListAdapter homeListAdapter;
    private ImageButton home_scan;
    private ImageView homeHeaderFullBuyImageView;
    private ImageView homeHeaderLimitBuyImageView;
    private ImageView homeHeaderRecommendImageView;


    private List<FullGiftEntity> homeHottestList;
    private LinearLayout homeTitleLayout;
    private static final int colorChangeHeight=200;

    private int pageIndex=1;
    private final int PULL_UP_MODE=100;
    private final int PULL_DOWN_MODE=101;
    private BaseApplication baseApplication;

    private FinalBitmap finalBitmap;
    private int withGiftId;
    private int desenoId;
    private int referralsId;
    private int bigSize;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        finalBitmap=FinalBitmap.create(getActivity());
        finalBitmap.configLoadingImage(R.drawable.default_middle);
        finalBitmap.configLoadfailImage(R.drawable.default_middle);

        homeListView= (RelativeLayout) inflater.inflate(R.layout.home_fragment,null,false);
        et = (EditText) homeListView.findViewById(R.id.home_search_edit);
        homeHeaderView=(LinearLayout)inflater.inflate(R.layout.home_header, null, false);
        baseApplication=(BaseApplication)getActivity().getApplication();
        initView();

        return homeListView;
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean iswifi=NetworkUtil.isWiFiActive(getActivity());
        boolean ismobile=NetworkUtil.isMobileConnected(getActivity());
        Log.d("net", "network is connected?  " + NetworkUtil.isConnected(getActivity())+" is wifi? " +iswifi +" is mobile ?"+ismobile);
    }

    private void initHeaderView(){
    	et.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getActivity(), SearchActivity.class);
				startActivity(intent);
			}
		});
      /*  ((Button)homeHeaderView.findViewById(R.id.recharge_button)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivityWithHeader(HeaderActivityType.Recharge.ordinal(),true);
            }
        });
        ((Button)homeHeaderView.findViewById(R.id.recommended_button)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	openNewActivityWithHeader(HeaderActivityType.RecommendJoin.ordinal(), false);
            }
        });
        
        ((Button)homeHeaderView.findViewById(R.id.btn_give_score)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	openNewActivityWithHeader(HeaderActivityType.GiveScore.ordinal(), false);
            }
        });
        
        ((Button)homeHeaderView.findViewById(R.id.bt_attention)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivityWithHeader(HeaderActivityType.Attention.ordinal(),true);
            }
        });*/
       /* tv_home_purchase = (TextView) homeHeaderView.findViewById(R.id.tv_home_purchase);
     //  tv_home_time = (TextView) homeHeaderView.findViewById(R.id.tv_home_time);
        tv_home_header_limitbuy = (TextView) homeHeaderView.findViewById(R.id.tv_home_header_limitbuy);
        tv_home_header_recommander = (TextView) homeHeaderView.findViewById(R.id.tv_home_header_recommander);

        homeHeaderFullBuyImageView=(ImageView)homeHeaderView.findViewById(R.id.home_header_fullbuy);
       //ll_home_header_fullbuy = (LinearLayout) homeHeaderView.findViewById(R.id.ll_home_header_fullbuy);
        ll_home_header_comb= (LinearLayout) homeHeaderView.findViewById(R.id.ll_home_header_comb);
       ll_home_header_limitbuy = (LinearLayout) homeHeaderView.findViewById(R.id.ll_home_header_limitbuy);
       ll_home_header_recommander = (LinearLayout) homeHeaderView.findViewById(R.id.ll_home_header_recommander);
        ll_home_header_comb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                openNewActivityWithHeader(HeaderActivityType.Combo.ordinal(),false);
			}
		});
        homeHeaderLimitBuyImageView=(ImageView)homeHeaderView.findViewById(R.id.home_header_limitbuy);
        ll_home_header_limitbuy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                openNewActivityWithHeader(HeaderActivityType.Purchase.ordinal(),false,desenoId);
			}
		});
        homeHeaderRecommendImageView=(ImageView)homeHeaderView.findViewById(R.id.home_header_recommander);
        ll_home_header_recommander.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                openNewActivityWithHeader(HeaderActivityType.Recommend.ordinal(),false,referralsId);
			}
		});
*/
    }

    private void initView(){
        initHeaderView();
        //refreshListView= (PullToRefreshListView) homeListView.findViewById(R.id.home_list);
        refreshListView.getRefreshableView().addHeaderView(homeHeaderView);
      //  bannerFragment= (BannerFragment) getFragmentManager().findFragmentById(R.id.bannerFragment);
        homeTitleLayout=(LinearLayout)homeListView.findViewById(R.id.home_title);
        home_scan = (ImageButton) homeListView.findViewById(R.id.home_scan);
        home_scan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getActivity(), MipcaActivityCapture.class);
				startActivity(intent);
			}
		});


        homeHottestList=new ArrayList<FullGiftEntity>();
        //homeListAdapter=new HomeListAdapter(homeHottestList,getActivity());
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //Toast.makeText(getActivity(),"onPullDownToRefresh",Toast.LENGTH_SHORT).show();
                loadDataHandler.sendEmptyMessage(PULL_DOWN_MODE);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //Toast.makeText(getActivity(),"onPullDownToRefresh",Toast.LENGTH_SHORT).show();
                loadDataHandler.sendEmptyMessage(PULL_UP_MODE);
            }
        });

      /*  refreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int height = getScrollY();
                if (height <= colorChangeHeight) {
                    float tmp = ((float) height / colorChangeHeight) * 255;
                    Integer alpha = (int) tmp;
                    Message msg = new Message();
                    msg.obj = alpha;
                    updateTitleBackgroundHandler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.obj = 255;
                    updateTitleBackgroundHandler.sendMessage(msg);
                }
            }
        });*/
        refreshListView.getRefreshableView().setAdapter(homeListAdapter);
       /* bannerFragment.setBannerCliclkListner(new BannerCliclkListner() {

            @SuppressLint("ShowToast")
            @Override
            public void click(String index) {
                Toast.makeText(getActivity(), index+"", Toast.LENGTH_SHORT).show();
            }
        });*/
        loadDataHandler.sendEmptyMessage(PULL_DOWN_MODE);
        //refreshListView.setRefreshing();
        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent=new Intent(getActivity(), NewProductDetailActivity.class);
                FullGiftEntity fullGiftEntity=homeHottestList.get((int) id);
               /* if(!TextUtils.isEmpty(fullGiftEntity.getUrl())){
                    int productId=Integer.parseInt(fullGiftEntity.getUrl());
                    //intent.putExtra("productId",productId);
                    //startActivity(intent);

                    Intent intent=new Intent(getActivity(), HomeHeaderActivity.class);
                    intent.putExtra("productId",productId);
                    intent.putExtra("type", HeaderActivityType.ProductDetail.ordinal());
                    intent.putExtra("hasHeader",false);
                    startActivity(intent);
                }*/

            }
        });

        refreshListView.setRefreshing();
    }
    private void openNewActivityWithHeader(int type,boolean needLogin){
    	openNewActivityWithHeader(type,needLogin,0);
    }
    private void openNewActivityWithHeader(int type,boolean needLogin,int advertsType){
//        if(baseApplication.getUserResult()==null &&needLogin){
//            Intent intent=new Intent(getActivity(),LoginActivity.class);
//            startActivity(intent);
//        }else{
            Intent intent=new Intent(getActivity(),HomeHeaderActivity.class);
            intent.putExtra("type",type);
            intent.putExtra("advertsType", advertsType);
            intent.putExtra("needLogin", needLogin);
            startActivity(intent);
//        }
    }


    private Handler updateTitleBackgroundHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
             Integer alpha=(Integer)msg.obj;
            if(alpha==null) return;
            updateBackground(alpha);


        }
    };

    private void updateBackground(int alpha){
        homeTitleLayout.setBackgroundColor(Color.argb(alpha,255,0,0));
    }

    public int getScrollY() {
        View c = refreshListView.getRefreshableView().getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = refreshListView.getRefreshableView().getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition > 1) {
            headerHeight = refreshListView.getRefreshableView().getHeight();
        }
        return -top+headerHeight ;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    private Handler loadDataHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PULL_DOWN_MODE:
                    fetchHomeData(true);
                    break;
                case PULL_UP_MODE:
                    fetchHomeData(false);
                    break;

            }
        }
    };
	private TextView tv_home_purchase;
	private TextView tv_home_header_limitbuy;
	private TextView tv_home_header_recommander;
	//private LinearLayout ll_home_header_fullbuy;
	private LinearLayout ll_home_header_limitbuy;
	private LinearLayout ll_home_header_recommander;
    private LinearLayout ll_home_header_comb;

	private EditText et;

    private void fetchHomeData(final boolean isFirst){

        BaseRequestClient client=new BaseRequestClient(getActivity());
        UserResult userResult=baseApplication.getUserResult();
        HomeRequest homeRequest=new HomeRequest();
       /* if(isFirst){
            homeRequest.setPageIndex(1);
        }else{
            homeRequest.setPageIndex(++pageIndex);
        }

       */ HomeRequestClient homeRequestClient=new HomeRequestClient(getActivity(),baseApplication);
        homeRequestClient.homeList(homeRequest, new HomeRequestClient.HomeRequestClientCallback() {
            @Override
            protected void list(HomeResult result) {
            	if (getActivity()==null) {
					return;
				}
               // bigSize=result.getListCount();
                /*if(isFirst){
                    homeHottestList.clear();
                    homeListAdapter.notifyDataSetChanged();
                    bannerFragment.setBannerEntityList(result.getBannersList());
                    homeHottestList.addAll(result.getHotList());
                    homeListAdapter.notifyDataSetChanged();
                    refreshListView.onRefreshComplete();
                    pageIndex=1;

                    updateFullBuy(result);

                }else{
                    homeHottestList.addAll(result.getHotList());
                    homeListAdapter.notifyDataSetChanged();

                }*/
                homeHottestList.clear();
                homeListAdapter.notifyDataSetChanged();
                bannerFragment.setBannerEntityList(result.getBannersList());
                //homeHottestList.addAll(result.getHotList());
               // homeListAdapter.notifyDataSetChanged();
                refreshListView.onRefreshComplete();
                updateFullBuy(result);
                refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            }

            @Override
            protected void listComplete(HomeResult result) {
            	if (getActivity()==null) {
					return;
				}
                refreshListView.onRefreshComplete();
                if (bigSize<=homeHottestList.size()) {
                    refreshListView.setMode(Mode.PULL_FROM_START);
                }                                                                              
            }
        });

    }
    
    private boolean isOne;
    private void updateFullBuy(HomeResult result){
        if(result!=null){
            if(result.getWithGiftImg()!=null){

                finalBitmap.display(homeHeaderFullBuyImageView,result.getWithGiftImg());
                tv_home_purchase.setText(result.getPhoneReferrals().getName());
               time=Long.parseLong(result.getPhoneReferrals().getEndTime1());
                if (!isOne) {
                    mHandler.sendEmptyMessage(0);
                    isOne=true;
                }
                withGiftId=result.getWithGiftId();
            }
            if(result.getDesenoImg()!=null){

                finalBitmap.display(homeHeaderLimitBuyImageView,result.getDesenoImg());
                tv_home_header_limitbuy.setText(result.getDesenoText());
                desenoId=result.getDesenoId();
            }
            if(result.getReferralsImg()!=null){

                finalBitmap.display(homeHeaderRecommendImageView,result.getReferralsImg());
                tv_home_header_recommander.setText(result.getReferralsText());
                referralsId=result.getReferralsId();
            }

        }
    }
    private SimpleDateFormat sm=new SimpleDateFormat("HH:mm:ss");
    private long time;
    private Handler mHandler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            mHandler.sendEmptyMessageDelayed(0, 1000);
            if (time>=1000) {
                String format = format(time);
                tv_home_time.setText(format);
                time=time-1000;
            }else {
                tv_home_time.setText("00:00:00");
            }
        }

        private String format(long tiem) {
            String format = sm.format(new Date(tiem));
            return format;
        };
    };
	private TextView tv_home_time;
    
}
