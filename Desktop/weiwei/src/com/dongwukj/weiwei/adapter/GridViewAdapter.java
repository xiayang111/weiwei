package com.dongwukj.weiwei.adapter;

import java.util.List;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import net.tsz.afinal.FinalBitmap;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.ClassifyDetails;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
	private List<ClassifyDetails> list;
	private Context context;
	private FinalBitmap fb;
	public GridViewAdapter(List<ClassifyDetails> list,Context context) {
		super();
		this.list = list;
		this.context = context;
		fb=FinalBitmap.create(context);
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 
		Viewhodler hodler=null;
		if (convertView!=null) {
			hodler=(Viewhodler) convertView.getTag();
		}else {
			convertView= View.inflate(context, R.layout.classify_gv_item, null);
			hodler=new Viewhodler();
			hodler.photo=(ImageView) convertView.findViewById(R.id.img);
			hodler.product_nama=(TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(hodler);
		}
		ClassifyDetails info=(ClassifyDetails) list.get(position);
        String imgUrl=info.getPhotoUrl();
        if(!NetworkUtil.checkUrl(imgUrl)){
            imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
        }
		fb.display(hodler.photo, imgUrl);
		hodler.product_nama.setText(info.getProduct_name());
		return convertView;
	}
	class Viewhodler{
		ImageView photo;
		TextView product_nama;
	}
}
