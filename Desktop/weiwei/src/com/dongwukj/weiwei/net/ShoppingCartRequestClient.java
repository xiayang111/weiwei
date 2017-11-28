package com.dongwukj.weiwei.net;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.AddToCartRequest;
import com.dongwukj.weiwei.idea.request.PhoneModifyCartRequest;
import com.dongwukj.weiwei.idea.request.PhonecartdeleteRequest;
import com.dongwukj.weiwei.idea.request.PhonecartlistRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.CartItemResult;
import com.dongwukj.weiwei.idea.result.PhoneAddProductResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.ui.widget.ParentCartListView;
import net.tsz.afinal.FinalDb;

import java.util.List;

/**
 * Created by sunjaly on 15/4/13.
 */
public class ShoppingCartRequestClient extends AbsDataRequestClient{

    public ShoppingCartRequestClient(Context context, BaseApplication baseApplication) {
        super(context, baseApplication);
    }

    public void list(PhonecartlistRequest request,final ShoppingCartRequestClientCallback callback){

        progressDialog.setMessage("加载中");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Phonecartlist", userResult, request, CartItemResult.class, new BaseRequestClient.RequestClientNewCallBack<CartItemResult>() {
            @Override
            public void callBack(CartItemResult result) {


                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode() && result.getCartItemList() != null && result.getCartItemList().size() > 0) {
                        callback.listSuccess(result);

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
                        callback.listEmpty(result);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                    callback.listEmpty(result);
                }
                showProgress(false);

            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(CartItemResult result) {
				callback.logOut(result);
				
			}
        });

    }

    public void deleteCart(PhonecartdeleteRequest request,final ShoppingCartRequestClientCallback callback){
        progressDialog.setMessage("删除中...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Phonecartdelete", userResult, request, CartItemResult.class, new BaseRequestClient.RequestClientNewCallBack<CartItemResult>() {
            @Override
            public void callBack(CartItemResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode() && result.getCartItemList() != null && result.getCartItemList().size() > 0) {
                        callback.listSuccess(result);

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
                        callback.listEmpty(result);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                    callback.listEmpty(result);
                }
                showProgress(false);
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(CartItemResult result) {
				callback.logOut(result);
			}
        });
    }

    public void modifyCart(PhoneModifyCartRequest request ,final ShoppingCartRequestClientCallback callback){
        progressDialog.setMessage("修改中...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("Phonecartmodify", userResult, request, CartItemResult.class, new BaseRequestClient.RequestClientNewCallBack<CartItemResult>() {
            @Override
            public void callBack(CartItemResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode() && result.getCartItemList() != null) {
                        callback.listSuccess(result);

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
                        callback.listEmpty(result);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                    callback.listEmpty(result);
                }
                showProgress(false);
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(CartItemResult result) {
				callback.logOut(result);
			}
        });
    }
    public void addCart(AddToCartRequest request ,final AddShoppingCartRequestClientCallback callback){
        progressDialog.setMessage("添加中...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("PhoneAddProduct", userResult, request, PhoneAddProductResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneAddProductResult>() {
            @Override
            public void callBack(PhoneAddProductResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
                        baseApplication.setCartCount(result.getTotalCount());
                        callback.listSuccess(result);
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
                        	 callback.listfaile(result);
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
			public void logOut(PhoneAddProductResult result) {
				callback.logOut(result);
			}
        });
    }
    
    public static abstract class ShoppingCartRequestClientCallback{
        protected void listSuccess(CartItemResult result){}
        protected void listEmpty(CartItemResult result){}
        protected void logOut(BaseResult result){}
        
        
    }
    public static abstract class AddShoppingCartRequestClientCallback{
        protected void listSuccess(PhoneAddProductResult result){}
        protected void listfaile(PhoneAddProductResult result){}
        protected void logOut(BaseResult result){}
    }
}
