package com.dongwukj.weiwei.receiver;

import java.io.File;
import java.util.List;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.LocationEntity;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.FirstpageEntity;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.activity.SplashActivity;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;

public class DownLoadFileService extends Service {

	private SharedPreferences sp;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	@Override
	public void onStart(Intent intent, int startId) {
		sp = getSharedPreferences("config", 0);
		BaseApplication application = (BaseApplication) getApplication();
		getPhoto(application);
	}
	/*private LocationEntity getLocationEntity(){
		LocationEntity lEntity=new LocationEntity();
		double[] ds = NetworkUtil.getGpsInfo(this);
		lEntity.setLat(ds[0]);
		lEntity.setLng(ds[1]);
		return lEntity;
	}*/
	protected void deletefire(int photoId) {
		File file=new File(getCacheDir(), photoId+"");
		if (file.exists()) {
			 file.delete();
		}
	}
	private void getPhoto(final BaseApplication baseApplication){
		final BaseRequestClient client=new BaseRequestClient(this);
		UserResult userResult=baseApplication.getUserResult();
		LocationEntity entity = new LocationEntity();
		entity.setLat(0);
		entity.setLng(0);
		client.httpPostByJson("PhoneStart", userResult, entity, FirstpageEntity.class, new BaseRequestClient.RequestClientCallBack<FirstpageEntity>() {
			@Override
			public void callBack(FirstpageEntity result) {
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						final int i = result.getStartPageiId();
						int i1 = sp.getInt("photoId", 0);
						if (i==i1) {
							return;	
						}
						deletefire(sp.getInt("photoId", 0));
						
						if (TextUtils.isEmpty( result.getStartpageurl())) {
							sp.edit().putInt("photoId", 0).commit();
							return;
						}
						File file=new File(getCacheDir(), i+"");
					    String version="1";
						try {
							PackageInfo info = DownLoadFileService.this.getPackageManager().getPackageInfo(DownLoadFileService.this.getPackageName(), 0);
							
							
							version=info.versionCode+"";
						} catch (NameNotFoundException e1) {
							
						}
						FinalHttp finalHttp=new FinalHttp(version);
						finalHttp.download(result.getStartpageurl(), file.getPath(), true, new AjaxCallBack<File>() {
							@Override
							public void onSuccess(File t) {
								sp.edit().putInt("photoId", i).commit();
								stopSelf();
							}
						});

					}
				}
				
			}
			@Override
			public void loading(long count, long current) {
				 // startHandler.sendEmptyMessageDelayed(100,1000);
			}
			@Override
			public void logOut(boolean isLogOut,FirstpageEntity result) {/*FinalDb finalDb=FinalDb.create(DownLoadFileService.this);
			List<UserResult> findAllByWhere = finalDb.findAllByWhere(
					UserResult.class, "isLogin=1");
			for (UserResult userResult : findAllByWhere) {
				userResult.setLogin(false);
				finalDb.update(userResult);
			}
			baseApplication.setUserResult(null);
			Intent intent = new Intent(BaseApplication.getContext(), LoginActivity.class);
			intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK  );
			intent.putExtra("isLoginOut", true);
			startActivity(intent);*/}
		});
	
	}
}
