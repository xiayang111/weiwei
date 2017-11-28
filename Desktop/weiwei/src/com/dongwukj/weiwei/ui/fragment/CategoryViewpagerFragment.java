package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.CategoryEntity;
import com.dongwukj.weiwei.ui.fragment.CategoryFragment.Itemclick;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CategoryViewpagerFragment extends BaseFragment {
	ArrayList<CategoryEntity> list=new ArrayList<CategoryEntity>();
	private View inflate;
	@Override
	public View setView_parent(LayoutInflater inflater) {
		inflate = inflater.inflate(R.layout.category_viewpager_fragment, null);
		return inflate;
	}

	@Override
	public void setListener() {
		
	}
	 public static int px2dip(Context context, float pxValue) {
	        final float scale = context.getResources().getDisplayMetrics().density;
	        return (int) (pxValue / scale + 0.5f);
	    }

	@Override
	public void initview() {
		viewPager = (ViewPager) inflate.findViewById(R.id.vp);
		 ll_ball = (LinearLayout) inflate.findViewById(R.id.ll_ball);
		adapter = new MyAdapter(activity.getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int paramInt) {
					for (int i = 0; i < ll_ball.getChildCount(); i++) {
						if (i==paramInt) {
							ll_ball.getChildAt(i).setEnabled(true);
						}else {
							ll_ball.getChildAt(i).setEnabled(false);
						}
					}
					
					
			}
				
				@Override
				public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int paramInt) {
					// TODO Auto-generated method stub
					
				}
			});
	}
	private ArrayList<Fragment> fragments=new ArrayList<Fragment>();
	private ArrayList<CategoryEntity> categoryEntitiesone=new ArrayList<CategoryEntity>();
	private ArrayList<CategoryEntity> categoryEntitiestwo=new ArrayList<CategoryEntity>();
	private ViewPager viewPager;
	private LinearLayout ll_ball;
	private MyAdapter adapter;
	public void setCategoryViewpager(ArrayList<CategoryEntity> list,Itemclick itemclick){
		this.list=list;
		fragments.clear();
		categoryEntitiesone.clear();
		categoryEntitiestwo.clear();
		adapter.notifyDataSetChanged();
		ll_ball.removeAllViews();
		if (list.size()>8) {
			for (int i = 0; i < 2; i++) {
				ImageView view=new ImageView(activity);
				LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				params.rightMargin=px2dip(activity, 10);
				view.setLayoutParams(params);
				view.setImageResource(R.drawable.imageview_selector);
				view.setEnabled(false);
				ll_ball.addView(view);
			}
			ll_ball.getChildAt(0).setEnabled(true);
		}
		
		if (list.size()>8) {
			for (int i = 0; i < list.size(); i++) {
				if (i>=8) {
					categoryEntitiestwo.add(list.get(i));
				}else {
					categoryEntitiesone.add(list.get(i));
				}
				
			}
			CategoryFragment categoryFragment = new CategoryFragment();
			categoryFragment.setCategoryFragment(categoryEntitiesone, itemclick);
			CategoryFragment categoryFragmenttwo = new CategoryFragment();
			categoryFragmenttwo.setCategoryFragment(categoryEntitiestwo, itemclick);
			fragments.add(categoryFragment);
			fragments.add(categoryFragmenttwo);
			//fragments.add(new CategoryFragment(categoryEntitiestwo,itemclick));
		}else {
			for (int i = 0; i <list.size(); i++) {
				categoryEntitiesone.add(list.get(i));
			}
			CategoryFragment categoryFragment = new CategoryFragment();
			categoryFragment.setCategoryFragment(categoryEntitiesone, itemclick);
			fragments.add(categoryFragment);
			//fragments.add(new CategoryFragment(categoryEntitiesone,itemclick));
		}
		adapter.notifyDataSetChanged();
		viewPager.setCurrentItem(0);
	}
	class MyAdapter extends FragmentPagerAdapter{

		public MyAdapter(FragmentManager fm) {
			super(fm);
			
		}

		@Override
		public Fragment getItem(int paramInt) {
			
			return fragments.get(paramInt);
		}

		@Override
		public int getCount() {
			
			return fragments.size();
		}

		
		
	} 
}
