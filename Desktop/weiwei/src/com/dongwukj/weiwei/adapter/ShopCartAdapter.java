package com.dongwukj.weiwei.adapter;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;
import cn.jpush.android.api.c;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.dongwukj.weiwei.idea.request.Unit;
import com.dongwukj.weiwei.idea.result.ExplainEntity;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopCartAdapter extends BaseAdapter {


	private Context context;
	private ArrayList<NewHomeEntity> newHomelists;
	private ViewHolder holder;
	private CartClick cartClick;
	private boolean isEdit;
	private boolean isOnlyShow;
	private int weightBalance;
	private FinalBitmap fb;
	private ExplainEntity explainEntity=new  ExplainEntity();
	
	public ExplainEntity getExplainEntity() {
		return explainEntity;
	}

	public void setExplainEntity(ExplainEntity explainEntity) {
		this.explainEntity = explainEntity;
		notifyDataSetChanged();
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public ShopCartAdapter(Context context, Boolean isOnlyShow,ArrayList<NewHomeEntity> newHomelists,CartClick cartClick) {
		super();
		this.context = context;
		this.newHomelists=newHomelists;
		this.cartClick=cartClick;
		this.isOnlyShow=isOnlyShow;
		fb = FinalBitmap.create(context);
		fb.configLoadfailImage(R.drawable.default_small);
		fb.configLoadingImage(R.drawable.default_small);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newHomelists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return newHomelists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView=View.inflate(context, R.layout.cart_list_item, null);
			holder = new ViewHolder();
			holder.cart_list_item_checkbox=(CheckBox) convertView.findViewById(R.id.cart_list_item_checkbox);
			holder.cart_list_item_image=(ImageView) convertView.findViewById(R.id.cart_list_item_image);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_weight=(TextView) convertView.findViewById(R.id.tv_weight);
			holder.manjian=(TextView) convertView.findViewById(R.id.manjian);
			holder.weight_unit=(TextView) convertView.findViewById(R.id.weight_unit);
			holder.discount_describe=(TextView) convertView.findViewById(R.id.discount_describe);
			holder.id_remove=(Button) convertView.findViewById(R.id.id_remove);
			holder.parent_cart_list_item_motifyamount_container=(LinearLayout) convertView.findViewById(R.id.parent_cart_list_item_motifyamount_container);
			holder.parent_cart_total_container=(LinearLayout) convertView.findViewById(R.id.parent_cart_total_container);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		if (isOnlyShow) {
			holder.parent_cart_list_item_motifyamount_container.setVisibility(View.GONE);
			holder.cart_list_item_checkbox.setVisibility(View.GONE);
			holder.parent_cart_total_container.setVisibility(View.GONE);
		}else {
			holder.parent_cart_list_item_motifyamount_container.setVisibility(View.VISIBLE);
			holder.cart_list_item_checkbox.setVisibility(View.VISIBLE);
			holder.parent_cart_total_container.setVisibility(View.VISIBLE);
		}
		final NewHomeEntity entity = newHomelists.get(position);
		if (entity.getIsFullcut()==1) {
			holder.manjian.setVisibility(View.VISIBLE);
		}else {
			holder.manjian.setVisibility(View.GONE);
		}
		fb.display(holder.cart_list_item_image, entity.getIcon());
		holder.cart_list_item_checkbox.setChecked(entity.isChecked());
		holder.tv_name.setText(entity.getName());
		holder.cart_list_item_checkbox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cartClick.checkboxCheck(((CheckBox)v).isChecked(),entity.getBusinessgoodsid());
			}
		});
		holder.cart_list_item_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cartClick.click(newHomelists.get(position).getGid());
			}
		});
		holder.id_remove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			cartClick.delete(entity.getBusinessgoodsid(),false);
			}
		});
		if (entity.getIsmain()==1) {
			weightBalance=entity.getMaincourseminweight()-entity.getIncrementweight();
		}else {
			weightBalance=entity.getSidecourseminweight()-entity.getIncrementweight();
		}
		holder.tv_weight.setText("重量："+(int)(entity.getIncrementweight()*entity.getCount()+weightBalance)+entity.getUnit().getName());
		final TextView tv_num=(TextView) convertView.findViewById(R.id.tv_num);
		final TextView tv_total_discount=(TextView) convertView.findViewById(R.id.tv_total_discount);
		final TextView cart_list_item_count=(TextView) convertView.findViewById(R.id.cart_list_item_count);
		final TextView cart_list_item_weigth=(TextView) convertView.findViewById(R.id.cart_list_item_weigth);
		ImageButton cart_list_item_reduce=(ImageButton) convertView.findViewById(R.id.cart_list_item_reduce);
		ImageButton cart_list_item_plus=(ImageButton) convertView.findViewById(R.id.cart_list_item_plus);
		cart_list_item_count.setText(entity.getCount()+"");
		cart_list_item_weigth.setText((int)(entity.getIncrementweight()*entity.getCount()+weightBalance)+"");
		String name=null;
		Unit unit = entity.getUnit();
		if (unit.getIsweightunit()==1) {
			name="/500"+unit.getName();
		}else {
			name="/"+unit.getName();
		}
		holder.weight_unit.setText(unit.getName());
		if (entity.getBusinessGoodsDiscountObject().getDiscountType()==1) {
			if (isOnlyShow) {
				tv_num.setText("特价: ￥"+String.format("%.2f", entity.getBusinessGoodsDiscountObject().getDiscountprice())+name+explainEntity.getSpecialwords());
			}else {
				tv_num.setText("特价: ￥"+String.format("%.2f", entity.getBusinessGoodsDiscountObject().getDiscountprice())+name);
			}
			
			holder.discount_describe.setVisibility(View.VISIBLE);
			holder.discount_describe.setText(explainEntity.getSpecialwords());
			tv_total_discount.setText(String.format("￥%.2f", (entity.getCount()*entity.getIncrementweight()+weightBalance)*entity.getGramdiscountprice()));
		}else if (entity.getBusinessGoodsDiscountObject().getDiscountType()==2) {
			holder.discount_describe.setVisibility(View.VISIBLE);
			if ((entity.getBusinessGoodsDiscountObject().getDiscount()-(int)(entity.getBusinessGoodsDiscountObject().getDiscount()))>0) {
				holder.discount_describe.setText(Html.fromHtml(explainEntity.getDiscountprefix()+"<font color='red'>"+entity.getBusinessGoodsDiscountObject().getDiscount()+"</font>"+explainEntity.getDiscountsuffix()));
			}else {
				holder.discount_describe.setText(Html.fromHtml(explainEntity.getDiscountprefix()+"<font color='red'>"+(int)entity.getBusinessGoodsDiscountObject().getDiscount()+"</font>"+explainEntity.getDiscountsuffix()));
			}
			//holder.discount_describe.setText(Html.fromHtml("余额支付<font color='red'>"+entity.getBusinessGoodsDiscountObject().getDiscount()+"</font>折"));
			tv_total_discount.setText(String.format("￥%.2f", (entity.getCount()*entity.getIncrementweight()+weightBalance)*entity.getGramprice()));
			if (isOnlyShow) {
				//tv_num.setText("价格 : ￥"+String.format("%.2f", entity.getPrice())+Html.fromHtml("余额支付<font color='red'>"+entity.getBusinessGoodsDiscountObject().getDiscount()+"</font>折"));
				if ((entity.getBusinessGoodsDiscountObject().getDiscount()-(int)(entity.getBusinessGoodsDiscountObject().getDiscount()))>0) {
					tv_num.setText(Html.fromHtml("价格 : ￥"+String.format("%.2f", entity.getPrice())+name+explainEntity.getDiscountprefix()+" (<font color='red'>"+entity.getBusinessGoodsDiscountObject().getDiscount()+"</font>)"+explainEntity.getDiscountsuffix()));
				}else {
					tv_num.setText(Html.fromHtml("价格 : ￥"+String.format("%.2f", entity.getPrice())+name+explainEntity.getDiscountprefix()+" (<font color='red'>"+(int)entity.getBusinessGoodsDiscountObject().getDiscount()+"</font>)"+explainEntity.getDiscountsuffix()));
				}
				
			}else {
				tv_num.setText("价格 : ￥"+String.format("%.2f", entity.getPrice())+name);
			}
			
		}else {
			tv_num.setText("价格 : ￥"+String.format("%.2f", entity.getPrice())+name);
			holder.discount_describe.setVisibility(View.INVISIBLE);
			tv_total_discount.setText(String.format("￥%.2f", (entity.getCount()*entity.getIncrementweight()+weightBalance)*entity.getGramprice()));
		}
		cart_list_item_reduce.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isEdit) {
					return;
				}
				if (Integer.parseInt(cart_list_item_count.getText().toString().trim())==0) {
					return;
				}else if (Integer.parseInt(cart_list_item_count.getText().toString().trim())==1) {
					cartClick.delete(entity.getBusinessgoodsid(),true);
				}else {
					cartClick.jian(entity.getBusinessgoodsid(),Integer.parseInt(cart_list_item_count.getText().toString().trim())-1);
				}
			}
		});
		cart_list_item_plus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isEdit) {
					return;
				}
				if (Integer.parseInt(cart_list_item_weigth.getText().toString().trim())+entity.getIncrementweight()>entity.getStockNum()) {
					Toast.makeText(context, "库存不足", Toast.LENGTH_SHORT).show();
					return;
				}
				if (Integer.parseInt(cart_list_item_weigth.getText().toString().trim())+entity.getIncrementweight()>entity.getLimitamount()&&entity.getLimitamount()>0) {
					Toast.makeText(context, "超过商品单次最大购买数量", Toast.LENGTH_SHORT).show();
					return;
				}
				cartClick.jia(entity.getBusinessgoodsid(), Integer.parseInt(cart_list_item_count.getText().toString().trim())+1);
			}
		});
		
		return convertView;
	}
	public class ViewHolder{
		public LinearLayout parent_cart_list_item_motifyamount_container;
		public LinearLayout parent_cart_total_container;
		public CheckBox cart_list_item_checkbox;
		public ImageView cart_list_item_image;
		public TextView tv_name;
		public TextView tv_weight;
		public TextView tv_num;
		public TextView manjian;
		public TextView weight_unit;
		public TextView tv_total_discount;
		public ImageButton cart_list_item_reduce;
		public ImageButton cart_list_item_plus;
		public TextView cart_list_item_count;
		public TextView discount_describe;
		public TextView cart_list_item_weigth;
		public Button id_remove;
	}
	public interface CartClick{
		public void jia(int goodId,int num);
		public void delete(Integer id,boolean isShow);
		public void jian(int goodId,int num);
		public void checkboxCheck(boolean b, int position);
		public void click(int gid);
	}
}
