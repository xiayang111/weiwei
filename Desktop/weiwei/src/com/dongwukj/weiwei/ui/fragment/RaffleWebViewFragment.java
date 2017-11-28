package com.dongwukj.weiwei.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dongwukj.weiwei.R;

public class RaffleWebViewFragment extends AbstractHeaderFragment {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bannerfragment_web, null);
		return view;
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void findView(View v) {
		String url = activity.getIntent().getStringExtra("lotteryUrl");
		WebView webview=(WebView) v.findViewById(R.id.webview);
		WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        
		webview.addJavascriptInterface(new WebInterface(),"MyAndroid");
		
		webview.setWebChromeClient(new WebChromeClient(){

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				// TODO Auto-generated method stub
				result.confirm();
				return false;
			}
			
		});
		
        webview.setWebViewClient(new WebViewClient(){
        	public void onPageStarted(WebView view, String url, Bitmap favicon) {
        		super.onPageStarted(view, url, favicon);
				//判断网络
				view.loadUrl("javascript:"+javaScriptString);
				progressDialog.setMessage("加载中...");
        		showProgress(true);
            }
        	@Override
        	public void onPageFinished(WebView view, String url) {
        		// TODO Auto-generated method stub
        		super.onPageFinished(view, url);
        		showProgress(false);
        		view.loadUrl("javascript:bindBack()");
        		//view.loadUrl(javaScriptString);
        	}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				if(errorCode!=200){
					view.loadUrl("<html><body>加载失败</body></html>");
				}
			}
		});
		
		webview.loadUrl(url);
	}

	private static final String javaScriptString="function bindBack(){\n" +
			"\t\tdocument.getElementsByClassName(\"J_back\")[0].href=\"javascript:MyAndroid.back()\";\n" +
			"\t}";



	public class WebInterface{
		@JavascriptInterface
		public void back(){
			activity.finish();
		}
	}

}
