package com.dongwukj.weiwei.net;

import android.content.Context;
import android.widget.Toast;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.CategoryRequest;
import com.dongwukj.weiwei.idea.request.PhoneGetMarketGoodsRequest;
import com.dongwukj.weiwei.idea.request.PhoneGetProductsRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.CategoryResult;
import com.dongwukj.weiwei.idea.result.NewHomeResult;
import com.dongwukj.weiwei.idea.result.PhoneGetProductsResult;
import com.dongwukj.weiwei.idea.result.UserResult;

import net.tsz.afinal.FinalDb;

import java.util.List;

/**
 * Created by sunjaly on 15/4/13.
 */
public class CategoryRequestClient extends AbsDataRequestClient {

    public CategoryRequestClient(Context context, BaseApplication baseApplication) {
        super(context, baseApplication);
    }

    public void searchOrListDetailCategory(PhoneGetMarketGoodsRequest request ,final CategoryRequestCallback callback){

//        progressDialog.setMessage("加载中...");
//        showProgress(true);

        baseRequestClient.httpPostByJsonNew("PhoneGetMarketGoods", userResult, request, NewHomeResult.class, new BaseRequestClient.RequestClientNewCallBack<NewHomeResult>() {

            @Override
            public void callBack(NewHomeResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode()) {
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

                        callback.detailList(result);
                    }
                } else {
                	callback.detailList(result);
                }
                callback.detailListComplete(result);
//                showProgress(false);

            }

            @Override
            public void loading(long count, long current) {


            }

			@Override
			public void logOut(NewHomeResult result) {
				callback.LogOut(result);
			}
        });
    }

    public void list(CategoryRequest categoryRequest,final  CategoryRequestCallback callback){
        progressDialog.setMessage("加载中...");
        showProgress(true);
        baseRequestClient.httpPostByJsonNew("PhoneGetcategory", userResult, categoryRequest, CategoryResult.class, new BaseRequestClient.RequestClientNewCallBack<CategoryResult>() {
            @Override
            public void callBack(CategoryResult result) {
                if (result != null) {
                    if (result.getCode() == BaseResult.CodeState.Success.getCode() && result.getCategories() != null) {
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
                        callback.listError(result);
                    }
                } else {
                    String toast = context.getResources().getString(R.string.data_error);
                    Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                    callback.listError(result);
                }
                showProgress(false);
            }

            @Override
            public void loading(long count, long current) {

            }

			@Override
			public void logOut(CategoryResult result) {
				callback.LogOut(result);
			}
        });

    }

    public static abstract class CategoryRequestCallback{
        protected void list(CategoryResult result){}
        protected void listError(CategoryResult result){}
        protected void detailList(NewHomeResult result){}
        protected void detailListComplete(NewHomeResult result){}
        protected void LogOut(BaseResult result){}
    }
}
