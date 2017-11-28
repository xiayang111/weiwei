package com.dongwukj.weiwei.ui;

import java.util.Stack;

import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.SystemClock;

public class AppManager {
	private static Stack<Activity> mActivityStack;
	public static Stack<Activity> getmActivityStack() {
		return mActivityStack;
	}

	public static void setmActivityStack(Stack<Activity> mActivityStack) {
		AppManager.mActivityStack = mActivityStack;
	}

	private static AppManager mAppManager;

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getInstance() {
		if (mAppManager == null) {
			mAppManager = new AppManager();
		}
		return mAppManager;
	}

	/**
	 * 添加Activity到堆�?
	 */
	public void addActivity(Activity activity) {
		if (mActivityStack == null) {
			mActivityStack = new Stack<Activity>();
		}
		mActivityStack.add(activity);
	}

	/**
	 * 获取栈顶Activity（堆栈中�?���?��压入的）
	 */
	public Activity getTopActivity() {
		Activity activity = mActivityStack.lastElement();
		return activity;
	}

	/**
	 * 结束栈顶Activity（堆栈中�?���?��压入的）
	 */
	public void killTopActivity() {
		Activity activity = mActivityStack.lastElement();
		killActivity(activity);
	}

	/**
	 * 结束指定的Activity	
	 */
	public void killActivity(Activity activity) {
		if (activity != null) {
			mActivityStack.remove(activity);
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void killActivity(Class<?> cls) {
		for (Activity activity : mActivityStack) {
			if (activity.getClass().equals(cls)) {
				killActivity(activity);
			}
		}
	}
	
	/**
	 * 结束所有的Activity
	 */
	public void killAllActivity() {
		/*for (int i = 0, size = mActivityStack.size(); i < size; i++) {
			if (null != mActivityStack.get(i)) {
				mActivityStack.get(i).finish();
			}
		}*/
		for (int i = mActivityStack.size()-1; i >-1 ; i--) {
			if (null != mActivityStack.get(i)) {
				mActivityStack.get(i).finish();
			}
		}
		mActivityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			killAllActivity();
//			ActivityManager activityMgr = (ActivityManager) context
//					.getSystemService(Context.ACTIVITY_SERVICE);
//			activityMgr.restartPackage(context.getPackageName());
//			activityMgr.killBackgroundProcesses(context.getPackageName());
			//android.os.Process.killProcess(android.os.Process.myPid());
			MobclickAgent.onKillProcess(context);
			System.exit(0);
		} catch (Exception e) {
		}
	}
}
