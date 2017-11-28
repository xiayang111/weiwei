package com.dongwukj.weiwei.adapter;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.CartProductEntity;
import com.dongwukj.weiwei.idea.result.CartSuitEntity;
import com.dongwukj.weiwei.idea.result.OrderProductInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

public class OrderGridViewAdapter extends BaseAdapter{
	private Context context;
	ArrayList<String> imageList;
	private FinalBitmap fb;

	

	public OrderGridViewAdapter(Context context, ArrayList<String> imageList) {
		this.context=context;
		this.imageList=imageList;
		fb = FinalBitmap.create(context);
		fb.configLoadfailImage(R.drawable.default_small);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView view=new ImageView(context);
		int width=0;
		int height=0;
		if (imageList.size()>4) {
			width=parent.getWidth()/3;
			height=(int)(context.getResources().getDimension(R.dimen.order_list_item_gridview_height))/(imageList.size()/3+1);
		}else {
			width=(parent.getWidth()-(int)(context.getResources().getDimension(R.dimen.order_list_item_gridview_horizontalSpacing)*3))/4;
			height=(int)(context.getResources().getDimension(R.dimen.order_list_item_gridview_height));
		}
		android.widget.AbsListView.LayoutParams params = new AbsListView.LayoutParams(width,height);
		view.setScaleType(ScaleType.FIT_XY);
		view.setLayoutParams(params);
		fb.display(view, imageList.get(position));
		return view;
	}
	
}
