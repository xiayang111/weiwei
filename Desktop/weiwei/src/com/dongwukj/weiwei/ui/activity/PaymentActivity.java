//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dongwukj.weiwei.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alipay.sdk.app.PayTask;
import com.dongwukj.weiwei.idea.data.PingppObject;
import com.dongwukj.weiwei.idea.result.WeixinResult;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;

import net.sourceforge.simcpux.Constants;
import net.sourceforge.simcpux.MD5;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//import com.baidu.android.pay.PayCallBack;
//import com.baidu.paysdk.api.BaiduPay;

public class PaymentActivity extends Activity implements IWXAPIEventHandler {
    public static final String VERSION = "2.0.3";
    private static final String TAG = "com.pingplusplus.android.PaymentActivity";
    public static final String EXTRA_CREDENTIAL = "com.pingplusplus.android.PaymentActivity.CREDENTIAL";
    public static final String EXTRA_CHARGE = "com.pingplusplus.android.PaymentActivity.CHARGE";
    public static final int RESULT_EXTRAS_INVALID = 2;
    private static final int ID_ALIPAY_RESULT = 1;
    private static final int ID_NOTIFY_SUCCESS = 2;
    private static final int ID_NOTIFY_FAIL = 3;
    private IWXAPI api = null;
    private static final String mockUrl = "http://sissi.pingxx.com/mock.php?ch_id=%s&channel=%s";
    private int wxPayStatus = 0;
    private int bfbPayStatus = 0;
    private ProgressDialog progressDialog = null;
    WebViewClient webViewClient = new WebViewClient() {
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(PaymentActivity.this.progressDialog != null && PaymentActivity.this.progressDialog.isShowing()) {
                PaymentActivity.this.progressDialog.dismiss();
            }

        }
    };
    private Handler mHandler = new PaymentActivity.PingppHandler(this);
    
    public PaymentActivity() {
    }


    private void payInit(Bundle savedInstanceState){
        if(savedInstanceState == null) {
            Intent intent = this.getIntent();
            String charge = intent.getStringExtra("com.pingplusplus.android.PaymentActivity.CHARGE");
            String channel = null;
            JSONObject credential = null;
            String wxAppId1;
            if(charge != null) {
                try {
                    JSONObject wxAppId = new JSONObject(charge);
                    if(wxAppId.has("credential")) {
                        channel = wxAppId.getString("channel");
                        if(wxAppId.has("livemode") && !wxAppId.getBoolean("livemode")) {
                            String e = wxAppId.getString("id");
                            this.openTestmodePage(e, channel);
                            return;
                        }

                        credential = wxAppId.getJSONObject("credential");
                    } else {
                        this.setResultAndFinish("fail", "invalid_charge_no_credential");
                    }
                } catch (JSONException var9) {
                    this.setResultAndFinish("fail", "invalid_charge_json_decode_fail");
                }
            } else {
                wxAppId1 = intent.getStringExtra("com.pingplusplus.android.PaymentActivity.CREDENTIAL");
                if(wxAppId1 != null) {
                    try {
                        credential = new JSONObject(wxAppId1);
                    } catch (JSONException var8) {
                        this.setResultAndFinish("fail", "invalid_credential_json_decode_fail");
                    }
                }
            }

            if(credential != null) {
                PingppObject.getInstance().setCurrentChannel(channel);

                try {
                    if(!this.isChannel(credential, "upmp", channel) && !this.isChannel(credential, "upacp", channel)) {
                        if(this.isChannel(credential, "wx", channel)) {
                           // this.channelWx(credential);
                        } else if(this.isChannel(credential, "alipay", channel)) {
                           // this.channelAlipay(credential);
                        } else if(this.isChannel(credential, "bfb", channel)) {
                            //this.channelBfb(credential);
                        } else {
                            PingppObject.getInstance().setCurrentChannel((String)null);
                            this.setResultAndFinish("fail", "invalid_charge_no_such_channel");
                        }
                    } else {
                        this.channelUnionPay(credential);
                    }
                } catch (JSONException var10) {
                    PingppObject.getInstance().setCurrentChannel((String)null);
                    this.setResultAndFinish("fail", "invalid_credential");
                }
            } else {
                wxAppId1 = PingppObject.getInstance().getWxAppId();
                if(wxAppId1 != null) {
                    this.api = WXAPIFactory.createWXAPI(this, wxAppId1);
                    this.api.handleIntent(this.getIntent(), this);
                }
            }

        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        payInit(savedInstanceState);
        if(savedInstanceState == null) {
        	Intent intent=this.getIntent();
        	int extra = intent.getIntExtra("type", 0);
        	
        	if (extra==8) {
        		String orderInfo = intent.getStringExtra("orderInfo");
				channelAlipay(orderInfo);
			}else if (extra==10) {
				WeixinResult result = (WeixinResult) intent.getSerializableExtra("orderInfo");
				channelWx(result);
			}
        	/*
            Intent intent = this.getIntent();
            String charge = intent.getStringExtra("com.pingplusplus.android.PaymentActivity.CHARGE");
            String channel = null;
            JSONObject credential = null;
            String wxAppId1;
            if(charge != null) {
                try {
                    JSONObject wxAppId = new JSONObject(charge);
                    if(wxAppId.has("credential")) {
                        channel = wxAppId.getString("channel");
                        if(wxAppId.has("livemode") && wxAppId.getBoolean("livemode")) {
                            String e = wxAppId.getString("id");
                            this.openTestmodePage(e, channel);
                            return;
                        }

                        credential = wxAppId.getJSONObject("credential");
                    } else {
                        this.setResultAndFinish("fail", "invalid_charge_no_credential");
                    }
                } catch (JSONException var9) {
                    this.setResultAndFinish("fail", "invalid_charge_json_decode_fail");
                }
            } else {
                wxAppId1 = intent.getStringExtra("com.pingplusplus.android.PaymentActivity.CREDENTIAL");
                if(wxAppId1 != null) {
                    try {
                        credential = new JSONObject(wxAppId1);
                    } catch (JSONException var8) {
                        this.setResultAndFinish("fail", "invalid_credential_json_decode_fail");
                    }
                }
            }

            if(credential != null) {
                PingppObject.getInstance().setCurrentChannel(channel);

                try {
                    if(!this.isChannel(credential, "upmp", channel) && !this.isChannel(credential, "upacp", channel)) {
                        if(this.isChannel(credential, "wx", channel)) {
                            this.channelWx(credential);
                        } else if(this.isChannel(credential, "alipay", channel)) {
                            this.channelAlipay(credential);
                        } else if(this.isChannel(credential, "bfb", channel)) {
                            // this.channelBfb(credential);
                        } else {
                            PingppObject.getInstance().setCurrentChannel((String)null);
                            this.setResultAndFinish("fail", "invalid_charge_no_such_channel");
                        }
                    } else {
                        this.channelUnionPay(credential);
                    }
                } catch (JSONException var10) {
                    PingppObject.getInstance().setCurrentChannel((String)null);
                    this.setResultAndFinish("fail", "invalid_credential");
                }
            } else {
                wxAppId1 = PingppObject.getInstance().getWxAppId();
                if(wxAppId1 != null) {
                    this.api = WXAPIFactory.createWXAPI(this, wxAppId1);
                    this.api.handleIntent(this.getIntent(), this);
                }
            }

        */}
    }

    private boolean isChannel(JSONObject credential, String channel, String channelInCharge) throws JSONException {
        return (channelInCharge == null || channelInCharge.equals(channel)) && credential.has(channel) && !credential.getString(channel).equals("[]") && !credential.getString(channel).equals("{}");
    }

  /*  private void channelAlipay(JSONObject credential) throws JSONException {
        JSONObject credentialData = credential.getJSONObject("alipay");
        final String orderInfo = credentialData.getString("orderInfo");
        (new Thread() {
            public void run() {
                PayTask alipay = new PayTask(PaymentActivity.this);
                String result = alipay.pay(orderInfo);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                PaymentActivity.this.mHandler.sendMessage(msg);
            }
        }).start();
    }*/
    
    private void channelAlipay(final String orderInfo)  {
      //  JSONObject credentialData = credential.getJSONObject("alipay");
      //  final String orderInfo = credentialData.getString("orderInfo");
        (new Thread() {
            public void run() {
                PayTask alipay = new PayTask(PaymentActivity.this);
                String result = alipay.pay(orderInfo);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                PaymentActivity.this.mHandler.sendMessage(msg);
            }
        }).start();
    }
    private void channelWx(WeixinResult credentialData) {
      
        String appId = credentialData.getAppId();
        PingppObject.getInstance().setWxAppId(appId);
        this.api = WXAPIFactory.createWXAPI(this, null);
        if(!this.api.isWXAppInstalled()) {
            this.setResultAndFinish("invalid", "wx_app_not_installed");
        } else {
            boolean isPaySupported = true;
                    //this.api.getWXAppSupportAPI() >= 570425345;
            if(isPaySupported) {
                this.wxPayStatus = 1;
                this.api.registerApp(appId);
               // this.api.handleIntent(this.getIntent(), this);
                PayReq req = new PayReq();
                req.appId = appId;
                req.partnerId = credentialData.getPartnerId();
                req.prepayId = credentialData.getPrepayId();
                req.nonceStr = credentialData.getNonceStr();
                req.timeStamp = credentialData.getTimeStampWX();
                req.packageValue = credentialData.getPackageValue();
                req.sign = credentialData.getSign();
             /* List<NameValuePair> signParams = new LinkedList<NameValuePair>();
                signParams.add(new BasicNameValuePair("appid", req.appId));
                signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
                signParams.add(new BasicNameValuePair("package", req.packageValue));
                signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
                signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
                signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

                req.sign = genAppSign(signParams);*/
                boolean success= this.api.sendReq(req);
                Log.d("pay", "weixin -------------------------+"+success);
            } else {
                this.setResultAndFinish("fail", "wx_app_not_support");
            }

        }
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        //this.sb.append("sign str\n"+sb.toString()+"\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", appSign);
        return appSign;
    }
   /* private void channelWx(JSONObject credential) throws JSONException {
        JSONObject credentialData = credential.getJSONObject("wx");
        String appId = credentialData.getString("appId");
        PingppObject.getInstance().setWxAppId(appId);
        this.api = WXAPIFactory.createWXAPI(this, appId);
        if(!this.api.isWXAppInstalled()) {
            this.setResultAndFinish("invalid", "wx_app_not_installed");
        } else {
            boolean isPaySupported = this.api.getWXAppSupportAPI() >= 570425345;
            if(isPaySupported) {
                this.wxPayStatus = 1;
                this.api.registerApp(appId);
                this.api.handleIntent(this.getIntent(), this);
                PayReq req = new PayReq();
                req.appId = appId;
                req.partnerId = credentialData.getString("partnerId");
                req.prepayId = credentialData.getString("prepayId");
                req.nonceStr = credentialData.getString("nonceStr");
                req.timeStamp = credentialData.getString("timeStamp");
                req.packageValue = credentialData.getString("packageValue");
                req.sign = credentialData.getString("sign");
                this.api.sendReq(req);
            } else {
                this.setResultAndFinish("fail", "wx_app_not_support");
            }

        }
    }*/

    private void channelUnionPay(JSONObject credential) throws JSONException {
        JSONObject credentialData;
        if(credential.has("upacp")) {
            credentialData = credential.getJSONObject("upacp");
        } else {
            credentialData = credential.getJSONObject("upmp");
        }

        String tn = credentialData.getString("tn");
        String mode = credentialData.getString("mode");
        int ret = UPPayAssistEx.startPay(this, (String)null, (String)null, tn, mode);
        if(ret == -1) {
            this.setResultAndFinish("invalid", "unionpay_plugin_not_found");
        } else if(ret != 0) {
            this.setResultAndFinish("fail", "unknown_error");
        }

    }

//    private void channelBfb(JSONObject credential) throws JSONException {
//        JSONObject credentialData = credential.getJSONObject("bfb");
//        HashMap map = new HashMap();
//        map.put("cashier_type", "0");
//        ArrayList infoList = new ArrayList();
//        Iterator it = credentialData.keys();
//
//        String orderInfo;
//        while(it.hasNext()) {
//            orderInfo = (String)it.next();
//            ArrayList keyValue = new ArrayList();
//            keyValue.add(orderInfo);
//            keyValue.add(credentialData.getString(orderInfo));
//            String kvStr = TextUtils.join("=", keyValue);
//            infoList.add(kvStr);
//        }
//
//        if(infoList.size() == 0) {
//            this.setResultAndFinish("fail", "invalid_credential");
//        } else {
//            orderInfo = TextUtils.join("&", infoList);
//            this.bfbPayStatus = 1;
//            BaiduPay.getInstance().doPay(this, orderInfo, new PayCallBack() {
//                public void onPayResult(int stateCode, String payDesc) {
//                    PaymentActivity.this.bfbPayStatus = 0;
//                    PaymentActivity.this.bfbHandlepayResult(stateCode, payDesc);
//                }
//
//                public boolean isHideLoadingDialog() {
//                    return false;
//                }
//            }, map);
//        }
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(PingppObject.getInstance().getCurrentChannel() == null || PingppObject.getInstance().getCurrentChannel().equalsIgnoreCase("upmp") || PingppObject.getInstance().getCurrentChannel().equalsIgnoreCase("upacp")) {
            if(data == null) {
                this.setResultAndFinish("fail", "");
            } else {
                String str = data.getExtras().getString("pay_result");
                if(str == null) {
                    this.setResultAndFinish("fail");
                } else if(str.equalsIgnoreCase("success")) {
                    this.setResultAndFinish("success");
                } else if(str.equalsIgnoreCase("fail")) {
                    this.setResultAndFinish("fail", "channel_returns_fail");
                } else if(str.equalsIgnoreCase("cancel")) {
                    this.setResultAndFinish("cancel", "user_cancelled");
                } else {
                    this.setResultAndFinish("fail", "unknown_error");
                }

            }
        }
    }

    protected void onResume() {
        super.onResume();
        if(this.wxPayStatus == 2 || this.bfbPayStatus == 2) {
            this.setResultAndFinish("cancel", "user_cancelled");
        }

    }

    protected void onPause() {
        super.onPause();
        if(this.wxPayStatus == 1) {
            this.wxPayStatus = 2;
        } else if(this.bfbPayStatus == 1 && PingppObject.getInstance().getCurrentChannel() != null && PingppObject.getInstance().getCurrentChannel().equalsIgnoreCase("bfb")) {
            this.bfbPayStatus = 2;
        }

    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(this.api != null) {
            this.setIntent(intent);
            this.api.handleIntent(intent, this);
        }

    }

    public void onReq(BaseReq req) {
    }

    public void onResp(BaseResp resp) {
        if(resp.getType() == 5) {
            this.wxPayStatus = 0;
            if(resp.errCode == 0) {
                this.setResultAndFinish("success");
            } else if(resp.errCode == -2) {
                this.setResultAndFinish("cancel", "user_cancelled");
            } else {
                this.setResultAndFinish("fail", "channel_returns_fail", "wx_err_code:" + resp.errCode);
            }
        }

    }

    private void setResultAndFinish(String result, String errorMsg, String extraMsg) {
        PingppObject.getInstance().setCurrentChannel((String)null);
        Intent intent = new Intent();
        intent.putExtra("pay_result", result);
        intent.putExtra("error_msg", errorMsg);
        intent.putExtra("extra_msg", extraMsg);
        this.setResult(-1, intent);
        this.finish();
    }

    private void setResultAndFinish(String result, String errorMsg) {
        this.setResultAndFinish(result, errorMsg, "");
    }

    private void setResultAndFinish(String result) {
        this.setResultAndFinish(result, "");
    }

    private void bfbHandlepayResult(int stateCode, String payDesc) {
        if(payDesc == null) {
            this.setResultAndFinish("cancel", "user_cancelled");
        } else {
            String tradeStatus = "statecode={";
            int imemoStart = payDesc.indexOf("statecode=");
            imemoStart += tradeStatus.length();
            int imemoEnd = payDesc.indexOf("};order_no=");
            tradeStatus = payDesc.substring(imemoStart, imemoEnd);
            if(tradeStatus.equals("0")) {
                this.setResultAndFinish("success");
            } else if(tradeStatus.equals("1")) {
                this.setResultAndFinish("in_process");
            } else if(!tradeStatus.equals("2") && !tradeStatus.equals("7")) {
                if(tradeStatus.equals("3")) {
                    this.setResultAndFinish("fail", "bfb_not_supported_method");
                } else if(tradeStatus.equals("4")) {
                    this.setResultAndFinish("fail", "bfb_token_expired");
                } else {
                    this.setResultAndFinish("fail", "unknown_error");
                }
            } else {
                this.setResultAndFinish("cancel", "user_cancelled");
            }

        }
    }

    public static String getVersion() {
        return "2.0.3";
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void openTestmodePage(String chargeId, String channel) {
        WebView webView = new WebView(this);
        webView.setLayoutParams(new LayoutParams(-1, -1));
        this.setContentView(webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUserAgentString(String.format("%s; %s/%s", new Object[]{webSettings.getUserAgentString(), "PingppAndroidSDK", "2.0.3"}));
        webView.addJavascriptInterface(new PaymentActivity.WebAppInterface(this), "PingppAndroidSDK");
        webView.setWebViewClient(this.webViewClient);
        webView.loadUrl(String.format("http://sissi.pingxx.com/mock.php?ch_id=%s&channel=%s", new Object[]{chargeId, channel}));
        this.progressDialog = ProgressDialog.show(this, "", "Loading", true);
    }

    static class PingppHandler extends Handler {
        WeakReference<PaymentActivity> mActivity;

        PingppHandler(PaymentActivity activity) {
            this.mActivity = new WeakReference(activity);
        }

        public void handleMessage(Message msg) {
            PaymentActivity a = (PaymentActivity)this.mActivity.get();
            switch(msg.what) {
                case 1:
                    String result = (String)msg.obj;

                    try {
                        int e = this.getResultStatus(result);
                        if(e == 9000) {
                            a.setResultAndFinish("success");
                        } else if(e == 6001) {
                            a.setResultAndFinish("cancel", "user_cancelled");
                        } else {
                            ArrayList results = new ArrayList();
                            results.add("resultStatus");
                            results.add(String.valueOf(e));
                            String aliErrMsg = TextUtils.join("=", results);
                            a.setResultAndFinish("fail", "channel_returns_fail", aliErrMsg);
                        }
                    } catch (Exception var7) {
                        a.setResultAndFinish("fail", "channel_returns_fail", result);
                    }
                    break;
                case 2:
                    a.setResultAndFinish("success");
                    break;
                case 3:
                default:
                    a.setResultAndFinish("fail", "testmode_notify_failed");
            }

        }

        private int getResultStatus(String result) throws Exception {
            String src = result.replace("{", "");
            src = src.replace("}", "");
            return Integer.parseInt(this.getContent(src, "resultStatus=", ";memo"));
        }

        private String getContent(String src, String startTag, String endTag) throws Exception {
            int start = src.indexOf(startTag);
            start += startTag.length();
            String content;
            if(endTag != null) {
                int end = src.indexOf(endTag);
                content = src.substring(start, end);
            } else {
                content = src.substring(start);
            }

            return content;
        }
    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            this.mContext = c;
        }

        @JavascriptInterface
        public void testmodeResult(String result) {
            if(result == null) {
                PaymentActivity.this.setResultAndFinish("fail", "unknown_error");
            } else {
                if(result.equals("success")) {
                    PaymentActivity.this.setResultAndFinish("success");
                } else if(result.equals("cancel")) {
                    PaymentActivity.this.setResultAndFinish("cancel", "user_cancelled");
                } else if(result.equals("fail")) {
                    PaymentActivity.this.setResultAndFinish("fail", "channel_returns_fail");
                } else if(result.equals("error")) {
                    PaymentActivity.this.setResultAndFinish("fail", "testmode_notify_failed");
                } else {
                    PaymentActivity.this.setResultAndFinish("fail", "unknown_error");
                }

            }
        }
    }
}
