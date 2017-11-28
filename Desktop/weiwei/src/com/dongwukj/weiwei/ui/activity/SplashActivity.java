package com.dongwukj.weiwei.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;



import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.receiver.DownLoadFileService;
import com.umeng.analytics.MobclickAgent;

import net.tsz.afinal.FinalDb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by pc on 2015/3/16.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void findViewById() {
    	img = (ImageView) findViewById(R.id.splash_imageview);
    	isFirstLogin = sp.getBoolean("IsFirstLogin", true);
        if (isFirstLogin) {
			img.setBackgroundResource(R.drawable.weiwei_splash);
			Editor edit = sp.edit();
			edit.putBoolean("IsFirstLogin", false).commit();
		}else {
			int photoId = sp.getInt("photoId", 0);
			//不是第一次登录，但是没有从网络获取到图片
			if (photoId==0) {
				img.setBackgroundResource(R.drawable.weiwei_splash);
			}else {
				try {
					Bitmap  drawable = getBitmap(photoId);
					if (drawable==null) {
						img.setBackgroundResource(R.drawable.weiwei_splash);
					}else {
						img.setImageBitmap(drawable);
					}
				} catch (Exception e) {
					img.setBackgroundResource(R.drawable.weiwei_splash);
				}
			}
		}
        Intent intent=new Intent(getApplicationContext(), DownLoadFileService.class);
        startService(intent);
        startHandler.sendEmptyMessageDelayed(100,1500);
        
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
    protected void initView() {
		MobclickAgent.updateOnlineConfig( getApplicationContext() );
		MobclickAgent.openActivityDurationTrack(false);
        setContentView(R.layout.splash_activity);
        baseApplication = (BaseApplication) getApplication();
        sp = getSharedPreferences("config", 0);
        //获取user信息
      getUserinfo(); 
  }

    private Handler startHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isFirstLogin) {
            	 Intent intent=new Intent(SplashActivity.this,GuideActivity.class);
                 startActivity(intent);
			}else {
				if (baseApplication.getUserResult()==null||!baseApplication.getUserResult().isLogin()) {
					Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
			        startActivity(intent);
				}
				else {
					Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
			        startActivity(intent);
				}
			}
           finish();
        }
    };
	private ImageView img;
	private BaseApplication baseApplication;
	private SharedPreferences sp;
	private boolean isFirstLogin;


	private Bitmap getBitmap(int photoId) {
		Bitmap bitmap;
		try {
			File file=new File(getCacheDir(), photoId+"");
			FileInputStream inputStream=new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(inputStream);
		} catch (Exception e) {
			bitmap=null;
		}
		return bitmap;
		
	}

	
}
