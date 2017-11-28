package com.dongwukj.weiwei.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by sunjaly on 15/3/27.
 */
public class ChildCartListView extends ListView{

    public ChildCartListView(Context context) {
        super(context);
    }

    public ChildCartListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    public interface ChildCartListChangeListener{
        void itemCheck(boolean checked, int groupId,int position,int mode);
        void upOrDownAmount(int groupId,int position,boolean isPlus,String amount);
        void itemClick(int groupId,int position);
    }
}
