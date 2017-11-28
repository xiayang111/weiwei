package com.dongwukj.weiwei.test;



import com.dongwukj.weiwei.BaseApplication;

import android.R.integer;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;

/**
 * ICE系统设置
 * @author 祁毅
 */
public class ICESystem {
	/**
	 * 获取屏幕密度
	 * @return 屏幕密度
	 */
	public static float getDensity() {
		return BaseApplication.getContext().getResources().getDisplayMetrics().density; 
	}
	
	/**
	 * 获取当前网络状态
	 * @return 网络状态（-1：没有网络，0：未知网络，1：移动网络，2：Wifi网络）
	 */
	public static int getNetworkState(){
		int selState = -1;
		ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        
//        && !networkInfo.isConnectedOrConnecting()
        if(networkInfo == null)
        	return selState;
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
        	selState = 1;
        } else if(nType == ConnectivityManager.TYPE_WIFI) {
        	selState = 2;
        } else {
        	selState = 0;
        }
        return selState;
	}
	
	/**
	 * 获取屏幕可显示宽高(包括进度条,不包括虚拟按钮)
	 * @param nOrientation 横屏为0,竖屏为1
	 * @return 返回x为宽y为高
	 */
	public static Point GetScreenOrientation(int nOrientation) {
		int nWidth = BaseApplication.getContext().getResources().getDisplayMetrics().widthPixels;
		int nHeight = BaseApplication.getContext().getResources().getDisplayMetrics().heightPixels;
    	Point ptScreen = new Point();
    	if (0 == nOrientation) {
    		ptScreen.x = Math.max(nWidth, nHeight);
    		ptScreen.y = Math.min(nWidth, nHeight);
    	}
    	else {
    		ptScreen.x = Math.min(nWidth, nHeight);
    		ptScreen.y = Math.max(nWidth, nHeight);
		}
    	return ptScreen;
	}

	/**
	 * 获取布局正确的宽高
	 * @param vfFrame 布局对象
	 * @param nOrientation 横屏为0,竖屏为1
	 * @return 返回x为宽y为高
	 */
	public static Point GetLayoutOrientation(ViewGroup vgFrame,int nOrientation) {
    	int nWidth = vgFrame.getWidth(); 
    	int nHeight = vgFrame.getHeight(); 
    	Point ptScreen = new Point();
    	if (0 == nOrientation) {
    		ptScreen.x = Math.max(nWidth, nHeight);
    		ptScreen.y = Math.min(nWidth, nHeight);
    	}
    	else {
    		ptScreen.x = Math.min(nWidth, nHeight);
    		ptScreen.y = Math.max(nWidth, nHeight);
		}
    	return ptScreen;
	}
	
	/**
	 * 获取测量布局宽高
	 * @param child 布局对象
	 * @return 返回x为宽y为高
	 */
	public static Point GetMeasureView(LinearLayout child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
		Point ptPoint = new Point();
		ptPoint.x = child.getMeasuredWidth();
		ptPoint.y = child.getMeasuredHeight();
		return ptPoint;
	}
	
	/**
	 * 获取手机IMEI
	 * @return IMEI
	 */
	public static String getImei(){
		TelephonyManager tm = (TelephonyManager) BaseApplication.getContext().getSystemService(BaseApplication.getContext().TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	
	/**
	 * 获取App是否在前台运行
	 * @return true:前台运行，false：不在前台运行
	 */
	public static boolean isRunningForeground ()  
	{  
	    ActivityManager am = (ActivityManager)BaseApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);  
	    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;  
	    String currentPackageName = cn.getPackageName();  
	    if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(BaseApplication.getContext().getPackageName()))  
	    {  
	        return true ;  
	    }  
	    return false ;  
	}  
}
