package com.dongwukj.weiwei.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dongwukj.weiwei.R;

public class AboutUsFragment extends AbstractHeaderFragment {
	//private static String url="http://121.40.60.250:8082/about/aboutusmobile";
	//private static String url="http://121.40.60.250:90/about/aboutusmobile";
	private static String url="http://www.vvlife.com/about/aboutusmobile";
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.about_us_layout, container,false);
		url=getActivity().getIntent().getStringExtra("url");
		return view;
	}

	@Override
	protected String setTitle() {
		return getActivity().getIntent().getStringExtra("title");
	}

	@Override
	protected void findView(View v) {
		final WebView webview=(WebView) v.findViewById(R.id.webview);
		WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient(){
        	@Override
        	public boolean shouldOverrideUrlLoading(WebView view, String url) {
        		webview.loadUrl(url);
        		return true;
        	}
        });
        webview.loadUrl(url);
	}

}
