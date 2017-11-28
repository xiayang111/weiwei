package com.dongwukj.weiwei.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.DeleteAddressRequest;
import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.dongwukj.weiwei.idea.result.AddressEntity;
import com.dongwukj.weiwei.idea.result.AddressResult;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.DeleteAddressResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;

import de.greenrobot.event.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;

public class AddressFragment extends AbstractHeaderFragment {
	private List<AddressEntity> list = new ArrayList<AddressEntity>();
	private SwipeListView lv_address;
	private final int REQUEST_CODE=100;
	/**
     * true：显示，false：不显示
     */
	//private boolean isDeleteShow=false;

    /**
     * 1：正常，2：选择
     */
    private int mode;
    
    //private boolean isAddress = false;   //判断是否有地址数据

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_address, null);
        mode=activity.getIntent().getIntExtra("mode",1);
        db = LiteOrm.newCascadeInstance(activity, baseApplication.DB_NAME);
        fromHome = activity.getIntent().getBooleanExtra("fromHome", false);
		return view;
	}

	@Override
	protected String setTitle() {
		String title=activity.getString(R.string.address_title);
		
		return title;
	}

	@Override
	protected void findView(View v) {
		//rightButton = (TextView) activity.findViewById(R.id.list_header_rightbutton);
		lv_address = (SwipeListView)v.findViewById(R.id.lv_address);
		addressHandler.sendEmptyMessage(100);
		tv_add = (TextView) v.findViewById(R.id.tv_add);
		tv_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openNewActivityWithHeader(HeaderActivityType.NewAddress.ordinal(), false);
			}
		});
		adapter = new Myadpter();
		lv_address.setAdapter(adapter);
		//setRightButton(R.drawable.weiwei_address_edit);
		adapter.setItemClick(new ItemClick() {
			
			@Override
			public void onItemClick(int position) {
				 if(!fromHome){
	                    AddressEntity entity=(AddressEntity) adapter.getItem(position);
	                    Intent intent=new Intent(activity,HomeHeaderActivity.class);
	                    intent.putExtra("type",HeaderActivityType.NewAddress.ordinal());
	                    intent.putExtra("needLogin", false);
	                    intent.putExtra("AddressEntity", entity);
	                    intent.putExtra("title", "修改收货地址");
	                    startActivityForResult(intent, REQUEST_CODE);
	                }else {
	                	AddressEntity entity=(AddressEntity) adapter.getItem(position);
	                	if (entity.getIsDefault()==1) {
	                		activity.finish();
	                		return;
						}
	                	PhoneSetDefaultAddress(entity);
	                /*    AddressEntity entity=(AddressEntity) adapter.getItem(position);
	                    Intent intent=new Intent();
	                    intent.putExtra("AddressEntity", entity);
	                    activity.setResult(Activity.RESULT_OK,intent);
	                    activity.finish();*/
	                }

			}
			
			private void PhoneSetDefaultAddress(final AddressEntity entity) {
				progressDialog.setMessage("获取地址信息中...");
				progressDialog.show();
				BaseRequestClient client=new BaseRequestClient(activity);
				PhoneSetDefaultAddressRequest request=new PhoneSetDefaultAddressRequest();
				request.setSaId(entity.getSaId());
				client.httpPostByJsonNew("PhoneSetDefaultAddress", baseApplication.getUserResult(), request, BaseResult.class, new BaseRequestClient.RequestClientNewCallBack<BaseResult>() {

					@Override
					public void callBack(BaseResult result) {
						if (result!=null) {
							if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
								saveResultToDB(entity.getMarketId());
								Intent intent=new Intent();
				                intent.putExtra("addressEntity", entity);
								activity.setResult(Activity.RESULT_OK,intent);
				                activity.finish();
				            	progressDialog.dismiss();
							}else {
							   	progressDialog.dismiss();
								showtoast(result.getMessage(), activity);
							}
						}else {
						   	progressDialog.dismiss();
							showtoast(activity.getResources().getString(R.string.data_error), activity);
						}
						
					}

					@Override
					public void loading(long count, long current) {
						
					}

					@Override
					public void logOut(BaseResult result) {
						progressDialog.dismiss();
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
			public void deleteItem(int said,int marketId) {
				deleteAddress(said,marketId);
			/*	QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
				qb.where("userAccount=? and marketId=? ", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),ploid});
				ArrayList<NewHomeEntity> arrayList = db.query(qb);
				if (arrayList.size()>0) {
					for (NewHomeEntity newHomeEntity : arrayList) {
						db.delete(newHomeEntity);
					}
				}*/
				
				
				
			}
		});
		/*lv_address.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
                if(mode==1){
                    AddressEntity entity=(AddressEntity) adapter.getItem(position);
                    Intent intent=new Intent(activity,HomeHeaderActivity.class);
                    intent.putExtra("type",HeaderActivityType.NewAddress.ordinal());
                    intent.putExtra("needLogin", false);
                    intent.putExtra("AddressEntity", entity);
                    startActivityForResult(intent, REQUEST_CODE);
                }else if(mode==2){
                    AddressEntity entity=(AddressEntity) adapter.getItem(position);
                    Intent intent=new Intent();
                    intent.putExtra("AddressEntity", entity);
                    activity.setResult(Activity.RESULT_OK,intent);
                    activity.finish();
                }

			}
		});*/
	}
	public void onEventMainThread(String type){}
   	
	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		super.onCreate(savedInstanceState);
	}
	 private void saveResultToDB(int id) {
		 FinalDb db=FinalDb.create(activity);
		 UserResult result = baseApplication.getUserResult();
	        List<UserResult> list = db.findAllByWhere(UserResult.class, "userAccount='" + result.getUserAccount() + "'");
	        if (list.size() > 0) {
	        	result.setMarketId(id);
	        	baseApplication.setUserResult(result);
	            db.update(result, "userAccount='" + result.getUserAccount() + "'");
	            if (!fromHome) {
	            	 EventBus.getDefault().post("login");
	  	        }
	           }
	     }
/*	private void setRightButton(int resId){
		setRightButtonClickListener(new OnClickListener() {     //控制  标题右边按钮方法

			@Override
			public void onClick(View v) {
				if(isAddress){
					//点击右边按钮, 
					isDeleteShow=!isDeleteShow;  //  给 isDeleteShow重新赋值
					if(isDeleteShow){			//如果删除按钮是显示的,标题右边按钮的背景为确认图片
						setRightButton(R.drawable.weiwei_address_ok);
					}else{					    //如果删除按钮是隐藏的,标题右边按钮的背景是编辑图片
						setRightButton(R.drawable.weiwei_address_edit);
					}
				}else{
					Toast.makeText(activity, "请添加地址", Toast.LENGTH_SHORT).show();
				}
				adapter.notifyDataSetChanged();
			}
		}, "", resId);
	}
	*/
	
	private void openNewActivityWithHeader(int type,Boolean needLogin){
		Intent intent=new Intent(activity,HomeHeaderActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("needLogin", needLogin);
        startActivityForResult(intent, REQUEST_CODE);
}
	
	private Handler addressHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 100:

                MyWeiWeiRequestClient addressRequestClient=new MyWeiWeiRequestClient(activity,baseApplication);
                addressRequestClient.fetchAddressList(new MyWeiWeiRequestClient.AddressRequestCallback() {
                	
                    @Override
                    protected void list(AddressResult result) {
                    	if (getActivity()==null) {
							return;
						}
                        list.clear();
                        if (result.getShipAddresses()==null||result.getShipAddresses().size()==0) {
                        /*	if (fromHome) {
                        		PhoneGetPlot();
							}else {*/
								openNewActivityWithHeader(HeaderActivityType.NewAddress.ordinal(), false);
	                       // }
                        	 Toast.makeText(getActivity(), "您还没有添加地址", Toast.LENGTH_SHORT).show();
                            
                           // isAddress = false;  //未获取到地址数据
                           // rightButton.setBackgroundResource(R.drawable.weiwei_address_edit);
                           // rightButton.setVisibility(View.GONE);
                           // isDeleteShow=false;
                            mode=1;
                        }else{
                        	if (mode==2) {
                        		//rightButton.setVisibility(View.GONE);
                            	
							}else {
								//rightButton.setVisibility(View.VISIBLE);
	                        	
							}
                        	//isAddress = true;   //获取到地址数据
                            list.addAll(result.getShipAddresses());
                            if (list.size()>=result.getMaxshipaddress()) {
                            	tv_add.setVisibility(View.GONE);
							}else {
								tv_add.setVisibility(View.VISIBLE);
							}
                        }
                        adapter.notifyDataSetChanged();

                    }
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
        			
                    };
                });

				break;

			default:
				break;
			}
		};
	};
	private Myadpter adapter;
	private DataBase db;
	//private TextView rightButton;
	
	
	private class Myadpter extends BaseAdapter{
		ItemClick itemClick;
		
		public void setItemClick(ItemClick itemClick) {
			this.itemClick = itemClick;
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
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView==null){
				holder = new ViewHolder();
				convertView = View.inflate(activity, R.layout.activity_address_item, null);
				holder.iv_address_delete = (ImageView) convertView.findViewById(R.id.iv_address_delete);
				holder.iv1_address = (ImageView) convertView.findViewById(R.id.iv1_address);
				holder.tv_receiver_name = (TextView) convertView.findViewById(R.id.tv_receiver_name);
				holder.tv_receiver_phone = (TextView) convertView.findViewById(R.id.tv_receiver_phone);
				holder.tv_receiver_address = (TextView) convertView.findViewById(R.id.tv_receiver_address);
				holder.id_remove = (TextView) convertView.findViewById(R.id.id_remove);
				holder.addressIsDefault=(TextView)convertView.findViewById(R.id.addressIsDefault);
				holder.ll_adderss_item=(LinearLayout) convertView.findViewById(R.id.ll_adderss_item);
				/*holder.iv_address_delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						AddressEntity entity = list.get(position);
						deleteAddress(entity.getSaId());
						Toast.makeText(activity, "地址已经删除!", Toast.LENGTH_SHORT).show();
					}


				});*/
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.tv_receiver_name.setText(list.get(position).getConsignee());   //收件人
			if (TextUtils.isEmpty(list.get(position).getMobile())||list.get(position).getMobile().equals("")) {
				holder.tv_receiver_phone.setText(list.get(position).getPhone()); 	  //收件人电话
				
			}else {
				holder.tv_receiver_phone.setText(list.get(position).getMobile()); 	  //收件人电话
				
			}
			holder.tv_receiver_address.setText(list.get(position).getRegion()+list.get(position).getPlotName()+list.get(position).getAddress());  //收件地址
			/*if (isDeleteShow) {
				holder.iv1_address.setVisibility(View.GONE);
				holder.iv_address_delete.setVisibility(View.VISIBLE);
			}else {
				holder.iv_address_delete.setVisibility(View.GONE);
				holder.iv1_address.setVisibility(View.VISIBLE);
			}*/
			holder.id_remove.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//Toast.makeText(activity, position+"", 0).show();
					lv_address.closeOpenedItems();
					itemClick.deleteItem(list.get(position).getSaId(),list.get(position).getMarketId());
				}
			});
			holder.ll_adderss_item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					itemClick.onItemClick(position);
				}
			});
			//默认地址
			AddressEntity addressEntity=list.get(position);
			if(addressEntity.getIsDefault()==1){
				holder.addressIsDefault.setVisibility(View.VISIBLE);
			}else{
				holder.addressIsDefault.setVisibility(View.GONE);
			}
			return convertView;
		}
		
		
		
	}
	interface ItemClick{
		public void deleteItem(int saId,int plotId);
		public void onItemClick(int position);
		
	}
	class ViewHolder{
		LinearLayout ll_adderss_item;
		TextView id_remove;
		TextView tv_receiver_name; 
		TextView tv_receiver_phone; 
		TextView tv_receiver_address; 
		ImageView iv_address_delete;
		ImageView iv1_address;
		TextView addressIsDefault;

	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==REQUEST_CODE&&resultCode==Activity.RESULT_OK) {
			//if (!fromHome) {
				addressHandler.sendEmptyMessage(100);
		/*	}else {
				Intent intent=new Intent();
				if (data!=null) {
					AddressEntity entity = (AddressEntity) data.getSerializableExtra("addressEntity");
					intent.putExtra("AddressEntity", entity);
				}
				activity.setResult(Activity.RESULT_OK);
				activity.finish();
			}*/
		
		}
	}
	//private int defaultSaId;
	private boolean fromHome;
	private TextView tv_add;
	private void deleteAddress(int said,final int marketId) {
		DeleteAddressRequest request=new DeleteAddressRequest();
		request.setSaIdList(new int[]{said});
		request.setSaId(said);
		MyWeiWeiRequestClient addressRequestClient = new MyWeiWeiRequestClient(activity, baseApplication);
		addressRequestClient.addAddress(request, new MyWeiWeiRequestClient.AddressRequestCallback() {
			
		

			@Override
			protected void add(DeleteAddressResult result) {
				//defaultSaId = result.getDefaultSaId();
			//	if (marketId==baseApplication.getUserResult().getMarketId()) {
					saveResultToDB(result.getMarketId());
					EventBus.getDefault().post("login");
				//}
				addressHandler.sendEmptyMessage(100);
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
		}); 
	
}


}
