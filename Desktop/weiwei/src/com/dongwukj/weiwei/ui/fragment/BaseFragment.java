package com.dongwukj.weiwei.ui.fragment;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
	public View view_parent;
	public Dialog guidedialog;
	public FragmentActivity activity;
	protected ProgressDialog progressDialog;
	public BaseApplication baseApplication;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		activity = getActivity();
		if (activity!=null) {
			progressDialog = new ProgressDialog(activity);
        	// progressDialog.setTitle("提示");
             progressDialog.setCancelable(false);
        }
		baseApplication=(BaseApplication) activity.getApplication();
		view_parent=setView_parent(inflater);
		initview();
		setListener();
		guidedialog=new Dialog(getActivity(), R.style.Dialog);
		/*List<CartEntity> lis;*/
		return view_parent;
	}
	  public int getTitleHeight(Activity activity) {  
	        Rect rect = new Rect();  
	          Window window = activity.getWindow();  
	         window.getDecorView().getWindowVisibleDisplayFrame(rect);  
	         int statusBarHeight = rect.top;  
	      int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();  
	         int titleBarHeight = contentViewTop - statusBarHeight;  

	         return titleBarHeight;  
	     } 
	    public int getStateHeight(Activity activity ) {  
	         Rect rect = new Rect();  
	        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);  
	         return rect.top;  
	    } 
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            onFragmentResume();
        } else {
            //相当于Fragment的onPause
            onFragmentPause();
        }
    }
    public void showguide(View address,OnClickListener listener,boolean Istop,int top,int bottom,int rule) {
    	if(guidedialog==null) return;
    	guidedialog.setCancelable(false);
    	RelativeLayout layout=new RelativeLayout(getActivity());
    	layout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT ));
    	int [] xy=new int[2];
    	address.getLocationInWindow(xy);
    	int height = address.getHeight();
    	if (Istop) {
    		android.widget.RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        	ImageView imageView1=new ImageView(getActivity());
        	imageView1.setImageResource(bottom);
        	params1.setMargins(xy[0], xy[1]-getTitleHeight(getActivity())-getStateHeight(getActivity())+height, 0, 0);
        	imageView1.setLayoutParams(params1);
        	params1.addRule(rule);
        	layout.addView(imageView1);
        	ImageView imageView=new ImageView(getActivity());
        	Drawable draw=getActivity().getResources().getDrawable(top);
        	imageView.setScaleType(ScaleType.FIT_XY);
        	imageView.setImageDrawable(draw);
        	android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        	params.setMargins(xy[0],xy[1]-getTitleHeight(getActivity())-getStateHeight(getActivity())-height/2, 0, 0);
        	params.addRule(rule);
        	imageView.setLayoutParams(params);
        	layout.addView(imageView);
        	guidedialog.setContentView(layout);
    	}
    	WindowManager m = getActivity().getWindowManager();
        Window dialogWindow = guidedialog.getWindow();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight()); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() ); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
		guidedialog.show();
		layout.setOnClickListener(listener);
		
	} 
    protected void onFragmentResume(){}
    protected void onFragmentPause(){}
    public void showProgress(boolean isShow){
        if(isShow){
            if(!progressDialog.isShowing()){
                progressDialog.show();
            }
        }else{
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }
    /**
	 * 设置要显示的界面
	 *
	 */
	 
	public abstract View setView_parent(LayoutInflater inflater);
	/**
	 * 
	 * 设置监听
	 */
	public abstract void setListener();
	
	/**
	 * 初始化控件	
	 */
	public abstract void initview();
	/**
	 * 进行具体的监听操作
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}