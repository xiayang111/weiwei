package com.dongwukj.weiwei.adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.daimajia.swipe.util.Attributes;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.CartEntity;
import com.dongwukj.weiwei.ui.widget.SubListView;

import java.util.*;

/**
 * Created by pc on 2015/3/13.
 */
public class NewGroupCartListAdapter extends BaseAdapter {

    private List<CartEntity> list;
    private Context context;
    private SubListView.SubListViewSwipeListener subListViewSwipeListener;
    public NewGroupCartListAdapter(List<CartEntity> list, Context context,SubListView.SubListViewSwipeListener subListViewSwipeListener) {
        this.list = list;
        this.context = context;
        this.subListViewSwipeListener=subListViewSwipeListener;
        subListViewAdapters=new HashMap<Integer, SubListViewAdapter>();
    }
    

    private Map<Integer,SubListViewAdapter> subListViewAdapters;
   

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
        SubListView subListView=null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.sub_listview,null);
            subListView=(SubListView)convertView.findViewById(R.id.listview);

            convertView.setTag(subListView);
        }else{
            subListView=(SubListView)convertView.getTag();

        }

        if(subListViewAdapters.containsKey(position)){
            subListView.setAdapter(subListViewAdapters.get(position));
        }else{
            SubListViewAdapter subListViewAdapter=new SubListViewAdapter(context,list.get(position).getCartItems(),position,subListViewSwipeListener);

            subListViewAdapters.put(position,subListViewAdapter);
            subListView.setAdapter(subListViewAdapters.get(position));
        }

        return convertView;
    }

    public class ViewHolder{
        public SubListView subListView;
        public SubListViewAdapter subListViewAdapter;
    }
}
