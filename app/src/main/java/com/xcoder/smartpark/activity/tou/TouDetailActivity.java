package com.xcoder.smartpark.activity.tou;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.tou.TouDetailService;

/**
 * Created by xcoder_xz on 2016/12/15 0015.
 * 访客详情
 */
@ContentView(R.layout.tou_detail_main)
public class TouDetailActivity extends BaseActivity {

    @Injection
    TouDetailService tds;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        tds.init(this);
    }

    @OnClick({R.id.tou_detail_black, R.id.tou_detail_phone_ll, R.id.tou_detail_refuse, R.id.tou_detail_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tou_detail_black:
                //后退
                this.finish();
                break;
            case R.id.tou_detail_phone_ll:
                //需要拨打电话
                tds.toPhone();
                break;
            case R.id.tou_detail_refuse:
                //拒绝
                tds.refuseTou();
                break;
            case R.id.tou_detail_confirm:
                //接受
                tds.confirmTou();
                break;
        }
    }

    @Override
    public void closeActivity() {

    }
}
