package com.dongwukj.weiwei.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dongwukj.weiwei.idea.request.PagedRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.net.AbsDataRequestClient;
import com.dongwukj.weiwei.net.AbsRequestClientCallback;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by sunjaly on 15/4/18.
 */
public abstract class AbstractPullLoadingFragment extends AbstractLoadingFragment{


    private PullToRefreshAdapterViewBase pullView;

    protected int pageIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= super.onCreateView(inflater, container, savedInstanceState);
        pullView=initPullView();
        initPullViewEvent();
        return v;
    }
    private void initPullViewEvent(){
        pullView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                pullRefreshHandler.sendEmptyMessage(PULL_DOWN);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                pullRefreshHandler.sendEmptyMessage(PULL_UP);
            }
        });

        pullView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
    }

    protected abstract PullToRefreshAdapterViewBase initPullView();

    private final int PULL_DOWN=1;
    private final int PULL_UP=2;
    private Handler pullRefreshHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PULL_DOWN:{
                    fetchData(true);
                    break;
                }
                case PULL_UP:{
                    fetchData(true);
                    break;
                }
            }
        }
    };

    protected abstract AbsDataRequestClient getRequestClient();
    protected abstract PagedRequest getRequest();

    private void fetchData(final boolean isFirst){
        AbsDataRequestClient requestClient=getRequestClient();
        PagedRequest request=getRequest();
        if(isFirst){
            request.setPageIndex(1);
        }else{
            request.setPageIndex(++pageIndex);
        }
//        requestClient.fetchDate("", getRequest(), new AbsRequestClientCallback() {
//            @Override
//            protected void success(BaseResult result) {
//
//                if (isFirst){
//                    pageIndex=1;
//                }
//                pullView.setMode(PullToRefreshBase.Mode.BOTH);
//            }
//
//            @Override
//            protected void fail(BaseResult result) {
//
//            }
//
//            @Override
//            protected void complete(BaseResult result) {
//
//                pullView.onRefreshComplete();
//            }
//        });
    }

}
