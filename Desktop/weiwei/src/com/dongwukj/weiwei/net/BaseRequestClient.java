package com.dongwukj.weiwei.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.request.RequestEntity;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.utils.DeviceUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
	







import com.dongwukj.weiwei.net.utils.SoapObjectUtil;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class BaseRequestClient {

    private static final String NAMESPACE = "http://tempuri.org/";

    private static  String BASE_REQUEST_URL;

    private static final boolean isDebug=true;

    static {
        if(isDebug){
        	//BASE_REQUEST_URL="http://121.40.60.250:90/wwapi/";
        	BASE_REQUEST_URL="http://192.168.0.224:8080/wwapi/";
        	//BASE_REQUEST_URL="http://json11.b.vvlife.com/wwapi/";
        	// BASE_REQUEST_URL="http://192.168.0.86:2552/wwapi/";
         //BASE_REQUEST_URL="http://localhost:2552/wwapi/";
        }else {
            BASE_REQUEST_URL="http://json.vvlife.com/wwapi/";
       }
    }
    private static final String contentType="application/json";

    private static final ExecutorService executorService = Executors  
            .newFixedThreadPool(3); 
  
	/**
	 * request timeout
	 */
	private int timeout;

    private Context context;
	 
	public BaseRequestClient(Context context){
		this(context,100*1000);
	}
	
	public BaseRequestClient(Context context,int timeout){
		this.context=context;
        this.timeout=timeout;
	} 
	public <T extends BaseRequest,U extends BaseResult> void httpPostByJsonNew(String action,UserResult userResult,T entity,final Class<U> clazz,final  RequestClientNewCallBack<U> callback){
		String imei=DeviceUtil.getDeviceId(context);
		
        /*if(isDebug){
            userResult=new UserResult();
            userResult.setUserAccount("1");
            userResult.setTokenId("c872581e4821a62bb78f95e5559bcd66");
        }*/
		if(userResult!=null){
			entity.setTokenId(userResult.getTokenId());
			entity.setUserAccount(userResult.getUserAccount());
		}
		String version="1";
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			
			entity.setVersion(info.versionCode);
			version=info.versionCode+"";
		} catch (NameNotFoundException e1) {
			entity.setVersion(1);
		}
		entity.setImei(imei);
		RequestEntity requestEntity=new RequestEntity();
		requestEntity.setAppKey(entity);
		String paramString=JSON.toJSONString(requestEntity);
		StringEntity stringEntity=null;
		try{
			stringEntity=new StringEntity(paramString, HTTP.UTF_8);
			stringEntity.setContentType(contentType);
			FinalHttp fh = new FinalHttp(version);
			fh.post(BASE_REQUEST_URL+action+"/",stringEntity,contentType,new AjaxCallBack<String>(){
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					callback.callBack(null);
				}	

				@Override
				public void onSuccess(String t) {
					U result=null;
					if(t!=null && t.length()>0){
						try {
							result=JSON.parseObject(t,clazz);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							callback.callBack(null);
						}
					}
					if(result!=null && result.getCode()==BaseResult.CodeState.Logout.getCode())
					{	
//						Intent intent=new Intent(context, LoginActivity.class);
//						intent.putExtra("isLoginOut", true);
						
						//result.setMessage("您的账号已经在其他地方登录！");
						callback.logOut(result);
					}else {
						callback.callBack(result);
					}
				

//					U result=null;
//					if(t!=null && t.length()>0){
//						result=JSON.parseObject(t,clazz);
//					}
//					if(result!=null && result.getCode()==BaseResult.CodeState.Logout.getCode())
//					{
//						callback.logOut(true,result);
//					}else{
//						callback.callBack(result);
//					}
				}

				@Override
				public void onLoading(long count, long current) {
					super.onLoading(count,current);
				}
			});
		}catch (Exception e){
			callback.callBack( null);
		}
	}

	public <T extends BaseRequest,U extends BaseResult> void httpPostByJson(String action,UserResult userResult,T entity,final Class<U> clazz,final  RequestClientCallBack<U> callback){
        String imei=DeviceUtil.getDeviceId(context);
        /*if(isDebug){
            userResult=new UserResult();
            userResult.setUserAccount("1");
            userResult.setTokenId("c872581e4821a62bb78f95e5559bcd66");
        }*/
        if(userResult!=null){
            entity.setTokenId(userResult.getTokenId());
            entity.setUserAccount(userResult.getUserAccount());
        }
        String version="1";
        try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			version=info.versionCode+"";
			entity.setVersion(info.versionCode);
		} catch (NameNotFoundException e1) {
			entity.setVersion(1);
		}
        entity.setImei(imei);
        RequestEntity requestEntity=new RequestEntity();
        requestEntity.setAppKey(entity);
        String paramString=JSON.toJSONString(requestEntity);
		StringEntity stringEntity=null;
		try{
			stringEntity=new StringEntity(paramString, HTTP.UTF_8);
			stringEntity.setContentType(contentType);
			FinalHttp fh = new FinalHttp(version);
			fh.post(BASE_REQUEST_URL+action+"/",stringEntity,contentType,new AjaxCallBack<String>(){
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					callback.callBack(null);
				}

				@Override
				public void onSuccess(String t) {
//					U result=null;
//					if(t!=null && t.length()>0){
//						result=JSON.parseObject(t,clazz);
//					}
//					callback.callBack(result);

					U result=null;
					if(t!=null && t.length()>0){
						try {
							result=JSON.parseObject(t,clazz);
						} catch (Exception e) {
							callback.callBack(null);
						}
					}
					if(result!=null && result.getCode()==BaseResult.CodeState.Logout.getCode())
					{
						callback.logOut(true,result);
					}else{
						callback.callBack(result);
					}
				}

				@Override
				public void onLoading(long count, long current) {
					super.onLoading(count,current);
				}
			});
		}catch (Exception e){
			callback.callBack( null);
		}
	}

    public  static void downloadFile(String url,String target,AjaxCallBack callBack){
        FinalHttp finalHttp=new FinalHttp("1");
        finalHttp.download(url, target, true, callBack);

    }


	public <T extends BaseRequest,U extends BaseResult> void httpPost(String action,T entity,final Class<U> clazz,final  RequestClientCallBack<U> callback){
        String imei=DeviceUtil.getDeviceId(context);
        entity.setImei(imei);
        HashMap<String, String> params=new HashMap<String, String>();
		getClassFieldToMap(params,entity,entity.getClass());
		AjaxParams requestParams = new AjaxParams(params);
		String version="1";
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			
			entity.setVersion(info.versionCode);
			version=info.versionCode+"";
		} catch (NameNotFoundException e1) {
			entity.setVersion(1);
		}
		FinalHttp fh = new FinalHttp(version);
		fh.post(BASE_REQUEST_URL+action, requestParams, new AjaxCallBack<String>(){
			@Override
			public void onLoading(long count, long current) {

			}

			@Override
			public void onSuccess(String t) {

				U result=null;
				if(t!=null && t.length()>0){
					result=JSON.parseObject(t,clazz);
				}
				callback.callBack(result);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				callback.callBack(null);
			}
		});
	}

	public <T extends BaseRequest,U extends BaseResult> void httpPostWithFile(String action,T entity,Map<String,File> fileMap,final Class<U> clazz,final  RequestClientCallBack<U> callback) throws FileNotFoundException{
        String imei=DeviceUtil.getDeviceId(context);
        entity.setImei(imei);
        HashMap<String, String> params=new HashMap<String, String>();
		getClassFieldToMap(params,entity,entity.getClass());
		AjaxParams requestParams = new AjaxParams(params);
		while(fileMap.keySet().iterator().hasNext()){
			String key=fileMap.keySet().iterator().next();
			requestParams.put(key,fileMap.get(key));
		}
		FinalHttp fh = new FinalHttp("1");

		fh.post(BASE_REQUEST_URL+action, requestParams, new AjaxCallBack<String>(){
			@Override
			public void onLoading(long count, long current) {
				callback.loading(count,current);
			}

			@Override
			public void onSuccess(String t) {

				U result=null;
				if(t!=null && t.length()>0){
					result=JSON.parseObject(t,clazz);
				}
				if(result!=null && result.getCode()==BaseResult.CodeState.Logout.getCode())
				{
					callback.logOut(true,result);
				}else{
					callback.callBack(result);
				}

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				callback.callBack(null);
			}
		});
	}





	public <T extends BaseRequest,U extends BaseResult> void webServicePost(String url, final String methodName, T entity, final Class<U> clazz, final RequestClientCallBack<U> callback){
        String imei=DeviceUtil.getDeviceId(context);
        entity.setImei(imei);
        final HttpTransportSE httpTransportSE = new HttpTransportSE(url);  

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);  
        HashMap<String, String> params=new HashMap<String, String>();
        getClassFieldToMap(params,entity,entity.getClass());

        if (params!=null && params.size()>0) {  
            for (Iterator<Map.Entry<String, String>> it = params.entrySet()  
                    .iterator(); it.hasNext();) {  
                Map.Entry<String, String> entry = it.next();  
                soapObject.addProperty(entry.getKey(), entry.getValue());  
            }  
        }  

        final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(  
                SoapEnvelope.VER11);  

        soapEnvelope.setOutputSoapObject(soapObject);  
        soapEnvelope.dotNet = true;  
        httpTransportSE.debug = true;  
  

        final Handler mHandler = new Handler() {  
  
            @Override  
            public void handleMessage(Message msg) {  
                super.handleMessage(msg);  

                SoapObject entity=(SoapObject) msg.obj;
                try {
                	U result=null;
                	if(entity!=null) {
                		result=SoapObjectUtil.soapToPojo(clazz, entity);
                	};
					callback.callBack(result);
				}catch(Exception e){
					Log.e("error", "error", e);
				}
                 
            }  
  
        };  
  

        executorService.submit(new Runnable() {  
  
            @Override  
            public void run() {  
                SoapObject resultSoapObject = null;  
                try {  
                    httpTransportSE.call(NAMESPACE + methodName, soapEnvelope);  
                    if (soapEnvelope.getResponse() != null) {  
                        // ��ȡ��������Ӧ���ص�SoapObject  
                        resultSoapObject = (SoapObject) soapEnvelope.bodyIn;  
                    }  
                } catch (HttpResponseException e) {  
                    e.printStackTrace();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                } catch (XmlPullParserException e) {  
                    e.printStackTrace();  
                } finally {  
                    // ����ȡ����Ϣ����Handler���͵����߳�  
                    mHandler.sendMessage(mHandler.obtainMessage(0,  
                            resultSoapObject));  
                }  
            }  
        });  
	}
	
	private static void getClassFieldToMap(Map<String,String> map,Object entity,Class clazz){
		 try{
			 Field[] fs=clazz.getDeclaredFields();
				for(Field field:fs){
					if(field.getType()==File.class) continue;
					field.setAccessible(true);
					map.put(field.getName(), field.get(entity).toString());
				}
				if(clazz !=null && clazz!=Object.class){
					getClassFieldToMap(map,entity,clazz.getSuperclass());
				}
		 }catch( IllegalAccessException e){
			 
		 }catch(Exception e){
			 
		 }
		
	}
	


	public interface RequestClientNewCallBack<U extends BaseResult>{
		public void callBack(U result);
		public void loading(long count,long current);
		public void logOut(U result);
	}
	
	public interface RequestClientCallBack<U extends BaseResult>{
		public void callBack(U result);
		public void loading(long count,long current);
		public void logOut(boolean isLogOut,U result);
	} 

}
