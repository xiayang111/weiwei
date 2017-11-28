package com.dongwukj.weiwei.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sunjaly on 15/3/30.
 */
public class SeparatedListAdapter extends BaseAdapter {

    public final Map<String,Adapter> sections=new LinkedHashMap<String, Adapter>();
    public final ArrayAdapter<String> headers;
    public final static int TYPE_SECTION_HEADER=0;

    public SeparatedListAdapter(Context context){
        headers=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1);

    }

    public void addSection(String section,Adapter adapter){
        this.headers.add(section);
        sections.put(section,adapter);
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        for(Object section:this.sections.keySet()){

        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
