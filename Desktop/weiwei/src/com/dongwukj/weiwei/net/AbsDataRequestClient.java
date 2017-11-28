package com.dongwukj.weiwei.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;
import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import net.tsz.afinal.FinalDb;

import java.util.List;

/**
 * Created by sunjaly on 15/4/13.
 */
public abstract class AbsDataRequestClient {
    protected Context context;
    protected BaseRequestClient baseRequestClient;
    protected BaseApplication baseApplication;
    protected UserResult userResult;
    public ProgressDialog progressDialog;

    public AbsDataRequestClient(Context context,BaseApplication baseApplication){
        this.context=context;
        this.baseApplication=baseApplication;
        baseRequestClient=new BaseRequestClient(context);
        userResult = this.baseApplication.getUserResult();
        if (context!=null) {
        	progressDialog=new ProgressDialog(this.context);
        	// progressDialog.setTitle("提示");
             progressDialog.setCancelable(false);
        }
       
    }

    public void showProgress(boolean isShow){
        if(isShow){
            if(!progressDialog.isShowing()){
                progressDialog.show();
            }
        }else{
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }

    public <T extends BaseResult> void fetchDate(String enterPoint,final boolean isShowDialog, BaseRequest request,Class<T> clazz,final AbsRequestClientCallback callback){
        if(isShowDialog){
            showProgress(true);
        }

        baseRequestClient.httpPostByJsonNew(enterPoint, userResult, request, clazz, new BaseRequestClient.RequestClientNewCallBack<T>() {
            @Override
            public void callBack(T result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.success(result);

                    } else {
                        callback.fail(result);
                        Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    callback.fail(result);
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
                callback.complete(result);
                if (isShowDialog) {
                    showProgress(false);
                }
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(T result) {
				callback.logOut(result);
			}


        });

    }


    public <T extends BaseResult> void fetchDate(String enterPoint, final BaseRequest request,Class<T> clazz,final AbsRequestClientCallback callback){

        fetchDate(enterPoint,false,request,clazz,callback);

    }





}
