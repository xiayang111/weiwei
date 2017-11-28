package com.dongwukj.weiwei.ui.activity;


import java.util.List;

import net.tsz.afinal.FinalDb;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.ui.AppManager;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends FragmentActivity implements OnClickListener{

	public static final String TAG = BaseActivity.class.getSimpleName();

	protected Handler mHandler = null;
	public BaseApplication baseApplication;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		baseApplication=(BaseApplication)getApplication();
		baseApplication.crashHandler.setUpdateActivity(this);
		if(savedInstanceState!=null){
			getUserinfo();
			if (baseApplication.getUserResult()!=null) {
				if (this.getClass()!=(HomeActivity.class)) {
					Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
					intent.putExtra("homeTab",0);
					startActivity(intent);
				}
			}
			
//			Intent intent=new Intent(getApplicationContext(),SplashActivity.class);
//			startActivity(intent);
			
		/*	if (this.getClass()!=(LoginActivity.class)) {
				FinalDb finalDb=FinalDb.create(this);
				List<UserResult> findAllByWhere = finalDb.findAllByWhere(
						UserResult.class, "isLogin=1");
				for (UserResult userResult : findAllByWhere) {
					userResult.setLogin(false);
					finalDb.update(userResult);
				}
				baseApplication.setUserResult(null);
				Intent intent = new Intent(this, LoginActivity.class);
				//intent.putExtra("isLoginOut", true);
				startActivity(intent);
			}*/
			Log.e("error", "error..............onCreate");
			
		}
		AppManager.getInstance().addActivity(this);
		initView();
		findViewById();
		//EventBus.getDefault().register(this);
		
	}
	@Override
	protected void onDestroy() {
		//EventBus.getDefault().unregister(this);
		super.onDestroy();
		AppManager.getInstance().killActivity(this);
	}
	public void onEventMainThread(String type){}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		Log.e("error", "--------onSaveInstanceState");
		outState=new Bundle();
		
		super.onSaveInstanceState(outState);
	}
	
	 private void getUserinfo() {
			FinalDb db=FinalDb.create(this);
			List<UserResult> list = db.findAllByWhere(UserResult.class, "isLogin='1'");
			if (list.size()==1) {
				UserResult result = list.get(0);
				baseApplication.setUserResult(result);
			}
		}
	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
		   MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
		MobclickAgent.onPause(this);
	}

	@Override
	public void finish() {
		//AppManager.getInstance().killActivity(this);
		super.finish();
	}

	/**
	 * 绑定控件id
	 */
	protected abstract void findViewById();

	/**
	 * 初始化控件
	 */
	protected abstract void initView();

	/**
	 * 通过类名启动Activity   nbnm
	 * 
	 * @param pClass
	 */
	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	/**
	 * 通过类名启动Activity，并且含有Bundle数据
	 * 
	 * @param pClass
	 * @param pBundle
	 */
	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}
	
	/**
	 * 通过Action启动Activity
	 * 
	 * @param pAction
	 */
	protected void openActivity(String pAction) {
		openActivity(pAction, null);
	}
	public  void showToast(String message,Context context){
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 通过Action启动Activity，并且含有Bundle数据
	 * 
	 * @param pAction
	 * @param pBundle
	 */
	protected void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}
	
	protected void DisPlay(String content){
		Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
	}
	/**点击事件*/
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	/**加载进度条*/
	public void showProgressDialog() {
		ProgressDialog progressDialog = null;
		
		if(progressDialog!=null){
			progressDialog.cancel();
		}
		progressDialog=new ProgressDialog(BaseActivity.this);
		Drawable drawable=getResources().getDrawable(R.drawable.loading_animation);
		progressDialog.setIndeterminateDrawable(drawable);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.setMessage("请稍候，正在努力加载。。");
		progressDialog.show();
	}

}
 