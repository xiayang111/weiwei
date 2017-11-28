package com.dongwukj.weiwei.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.CategoryListViewAdapter;
import com.dongwukj.weiwei.adapter.GridViewAdapter;
import com.dongwukj.weiwei.idea.request.CategoryRequest;
import com.dongwukj.weiwei.idea.result.CategoryEntity;
import com.dongwukj.weiwei.idea.result.CategoryResult;
import com.dongwukj.weiwei.idea.result.ClassifyDetails;
import com.dongwukj.weiwei.net.CategoryRequestClient;
import com.dongwukj.weiwei.ui.activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunjaly on 15/4/20.
 */
public class NewClassifyFragment extends AbstractLoadingFragment {

    private List<TextView> lv_list;
    private LinearLayout ll;
    private ListView lv;
    //private PullToRefreshListView leftListView;
    private List<ClassifyDetails> list_Details;
    private GridViewAdapter adapter;
    private int mainCategoryId = 12;
    private BaseApplication baseApplication;
    private List<CategoryEntity> mainCategoryList;
    //private ArrayList<CategoryEntity> secondCategoryList;
    private CategoryListViewAdapter categoryListViewAdapter;
   // private CategoryGridViewAdapter categoryGridViewAdapter;

    //private CategoryRightListViewAdapter categoryRightListViewAdapter;
    private EditText et;

    private ClassifySecondFragment classifySecondFragment;


    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        baseApplication = (BaseApplication) activity.getApplication();
        ll = (LinearLayout) inflater.inflate(R.layout.classify_fragment, container,false);
        lv = (ListView) ll.findViewById(R.id.lv);
        //leftListView = (PullToRefreshListView) ll.findViewById(R.id.left_lv);
        //initView();
        return ll;
    }

    @Override
    protected void findViews(View v) {

        classifySecondFragment=(ClassifySecondFragment)getFragmentManager().findFragmentById(R.id.classify_second_fragment);
        classifySecondFragment.setHasHeader(false);

        et = (EditText) v.findViewById(R.id.home_search_edit);
        et.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, SearchActivity.class);
                startActivity(intent);
            }
        });
        mainCategoryList=new ArrayList<CategoryEntity>();
        //secondCategoryList=new ArrayList<CategoryEntity>();
        categoryListViewAdapter=new CategoryListViewAdapter(activity,mainCategoryList);
        //categoryGridViewAdapter=new CategoryGridViewAdapter(activity,secondCategoryList);
        //categoryRightListViewAdapter=new CategoryRightListViewAdapter(activity,secondCategoryList);
        lv.setAdapter(categoryListViewAdapter);
        //gv.setAdapter(categoryGridViewAdapter);
        //leftListView.setAdapter(categoryRightListViewAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainCategoryId=mainCategoryList.get(position).getCateId();
                categoryListViewAdapter.setSelectedId(position);
                categoryListViewAdapter.notifyDataSetChanged();
                //fetchSecondCategory();
                if(classifySecondFragment!=null){
                    classifySecondFragment.reload(mainCategoryId);
                }
            }
        });
        /*categoryRightListViewAdapter.setOnCategoryItemClickListener(new CategoryRightListViewAdapter.OnCategoryItemClickListener() {
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
                        startActivity(intent);
                    }

                }
            }
        });*/

        /*gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryEntity categoryEntity=secondCategoryList.get(position);
                if(categoryEntity!=null){
                    Intent intent=new Intent(activity, HomeHeaderActivity.class);
                    intent.putExtra("title",categoryEntity.getName());
                    intent.putExtra("categoryId",categoryEntity.getCateId());
                    intent.putExtra("type", HeaderActivityType.CategoryDetails.ordinal());
                    startActivity(intent);
                }
            }
        });*/

        updateDataHandler.sendEmptyMessage(UPDATE_MAIN);
    }

    @Override
    protected void reload() {
        updateDataHandler.sendEmptyMessage(UPDATE_MAIN);
        startLoading();
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
                    if(classifySecondFragment!=null){
                        classifySecondFragment.reload(mainCategoryId);
                    }
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
                endLoading();
                mainCategoryList.addAll(result.getCategories());
                categoryListViewAdapter.setSelectedId(0);
                categoryListViewAdapter.notifyDataSetChanged();

                mainCategoryId=result.getCategories().get(0).getCateId();
                updateDataHandler.sendEmptyMessage(UPDATE_SECOND);
            }

            @Override
            protected void listError(CategoryResult result) {
            	if (getActivity()==null) {
					return;
				}
                failLoading();
            }
        });


    }

    /*private void fetchSecondCategory(){
        CategoryRequest categoryRequest = new CategoryRequest(2, mainCategoryId);
        CategoryRequestClient categoryRequestClient=new CategoryRequestClient(activity,baseApplication);
        categoryRequestClient.list(categoryRequest, new CategoryRequestClient.CategoryRequestCallback() {
            @Override
            protected void list(CategoryResult result) {
                secondCategoryList.clear();
                if(result.getCategories()!=null){
                    secondCategoryList.addAll(result.getCategories());
                }

                *//*for(CategoryEntity categoryEntity:result.getCategories()){
                    if(categoryEntity.getChildCategorys()!=null){
                        secondCategoryList.addAll(categoryEntity.getChildCategorys());
                    }
                }*//*

                categoryRightListViewAdapter.notifyDataSetChanged();
            }
        });

    }*/
}
