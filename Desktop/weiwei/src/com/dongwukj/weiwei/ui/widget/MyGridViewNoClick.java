package com.dongwukj.weiwei.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class MyGridViewNoClick extends GridView {

	public MyGridViewNoClick(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyGridViewNoClick(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyGridViewNoClick(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	 @Override
	    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        int expandSpec = MeasureSpec.makeMeasureSpec(
	                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
	        super.onMeasure(widthMeasureSpec, expandSpec);
	    }
	 @Override  
	 public boolean dispatchTouchEvent(MotionEvent ev) {  
//	     if(ev.getAction() == MotionEvent.ACTION_MOVE)  
//	     {  
//	         return true;  
//	     }else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//			return true;
//		}  
	    
	     return false;  
	 }  
	 
	 
	 @Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return true;
	}
	 
}
