package com.dongwukj.weiwei.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.daimajia.swipe.SwipeLayout;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by sunjaly on 15/3/20.
 */
public class GroupCartListView extends ListView   implements SubListView.SubListViewSwipeListener{

    private Set<CustomeSwipeLayout> customeSwipeLayoutSet;



    public GroupCartListView(Context context) {
        this(context, null);
    }

    public GroupCartListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        customeSwipeLayoutSet=new HashSet<CustomeSwipeLayout>();
    }


    @Override
    public void onStartOpen(CustomeSwipeLayout swipeLayout) {
        closeAllExcept(swipeLayout);
    }

    @Override
    public void onOpen(CustomeSwipeLayout swipeLayout) {
        closeAllExcept(swipeLayout);
    }

    @Override
    public void generateView(CustomeSwipeLayout swipeLayout) {
        customeSwipeLayoutSet.add(swipeLayout);
    }

    public void closeAllExcept(SwipeLayout layout) {
        for (SwipeLayout s : customeSwipeLayoutSet) {
            if (s != layout)
                s.close();
        }
    }
}
