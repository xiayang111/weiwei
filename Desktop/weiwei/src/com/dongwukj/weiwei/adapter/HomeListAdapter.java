package com.dongwukj.weiwei.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.test.MoreAsserts;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.FullGiftEntity;
import com.dongwukj.weiwei.idea.result.HomeProduceEntity;
import com.dongwukj.weiwei.net.utils.NetworkUtil;

import net.tsz.afinal.FinalBitmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by pc on 2015/3/12.
 */
public class HomeListAdapter extends BaseAdapter  {

    private Context context;

    private FinalBitmap fb;
    private TreeMap<Integer, List<FullGiftEntity>> maps;

	private Object[] arrays;
	private itemClick itemClickinter;
	
    public HomeListAdapter(TreeMap<Integer, List<FullGiftEntity>> maps, Context context) {
        this.maps = maps;
        this.context = context;
        fb = FinalBitmap.create(this.context);//初始化FinalBitmap模块
//        fb.configLoadingImage(R.drawable.);
//        fb.configLoadfailImage(R.drawable.banner);
        arrays = maps.keySet().toArray();
    }

    @Override
    public void notifyDataSetChanged() {
    	arrays = maps.keySet().toArray();
    };
    
    @Override
    public int getCount() {
        return arrays.length;
    }

    @Override
    public Object getItem(int position) {
        return maps.get(arrays[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.home_list_view_item1,null);
            viewHolder=new ViewHolder();
            viewHolder.iconImageView1=(ImageView)convertView.findViewById(R.id.home_list_item_imageview1);
            viewHolder.iconImageView2=(ImageView)convertView.findViewById(R.id.home_list_item_imageview2);
            viewHolder.iconImageView3=(ImageView)convertView.findViewById(R.id.home_list_item_imageview3);
            viewHolder.ll=(LinearLayout)convertView.findViewById(R.id.ll);
            viewHolder.tv_title=(TextView)convertView.findViewById(R.id.tv_title);
            viewHolder.tv_more=(TextView)convertView.findViewById(R.id.tv_more);
            //viewHolder.view1=(TextView)convertView.findViewById(R.id.view1);
            //viewHolder.view2=(TextView)convertView.findViewById(R.id.view2);
//            viewHolder.titleTextView=(TextView)convertView.findViewById(R.id.home_list_item_title);
//            viewHolder.descriptionTextView=(TextView)convertView.findViewById(R.id.home_list_item_description);
//            viewHolder.currentPriceTextView=(TextView)convertView.findViewById(R.id.home_list_item_current_price);
//            viewHolder.prePriceTextView=(TextView)convertView.findViewById(R.id.home_list_item_pre_price);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        final List<FullGiftEntity> list = maps.get(arrays[position]);
        if (list.size()==1) {
			viewHolder.ll.setVisibility(View.GONE);
			//viewHolder.view1.setVisibility(View.GONE);
			fb.display(viewHolder.iconImageView1,  checkUrl(list.get(0).getBody()));
			viewHolder.iconImageView1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					itemClickinter.iconImageViewClick(list.get(0).getUrl());
				}
			});
		}else if (list.size()==2) {
			viewHolder.ll.setVisibility(View.VISIBLE);
			//viewHolder.view2.setVisibility(View.GONE);
			viewHolder.iconImageView3.setVisibility(View.GONE);
			fb.display(viewHolder.iconImageView1,  checkUrl(list.get(0).getBody()));
			viewHolder.iconImageView1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					itemClickinter.iconImageViewClick(list.get(0).getUrl());
				}
			});
			viewHolder.iconImageView2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					itemClickinter.iconImageViewClick(list.get(1).getUrl());
				}
			});
			fb.display(viewHolder.iconImageView2,  checkUrl(list.get(1).getBody()));
		}else {
			viewHolder.ll.setVisibility(View.VISIBLE);
			//viewHolder.view1.setVisibility(View.VISIBLE);
			//viewHolder.view2.setVisibility(View.VISIBLE);
			viewHolder.iconImageView3.setVisibility(View.VISIBLE);
			fb.display(viewHolder.iconImageView1, checkUrl(list.get(0).getBody()),BitmapFactory.decodeResource(context.getResources(), R.drawable.weiwei_home_1));
			fb.display(viewHolder.iconImageView2,  checkUrl(list.get(1).getBody()),BitmapFactory.decodeResource(context.getResources(), R.drawable.weiwei_home_2));
			fb.display(viewHolder.iconImageView3,  checkUrl(list.get(2).getBody()),BitmapFactory.decodeResource(context.getResources(), R.drawable.weiwei_home_2));
			viewHolder.iconImageView1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					itemClickinter.iconImageViewClick(list.get(0).getUrl());
				}
			});
			viewHolder.iconImageView2.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					itemClickinter.iconImageViewClick(list.get(1).getUrl());
					}
				});
			viewHolder.iconImageView3.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					itemClickinter.iconImageViewClick(list.get(2).getUrl());
				}
			});
		}
        viewHolder.tv_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				itemClickinter.moreClick(Integer.parseInt(list.get(0).getExtField5().split("-")[2]),list.get(0).getExtField5().split("-")[1]);
			}
		});
        viewHolder.tv_title.setText(list.get(0).getExtField5().split("-")[1]);
//        FullGiftEntity entity=list.get(position);
//        String imgUrl=entity.getBody();
//        if(!NetworkUtil.checkUrl(imgUrl)){
//            imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
//        }
//        fb.display(viewHolder.iconImageView1,imgUrl);
//        viewHolder.titleTextView.setText(entity.getTitle());
//        viewHolder.descriptionTextView.setText(entity.getDescription());
//        viewHolder.currentPriceTextView.setText(String.format("%.1f",entity.getCurrentPrice()));
//        viewHolder.prePriceTextView.setText("￥"+String.format("%.1f",entity.getPrePrice() ));
//        viewHolder.prePriceTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        return convertView;
    }

    private String checkUrl(String url){
    	if(!NetworkUtil.checkUrl(url)){
    		url= BaseApplication.BASE_IMAGE_HOST+url;
    		return url;
      }else {
		return url;
	}
    }
    public void setitemClick(itemClick itemClickinter){
    	this.itemClickinter=itemClickinter;
    }
    private class ViewHolder{
        public ImageView iconImageView1;
        public ImageView iconImageView2;
        public ImageView iconImageView3;
        public LinearLayout ll;
        public TextView tv_title;
        public TextView tv_more;
       // public TextView view1;
       // public TextView view2;
        
//        public TextView titleTextView;
//        public TextView descriptionTextView;
//        public TextView currentPriceTextView;
//        public TextView prePriceTextView;

    }
    public interface itemClick{
    	public void iconImageViewClick(int id);
    	public void moreClick(int id,String title);
    }
	
}
