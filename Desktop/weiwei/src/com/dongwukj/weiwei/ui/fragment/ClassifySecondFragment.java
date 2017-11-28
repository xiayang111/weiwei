package com.dongwukj.weiwei.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.CategoryRightListViewAdapter;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.CategoryRequest;
import com.dongwukj.weiwei.idea.result.CategoryEntity;
import com.dongwukj.weiwei.idea.result.CategoryResult;
import com.dongwukj.weiwei.net.CategoryRequestClient;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/4/28.
 */
public class ClassifySecondFragment extends AbstractHeaderFragment {

    private ArrayList<CategoryEntity> secondCategoryList;
    private CategoryRightListViewAdapter categoryRightListViewAdapter;
    private PullToRefreshListView leftListView;
    private int mainCategoryId = -1;
    private boolean hasHeader=true;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.classify_second_fragment,container,false);

        return view;
    }

    @Override
    protected String setTitle() {
        String title=activity.getIntent().getStringExtra("title");
        return title;
    }

    @Override
    protected void setTitleAndLeftButton(){
        if(hasHeader){
            super.setTitleAndLeftButton();
        }else{

        }

    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    @Override
    protected void findView(View v) {
        secondCategoryList=new ArrayList<CategoryEntity>();
        categoryRightListViewAdapter=new CategoryRightListViewAdapter(activity,secondCategoryList);
        leftListView = (PullToRefreshListView) v.findViewById(R.id.left_lv);
        leftListView.setAdapter(categoryRightListViewAdapter);
        categoryRightListViewAdapter.setOnCategoryItemClickListener(new CategoryRightListViewAdapter.OnCategoryItemClickListener() {
            @Override
            public void onItemClick(int parentId, int childId) {
                CategoryEntity categoryEntity=secondCategoryList.get(parentId);
                if(categoryEntity!=null){
                    CategoryEntity childCategoryEntity =categoryEntity.getChildCategorys().get(childId);
                    if(childCategoryEntity!=null){
                        Intent intent=new Intent(activity, HomeHeaderActivity.class);
                        intent.putExtra("title",childCategoryEntity.getName());
                        intent.putExtra("categoryId",childCategoryEntity.getCateId());
                        intent.putExtra("type", HeaderActivityType.CategoryDetails.ordinal());
                        intent.putExtra("hasHeader", false);
                        startActivity(intent);
                    }

                }
            }
        });
        mainCategoryId=activity.getIntent().getIntExtra("categoryId",-1);
        if(mainCategoryId!=-1){
            fetchSecondCategory();
        }

    }

    public void reload(int categoryId){
        mainCategoryId=categoryId;
        fetchSecondCategory();
    }

    private void fetchSecondCategory(){
        CategoryRequest categoryRequest = new CategoryRequest(2, mainCategoryId);
        CategoryRequestClient categoryRequestClient=new CategoryRequestClient(activity,baseApplication);
        categoryRequestClient.list(categoryRequest, new CategoryRequestClient.CategoryRequestCallback() {
            @Override
            protected void list(CategoryResult result) {
            	if (getActivity()==null) {
					return;
				}
                secondCategoryList.clear();
                if(result.getCategories()!=null){
                    secondCategoryList.addAll(result.getCategories());
                }

                /*for(CategoryEntity categoryEntity:result.getCategories()){
                    if(categoryEntity.getChildCategorys()!=null){
                        secondCategoryList.addAll(categoryEntity.getChildCategorys());
                    }
                }*/

                categoryRightListViewAdapter.notifyDataSetChanged();
            }
        });

    }
}
