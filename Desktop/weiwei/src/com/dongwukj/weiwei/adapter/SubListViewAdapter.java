package com.dongwukj.weiwei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.CartEntity;
import com.dongwukj.weiwei.ui.widget.CustomeSwipeLayout;
import com.dongwukj.weiwei.ui.widget.SubListView;

import java.util.List;

/**
 * Created by sunjaly on 15/3/20.
 */
public class SubListViewAdapter extends BaseSwipeAdapter {


    private Context mContext;
    private List<CartEntity.CartItem> list;
    private int groupId;

    private SubListView.SubListViewSwipeListener subListViewSwipeListener;


    public SubListViewAdapter(Context mContext, List<CartEntity.CartItem> list, int groupId,SubListView.SubListViewSwipeListener subListViewSwipeListener ) {
        this.mContext = mContext;
        this.list = list;
        this.groupId = groupId;
        this.subListViewSwipeListener=subListViewSwipeListener;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.sub_listview_item, null);
        CustomeSwipeLayout swipeLayout = (CustomeSwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        subListViewSwipeListener.generateView(swipeLayout);
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
               // YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
                subListViewSwipeListener.onOpen((CustomeSwipeLayout)layout);
            }

            @Override
            public void onClose(SwipeLayout layout) {
                super.onClose(layout);

            }

            @Override
            public void onStartOpen(SwipeLayout layout) {
                 subListViewSwipeListener.onStartOpen((CustomeSwipeLayout)layout);
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void fillValues(final int position, View convertView) {
        TextView t = (TextView)convertView.findViewById(R.id.position);
        t.setText((position + 1) + ".");
        TextView t1=(TextView)convertView.findViewById(R.id.text_data);
        t1.setText(list.get(position).getTitle());

        Button button=(Button)convertView.findViewById(R.id.delete);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> openedList = getOpenItems();
                if (openedList.size() > 0) {
                    Toast.makeText(mContext, "selected:" + openedList.get(0)+"----posi"+position, Toast.LENGTH_SHORT).show();
                }
                list.remove(position);
                notifyDataSetChanged();
                closeAllItems();

            }
        });

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
}
