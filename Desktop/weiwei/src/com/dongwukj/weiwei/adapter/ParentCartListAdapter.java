package com.dongwukj.weiwei.adapter;

import java.util.List;

import android.util.Log;
import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.LLSInterface;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.CartItemEntity;
import com.dongwukj.weiwei.idea.result.FullDiscountPromotionConfigInfo;
import com.dongwukj.weiwei.ui.widget.ChildCartListView;
import com.dongwukj.weiwei.ui.widget.ParentCartListView;

/**
 * Created by sunjaly on 15/3/27.
 */
public class ParentCartListAdapter extends BaseAdapter {

    private Context context;

    private List<CartItemEntity> list;
    ViewHolder viewHolder=null;
    private boolean isShow=false;
    private FinalBitmap finalBitmap;
    private int currentMode= ParentCartListView.MODE_NORMAL;
    private ChildCartListView.ChildCartListChangeListener childCartListChangeListener;

    public ParentCartListAdapter(Context context,List<CartItemEntity> list,ChildCartListView.ChildCartListChangeListener childCartListChangeListener) {
        this(context,list,childCartListChangeListener,false);
    }

    public ParentCartListAdapter(Context context,List<CartItemEntity> list,ChildCartListView.ChildCartListChangeListener childCartListChangeListener,boolean isShow){
        this.context = context;
        this.list=list;
        this.childCartListChangeListener=childCartListChangeListener;
        this.isShow=isShow;
        finalBitmap=FinalBitmap.create(this.context);
        finalBitmap.configLoadfailImage(R.drawable.default_small);
        finalBitmap.configLoadingImage(R.drawable.default_small);
        
    }


    public void notifyDataSetChanged(int mode,FullDiscountPromotionConfigInfo fullDiscountPromotionConfigInfo) {
        this.currentMode=mode;
        this.fullDiscountPromotionConfigInfo=fullDiscountPromotionConfigInfo;
        super.notifyDataSetChanged();

    }
    /*public void setFullDiscountPromotionConfigInfo(FullDiscountPromotionConfigInfo fullDiscountPromotionConfigInfo){
    	this.fullDiscountPromotionConfigInfo=fullDiscountPromotionConfigInfo;
    }*/
    private FullDiscountPromotionConfigInfo fullDiscountPromotionConfigInfo;

	private String imgUrl;

	private String TaocanImgUrl;

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
        return position;
    }
    public void setImageUrl(String imgUrl, String TaocanImgUrl){
    	this.imgUrl=imgUrl;
    	this.TaocanImgUrl=TaocanImgUrl;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       
        if(convertView==null){
            convertView=View.inflate(this.context, R.layout.parent_cart_list_item,null);;
            viewHolder=new ViewHolder();
            viewHolder.childCartListView=(ChildCartListView)convertView.findViewById(R.id.child_cart_list_view);
            viewHolder.parentCartListItemHeader=(LinearLayout)convertView.findViewById(R.id.parent_cart_list_item_header);
            viewHolder.totalContainerView=convertView.findViewById(R.id.parent_cart_total_container);
            viewHolder.parentCartListItemCheckBox=(CheckBox)convertView.findViewById(R.id.parent_cart_list_item_checkbox);
            viewHolder.ll_slogan=(LinearLayout)convertView.findViewById(R.id.ll_slogan);
            viewHolder.parent_cart_list_item_image=(ImageView)convertView.findViewById(R.id.parent_cart_list_item_image);
            viewHolder.parent_cart_list_item_title=(TextView)convertView.findViewById(R.id.parent_cart_list_item_title);
            viewHolder.parent_cart_list_item_price=(TextView)convertView.findViewById(R.id.parent_cart_list_item_price);
            viewHolder.parent_cart_list_item_count=(TextView)convertView.findViewById(R.id.parent_cart_list_item_count);
            viewHolder.tv_slogan=(TextView)convertView.findViewById(R.id.tv_slogan);
            viewHolder.parent_cart_list_item_motifyamount_container=(LinearLayout)convertView.findViewById(R.id.parent_cart_list_item_motifyamount_container);
            viewHolder.tv_total_discount=(TextView)convertView.findViewById(R.id.tv_total_discount);
            viewHolder.tv_total_shop=(TextView)convertView.findViewById(R.id.tv_total_shop);
            
           // convertView.findViewById(R.id.parent_cart_list_item_container).setOnClickListener(new View.OnClickListener() {


            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.parent_cart_list_item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childCartListChangeListener != null) {
                    childCartListChangeListener.itemClick(position, position);
                }
            }
        });

        viewHolder.parentCartListItemCheckBox.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(childCartListChangeListener!=null){
                    childCartListChangeListener.itemCheck(((CheckBox)v).isChecked(),position,-1,currentMode);
                }
            }
        });
        CartItemEntity cartItemEntity=list.get(position);
        if(cartItemEntity.getType()==1 && cartItemEntity.getCartSuit()!=null){
        	if(currentMode==ParentCartListView.MODE_NORMAL){
                viewHolder.parentCartListItemCheckBox.setChecked(cartItemEntity.getCartSuit().getChecked());
            }else{
                viewHolder.parentCartListItemCheckBox.setChecked(cartItemEntity.getCartSuit().getDeleteChecked());
            }
           /* viewHolder.parentCartListItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(childCartListChangeListener!=null){
                        childCartListChangeListener.itemCheck(isChecked,position,-1,currentMode);
                    }
                }
            });*/

            viewHolder.childCartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int subPosition, long id) {
                    if(childCartListChangeListener!=null){
                        childCartListChangeListener.itemClick(position,subPosition);
                    }
                }
            });
            ImageButton reduceButton=(ImageButton)convertView.findViewById(R.id.parent_cart_list_item_reduce);
            reduceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	if (currentMode==ParentCartListView.MODE_EDIT) {
                	
					}else {
						if(childCartListChangeListener!=null){
	                        childCartListChangeListener.upOrDownAmount(position,position,false,viewHolder.parent_cart_list_item_count.getText().toString().trim());
	                    }
					}
                   
                }
            });
            ImageButton plusButton=(ImageButton)convertView.findViewById(R.id.parent_cart_list_item_plus);
            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	if (currentMode==ParentCartListView.MODE_EDIT) {
                		 
					}else {
						if(childCartListChangeListener!=null){
	                        childCartListChangeListener.upOrDownAmount(position,position,true,viewHolder.parent_cart_list_item_count.getText().toString().trim());
	                    }
					}
                   
                }
            });
            if (cartItemEntity.getCartSuit().getSuitPromotion()!=null) {
            	finalBitmap.display( viewHolder.parent_cart_list_item_image, TaocanImgUrl.replace("{0}", cartItemEntity.getCartSuit().getSuitPromotion().getShowImg()));
            }
            viewHolder.ll_slogan.setVisibility(View.GONE);
            viewHolder.parent_cart_list_item_title.setText(cartItemEntity.getCartSuit().getSuitPromotion().getName());
            viewHolder.parent_cart_list_item_price.setText(String.format("价格：%.2f x%d  ",cartItemEntity.getCartSuit().getSuitPrice(),cartItemEntity.getCartSuit().getBuyCount()));
            viewHolder.parent_cart_list_item_count.setText(cartItemEntity.getCartSuit().getBuyCount()+"");
            viewHolder.parentCartListItemHeader.setVisibility(View.VISIBLE);
            viewHolder.tv_total_shop.setVisibility(View.GONE);
            viewHolder.tv_total_discount.setText(String.format("￥%.2f", cartItemEntity.getCartSuit().getSuitPrice()*cartItemEntity.getCartSuit().getBuyCount()));
        }else{
            viewHolder.parentCartListItemHeader.setVisibility(View.GONE);
            if (fullDiscountPromotionConfigInfo!=null) {
				Integer id = cartItemEntity.getCartProduct().getOrderProductInfo().getCateId();
				if (fullDiscountPromotionConfigInfo.getTargetCateIdList().contains(id)||fullDiscountPromotionConfigInfo.getFromCateIdList().contains(id)) {
					if (cartItemEntity.getCartProduct().getSinglePromotion()==null) {
						viewHolder.ll_slogan.setVisibility(View.VISIBLE);
						viewHolder.tv_slogan.setText(fullDiscountPromotionConfigInfo.getName());
					}else {
						viewHolder.ll_slogan.setVisibility(View.GONE);
					}
				}else {
					viewHolder.ll_slogan.setVisibility(View.GONE);
				}
			}
            changtotal(viewHolder, cartItemEntity);
        }
        ChildCartListAdapter childCartListAdapter=new ChildCartListAdapter(this.context,position,list.get(position),childCartListChangeListener,currentMode,isShow,imgUrl);
        viewHolder.childCartListView.setAdapter(childCartListAdapter);
        if(currentMode==ParentCartListView.MODE_NORMAL){
            viewHolder.totalContainerView.setVisibility(View.VISIBLE);
        }else{
            viewHolder.totalContainerView.setVisibility(View.GONE);
        }

        if(isShow){
            viewHolder.parentCartListItemCheckBox.setVisibility(View.GONE);
            viewHolder.parent_cart_list_item_motifyamount_container.setVisibility(View.GONE);
            viewHolder.totalContainerView.setVisibility(View.GONE);
        }

        return convertView;
    }

	private void changtotal(ViewHolder viewHolder, CartItemEntity cartItemEntity) {
		if (cartItemEntity.getCartProduct().getOrderProductInfo().getDiscountPrice().equals(cartItemEntity.getCartProduct().getOrderProductInfo().getShopPrice())) {
			viewHolder.tv_total_shop.setVisibility(View.GONE);
		    viewHolder.tv_total_discount.setText(String.format("￥%.2f", cartItemEntity.getCartProduct().getOrderProductInfo().getDiscountPrice()*cartItemEntity.getCartProduct().getOrderProductInfo().getBuyCount()));
		}else {
			if (cartItemEntity.getCartProduct().getSelected()) {
				viewHolder.tv_total_shop.setVisibility(View.VISIBLE);
				viewHolder.tv_total_discount.setText(String.format("￥%.2f", cartItemEntity.getCartProduct().getOrderProductInfo().getDiscountPrice()*cartItemEntity.getCartProduct().getOrderProductInfo().getBuyCount()));
			    viewHolder.tv_total_shop.setText(String.format("￥%.2f", cartItemEntity.getCartProduct().getOrderProductInfo().getShopPrice()*cartItemEntity.getCartProduct().getOrderProductInfo().getBuyCount()));
			    viewHolder.tv_total_shop.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		/*	    viewHolder.ll_slogan.setVisibility(View.VISIBLE);
				viewHolder.tv_slogan.setText(fullDiscountPromotionConfigInfo.getName());*/
			}else {
				viewHolder.tv_total_shop.setVisibility(View.GONE);
			    viewHolder.tv_total_discount.setText(String.format("￥%.2f", cartItemEntity.getCartProduct().getOrderProductInfo().getShopPrice()*cartItemEntity.getCartProduct().getOrderProductInfo().getBuyCount()));
			}
		}
	}

	
    private class ViewHolder{
        public ChildCartListView childCartListView;
        public View totalContainerView;
        public LinearLayout parentCartListItemHeader;
        public CheckBox parentCartListItemCheckBox;
        public ImageView parent_cart_list_item_image;
        public TextView parent_cart_list_item_title;
        public TextView parent_cart_list_item_price;
        public TextView parent_cart_list_item_count;
        public LinearLayout parent_cart_list_item_motifyamount_container;
        public LinearLayout ll_slogan;
        public TextView tv_slogan;
        public TextView tv_total_discount;
        public TextView tv_total_shop;

    }

    private boolean discountPriceIsShow=true;
	public void setDiscountPriceIsShow(boolean discountPriceIsShow) {
		this.discountPriceIsShow=discountPriceIsShow;
	}
}
