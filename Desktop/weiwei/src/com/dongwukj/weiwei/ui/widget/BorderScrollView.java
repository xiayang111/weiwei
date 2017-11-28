package com.dongwukj.weiwei.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class BorderScrollView extends ScrollView {

    private OnBorderListener onBorderListener;
    private View contentView;
    boolean isStart = true;
    boolean isEnd = false;

    public BorderScrollView(Context context) {
        super(context);
    }

    public BorderScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BorderScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (this.onBorderListener != null) {
            if (contentView != null && contentView.getMeasuredHeight() <= getScrollY() + getHeight()) {
                if (onBorderListener != null) {
                    onBorderListener.onBottom(true);
                }
            }else{
                if (onBorderListener != null) {
                    onBorderListener.onBottom(false);
                }
            }
//            else if(contentView != null && contentView.getMeasuredHeight()<=getScrollY() + getHeight() && contentView.getMeasuredHeight()>=getScrollY() + getHeight() + 10){
//                if (onBorderListener != null) {
//                    onBorderListener.onBottom(false);
//                }
//            }
            boolean var5 = this.isStart;
            boolean var6;
            if (y <= 30) {
                var6 = true;
            } else {
                var6 = false;
            }

            if (var6 ^ var5) {
                boolean var7 = this.isStart;
                boolean var8 = false;
                if (!var7) {
                    var8 = true;
                }

                this.isStart = var8;
                this.onBorderListener.onTop(this.isStart);
            }
        }
        //doOnBorderListener();
    }

    public void setOnBorderListener(final OnBorderListener onBorderListener) {
        this.onBorderListener = onBorderListener;
        if (onBorderListener == null) {
            return;
        }

        if (contentView == null) {
            contentView = getChildAt(0);
        }
    }

    /**
     * OnBorderListener, Called when scroll to top or bottom
     *
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-22
     */
    public static interface OnBorderListener {

        /**
         * Called when scroll to bottom
         */
        public void onBottom(boolean isBottom);

        /**
         * Called when scroll to top
         */
        public void onTop(boolean isStart);
    }

//    private void doOnBorderListener() {
//        if (contentView != null && contentView.getMeasuredHeight() <= getScrollY() + getHeight()) {
//            if (onBorderListener != null) {
//                onBorderListener.onBottom();
//            }
//        } else if (getScrollY() == 0) {
//            if (onBorderListener != null) {
//                onBorderListener.onTop();
//            }
//        }
//    }
}