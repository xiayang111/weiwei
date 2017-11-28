package com.dongwukj.weiwei.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.ParentCartListAdapter;

/**
 * Created by sunjaly on 15/3/27.
 */
public class ParentCartListView extends ListView {

    private int currentMode;

    public static final int MODE_EDIT=1;
    public static final int MODE_NORMAL=0;

    private LayoutMode layoutMode;

    public int getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(int currentMode) {
        this.currentMode = currentMode;
        updateHandler.sendEmptyMessage(100);
    }

    public ParentCartListView(Context context) {
        super(context);
    }

    public ParentCartListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ParentCartListView);
        int ordinal = a.getInt(R.styleable.ParentCartListView_layoutMode, LayoutMode.Normal.ordinal());
        layoutMode=LayoutMode.values()[ordinal];
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(layoutMode==LayoutMode.Stretch){
            int expandSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }else {
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        }

    }

    private Handler updateHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ParentCartListAdapter parentCartListAdapter=(ParentCartListAdapter)getAdapter();
            if(parentCartListAdapter!=null){
                parentCartListAdapter.notifyDataSetChanged(currentMode,null);
            }
        }
    };


    public static enum LayoutMode {
        Normal,
        Stretch
    }

}
