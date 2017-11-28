package com.dongwukj.weiwei.adapter;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import com.alibaba.fastjson.parser.deserializer.StringFieldDeserializer;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.NewOrderProductEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderDetailItemAdapter extends BaseAdapter {
	ViewHolder holder;
	private Context context;
	private ArrayList<NewOrderProductEntity> products;
	private FinalBitmap fb;
	private int state;
	public OrderDetailItemAdapter(Context context,
			ArrayList<NewOrderProductEntity> products,int state) {
		super();
		this.context = context;
		this.products = products;
		this.state=state;
		fb = FinalBitmap.create(context);
		fb.configLoadfailImage(R.drawable.default_small);
		fb.configLoadfailImage(R.drawable.default_small);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return products.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return products.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView=View.inflate(context, R.layout.order_detail_item, null);
			holder=new ViewHolder();
			holder.image=(ImageView) convertView.findViewById(R.id.image);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_weight=(TextView) convertView.findViewById(R.id.tv_weight);
			holder.tv_realweight=(TextView) convertView.findViewById(R.id.tv_realweight);
			holder.tv_price=(TextView) convertView.findViewById(R.id.tv_price);
			holder.tv_rebateamount=(TextView) convertView.findViewById(R.id.tv_rebateamount);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		NewOrderProductEntity entity = products.get(position);
		if (state>=70&&state<85) {
			holder.tv_realweight.setVisibility(View.GONE);
			holder.tv_rebateamount.setVisibility(View.GONE);
		}
		fb.display(holder.image, entity.getShowimg());
		holder.tv_name.setText(entity.getName());
		
		
			holder.tv_weight.setText((int)entity.getWeight()+entity.getUomname());
			holder.tv_realweight.setText("实际配送:"+(int)entity.getRealweight()+entity.getUomname());
			holder.tv_price.setText(String.format("总价:￥%.2f", entity.getProductamount()));
			holder.tv_rebateamount.setText(String.format("返款:￥%.2f", entity.getRebateamount()));
			return convertView;
	}
	class ViewHolder{
		public ImageView image;
		public TextView tv_name;
		public TextView tv_weight;
		public TextView tv_realweight;
		public TextView tv_price;
		public TextView tv_rebateamount;
	}
}
