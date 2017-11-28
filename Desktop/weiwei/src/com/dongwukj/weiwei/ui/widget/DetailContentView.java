//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dongwukj.weiwei.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AbsListView.RecyclerListener;
//import com.achievo.vipshop.view.detail.CouponGouPanel;
//import com.achievo.vipshop.view.interfaces.IDetailItemHolder;
import java.util.ArrayList;
import java.util.Iterator;

public class DetailContentView extends ListView {
    Adapter adapter;
    boolean isEnd = false;
    boolean isStart = false;
    private DetailContentView.OnScrollToEndListener onScrollToEndListener;

    public DetailContentView(Context var1) {
        super(var1);
        this.init();
    }

    public DetailContentView(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.init();
    }

    public DetailContentView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.init();
    }

    private void init() {
        this.setRecyclerListener(new RecyclerListener() {
            public void onMovedToScrapHeap(final View var1) {
                DetailContentView.this.postDelayed(new Runnable() {
                    public void run() {
                        Object var1x = var1.getTag();
//                        if(DetailContentView.this.indexOfChild(var1) < 0 && var1x != null && var1x instanceof IDetailItemHolder) {
//                            ((IDetailItemHolder)var1x).onDetached();
//                        }

                    }
                }, 30L);
            }
        });
    }

    public void close() {
        ArrayList var1 = new ArrayList();
        this.reclaimViews(var1);
        Iterator var2 = var1.iterator();

//        while(var2.hasNext()) {
//            Object var3 = ((View)var2.next()).getTag();
//            if(var3 != null && var3 instanceof IDetailItemHolder) {
//                ((IDetailItemHolder)var3).close();
//            }
//        }

    }

    public void judgeIsEnd(boolean var1) {
        if(this.onScrollToEndListener != null && var1 ^ this.isEnd) {
            boolean var2;
            if(this.isEnd) {
                var2 = false;
            } else {
                var2 = true;
            }

            this.isEnd = var2;
            this.onScrollToEndListener.onScrollToEnd(this.isEnd);
        }

    }

    public void onActivityPause() {
        int var1 = this.getChildCount();

//        for(int var2 = 0; var2 < var1; ++var2) {
//            Object var3 = this.getChildAt(var2).getTag();
//            if(var3 != null && var3 instanceof IDetailItemHolder) {
//                ((IDetailItemHolder)var3).onActivityPause();
//            }
//        }

    }

    public void onActivityRestart() {
        int var1 = this.getChildCount();

        for(int var2 = 0; var2 < var1; ++var2) {
            Object var3 = this.getChildAt(var2).getTag();
//            if(var3 != null && var3 instanceof CouponGouPanel) {
//                ((IDetailItemHolder)var3).onActivityRestart();
//            }
        }

    }

    public void onActivityResume() {
        int var1 = this.getChildCount();

//        for(int var2 = 0; var2 < var1; ++var2) {
//            Object var3 = this.getChildAt(var2).getTag();
//            if(var3 != null && var3 instanceof IDetailItemHolder) {
//                ((IDetailItemHolder)var3).onActivityResume();
//            }
//        }

    }

    public void onActivityStop() {
        int var1 = this.getChildCount();

//        for(int var2 = 0; var2 < var1; ++var2) {
//            Object var3 = this.getChildAt(var2).getTag();
//            if(var3 != null && var3 instanceof IDetailItemHolder) {
//                ((IDetailItemHolder)var3).onActivityStop();
//            }
//        }

    }

    protected void onScrollChanged(int var1, int var2, int var3, int var4) {
        super.onScrollChanged(var1, var2, var3, var4);
        if(this.onScrollToEndListener != null) {
            int var5 = -1;
            int var6 = this.getLastVisiblePosition();
            if(this.adapter != null && var6 == this.adapter.getCount()) {
                var5 = this.getChildAt(-1 + this.getChildCount()).getBottom();
            }

            if(var5 >= 0) {
                int var7 = -10 + (var5 - this.getHeight());
                boolean var8 = false;
                if(var2 >= var7) {
                    var8 = true;
                }

                this.judgeIsEnd(var8);
                return;
            }

            this.judgeIsEnd(false);
        }

    }

    public void setAdapter(ListAdapter var1) {
        super.setAdapter(var1);
        this.adapter = var1;
        if(var1 instanceof HeaderViewListAdapter) {
            this.adapter = ((HeaderViewListAdapter)var1).getWrappedAdapter();
        }

    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    public void setOnScrollToEndistener(DetailContentView.OnScrollToEndListener var1) {
        this.onScrollToEndListener = var1;
    }

    public interface OnScrollToEndListener {
        void onScrollToEnd(boolean var1);
    }
}
