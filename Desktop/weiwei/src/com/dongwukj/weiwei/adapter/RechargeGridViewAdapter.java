package com.dongwukj.weiwei.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.R.color;
import com.dongwukj.weiwei.idea.result.PrepaidRuleEntity;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by pc on 2015/3/16.
 */
public class RechargeGridViewAdapter extends BaseAdapter {

    private ArrayList<PrepaidRuleEntity> list;
    private Context context;
    private Vector<Boolean> rechargeCheckList =null;
    private int lastPosition = -1;

    public RechargeGridViewAdapter(ArrayList<PrepaidRuleEntity> list, Context context) {
        this.list = list;
        this.context = context;
        /*rechargeCheckList= new Vector<Boolean>();
        for(int i=0;i<rechargeList.length;i++){
            rechargeCheckList.add(false);
        }*/
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
            convertView=View.inflate(context, R.layout.recharge_gridview_item,null);
            viewHolder=new ViewHolder();
            viewHolder.tv=(TextView)convertView.findViewById(R.id.recharge_gridview_item_text);

            viewHolder.rl_layout=(RelativeLayout)convertView.findViewById(R.id.rl_layout);

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        if(list.get(position).isIschecked()){
            //context.getResources().getDrawable(R.drawable.weiwei_border_content);
            viewHolder.rl_layout.setBackgroundResource(R.drawable.weiwei_border_content_checked);
        }else{
            viewHolder.rl_layout.setBackgroundResource(R.drawable.weiwei_border_content);

            //textView.setBackground(context.getResources().getDrawable(R.drawable.weiwei_border_content));
        }

        viewHolder.tv.setText((int)list.get(position).getMinMoney()+"元");
        return convertView;
    }

    public void changeState(int position){
      /*  if(lastPosition != -1)
            rechargeCheckList.setElementAt(false, lastPosition);
        rechargeCheckList.setElementAt(true,position);
        lastPosition = position;*/
        notifyDataSetChanged();
    }


    private class ViewHolder{
        public RelativeLayout rl_layout;
        public TextView tv;
    }
}
