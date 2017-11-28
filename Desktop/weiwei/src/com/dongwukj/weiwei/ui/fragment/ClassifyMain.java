package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.NewHomeListAdapter;
import com.dongwukj.weiwei.adapter.NewHomeListAdapter.HomeClick;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.dongwukj.weiwei.idea.request.NewHomeRequest;
import com.dongwukj.weiwei.idea.result.AddressEntity;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.CategoryEntity;
import com.dongwukj.weiwei.idea.result.EventBusEntity;
import com.dongwukj.weiwei.idea.result.NewHomeResult;
import com.dongwukj.weiwei.idea.result.PhoneGetDefaultAddressResult;
import com.dongwukj.weiwei.idea.result.Plot;
import com.dongwukj.weiwei.idea.result.UpdateClassifyEntity;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.HomeActivity;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ClassifyMain extends BaseFragment {

	protected static final int PULL_DOWN_MODE = 100;
	protected static final int PULL_UP_MODE = 200;
	private int cateId = 0;
	private int pageIndex = 1;
	private DrawerLayout dw;
	private TextView tv_address;
	private boolean isAdd;
	private Plot plot = null;
	private ArrayList<NewHomeEntity> newHomeLists = new ArrayList<NewHomeEntity>();
	 private boolean isFirst=true;
	public ClassifyMain(DrawerLayout dw, TextView tv_address) {
		this.dw = dw;
		this.tv_address = tv_address;
	}
	@Override
	public void onPause() {
		isFirst=false;
		super.onPause();
	}
	@Override
	public View setView_parent(LayoutInflater inflater) {

		return inflater.inflate(R.layout.classifymain, null);
	}

	@Override
	public void setListener() {

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

	public void onEventMainThread(EventBusEntity entity) {
		if (entity.isClassifyMenu()) {
			this.cateId = entity.getCateId();
			refreshListView.setMode(Mode.PULL_FROM_START);
			refreshListView.setRefreshing();
		}
	}

	public void onEventMainThread(String type) {
		if (type.equals("login")) {
			checkAddress();
			cateId=0;
			newHomeLists.clear();
			newHomeListAdapter.notifyDataSetChanged();
			refreshListView.setMode(Mode.PULL_FROM_START);
			refreshListView.setRefreshing();
			
		}else if (type.equals("shuaxin")) {
			refreshListView.setMode(Mode.PULL_FROM_START);
			refreshListView.setRefreshing();
		}
	}
	
	
	public void onEventMainThread(Integer id) {
		for (NewHomeEntity newHomeEntity : newHomeLists) {
			if (id == newHomeEntity.getBusinessgoodsid()) {
				newHomeEntity.setCount(0);
				newHomeEntity.setIsmain(1);
			}
		}
		newHomeListAdapter.notifyDataSetChanged();
	}

	public void onEventMainThread(Boolean item) {
		if (baseApplication.getUserResult() == null) {
			return;
		}
		QueryBuilder qb = new QueryBuilder(NewHomeEntity.class);
		qb.where(
				"userAccount=? and marketId=?",
				new Integer[] {
						Integer.parseInt(baseApplication.getUserResult()
								.getUserAccount()),
						baseApplication.getUserResult().getMarketId() });
		ArrayList<NewHomeEntity> list = db.query(qb);
		for (NewHomeEntity newHomeEntity : newHomeLists) {
			for (NewHomeEntity Entity : list) {
				if (newHomeEntity.getBusinessgoodsid() == Entity
						.getBusinessgoodsid()) {
					newHomeEntity.setCount(Entity.getCount());
					newHomeEntity.setIsmain(Entity.getIsmain());
				}
			}
		}
		newHomeListAdapter.notifyDataSetChanged();
	}

	@Override
	public void initview() {
		tv_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isAdd) {
					PhoneGetPlotResult result = new PhoneGetPlotResult();
					result.setPlot(plot);
					Intent intent = new Intent(activity,
							HomeHeaderActivity.class);
					intent.putExtra("type",
							HeaderActivityType.NewAddress.ordinal());
					intent.putExtra("fromorder", result);
					startActivityForResult(intent, 300);
				} else {
					Intent intent = new Intent(activity,
							HomeHeaderActivity.class);
					intent.putExtra("type",
							HeaderActivityType.Address.ordinal());
					intent.putExtra("needLogin", true);
					intent.putExtra("fromHome", true);
					startActivityForResult(intent, 200);
				}

			}
		});
		ll_wrong = (LinearLayout) view_parent.findViewById(R.id.ll_wrong);
		img_xiangshang = (ImageView) view_parent.findViewById(R.id.img_xiangshang);
		img_xiangshang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refreshListView.getRefreshableView().setSelection(0);
			}
		});
		db = LiteOrm.newCascadeInstance(activity, baseApplication.DB_NAME);
		refreshListView = (PullToRefreshListView) view_parent
				.findViewById(R.id.lv_product);
		refreshListView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						loadDataHandler.sendEmptyMessage(PULL_DOWN_MODE);
						img_xiangshang.setVisibility(View.GONE);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						loadDataHandler.sendEmptyMessage(PULL_UP_MODE);
						img_xiangshang.setVisibility(View.VISIBLE);
					}
				});
		newHomeListAdapter = new NewHomeListAdapter(activity,newHomeLists, 
				new HomeClick() {

					@Override
					public void jian(int goodId, int num) {
						QueryBuilder qb = new QueryBuilder(NewHomeEntity.class);
						qb.where(
								"userAccount=? and marketId=? and businessgoodsid=?",
								new Integer[] {
										Integer.parseInt(baseApplication
												.getUserResult()
												.getUserAccount()),
										baseApplication.getUserResult()
												.getMarketId(), goodId });
						ArrayList<NewHomeEntity> arrayList = db.query(qb);
						if (arrayList != null && arrayList.size() > 0) {
							arrayList.get(0).setCount(num);
							db.update(arrayList.get(0));
						}
						for (NewHomeEntity newHomeEntity : newHomeLists) {
							if (goodId == newHomeEntity.getBusinessgoodsid()) {
								newHomeEntity.setCount(num);
							}
						}
						((HomeActivity) activity)
								.setBuycount(((HomeActivity) activity)
										.getCount());

					}

					@Override
					public void jia(int goodId, int num, NewHomeEntity entity) {
						QueryBuilder qb = new QueryBuilder(NewHomeEntity.class);

						String account = baseApplication.getUserResult()
								.getUserAccount();
						int marketId = baseApplication.getUserResult()
								.getMarketId();
						// qb.where("businessgoodsid=?", new Integer[]{goodId});
						qb.where(
								"userAccount=? and marketId=? and businessgoodsid=?",
								new Integer[] { Integer.parseInt(account),
										marketId, goodId });
						ArrayList<NewHomeEntity> arrayList = db.query(qb);
						if (arrayList != null && arrayList.size() > 0) {
							arrayList.get(0).setCount(num);
							long millis = System.currentTimeMillis();
							arrayList.get(0).setAddTime(millis);
							db.update(arrayList.get(0));
						} else {
							long millis = System.currentTimeMillis();
							entity.setAddTime(millis);
							entity.setCount(num);
							entity.setMarketId(marketId);
							entity.setUserAccount(Integer.parseInt(account));
							db.insert(entity);
						}
						for (NewHomeEntity newHomeEntity : newHomeLists) {
							if (goodId == newHomeEntity.getBusinessgoodsid()) {
								newHomeEntity.setCount(num);
							}
						}
						((HomeActivity) activity)
								.setBuycount(((HomeActivity) activity)
										.getCount());
					}

					@Override
					public void delete(Integer id) {
						QueryBuilder qb = new QueryBuilder(NewHomeEntity.class);
						qb.where(
								"userAccount=? and marketId=? and businessgoodsid=?",
								new Integer[] {
										Integer.parseInt(baseApplication
												.getUserResult()
												.getUserAccount()),
										baseApplication.getUserResult()
												.getMarketId(), id });
						ArrayList<NewHomeEntity> arrayList = db.query(qb);
						if (arrayList != null && arrayList.size() > 0) {
							db.delete(arrayList.get(0));
						}
						for (NewHomeEntity newHomeEntity : newHomeLists) {
							if (id == newHomeEntity.getBusinessgoodsid()) {
								newHomeEntity.setCount(0);
							}
						}
						EventBus.getDefault().post(id);
						((HomeActivity) activity)
								.setBuycount(((HomeActivity) activity)
										.getCount());
					}

					@Override
					public void ItemClick(int gid) {
						 Intent intent=new Intent(activity, HomeHeaderActivity.class);
		                  intent.putExtra("productId",gid);
		                  intent.putExtra("type",HeaderActivityType.ProductDetail.ordinal());
		                //  intent.putExtra("hasHeader",false);
		                  startActivity(intent);
						
					}
				});
		/*refreshListView.getRefreshableView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				NewHomeEntity entity=(NewHomeEntity) newHomeListAdapter.getItem(Integer.parseInt(id+""));
				  Intent intent=new Intent(activity, HomeHeaderActivity.class);
                  intent.putExtra("productId",entity.getGid());
                  intent.putExtra("type",HeaderActivityType.ProductDetail.ordinal());
                //  intent.putExtra("hasHeader",false);
                  startActivity(intent);
            }
		});*/
		refreshListView.setAdapter(newHomeListAdapter);
		checkAddress();
		loadDataHandler.sendEmptyMessageDelayed(300, 500);
	}

	private Handler loadDataHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case PULL_DOWN_MODE:
				if (isNeedUpdate) {
					checkAddress();
					isNeedUpdate=false;
					EventBus.getDefault().post("UpdateClassify");
				}
				fetchHomeData(true);
				break;
			case PULL_UP_MODE:
				fetchHomeData(false);
				break;
			case 300:
				refreshListView.setMode(Mode.PULL_FROM_START);
				refreshListView.setRefreshing();
				break;

			}
		}
	};
	 public void onEventMainThread(UpdateClassifyEntity entity){
		   if (entity.getName().equals("updateClassify")) {
			   refreshListView.setMode(Mode.PULL_FROM_START);
			  cateId=entity.getCateId();
			  refreshListView.setRefreshing();
		}
	   }

	 private boolean isNeedUpdate;
	private void checkAddress() {
		BaseRequestClient client = new BaseRequestClient(activity);
		BaseRequest request = new BaseRequest();
		client.httpPostByJsonNew(
				"PhoneGetDefaultAddress",
				baseApplication.getUserResult(),
				request,
				PhoneGetDefaultAddressResult.class,
				new BaseRequestClient.RequestClientNewCallBack<PhoneGetDefaultAddressResult>() {

					@Override
					public void callBack(PhoneGetDefaultAddressResult result) {
						if (result != null) {
							if (result.getCode() == BaseResult.CodeState.Success
									.getCode()) {
								tv_address.setVisibility(View.VISIBLE);
								if (result.getShipAddress() != null) {
									isAdd = false;
									tv_address.setText(result.getPlot()
											.getName());
								} else {
									plot = result.getPlot();
									tv_address.setText(result.getPlot()
											.getName());
									isAdd = true;
								}
							} else {
								Toast.makeText(activity, result.getMessage(),
										Toast.LENGTH_SHORT).show();

							}
						} else {
							isNeedUpdate=true;
							Toast.makeText(
									activity,
									activity.getResources().getString(
											R.string.data_error),
									Toast.LENGTH_SHORT).show();

						}
					}

					@Override
					public void loading(long count, long current) {

					}

					@Override
					public void logOut(PhoneGetDefaultAddressResult result) {
						FinalDb finalDb = FinalDb.create(activity);
						List<UserResult> findAllByWhere = finalDb
								.findAllByWhere(UserResult.class, "isLogin=1");
						for (UserResult userResult : findAllByWhere) {
							userResult.setLogin(false);
							finalDb.update(userResult);
						}
						baseApplication.setUserResult(null);
						Intent intent = new Intent(activity,
								LoginActivity.class);
						intent.putExtra("isLoginOut", true);
						startActivity(intent);
					}
				});
	}

	private DataBase db;
	private NewHomeListAdapter newHomeListAdapter;
	private PullToRefreshListView refreshListView;
	private LinearLayout ll_wrong;
	private ImageView img_xiangshang;
	@Override
	public void onResume() {
		if (!isFirst) {
			QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
			qb.where("userAccount=? and marketId=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId()});
			ArrayList<NewHomeEntity> arrayList = db.query(qb);
			for (NewHomeEntity newHomeEntity : arrayList) {
				if (newHomeEntity.getAddTime()<baseApplication.getWeeTimew()) {
					db.delete(newHomeEntity);
					refreshListView.setMode(Mode.PULL_FROM_START);
					refreshListView.setRefreshing();
					((HomeActivity)activity).setBuycount(((HomeActivity)activity).getCount());
				}
			}
		}
		
		super.onResume();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
			AddressEntity entity = (AddressEntity) data
					.getSerializableExtra("addressEntity");
			tv_address.setText(entity.getPlotName());
			EventBus.getDefault().post("login");
		} else if (requestCode == 300 && resultCode == Activity.RESULT_OK) {
			AddressEntity entity = (AddressEntity) data
					.getSerializableExtra("addressEntity");
		}
	}
	private NewHomeResult resultEntity;
	private void fetchHomeData(final boolean isFirst) {
		BaseRequestClient client = new BaseRequestClient(activity);
		NewHomeRequest requeset = new NewHomeRequest();
		requeset.setCategoryId(cateId);
		if (isFirst) {
			requeset.setPageIndex(1);
		} else {
			requeset.setPageIndex(++pageIndex);
		}
		client.httpPostByJsonNew(
				"PhoneGetMarketGoods",
				baseApplication.getUserResult(),
				requeset,
				NewHomeResult.class,
				new BaseRequestClient.RequestClientNewCallBack<NewHomeResult>() {

					@Override
					public void callBack(NewHomeResult result) {
						if (result != null) {
							if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
								resultEntity=result;
								newHomeListAdapter.setEntity(resultEntity);
								if (result.getGoodsList().size()>0) {
									ll_wrong.setVisibility(View.GONE);
									ArrayList<NewHomeEntity> list = result.getGoodsList();
									QueryBuilder qb = new QueryBuilder(NewHomeEntity.class);
									qb.where("userAccount=? and marketId=?",new Integer[] {Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId() });
									ArrayList<NewHomeEntity> arrayList = db.query(qb);
									if (arrayList.size() > 0) {
										for (NewHomeEntity Entity : list) {
											for (NewHomeEntity newHomeEntity : arrayList) {
												if (Entity.getBusinessgoodsid() == newHomeEntity
														.getBusinessgoodsid()) {
													Entity.setCount(newHomeEntity
															.getCount());
													Entity.setIsmain(newHomeEntity.getIsmain());
												}
											}
										}
									}
									if (isFirst) {
										newHomeLists.clear();
										newHomeListAdapter.notifyDataSetChanged();
										newHomeLists.addAll(list);
										newHomeListAdapter.notifyDataSetChanged();
										pageIndex = 1;
									} else {

										newHomeLists.addAll(result.getGoodsList());
										newHomeListAdapter.notifyDataSetChanged();
									}
									refreshListView.onRefreshComplete();
									if (newHomeLists.size() >= result.getTotal()) {
										refreshListView
												.setMode(Mode.PULL_FROM_START);
									} else {
										refreshListView
												.setMode(PullToRefreshBase.Mode.BOTH);
									}
								}else {
									ll_wrong.setVisibility(View.VISIBLE);
									/*Toast.makeText(activity, "该分类暂没有商品信息",
											Toast.LENGTH_SHORT).show();*/
								}
								
							} else {
								Toast.makeText(activity, result.getMessage(),
										Toast.LENGTH_SHORT).show();
							}

						} else {

							Toast.makeText(
									activity,
									activity.getResources().getString(
											R.string.data_error),
									Toast.LENGTH_SHORT).show();
						}
						refreshListView.onRefreshComplete();

					}

					@Override
					public void loading(long count, long current) {

					}

					@Override
					public void logOut(NewHomeResult result) {
						refreshListView.onRefreshComplete();
						FinalDb finalDb = FinalDb.create(activity);
						List<UserResult> findAllByWhere = finalDb
								.findAllByWhere(UserResult.class, "isLogin=1");
						for (UserResult userResult : findAllByWhere) {
							userResult.setLogin(false);
							finalDb.update(userResult);
						}
						baseApplication.setUserResult(null);
						Intent intent = new Intent(activity,
								LoginActivity.class);
						intent.putExtra("isLoginOut", true);
						startActivity(intent);
						
					}
				});
	}
}
