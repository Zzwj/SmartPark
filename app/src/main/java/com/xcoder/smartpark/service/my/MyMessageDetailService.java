package com.xcoder.smartpark.service.my;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.activity.my.MyMessageDetailActivity;
import com.xcoder.smartpark.view.my.MyMessageDetailView;

/**
 * Created by xcoder_xz on 2017/1/7 0007.
 * 公告详情
 */

public class MyMessageDetailService {

    @Injection
    MyMessageDetailView mmdv;
    private MyMessageDetailActivity mmdActivity;

    public void init(MyMessageDetailActivity mmdActivity) {
        this.mmdActivity = mmdActivity;
        initView();
    }

    public void initView() {
        try {
            if (mmdActivity.getIntent() != null) {
                mmdActivity.getIntent().getStringExtra("contentDetail");
                mmdv.my_message_detail_tv.setText(mmdActivity.getIntent().getStringExtra("contentDetail")+"");
                mmdv.my_message_detail_title.setText(mmdActivity.getIntent().getStringExtra("contentTitle")+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
