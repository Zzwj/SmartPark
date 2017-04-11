package com.xcoder.smartpark.activity.my;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.my.MyMoneyService;

/**
 * Created by Administrator on 2016/12/16 0016.
 * 我的--我的钱包
 */
@ContentView(R.layout.my_mymoney_main)
public class MyMoneyActivity extends BaseActivity {

    @Injection
    MyMoneyService mms;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        mms.init(this);
    }

    @Override
    public void closeActivity() {
        
    }

    @OnClick({R.id.my_mymoney_black})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.my_mymoney_black:
                finish();
                break;
        }
    }




}
