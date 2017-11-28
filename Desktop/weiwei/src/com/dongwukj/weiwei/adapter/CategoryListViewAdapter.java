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
public class CategoryListViewAdapter extends BaseAdapter {

    private Context context;
    private List<CategoryEntity> list;
    private FinalBitmap finalBitmap;

    private int selectedId;

    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    public CategoryListViewAdapter(Context context, List<CategoryEntity> list) {
        this.context = context;
        this.list = list;
        finalBitmap=FinalBitmap.create(context);
        finalBitmap.configLoadfailImage(R.drawable.default_small);
        finalBitmap.configLoadingImage(R.drawable.default_small);
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
            convertView=View.inflate(context, R.layout.classify_lv_item,null);
            viewHolder=new ViewHolder();
            viewHolder.img=(ImageView)convertView.findViewById(R.id.classify_lv_item_img);
            viewHolder.title=(TextView)convertView.findViewById(R.id.classify_lv_item_title);
            viewHolder.checkedImage=(ImageView)convertView.findViewById(R.id.classify_lv_item_check_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getName());
        String imgUrl=list.get(position).getIcon();
        if(!NetworkUtil.checkUrl(imgUrl)){
            imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
        }
        if(position==selectedId){
            viewHolder.title.setTextColor(context.getResources().getColor(R.color.left_category_select_text_color));
            viewHolder.checkedImage.setVisibility(View.VISIBLE);
        }else{
            viewHolder.title.setTextColor(context.getResources().getColor(R.color.left_category_text_color));
            viewHolder.checkedImage.setVisibility(View.GONE);
        }

        finalBitmap.display(viewHolder.img,imgUrl);
        return convertView;
    }

    private class ViewHolder{
        public TextView title;
        public ImageView img;
        public ImageView checkedImage;
    }
}
