//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dongwukj.weiwei.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.HeaderViewListAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

public class ExpandableScrollView extends LinearLayout {

    static final double HORIZONTAL_RATIO = Math.tan(Math.toRadians(45.0D));
    boolean allowInterceptForHorizontal;
    boolean animating;
    Context context;
    View divider;
    int dividerHeight;
    MotionEvent down;
    boolean enabled;
    View expandedView;
    boolean innerDragging;
    boolean isDragging;
    boolean isHorizontalMove;
    float lastX;
    float lastY;
    int listItemCount;
    Scroller mScroller;
    private int mSlop;
    VelocityTracker mVelocityTracker;
    int maxUpheight;
    View normalView;
    boolean onDownHalf;
    ExpandableScrollView.OnPullListener onPullListener;
    boolean onUpHalf;
    boolean upDraggable = false;
    int visibleHeight;

    private DisplayMetrics displayMetrics;

    public ExpandableScrollView(Context context){
        this(context,null);
    }

    public ExpandableScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.maxUpheight = this.visibleHeight;
        this.enabled = true;
        this.isDragging = false;
        this.isHorizontalMove = false;
        this.allowInterceptForHorizontal = false;
        this.animating = false;
        this.listItemCount = 0;
        this.context = context;
        this.setOrientation(VERTICAL);
        this.mScroller = new Scroller(context);
        this.mSlop = 20;
        displayMetrics=new DisplayMetrics();
        WindowManager windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        this.visibleHeight = displayMetrics.heightPixels-100;
                //BaseApplication.screenHeight - Utils.dip2px(var1, 50.0F);
        this.dividerHeight = this.visibleHeight / 4;
        this.setEnabled(true);

    }
    private void acquireVelocityTracker(MotionEvent var1) {
        if(this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }

        this.mVelocityTracker.addMovement(var1);
    }

    private boolean checkIsHorizontalMove(MotionEvent var1, MotionEvent var2) {
        return var1 != null && var2 != null && (double)Math.abs(var1.getX() - var2.getX()) / (double)Math.abs(var1.getY() - var2.getY()) > HORIZONTAL_RATIO;
    }

    private void releaseVelocityTracker() {
        if(this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }

    }

    private void setScrollingCacheEnabled(boolean var1) {
        int var2 = this.getChildCount();

        for(int var3 = 0; var3 < var2; ++var3) {
            View var4 = this.getChildAt(var3);
            if(var4.getVisibility() != GONE) {
                var4.setDrawingCacheEnabled(var1);
            }
        }

    }

    public void computeScroll() {
        super.computeScroll();
        if(this.mScroller.computeScrollOffset()) {
            this.scrollTo(0, this.mScroller.getCurrY());
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }

    protected void onFinishInflate() {
        super.onFinishInflate();
    }



    public boolean onInterceptTouchEvent(MotionEvent var1) {
        boolean var15;
        if(!this.enabled) {
            var15 = super.onInterceptTouchEvent(var1);
        } else {
            switch(255 & var1.getAction()) {
                case 0:
                    if(this.down != null) {
                        this.down.recycle();
                    }

                    this.down = MotionEvent.obtain(var1);
                    this.maxUpheight = this.visibleHeight;
                    boolean var14 = this.upDraggable;
                    var15 = false;
                    if(var14) {
                        if(this.normalView instanceof AbsListView) {
                            this.maxUpheight = -1;
                            Object var16 = ((AbsListView)this.normalView).getAdapter();
                            if(var16 instanceof HeaderViewListAdapter) {
                                var16 = ((HeaderViewListAdapter)var16).getWrappedAdapter();
                            }

                            this.listItemCount = ((Adapter)var16).getCount();
                            return false;
                        }

                        if(this.normalView instanceof ScrollView) {
                            this.maxUpheight = ((ScrollView)this.normalView).getChildAt(0).getHeight();
                            return false;
                        }

                        this.maxUpheight = this.normalView.getHeight();
                        return false;
                    }
                    break;
                case 2:
                    if(this.down == null) {
                        this.down = MotionEvent.obtain(var1);
                    }

                    int var2 = (int)(var1.getY() - this.down.getY());
                    if(this.enabled && this.expandedView.getScrollY() == 0) {
                        int var3 = this.maxUpheight;
                        boolean var4 = false;
                        if(var3 < 0) {
                            boolean var9 = this.normalView instanceof AbsListView;
                            var4 = false;
                            if(var9) {
                                AbsListView var10 = (AbsListView)this.normalView;
                                int var11 = var10.getLastVisiblePosition();
                                int var12 = this.listItemCount;
                                var4 = false;
                                if(var11 == var12) {
                                    this.maxUpheight = var10.getChildAt(-1 + var10.getChildCount()).getBottom();
                                    int var13 = this.maxUpheight;
                                    var4 = false;
                                    if(var13 < 0) {
                                        var4 = true;
                                    }
                                }
                            }
                        }

                        if(this.maxUpheight < 0) {
                            if(!var4) {
                                this.innerDragging = true;
                                this.requestDisallowInterceptTouchEvent(true);
                                return false;
                            }

                            this.maxUpheight = this.visibleHeight;
                        }

                        boolean var5;
                        if(this.getScrollY() >= this.visibleHeight) {
                            var5 = true;
                        } else {
                            var5 = false;
                        }

                        boolean var6;
                        if(var5 && var2 >= this.mSlop) {
                            var6 = true;
                        } else {
                            var6 = false;
                        }

                        this.onDownHalf = var6;
                        if(!var6) {
                            boolean var7;
                            label86: {
                                if(var2 <= -this.mSlop) {
                                    if(this.getScrollY() <= 10 && this.normalView.getScrollY() >= -10 + (this.maxUpheight - this.visibleHeight)) {
                                        var5 = true;
                                    } else {
                                        var5 = false;
                                    }

                                    if(var5) {
                                        var7 = true;
                                        break label86;
                                    }
                                }

                                var7 = false;
                            }

                            this.onUpHalf = var7;
                            if(!var7) {
                                this.acquireVelocityTracker(var1);
                                this.mVelocityTracker.computeCurrentVelocity(100);
                                float var8 = this.mVelocityTracker.getYVelocity();
                                this.releaseVelocityTracker();
                                if(!var5 && var8 > 70.0F && var2 < 0) {
                                    this.innerDragging = true;
                                    this.requestDisallowInterceptTouchEvent(true);
                                    return false;
                                }

                                return super.onInterceptTouchEvent(var1);
                            }
                        }

                        if(this.down != null) {
                            this.down.recycle();
                            this.down = null;
                        }

                        this.innerDragging = false;
                        this.requestDisallowInterceptTouchEvent(false);
                        return true;
                    }
                case 1:
                default:
                    return super.onInterceptTouchEvent(var1);
            }
        }

        return var15;
    }
    protected void onMeasure(int var1, int var2) {
        //super.onMeasure(var1, MeasureSpec.makeMeasureSpec(536870911, 0));
        super.onMeasure(var1,var2);
    }
    public boolean onTouchEvent(MotionEvent var1) {
        if(!this.enabled) {
            return false;
        } else {
            int var2 = this.visibleHeight;
            switch(255 & var1.getAction()) {
                case 0:
                    return true;
                case 1:
                    this.isDragging = false;
                    this.releaseVelocityTracker();
                    this.requestDisallowInterceptTouchEvent(false);
                    float var3 = 1.0F;
                    if(this.down != null) {
                        var3 = this.down.getY() - var1.getY();
                    }

                    this.setScrollingCacheEnabled(false);
                    if((var3 <= 0.0F || (double)this.getScrollY() <= 0.05D * (double)var2) && (var3 >= 0.0F || (double)this.getScrollY() <= 0.95D * (double)var2 + (double)this.dividerHeight)) {
                        if(this.getScrollY() != var2) {
                            this.animating = true;
                            this.mScroller.startScroll(0, this.getScrollY(), 0, -this.getScrollY(), 600);
                            ViewCompat.postInvalidateOnAnimation(this);
                            this.innerDragging = false;
                            if(this.onPullListener != null) {
                                this.postDelayed(new Runnable() {
                                    public void run() {
                                        ExpandableScrollView.this.onPullListener.onReleased(true);
                                    }
                                }, 200L);
                            }
                        }
                    } else {
                        int var5;
                        if(this.expandedView.getParent() != this) {
                            var5 = ((View)this.expandedView.getParent()).getTop();
                        } else {
                            var5 = this.expandedView.getTop();
                        }

                        this.mScroller.startScroll(0, this.getScrollY(), 0, var5 - this.getScrollY(), 800);
                        if(this.onPullListener != null) {
                            this.postDelayed(new Runnable() {
                                public void run() {
                                    ExpandableScrollView.this.onPullListener.onReleased(false);
                                }
                            }, 200L);
                        }

                        ViewCompat.postInvalidateOnAnimation(this);
                        this.animating = true;
                        this.innerDragging = true;
                    }

                    this.onUpHalf = false;
                    this.onDownHalf = false;
                    if(this.down != null) {
                        this.down.recycle();
                        this.down = null;
                    }
                    break;
                case 2:
                    if(this.down == null) {
                        this.down = MotionEvent.obtain(var1);
                        this.lastY = this.down.getY();
                    }

                    if(this.allowInterceptForHorizontal && this.checkIsHorizontalMove(this.down, var1)) {
                        this.requestDisallowInterceptTouchEvent(false);
                        this.isHorizontalMove = true;
                    } else {
                        this.isHorizontalMove = false;
                        float var7 = this.down.getY() - var1.getY();
                        double var8 = Math.max(0.0D, 1.0D - Math.pow((double)Math.max(0.0F, 1.0F - Math.abs(var7 / (float)var2)), 2.0D));
                        double var10 = var8 * (double)this.dividerHeight;
                        if(var7 <= 0.0F) {
                            var10 = -var10;
                        }

                        this.setScrollingCacheEnabled(true);
                        int var12;
                        if(this.expandedView.getParent() != this) {
                            var12 = ((View)this.expandedView.getParent()).getTop();
                        } else {
                            var12 = this.expandedView.getTop();
                        }

                        if(!this.onDownHalf) {
                            var12 = 0;
                        }

                        this.scrollTo(0, var12 + (int)var10);
                        this.lastY = var1.getY();
                        this.requestDisallowInterceptTouchEvent(true);
                        this.isDragging = true;
                        if(this.onPullListener != null) {
                            byte var13;
                            if(this.onDownHalf) {
                                if(var7 > 0.0F) {
                                    var13 = 0;
                                } else {
                                    var13 = 1;
                                }
                            } else if(var7 > 0.0F) {
                                var13 = 1;
                            } else {
                                var13 = 0;
                            }

                            ExpandableScrollView.OnPullListener var14 = this.onPullListener;
                            boolean var15;
                            if(this.onDownHalf) {
                                var15 = false;
                            } else {
                                var15 = true;
                            }

                            var14.onPulled(var15, var8 * (double)var13);
                        }
                    }
            }

            return false;
        }
    }

    public void scrollToTop() {
        this.normalView.scrollTo(0, 0);
        if(this.expandedView != null) {
            this.expandedView.clearAnimation();
            this.expandedView.scrollTo(0, 0);
        }

        if(this.onPullListener != null) {
            this.onPullListener.onSilentReset();
        }

        this.animating = true;
        this.mScroller.startScroll(0, this.getScrollY(), 0, -this.getScrollY(), 200);
        ViewCompat.postInvalidateOnAnimation(this);
        this.innerDragging = false;
    }

    public void setDivider(View var1) {
        this.divider = var1;
    }

    public void setEnabled(boolean var1) {
        super.setEnabled(var1);
        this.enabled = var1;
    }

    public void setExpandedView(View var1) {
        this.expandedView = var1;
        var1.setVisibility(VISIBLE);
        this.expandedView.getLayoutParams().height = this.visibleHeight;
    }

    public void setNormalView(View var1) {
        this.normalView = var1;
        this.normalView.getLayoutParams().height = this.visibleHeight;
        boolean var2;
        if(!(this.normalView instanceof ScrollView) && !(this.normalView instanceof AbsListView)) {
            var2 = false;
        } else {
            var2 = true;
        }

        this.upDraggable = var2;
    }

    public void setOnPullListener(ExpandableScrollView.OnPullListener var1) {
        this.onPullListener = var1;
    }

    public void setVisibleHeight(int var1, int var2) {
        this.visibleHeight = var1;
        if(this.expandedView != null) {
            this.expandedView.getLayoutParams().height = var1;
        }

        if(this.normalView != null) {
            this.normalView.getLayoutParams().height = var1;
        }

        if(this.divider != null) {
            LayoutParams var3 = (LayoutParams)this.divider.getLayoutParams();
            int var4 = this.visibleHeight / 4;
            this.dividerHeight = var4;
            var3.height = var4;
        }

    }

    public interface OnPullListener {
        void onPulled(boolean var1, double var2);

        void onReleased(boolean var1);

        void onSilentReset();
    }
}
