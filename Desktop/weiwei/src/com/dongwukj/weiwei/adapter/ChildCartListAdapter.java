package com.dongwukj.weiwei.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.CartItemEntity;
import com.dongwukj.weiwei.idea.result.CartProductEntity;
import com.dongwukj.weiwei.idea.result.OrderProductInfo;
import com.dongwukj.weiwei.idea.result.ProductInfoEntity;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.widget.ChildCartListView;
import com.dongwukj.weiwei.ui.widget.ParentCartListView;

import net.tsz.afinal.FinalBitmap;

/**
 * Created by sunjaly on 15/3/27.
 */
public class ChildCartListAdapter extends BaseAdapter {

    private Context context;
    private int groupId;
    private CartItemEntity cartItemEntity;
    private FinalBitmap finalBitmap;
    private int currentMode= ParentCartListView.MODE_NORMAL;
    private ChildCartListView.ChildCartListChangeListener childCartListChangeListener;
    ViewHolder viewHolder=null;
    private boolean isShow;
	private String imgUrl;

    public ChildCartListAdapter(Context context,int groupId,CartItemEntity cartItemEntity,
                                ChildCartListView.ChildCartListChangeListener childCartListChangeListener,int mode,
                                boolean isShow, String imgUrl) {
        this.context = context;
        this.groupId=groupId;
        this.imgUrl=imgUrl;
        this.cartItemEntity=cartItemEntity;
        finalBitmap=FinalBitmap.create(this.context);
        finalBitmap.configLoadfailImage(R.drawable.default_small);
        finalBitmap.configLoadingImage(R.drawable.default_small);
        this.childCartListChangeListener=childCartListChangeListener;
        this.currentMode=mode;
        this.isShow=isShow;
    }

    @Override
    public int getCount() {
        if(cartItemEntity.getType()==0){
            if(cartItemEntity.getCartProduct()!=null){
                return 1;
            }
        }else if(cartItemEntity.getType()==1){
            if(cartItemEntity.getCartSuit()!=null){
                return cartItemEntity.getCartSuit().getCartProductList().size();
            }
        }else if(cartItemEntity.getType()==2){
            if(cartItemEntity.getCartFullSend()!=null){
                return cartItemEntity.getCartFullSend().getFullSendMainCartProductList().size();
            }
        }else if(cartItemEntity.getType()==3){
            if(cartItemEntity.getCartFullCut()!=null){
                return cartItemEntity.getCartFullCut().getFullCutCartProductList().size();
            }
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      
        if(convertView==null){
            convertView=View.inflate(context, R.layout.child_cart_list_item,null);
            viewHolder=new ViewHolder();
            viewHolder.checkBox=(CheckBox)convertView.findViewById(R.id.child_cart_list_item_checkbox);
            /*viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(childCartListChangeListener!=null){
                        childCartListChangeListener.itemCheck(isChecked,groupId,position,currentMode);
                    }
                }
            });*/
            viewHolder.checkBox.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(childCartListChangeListener!=null){
                        childCartListChangeListener.itemCheck(viewHolder.checkBox.isChecked(),groupId,position,currentMode);
                    }
				}
			});
            viewHolder.image=(ImageView)convertView.findViewById(R.id.child_cart_list_item_image);
            viewHolder.title=(TextView)convertView.findViewById(R.id.child_cart_list_item_title);
            viewHolder.description=(TextView)convertView.findViewById(R.id.child_cart_list_item_description);
            viewHolder.price=(TextView)convertView.findViewById(R.id.child_cart_list_item_price);
            viewHolder.amount=(TextView)convertView.findViewById(R.id.child_cart_list_item_count);
            viewHolder.tv_qianggou=(TextView)convertView.findViewById(R.id.tv_qianggou);
            viewHolder.tv_canbuy_num=(TextView)convertView.findViewById(R.id.tv_canbuy_num);
            viewHolder.amountMotifyContainer=(LinearLayout)convertView.findViewById(R.id.child_cart_list_item_motifyamount_container);
            ImageButton reduceButton=(ImageButton)convertView.findViewById(R.id.child_cart_list_item_reduce);
            reduceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(childCartListChangeListener!=null){
                    	if (currentMode==ParentCartListView.MODE_EDIT) {
                        	
    					}else {
    						if(childCartListChangeListener!=null){
    	                        childCartListChangeListener.upOrDownAmount(groupId,position,false,viewHolder.amount.getText().toString().trim());
    	                    }
    					}
                       
                    }
                }
            });
            ImageButton plusButton=(ImageButton)convertView.findViewById(R.id.child_cart_list_item_plus);
            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	if (currentMode==ParentCartListView.MODE_EDIT) {
                		 
					}else {
						if(childCartListChangeListener!=null){
	                        childCartListChangeListener.upOrDownAmount(groupId,position,true,viewHolder.amount.getText().toString().trim());
	                    }
					}
                   
                }
            });
            //convertView.findViewById(R.id.child_cart_list_item_container).setOnClickListener(new View.OnClickListener() {
            convertView.findViewById(R.id.child_cart_list_item_image).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(childCartListChangeListener!=null){
                        childCartListChangeListener.itemClick(groupId,position);
                    }
                }
            });

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }


        if(cartItemEntity.getType()!=1){
            if(cartItemEntity.getType()==0 && cartItemEntity.getCartProduct()!=null){
                fillContent(viewHolder,cartItemEntity.getCartProduct());

            }else if(cartItemEntity.getType()==2 && cartItemEntity.getCartFullSend()!=null){
                fillContent(viewHolder, cartItemEntity.getCartFullSend().getFullSendMainCartProductList().get(position));
            }else if(cartItemEntity.getType()==3 && cartItemEntity.getCartFullCut()!=null){
                fillContent(viewHolder,cartItemEntity.getCartFullCut().getFullCutCartProductList().get(position));
            }
            if(isShow){
                viewHolder.checkBox.setVisibility(View.GONE);
                viewHolder.amountMotifyContainer.setVisibility(View.GONE);
            }
        }else if(cartItemEntity.getType()==1 && cartItemEntity.getCartSuit()!=null){
            fillContent(viewHolder,cartItemEntity.getCartSuit().getCartProductList().get(position));
            viewHolder.checkBox.setVisibility(View.INVISIBLE);
            viewHolder.amountMotifyContainer.setVisibility(View.INVISIBLE);
            if(isShow){
                viewHolder.checkBox.setVisibility(View.INVISIBLE);
                viewHolder.amountMotifyContainer.setVisibility(View.GONE);
            }
        }



        return convertView;
    }

    private void fillContent(ViewHolder viewHolder,CartProductEntity cartProductEntity){
        if(currentMode==ParentCartListView.MODE_NORMAL){
            viewHolder.checkBox.setChecked(cartProductEntity.getSelected());
        }else{
            viewHolder.checkBox.setChecked(cartProductEntity.getDeleteSelected());
        }
        //viewHolder.checkBox.setChecked(cartProductEntity.getSelected());
        OrderProductInfo orderProductInfo=cartProductEntity.getOrderProductInfo();
        String Url=orderProductInfo.getShowImg();
        /*if(!NetworkUtil.checkUrl(Url)){
            Url= BaseApplication.BASE_IMAGE_HOST+imgUrl;
        }*/
        finalBitmap.display(viewHolder.image,imgUrl.replace("{0}", Url));
        viewHolder.title.setText(orderProductInfo.getName());
        if (cartProductEntity.getSinglePromotion()!=null) {
        	viewHolder.tv_canbuy_num.setText("限购"+cartProductEntity.getSinglePromotion().getAllowBuyCount()+"件");
        	viewHolder.tv_qianggou.setVisibility(View.VISIBLE);
         }else {
        	 viewHolder.tv_qianggou.setVisibility(View.INVISIBLE);
		}
        viewHolder.description.setText(String.format("%d克",orderProductInfo.getWeight()));
        viewHolder.price.setText(String.format("%.2f  x%d",orderProductInfo.getDiscountPrice(),orderProductInfo.getBuyCount()));
        viewHolder.amount.setText(orderProductInfo.getBuyCount()+"");
    }

    public class ViewHolder{
        public CheckBox checkBox;
        public ImageView image;
        public TextView title;
        public TextView description;
        public TextView price;
        public TextView amount;
        public TextView tv_canbuy_num;
        public TextView tv_qianggou;
        public LinearLayout amountMotifyContainer;
       
    }
}
