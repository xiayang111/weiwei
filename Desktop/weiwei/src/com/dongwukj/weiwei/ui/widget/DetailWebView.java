//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dongwukj.weiwei.ui.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class DetailWebView extends WebView {
    public static final String FRAME = "<html><head><style type=\"text/css\">div#header {height:MY_TOP_MARGINpx} div#footer {height:MY_BOTTOM_MARGINpx} </style></head><body><div id=\"header\"></div><div> DESCRIPT </div><div id=\"footer\">\n</div></body></html>";
    private int bottom;
    private String brandID;
    boolean initedData = false;
    boolean isStart = true;
    private boolean loaded;
    private String mHtmlString;
    private String mProductID;
    private DetailWebView.OnScrollListener onScrollListener;
    ProgressBar progressBar;
    private int top;

    public DetailWebView(Context var1) {
        super(var1);
        //this.initView();
    }

    public DetailWebView(Context var1, AttributeSet var2) {
        super(var1, var2);
        //this.initView();
    }

    public DetailWebView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        //this.initView();
    }

    private void initView() {
        WebSettings var1 = this.getSettings();
        var1.setUseWideViewPort(true);
        if(VERSION.SDK_INT >= 11) {
            var1.setBuiltInZoomControls(true);
            var1.setDisplayZoomControls(false);
            var1.setSupportZoom(true);
        } else {
            var1.setBuiltInZoomControls(false);
            var1.setSupportZoom(false);
        }

        var1.setLoadWithOverviewMode(true);
        var1.setJavaScriptEnabled(true);
        var1.setDefaultTextEncodingName("utf-8");
        var1.setDefaultFontSize(32);
        Context var2 = this.getContext();
        this.progressBar = new ProgressBar(this.getContext(), (AttributeSet)null, 16842872);
        this.addView(this.progressBar, -1, 6);
        this.progressBar.setVisibility(GONE);
    }



    protected void onScrollChanged(int var1, int var2, int var3, int var4) {
        super.onScrollChanged(var1, var2, var3, var4);
        if(this.onScrollListener != null) {
            boolean var5 = this.isStart;
            boolean var6;
            if(var2 <= 30) {
                var6 = true;
            } else {
                var6 = false;
            }

            if(var6 ^ var5) {
                boolean var7 = this.isStart;
                boolean var8 = false;
                if(!var7) {
                    var8 = true;
                }

                this.isStart = var8;
                this.onScrollListener.onScrollToStart(this.isStart);
            }
        }

    }

    public void scrollToTop() {
        this.flingScroll(0, 1);
        this.postDelayed(new Runnable() {
            public void run() {
                DetailWebView.this.isStart = true;
                DetailWebView.this.scrollTo(0, 0);
            }
        }, 500L);
    }

    public void setData(String var1, String var2, String var3) {
        this.brandID = var1;
        this.mProductID = var2;
        this.mHtmlString = var3;
        this.initedData = true;
        if(this.top > 0 && this.bottom > 0) {
           // this.loadContent();
        }

    }

    public void setOnScrollListener(DetailWebView.OnScrollListener var1) {
        this.onScrollListener = var1;
    }

    public void setVerticalOffset(int var1, int var2) {
        this.top = var1;
        this.bottom = var2;
        if(this.initedData) {
            //this.loadContent();
        }

    }

    public interface OnScrollListener {
        void onScrollToStart(boolean var1);
    }
}
