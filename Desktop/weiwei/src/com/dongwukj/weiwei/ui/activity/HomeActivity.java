package com.dongwukj.weiwei.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.adapter.FragmentTabAdapter;
import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.update.NewUpgradeManager;
import com.dongwukj.weiwei.net.update.UpgradeManager;
import com.dongwukj.weiwei.ui.AppManager;
import com.dongwukj.weiwei.ui.fragment.ClassifyFragment;
import com.dongwukj.weiwei.ui.fragment.NewClassifyFragment;
import com.dongwukj.weiwei.ui.fragment.NewHomeFragment;
import com.dongwukj.weiwei.ui.fragment.ShoppingCarFragment;
import com.dongwukj.weiwei.ui.fragment.UserCenterFragment;
import com.dongwukj.weiwei.ui.widget.BadgeView;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;

/**
 * Created by pc on 2015/3/17.
 */
public class HomeActivity extends BaseActivity {

    private RadioGroup rgs;
    public List<Fragment> fragments = new ArrayList<Fragment>();
    private FragmentTabAdapter tabAdapter;
	private BadgeView radioButtonBadgeView;
	private DataBase db;
	private int index;
	
	public void setBadgeViewText(String text){
		
		if (Integer.parseInt(text)>=99) {
			radioButtonBadgeView.setTextSize(9);
			radioButtonBadgeView.setText("99+");
		}else {
			radioButtonBadgeView.setTextSize(12);
			radioButtonBadgeView.setText(text);
		}
		radioButtonBadgeView.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
		if (!text.equals("0")) {
			radioButtonBadgeView.show();
		}else {
			radioButtonBadgeView.hide();
		}
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}
	@Override
    protected void onResume() {
		
		if (baseApplication!=null&&baseApplication.getUserResult()!=null) {
			setBuycount(getCount());
		}else {
			getUserinfo();
			if (baseApplication.getUserResult()!=null) {
				setBuycount(getCount());
			}
		}
    	super.onResume();
        Log.d("activity","resume");
	}
   
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	public void setBuycount(int count){
		setBadgeViewText(count+"");
	}
	public int getCount(){
		if (baseApplication==null||baseApplication.getUserResult()==null) {
			getUserinfo();
		}
		int num=0;
    	QueryBuilder qb=new QueryBuilder(NewHomeEntity.class);
    	qb.where("userAccount=? and marketId=?", new Integer[]{Integer.parseInt(baseApplication.getUserResult().getUserAccount()),baseApplication.getUserResult().getMarketId()});
		ArrayList<NewHomeEntity> arrayList = db.query(qb);
		for (NewHomeEntity newHomeEntity : arrayList) {
			num=num+newHomeEntity.getCount();
		}
		return arrayList.size();
	}
     private void getUserinfo() {
		FinalDb db=FinalDb.create(this);
		List<UserResult> list = db.findAllByWhere(UserResult.class, "isLogin='1'");
		if (list.size()==1) {
			UserResult result = list.get(0);
			baseApplication=(BaseApplication) getApplication();
			baseApplication.setUserResult(result);
		}
	}
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent!=null){
        	index = intent.getIntExtra("homeTab",-1);
            if(index!=-1 && tabAdapter!=null){
            	tabAdapter.showTabByIndex(index);
            
            }
        }

    }
    	
    public void showTabByIndex(int index){
    	if(index!=-1 && tabAdapter!=null){
            tabAdapter.showTabByIndex(index);
        }
    }
    
    @Override
    protected void findViewById() {
    	
    	
        //fragments.add(new HomeFragment());
        fragments.add(new NewHomeFragment());
        fragments.add(new ClassifyFragment());
        //fragments.add(new NewClassifyFragment());

        fragments.add(new ShoppingCarFragment());
       fragments.add(new UserCenterFragment());
        //fragments.add(new ComboFragment());
        
       
        rgs = (RadioGroup) findViewById(R.id.tabs_rg);
       Button radioButton = (Button) findViewById(R.id.bt);
       RadioButton radioButton2=(RadioButton) findViewById(R.id.tab_rb_d);
       	radioButtonBadgeView=new BadgeView(getApplicationContext(),radioButton);
    	radioButtonBadgeView.setTextColor(Color.WHITE);
    	radioButtonBadgeView.setTextSize(10);
        tabAdapter = new FragmentTabAdapter(this, fragments, R.id.tab_content, rgs,index);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener(){
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                //System.out.println("Extra---- " + index + " checked!!! ");
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.main_activity);
        /*
         * BUG #219    判断有没有网络,
         */
       // application = (BaseApplication) getApplication();
       NewUpgradeManager upgradeManager=new NewUpgradeManager(this,baseApplication,false);
        upgradeManager.checkVersion();
        db = LiteOrm.newCascadeInstance(getApplicationContext(), baseApplication.DB_NAME);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            showExitSystem();
        }
        return true;
    }

    public void showExitSystem() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        BitmapDrawable drawable=(BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher_small);
        
        builder.setIcon(drawable)
                .setTitle("惟惟")
                .setMessage("是否退出应用")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	MobclickAgent.onKillProcess(getApplicationContext());
                        AppManager.getInstance().AppExit(getApplicationContext());
                      //  android.os.Process.killProcess(android.os.Process.myPid());
                    	
                        System.exit(0);
                    }
                })
                .setPositiveButton("取消",null )
                .show();
    }
}
