package com.dongwukj.weiwei.ui.fragment;

import android.os.Handler;
import android.widget.GridView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;



public class PullToRefreshTest {
    protected  int PULL_DOWN_MODE;
	protected int PULL_UP_MODE;
	private int PULL_EMPTY;
	
	
    private Handler mhHandler;
    public void initListView(PullToRefreshListView pullToRefreshListView){
    	pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mhHandler.sendEmptyMessage(PULL_DOWN_MODE);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mhHandler.sendEmptyMessage(PULL_UP_MODE);
			}
		});
    	pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
    	mhHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);
    }
    public void initGridView(PullToRefreshGridView PullToRefreshGridView){
    	PullToRefreshGridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				mhHandler.sendEmptyMessage(PULL_DOWN_MODE);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				mhHandler.sendEmptyMessage(PULL_UP_MODE);
			}
		});
    	PullToRefreshGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
    	mhHandler.sendEmptyMessageDelayed(PULL_EMPTY, 500);
    }
	public PullToRefreshTest(int pULL_DOWN_MODE, int pULL_UP_MODE,
			int pULL_EMPTY, 
			Handler mhHandler) {
		super();
		PULL_DOWN_MODE = pULL_DOWN_MODE;
		PULL_UP_MODE = pULL_UP_MODE;
		PULL_EMPTY = pULL_EMPTY;
		this.mhHandler = mhHandler;
		
	}
	
    
}
