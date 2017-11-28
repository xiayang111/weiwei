package com.dongwukj.weiwei.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.CartEntity;

import java.util.List;

/**
 * Created by pc on 2015/3/13.
 */
public class SubCartListAdapter extends BaseAdapter {

    private List<CartEntity.CartItem> list;
    private Context context;

    public SubCartListAdapter(List<CartEntity.CartItem> list, Context context) {
        this.list = list;
        this.context = context;
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
//        TextView textView=null;
//        if(convertView==null){
            convertView=View.inflate(context, R.layout.cart_sub_list_item,null);
//            textView=(TextView)convertView.findViewById(R.id.car_sub_list_item_title);
//            convertView.setTag(textView);
//        }else{
//            textView=(TextView)convertView.getTag();
//        }
//        textView.setText(list.get(position).getTitle());
        return convertView;
    }
    	
    public void remove(int position){
    	list.remove(position);
    	notifyDataSetChanged();
    }
    
    
}
