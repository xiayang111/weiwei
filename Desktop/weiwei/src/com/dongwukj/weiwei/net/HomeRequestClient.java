package com.dongwukj.weiwei.net;

import android.content.Context;
import android.widget.Toast;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.*;
import com.dongwukj.weiwei.idea.result.*;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.tsz.afinal.FinalDb;

import java.util.List;

/**
 * Created by sunjaly on 15/4/13.
 */
public class HomeRequestClient extends AbsDataRequestClient {

    public HomeRequestClient(Context context, BaseApplication baseApplication) {
        super(context, baseApplication);

    }


    public void homeList(HomeRequest homeRequest,final HomeRequestClientCallback callback){

        baseRequestClient.httpPostByJsonNew("PhoneGethomepage", userResult, homeRequest, HomeResult.class, new BaseRequestClient.RequestClientNewCallBack<HomeResult>() {
            @Override
            public void callBack(HomeResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {//&&result.getHotList()!=null
                        callback.list(result);
                    } else {
                        if(result!=null && result.getCode()==BaseResult.CodeState.Logout.getCode())
                        {
                            FinalDb finalDb = FinalDb.create(context);
                            List<UserResult> findAllByWhere = finalDb.findAllByWhere(
                                    UserResult.class, "isLogin=1");
                            for (UserResult userResult : findAllByWhere) {
                                userResult.setLogin(false);
                                finalDb.update(userResult);
                            }
                            // finalDb.deleteAll(UserResult.class);
                            baseApplication.setUserResult(null);
                            baseApplication.setCartCount(0);
                        }
                        Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                       // callback.listError(result);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                    callback.listError(result);
                }
                callback.listComplete(result);
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(HomeResult result) {
				callback.logOut(result);
			}
        });
    }


    public void comboList(ComboRequest request,final ComboRequestClientCallback callback){

       /* progressDialog.setMessage("加载中...");
        showProgress(true);*/
        baseRequestClient.httpPostByJsonNew("Phonepackage", userResult, request, ComboResult.class, new BaseRequestClient.RequestClientNewCallBack<ComboResult>() {

            @Override
            public void callBack(ComboResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.list(result);
                    } else {
                        if(result!=null && result.getCode()==BaseResult.CodeState.Logout.getCode())
                        {
                            FinalDb finalDb = FinalDb.create(context);
                            List<UserResult> findAllByWhere = finalDb.findAllByWhere(
                                    UserResult.class, "isLogin=1");
                            for (UserResult userResult : findAllByWhere) {
                                userResult.setLogin(false);
                                finalDb.update(userResult);
                            }
                            // finalDb.deleteAll(UserResult.class);
                            baseApplication.setUserResult(null);
                            baseApplication.setCartCount(0);
                        }
                        Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
                callback.listComplete(result);
               /* showProgress(false);*/
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(ComboResult result) {
				callback.logOut(result);
			}
        });
    }

    public void comboDetailList(ComboDetailRequest request,final ComboRequestClientCallback callback){
        progressDialog.setMessage("加载中...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Phonepackagedetail", userResult, request, ComboDetailResult.class, new BaseRequestClient.RequestClientNewCallBack<ComboDetailResult>() {

            @Override
            public void callBack(ComboDetailResult result) {
            	if (result != null) {
                    if (BaseResult.CodeState.Success.getCode() == result.getCode()) {
                        callback.detailList(result);
                    } else {
                        if(result!=null && result.getCode()==BaseResult.CodeState.Logout.getCode())
                        {
                            FinalDb finalDb = FinalDb.create(context);
                            List<UserResult> findAllByWhere = finalDb.findAllByWhere(
                                    UserResult.class, "isLogin=1");
                            for (UserResult userResult : findAllByWhere) {
                                userResult.setLogin(false);
                                finalDb.update(userResult);
                            }
                            // finalDb.deleteAll(UserResult.class);
                            baseApplication.setUserResult(null);
                            baseApplication.setCartCount(0);
                        }
                        Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
                showProgress(false);


            }

            @Override
            public void loading(long count, long current) {
                // TODO Auto-generated method stub

            }

			@Override
			public void logOut(ComboDetailResult result) {
				callback.logOut(result);
				
			}
        });
    }

    public void presentList(PresentRequest request, final PresentRequestClientCallback callback){

        progressDialog.setMessage("加载中");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("PhoneAdvertsList", userResult, request, PresentResult.class, new BaseRequestClient.RequestClientNewCallBack<PresentResult>() {

            @Override
            public void callBack(PresentResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.list(result);
                    } else {
                        if(result!=null && result.getCode()==BaseResult.CodeState.Logout.getCode())
                        {
                            FinalDb finalDb = FinalDb.create(context);
                            List<UserResult> findAllByWhere = finalDb.findAllByWhere(
                                    UserResult.class, "isLogin=1");
                            for (UserResult userResult : findAllByWhere) {
                                userResult.setLogin(false);
                                finalDb.update(userResult);
                            }
                            // finalDb.deleteAll(UserResult.class);
                            baseApplication.setUserResult(null);
                            baseApplication.setCartCount(0);
                        }
                        Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
                callback.listComplete(result);
                showProgress(false);
            }

            @Override
            public void loading(long count, long current) {


            }

			@Override
			public void logOut(PresentResult result) {
				callback.logOut(result);
				
			}
        });
    }

    public void recommendList(RecommendRequest request,final RecommendRequestClientCallback callback){
        /*progressDialog.setMessage("加载中...");
        showProgress(true);*/
        baseRequestClient.httpPostByJsonNew("PhoneAdvertsList", userResult, request, RecommendResult.class, new BaseRequestClient.RequestClientNewCallBack<RecommendResult>() {

            @Override
            public void callBack(RecommendResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.list(result);
                    } else {
                        if(result!=null && result.getCode()==BaseResult.CodeState.Logout.getCode())
                        {
                            FinalDb finalDb = FinalDb.create(context);
                            List<UserResult> findAllByWhere = finalDb.findAllByWhere(
                                    UserResult.class, "isLogin=1");
                            for (UserResult userResult : findAllByWhere) {
                                userResult.setLogin(false);
                                finalDb.update(userResult);
                            }
                            // finalDb.deleteAll(UserResult.class);
                            baseApplication.setUserResult(null);
                            baseApplication.setCartCount(0);
                        }
                        Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
                callback.listComplete(result);
          /*      showProgress(false);*/

            }

            @Override
            public void loading(long count, long current) {


            }

			@Override
			public void logOut(RecommendResult result) {
				callback.logOut(result);
				
			}
            
        });
    }
    
    
    public void checkUpgrade(final upgradeRequestClientCallback callback){

        BaseRequest baseRequest=new BaseRequest();
        baseRequestClient.httpPostByJsonNew("PhonePackageUpdate", userResult, baseRequest, ApkInfo.class, new BaseRequestClient.RequestClientNewCallBack<ApkInfo>() {

            @Override
            public void callBack(ApkInfo result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.success(result);
                    } else {
                        if(result!=null && result.getCode()==BaseResult.CodeState.Logout.getCode())
                        {/*
                            FinalDb finalDb = FinalDb.create(context);
                            List<UserResult> findAllByWhere = finalDb.findAllByWhere(
                                    UserResult.class, "isLogin=1");
                            for (UserResult userResult : findAllByWhere) {
                                userResult.setLogin(false);
                                finalDb.update(userResult);
                            }
                            // finalDb.deleteAll(UserResult.class);
                            baseApplication.setUserResult(null);
                            baseApplication.setCartCount(0);
                        */}
                        callback.fail(result);
                    }
                } else {

                    callback.fail(result);
                }

                //showProgress(false);

            }

            @Override
            public void loading(long count, long current) {


            }

			@Override
			public void logOut(ApkInfo result) {
				callback.logOut(result);
				
			}
        });
    }

    public static abstract class upgradeRequestClientCallback{
        protected void success(ApkInfo apkInfo){}
        protected void fail(ApkInfo apkInfo){}
        protected void logOut(BaseResult result){}
    }

    public static abstract class RecommendRequestClientCallback{
        protected void list(RecommendResult result){}
        protected void listComplete(RecommendResult result){}
        protected void logOut(BaseResult result){}
    }

    public static abstract class PresentRequestClientCallback {
        protected void list(PresentResult result){}
        protected void listComplete(PresentResult result){}
        protected void logOut(BaseResult result){}
    }

    public static abstract class ComboRequestClientCallback{
        protected void list(ComboResult result){}
        protected void listComplete(ComboResult result){}
        protected void logOut(BaseResult result){}
        protected void detailList(ComboDetailResult result){}
    }

    public static abstract class HomeRequestClientCallback{
        protected void list(HomeResult result){}
        protected void listError(HomeResult result){}
        protected void listComplete(HomeResult result){}
        protected void logOut(HomeResult result){}
    }


}
