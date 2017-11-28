package com.dongwukj.weiwei.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import com.dongwukj.weiwei.R;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * Created by sunjaly on 15/4/26.
 */
public class TestPullLayout extends LinearLayout {

    Scroller mScroller;
    PullToRefreshScrollView contentView;
    PullToRefreshScrollView detailView;
    int visibleHeight;
    private DisplayMetrics displayMetrics;

    DetailScrollTopIndicator detailScrollTopIndicator;
    DetailScrollBottomIndicator detailScrollBottomIndicator;

    public void setDetailScrollTopIndicator(DetailScrollTopIndicator detailScrollTopIndicator) {
        this.detailScrollTopIndicator = detailScrollTopIndicator;
    }

    public void setDetailScrollBottomIndicator(DetailScrollBottomIndicator detailScrollBottomIndicator) {
        this.detailScrollBottomIndicator = detailScrollBottomIndicator;
    }

    public void setContentView(PullToRefreshScrollView contentView) {
        this.contentView = contentView;
        this.contentView.getLayoutParams().height = this.visibleHeight;
        this.contentView.setOnRefreshListener(new ContentViewPulled());
        detailScrollBottomIndicator = (DetailScrollBottomIndicator) contentView.findViewById(R.id.bottom_indicator);
        contentView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ScrollView>() {
            @Override
            public void onPullEvent(PullToRefreshBase<ScrollView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
                /*contentViewText.setText(state.toString());*/

                if(detailScrollBottomIndicator==null) return;
                if(state== PullToRefreshBase.State.PULL_TO_REFRESH){

                    detailScrollBottomIndicator.onPulled(0);
                }else if(state== PullToRefreshBase.State.RELEASE_TO_REFRESH){
                    detailScrollBottomIndicator.setVisibility(VISIBLE);
                     detailScrollBottomIndicator.onPulled(1);
                 }
            }
        });

        ILoadingLayout loadingLayout=contentView.getLoadingLayoutProxy();
        loadingLayout.setLoadingDrawable(null);
        loadingLayout.setRefreshingLabel(" ");
        loadingLayout.setPullLabel("  ");
        loadingLayout.setLastUpdatedLabel("  ");
        loadingLayout.setReleaseLabel("  ");

    }

    public void setDetailView(PullToRefreshScrollView detailView) {
        this.detailView = detailView;
        this.detailView.getLayoutParams().height = this.visibleHeight;
        this.detailView.setVisibility(VISIBLE);
        this.detailView.setOnRefreshListener(new DetailViewPulled());


        detailScrollTopIndicator = (DetailScrollTopIndicator) detailView.findViewById(R.id.top_indicator);

        detailView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ScrollView>() {
            @Override
            public void onPullEvent(PullToRefreshBase<ScrollView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
                if(detailScrollTopIndicator==null) return;
                if(state== PullToRefreshBase.State.RESET || state== PullToRefreshBase.State.PULL_TO_REFRESH){
                    detailScrollTopIndicator.onPulled(0);
                }else if(state== PullToRefreshBase.State.RELEASE_TO_REFRESH){
                    detailScrollTopIndicator.onPulled(1);
                }
            }
        });
        ILoadingLayout loadingLayout1=detailView.getLoadingLayoutProxy();
        loadingLayout1.setLoadingDrawable(null);
        loadingLayout1.setRefreshingLabel(" ");
        loadingLayout1.setPullLabel("  ");
        loadingLayout1.setLastUpdatedLabel("  ");
        loadingLayout1.setReleaseLabel("  ");
    }

    public TestPullLayout(Context context) {
        this(context, null);
    }

    public TestPullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller=new Scroller(context);
        displayMetrics=new DisplayMetrics();
        WindowManager windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        this.visibleHeight = displayMetrics.heightPixels-200;
    }

    public void computeScroll() {
        super.computeScroll();
        if(this.mScroller.computeScrollOffset()) {
            this.scrollTo(0, this.mScroller.getCurrY());
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }


    public void updateUI(int height){
        if(height!=visibleHeight){
            visibleHeight=height;
            //this.detailView.getLayoutParams().height = this.visibleHeight;
            //this.contentView.getLayoutParams().height = this.visibleHeight;
            ViewGroup.LayoutParams layoutParams1=this.contentView.getLayoutParams();
            ViewGroup.LayoutParams layoutParams2=this.detailView.getLayoutParams();
            layoutParams1.height=this.visibleHeight;
            layoutParams2.height=this.visibleHeight;

            this.contentView.setLayoutParams(layoutParams1);
            this.detailView.setLayoutParams(layoutParams2);



        }
        ViewCompat.postInvalidateOnAnimation(this);
    }


    private class DetailViewPulled implements PullToRefreshBase.OnRefreshListener2{
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            detailView.onRefreshComplete();
            detailScrollTopIndicator.onReleased();
            detailScrollBottomIndicator.setVisibility(VISIBLE);
            detailScrollTopIndicator.setVisibility(INVISIBLE);

            int var5;
            if(TestPullLayout.this.detailView.getParent() != TestPullLayout.this) {
                var5 = ((View)TestPullLayout.this.detailView.getParent()).getTop();
            } else {
                var5 = TestPullLayout.this.detailView.getTop();
            }
            //TestPullLayout.this.mScroller.startScroll(0, TestPullLayout.this.getScrollY(), 0, var5 - TestPullLayout.this.getScrollY(), 800);
            TestPullLayout.this.mScroller.startScroll(0, TestPullLayout.this.getScrollY(), 0, -TestPullLayout.this.getScrollY(), 600);
            ViewCompat.postInvalidateOnAnimation(TestPullLayout.this);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {

        }
    }


    private class ContentViewPulled implements PullToRefreshBase.OnRefreshListener2{
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {

        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            contentView.onRefreshComplete();
            detailScrollBottomIndicator.onReleased();
            detailScrollBottomIndicator.setVisibility(INVISIBLE);
            detailScrollTopIndicator.setVisibility(VISIBLE);
            int var5;
            if(TestPullLayout.this.detailView.getParent() != TestPullLayout.this) {
                var5 = ((View)TestPullLayout.this.detailView.getParent()).getTop();
            } else {
                var5 = TestPullLayout.this.detailView.getTop();
            }
            //TestPullLayout.this.mScroller.startScroll(0, TestPullLayout.this.getScrollY(), 0, -TestPullLayout.this.getScrollY(), 600);
            TestPullLayout.this.mScroller.startScroll(0, TestPullLayout.this.getScrollY(), 0, var5 - TestPullLayout.this.getScrollY(), 800);
            //TestPullLayout.this.mScroller.startScroll(0,TestPullLayout.this.getScrollY(),0,100,600);
            ViewCompat.postInvalidateOnAnimation(TestPullLayout.this);
        }
    }
}
