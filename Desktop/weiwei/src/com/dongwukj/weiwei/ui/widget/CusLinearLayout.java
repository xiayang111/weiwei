package com.dongwukj.weiwei.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.dongwukj.weiwei.R;

/**
 * Created by sunjaly on 15/4/2.
 */
public class CusLinearLayout extends LinearLayout {
    public CusLinearLayout(Context context) {
        super(context);
    }

    public CusLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        int top=getTop();
//        int paddingTop=getPaddingTop();
//        //-getResources().getDimensionPixelSize(R.dimen.home_list_dividerheight);
//        if(top!=paddingTop){
//            offsetTopAndBottom(paddingTop-top);
//        }
         int top=getResources().getDimensionPixelSize(R.dimen.home_list_dividerheight);
            if(getTop()==top) {
                offsetTopAndBottom(-top);
            }

        super.onLayout(changed, l, t, r, b);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
