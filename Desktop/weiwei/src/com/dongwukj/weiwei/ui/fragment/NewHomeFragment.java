package com.dongwukj.weiwei.ui.fragment;


import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.result.AdEntity;
import com.dongwukj.weiwei.idea.result.AddressEntity;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.FullGiftEntity;
import com.dongwukj.weiwei.idea.result.HomeCategorie;
import com.dongwukj.weiwei.idea.result.PhoneGetDefaultAddressResult;
import com.dongwukj.weiwei.idea.result.PhoneGethomepageResult;
import com.dongwukj.weiwei.idea.result.Plot;
import com.dongwukj.weiwei.idea.result.UpdateClassifyEntity;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeActivity;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.activity.MipcaActivityCapture;
import com.dongwukj.weiwei.ui.activity.SearchActivity;
import com.dongwukj.weiwei.ui.fragment.BannerFragment.BannerCliclkListner;
import com.dongwukj.weiwei.ui.widget.JazzyViewPager;
import com.dongwukj.weiwei.ui.widget.JazzyViewPager.TransitionEffect;
import com.dongwukj.weiwei.ui.widget.OutlineContainer;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import de.greenrobot.event.EventBus;

/**
 * Created by sunjaly on 15/4/18.
 */
public class NewHomeFragment extends AbstractLoadingFragment {


    private RelativeLayout homeListView;

    private LinearLayout et;
	private TextView tv_address;
    private ImageButton home_scan;
    private LinearLayout homeTitleLayout;
    private BannerFragment bannerFragment;
    private PullToRefreshListView home_pull;

	private LinearLayout ll_classfy;

	private Homeadapter homeClassifyadapter;

	

    private final int PULL_UP_MODE=100;
    private final int PULL_DOWN_MODE=101;
    private BaseApplication baseApplication;

    private FinalBitmap finalBitmap;

    private boolean isAdd;
 

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	finalBitmap=FinalBitmap.create(activity);
        finalBitmap.configLoadingImage(R.drawable.default_big);
        finalBitmap.configLoadfailImage(R.drawable.default_big);
        homeListView= (RelativeLayout) inflater.inflate(R.layout.home_fragment,null,false);
        baseApplication=(BaseApplication)activity.getApplication();
        return homeListView;
    }

   
    private Plot plot=null;
    @Override
    protected void findViews(View v) {
    	et = (LinearLayout) homeListView.findViewById(R.id.ll_search);
    	((TextView)homeListView.findViewById(R.id.tv_changeAddress)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isAdd) {
					PhoneGetPlotResult result=new PhoneGetPlotResult();
					result.setPlot(plot);
					Intent intent=new Intent(activity, HomeHeaderActivity.class);
	                intent.putExtra("type", HeaderActivityType.NewAddress.ordinal());
	                intent.putExtra("fromorder", result);
	                startActivityForResult(intent, 300);
				}else {
					Intent intent=new Intent(activity,HomeHeaderActivity.class);
		            intent.putExtra("type",HeaderActivityType.Address.ordinal());
		            intent.putExtra("needLogin",true);
		            intent.putExtra("fromHome", true);
		            startActivityForResult(intent, 200);
				}
				
			}
    	});;
    	tv_address = (TextView) v.findViewById(R.id.tv_address);
    	homeTitleLayout=(LinearLayout)homeListView.findViewById(R.id.home_title);
        home_scan = (ImageButton) homeListView.findViewById(R.id.home_scan);
        home_scan.setOnClickListener(new View.OnClickListener() {
        	
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, MipcaActivityCapture.class);
                startActivity(intent);
            }
        });
        initHeaderView();
        inithome(v);
        initvp(v);
     }
    private int index=0;
    private JazzyViewPager mJazzy;
    private boolean isfirst=true;
	private void initvp(View v) {
		mJazzy = (JazzyViewPager)v.findViewById(R.id.jazzy_pager);
		mJazzy.setTransitionEffect(TransitionEffect.FlipVertical);
		vpAdapter = new MainAdapter();
		mJazzy.setAdapter(vpAdapter);
		mJazzy.setPageMargin(30);
		
		if (isfirst) {
			mHandler.sendEmptyMessage(1000);
			isfirst=false;
		}
		
	}
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			mHandler.sendEmptyMessageDelayed(1000, 2000);
			switch (index) {
			
			case 0:
				mJazzy.setCurrentItem(0);
				index=1;
				break;
			case 1:
				mJazzy.setCurrentItem(1);
				index=2;
				break;
			case 2:
				mJazzy.setCurrentItem(2);
				index=0;
				break;

			default:
				break;
			}
		};
	};
	private TextView tv_newAddress;
	private class MainAdapter extends PagerAdapter
	{
		@Override
		public Object instantiateItem(ViewGroup container, final int position)
		{
			TextView text = new TextView(activity);
			text.setGravity(Gravity.CENTER);
			text.setTextSize(16);
			text.setTextColor(Color.WHITE);
			if (position==0) {
				text.setText("您当前的地址为：");
			}else if (position==1) {
				if (TextUtils.isEmpty(address)) {
					text.setText("");
				}else {
					text.setText(address);
				}
				tv_newAddress=text;
			}else {
				text.setText("下单前请确认收货地址");
			}
			
			text.setPadding(10, 10, 10, 10);
			/*int bg = Color.rgb((int) Math.floor(Math.random() * 128) + 64,
					(int) Math.floor(Math.random() * 128) + 64,
					(int) Math.floor(Math.random() * 128) + 64);
			text.setBackgroundColor(bg);*/
			container.addView(text, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			mJazzy.setObjectForPosition(text, position);
			return text;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object obj)
		{
			container.removeView(mJazzy.findViewFromObject(position));
		}

		@Override
		public int getCount()
		{
			return 3;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj)
		{
			if (view instanceof OutlineContainer)
			{
				return ((OutlineContainer) view).getChildAt(0) == obj;
			} else
			{
				return view == obj;
			}
		}
	}
	private void inithome(View v) {
		LinearLayout newhomeheader=(LinearLayout) View.inflate(activity, R.layout.newhomeheader,null);
		home_pull = (PullToRefreshListView) v.findViewById(R.id.home_pull);
		home_pull.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				 loadDataHandler.sendEmptyMessage(PULL_DOWN_MODE);
			}
		});
		bannerFragment = new BannerFragment();
		bannerFragment.setBannerCliclkListner(new BannerCliclkListner() {
			
			@Override
			public void click(AdEntity entiy) {
				switch (entiy.getExtField1()) {
				case 0:
					
					break;
				case 1:
					Intent intent1 = new Intent(activity, HomeHeaderActivity.class);
					intent1.putExtra("type", HeaderActivityType.AboutUs.ordinal());
					intent1.putExtra("url",entiy.getExtField2() );
					intent1.putExtra("title", entiy.getExtField3());
					startActivity(intent1);
					break;
				case 2:
					Intent intent=new Intent(activity, HomeHeaderActivity.class);
					intent.putExtra("productId",Integer.parseInt(entiy.getExtField2()));
	                intent.putExtra("type",HeaderActivityType.ProductDetail.ordinal());
	                startActivity(intent);
					break;
				case 3:
					EventBus.getDefault().post(new UpdateClassifyEntity(Integer.parseInt(entiy.getExtField2()), "updateClassify"));
					((HomeActivity)activity).showTabByIndex(1);
					break;
				case 4:
					openNewActivityWithHeader(HeaderActivityType.Recharge.ordinal(), true);
					break;
				
				default:
					break;
				}
			}
		});
		activity.getSupportFragmentManager().beginTransaction().replace(R.id.fm_banner, bannerFragment).commitAllowingStateLoss();
		ll_classfy = (LinearLayout) newhomeheader.findViewById(R.id.ll_classfy);
		home_pull.getRefreshableView().addHeaderView(newhomeheader);
		homeClassifyadapter = new Homeadapter();
		home_pull.setAdapter(homeClassifyadapter);
		loadDataHandler.sendEmptyMessage(PULL_DOWN_MODE);
		home_pull.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				FullGiftEntity fullGiftEntity =(FullGiftEntity) homeClassifyadapter.getItem(Integer.parseInt(arg3+""));
				
				Intent intent=new Intent(activity, HomeHeaderActivity.class);
				
                intent.putExtra("productId",fullGiftEntity.getUrl());
                intent.putExtra("type",HeaderActivityType.ProductDetail.ordinal());
              //  intent.putExtra("hasHeader",false);
                startActivity(intent);
				
			}
		});
    } 
	
	
	 private void openNewActivityWithHeader(int type,Boolean needLogin){
 		Intent intent=new Intent(activity,HomeHeaderActivity.class);
         intent.putExtra("type",type);
         intent.putExtra("needLogin", needLogin);
         startActivity(intent);
    
 }
	
	class Homeadapter extends BaseAdapter{

		@Override
		public int getCount() {
			
			return referralsList.size();
		}

		@Override
		public Object getItem(int position) {
			
			return referralsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(activity, R.layout.home_item, null);
			ImageView imageView=(ImageView) view.findViewById(R.id.image);
			String icon = referralsList.get(position).getBody();
			finalBitmap.display(imageView, icon);
			return view;
		}
		
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	EventBus.getDefault().register(this);
    	super.onCreate(savedInstanceState);
    }
    @Override
    public void onDestroy() {
    	EventBus.getDefault().unregister(this);
    	super.onDestroy();
    }
  

    public void onEventMainThread(Boolean item){}
    
    public void onEventMainThread(Integer id){}
    public void onEventMainThread(UpdateClassifyEntity entity){}
    public void onEventMainThread(String type){
    	if (type.equals("login")) {
    		loadDataHandler.sendEmptyMessage(PULL_DOWN_MODE);
		}
    }
	@Override
    protected void reload() {
        startLoading();
        loadDataHandler.sendEmptyMessage(PULL_DOWN_MODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean iswifi= NetworkUtil.isWiFiActive(activity);
        boolean ismobile=NetworkUtil.isMobileConnected(activity);
        Log.d("net", "network is connected?  " + NetworkUtil.isConnected(activity) + " is wifi? " + iswifi + " is mobile ?" + ismobile);
        
    }
    @Override
    public void onPause() {
    	EventBus.getDefault().post(true);
    	super.onPause();
    }
    /**
     * 搜索
     */
    private void initHeaderView(){
        et.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, SearchActivity.class);
                startActivity(intent);
            }
        });
     }
  
    

    private Handler loadDataHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PULL_DOWN_MODE:
                	checkAddress();
                    break;
                case PULL_UP_MODE:
                    //fetchHomeData(false);
                    break;
               
                case 1000:
                	EventBus.getDefault().post(new UpdateClassifyEntity(Cateid, "updateClassify"));
					
                    break;
               
                 

            }
        }
        
	
    };
   private  ArrayList<FullGiftEntity> referralsList=new ArrayList<FullGiftEntity>();
   private int Cateid=0;
	private void fetchHomeData() {
		BaseRequestClient client=new BaseRequestClient(activity);
		client.httpPostByJsonNew("PhoneGethomepage", baseApplication.getUserResult(), new BaseRequest(), PhoneGethomepageResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneGethomepageResult>() {

			@Override
			public void callBack(PhoneGethomepageResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						ArrayList<AdEntity> list = result.getBannersList();
						bannerFragment.setBannerEntityList(list);
						ArrayList<HomeCategorie> list2 = result.getHomeCategories();
						ll_classfy.removeAllViews();
						for (final HomeCategorie homeCategorie : list2) {
							
							View view= View.inflate(activity, R.layout.homeclassify_item, null);
							view.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									//showtoast(homeCategorie.getName()+"", activity);
									Cateid=homeCategorie.getCateid();
									loadDataHandler.sendEmptyMessageDelayed(1000, 500);
									((HomeActivity)activity).showTabByIndex(1);
								}
							});
							ImageView img=(ImageView) view.findViewById(R.id.image);
							TextView tv_name=(TextView) view.findViewById(R.id.tv_name);
							tv_name.setText(homeCategorie.getName());
							finalBitmap.display(img, homeCategorie.getIcon());
							ll_classfy.addView(view);
						}
						referralsList.clear();
						homeClassifyadapter.notifyDataSetChanged();
						referralsList.addAll(result.getReferralsList());
						homeClassifyadapter.notifyDataSetChanged();
						endLoading();
					}else {
						showtoast(result.getMessage(), activity);	
						failLoading();
					}
				}else {
					showtoast(activity.getResources().getString(R.string.data_error), activity);
					failLoading();
				}
				home_pull.onRefreshComplete();
			}

			@Override
			public void loading(long count, long current) {}

			@Override
			public void logOut(PhoneGethomepageResult result) {

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
		});
		
	}
	private String address;

	private MainAdapter vpAdapter;
	/*
	 * 获取默认地址
	 */
	private void checkAddress() {
		BaseRequestClient client=new BaseRequestClient(activity);
		BaseRequest request=new BaseRequest();
		client.httpPostByJsonNew("PhoneGetDefaultAddress", baseApplication.getUserResult(), request, PhoneGetDefaultAddressResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneGetDefaultAddressResult>() {

			@Override
			public void callBack(PhoneGetDefaultAddressResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						if (result.getShipAddress()!=null) {
							isAdd=false;
							//tv_address.setText("您当前选中的的收货地址是："+result.getPlot().getName()+"，下单后将无法修改收货地址，请预先选定好收货地址！");
							tv_address.setText(result.getPlot().getName());
							address=result.getPlot().getName();
							
						}else {
							plot = result.getPlot();
							//tv_address.setText("您当前选中的的收货地址是："+result.getPlot().getName()+"，下单后将无法修改收货地址，请预先选定好收货地址！");
							tv_address.setText(result.getPlot().getName());
							address=result.getPlot().getName();
							isAdd=true;
						}
						tv_newAddress.setText(address);
						//vpAdapter.notifyDataSetChanged();
						fetchHomeData();
		            }else {
						failLoading();
						showtoast(result.getMessage(), activity);
					}
				}else {
					failLoading();
					showtoast(activity.getResources().getString(R.string.data_error), activity);
				}
			}

			@Override
			public void loading(long count, long current) {
				
			}

			@Override
			public void logOut(PhoneGetDefaultAddressResult result) {
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
		});
	
	}
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode==100&&resultCode==200) {
			
		}else if (requestCode==200&&resultCode==Activity.RESULT_OK) {
			AddressEntity entity=(AddressEntity) data.getSerializableExtra("addressEntity");
			//tv_address.setText("您当前选中的的收货地址是："+entity.getPlotName()+"，下单后将无法修改收货地址，请预先选定好收货地址！");
			tv_address.setText(entity.getPlotName());
			address=entity.getPlotName();
			tv_newAddress.setText(address);
			//vpAdapter.notifyDataSetChanged();
			EventBus.getDefault().post("login");
		}else if (requestCode==300&&resultCode==Activity.RESULT_OK) {
			AddressEntity entity=(AddressEntity) data.getSerializableExtra("addressEntity");
        }
    }


   

    
	

}
