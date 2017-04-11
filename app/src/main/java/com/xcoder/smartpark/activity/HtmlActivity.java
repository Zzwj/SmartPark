package com.xcoder.smartpark.activity;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.HtmlService;

/**
 * Created by xcoder on 2017/1/1 0001.
 * 通 用于显示h5页面的
 */
@ContentView(R.layout.html_main)
public class HtmlActivity extends BaseActivity {

    @Injection
    HtmlService hs;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        hs.init(this);
    }

    @OnClick({R.id.html_black})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.html_black:
                this.finish();
                break;
        }
    }

    @Override
    public void closeActivity() {

    }
}
