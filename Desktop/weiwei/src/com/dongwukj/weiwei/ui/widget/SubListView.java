package com.dongwukj.weiwei.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import com.daimajia.swipe.SwipeLayout;

/**
 * Created by sunjaly on 15/3/20.
 */
public class SubListView extends ListView {

    public SubListView(Context context) {
        super(context);
    }

    public SubListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


    public interface SubListViewSwipeListener{
        void onStartOpen(CustomeSwipeLayout swipeLayout);
        void onOpen(CustomeSwipeLayout swipeLayout);
        void generateView(CustomeSwipeLayout swipeLayout);
    }
}
