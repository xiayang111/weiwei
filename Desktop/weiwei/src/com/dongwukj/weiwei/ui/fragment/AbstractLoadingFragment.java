package com.dongwukj.weiwei.ui.fragment;

import java.util.List;
import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.AppManager;
import com.dongwukj.weiwei.ui.activity.HomeActivity;
import com.dongwukj.weiwei.zxing.decoding.FinishListener;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by sunjaly on 15/4/18.
 */
public abstract class AbstractLoadingFragment extends Fragment {

    /**
     * 内容view
     */
    private RelativeLayout abs_layout_content;
    /**
     * 加载中view
     */
    private View loading_layout;
    private ImageView view1;
	private ImageView view2;
	private ImageView view3;
	private ImageView view4,view5;
	private int index=1;
    private View loading_view_container;
    private boolean isStart=false;
    private View loadfail_view_container;

    private Button loading_repeat_button;

    private BaseApplication baseApplication;
    public FragmentActivity activity;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.abstract_layout_fragment,container,false);
        abs_layout_content=(RelativeLayout)v.findViewById(R.id.abs_layout_content);
        loading_layout=v.findViewById(R.id.loading_layout);
        activity=getActivity();
        baseApplication=(BaseApplication)activity.getApplication();
        View contentView=initContentView(inflater,container,savedInstanceState);
        init();
        findViews(contentView);
        abs_layout_content.addView(contentView);
        
        

        return v;
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
    private void init(){

    	if (activity!=null) {
    		progressDialog = new ProgressDialog(activity);
        	// progressDialog.setTitle("提示");
             progressDialog.setCancelable(false);
        }
        loading_view_container=loading_layout.findViewById(R.id.loading_view_container);
        loadfail_view_container=loading_layout.findViewById(R.id.loadfail_view_container);
        loading_repeat_button=(Button)loading_layout.findViewById(R.id.loading_repeat_button);
        check_network_container = loading_layout.findViewById(R.id.check_network_container);  //实例化检查网络的布局
        bt_retry = loading_layout.findViewById(R.id.bt_retry);								  //网络 重试按钮
        loading_view_container.setVisibility(View.GONE);
        loadfail_view_container.setVisibility(View.GONE);
        tv_wrong1 = (TextView) loading_layout.findViewById(R.id.tv_wrong1);
        tv_wrong2=(TextView) loading_layout.findViewById(R.id.tv_wrong2);
        
        ll_left_fanhui = (LinearLayout) loading_layout.findViewById(R.id.ll_left_fanhui);
        if(activity.getClass()==(HomeActivity.class)){
        	ll_left_fanhui.setVisibility(View.GONE);
        }
        ll_left_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activity.finish();
				
			}
		});
        
        
        
        
        
        
        
        bt_retry.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 reload();
				
			}
		});
        loading_repeat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });

        loading_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click","click");
            }
        });
        /*
         * BUG #219
         */
        checkNetwork();
        view1 = (ImageView)loading_layout.findViewById(R.id.img1);
		view2 = (ImageView)loading_layout. findViewById(R.id.img2);
		view3 = (ImageView)loading_layout. findViewById(R.id.img3);
		view4 = (ImageView) loading_layout.findViewById(R.id.img4);
		view5 = (ImageView) loading_layout.findViewById(R.id.img5);
		
		animation1 = new TranslateAnimation(0, 0, 0,-50);  
        animation1.setInterpolator(new OvershootInterpolator ());  
        animation1.setDuration(300);  
        animation1.setRepeatCount(2);  
        animation1.setRepeatMode(Animation.REVERSE);  
       
        animation1.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				
				switch (index) {
				case 1:
					if (isStart) {
						return;
					}
					view2.setVisibility(View.VISIBLE);
					view1.setVisibility(View.INVISIBLE);
					view3.setVisibility(View.INVISIBLE);
					view4.setVisibility(View.INVISIBLE);
					view5.setVisibility(View.VISIBLE);
					index=2;
					view2.startAnimation(animation1);
					break;
				case 2:
					if (isStart) {
						return;
					}
					view3.setVisibility(View.VISIBLE);
					view1.setVisibility(View.INVISIBLE);
					view2.setVisibility(View.INVISIBLE);
					view4.setVisibility(View.INVISIBLE);
					view5.setVisibility(View.VISIBLE);
					index=3;
					view3.startAnimation(animation1);
					break;
				case 3:
					if (isStart) {
						return;
					}
					view4.setVisibility(View.VISIBLE);
					view2.setVisibility(View.INVISIBLE);
					view1.setVisibility(View.INVISIBLE);
					view3.setVisibility(View.INVISIBLE);
					view5.setVisibility(View.VISIBLE);
					index=4;
					view4.startAnimation(animation1);
					break;
				case 4:
					if (isStart) {
						return;
					}
					view1.setVisibility(View.VISIBLE);
					view2.setVisibility(View.INVISIBLE);
					view3.setVisibility(View.INVISIBLE);
					view4.setVisibility(View.INVISIBLE);
					view5.setVisibility(View.INVISIBLE);
					index=1;
					view1.startAnimation(animation1);
					break;

				default:
					break;
					
				}
				
				
			}
		});
    }
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	 MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }
    @Override
    public void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	   MobclickAgent.onPageEnd(this.getClass().getSimpleName()); 
    }
    private void checkNetwork(){
    	loadingUIHandler.sendEmptyMessage(CHECK_NET);
    }

    /**
     * 初始化自己的view
     * @return
     */
    protected abstract View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 查找控件
     * @param v
     */
    protected abstract void findViews(View v);

    /**
     * 重新加载
     */
    protected abstract void reload();

    /**
     * 开始加载
     */
    protected void startLoading(){
    	isStart=false;
    	view1.startAnimation(animation1);
        loadingUIHandler.sendEmptyMessage(LOADING_START);
    }

    /**
     * 结束加载
     */
    protected void endLoading(){
    	isStart=true;
        loadingUIHandler.sendEmptyMessage(LOADING_END);
    }

    /**
     * 加载失败
     */
    protected void failLoading(){
    	isStart=true;
        loadingUIHandler.sendEmptyMessage(LOADING_FAIL);
    }


    private final int LOADING_START=1;
    private final int LOADING_END=2;
    private final int LOADING_FAIL=3;
    private final int CHECK_NET=4;

    private Handler loadingUIHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
            	case CHECK_NET:
            			if(!NetworkUtil.isConnected(activity)){
            		        //如果在首页HomeActivity 返回键隐藏不显示
            				if(activity.getClass()==(HomeActivity.class)){
            		        	ll_left_fanhui.setVisibility(View.GONE);
                                loading_layout.setVisibility(View.VISIBLE);
                                loading_view_container.setVisibility(View.GONE);
                                loadfail_view_container.setVisibility(View.GONE);
                                check_network_container.setVisibility(View.VISIBLE);
            		        }else{
                                loading_layout.setVisibility(View.VISIBLE);
                                loading_view_container.setVisibility(View.GONE);
                                loadfail_view_container.setVisibility(View.GONE);
                                check_network_container.setVisibility(View.VISIBLE);
                                ll_left_fanhui.setVisibility(View.VISIBLE);
            		        }
            			}else{
            				startLoading();
            			}
            		break;
                case LOADING_START:{
                	loading_layout.setVisibility(View.VISIBLE);
                    loading_view_container.setVisibility(View.VISIBLE);
                    loadfail_view_container.setVisibility(View.GONE);
                    break;
                }
                case LOADING_END:{
                	loading_layout.setVisibility(View.GONE);
                    loading_view_container.setVisibility(View.GONE);
                    loadfail_view_container.setVisibility(View.GONE);
                    break;
                }
                case LOADING_FAIL:{
                    loading_layout.setVisibility(View.VISIBLE);
                    loading_view_container.setVisibility(View.GONE);
                    loadfail_view_container.setVisibility(View.VISIBLE);
                    if(activity.getClass()==(HomeActivity.class)){
                    	tv_wrong1.setText("数据加载失败");
                    	tv_wrong2.setVisibility(View.GONE);
        	        	ll_left_fanhui.setVisibility(View.GONE);
    		        }else{
    		        	tv_wrong1.setText("您的人品太好啦");
                    	tv_wrong2.setVisibility(View.VISIBLE);
                        ll_left_fanhui.setVisibility(View.VISIBLE);
    		        }
                    break;
                }
            }
        }
    };
	private View check_network_container;
	private View bt_retry;
	private Button bt_fanhui;
	private LinearLayout ll_left_fanhui;
	private TextView tv_wrong1,tv_wrong2;
	protected ProgressDialog progressDialog;
	private TranslateAnimation animation1;

}
