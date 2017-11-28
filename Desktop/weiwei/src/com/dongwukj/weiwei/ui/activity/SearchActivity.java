package com.dongwukj.weiwei.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baidu.location.LLSInterface;
import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.NewHomeListAdapter;
import com.dongwukj.weiwei.adapter.NewHomeListAdapter.HomeClick;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.AddToCartRequest;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.dongwukj.weiwei.idea.request.PhoneGetMarketGoodsRequest;
import com.dongwukj.weiwei.idea.request.PhoneGetProductsRequest;
import com.dongwukj.weiwei.idea.request.linkedwordsRequest;
import com.dongwukj.weiwei.idea.result.*;
import com.dongwukj.weiwei.idea.result.linkedwordsResult.keyWordEntity;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.CategoryRequestClient;
import com.dongwukj.weiwei.net.ShoppingCartRequestClient;
import com.dongwukj.weiwei.net.ShoppingCartRequestClient.AddShoppingCartRequestClientCallback;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.widget.BadgeView;
import com.dongwukj.weiwei.ui.widget.MyGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

	protected static final int EMPTY = 100;
	protected static final int PULL_FROM_UP = 101;
	protected static final int PULL_FROM_DOWN = 102;
	protected static final int TYPE2 = 2;
	private static final int TYPE3 = 3;
	protected static final int TYPE1 = 1;
	private LinearLayout img;
	private AutoCompleteTextView auto;
	private MyGridView gd_new;
	private MyGridView gd_near;
	private String[] keywords_new={};
	private String[] keywords_linked;
	//private ArrayList<keyWordEntity> keyWords;
	private MyBaseAdapter adBaseAdapter;
	private ArrayAdapter<String> adapter;
	private LinearLayout ll_wrong;
	private boolean isShow=false;
	private boolean want_show=true;
	private String keyword_new;
    private ArrayList<NewHomeEntity> products=new ArrayList<NewHomeEntity>();
	private PullToRefreshListView pg;
	private FinalBitmap fb;
	//private MyBaseAdapterForPg pgAdapterForPg;
	private FinalDb db;
	private String[] keywords_histories;
	private ScrollView sl;
	private TextView tv_name;//搜索不到商品的名字
	private TextView tv_delete;//删除搜索内容
	//private BadgeView buyNumView;
	//private int buyNum;
	StringBuilder builder=new StringBuilder();
	List<ProductInfoEntity> list=new ArrayList<ProductInfoEntity>();
	@Override
	protected void findViewById() {
		RadioGroup rg=(RadioGroup) findViewById(R.id.tabs_rg);
		tv_empty2 = (TextView) findViewById(R.id.tv_empty2);
		tv_empty1 = (TextView) findViewById(R.id.tv_empty1);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				for (int i = 0; i < group.getChildCount(); i++) {
					if (group.getChildAt(i).getId()==checkedId) {
						Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
						intent.putExtra("homeTab", i);
						startActivity(intent);
						finish();
					}
				}
				
			}
		});
		Button radioButton = (Button) findViewById(R.id.bt);
		radioButtonBadgeView = new BadgeView(getApplicationContext(),radioButton);
    	radioButtonBadgeView.setTextColor(Color.WHITE);
    	radioButtonBadgeView.setTextSize(10);
		
		
		
		bt_clear = (Button) findViewById(R.id.bt_clear);
		bt_clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				List<SearchHistoryEntity> SearchHistoryEntitys = db.findAll(SearchHistoryEntity.class);
				if (SearchHistoryEntitys!=null&&SearchHistoryEntitys.size()>0) {
					db.deleteAll(SearchHistoryEntity.class);
					keywords_histories=new String[0];
					historyAdapter.setkeywords(keywords_histories);
					tv_empty1.setVisibility(View.VISIBLE);
					bt_clear.setBackgroundResource(R.drawable.weiwei_button_gary_gary);
					bt_clear.setTextColor(getResources().getColor(R.color.gray));
				}
			}
		});
		img = (LinearLayout) findViewById(R.id.ll_left);
		auto = (AutoCompleteTextView) findViewById(R.id.autotext);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_delete = (TextView) findViewById(R.id.delete);
		tv_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				auto.setText("");
			}
		});
		/*auto.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				  Drawable drawable = auto.getCompoundDrawables()[2];
	                if (drawable == null)
	                    return false;
	                 
	                if (event.getAction() != MotionEvent.ACTION_UP)
	                    return false;
	                
	                //drawable.getIntrinsicWidth() 获取drawable资源图片呈现的宽度
	                if (event.getX() > auto.getWidth() - auto.getPaddingRight() 
	                                - drawable.getIntrinsicWidth() ) {
	                        //进入这表示图片被选中，可以处理相应的逻辑了
	                	Toast.makeText(getApplicationContext(), "12321321", 0).show();
	                	auto.setDrawingCacheEnabled(false);
	                	return true;
	                }
	                                
	                                return false;
			}
		});*/
		 ll_wrong=(LinearLayout) findViewById(R.id.ll_wrong);
		gd_new = (MyGridView) findViewById(R.id.gd_new);
		gd_near = (MyGridView) findViewById(R.id.gd_near);
		//rl = (RelativeLayout) findViewById(R.id.rl);
		adBaseAdapter = new  MyBaseAdapter(keywords_new);
		//list_header_rightbutton = (TextView) findViewById(R.id.list_header_rightbutton);
		Button search=(Button) findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(auto.getText().toString().trim())) {
					Toast.makeText(getApplicationContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
				}else {
					isShow=true;
					want_show=false;          
					updateUIHandler.sendEmptyMessageDelayed(103, 10);
					InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
					imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					SearchHistoryEntity entity=new SearchHistoryEntity();
					entity.setKeyword(auto.getText().toString().trim().replace("'", "''"));
					entity.setTime(System.currentTimeMillis());
					saveHistoryToDb(entity);
				}
			}
		});
		gd_new.setAdapter(adBaseAdapter);
		List<SearchHistoryEntity> SearchHistoryEntitys = db.findAll(SearchHistoryEntity.class,"time DESC limit 9");
		//db.findAllByWhere(SearchHistoryEntity.class, "order by time DESC limit 10");
		if (SearchHistoryEntitys!=null&&SearchHistoryEntitys.size()>0) {
			for (SearchHistoryEntity searchHistoryEntity : SearchHistoryEntitys) {
				builder.append(searchHistoryEntity.getKeyword()+"/./");
			}
			keywords_histories =builder.toString().trim().split("/./");
			historyAdapter = new MyBaseAdapter(keywords_histories);
			gd_near.setAdapter(historyAdapter);
		}else {
			tv_empty1.setVisibility(View.VISIBLE);
			bt_clear.setBackgroundResource(R.drawable.weiwei_button_gary_gary);
			bt_clear.setTextColor(getResources().getColor(R.color.gray));
		}
		sl = (ScrollView) findViewById(R.id.sl);
		prodcutAdapte = new NewHomeListAdapter(this,products,new HomeClick() {
			
			@Override
			public void jian(int goodId, int num) {
				QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
				qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId(),goodId});
				ArrayList<NewHomeEntity> arrayList = basedb.query(qb);
				if (arrayList!=null&&arrayList.size()>0) {
					arrayList.get(0).setCount(num);
					basedb.update(arrayList.get(0));
				}
				for (NewHomeEntity newHomeEntity : products) {
		  			if (goodId==newHomeEntity.getBusinessgoodsid()) {
		  				newHomeEntity.setCount(num);
					}
		  		}
				setBuycount(getCount());
			}
			
			@Override
			public void jia(int goodId, int num, NewHomeEntity homeEntity) {

				QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
				
				String account = baseApplication.getUserResult().getUserAccount();
				 int marketId = baseApplication.getUserResult().getMarketId();
				//qb.where("businessgoodsid=?", new Integer[]{goodId});
				qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(account),marketId,goodId});
				ArrayList<NewHomeEntity> arrayList = basedb.query(qb);
				if (arrayList!=null&&arrayList.size()>0) {
					arrayList.get(0).setCount(num);
					long millis = System.currentTimeMillis();
					arrayList.get(0).setAddTime(millis);
					basedb.update(arrayList.get(0));
				}else {
					homeEntity.setCount(num);
					long millis = System.currentTimeMillis();
					homeEntity.setAddTime(millis);
					homeEntity.setMarketId(marketId);
					homeEntity.setUserAccount(Integer.parseInt(account));
					basedb.insert(homeEntity);
				}
				for (NewHomeEntity newHomeEntity : products) {
		  			if (goodId==newHomeEntity.getBusinessgoodsid()) {
		  				newHomeEntity.setCount(num);
					}
		  		}
				setBuycount(getCount());
			}
			
			@Override
			public void delete(Integer id) {

				QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
				qb.where("userAccount=? and marketId=? and businessgoodsid=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId(),id});
				ArrayList<NewHomeEntity> arrayList = basedb.query(qb);
				if (arrayList!=null&&arrayList.size()>0) {
					basedb.delete(arrayList.get(0));
				}
				for (NewHomeEntity newHomeEntity : products) {
		  			if (id==newHomeEntity.getBusinessgoodsid()) {
		  				newHomeEntity.setCount(0);
					}
		  		}
				EventBus.getDefault().post(id);
				setBuycount(getCount());
			}

			@Override
			public void ItemClick(int gid) {
				  Intent intent=new Intent(getApplicationContext(), HomeHeaderActivity.class);
	                intent.putExtra("productId",gid);
	                intent.putExtra("type",HeaderActivityType.ProductDetail.ordinal());
	                //intent.putExtra("hasHeader",false);
	                startActivity(intent);
			}
		});
		auto.setThreshold(1);  
		auto.setLinkTextColor(getResources().getColor(R.color.black));
		auto.setOnFocusChangeListener(new OnFocusChangeListener() {  
			            @Override  
			             public void onFocusChange(View v, boolean hasFocus) {  
			                AutoCompleteTextView view = (AutoCompleteTextView) v;  
			               if (!hasFocus) {  
			            	   auto.dismissDropDown();
			                 }  
			           }   
			         });  
		 auto.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode==KeyEvent.KEYCODE_ENTER) {
					if (TextUtils.isEmpty(auto.getText().toString().trim())) {
						Toast.makeText(getApplicationContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
					}else {
						isShow=true;
						want_show=false;          
						updateUIHandler.sendEmptyMessageDelayed(103, 10);
						InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
						imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
						SearchHistoryEntity entity=new SearchHistoryEntity();
						entity.setKeyword(auto.getText().toString().trim());
						entity.setTime(System.currentTimeMillis());
						saveHistoryToDb(entity);
					}
					
				}
				return false;
			}
		});
		auto.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				isShow=true;
				want_show=false;
				updateUIHandler.sendEmptyMessageDelayed(103, 10);
				InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				SearchHistoryEntity entity=new SearchHistoryEntity();
				entity.setKeyword(auto.getText().toString().trim());
				entity.setTime(System.currentTimeMillis());
				saveHistoryToDb(entity);
			}
		});
		auto.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				if (!TextUtils.isEmpty(auto.getText().toString().trim())) {
					tv_delete.setVisibility(View.VISIBLE);
					getPhonelinkedword(auto.getText().toString().trim());
					want_show=true;
				}else {
					tv_delete.setVisibility(View.GONE);
				}
			}
		});
		gd_new.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				isShow=true;
				keyword_new=keywords_new[position];
				updateUIHandler.sendEmptyMessageDelayed(102,10);
				SearchHistoryEntity entity=new SearchHistoryEntity();
				entity.setKeyword(keyword_new);
				entity.setTime(System.currentTimeMillis());
				saveHistoryToDb(entity);
			}
		});
		gd_near.setOnItemClickListener(new OnItemClickListener() {

		
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				isShow=true;
				keyword_history = keywords_histories[position];
				updateUIHandler.sendEmptyMessageDelayed(101,10);
				SearchHistoryEntity entity=new SearchHistoryEntity();
				entity.setKeyword(keyword_history);
				entity.setTime(System.currentTimeMillis());
				saveHistoryToDb(entity);
			}
		});
		img.setOnClickListener(this);
		pg = (PullToRefreshListView) findViewById(R.id.gd_detail);
		pg.setAdapter(prodcutAdapte);
		pg.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getDataHandler.sendEmptyMessage(PULL_FROM_UP);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getDataHandler.sendEmptyMessage(PULL_FROM_DOWN);
			}
		});
		/*pg.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Integer pId = list.get(position).getpId();
				 Intent intent=new Intent(getApplicationContext(), NewProductDetailActivity.class);
	                intent.putExtra("productId",pId);
	                startActivity(intent);

                Intent intent=new Intent(SearchActivity.this, HomeHeaderActivity.class);
                intent.putExtra("productId",pId);
                intent.putExtra("type", HeaderActivityType.ProductDetail.ordinal());
                intent.putExtra("hasHeader",false);
                startActivity(intent);
				NewHomeEntity entity=(NewHomeEntity) prodcutAdapte.getItem(Integer.parseInt(id+""));
				  Intent intent=new Intent(getApplicationContext(), HomeHeaderActivity.class);
                intent.putExtra("productId",entity.getGid());
                intent.putExtra("type",HeaderActivityType.ProductDetail.ordinal());
                //intent.putExtra("hasHeader",false);
                startActivity(intent);
			}
		});*/
		getHotKey();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	   
    public void onEventMainThread(Integer id)
  	{	
      	for (NewHomeEntity newHomeEntity : products) {
  			if (id==newHomeEntity.getBusinessgoodsid()) {
  				newHomeEntity.setCount(0);
  				newHomeEntity.setIsmain(1);
			}
  		}
      	prodcutAdapte.notifyDataSetChanged();
  	}
    public void onEventMainThread(Boolean item)
	{	
    	QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
		qb.where("userAccount=? and marketId=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId()});
		ArrayList<NewHomeEntity> list = basedb.query(qb);
    	for (NewHomeEntity newHomeEntity : products) {
			for (NewHomeEntity Entity : list) {
				if (newHomeEntity.getBusinessgoodsid()==Entity.getBusinessgoodsid()) {
					newHomeEntity.setCount(Entity.getCount());
					newHomeEntity.setIsmain(Entity.getIsmain());
				}
			}
		}
    	prodcutAdapte.notifyDataSetChanged();
	}
    public void setBadgeViewText(String text){
		
		if (Integer.parseInt(text)>=99) {
			radioButtonBadgeView.setTextSize(9);
			radioButtonBadgeView.setText("99+");
		}else {
			radioButtonBadgeView.setTextSize(12);
			radioButtonBadgeView.setText(text);
		}
		radioButtonBadgeView.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
		if (!text.equals("0")) {
			radioButtonBadgeView.show();
		}else {
			radioButtonBadgeView.hide();
		}
		
	}
    public void setBuycount(int count){
		setBadgeViewText(count+"");
	}
	public int getCount(){
		int num=0;
    	QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
    	qb.where("userAccount=? and marketId=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId()});
		ArrayList<NewHomeEntity> arrayList = basedb.query(qb);
		for (NewHomeEntity newHomeEntity : arrayList) {
			num=num+newHomeEntity.getCount();
		}
		return arrayList.size();
	}
	private String keyword_history;
	@Override
	protected void onResume() {
		if (!isFirst) {
			DataBase db = LiteOrm.newCascadeInstance(getApplicationContext(), baseApplication.DB_NAME);
			QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
			qb.where("userAccount=? and marketId=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId()});
			ArrayList<NewHomeEntity> arrayList = db.query(qb);
			for (NewHomeEntity newHomeEntity : arrayList) {
				if (newHomeEntity.getAddTime()<baseApplication.getWeeTimew()) {
					db.delete(newHomeEntity);
					pg.setMode(Mode.PULL_FROM_START);
					pg.setRefreshing();
					EventBus.getDefault().post("shuaxin");
				}
			}
		}
		
		setBuycount(getCount());
		super.onResume();
	    MobclickAgent.onPageStart("Search"); 
	    MobclickAgent.onResume(this);
	}
	public void onEventMainThread(String type) {
		if (type.equals("shuaxin")) {
			pg.setMode(Mode.PULL_FROM_START);
			pg.setRefreshing();
			
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		EventBus.getDefault().post(true);
		isFirst=false;
		super.onPause();
		 MobclickAgent.onPageEnd("Search");
		 MobclickAgent.onPause(this);
	}
	protected void saveHistoryToDb(SearchHistoryEntity entity) {
		List<SearchHistoryEntity> findAllByWhere = db.findAllByWhere(SearchHistoryEntity.class, "keyword='"+entity.getKeyword()+"'");
		if (findAllByWhere.size()==0) {
			db.save(entity);
		}else {
			db.update(entity,"keyword='"+entity.getKeyword()+"'");
		}
	}
	private void changelayout(int type){
		sl.setVisibility(View.GONE);
		pg.getRefreshableView().setVisibility(View.VISIBLE);
			/*sl.setVisibility(View.GONE);
			pg.getRefreshableView().setVisibility(View.VISIBLE);
			//rl.setVisibility(View.VISIBLE);
			list_header_rightbutton.setVisibility(View.VISIBLE);
			list_header_rightbutton.setBackgroundResource(R.drawable.weiwei_gouwuche_checked);
			buyNumView=new BadgeView(getApplicationContext(), rl);
	    	buyNumView.setTextColor(Color.WHITE);
	        buyNumView.setTextSize(10);
	        buyNum = baseApplication.getCartCount();
	        setBadgeViewText(buyNum+"");
	        list_header_rightbutton.setOnClickListener( new OnClickListener() {
				@Override
				public void onClick(View v) {
					openNewActivityWithHeader(HeaderActivityType.AddShopCart.ordinal(), true, false);
				}
			});*/
			Message msg=Message.obtain();
			msg.obj=type;
			msg.what=EMPTY;
			getDataHandler.sendMessage(msg);
		
	}
	/*private void openNewActivityWithHeader(int type,boolean isneedlogin,boolean hasheader) {
        Intent intent = new Intent(getApplicationContext(), HomeHeaderActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("needLogin", isneedlogin);
        intent.putExtra("hasHeader", hasheader);
        intent.putExtra("isFromDetails", true);
        startActivity(intent);
    }*/
	/* private void setBadgeViewText(String buyNum) {
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
	        
		}*/

	/*protected void onResume() {
		buyNum=baseApplication.getCartCount();
		if (buyNumView!=null) {
			setBadgeViewText(buyNum+"");
		}
    	super.onResume();
	}*/
	private Handler updateUIHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 103:
				auto.dismissDropDown(); 
				changelayout(TYPE3);
				break;
			case 102:
				changelayout(TYPE2);
				break;
			case 101:
				changelayout(TYPE1);
				break;
				
			default:
				break;
			}
		};
	};
	private int bigsize;
	
	private NewHomeResult resultEntity;
	protected void GetProducts(final boolean isFirst,final String keyword) {
		PhoneGetMarketGoodsRequest request=new PhoneGetMarketGoodsRequest();
		//request.setAddNum(10);
        request.setKeyWord(keyword);
		if (isFirst) {
			request.setPageIndex(1);
		}else {
			request.setPageIndex(++pagetype);
		}

        CategoryRequestClient categoryRequestClient=new CategoryRequestClient(this,baseApplication);
        categoryRequestClient.searchOrListDetailCategory(request, new CategoryRequestClient.CategoryRequestCallback() {
            @Override
            protected void detailList(NewHomeResult result) {

				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						resultEntity=result;
						prodcutAdapte.setEntity(resultEntity);
						if (result.getGoodsList().size()==0) {
							products.clear();
							prodcutAdapte.notifyDataSetChanged();
							ll_wrong.setVisibility(View.VISIBLE);
							pg.setVisibility(View.GONE);
							  tv_name.setText(Html.fromHtml("没有找到与<font color='red'>"+"\""+keyword+"\""+"</font>相关的宝贝"));
			                 pg.onRefreshComplete();
			                 return;
						}
						ll_wrong.setVisibility(View.GONE);
			            pg.setVisibility(View.VISIBLE);

						ArrayList<NewHomeEntity> list = result.getGoodsList();
						QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
						qb.where("userAccount=? and marketId=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId()});
						ArrayList<NewHomeEntity> arrayList = basedb.query(qb);
						if (arrayList.size()>0) {
							for (NewHomeEntity Entity : list) {
								for (NewHomeEntity newHomeEntity : arrayList) {
									if (Entity.getBusinessgoodsid()==newHomeEntity.getBusinessgoodsid()) {
										Entity.setCount(newHomeEntity.getCount());
										Entity.setIsmain(newHomeEntity.getIsmain());
									}
								}
							}	
						}
						
						if (isFirst) {
							products.clear();
							prodcutAdapte.notifyDataSetChanged();
							products.addAll(list);
							prodcutAdapte.notifyDataSetChanged();
							pagetype=1;
						}else {
							
							products.addAll(result.getGoodsList());
							prodcutAdapte.notifyDataSetChanged();
						}
						 	pg.onRefreshComplete();
			 				if (products.size()>=result.getTotal()) {
			 					pg.setMode(Mode.PULL_FROM_START);
							}else {
								pg.setMode(PullToRefreshBase.Mode.BOTH);
							}
					}else {
						Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
					}
					
				}else {
					Toast.makeText(getApplicationContext(),getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
 				 pg.onRefreshComplete();
 				
            }
            @Override
            protected void LogOut(BaseResult result) {
            	
            	pg.onRefreshComplete();
				FinalDb finalDb = FinalDb.create(getApplicationContext());
				List<UserResult> findAllByWhere = finalDb
						.findAllByWhere(UserResult.class, "isLogin=1");
				for (UserResult userResult : findAllByWhere) {
					userResult.setLogin(false);
					finalDb.update(userResult);
				}
				baseApplication.setUserResult(null);
				Intent intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				intent.putExtra("isLoginOut", true);
				startActivity(intent);
				super.LogOut(result);
			 }
            @Override
            protected void detailListComplete(NewHomeResult result) {
               
            }
        });


		
	}
	private String keyword;
	private int pagetype=1;
	private Handler getDataHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case EMPTY:
				int type=(Integer) msg.obj;
				switch (type) {
				case 1:
					keyword=keyword_history;
					break;
				case TYPE2:
					keyword=keyword_new;
					break;
				case TYPE3:
					keyword=auto.getText().toString().trim();
					break;

				default:
					break;
				}
				pg.setRefreshing();
				pg.setMode(Mode.BOTH);
				break;
			case PULL_FROM_UP:
				GetProducts(true,keyword);
				break;
			case PULL_FROM_DOWN:
				GetProducts(false,keyword);
				break;

			default:
				break;
			}
		};
	};
	private MyBaseAdapter historyAdapter;
	private DataBase basedb;
	private NewHomeListAdapter prodcutAdapte;
	private BadgeView radioButtonBadgeView;
	private TextView tv_empty1;
	private TextView tv_empty2;
	private Button bt_clear;
	
	
	//private RelativeLayout rl;
	//private TextView list_header_rightbutton;
	protected void getPhonelinkedword(String string) {
		BaseRequestClient client=new BaseRequestClient(getApplicationContext());
		UserResult result = baseApplication.getUserResult();
		linkedwordsRequest request=new linkedwordsRequest();
//		request.setAddNum(10);
//		request.setKeyword(string);
		request.setKeyWord(string);
		client.httpPostByJson("PhoneGetKeyWord", result, request,linkedwordsResult.class, new BaseRequestClient.RequestClientCallBack<linkedwordsResult>() {

			@Override
			public void callBack(linkedwordsResult result) {

				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						if (result.getKeyWordObjects().size()>0) {
							ArrayList<keyWordEntity> wordObjects = result.getKeyWordObjects();
							keywords_linked=new String[wordObjects.size()];
							for (int i = 0; i <wordObjects.size(); i++) {
								keywords_linked[i]=wordObjects.get(i).getName();
							}
							adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.autotextview, keywords_linked);
							auto.setAdapter(adapter);
							if (want_show) {
								auto.showDropDown();
							}
						}
						
					}
				}else {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void loading(long count, long current) {
			}
			@Override
			public void logOut(boolean isLogOut,linkedwordsResult result) {FinalDb finalDb=FinalDb.create(getApplicationContext());
			List<UserResult> findAllByWhere = finalDb.findAllByWhere(
					UserResult.class, "isLogin=1");
			for (UserResult userResult : findAllByWhere) {
				userResult.setLogin(false);
				finalDb.update(userResult);
			}
			baseApplication.setUserResult(null);
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			intent.putExtra("isLoginOut", true);
			startActivity(intent);}
		});
	}
	private void getHotKey() {
		BaseRequestClient client=new BaseRequestClient(getApplicationContext());
		UserResult result = baseApplication.getUserResult();
		BaseRequest request=new BaseRequest();
		client.httpPostByJson("Phonetopword", result, request, HotKeyResult.class, new BaseRequestClient.RequestClientCallBack<HotKeyResult>() {

			@Override
			public void callBack(HotKeyResult result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						keywords_new=result.getKeywords();
						if (keywords_new.length<=0) {
							tv_empty2.setVisibility(View.VISIBLE);
						}
						adBaseAdapter.setkeywords(keywords_new);
					}
				}else {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void loading(long count, long current) {
			}
			@Override
			public void logOut(boolean isLogOut,HotKeyResult result) {FinalDb finalDb=FinalDb.create(getApplicationContext());
			List<UserResult> findAllByWhere = finalDb.findAllByWhere(
					UserResult.class, "isLogin=1");
			for (UserResult userResult : findAllByWhere) {
				userResult.setLogin(false);
				finalDb.update(userResult);
			}
			baseApplication.setUserResult(null);
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			intent.putExtra("isLoginOut", true);
			startActivity(intent);}
		});
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_left:
			finish();
			break;

		default:
			break;
		}
	}
	 private void addComBoToCart(int pmid,boolean iscombo) {
		 	AddToCartRequest request = new AddToCartRequest();
		 	if (iscombo) {
		 		 request.setGoodsNum("1");
			      request.setPmId(pmid+"");
			}else {
				 request.setGoodsNum("1");
			       request.setGoodsId(pmid);
			}
	       
			ShoppingCartRequestClient client=new ShoppingCartRequestClient(SearchActivity.this, baseApplication);
			client.addCart(request, new AddShoppingCartRequestClientCallback() {
				@Override
				protected void listSuccess(PhoneAddProductResult result) {
					Toast.makeText(getApplicationContext(), "商品加到购物车成功!", Toast.LENGTH_SHORT).show();
//					setBadgeViewText(baseApplication.getCartCount()+"");
	            }
			});
		}
	 private boolean isLogin() {
	        UserResult result = baseApplication.getUserResult();
	        if (result != null && result.getUserAccount() != null) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	public void checkedIsloginToBuy(int pmid,boolean iscombo){
		 if (isLogin()) {
				addComBoToCart(pmid,iscombo);
			}else {
				showdialog();
			}
	 }
	 public void showdialog() {
	        final Dialog dialog = new Dialog(SearchActivity.this, R.style.Dialog);
	        //View view = View.inflate(getApplicationContext(), R.layout.ordercancle_dialog, null);
	        dialog.setContentView(R.layout.ordercancle_dialog);
	        dialog.setCancelable(false);
	        TextView tv_cancle = (TextView) dialog.findViewById(R.id.tv_cancle);
	        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);
	        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
	        
			WindowManager m = getWindowManager();
	        Window dialogWindow = dialog.getWindow();
	        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
	        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
	        p.height = (int) (d.getHeight() * 0.36); // 高度设置为屏幕的0.30
	        p.width = (int) (d.getWidth() * 0.80); // 宽度设置为屏幕的0.80
	        dialogWindow.setAttributes(p);
	        
	        tv_title.setText("是否立即登录？");
	        tv_ok.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                dialog.dismiss();
	            }
	        });
	        tv_cancle.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                dialog.dismiss();
	                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
	                startActivity(intent);
	            }
	        });
	        dialog.show();

	    }
	 private boolean isFirst=true;
	@Override
	protected void initView() {
		
		setContentView(R.layout.activity_search);
		fb = FinalBitmap.create(getApplicationContext());
		db = FinalDb.create(this);
		basedb = LiteOrm.newCascadeInstance(this, baseApplication.DB_NAME);
	}
	private class MyBaseAdapter extends BaseAdapter{
		private String[] keywords;
		
		public void setkeywords(String[] keywords){
			this.keywords = keywords;
			notifyDataSetChanged();
		}
		
		public MyBaseAdapter(String[] keywords) {
			super();
			this.keywords = keywords;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return keywords.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return keywords[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(), R.layout.search_textview, null);
			TextView tv=(TextView) view.findViewById(R.id.tv);
			if (keywords.length>0) {
				tv.setText(keywords[position]);
			}
			return view;
		}
	}

	
}
