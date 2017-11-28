package com.dongwukj.weiwei.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dongwukj.weiwei.R;

public class BannerWebViewFragment extends AbstractHeaderFragment {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bannerfragment_web, null);
		return view;
	}

	@Override
	protected String setTitle() {
		
		return null;
	}

	@Override
	protected void findView(View v) {
		String url = activity.getIntent().getStringExtra("url");
		WebView webview=(WebView) v.findViewById(R.id.webview);
		WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient(){
        	@Override
        	public boolean shouldOverrideUrlLoading(WebView view, String url) {
        		// TODO Auto-generated method stub
        		return super.shouldOverrideUrlLoading(view, url);
        	}
        });
	}

}
