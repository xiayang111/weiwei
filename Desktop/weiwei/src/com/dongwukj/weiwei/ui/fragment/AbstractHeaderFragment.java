package com.dongwukj.weiwei.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by sunjaly on 15/3/26.
 */
public abstract class AbstractHeaderFragment extends Fragment {

    protected BaseApplication baseApplication;
	public Dialog dialog;
	public Dialog guidedialog;
	public FragmentActivity  activity;
	protected ProgressDialog progressDialog;
	
    
	
	
	

	protected void setRightButtonClickListener(OnClickListener listener,String title){
    	if(listener!=null){
    		TextView list_header_rightbutton=(TextView)activity.findViewById(R.id.list_header_rightbutton);
    		list_header_rightbutton.setVisibility(View.VISIBLE);
    		list_header_rightbutton.setOnClickListener(listener);
    		list_header_rightbutton.setText(title);
    	}
    }

    protected void setRightButtonClickListener(OnClickListener listener,String title,int resId){
        if(listener!=null){
        	TextView list_header_rightbutton=(TextView)activity.findViewById(R.id.list_header_rightbutton);
            list_header_rightbutton.setVisibility(View.VISIBLE);
            list_header_rightbutton.setOnClickListener(listener);
            list_header_rightbutton.setText(title);
            list_header_rightbutton.setBackgroundResource(resId);
            
        }
    }
    
    //提示确认 对话框
    protected void showDialogs(OnClickListener listener,String title1){
    	dialog = new Dialog(activity, R.style.Dialog);
		LinearLayout view = (LinearLayout) View.inflate(activity, R.layout.is_delete_address_dialog, null);
		dialog.setContentView(view);
		dialog.setCancelable(false); //为false 按返回键不能 dismiss Dialog
		
		TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(title1);
		tv_ok.setOnClickListener(listener);
		TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		tv_cancel.setOnClickListener(listener);
			WindowManager m = activity.getWindowManager();
	        Window dialogWindow = dialog.getWindow();
	        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
	        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
	        p.height = (int) (d.getHeight() * 0.18); // 高度设置为屏幕的0.6
	        p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕的0.65
	        dialogWindow.setAttributes(p);
		
		dialog.show();
		
    }
    

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
    public void showtoast(String text,Context context) {
    	if (context==null) {
			return;
		}
    	Toast.makeText(baseApplication, text, Toast.LENGTH_SHORT).show();
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		activity =getActivity();
		if (activity!=null) {
			progressDialog = new ProgressDialog(activity);
        	// progressDialog.setTitle("提示");
             progressDialog.setCancelable(false);
        }
        baseApplication=(BaseApplication)activity.getApplication();
        View view=initView(inflater,container,savedInstanceState);
        findView(view);
        setTitleAndLeftButton();
        return view;
    }
    protected void setTitleAndLeftButton(){
        View view1=activity.findViewById(R.id.list_header_title);
        if(view1!=null){
            ((TextView)view1).setText(setTitle());
        }
        View view2=activity.findViewById(R.id.ll_left);
        
        
        if(view2!=null){
            ((LinearLayout)view2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	activity.finish();
                }
            });
        }
    }
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	 MobclickAgent.onPageStart(this.getClass().getSimpleName());
    	super.onResume();
    	
    }
    @Override
    public void onPause() {
    	// TODO Auto-generated method stub
    	MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    	super.onPause();
    	
    	
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
    public void showguide(View address,OnClickListener listener,boolean Istop,int top,int bottom) {
    	guidedialog = new Dialog(activity, R.style.Dialog);
    	guidedialog.setCancelable(false);
    	RelativeLayout layout=new RelativeLayout(activity);
    	layout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT ));
    	int [] xy=new int[2];
    	address.getLocationInWindow(xy);
    	int height = address.getHeight();
    	if (Istop) {
    		android.widget.RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        	ImageView imageView1=new ImageView(activity);
        	imageView1.setImageResource(bottom);
        	params1.setMargins(xy[0], xy[1]-getTitleHeight(activity)-getStateHeight(activity)+height, 0, 0);
        	imageView1.setLayoutParams(params1);
        	layout.addView(imageView1);
        	ImageView imageView=new ImageView(activity);
        	Drawable draw=activity.getResources().getDrawable(top);
        	imageView.setScaleType(ScaleType.FIT_XY);
        	imageView.setImageDrawable(draw);
        	android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        	params.setMargins(xy[0],xy[1]-getTitleHeight(activity)-getStateHeight(activity)-height/2, 0, 0);
        	imageView.setLayoutParams(params);
        	layout.addView(imageView);
    		guidedialog.setContentView(layout);
		}else {
			android.widget.RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
	    	ImageView imageView=new ImageView(activity);
	    	Drawable draw=activity.getResources().getDrawable(top);
	    	imageView.setScaleType(ScaleType.FIT_XY);
	    	imageView.setImageDrawable(draw);
	    	android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
	    	params.setMargins(xy[0],xy[1]-getTitleHeight(activity)-getStateHeight(activity)-height/2, 0, 0);
	    	imageView.setLayoutParams(params);
	    	layout.addView(imageView);
	    	ImageView imageView1=new ImageView(activity);
	    	imageView1.setImageResource(bottom);
	    	params1.setMargins(xy[0], xy[1]-getTitleHeight(activity)-getStateHeight(activity)+height, 0, 0);
	    	imageView1.setLayoutParams(params1);
	    	layout.addView(imageView1);
			guidedialog.setContentView(layout);
		}
    	
		WindowManager m = activity.getWindowManager();
        Window dialogWindow = guidedialog.getWindow();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight()); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() ); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
		guidedialog.show();
		layout.setOnClickListener(listener);
		
	}            
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    protected abstract String setTitle();
    protected abstract void findView(View v);
    
    

}
