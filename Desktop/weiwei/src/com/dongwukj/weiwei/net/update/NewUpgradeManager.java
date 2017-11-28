package com.dongwukj.weiwei.net.update;

import java.io.File;
import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.ApkInfo;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.HomeRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.receiver.DownLoadFileService;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

/**
 * Created by sunjaly on 15/4/17.
 */
public class NewUpgradeManager {

    private Context context;
    private BaseApplication baseApplication;
    /**
     * 主动更新
     */
    private boolean isAction;

    private ProgressDialog progressDialog;
    private AlertDialog noticeDialog;

    private HttpHandler<File> downloadHandler;

    FinalDb finalDb;

    private boolean isReady=false;

    private final String apkPath= Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/weiwei_1.1/apk/";


    private ApkInfo apkInfo;


    private TextView progressText;
    private ProgressBar progressView;
    private AlertDialog downloadDialog;


    public NewUpgradeManager(Context context,BaseApplication baseApplication,boolean isAction)  {
        this.context = context;
        this.isAction=isAction;
        this.baseApplication=baseApplication;
        init();
    }

    private void init()   {
        try {
            createDir();
            finalDb=FinalDb.create(context);
            isReady=true;

        }catch (Exception e){
            isReady=false;
        }

    }

    private void createDir() throws Exception {
        try {
            File file=new File(apkPath);
            File parentFile=file.getParentFile();
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            if(!file.exists()){
                file.mkdirs();
            }
        }catch (Exception e){
                throw new Exception("cannt create file");
        }
    }

    final static int CHECK_FAIL = 0;
    final static int CHECK_SUCCESS = 1;
    final static int CHECK_NOUPGRADE = 2;
    final static int CHECK_NETFAIL = 3;
    final static int CHECK_INIT_ERROR=4;
    final static int CHECK_DOWNLOAD_FAIL=5;

    Handler updateHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            switch(msg.what){
                case CHECK_SUCCESS:{
                    Boolean isLoaded=(Boolean)msg.obj;

                  //  showNoticeDialog(isLoaded);
                    //showWin();
                    showDialogs(isLoaded);
                    break;
                }
                case CHECK_NOUPGRADE:{  //不需要更新
                    if(isAction) Toast.makeText(context, "当前版本是最新版。", Toast.LENGTH_SHORT).show();
                    break;
                }
                case CHECK_NETFAIL:{
                    if(isAction) Toast.makeText(context, "网络连接不正常。", Toast.LENGTH_SHORT).show();
                    break;
                }
                case CHECK_FAIL:{
                    if(isAction) Toast.makeText(context, "从服务器获取更新数据失败。", Toast.LENGTH_SHORT).show();
                    break;
                }
                case CHECK_INIT_ERROR:{
                    if(isAction) Toast.makeText(context, "暂时不能更新", Toast.LENGTH_SHORT).show();
                    break;
                }
                case CHECK_DOWNLOAD_FAIL:{
                    if(isAction) Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
                }

            }
        };
    };


    private boolean checkDb(){

        try{
            List<ApkInfo> list=finalDb.findAllByWhere(ApkInfo.class,""," date desc");
            if(list!=null && list.size()>0){
                ApkInfo apkInfo=list.get(0);
                File file=new File(apkInfo.getApkPath());
                if(file.exists()){
                    this.apkInfo=apkInfo;
                    return true;
                }else{
                    //清空数据库
                    finalDb.deleteAll(ApkInfo.class);
                    return false;
                }

            }
        }catch (Exception e){
            Log.e("error","error",e);
        }

        return false;
    }

    /**
     * 检查更新
     */
    public void checkVersion(){

        boolean ff=false;
        if(ff){
            updateHandler.sendEmptyMessage(CHECK_SUCCESS);
            return;
        }

        if(!isReady){
            updateHandler.sendEmptyMessage(CHECK_INIT_ERROR);
            return;
        }
        if(isAction){
            progressDialog=new ProgressDialog(this.context);
            progressDialog.setMessage("请稍后，正在检查更新...");
            
        }
        if(!NetworkUtil.isConnected(context)){
            updateHandler.sendEmptyMessage(CHECK_NETFAIL);
        }else{
            HomeRequestClient homeRequestClient=new HomeRequestClient(context,baseApplication);
            homeRequestClient.checkUpgrade(new HomeRequestClient.upgradeRequestClientCallback() {
                @Override
                protected void success(ApkInfo apkInfo1) {
                    apkInfo=apkInfo1;
                    //加载是否下载过
                    if (!apkInfo1.getIsUpdate()) {
                    	if (isAction) {
                    		Toast.makeText(context, "当前已是最新版本", Toast.LENGTH_SHORT).show();
						}
						return;
					}
                  
                    if(checkDb()){
                        //提示安装
                        updateHandler.obtainMessage(CHECK_SUCCESS,true).sendToTarget();
                    }else{
                    	if (!isAction) {
                    		if (apkInfo1.getIsForce()) {
                    			updateHandler.obtainMessage(CHECK_SUCCESS,false).sendToTarget();
							}
                    	}else {
							 updateHandler.obtainMessage(CHECK_SUCCESS,false).sendToTarget();
						}
                      
                    }
                }

                @Override
                protected void fail(ApkInfo apkInfo) {
                    updateHandler.sendEmptyMessage(CHECK_FAIL);
                }
                @Override
                protected void logOut(BaseResult result) {}
            });
        }
    }

	 protected void showDialogs(final boolean isLoad){
	    	final Dialog dialog = new Dialog(context, R.style.Dialog);
			LinearLayout view = (LinearLayout) View.inflate(context, R.layout.version_update_dialog, null);
			dialog.setContentView(view);
			dialog.setCancelable(!apkInfo.getIsForce()); //判断是否为强制跟新
			WindowManager m = ((Activity) context).getWindowManager();
		    Window dialogWindow = dialog.getWindow();
		    Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		    WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		    p.height = (int) (d.getHeight() *0.4); // 高度设置为屏幕的0.6
		    p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕的0.65
		    dialogWindow.setAttributes(p);
		    TextView version=(TextView) view.findViewById(R.id.version);
		    TextView size=(TextView) view.findViewById(R.id.size);
		    TextView rizhi=(TextView) view.findViewById(R.id.rizhi);
		    TextView tv_ok=(TextView) view.findViewById(R.id.tv_ok);
		    TextView tv_cancel=(TextView) view.findViewById(R.id.tv_cancel);
		    if (apkInfo!=null) {
		    	version.setText(apkInfo.getApkVersion()+"");
		    	size.setText(apkInfo.getApkSize());
		    	rizhi.setText(apkInfo.getApkLog());
			}else {
				return;
			}
		    if (apkInfo.getIsForce()) {
		    	tv_cancel.setVisibility(View.GONE);
			}else {
				tv_cancel.setVisibility(View.VISIBLE);
			}
		    if (isLoad) {
		    	tv_ok.setText("安装");
			}else {
				tv_ok.setText("下载");
			}
		    tv_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(isLoad){
	                    //安装
	                    installApk();
	                    if (apkInfo.getIsForce()) {
							
						}else {
							dialog.dismiss();
						}
	                }else{
	                    //下载
	                    downloadFile(true);
	                    dialog.dismiss();
	                }
					
				}
			});
		    tv_cancel.setOnClickListener(new OnClickListener() {
		    	
		    	@Override
		    	public void onClick(View v) {
		    		dialog.dismiss();
		    	}
		    });
		    dialog.show();
			 
	    }
    public void cancel(){
        if(downloadHandler!=null){
            downloadHandler.cancel(true);
        }
    }

   
    /**
     * 安装apk
     */
    private void installApk(){
       
        File file = new File(apkInfo.getApkPath());
        if(!file.exists()){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
        //清理数据
    }
    
    interface HintInstall{
    	public void hintInstall();
    }
    private HintInstall mHintInstall;
    private void preDownload(){
    	
    	
    	final Dialog dialog = new Dialog(context, R.style.Dialog);
		LinearLayout view = (LinearLayout) View.inflate(context, R.layout.upgrade_apk_layout, null);
		dialog.setContentView(view);
		dialog.setCancelable(false); //为false 按返回键不能 dismiss Dialog
		 progressText = (TextView)view.findViewById(R.id.progressCount_text);
	     progressText.setText("进度：0%");
	     progressView = (ProgressBar)view.findViewById(R.id.progressbar);
	     final TextView tv_ok=(TextView) view.findViewById(R.id.tv_ok);
	     final TextView tv_cancel=(TextView) view.findViewById(R.id.tv_cancel);
	     final LinearLayout ll_install=(LinearLayout) view.findViewById(R.id.ll_install);
		WindowManager m = ((Activity) context).getWindowManager();
	    Window dialogWindow = dialog.getWindow();
	     Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
	     WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
	        p.height = (int) (d.getHeight() * 0.25); // 高度设置为屏幕的0.6
	        p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕的0.65
	        dialogWindow.setAttributes(p);
	        tv_ok.setVisibility(View.GONE);  
	        ll_install.setVisibility(View.GONE);
	        tv_cancel.setVisibility(View.GONE);   
	        mHintInstall=new HintInstall() {
				
				@Override
				public void hintInstall() {
					ll_install.setVisibility(View.VISIBLE);
					  tv_ok.setVisibility(View.VISIBLE); 
					  tv_ok.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							if (apkInfo.getIsForce()) {
								 installApk();
							}else {
								 installApk();
								 dialog.dismiss();
							}
						}
					});
					if (!apkInfo.getIsForce()) {
						tv_cancel.setVisibility(View.VISIBLE); 
						tv_cancel.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});
					}
				}
			};
		dialog.show();
		
    
    	
    	
    	
    
    }

    /**
     * 下载文件逻辑
     * @param isShow  true:静默下载 false 直接下载
     */
    private void downloadFile(final boolean isShow){
        if(noticeDialog!=null && noticeDialog.isShowing()){
            noticeDialog.dismiss();
        }
        String version="1";
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			
			
			version=info.versionCode+"";
		} catch (NameNotFoundException e1) {
			
		}
        FinalHttp finalHttp=new FinalHttp(version);
        String targetPath=apkPath+apkInfo.getApkName()+".apk";
        if(isShow){
            preDownload();
        }
        downloadHandler= finalHttp.download(apkInfo.getDownloadUrl(), targetPath, false, new AjaxCallBack<File>() {
            @Override
            public boolean isProgress() {
                return super.isProgress();
            }

            @Override
            public int getRate() {
                return super.getRate();
            }

            @Override
            public AjaxCallBack<File> progress(boolean progress, int rate) {
                return super.progress(progress, rate);
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
                if(isShow){
                    float progress=((float)100*current/count);
                    progressText.setText("进度："+String.format("%.2f",progress)+"%");
                    progressView.setProgress((int)progress);
                }

            }

            @Override
            public void onSuccess(File file) {
                super.onSuccess(file);
                finalDb.deleteAll(ApkInfo.class);
                apkInfo.setApkPath(file.getAbsolutePath());
                finalDb.save(apkInfo);
                if (mHintInstall!=null) {
                	mHintInstall.hintInstall();
				}
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                updateHandler.sendEmptyMessage(CHECK_DOWNLOAD_FAIL);
            }
        });
    }
}
