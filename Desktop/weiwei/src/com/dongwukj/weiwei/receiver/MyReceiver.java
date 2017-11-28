package com.dongwukj.weiwei.receiver;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.alibaba.fastjson.JSON;
import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.data.BusinessContent;
import com.dongwukj.weiwei.idea.data.PushMessage;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.ui.AppManager;
import com.dongwukj.weiwei.ui.activity.HomeActivity;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.fragment.UserCenterFragment;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;

import net.tsz.afinal.FinalDb;

import java.util.Date;
import java.util.List;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	processCustomMessage(context, bundle);
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            PushMessage pushMessage=new PushMessage();
			pushMessage.setCreateTime(new Date());
			pushMessage.setBusinessType(4);
			pushMessage.setMsgType(3);
			BusinessContent businessContent=new BusinessContent();
			businessContent.setBusinessId(0);
			businessContent.setBusinessUrl("");
			businessContent.setMsg(bundle.getString(JPushInterface.EXTRA_ALERT));
			businessContent.setTitle(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
			pushMessage.setBusinessContent(businessContent);
			//DataBase dataBase= LiteOrm.newCascadeInstance(context, BaseApplication.DB_NAME);
			//dataBase.save(pushMessage);
            FinalDb finalDb=FinalDb.create(context);
            finalDb.save(pushMessage.getData());
            if (AppManager.getInstance().getTopActivity().getClass()==HomeActivity.class) {
          	  HomeActivity activity= (HomeActivity) AppManager.getInstance().getTopActivity();
          	  if (activity.getSupportFragmentManager().findFragmentByTag("UserCenterFragment")!=null) {
          		  UserCenterFragment fragment = (UserCenterFragment) activity.getSupportFragmentManager().findFragmentByTag("UserCenterFragment");
          		  fragment.ChangeHasMessage();
          	  }
			}
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			/*PushMessage pushMessage=new PushMessage();
			pushMessage.setCreateTime(new Date());
			pushMessage.setBusinessType(4);
			pushMessage.setMsgType(3);
			BusinessContent businessContent=new BusinessContent();
			businessContent.setBusinessId(0);
			businessContent.setBusinessUrl("");
			businessContent.setMsg(bundle.getString(JPushInterface.EXTRA_ALERT));
			businessContent.setTitle(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
			pushMessage.setBusinessContent(businessContent);
			//DataBase dataBase= LiteOrm.newCascadeInstance(context, BaseApplication.DB_NAME);
			//dataBase.save(pushMessage);
            FinalDb finalDb=FinalDb.create(context);
            finalDb.save(pushMessage.getData());*/
            FinalDb finalDb=FinalDb.create(context);
        	//打开自定义的Activity
        	Intent i = new Intent();
			//FinalDb finalDb = FinalDb.create(context);
			List<UserResult> findAllByWhere = finalDb.findAllByWhere(UserResult.class, "isLogin=1");
			if(findAllByWhere!=null && findAllByWhere.size()>0){
				i.setClass(context,HomeHeaderActivity.class);
				i.putExtra("type", HeaderActivityType.MessageCenter.ordinal());
				i.putExtra("backHome",true);
			}else{
				i.setClass(context,LoginActivity.class);
				i.putExtra("homeTab",2);
			}
        	//i.putExtras(bundle);
        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
        	context.startActivity(i);
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} 
			else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}


	private void sendNotify(Context context,PushMessage pushMessage){
        if(pushMessage==null || pushMessage.getBusinessContent()==null) return;
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(pushMessage.getBusinessContent().getTitle())
				.setContentText(pushMessage.getBusinessContent().getMsg());
		mBuilder.setTicker(pushMessage.getBusinessContent().getTitle());//第一次提示消息的时候显示在通知栏上
		//mBuilder.setNumber(12);
		mBuilder.setAutoCancel(true);//自己维护通知的消失
		mBuilder.setDefaults(Notification.DEFAULT_ALL);

		//构建一个Intent
		Intent resultIntent = new Intent(context,MessageReceiver.class);
		//resultIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK  );
		/*FinalDb finalDb = FinalDb.create(context);
		List<UserResult> findAllByWhere = finalDb.findAllByWhere(UserResult.class, "isLogin=1");
		if(findAllByWhere!=null && findAllByWhere.size()>0){
			resultIntent.setClass(context,HomeHeaderActivity.class);
			resultIntent.putExtra("type", HeaderActivityType.MessageCenter.ordinal());
			resultIntent.putExtra("backHome",true);
		}else{
			resultIntent.setClass(context,HomeActivity.class);
			resultIntent.putExtra("homeTab",3);
		}*/
		//封装一个Intent
		PendingIntent resultPendingIntent = PendingIntent.getBroadcast(
				context, 0, resultIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		
		// 设置通知主题的意图
		mBuilder.setContentIntent(resultPendingIntent);
		//获取通知管理器对象
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());

	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {

		String messageContent=bundle.getString(JPushInterface.EXTRA_EXTRA);
		if(!TextUtils.isEmpty(messageContent)){
			try {
				PushMessage pushMessage= JSON.parseObject(messageContent, PushMessage.class);
				pushMessage.setCreateTime(new Date());
//				DataBase dataBase= LiteOrm.newCascadeInstance(context, BaseApplication.DB_NAME);
//				dataBase.save(pushMessage);

                FinalDb finalDb=FinalDb.create(context);
                finalDb.save(pushMessage.getData());
              if (AppManager.getInstance().getTopActivity().getClass()==HomeActivity.class) {
            	  HomeActivity activity= (HomeActivity) AppManager.getInstance().getTopActivity();
            	  if (activity.getSupportFragmentManager().findFragmentByTag("UserCenterFragment")!=null) {
            		  UserCenterFragment fragment = (UserCenterFragment) activity.getSupportFragmentManager().findFragmentByTag("UserCenterFragment");
            		  fragment.ChangeHasMessage();
            	  }
			}
				sendNotify(context,pushMessage);
			}catch (Exception e){
				Log.d("push",e.toString());
			}


		}


//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (null != extraJson && extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			context.sendBroadcast(msgIntent);
//		}
	}
}
