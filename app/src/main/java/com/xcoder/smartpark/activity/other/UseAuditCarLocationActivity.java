package com.xcoder.smartpark.activity.other;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.other.UseAuditCarLocationService;
import com.xcoder.smartpark.view.other.UseAuditCarLocationView;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by xcoder-xz on 2016/12/30 0030.
 * 用车审核----车辆位置
 */
@ContentView(R.layout.other_use_audit_car_location_main)
public class UseAuditCarLocationActivity extends BaseActivity {

    @Injection
    UseAuditCarLocationService uacls;

    @Injection
    UseAuditCarLocationView uaclv;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        uaclv.audit_location_map.onCreate(savedInstanceState);
        uacls.init(this);
    }

    @Override
    public void closeActivity() {
        OkHttpUtils.getInstance().cancelTag("UseAuditCarLocationActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        uaclv.audit_location_map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        uaclv.audit_location_map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        uaclv.audit_location_map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        uaclv.audit_location_map.onSaveInstanceState(outState);
    }

    @OnClick({R.id.audit_location_map_black})
    public void Onclick(View view) {
        switch (view.getId()) {
            case R.id.audit_location_map_black:
                finish();
                break;
        }
    }
}
