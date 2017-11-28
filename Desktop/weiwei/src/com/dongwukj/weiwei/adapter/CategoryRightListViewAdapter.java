package com.dongwukj.weiwei.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.CategoryEntity;
import com.dongwukj.weiwei.ui.widget.FullSizeGridView;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/4/22.
 */
public class CategoryRightListViewAdapter extends BaseAdapter {

    private ArrayList<CategoryEntity> categoryEntityArrayList;
    private Context context;

    private OnCategoryItemClickListener onCategoryItemClickListener;

    public void setOnCategoryItemClickListener(OnCategoryItemClickListener onCategoryItemClickListener) {
        this.onCategoryItemClickListener = onCategoryItemClickListener;
    }

    public CategoryRightListViewAdapter(Context context,ArrayList<CategoryEntity> categoryEntityArrayList) {
        this.categoryEntityArrayList = categoryEntityArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categoryEntityArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryEntityArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.classify_left_lv_item,null);
            viewHolder=new ViewHolder();
            viewHolder.gridView=(FullSizeGridView)convertView.findViewById(R.id.classify_left_lv_item_grid);
            viewHolder.textView=(TextView)convertView.findViewById(R.id.classify_left_lv_item_text);
            viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    if(onCategoryItemClickListener!=null){
                        onCategoryItemClickListener.onItemClick(position,pos);
                    }
                }
            });
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText(categoryEntityArrayList.get(position).getName());
        CategoryGridViewAdapter categoryGridViewAdapter=new CategoryGridViewAdapter(context,categoryEntityArrayList.get(position).getChildCategorys());
        viewHolder.gridView.setAdapter(categoryGridViewAdapter);

        return convertView;
    }

    private class ViewHolder{
        public FullSizeGridView gridView;
        public TextView textView;
    }


    public interface OnCategoryItemClickListener{
        public void onItemClick(int parentId,int childId);
    }

}
