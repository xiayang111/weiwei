package com.dongwukj.weiwei.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.ui.widget.LazyViewPager;
import com.dongwukj.weiwei.ui.widget.LazyViewPager.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;

/**
 * Created by sunjaly on 15/4/20.
 */
public class MessageCenterFragment extends AbstractHeaderFragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private RadioGroup tabs_rg;
    private LazyViewPager viewPager;
    
    private ArrayList<Fragment> fragments;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.message_center_layout,container,false);
        return view;
    }

    @Override
    protected String setTitle() {
        //return "我的信息";
        return getResources().getString(R.string.my_message);
    }
   
    @Override
    protected void findView(View v) {
        fragments=new ArrayList<Fragment>();
        MessageContentFragment f1=new MessageContentFragment();
        f1.setBusinessType(0);
        fragments.add(f1);

        MessageContentFragment f2=new MessageContentFragment();
       
        f2.setBusinessType(1);
        fragments.add(f2);
       

        MessageContentFragment f3=new MessageContentFragment();
        f3.setBusinessType(2);
        fragments.add(f3);

        MessageContentFragment f4=new MessageContentFragment();
        f4.setBusinessType(3);
        fragments.add(f4);

        MessageContentFragment f5=new MessageContentFragment();
        f5.setBusinessType(4);
        fragments.add(f5);



        tabs_rg=(RadioGroup)v.findViewById(R.id.tabs_rg);
        viewPager=(LazyViewPager)v.findViewById(R.id.vp_show);
        tabs_rg.setOnCheckedChangeListener(this);
        viewPager.setAdapter(new MessageViewPageAdapter(getFragmentManager(),fragments));
        
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
		        View radioButton=tabs_rg.getChildAt(position);
		        if(radioButton!=null && radioButton instanceof RadioButton){
		            ((RadioButton)radioButton).setChecked(true);
		        }
		    }
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for(int i=0;i<tabs_rg.getChildCount();i++){
            if(tabs_rg.getChildAt(i).getId()==checkedId){
            	//tabs_rg.getChildAt(i).
            	//RadioButton at = (RadioButton) group.getChildAt(i);
				//at.setTextColor(color.black);
                viewPager.setCurrentItem(i);
                //break;
            }else {
            	RadioButton at = (RadioButton) group.getChildAt(i);
				//at.setTextColor(color.gray);
			}
        }

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        View radioButton=tabs_rg.getChildAt(i);
        if(radioButton!=null && radioButton instanceof RadioButton){
            ((RadioButton)radioButton).setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    private class MessageViewPageAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> list;

        public MessageViewPageAdapter(FragmentManager fm,ArrayList<Fragment> list) {
            super(fm);
            this.list=list;
        }

        @Override
        public Fragment getItem(int i) {
            return list.get(i);
        }

        @Override
        public int getCount() {
            return list.size();
        }

		
    }
}
