package com.dongwukj.weiwei.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.CategoryGridViewAdapter;
import com.dongwukj.weiwei.adapter.CategoryListViewAdapter;
import com.dongwukj.weiwei.adapter.CategoryRightListViewAdapter;
import com.dongwukj.weiwei.adapter.GridViewAdapter;
import com.dongwukj.weiwei.idea.request.CategoryRequest;
import com.dongwukj.weiwei.idea.result.CategoryEntity;
import com.dongwukj.weiwei.idea.result.CategoryResult;
import com.dongwukj.weiwei.idea.result.ClassifyDetails;
import com.dongwukj.weiwei.net.CategoryRequestClient;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class OldClassifyFragment extends Fragment {

    private List<TextView> lv_list;
    private LinearLayout ll;
    private ListView lv;
    private PullToRefreshListView leftListView;
    private List<ClassifyDetails> list_Details;
    private GridViewAdapter adapter;
    private int mainCategoryId = 12;
    private BaseApplication baseApplication;
    private List<CategoryEntity> mainCategoryList;
    private ArrayList<CategoryEntity> secondCategoryList;
    private CategoryListViewAdapter categoryListViewAdapter;
    private CategoryGridViewAdapter categoryGridViewAdapter;

    private CategoryRightListViewAdapter categoryRightListViewAdapter;
    private FragmentActivity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	activity=getActivity();
        baseApplication = (BaseApplication)activity.getApplication();
        ll = (LinearLayout) inflater.inflate(R.layout.classify_fragment, null);
        lv = (ListView) ll.findViewById(R.id.lv);
        leftListView = (PullToRefreshListView) ll.findViewById(R.id.left_lv);
        initView();
        return ll;
    }

    private void initView() {
        mainCategoryList=new ArrayList<CategoryEntity>();
        secondCategoryList=new ArrayList<CategoryEntity>();
        categoryListViewAdapter=new CategoryListViewAdapter(activity,mainCategoryList);
        categoryGridViewAdapter=new CategoryGridViewAdapter(activity,secondCategoryList);
        categoryRightListViewAdapter=new CategoryRightListViewAdapter(activity,secondCategoryList);
        lv.setAdapter(categoryListViewAdapter);
        //gv.setAdapter(categoryGridViewAdapter);
        leftListView.setAdapter(categoryRightListViewAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainCategoryId=mainCategoryList.get(position).getCateId();
                fetchSecondCategory();
            }
        });
        /*gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryEntity categoryEntity=secondCategoryList.get(position);
                if(categoryEntity!=null){
                    Intent intent=new Intent(getActivity(), HomeHeaderActivity.class);
                    intent.putExtra("title",categoryEntity.getName());
                    intent.putExtra("categoryId",categoryEntity.getCateId());
                    intent.putExtra("type", HeaderActivityType.CategoryDetails.ordinal());
                    startActivity(intent);
                }
            }
        });*/

        updateDataHandler.sendEmptyMessage(UPDATE_MAIN);

    }

    private final int UPDATE_MAIN=100;
    private final int UPDATE_SECOND=101;
    private Handler updateDataHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_MAIN:
                    fetchMainCategory(false);
                    break;
                case UPDATE_SECOND:
                    fetchSecondCategory();
                    break;
            }
        }
    };

    private void fetchMainCategory(boolean isInner) {
        CategoryRequest categoryRequest = new CategoryRequest(1, 12);


        CategoryRequestClient categoryRequestClient=new CategoryRequestClient(activity,baseApplication);
        categoryRequestClient.list(categoryRequest, new CategoryRequestClient.CategoryRequestCallback() {
            @Override
            protected void list(CategoryResult result) {
            	if (getActivity()==null) {
					return;
				}
                mainCategoryList.addAll(result.getCategories());
                categoryListViewAdapter.notifyDataSetChanged();
                lv.setSelection(0);
                mainCategoryId=result.getCategories().get(0).getCateId();
                updateDataHandler.sendEmptyMessage(UPDATE_SECOND);
            }
        });


    }
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	   MobclickAgent.onPageStart("ClassifyFragment");
    }
    @Override
    public void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	 MobclickAgent.onPageEnd("ClassifyFragment"); 
    }
    private void fetchSecondCategory(){
    	secondCategoryList.clear();
    	categoryGridViewAdapter.notifyDataSetChanged();
        CategoryRequest categoryRequest = new CategoryRequest(2, mainCategoryId);
        CategoryRequestClient categoryRequestClient=new CategoryRequestClient(activity,baseApplication);
        categoryRequestClient.list(categoryRequest, new CategoryRequestClient.CategoryRequestCallback() {
            @Override
            protected void list(CategoryResult result) {
            	if (getActivity()==null) {
					return;
				}
                if(result.getCategories()!=null){
                    secondCategoryList.addAll(result.getCategories());
                }
                /*for(CategoryEntity categoryEntity:result.getCategories()){
                    if(categoryEntity.getChildCategorys()!=null){
                        secondCategoryList.addAll(categoryEntity.getChildCategorys());
                    }
                }*/

                categoryGridViewAdapter.notifyDataSetChanged();
            }
        });

    }
}
