package com.dongwukj.weiwei.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.CategoryEntity;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import net.tsz.afinal.FinalBitmap;

import java.util.List;

/**
 * Created by sunjaly on 15/3/24.
 */
public class CategoryGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<CategoryEntity> list;
    private FinalBitmap finalBitmap;

    public CategoryGridViewAdapter(Context context, List<CategoryEntity> list) {
        this.context = context;
        this.list = list;
        finalBitmap=FinalBitmap.create(context);
        finalBitmap.configLoadingImage(R.drawable.default_small);
        finalBitmap.configLoadfailImage(R.drawable.default_small);
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
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.classify_gv_item,null);
            viewHolder=new ViewHolder();
            viewHolder.textView=(TextView)convertView.findViewById(R.id.tv);
            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        String imgUrl=list.get(position).getIcon();
        if(!NetworkUtil.checkUrl(imgUrl)){
            imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
        }
        finalBitmap.display(viewHolder.imageView,imgUrl);
        viewHolder.textView.setText(list.get(position).getName());
        return convertView;
    }


    private class ViewHolder{
        public TextView textView;
        public ImageView imageView;
    }
}
