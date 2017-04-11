package com.xcoder.smartpark.activity.my;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.my.MyMessageDetailService;

/**
 * Created by xcoder_xz on 2017/1/7 0007.
 * 公告详情
 */
@ContentView(R.layout.my_message_detail_main)
public class MyMessageDetailActivity extends BaseActivity {
    @Injection
    MyMessageDetailService mmds;
    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        mmds.init(this);
    }

    @OnClick({R.id.my_message_detail_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_message_detail_back:
                this.finish();
                break;
        }
    }

    @Override
    public void closeActivity() {

    }
}
