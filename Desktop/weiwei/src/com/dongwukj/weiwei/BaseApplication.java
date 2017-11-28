package com.dongwukj.weiwei;

import java.util.Calendar;
import java.util.HashSet;

import android.app.AlertDialog;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.dongwukj.weiwei.idea.result.AreaEntity;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.utils.NetworkUtil;



/**
 * Created by pc on 2015/3/16.
 */
public class BaseApplication extends Application {

	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	private static UserResult userResult;
    private static int cartCount;
    private static Context context;
    private static final String TAG = "JPush";
    public static final String DB_NAME = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/vvlife_1.1/orm/cascade.db";
            //"weiwei.db";

    public static final String BASE_IMAGE_HOST = "http://www.vvlife.com";
    public static com.dongwukj.weiwei.test.ICECrashHandler crashHandler = null;
    
    public int getCartCount() {
		return cartCount;
	}

	public void setCartCount(int cartCount) {
		this.cartCount = cartCount;
	}

	public static Context getContext() {
		return context;
	}
	public long getWeeTimew(){
		Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
       return cal.getTimeInMillis();
	}
	@Override
    public void onCreate() {
    	
        Log.d(TAG, "[ExampleApplication] onCreate");
        super.onCreate();
        context=this;
        crashHandler = com.dongwukj.weiwei.test.ICECrashHandler.getInstance();  
		crashHandler.init(); 
//
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        HashSet<String> tags=new HashSet<String>();
        tags.add("42");
        JPushInterface.init(this);     		// 初始化 JPush
        JPushInterface.setTags(this, tags, null);
        //location();
        
        
    }
	public void onTerminate() {
		super.onTerminate();
		System.exit(0);
    }
	
		
 
    
	private void location() {
    	mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		initLocation();
		mLocationClient.start();
		
	}

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			AreaEntity areaentity = AreaEntity.getInstance();
			String province = location.getProvince();
			String city = location.getCity();
			String district = location.getDistrict();
			String street = location.getStreet();
			String streetNumber = location.getStreetNumber();
			
			areaentity.setProvince(province);
			areaentity.setCity(city);
			areaentity.setArea(district);
			areaentity.setStreet(street);
			areaentity.setStreetnumber(streetNumber);
			
			mLocationClient.stop();
		}


	}
	
	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
		option.setCoorType("gcj02");//返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(1000);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

    public UserResult getUserResult() {
        return userResult;
    }

    public void setUserResult(UserResult userResult) {
        this.userResult = userResult;
    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
        
        System.exit(0);
    }



}
