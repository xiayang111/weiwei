package com.dongwukj.weiwei.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;

import com.dongwukj.weiwei.BaseApplication;




import com.dongwukj.weiwei.R;







import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.AppManager;
import com.dongwukj.weiwei.ui.activity.HomeActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;






/**
 * 程序崩溃处理线程<br>
 * 当程序发生崩溃的时候,有该类来接管程序,并记录发送错误报告
 * @author 祁毅
 */
public class ICECrashHandler implements UncaughtExceptionHandler{
	private static ICECrashHandler instance;
	private String sDebugCrash;
	private Activity updateActivity = null;
;
	  private Thread.UncaughtExceptionHandler mDefaultHandler;
	 public static ICECrashHandler getInstance() {      
	        if(instance == null)  
	            instance = new ICECrashHandler();     
	        return instance;      
	    }  
	  public void init() {    
	        //获取系统默认的UncaughtException处理器      
	        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();      
	        //设置该CrashHandler为程序的默认处理器      
	        Thread.setDefaultUncaughtExceptionHandler(this);      
	    }  
	  public void setUpdateActivity(Activity activity){
	    	this.updateActivity = activity;
	    	
	    }
	  
	 
@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		StringWriter stackTrace = new StringWriter();
    	ex.printStackTrace(new PrintWriter(stackTrace));
    	sDebugCrash = stackTrace.toString();
    	//PhoneCollapseLog(updateActivity,sDebugCrash);
    	
    	new Thread(new Runnable() {			
			@Override
			public void run() { 
				Looper.prepare();
				if(updateActivity != null) {
					
					//PhoneCollapseLog(updateActivity,sDebugCrash);
					//mHandler.sendEmptyMessage(100);
					/*int height = ICESystem.GetScreenOrientation(updateActivity.getResources().getConfiguration().orientation).y;
					RelativeLayout rltMain = new RelativeLayout(updateActivity);
					RelativeLayout.LayoutParams rlp = 
    		    			new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, (int)(height * 0.8));
					View vView = LayoutInflater.from(updateActivity).inflate(R.layout.ice_framework_crash_debug, null);
					TextView tv = (TextView) vView.findViewById(R.id.txtExceptionInfo);
					tv.setText(sDebugCrash);
					rltMain.addView(vView, rlp);
					ICEMessagebox.showViewBox(updateActivity, 
    						"崩溃日志", 
    						rltMain);*/
				}
	        	//锁死对话
	        	try {		
    				Looper.getMainLooper();
    				Looper.loop();
    			}
    			catch(RuntimeException e){
    				//ICELog.e(TAG, e.toString());
    			}		 
    			Looper.loop();
			}

			
    	}).start();
    	PhoneCollapseLog(updateActivity,sDebugCrash);
    	BaseApplication application=(BaseApplication) updateActivity.getApplication();
    	if (thread.getId()!=1) {
    		thread.interrupt();
    		AppManager.getInstance().killAllActivity();
    		if (application!=null&&application.getUserResult()!=null) {
			Intent intent = new Intent(updateActivity, HomeActivity.class);
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        intent.putExtra("homeTab",0);
	        updateActivity.startActivity(intent); } 
		}else {
			AppManager.getInstance().killAllActivity();
			if (application!=null&&application.getUserResult()!=null) {
				Intent intent = new Intent(updateActivity, HomeActivity.class);
			    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    intent.putExtra("homeTab",0);
			    updateActivity.startActivity(intent);      
			}
		}
    	AppManager.getInstance().AppExit(updateActivity);
	}

	private void PhoneCollapseLog(final Activity updateActivity2, String sDebugCrash2) {
		PhoneCollapseLogRequest request=new  PhoneCollapseLogRequest();
	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String time = dateFormat.format(System.currentTimeMillis());
	String version=Build.VERSION.SDK;
	String PhoneType=Build.VERSION.RELEASE;
	String name=Build.MODEL;
	request.setPhoneName(name);
	request.setCollapseLog(sDebugCrash2);
	request.setCollapseTime(time);
	request.setPhoneType(PhoneType);
	request.setPhoneVersion(version);
	BaseRequestClient client=new BaseRequestClient(updateActivity2);
	BaseApplication application=(BaseApplication) updateActivity2.getApplication();
	
	client.httpPostByJsonNew("PhoneCollapseLog", application.getUserResult(), request, BaseResult.class, new BaseRequestClient.RequestClientNewCallBack<BaseResult>() {

		@Override
		public void callBack(BaseResult result) {
			
		}

		@Override
		public void loading(long count, long current) {
			
		}

		@Override
		public void logOut(BaseResult result) {
			
		}
	});
	//AppManager.getInstance().AppExit(updateActivity2);
	/*Intent resultIntent = new Intent();
	resultIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK  );
	resultIntent.setClass(updateActivity2,LoginActivity.class);
	resultIntent.putExtra("homeTab",2);
	updateActivity2.startActivity(resultIntent);*/
	}
	/*@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		updateActivity.finish();
	}*/
	
	
	
	
	/*
	 public void uncaughtException(Thread thread, Throwable ex) {
	        System.out.println("system wrong....");
	        // MBOPApplication app=(MBOPApplication) mainContext;
	        // app.setNeed2Exit(true);
	        //异常信息收集
	        collectCrashExceptionInfo(thread, ex);
	        //应用程序信息收集
	        collectCrashApplicationInfo(app);
	        //保存错误报告文件到文件。
	        saveCrashInfoToFile(ex);
	        //MBOPApplication.setCrash(true);
	        //判断是否为UI线程异常，thread.getId()==1 为UI线程
	        if (thread.getId() != 1) {
//	            System.out.println("Exception ThreadId" + thread.getId());
	            thread.interrupt();
	            //TODO 跳转到IndexActivity
	            System.out.println("Thread ID--->" + Thread.currentThread().getId());
//	            Intent intent =new Intent(mainContext,IndexActivity.class);
//	            actContext.startActivity(intent);
	            //弹出对话框提示用户是否上传异常日志至服务器
	            new Thread(){
	                public void run() {}{                                        
	                    Looper.prepare();
	                    new AlertDialog.Builder(app.getCurrentAct()).setTitle("异常处理").setMessage("您的程序出现异常，是否将异常信息上传至服务器?")
	                    .setPositiveButton("是", new OnClickListener() {
	                        
	                        public void onClick(DialogInterface dialog, int which) {
	                            new Thread(new Runnable() {
	                                
	                                @Override
	                                public void run() {
	                                    sendCrashReportsToServer(app,false);
	                                    
	                                }
	                            }).start();
//	                            new Thread(){
//	                                public void run() {}{    
//	                                    try{
//	                                        System.out.println("执行上传线程ID"+this.getId());
//	                                        this.sleep(5000);
//	                                    }catch(Exception e){
//	                                        
//	                                    }
//	                                    sendCrashReportsToServer(app);
//	                                }
//	                            }.start();                                    
	                        }
	                    }).setNegativeButton("否", new OnClickListener() {
	                        
	                        @Override
	                        public void onClick(DialogInterface dialog, int which) {
	                            
	                        }
	                    }).create().show();
	                    Looper.loop();                                 
	                }
	            }.start();
	            
	        } else {

//	            UserSessionCache usc=UserSessionCache.getInstance();
//	            ObjectOutputStream oos=null;
//	            try {
//	                oos.writeObject(usc);
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	            }
//	            SharedPreferences prefenPreferences = mainContext
//	            .getSharedPreferences("IsMBOPCrash",Activity.MODE_PRIVATE);
//	            SharedPreferences.Editor editor = prefenPreferences.edit();
//	            editor.clear();
//	            editor.putBoolean("ISCRASH", true);
//	            editor.commit();
	            
	            // 方案一:将所有Activity放入Activity列表中，然后循环从列表中删除，即可退出程序

	            for (int i = app.getActivityList().size()-1; i >=0; i--) {
	                Activity act = app.getActivityList().get(i);
	                act.finish();
	            }
	            CoreCommonMethod.setCrash(app, true);
	            Intent intent = new Intent(app, WelcomeActivity.class);
	            intent.putExtra(WelcomeActivity.EXTRA_DIRECT_TO_INDEX, true);
	            intent.putExtra(WelcomeActivity.EXTRA_USERINFO, UserSessionCache.getInstance().getUserInfo());
	            intent.putExtra(WelcomeActivity.EXTRA_CURRENT_PORTAL_ID, UserSessionCache.getInstance().getCurrentPortalId());
//	            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//	            app.startActivity(intent);                     
	            android.os.Process.killProcess(android.os.Process.myPid());  
	            
	            //方案二：直接使用ActivityManager的restartPackage方法关闭应用程序，
	            //此方法在android2.1之后被弃用，不起作用
//	            ActivityManager am = (ActivityManager) mainContext.getSystemService(Context.ACTIVITY_SERVICE);
//	            am.restartPackage(mainContext.getPackageName());

	        }
	    }*/


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
	