package com.xcoder.smartpark.activity.my;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.my.MyMessageService;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by Administrator on 2016/12/16 0016.
 * 我的-我的公告
 */
@ContentView(R.layout.my_message_main)
public class MyMessageActivity extends BaseActivity {

    @Injection
    MyMessageService mms;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        mms.init(this);
    }

    @OnClick(R.id.my_message_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_message_back:
                this.finish();
                break;
        }
    }

    @Override
    public void closeActivity() {
        try {
            OkHttpUtils.getInstance().cancelTag("MyMessageActivity");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
