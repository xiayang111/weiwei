package com.dongwukj.weiwei.receiver;

import java.util.List;

import net.tsz.afinal.FinalDb;

import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.ui.activity.HomeActivity;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MessageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent resultIntent = new Intent();
		resultIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK  );
		FinalDb finalDb = FinalDb.create(context);
		List<UserResult> findAllByWhere = finalDb.findAllByWhere(UserResult.class, "isLogin=1");
		if(findAllByWhere!=null && findAllByWhere.size()>0){
			resultIntent.setClass(context,HomeHeaderActivity.class);
			resultIntent.putExtra("type", HeaderActivityType.MessageCenter.ordinal());
			resultIntent.putExtra("backHome",true);
		}else{
			resultIntent.setClass(context,LoginActivity.class);
			resultIntent.putExtra("homeTab",2);
		}
		context.startActivity(resultIntent);
	}

}
