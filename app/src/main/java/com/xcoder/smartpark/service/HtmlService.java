package com.xcoder.smartpark.service;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.activity.HtmlActivity;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.HtmlView;

/**
 * Created by xcoder_xz on 2017/1/1 0001.
 * <p>
 * 通用h5显示页面
 */

public class HtmlService {
    @Injection
    HtmlView hv;

    private HtmlActivity htmlActivity;
    private Intent intent;
    private String htmlUrl;

    public void init(HtmlActivity htmlActivity) {
        this.htmlActivity = htmlActivity;
        intent = htmlActivity.getIntent();
        initView();

    }

    public void initData() {
        if(TextUtils.isEmpty(htmlUrl)){
            ToastUtil.showToast("Url错误");
            return;
        }
        hv.html_webview.getSettings().setAppCacheEnabled(false);
//    //    hv.html_webview.getSettings().setDomStorageEnabled(true);
//        hv.html_webview.getSettings().setUseWideViewPort(true);
//      //  hv.html_webview.setWebViewClient(new MyWebViewClient());
//      //  hv.html_webview.setWebChromeClient(new MyWebChrome());
//        hv.html_webview.getSettings().setLoadWithOverviewMode(true);
//     //   hv.html_webview.setDownloadListener(new MyWebViewDownLoadListener());
//        //启动缓存
//        hv.html_webview.getSettings().setAppCacheEnabled(true);
//        //设置缓存模式
//        hv.html_webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        hv.html_webview.getSettings().setJavaScriptEnabled(true);
//      //  hv.html_webview.addJavascriptInterface(new JsOperation(ctx), "client");
        hv.html_webview.getSettings().setUseWideViewPort(true);

        hv.html_webview.getSettings().setBuiltInZoomControls(true);
        hv.html_webview.getSettings().setSupportZoom(true);
        hv.html_webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);// 默认缩放模式

        hv.html_webview.getSettings().setDomStorageEnabled(true);
        hv.html_webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });



        hv.html_webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    hv.html_progressbar.setVisibility(View.GONE);
                } else {
                    if (View.INVISIBLE == hv.html_progressbar.getVisibility()) {
                        hv.html_progressbar.setVisibility(View.VISIBLE);
                    }
                    hv.html_progressbar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        hv.html_webview.loadUrl(htmlUrl);
    }

    public void initView() {
        if (intent != null && intent.getStringExtra("htmlTitle") != null && intent.getStringExtra("htmlUrl") != null) {
            //设置显示标题
            hv.html_head_title.setText(intent.getStringExtra("htmlTitle"));
            htmlUrl = intent.getStringExtra("htmlUrl");
            initData();
        }
    }
}
