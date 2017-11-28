package com.dongwukj.weiwei.adapter;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.dongwukj.weiwei.idea.request.Speciality;
import com.dongwukj.weiwei.idea.request.Unit;
import com.dongwukj.weiwei.idea.result.NewHomeResult;
import com.dongwukj.weiwei.ui.widget.MyGridViewNoClick;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewHomeListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<NewHomeEntity> newHomeLists;
	private ViewHolder holdel=null;
	private HomeClick homeClick;
	private FinalBitmap fb;
	private int weightBalance;
	private NewHomeResult entity=new NewHomeResult();
	public NewHomeListAdapter(Context context,ArrayList<NewHomeEntity> newHomeLists,HomeClick homeClick) {
		super();
		this.context = context;
		this.newHomeLists=newHomeLists;
		this.homeClick=homeClick;
		fb = FinalBitmap.create(context);
		fb.configLoadfailImage(R.drawable.default_small);
		fb.configLoadingImage(R.drawable.default_small);
	}

	
	

	public void setEntity(NewHomeResult entity) {
		this.entity = entity;
	}


	@Override
	public int getCount() {
		
		return newHomeLists.size();
	}

	@Override
	public Object getItem(int position) {
		
		return newHomeLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		if (convertView==null) {
			convertView=View.inflate(context, R.layout.new_home_list_item, null);
			holdel = new ViewHolder();
			holdel.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holdel.tv_weigth=(TextView) convertView.findViewById(R.id.tv_weigth);
			holdel.attr1=(TextView) convertView.findViewById(R.id.attr1);
			holdel.attr2=(TextView) convertView.findViewById(R.id.attr2);
			holdel.weight_unit=(TextView) convertView.findViewById(R.id.weight_unit);
			holdel.attr3=(TextView) convertView.findViewById(R.id.attr3);
			holdel.newPrice=(TextView) convertView.findViewById(R.id.newPrice);
			holdel.discount_des=(TextView) convertView.findViewById(R.id.discount_des);
			holdel.tv_pricename=(TextView) convertView.findViewById(R.id.tv_pricename);
			holdel.tv_gramdiscount=(TextView) convertView.findViewById(R.id.tv_gramdiscount);
			//holdel.discount_type=(TextView) convertView.findViewById(R.id.discount_type);
			holdel.img=(ImageView) convertView.findViewById(R.id.img);
			holdel.shouwan=(ImageView) convertView.findViewById(R.id.shouwan);
			holdel.ll_total=(LinearLayout) convertView.findViewById(R.id.ll_total);
			//holdel.view=(View) convertView.findViewById(R.id.view);
			holdel.gd=(MyGridViewNoClick) convertView.findViewById(R.id.gd);
			convertView.setTag(holdel);
		}else {
			holdel=(ViewHolder) convertView.getTag();
		}
		final NewHomeEntity homeEntity = newHomeLists.get(position);
		
		Gdadapter adapter=new Gdadapter(homeEntity.getIconList());
		holdel.gd.setAdapter(adapter);
		fb.display(holdel.img, homeEntity.getIcon());
		final TextView tv_totalWeight=(TextView) convertView.findViewById(R.id.tv_totalWeight);
		final TextView tv_totalPrice=(TextView) convertView.findViewById(R.id.tv_totalPrice);
		final TextView tv_num=(TextView) convertView.findViewById(R.id.tv_num);
		final TextView tv_total_weigth=(TextView) convertView.findViewById(R.id.tv_total_weigth);
		TextView tv_jia=(TextView) convertView.findViewById(R.id.tv_jia);
		TextView tv_jian=(TextView) convertView.findViewById(R.id.tv_jian);
		tv_num.setText(homeEntity.getCount()+"");
		if (homeEntity.getStatus()==1) {
			holdel.shouwan.setVisibility(View.GONE);
			tv_jia.setVisibility(View.VISIBLE);
			holdel.weight_unit.setVisibility(View.VISIBLE);
		}else {
			holdel.shouwan.setVisibility(View.VISIBLE);
			tv_jia.setVisibility(View.GONE);
			holdel.weight_unit.setVisibility(View.GONE);
		}
		if (homeEntity.getIsmain()==1) {
			weightBalance=homeEntity.getMaincourseminweight()-homeEntity.getIncrementweight();
		}else {
			weightBalance=homeEntity.getSidecourseminweight()-homeEntity.getIncrementweight();
		}
		if (homeEntity.getCount()==0) {
			weightBalance=0;
		}
		tv_total_weigth.setText((int)(homeEntity.getCount()*homeEntity.getIncrementweight()+weightBalance)+"");
		String name=null;
		final Unit unit = homeEntity.getUnit();
		if (unit.getIsweightunit()==1) {
			name="/500"+unit.getName();
		}else {
			name="/"+unit.getName();
		}
		holdel.weight_unit.setText(unit.getName());
		holdel.tv_gramdiscount.setVisibility(View.GONE);
		switch (homeEntity.getBusinessGoodsDiscountObject().getDiscountType()) {
		case 0:
			holdel.newPrice.setText(String.format("￥%.2f", homeEntity.getPrice())+name);
			//holdel.discount_type.setVisibility(View.GONE);
			holdel.discount_des.setVisibility(View.GONE);
			holdel.tv_pricename.setTextColor(context.getResources().getColor(R.color.weiwei_content_border_color));
			holdel.tv_pricename.setText("价格：");
			break;
		case 1:
			holdel.newPrice.setText(String.format("￥%.2f", homeEntity.getBusinessGoodsDiscountObject().getDiscountprice())+name);
			holdel.tv_pricename.setTextColor(context.getResources().getColor(R.color.red));
			//holdel.discount_type.setVisibility(View.VISIBLE);
			holdel.discount_des.setVisibility(View.VISIBLE);
			//holdel.discount_type.setText("不参与其他折扣");
			holdel.discount_des.setText(entity.getSpecialwords());
			holdel.tv_pricename.setText("特价：");
			holdel.tv_gramdiscount.setVisibility(View.VISIBLE);
			holdel.tv_gramdiscount.setText(homeEntity.getGramdiscount()+"折");
			break;
		case 2:
			holdel.newPrice.setText(String.format("￥%.2f", homeEntity.getPrice())+name);
			//holdel.discount_type.setVisibility(View.VISIBLE);
			holdel.discount_des.setVisibility(View.VISIBLE);
			holdel.tv_pricename.setTextColor(context.getResources().getColor(R.color.weiwei_content_border_color));
			//holdel.discount_type.setText("余额支付"+homeEntity.getBusinessGoodsDiscountObject().getDiscount()+"折");
			if ((homeEntity.getBusinessGoodsDiscountObject().getDiscount()-(int)(homeEntity.getBusinessGoodsDiscountObject().getDiscount()))>0) {
				//holdel.discount_type.setText(Html.fromHtml("余额支付<font color='red'>"+homeEntity.getBusinessGoodsDiscountObject().getDiscount()+"</font>折"));
				holdel.discount_des.setText(entity.getDiscountprefix()+homeEntity.getBusinessGoodsDiscountObject().getDiscount()+entity.getDiscountsuffix());
			}else {
				//holdel.discount_type.setText(Html.fromHtml("余额支付<font color='red'>"+(int)homeEntity.getBusinessGoodsDiscountObject().getDiscount()+"</font>折"));
				holdel.discount_des.setText(entity.getDiscountprefix()+(int)homeEntity.getBusinessGoodsDiscountObject().getDiscount()+entity.getDiscountsuffix());
			}
			
			holdel.tv_pricename.setText("价格：");
			break;

		default:
			break;
		}
		if (homeEntity.getCount()>0) {
			holdel.ll_total.setVisibility(View.VISIBLE);
			//holdel.view.setVisibility(View.GONE);
			tv_jian.setVisibility(View.VISIBLE);
			tv_total_weigth.setVisibility(View.VISIBLE);
			
		}else {
			holdel.ll_total.setVisibility(View.GONE);
			//holdel.view.setVisibility(View.VISIBLE);
			tv_jian.setVisibility(View.INVISIBLE);
			tv_total_weigth.setVisibility(View.INVISIBLE);
		}
		holdel.img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (newHomeLists.get(position).getStatus()==1) {
					homeClick.ItemClick(newHomeLists.get(position).getGid());
				}else {
					Toast.makeText(context, "此商品已售罄", Toast.LENGTH_SHORT).show();;
				}
				
			}
		});
		tv_totalWeight.setText("总计:"+(int)(Integer.parseInt(tv_num.getText().toString().trim())*newHomeLists.get(position).getIncrementweight()+weightBalance)+unit.getName());
		if (homeEntity.getBusinessGoodsDiscountObject().getDiscountType()==1) {
			tv_totalPrice.setText("总价:"+String.format("￥%.2f",(int)(Integer.parseInt(tv_num.getText().toString().trim())*newHomeLists.get(position).getIncrementweight()+weightBalance)*newHomeLists.get(position).getGramdiscountprice()));
		}else {
			tv_totalPrice.setText("总价:"+String.format("￥%.2f", (int)(Integer.parseInt(tv_num.getText().toString().trim())*newHomeLists.get(position).getIncrementweight()+weightBalance)*newHomeLists.get(position).getGramprice()));
		}
		tv_jia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//int stocknum = newHomeLists.get(position).getStockNum();
				if (Integer.parseInt(tv_total_weigth.getText().toString().trim())+newHomeLists.get(position).getIncrementweight()>newHomeLists.get(position).getStockNum()) {
					Toast.makeText(context, "库存不足", Toast.LENGTH_SHORT).show();
					return;
				}
				if (Integer.parseInt(tv_total_weigth.getText().toString().trim())+newHomeLists.get(position).getIncrementweight()>homeEntity.getLimitamount()&&homeEntity.getLimitamount()>0) {
					Toast.makeText(context, "超过商品单次最大购买数量", Toast.LENGTH_SHORT).show();
					return;
				}
				tv_num.setText(Integer.parseInt(tv_num.getText().toString().trim())+1+"");
				if (newHomeLists.get(position).getBusinessGoodsDiscountObject().getDiscountType()==1) {
					tv_totalPrice.setText("总价:"+String.format("￥%.2f",(int)(Integer.parseInt(tv_num.getText().toString().trim())*newHomeLists.get(position).getIncrementweight()+weightBalance)*newHomeLists.get(position).getGramdiscountprice()));
				}else {
					tv_totalPrice.setText("总价:"+String.format("￥%.2f", (int)(Integer.parseInt(tv_num.getText().toString().trim())*newHomeLists.get(position).getIncrementweight()+weightBalance)*newHomeLists.get(position).getGramprice()));
				}
				
				tv_totalWeight.setText("总计:"+(int)((Integer.parseInt(tv_num.getText().toString().trim())*newHomeLists.get(position).getIncrementweight())+(newHomeLists.get(position).getMaincourseminweight()-newHomeLists.get(position).getIncrementweight()))+unit.getName());
				homeClick.jia(homeEntity.getBusinessgoodsid(), Integer.parseInt(tv_num.getText().toString().trim()),newHomeLists.get(position));
				notifyDataSetChanged();
			}
		});
		tv_jian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(tv_num.getText().toString().trim())==0) {
					return;
				}else if (Integer.parseInt(tv_num.getText().toString().trim())==1) {
					
					tv_num.setText(0+"");
					tv_totalPrice.setText("总价:"+String.format("￥%.2f", 0.0));
					tv_totalWeight.setText("总计:"+0+unit.getName());
					homeClick.delete(homeEntity.getBusinessgoodsid());
					notifyDataSetChanged();
				}else {
					tv_num.setText(Integer.parseInt(tv_num.getText().toString().trim())-1+"");
					if (newHomeLists.get(position).getBusinessGoodsDiscountObject().getDiscountType()==1) {
						tv_totalPrice.setText("总价:"+String.format("￥%.2f",(int)(Integer.parseInt(tv_num.getText().toString().trim())*newHomeLists.get(position).getIncrementweight()+weightBalance)*newHomeLists.get(position).getGramdiscountprice()));
					}else {
						tv_totalPrice.setText("总价:"+String.format("￥%.2f", (int)(Integer.parseInt(tv_num.getText().toString().trim())*newHomeLists.get(position).getIncrementweight()+weightBalance)*newHomeLists.get(position).getGramprice()));
					}
					
					//tv_totalWeight.setText("总计:"+(int)(Integer.parseInt(tv_num.getText().toString().trim())*newHomeLists.get(position).getWeight())+"g");
					homeClick.jian(homeEntity.getBusinessgoodsid(),Integer.parseInt(tv_num.getText().toString().trim()));
					notifyDataSetChanged();
				}
				
			}
		});
		holdel.tv_name.setText(homeEntity.getName());
		holdel.tv_weigth.setText("净重:"+(int)homeEntity.getWeight()+"g");
		/*ArrayList<com.dongwukj.weiwei.idea.request.NewHomeattribute> attributeValueList = homeEntity.getAttributeValueList();
		if (attributeValueList.size()==1) {
			holdel.attr1.setVisibility(View.VISIBLE);
			holdel.attr2.setVisibility(View.INVISIBLE);
			holdel.attr3.setVisibility(View.INVISIBLE);
			holdel.attr1.setText(attributeValueList.get(0).getAttrvaluename());
		}else if (attributeValueList.size()==2) {
			holdel.attr1.setVisibility(View.VISIBLE);
			holdel.attr2.setVisibility(View.VISIBLE);
			holdel.attr3.setVisibility(View.INVISIBLE);
			holdel.attr1.setText(attributeValueList.get(0).getAttrvaluename());
			holdel.attr2.setText(attributeValueList.get(1).getAttrvaluename());
		}else if (attributeValueList.size()>=3) {
			holdel.attr1.setVisibility(View.VISIBLE);
			holdel.attr2.setVisibility(View.VISIBLE);
			holdel.attr3.setVisibility(View.VISIBLE);
			holdel.attr1.setText(attributeValueList.get(0).getAttrvaluename());
			holdel.attr2.setText(attributeValueList.get(1).getAttrvaluename());
			holdel.attr3.setText(attributeValueList.get(2).getAttrvaluename());
		}*/
		return convertView;
	}
	class ViewHolder{
		public TextView tv_name;
		public TextView tv_weigth;
		public TextView attr1;
		public TextView attr2;
		public TextView attr3;
		public TextView newPrice;
		public TextView tv_jia;
		public TextView tv_num;
		public TextView weight_unit;
		public TextView tv_jian;
		public TextView discount_des;
		public TextView tv_totalWeight;
		public TextView tv_totalPrice;
		public TextView tv_pricename;
		public TextView tv_gramdiscount;
		public ImageView shouwan;
		public TextView tv_total_weigth;//变化的
		//public TextView discount_type;//变化的
		public ImageView img;
		public LinearLayout ll_total;
		//public View view;
		public MyGridViewNoClick gd;
	}
	public interface HomeClick{
		public void jia(int goodId,int num, NewHomeEntity homeEntity);
		public void delete(Integer id);
		public void jian(int goodId,int num);
		public void ItemClick(int gid);
	}
	class Gdadapter extends BaseAdapter{
		private ArrayList<Speciality> iconList;
		
		public Gdadapter(ArrayList<Speciality> iconList) {
			super();
			this.iconList = iconList;
		}

		@Override
		public int getCount() {
			
			return iconList.size();
		}

		@Override
		public Object getItem(int position) {
			
			return null;
		}

		@Override
		public long getItemId(int position) {
			
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			/* RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					 RelativeLayout.LayoutParams.WRAP_CONTENT,
					 RelativeLayout.LayoutParams.WRAP_CONTENT);*/
			View view = View.inflate(context, R.layout.classify_attr, null);
			
			ImageView img=(ImageView) view.findViewById(R.id.img);
			//img.setLayoutParams(lp);
			fb.display(img, iconList.get(position).getIcon());
			return view;
		}
		
	}
}
