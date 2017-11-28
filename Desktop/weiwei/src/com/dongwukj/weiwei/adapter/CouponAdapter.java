package com.dongwukj.weiwei.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.dongwukj.weiwei.R;


import com.dongwukj.weiwei.idea.result.CouponListEntity;
import com.dongwukj.weiwei.idea.result.CouponTypeEntity;
import com.dongwukj.weiwei.idea.result.LimitProducts;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CouponAdapter extends BaseAdapter {
	private ArrayList<CouponTypeEntity> list ;
	private Context context;
	
	
	public CouponAdapter(ArrayList<CouponTypeEntity> list, Context context) {
		super();
		this.list = list;
		this.context = context;
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
		ViewHolder holder = null;
		if(convertView==null){
			holder =new ViewHolder();
			convertView = View.inflate(context, R.layout.my_coupon_item_layout, null);
			holder.tv_claim_coupon = (TextView) convertView.findViewById(R.id.tv_claim_coupon);
			
			holder.tv_claim_coupon.setVisibility(View.GONE);
			holder.ll=(LinearLayout) convertView.findViewById(R.id.ll);
			holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
			holder.tv_order_amountlower = (TextView) convertView.findViewById(R.id.tv_order_amountlower);
			holder.tv_UseStartTime = (TextView) convertView.findViewById(R.id.tv_UseStartTime);
			holder.tv_UseEndTime = (TextView) convertView.findViewById(R.id.tv_UseEndTime);
			holder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
			convertView.setTag(holder);
			
		}else{
			
			holder = (ViewHolder) convertView.getTag();
		}
		CouponListEntity entity = list.get(position).getCouponType();
		if (TextUtils.isEmpty(entity.getLimitCateName())&&entity.getLimitProducts().size()==0) {
			holder.ll.setBackgroundResource(R.drawable.weiwei_coupon_green);
			holder.tv_order_amountlower.setText("消费满"+(int)entity.getOrderAmountLower()+"元使用(所有商品可用)");
			holder.tv_product_name.setVisibility(View.GONE);
		}else if (TextUtils.isEmpty(entity.getLimitCateName())&&entity.getLimitProducts().size()!=0) {
			holder.ll.setBackgroundResource(R.drawable.weiwei_coupon_yellow);
			holder.tv_order_amountlower.setText("消费满"+entity.getOrderAmountLower()+"元使用(以下商品可用)");
			holder.tv_product_name.setVisibility(View.VISIBLE);
			StringBuffer name=new StringBuffer();
			ArrayList<LimitProducts> products = entity.getLimitProducts();
			for (LimitProducts limitProducts : products) {
				name=name.append(limitProducts.getName());
			}
			holder.tv_product_name.setText(name);
		}else if (!TextUtils.isEmpty(entity.getLimitCateName())&&entity.getLimitProducts().size()==0) {
			holder.ll.setBackgroundResource(R.drawable.weiwei_coupon_red);
			holder.tv_order_amountlower.setText("消费满"+entity.getOrderAmountLower()+"元使用("+entity.getLimitCateName()+"类别可用)");
			holder.tv_product_name.setVisibility(View.GONE);
		}
		holder.tv_money.setText((int)list.get(position).getCouponType().getMoney()+"");
//		holder.tv_order_amountlower.setText("消费满"+list.get(position).getCouponType().getOrderAmountLower()+"元使用(不含促销商品)");
		 
		holder.tv_UseStartTime.setText(sm.format(new Date(Long.parseLong(list.get(position).getCouponType().getUseStartTime()))));
		holder.tv_UseEndTime.setText(sm.format(new Date(Long.parseLong(list.get(position).getCouponType().getUseEndTime()))));
		return convertView;
	}
	SimpleDateFormat sm=new SimpleDateFormat("yyyy.MM.dd");

	class ViewHolder{
		LinearLayout ll;
		TextView tv_product_name;
		TextView tv_money;
		TextView tv_order_amountlower;
		TextView tv_UseStartTime;  //使用开始时间
		TextView tv_UseEndTime;  //使用过期时间
		
		TextView tv_claim_coupon;
		
	}


}
