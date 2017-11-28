package com.dongwukj.weiwei.net;

import android.content.Context;
import android.os.Message;
import android.view.View;
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
public class MyWeiWeiRequestClient extends AbsDataRequestClient {

    public MyWeiWeiRequestClient(Context context, BaseApplication baseApplication) {
        super(context, baseApplication);
    }

    public void attentionList(AttentionRequest attentionRequest, final AttentionRequestCallback callback){
       /* progressDialog.setMessage("加载中...");
        showProgress(true);*/
        baseRequestClient.httpPostByJsonNew("Phoneattention", userResult, attentionRequest, AttentionResult.class, new BaseRequestClient.RequestClientNewCallBack<AttentionResult>() {
            @Override
            public void callBack(AttentionResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode() && result.getAttentions() != null) {
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
               // showProgress(false);
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(AttentionResult result) {
				callback.logOut(result);
				
			}
        });
    }

    public void attentionDelete(AttentionDeleteRequest attentionDeleteRequest ,final AttentionRequestCallback callback){

//        progressDialog.setMessage("删除中...");
//        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Phoneattentiondelete", userResult, attentionDeleteRequest, BaseResult.class, new BaseRequestClient.RequestClientNewCallBack<BaseResult>() {
            @Override
            public void callBack(BaseResult result) {
                if (result != null) {
                    //todo:删除时需要匹配页码
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.delete(result);
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
              //  showProgress(false);

            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(BaseResult result) {
				callback.logOut(result);
				
			}
        });
    }
    public void fetchAddressList(final AddressRequestCallback callback){
        progressDialog.setMessage("获取数据中...");
        showProgress(true);
        final BaseRequest request = new BaseRequest();
        baseRequestClient.httpPostByJsonNew("Phoneaddress", userResult, request, AddressResult.class, new BaseRequestClient.RequestClientNewCallBack<AddressResult>() {

            @Override
            public void callBack(AddressResult result) {
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
                showProgress(false);
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(AddressResult result) {
				showProgress(false);
				callback.logOut(result);
				
			}
        });
    }

    public void addAddress(DeleteAddressRequest request, final AddressRequestCallback callback){
        progressDialog.setMessage("删除中...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Phoneaddressdelete", userResult, request, DeleteAddressResult.class, new BaseRequestClient.RequestClientNewCallBack<DeleteAddressResult>() {

            @Override
            public void callBack(DeleteAddressResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.add(result);
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


            }

			@Override
			public void logOut(DeleteAddressResult result) {
				 showProgress(false);
				callback.logOut(result);
				
			}
        });

    }


    public void footPrintList(FootPrintRequest request,final FootPrintRequestCallback callback){
        /*progressDialog.setMessage("获取中...");
        showProgress(true);*/
        baseRequestClient.httpPostByJsonNew("Phonefootprint", userResult, request, FootPrintResult.class, new BaseRequestClient.RequestClientNewCallBack<FootPrintResult>() {
            @Override
            public void callBack(FootPrintResult result) {
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
                //showProgress(false);
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(FootPrintResult result) {
				callback.logOut(result);
			}
        });
    }
    
    public void regionsList(NewAddressRequest request,final RegionsRequestCallback callback,boolean isNeedShowProgress){
    	progressDialog.setMessage("获取中...");
        showProgress(isNeedShowProgress);
        baseRequestClient.httpPostByJsonNew("Phoneregions", userResult, request, NewAddressResult.class, new BaseRequestClient.RequestClientNewCallBack<NewAddressResult>() {
            @Override
            public void callBack(NewAddressResult result) {
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

                showProgress(false);
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(NewAddressResult result) {
				showProgress(false);
				callback.logOut(result);
			}
        });
    }
    


    public void orderList(OrderListRequest request,final OrderRequestCallback callback){
//        progressDialog.setMessage("加载中...");
//        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Phoneorderlist", userResult, request, OrderListResult.class, new BaseRequestClient.RequestClientNewCallBack<OrderListResult>() {

            @Override
            public void callBack(OrderListResult result) {
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
//               showProgress(false);
            }

            @Override
            public void loading(long count, long current) {


            }

			@Override
			public void logOut(OrderListResult result) {
				callback.logOut(result);	
			}
        });
    }
    

    public void purseList(PurseRequest request,final PurseRequestClientCallback callback){//调这个方法
        progressDialog.setMessage("加载中...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Phonewallet", userResult, request, PurseResult.class, new BaseRequestClient.RequestClientNewCallBack<PurseResult>() {

            @Override
            public void callBack(PurseResult result) {

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
                showProgress(false);

            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(PurseResult result) {
				callback.logOut(result);
			}
        });
    }

    /**
     * 用户登录请求接口
     * @param entity
     * @param callback
     */
    public void login(LoginEntity entity,final LoginRequestClientCallback callback){

        progressDialog.setMessage("登录中...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("PhoneLogin", userResult, entity, LoginUserResult.class, new BaseRequestClient.RequestClientNewCallBack<LoginUserResult>() {

            @Override
            public void callBack(LoginUserResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        String tokenId = result.getTokenId();

                        if (tokenId != null) {
                            callback.login(result);
                        } else {
                            Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                        }
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
                        Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
                callback.loginComplete(result);
                showProgress(false);
            }

            public void loading(long count, long current) {

            }

			@Override
			public void logOut(LoginUserResult result) {
				callback.logOut(result);
				showProgress(false);
			}
        });
    }


    public void smsCode(SmsRequest request,final RegisterRequestClientCallback callback){
        progressDialog.setMessage("正在获取验证码...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Smscode", userResult, request, SmsResult.class, new BaseRequestClient.RequestClientNewCallBack<SmsResult>() {

            @Override
            public void callBack(SmsResult result) {
                // TODO Auto-generated method stub
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.smsCode(result);

                    } else {
                        if(result!=null && result.getCode()==BaseResult.CodeState.Logout.getCode())
                        {
                            /*FinalDb finalDb = FinalDb.create(context);
                            List<UserResult> findAllByWhere = finalDb.findAllByWhere(
                                    UserResult.class, "isLogin=1");
                            for (UserResult userResult : findAllByWhere) {
                                userResult.setLogin(false);
                                finalDb.update(userResult);
                            }
                            // finalDb.deleteAll(UserResult.class);
                            baseApplication.setUserResult(null);
                            baseApplication.setCartCount(0);*/
                        	callback.logOut(result);
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


            }

			@Override
			public void logOut(SmsResult result) {
				showProgress(false);
				callback.logOut(result);
			}

        });
    }
    
    public void smsUpdatecode(SmsRequest request,final RegisterRequestClientCallback callback){
        progressDialog.setMessage("正在获取验证码...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("SmsUpdatecode", userResult, request, SmsResult.class, new BaseRequestClient.RequestClientNewCallBack<SmsResult>() {

            @Override
            public void callBack(SmsResult result) {
                // TODO Auto-generated method stub
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.smsCode(result);
                    } else {
                        Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(result!=null && result.getCode()==BaseResult.CodeState.Logout.getCode()){};
                }
                showProgress(false);

            }

            @Override
            public void loading(long count, long current) {}

			@Override
			public void logOut(SmsResult result) {
				showProgress(false);
				callback.logOut(result);
			}

        });
    }


    public void register(RegisterEntity request,final RegisterRequestClientCallback callback){


        progressDialog.setMessage("正在注册...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Phoneregister", userResult, request, RegisterResult.class, new BaseRequestClient.RequestClientNewCallBack<RegisterResult>() {

            @Override
            public void callBack(RegisterResult result) {
                // TODO Auto-generated method stub
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.register(result);
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
                        Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
                showProgress(false);

            }

            @Override
            public void loading(long count, long current) {


            }

			@Override
			public void logOut(RegisterResult result) {
				 showProgress(false);
				callback.logOut(result);
			}

        });
    }
    public void getOrderListDetail(OrderDetailRequest request,final OrderListDetailRequestCallback callback){

        progressDialog.setMessage("获取订单详情中...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Phonegetorderid", userResult, request, OrderDetailResult.class, new BaseRequestClient.RequestClientNewCallBack<OrderDetailResult>() {

            @Override
            public void callBack(OrderDetailResult result) {
                // TODO Auto-generated method stub
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
                showProgress(false);

            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(OrderDetailResult result) {
				 showProgress(false);
				callback.logOut(result);
			}

        });
    
    }
    public void orderCancle(OrderCancleRequest request,final OrderListDetailRequestCallback callback){


        progressDialog.setMessage("取消订单中...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Phoneordercancel", userResult, request, BaseResult.class, new BaseRequestClient.RequestClientNewCallBack<BaseResult>() {

            @Override
            public void callBack(BaseResult result) {
                // TODO Auto-generated method stub
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.cancle(result);
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

            }

			@Override
			public void logOut(BaseResult result) {
				callback.logOut(result);
			}

        });
    
    
    }
    public void orderDelete(OrderCancleRequest request,final OrderListDetailRequestCallback callback){


        progressDialog.setMessage("订单删除中...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Phoneorderdelete", userResult, request, BaseResult.class, new BaseRequestClient.RequestClientNewCallBack<BaseResult>() {

            @Override
            public void callBack(BaseResult result) {
                // TODO Auto-generated method stub
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.delete(result);
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

            }

			@Override
			public void logOut(BaseResult result) {
				callback.logOut(result);
			}

        });
    
    
    }

    public void confirmOrder(PhoneConfirmOrderRequest request,final ConfirmRequestClientCallback callback){

        baseRequestClient.httpPostByJsonNew("Phoneconfirmorder", userResult, request, PhoneConfirmOrderResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneConfirmOrderResult>() {
            @Override
            public void callBack(PhoneConfirmOrderResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.result(result);
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
                callback.resultComplete(result);
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(PhoneConfirmOrderResult result) {
				callback.logOut(result);
			}
        });


    }

    public void submitOrder(PhonecommitorderRequest request,final SubmitOrderRequestClientCallback callback){

        progressDialog.setMessage("提交中...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Phonecommitorder", userResult, request, PhonecommitorderResult.class, new BaseRequestClient.RequestClientNewCallBack<PhonecommitorderResult>() {
            @Override
            public void callBack(PhonecommitorderResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.result(result);
                    } else if (result.getCode() == 959) {
                        callback.result(result);
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
                        }else {
                        	callback.resultFalied(result);
						}
                        Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
                callback.resultComplete(result);
                showProgress(false);
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(PhonecommitorderResult result) {
				callback.logOut(result);
			}
        });

    }


    public void payOrderByYue(PhonepayRequest request,final PhonepayRequestClientCallback callback){

        progressDialog.setMessage("支付中...");
        showProgress(true);

        baseRequestClient.httpPostByJsonNew("Phonepay", userResult, request, PhonepayResult.class, new BaseRequestClient.RequestClientNewCallBack<PhonepayResult>() {
            @Override
            public void callBack(PhonepayResult result) {

                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.result(result);
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
                        callback.resultFail(result);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                    callback.resultFail(result);
                }
                callback.resultComplete(result);
                showProgress(false);
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(PhonepayResult result) {
				showProgress(false);
				callback.logOut(result);
			}
        });

    }

    /**
     * 获取支付密码验证问题
     */
    public void phoneVerifyQuestion(final PhoneVerifyQuestionRequestCallback callback){

        baseRequestClient.httpPostByJsonNew("PhoneVerifyQuestion", userResult, new BaseRequest(), PhoneVerifyQuestionResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneVerifyQuestionResult>() {
            @Override
            public void callBack(PhoneVerifyQuestionResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.result(result);
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
                        callback.resultFail(result);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                    callback.resultFail(result);
                }
                callback.resultComplete(result);
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(PhoneVerifyQuestionResult result) {
				callback.logOut(result);
			}
        });


    }

    /*
     * 设置支付密码,安全问题.
     */
    
    public void payPassword(PayPasswordRequest request,final PhonePayPasswordRequestCallback callback){
    	baseRequestClient.httpPostByJsonNew("PhonePayPassword", userResult, request, BaseResult.class, new BaseRequestClient.RequestClientNewCallBack<BaseResult>() {

            @Override
            public void callBack(BaseResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        callback.setPaypassword(result);
                    } else {
                        if (result != null && result.getCode() == BaseResult.CodeState.Logout.getCode()) {
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

            }

            @Override
            public void loading(long count, long current) {
                // TODO Auto-generated method stub

            }

			@Override
			public void logOut(BaseResult result) {
				callback.logOut(result);
			}
        });
    } 
    
    public static abstract class PhonePayPasswordRequestCallback{
        protected void setPaypassword(BaseResult result){}
        protected void logOut(BaseResult result){}
        
        
    }
    
    public static abstract class PhoneVerifyQuestionRequestCallback{
        protected void result(PhoneVerifyQuestionResult result){}
        protected void resultFail(PhoneVerifyQuestionResult result){}
        protected void resultComplete(PhoneVerifyQuestionResult result){}
        protected void logOut(BaseResult result){}
    }


    public static abstract class PhonepayRequestClientCallback{
        protected void result(PhonepayResult result){}
        protected void resultFail(PhonepayResult result){}
        protected void resultComplete(PhonepayResult result){}
        protected void logOut(BaseResult result){}
    }


    public static abstract class SubmitOrderRequestClientCallback{
        protected void result(PhonecommitorderResult result){}
        protected void resultComplete(PhonecommitorderResult result){}
        protected void resultFalied(PhonecommitorderResult result){}
        protected void logOut(BaseResult result){}
        
    }

    public static abstract class ConfirmRequestClientCallback{

        protected void result(PhoneConfirmOrderResult result){}
        protected void resultComplete(PhoneConfirmOrderResult result){}
        protected void logOut(BaseResult result){}

    }

    public static abstract class RegisterRequestClientCallback{
        protected void smsCode(SmsResult result){}
        protected void register(RegisterResult result){}
        protected void logOut(BaseResult result){}
    }


    public static abstract class LoginRequestClientCallback{
        protected void login(LoginUserResult result){}
        protected void loginComplete(LoginUserResult result){}
        protected void logOut(BaseResult result){}
    }

    public static abstract class PurseRequestClientCallback{
        protected void list(PurseResult result){}
        protected void logOut(BaseResult result){}

    }

    public static abstract class OrderRequestCallback{
        protected void list(OrderListResult result){}
        protected void listComplete(OrderListResult result){}
        protected void logOut(BaseResult result){}
    }

    public static abstract class FootPrintRequestCallback{
        protected void list(FootPrintResult result){}
        protected void listComplete(FootPrintResult result){}
        protected void logOut(BaseResult result){}
    }

    public static abstract class AddressRequestCallback{
        protected  void list(AddressResult result){}
        protected  void add(DeleteAddressResult result){}
        protected void logOut(BaseResult result){}
    }


    public static abstract class AttentionRequestCallback{
        protected void list(AttentionResult result){}
        protected void listComplete(AttentionResult result){}
        protected void logOut(BaseResult result){}

        protected void delete(BaseResult result){};

    }
    public static abstract class OrderListDetailRequestCallback{
    	protected void list(OrderDetailResult result){}
    	protected void cancle(BaseResult result){}
    	protected void delete(BaseResult result){}
        protected void logOut(BaseResult result){}
    }
    
    public static abstract class RegionsRequestCallback{
    	protected void list(NewAddressResult result){}
        protected void logOut(BaseResult result){}
    	
    }
}
