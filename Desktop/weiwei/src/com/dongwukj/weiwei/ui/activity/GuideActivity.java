package com.dongwukj.weiwei.ui.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.dongwukj.weiwei.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {

	private List<ImageView> list;
	private Button bt;
	private int[] imgs;

	@Override
	protected void findViewById() {
		ViewPager vp = (ViewPager) findViewById(R.id.vp);
		bt = (Button) findViewById(R.id.bt);
		bt.setOnClickListener(this);
		imgs = new int[] { R.drawable.weiwei_guide1, R.drawable.weiwei_guide2,
				R.drawable.weiwei_guide3,R.drawable.weiwei_guide4 };
		list = new ArrayList<ImageView>();
		for (int i = 0; i < imgs.length; i++) {
			ImageView view = new ImageView(this);
			view.setBackgroundResource(imgs[i]);
			list.add(view);
		}
		vp.setAdapter(new PagerAdapter() {

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(list.get(position));
				return list.get(position);
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
				container.removeView(list.get(position));
			}

			@Override
			public boolean isViewFromObject(View paramView, Object paramObject) {
				// TODO Auto-generated method stub
				return paramView == paramObject;
			}

			@Override
			public int getCount() {

				return list.size();
			}
		});
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int paramInt) {
				
				if (paramInt==list.size()-1) {
					bt.setVisibility(View.VISIBLE);
				}else {
					bt.setVisibility(View.GONE);
				}
				
			}
			
			@Override
			public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int paramInt) {
				
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt:
			Intent intent=new Intent(GuideActivity.this,LoginActivity.class);
	         startActivity(intent);
	         finish();
			break;

		default:
			break;
		}
	}
	@Override
	protected void initView() {
		setContentView(R.layout.activity_guide);
	}

}
