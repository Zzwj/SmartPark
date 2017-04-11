package com.xcoder.smartpark.view;

import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2017/1/1 0001.
 * 通用h5显示页面
 */

public class HtmlView {
    @ViewInject(R.id.html_head_title)
    public TextView html_head_title;

    @ViewInject(R.id.html_webview)
    public WebView html_webview;

    @ViewInject(R.id.html_progressbar)
    public ProgressBar html_progressbar;
}
