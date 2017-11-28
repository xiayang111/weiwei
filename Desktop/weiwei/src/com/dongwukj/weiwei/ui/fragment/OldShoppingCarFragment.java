package com.dongwukj.weiwei.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.ParentCartListAdapter;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.PhoneModifyCartRequest;
import com.dongwukj.weiwei.idea.request.PhonecartdeleteRequest;
import com.dongwukj.weiwei.idea.request.PhonecartlistRequest;
import com.dongwukj.weiwei.idea.result.*;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.ShoppingCartRequestClient;
import com.dongwukj.weiwei.ui.activity.HomeActivity;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.widget.ChildCartListView;
import com.dongwukj.weiwei.ui.widget.ParentCartListView;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.umeng.analytics.MobclickAgent;

import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class OldShoppingCarFragment extends BaseFragment implements ChildCartListView.ChildCartListChangeListener{

	private List<CartItemEntity> list;

    private Float cut;
    private ParentCartListAdapter parentCartListAdapter;
    private ParentCartListView parentCartListView;
    private BaseApplication baseApplication;
    private Button cartEditButton;
    private LinearLayout cartPayContainer;
    private LinearLayout cartDeleteContainer;
    private Button cartDeleteButton;
    private TextView parent_cart_total_amount;
    private RelativeLayout parent_cart_empty;
    private LinearLayout parent_cart_empty_text;
    private boolean isShoudong;
    private String imgUrl;
	private String taocanImgUrl;
	@Override
	public View setView_parent(LayoutInflater inflater) {
        view_parent=inflater.inflate(R.layout.parent_cart_list,null);
		return view_parent;
	}
	
	/*
	 * #198 暴露方法,跳转到首页方法
	 */
	private void showHome(){
		if (isFromDetails) {
			Intent intent=new Intent(activity,HomeActivity.class);
			intent.putExtra("homeTab",0);
			startActivity(intent);
			activity.finish();
		}else {
			((HomeActivity)activity).showTabByIndex(0);
		}
		
	}

	@Override
	public void setListener() {
        setUserVisibleHint(true);
	}
	
     private List<CartItemEntity> getCheckedCartList(){
    	 List<CartItemEntity> checkedList=new ArrayList<CartItemEntity>();
         for(CartItemEntity cartItemEntity:list){
             if(cartItemEntity.getType()==0 &&cartItemEntity.getCartProduct()!=null& cartItemEntity.getCartProduct().getSelected()){
                 checkedList.add(cartItemEntity);
             }else if(cartItemEntity.getType()==1 && cartItemEntity.getCartSuit()!=null && cartItemEntity.getCartSuit().getCartProductList()!=null
                     && cartItemEntity.getCartSuit().getCartProductList().size()>0 && cartItemEntity.getCartSuit().getChecked()){
                 checkedList.add(cartItemEntity);
             }else if(cartItemEntity.getType()==2 && cartItemEntity.getCartFullSend()!=null &&cartItemEntity.getCartFullSend().getFullSendMainCartProductList()!=null &&
                     cartItemEntity.getCartFullSend().getFullSendMainCartProductList().size()>0){
                 List<CartProductEntity> src=cartItemEntity.getCartFullSend().getFullSendMainCartProductList();
                 List<CartProductEntity> dest=new ArrayList<CartProductEntity>();
                 Collections.copy(dest,src);


//                 cartProductEntity=cartItemEntity.getCartFullSend().getFullSendMainCartProductList().get(position);
             }else if(cartItemEntity.getType()==3){
//                 cartProductEntity=cartItemEntity.getCartFullCut().getFullCutCartProductList().get(position);
             }
         }
         return checkedList;
     }



    DataBase db;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PAY_REQUEST_CODE:{
                parent_cart_empty_text.setVisibility(View.GONE);
                changeState(false);
                updateHandler.sendEmptyMessage(100);
                break;
            }
        }
    }

    private final int PAY_REQUEST_CODE=1;


    private Handler checkHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    protected void onFragmentPause() {
    	map.clear();
    };
	@Override
	public void initview() {
		isFromDetails = activity.getIntent().getBooleanExtra("isFromDetails", false);
		if (!isFromDetails) {
			activity = (HomeActivity) activity;
		}
		LinearLayout bt_back=(LinearLayout) view_parent.findViewById(R.id.ll_left);
		bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
		if (isFromDetails) {
			bt_back.setVisibility(View.VISIBLE);
		}else {
			bt_back.setVisibility(View.GONE);
		}
		view_parent.findViewById(R.id.bt_food).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showHome();
			}
		});
		
		 cb_delete = (CheckBox) view_parent.findViewById(R.id.cb_delete);
		 cb_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean checked = cb_delete.isChecked();
				isShoudong=false;
				cb_delete.setChecked(checked);
				for (CartItemEntity cartItemEntity : list) {
					if (cartItemEntity.getCartProduct()!=null) {
						cartItemEntity.getCartProduct().setDeleteSelected(checked);
					}
					if (cartItemEntity.getCartSuit()!=null) {
						cartItemEntity.getCartSuit().setDeleteChecked(checked);
					}if (cartItemEntity.getCartFullSend()!=null&&cartItemEntity.getCartFullSend().getFullSendMainCartProductList()!=null) {
						ArrayList<CartProductEntity> list2 = cartItemEntity.getCartFullSend().getFullSendMainCartProductList();
						for (CartProductEntity cartProductEntity : list2) {
							cartProductEntity.setDeleteSelected(checked);
						}
					}
				}
				parentCartListAdapter.notifyDataSetChanged();
			}
		});
		 cb_select = (CheckBox) view_parent.findViewById(R.id.cb_select);
		 cb_select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean checked = cb_select.isChecked();
				cb_select.setChecked(checked);
			for (CartItemEntity cartItemEntity : list) {
					if (cartItemEntity.getCartProduct()!=null) {
						cartItemEntity.getCartProduct().setSelected(checked);
					}
					if (cartItemEntity.getCartSuit()!=null) {
						cartItemEntity.getCartSuit().setChecked(checked);
					}if (cartItemEntity.getCartFullSend()!=null&&cartItemEntity.getCartFullSend().getFullSendMainCartProductList()!=null) {
						ArrayList<CartProductEntity> list2 = cartItemEntity.getCartFullSend().getFullSendMainCartProductList();
						for (CartProductEntity cartProductEntity : list2) {
							cartProductEntity.setDeleteSelected(checked);
						}
					}
				}
				parentCartListAdapter.notifyDataSetChanged();
				if (checked) {
					selectedAmount(getSelectedCartItemKeyList());
					map.clear();
				}else {
					parent_cart_total_amount.setText("￥0.0");
					parentCartListAdapter.notifyDataSetChanged();
				}
				
				
			}
		});
		parent_cart_submit_button = (Button) view_parent.findViewById(R.id.parent_cart_submit_button);
        view_parent.findViewById(R.id.parent_cart_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (parent_cart_total_amount.getText().toString().trim().equals("￥0.0")) {
					Toast.makeText(activity, "请勾选您要结算的商品", Toast.LENGTH_SHORT).show();
					return;
				}
                db = LiteOrm.newCascadeInstance(activity, BaseApplication.DB_NAME);
                ArrayList<CartItemEntity> newList= db.queryAll(CartItemEntity.class);
                for(CartItemEntity cartItemEntity:newList){
                    db.delete(cartItemEntity);
                }
                ArrayList<CartItemEntity> newList0= db.queryAll(CartItemEntity.class);

                for (CartItemEntity cartItemEntity:list){
                    db.save(cartItemEntity);
                }
                ArrayList<CartItemEntity> newList1= db.queryAll(CartItemEntity.class);
                db.close();
                Intent intent=new Intent(activity, HomeHeaderActivity.class);
                intent.putExtra("type", HeaderActivityType.ConfirmOrder.ordinal());
                intent.putExtra("imgUrl", imgUrl);
                intent.putExtra("taocanImgUrl", taocanImgUrl);
                //startActivity(intent);
                startActivityForResult(intent,PAY_REQUEST_CODE);
                
            }
        });


        cartPayContainer= (LinearLayout) view_parent.findViewById(R.id.cart_pay_container);
        cartDeleteContainer=(LinearLayout)view_parent.findViewById(R.id.cart_delete_container);

        list=new ArrayList<CartItemEntity>();
        baseApplication=(BaseApplication)activity.getApplication();
        parentCartListView=(ParentCartListView)view_parent.findViewById(R.id.parent_cart_list);
        parentCartListAdapter=new ParentCartListAdapter(activity,list,this);

        parentCartListView.setAdapter(parentCartListAdapter);

        cartEditButton=(Button)view_parent.findViewById(R.id.cart_edit_button);
        cartEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("0".equals(cartEditButton.getTag().toString())){
                    changeState(true);

                }else{
                    changeState(false);
                }
            }
        });
        cartDeleteButton=(Button)view_parent.findViewById(R.id.cart_delete_button);
        cartDeleteButton.setOnClickListener(new CartButtonOnClickListener());

        parent_cart_total_amount=(TextView)view_parent.findViewById(R.id.parent_cart_total_amount);
        parent_cart_empty=(RelativeLayout)view_parent.findViewById(R.id.parent_cart_empty);
        parent_cart_empty_text=(LinearLayout)view_parent.findViewById(R.id.parent_cart_empty_text);
	}

	private void selectedAmount(final String selectedCartItemKeyList){
		 PhonecartlistRequest request=new PhonecartlistRequest();
		 request.setSelectedCartItemKeyList(selectedCartItemKeyList);
		 if (TextUtils.isEmpty(selectedCartItemKeyList)||selectedCartItemKeyList.equals("")) {
				parent_cart_total_amount.setText("￥0.0");
				parent_cart_submit_button.setText("结算");
				return;
			}
		 ShoppingCartRequestClient shoppingCartRequestClient=new ShoppingCartRequestClient(activity,baseApplication);
	     shoppingCartRequestClient.list(request, new ShoppingCartRequestClient.ShoppingCartRequestClientCallback() {
	         

				@Override
	            protected void listSuccess(CartItemResult result) {
					if (getActivity()==null) {
						return;
					}
	            /*	baseApplication.setCartCount(result.getTotalCount());
	            	if (!isFromDetails) {
	            		setBadgeViewText(result.getTotalCount()+"");
	                }*/
					parent_cart_submit_button.setText("结算("+result.getTotalCount()+")");
					parent_cart_total_amount.setText(String.format("￥%.1f",result.getOrderAmount()));
		            
	            	imgUrl = result.getImgUrl();
	                taocanImgUrl = result.getTaocanImgUrl();
	                list.clear();
	                ArrayList<CartItemEntity> cartItemList = result.getCartItemList();
	                Set<Integer> set = map.keySet();
	                if (set.size()!=0) {
	                	for (Integer integer : set) {
	                		CartItemEntity cartItemEntity = cartItemList.get(integer);
	                		if(cartItemEntity.getType()==0){
	                            	cartItemEntity.getCartProduct().setSelected(map.get(integer));
	                              

	                        }else if(cartItemEntity.getType()==1){
	                        	cartItemEntity.getCartSuit().setChecked(map.get(integer));
	                           }
	                	}
					}
	                
	                list.addAll(cartItemList);
	                parentCartListAdapter.setImageUrl(result.getImgUrl(),result.getTaocanImgUrl());
	                parentCartListAdapter.notifyDataSetChanged(ParentCartListView.MODE_NORMAL,result.getFullDiscountPromotionConfigInfo());
	            }

	            @Override
	            protected void listEmpty(CartItemResult result) {
	            	if (getActivity()==null) {
						return;
					}
	            	baseApplication.setCartCount(result.getTotalCount());
	            	if (!isFromDetails) {
	            		setBadgeViewText(result.getTotalCount()+"");
	                }
	            	cartEditButton.setVisibility(View.INVISIBLE);
	                parent_cart_empty.setVisibility(View.VISIBLE);
	                parent_cart_empty_text.setVisibility(View.VISIBLE);
	            }
	        });

	}

    private void changeState(boolean isEdit){
        if(!isEdit) {
            cartEditButton.setText("编辑");
            cartEditButton.setTag(0);
            cartPayContainer.setVisibility(View.VISIBLE);
            cartDeleteContainer.setVisibility(View.GONE);
            parentCartListView.setCurrentMode(ParentCartListView.MODE_NORMAL);
            parentCartListAdapter.notifyDataSetChanged(ParentCartListView.MODE_NORMAL,null);
         }else{
            cartEditButton.setText("取消");
            cartEditButton.setTag(1);
            cartPayContainer.setVisibility(View.GONE);
            cartDeleteContainer.setVisibility(View.VISIBLE);
            parentCartListView.setCurrentMode(ParentCartListView.MODE_EDIT);
            parentCartListAdapter.notifyDataSetChanged(ParentCartListView.MODE_EDIT,null);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    protected void onFragmentResume() {
        super.onFragmentResume();
        parent_cart_empty_text.setVisibility(View.GONE);
        changeState(false);
        updateHandler.sendEmptyMessage(100);
    }
    private boolean isBackFromCart;
    @Override
    public void onResume() {
    	 MobclickAgent.onPageStart("ShopCart"); 
		if (isBackFromCart) {
    		updateHandler.sendEmptyMessage(100);
    		changeState(false);
		}
    	super.onResume();
    }
    @Override
    public void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	   MobclickAgent.onPageEnd("ShopCart"); 
    }
    private final int UPDATE_TOTAL_PRICE=110;

    private Handler updateHandler =new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    updateData();
                    break;
                case UPDATE_TOTAL_PRICE:

                    Float totalPrice=(Float)msg.obj;
                    parent_cart_total_amount.setText(String.format("￥%.1f",totalPrice));
                    break;
            }

		}
	};

    private void calTotalPrice(){
        updateHandler.post(new Runnable() {
            @Override
            public void run() {
                float totalPrice=0;
                if(list!=null && list.size()>0) {

                    for (CartItemEntity cartItemEntity : list) {
                        if (cartItemEntity.getType() == 0 && cartItemEntity.getCartProduct() != null) {
                            if (cartItemEntity.getCartProduct().getSelected()) {
                                totalPrice+=cartItemEntity.getCartProduct().getOrderProductInfo().getDiscountPrice()*cartItemEntity.getCartProduct().getOrderProductInfo().getBuyCount();
                            }
                        } else if (cartItemEntity.getType() == 1 && cartItemEntity.getCartSuit() != null) {
                            if (cartItemEntity.getCartSuit().getChecked()) {
                                totalPrice+=cartItemEntity.getCartSuit().getSuitPrice()*cartItemEntity.getCartSuit().getBuyCount();
                            }
                        } else if (cartItemEntity.getType() == 2 && cartItemEntity.getCartFullSend() != null) {
                            if (cartItemEntity.getCartFullSend().getFullSendMainCartProductList() != null && cartItemEntity.getCartFullSend().getFullSendMainCartProductList().size() > 0) {
                                ArrayList<CartProductEntity> productEntityArrayList = cartItemEntity.getCartFullSend().getFullSendMainCartProductList();
                                for (CartProductEntity cartProductEntity : productEntityArrayList) {
                                    if (cartProductEntity.getSelected()) {
                                        totalPrice+=cartProductEntity.getOrderProductInfo().getDiscountPrice()*cartProductEntity.getOrderProductInfo().getBuyCount();
                                    }
                                }


                            }
                        } else if (cartItemEntity.getType() == 3 && cartItemEntity.getCartFullCut() != null) {
                            if (cartItemEntity.getCartFullCut().getFullCutCartProductList() != null && cartItemEntity.getCartFullCut().getFullCutCartProductList().size() > 0) {
                                ArrayList<CartProductEntity> productEntityArrayList = cartItemEntity.getCartFullCut().getFullCutCartProductList();
                                for (CartProductEntity cartProductEntity : productEntityArrayList) {
                                    if (cartProductEntity.getSelected()) {

                                        totalPrice+=cartProductEntity.getOrderProductInfo().getDiscountPrice()*cartProductEntity.getOrderProductInfo().getBuyCount();
                                    }
                                }

                            }
                        }
                    }
                }
                updateHandler.obtainMessage(UPDATE_TOTAL_PRICE,totalPrice).sendToTarget();
            }
        });
    }
    /*
     * 
     * 获取购物车数量
     */
    private void getUserCartCount(){
    	BaseRequestClient client=new BaseRequestClient(activity);
    	client.httpPostByJson("Phonecartlistcount", baseApplication.getUserResult(), new BaseRequest(), ExtraDate.class, new BaseRequestClient.RequestClientCallBack<ExtraDate>() {

			@Override
			public void callBack(ExtraDate result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						baseApplication.setCartCount(result.getTotalCount());
						if (!isFromDetails) {
		            		setBadgeViewText(result.getTotalCount()+"");
		                }
		            	
					}else {
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(activity, getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void logOut(boolean isLogOut,ExtraDate result) {
				if(isLogOut){
					FinalDb finalDb = FinalDb.create(activity);
					List<UserResult> findAllByWhere = finalDb.findAllByWhere(
							UserResult.class, "isLogin=1");
					for (UserResult userResult : findAllByWhere) {
						userResult.setLogin(false);
						finalDb.update(userResult);
					}
					// finalDb.deleteAll(UserResult.class);
					baseApplication.setUserResult(null);
					baseApplication.setCartCount(0);
					Toast.makeText(activity, "您的账号已经在其他地方登录！", Toast.LENGTH_SHORT).show();
				}
			}
		});
    }

	private void updateData(){
        fetchData();
	}
	private void setBadgeViewText(String text){
		((HomeActivity)activity).setBadgeViewText(text);
	}
    private void fetchData(){


		if(baseApplication.getUserResult()==null){

			baseApplication.setCartCount(0);
			if (!isFromDetails) {
				setBadgeViewText(0+"");
			}
			cartEditButton.setVisibility(View.INVISIBLE);
			parent_cart_empty.setVisibility(View.VISIBLE);
			parent_cart_empty_text.setVisibility(View.VISIBLE);
			return;
		}
    	cartPayContainer.setVisibility(View.GONE);
        PhonecartlistRequest request=new PhonecartlistRequest();

        ShoppingCartRequestClient shoppingCartRequestClient=new ShoppingCartRequestClient(activity,baseApplication);
		list.clear();
        shoppingCartRequestClient.list(request, new ShoppingCartRequestClient.ShoppingCartRequestClientCallback() {
       

			@Override
            protected void listSuccess(CartItemResult result) {
				if (getActivity()==null) {
					return;
				}
            	baseApplication.setCartCount(result.getTotalCount());
            	cut = result.getFullCut();
            	if (!isFromDetails) {
            		setBadgeViewText(result.getTotalCount()+"");
                }
            	parent_cart_submit_button.setText("结算("+result.getTotalCount()+")");
            	 parent_cart_empty.setVisibility(View.GONE);
                cartPayContainer.setVisibility(View.VISIBLE);
                cartEditButton.setVisibility(View.VISIBLE);
                parent_cart_total_amount.setText(String.format("￥%.1f",result.getOrderAmount()));
                imgUrl = result.getImgUrl();
                taocanImgUrl = result.getTaocanImgUrl();
                list.clear();
                list.addAll(result.getCartItemList());
                parentCartListAdapter.setImageUrl(result.getImgUrl(),result.getTaocanImgUrl());
                parentCartListAdapter.notifyDataSetChanged(ParentCartListView.MODE_NORMAL,result.getFullDiscountPromotionConfigInfo());
            }

            @Override
            protected void listEmpty(CartItemResult result) {
            	if (getActivity()==null) {
					return;
				}
            	
            	if (result!=null) {
            		baseApplication.setCartCount(result.getTotalCount());
                	if (!isFromDetails) {
                		setBadgeViewText(result.getTotalCount()+"");
                    }
                	
				}
            	cartEditButton.setVisibility(View.INVISIBLE);
                parent_cart_empty.setVisibility(View.VISIBLE);
                parent_cart_empty_text.setVisibility(View.VISIBLE);
            }
        });


    }
    
    private HashMap<Integer, Boolean> map=new HashMap<Integer, Boolean>();
    @Override
    public void itemCheck(boolean checked, int groupId, int position,int currentMode) {
    	
        CartItemEntity cartItemEntity=list.get(groupId);
        if(cartItemEntity.getType()==0){
            if(currentMode==ParentCartListView.MODE_NORMAL){
                cartItemEntity.getCartProduct().setSelected(checked);
                map.put(groupId, checked);
                String selectedCartItemKeyList = getSelectedCartItemKeyList();
                selectedAmount(selectedCartItemKeyList);
            }else{
                cartItemEntity.getCartProduct().setDeleteSelected(checked);
            }

        }else if(cartItemEntity.getType()==1){

            if(currentMode==ParentCartListView.MODE_NORMAL){
                if(position==-1){
                	map.put(groupId, checked);
                    cartItemEntity.getCartSuit().setChecked(checked);
                    String selectedCartItemKeyList = getSelectedCartItemKeyList();
                    selectedAmount(selectedCartItemKeyList);
                }

            }else{

                if(position==-1){
                    cartItemEntity.getCartSuit().setDeleteChecked(checked);
                }
            }
        }else if(cartItemEntity.getType()==2){
            if(currentMode==ParentCartListView.MODE_NORMAL){
                cartItemEntity.getCartFullSend().getFullSendMainCartProductList().get(position).setSelected(checked);
            }else{
                cartItemEntity.getCartFullSend().getFullSendMainCartProductList().get(position).setDeleteSelected(checked);
            }
        }else if(cartItemEntity.getType()==3){
            if(currentMode==ParentCartListView.MODE_NORMAL){
                cartItemEntity.getCartFullCut().getFullCutCartProductList().get(position).setSelected(checked);
            }else{
                cartItemEntity.getCartFullCut().getFullCutCartProductList().get(position).setDeleteSelected(checked);
            }
        }
       // calTotalPrice();
        changedeletecheckbox();
        changeseletecheckbox();
        
    }

   private void changeseletecheckbox() {
	   for (CartItemEntity cartItemEntity : list) {
			CartProductEntity cartProduct = cartItemEntity.getCartProduct();
			CartSuitEntity suit = cartItemEntity.getCartSuit();
			if ((cartProduct!=null&&!cartProduct.getSelected())||(suit!=null&&!suit.getChecked())) {
				cb_select.setChecked(false);
				break;
			}
		}
	}

   private void changedeletecheckbox() {
    	for (CartItemEntity cartItemEntity : list) {
			CartProductEntity cartProduct = cartItemEntity.getCartProduct();
			CartSuitEntity suit = cartItemEntity.getCartSuit();
			if ((cartProduct!=null&&!cartProduct.getDeleteSelected())||(suit!=null&&!suit.getDeleteChecked())) {
				cb_delete.setChecked(false);
				break;
			}
		}
	}

	@Override
    public void upOrDownAmount(int groupId, int position, boolean isPlus,String amount) {
        CartItemEntity cartItemEntity=list.get(groupId);
        map.put(groupId, true);
        CartProductEntity cartProductEntity=null;
        if(cartItemEntity.getType()==0){
            cartProductEntity=cartItemEntity.getCartProduct();
            cartProductEntity.setSelected(true);
        }else if(cartItemEntity.getType()==1){
        	//cartProductEntity=cartItemEntity.getCartSuit().getCartProductList().get(position);
        	cartItemEntity.getCartSuit().setChecked(true);
            if(!isPlus && cartItemEntity.getCartSuit().getBuyCount()==1){
                return;
            }
            changAmount(cartItemEntity.getCartSuit().getPmId(),cartItemEntity.getCartSuit().getBuyCount(),true,isPlus);


        }else if(cartItemEntity.getType()==2){

            cartProductEntity=cartItemEntity.getCartFullSend().getFullSendMainCartProductList().get(position);
        }else if(cartItemEntity.getType()==3){
            cartProductEntity=cartItemEntity.getCartFullCut().getFullCutCartProductList().get(position);
        }
        if(cartProductEntity!=null){
            //changAmount(cartProductEntity,isPlus);
            if(!isPlus && cartProductEntity.getOrderProductInfo().getBuyCount()==1){
                return;
            }else if (cartProductEntity.getSinglePromotion()!=null) {
            	if (isPlus&&Integer.parseInt(amount)>=cartProductEntity.getSinglePromotion().getAllowBuyCount()) {
					Toast.makeText(activity, "不能大于限购数量", Toast.LENGTH_SHORT).show();
					return;
				}
            	//cartProductEntity.getSinglePromotion().getAllowBuyCount();
			}
            changAmount(cartProductEntity.getOrderProductInfo().getPid(),cartProductEntity.getOrderProductInfo().getBuyCount(),false,isPlus);
            return;
        }
    }

    private void changAmount(int id,int amount,boolean isPm,boolean isPlus){

        CartAmountChangeEntity cartAmountChangeEntity=new CartAmountChangeEntity();
        cartAmountChangeEntity.setId(id);
        cartAmountChangeEntity.setAmount(amount);
        cartAmountChangeEntity.setPlus(isPlus);
        cartAmountChangeEntity.setPm(isPm);
        motifyCartHandler.obtainMessage(MODIFY_CART_CODE,cartAmountChangeEntity).sendToTarget();
    }

    private class CartAmountChangeEntity{
        private int id;
        private int amount;
        private boolean isPm;
        private boolean isPlus;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public boolean isPm() {
            return isPm;
        }

        public void setPm(boolean isPm) {
            this.isPm = isPm;
        }

        public boolean isPlus() {
            return isPlus;
        }

        public void setPlus(boolean isPlus) {
            this.isPlus = isPlus;
        }
    }
    private String getSelectedCartItemKeyList(){

        StringBuilder stringBuilder=new StringBuilder();
        	if(list!=null && list.size()>0) {

            for (CartItemEntity cartItemEntity : list) {
                if (cartItemEntity.getType() == 0 && cartItemEntity.getCartProduct() != null) {
                    if (cartItemEntity.getCartProduct().getSelected()) {
                        stringBuilder.append("0_"+cartItemEntity.getCartProduct().getOrderProductInfo().getPid()+",");
                    }
                } else if (cartItemEntity.getType() == 1 && cartItemEntity.getCartSuit() != null) {
                    if (cartItemEntity.getCartSuit().getChecked()) {
                        stringBuilder.append("1_"+cartItemEntity.getCartSuit().getPmId()+",");
                    }
                } else if (cartItemEntity.getType() == 2 && cartItemEntity.getCartFullSend() != null) {
                    if (cartItemEntity.getCartFullSend().getFullSendMainCartProductList() != null && cartItemEntity.getCartFullSend().getFullSendMainCartProductList().size() > 0) {
                        ArrayList<CartProductEntity> productEntityArrayList = cartItemEntity.getCartFullSend().getFullSendMainCartProductList();
                        ArrayList<CartProductEntity> newProductEntityArrayList = new ArrayList<CartProductEntity>();
                        for (CartProductEntity cartProductEntity : productEntityArrayList) {
                            if (cartProductEntity.getSelected()) {
                                stringBuilder.append("0_"+cartProductEntity.getOrderProductInfo().getPid()+",");
                            }
                        }

                        cartItemEntity.getCartFullSend().setFullSendMainCartProductList(newProductEntityArrayList);
                        if (newProductEntityArrayList.size() > 0) {
                            
                        }
                    }
                } else if (cartItemEntity.getType() == 3 && cartItemEntity.getCartFullCut() != null) {
                    if (cartItemEntity.getCartFullCut().getFullCutCartProductList() != null && cartItemEntity.getCartFullCut().getFullCutCartProductList().size() > 0) {
                        ArrayList<CartProductEntity> productEntityArrayList = cartItemEntity.getCartFullCut().getFullCutCartProductList();
                        ArrayList<CartProductEntity> newProductEntityArrayList = new ArrayList<CartProductEntity>();
                        for (CartProductEntity cartProductEntity : productEntityArrayList) {
                            if (cartProductEntity.getSelected()) {
                               stringBuilder.append("0_"+cartProductEntity.getOrderProductInfo().getPid()+",");
                            }
                        }
                        cartItemEntity.getCartFullCut().setFullCutCartProductList(newProductEntityArrayList);
                        if (newProductEntityArrayList.size() > 0) {
                           
                        }
                    }
                }
            }
        }
        
        return stringBuilder.toString();
        
	}
    @Override
    public void itemClick(int groupId, int position) {
    	isBackFromCart=true;
        CartItemEntity cartItemEntity=list.get(groupId);
        CartProductEntity cartProductEntity=null;
        if(cartItemEntity.getType()==0){
            cartProductEntity=cartItemEntity.getCartProduct();
            int pid=cartItemEntity.getCartProduct().getOrderProductInfo().getPid();
            Intent intent=new Intent(activity, HomeHeaderActivity.class);
            intent.putExtra("productId",pid);
            intent.putExtra("type",HeaderActivityType.ProductDetail.ordinal());
            intent.putExtra("hasHeader",false);
            intent.putExtra("isFromShopcart", true);
            startActivity(intent);
        }else if(cartItemEntity.getType()==1){
            //cartProductEntity=cartItemEntity.getCartSuit().getCartProductList().get(position);
            int id=cartItemEntity.getCartSuit().getPmId();
            //String title=cartItemEntity.getCartSuit().
            Intent intent=new Intent(activity,HomeHeaderActivity.class);
            intent.putExtra("type",HeaderActivityType.ComboDetail.ordinal());
            intent.putExtra("pmId", id);
            intent.putExtra("title", cartItemEntity.getCartSuit().getSuitPromotion().getName());
            intent.putExtra("isFromShopcart", true);
            startActivity(intent);
	        //intent1.putExtra("title", title);
	       // startActivity(intent);
        }else if(cartItemEntity.getType()==2){
            cartProductEntity=cartItemEntity.getCartFullSend().getFullSendMainCartProductList().get(position);
        }else if(cartItemEntity.getType()==3){
            cartProductEntity=cartItemEntity.getCartFullCut().getFullCutCartProductList().get(position);
        }
        if(cartProductEntity!=null){
            Toast.makeText(activity,cartProductEntity.getOrderProductInfo().getName(),Toast.LENGTH_SHORT).show();
        }
    }

    private final int MODIFY_PLUS_CART_CODE=111;
    private final int MODIFY_CUT_CART_CODE=112;
    private final int DELETE_CAR_CODE=113;
    private final int MODIFY_CART_CODE=114;

    private Handler motifyCartHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MODIFY_PLUS_CART_CODE:
                    CartProductEntity cartProductEntity=(CartProductEntity)msg.obj;
                    motifyCartAmount(cartProductEntity.getOrderProductInfo().getPid(),cartProductEntity.getOrderProductInfo().getBuyCount(),false,true);
                    break;
                case MODIFY_CUT_CART_CODE:
                    CartProductEntity cartProductEntity1=(CartProductEntity)msg.obj;
                    motifyCartAmount(cartProductEntity1.getOrderProductInfo().getPid(),cartProductEntity1.getOrderProductInfo().getBuyCount(),false,false);

                    break;
                case DELETE_CAR_CODE:
                    deleteCartByList();
                    break;
                case MODIFY_CART_CODE:
                    CartAmountChangeEntity cartAmountChangeEntity=(CartAmountChangeEntity)msg.obj;
                    motifyCartAmount(cartAmountChangeEntity.getId(),cartAmountChangeEntity.getAmount(),cartAmountChangeEntity.isPm(),cartAmountChangeEntity.isPlus());
                    break;
            }
        }
    };


	private CheckBox cb_delete,cb_select;


	private HomeActivity activity2;


	private boolean isFromDetails;

	private Button parent_cart_submit_button;

    private void deleteCartByList(){
    	
        StringBuilder stringBuilder=new StringBuilder();
        StringBuilder pmStringBuilder=new StringBuilder();
        for(CartItemEntity cartItemEntity:list){
            CartProductEntity cartProductEntity=null;
            if(cartItemEntity.getType()==0){
                cartProductEntity=cartItemEntity.getCartProduct();
                if(cartProductEntity.getDeleteSelected()){
                    stringBuilder.append(cartProductEntity.getOrderProductInfo().getPid() + ",");
                }

            }else if(cartItemEntity.getType()==1 &&cartItemEntity.getCartSuit()!=null){
                //cartProductEntity=cartItemEntity.getCartSuit().getCartProductList().get(position);
                if(cartItemEntity.getCartSuit().getDeleteChecked()){
                    pmStringBuilder.append(cartItemEntity.getCartSuit().getPmId() + ",");
                }

            }else if(cartItemEntity.getType()==2 &&cartItemEntity.getCartFullSend().getFullSendMainCartProductList()!=null){
                //cartProductEntity=cartItemEntity.getCartFullSend().getFullSendMainCartProductList().get(position);
                for(CartProductEntity cartProductEntity1:cartItemEntity.getCartFullSend().getFullSendMainCartProductList()){
                    if(cartProductEntity1.getDeleteSelected()){
                        stringBuilder.append(cartProductEntity1.getOrderProductInfo().getPid() + ",");
                    }
                }
            }else if(cartItemEntity.getType()==3 &&cartItemEntity.getCartFullCut().getFullCutCartProductList()!=null){
                //cartProductEntity=cartItemEntity.getCartFullCut().getFullCutCartProductList().get(position);
                for(CartProductEntity cartProductEntity1 :cartItemEntity.getCartFullCut().getFullCutCartProductList()){
                    if(cartProductEntity1.getDeleteSelected()){
                        stringBuilder.append(cartProductEntity1.getOrderProductInfo().getPid() + ",");
                    }
                }
            }

        }
        if(stringBuilder.length()>0 || pmStringBuilder.length()>0){
            deleteCartRequest(stringBuilder.toString(),pmStringBuilder.toString());
        }


    }
    
    private void motifyCartAmount(int id,int amount,boolean isPm,boolean isPlus){



        BaseRequestClient client=new BaseRequestClient(activity);
        UserResult userResult=baseApplication.getUserResult();
        PhoneModifyCartRequest request=new PhoneModifyCartRequest();
        request.setSelectedCartItemKeyList(getSelectedCartItemKeyList());
        if(isPm){
            request.setPmId(id);

        }else{
            request.setGoodsId(id);
        }
        if(!isPlus){
            request.setGoodsNum(amount-1);
        }else{
            request.setGoodsNum(amount+1);
        }

        ShoppingCartRequestClient shoppingCartRequestClient=new ShoppingCartRequestClient(activity,baseApplication);
        shoppingCartRequestClient.modifyCart(request, new ShoppingCartRequestClient.ShoppingCartRequestClientCallback() {
            @Override
            protected void listSuccess(CartItemResult result) {
            	if (getActivity()==null) {
					return;
				}
            	getUserCartCount();
            	//baseApplication.setCartCount(result.getTotalCount());
            	parent_cart_submit_button.setText("结算("+result.getTotalCount()+")");
            	parent_cart_total_amount.setText(String.format("￥%.1f",result.getOrderAmount()));
                parent_cart_empty.setVisibility(View.GONE);
                cartPayContainer.setVisibility(View.VISIBLE);
                list.clear();
                ArrayList<CartItemEntity> cartItemList = result.getCartItemList();
                Set<Integer> set = map.keySet();
                if (set.size()!=0) {
                	for (Integer integer : set) {
                		CartItemEntity cartItemEntity = cartItemList.get(integer);
                		if(cartItemEntity.getType()==0){
                            	cartItemEntity.getCartProduct().setSelected(map.get(integer));
                              

                        }else if(cartItemEntity.getType()==1){
                        	cartItemEntity.getCartSuit().setChecked(map.get(integer));
                           }
                	}
				}
                list.addAll(cartItemList);
                parentCartListAdapter.notifyDataSetChanged(ParentCartListView.MODE_NORMAL,result.getFullDiscountPromotionConfigInfo());
            }

            @Override
            protected void listEmpty(CartItemResult result) {
            	if (getActivity()==null) {
					return;
				}
            	baseApplication.setCartCount(result.getTotalCount());
            	if (!isFromDetails) {
            		setBadgeViewText(result.getTotalCount()+"");
                }
            	if (result.getCode()==931||result.getCode()==999) {
					
				}else {
					parent_cart_empty.setVisibility(View.VISIBLE);
	                parent_cart_empty_text.setVisibility(View.VISIBLE);
				}
                
            }
        });


    }


    private class CartButtonOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cart_delete_button:
                    motifyCartHandler.sendEmptyMessage(DELETE_CAR_CODE);
                    break;
            }
        }
    }

    private void deleteCartRequest(String deleteGoodsIds,String deletePms){

        BaseRequestClient client=new BaseRequestClient(activity);
        UserResult userResult=baseApplication.getUserResult();
        PhonecartdeleteRequest request=new PhonecartdeleteRequest();
        request.setGoodsId(deleteGoodsIds);
        request.setPmId(deletePms);

        ShoppingCartRequestClient shoppingCartRequestClient=new ShoppingCartRequestClient(activity,baseApplication);
        shoppingCartRequestClient.deleteCart(request, new ShoppingCartRequestClient.ShoppingCartRequestClientCallback() {
            @Override
            protected void listSuccess(CartItemResult result) {
            	if (getActivity()==null) {
					return;
				}
            	baseApplication.setCartCount(result.getTotalCount());
            	if (!isFromDetails) {
            		setBadgeViewText(result.getTotalCount()+"");
                }
            	parent_cart_submit_button.setText("结算("+result.getTotalCount()+")");
            	parent_cart_empty.setVisibility(View.GONE);
                parent_cart_total_amount.setText(String.format("￥%.1f",result.getOrderAmount()));
                list.clear();
                list.addAll(result.getCartItemList());
                parentCartListAdapter.notifyDataSetChanged(ParentCartListView.MODE_EDIT,result.getFullDiscountPromotionConfigInfo());
            }

            @Override
            protected void listEmpty(CartItemResult result) {
            	if (getActivity()==null) {
					return;
				}
            	baseApplication.setCartCount(result.getTotalCount());
            	if (!isFromDetails) {
            		setBadgeViewText(result.getTotalCount()+"");
                }
            	 parent_cart_empty.setVisibility(View.VISIBLE);
                parent_cart_empty_text.setVisibility(View.VISIBLE);
                changeState(false);
				cartEditButton.setVisibility(View.INVISIBLE);
				cartPayContainer.setVisibility(View.VISIBLE);
            }
        });


    }


}
