package com.dongwukj.weiwei.ui.fragment;

import java.io.File;
import java.io.IOException;

import com.dongwukj.weiwei.R;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("JavascriptInterface")
public class UserAgreementFragment extends AbstractHeaderFragment {

	private WebView agreemente_webview;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bannerfragment_web, null);
		return view;
	}

	@Override
	protected String setTitle() {
		return getResources().getString(R.string.user_agreement);
	}

	@Override
	protected void findView(View v) {
		agreemente_webview = (WebView) v.findViewById(R.id.webview);
		agreemente_webview.addJavascriptInterface(new MyAndroid(), "MyAndroid");
		
		
		WebSettings settings = agreemente_webview.getSettings();
		settings.setJavaScriptEnabled(true);     //设置支持JavaScript脚本
		settings.setBuiltInZoomControls(false);  ;// 设置支持缩放
		settings.setAllowFileAccess(true);		 // 设置允许访问文件数据
	 
		Uri uri=Uri.parse("file:///android_asset/user_agreement.html");
		agreemente_webview.loadUrl("file:/android_asset/user_agreement.html");
	 

	}
	
	
	
	public class MyAndroid {
		
		@JavascriptInterface
		 public void back(){
			 activity.finish();
		 }
	}

}
