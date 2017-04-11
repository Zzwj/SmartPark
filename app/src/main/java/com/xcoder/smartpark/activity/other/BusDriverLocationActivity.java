package com.xcoder.smartpark.activity.other;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.other.BusDriverLocationServcice;
import com.xcoder.smartpark.service.other.BusDriverServcice;
import com.xcoder.smartpark.view.other.BusDriverLocationView;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by xcoder-xz on 2016/12/30 0030.
 * 公车司机---车辆位置设置
 */
@ContentView(R.layout.other_bus_driver_location_main)
public class BusDriverLocationActivity extends BaseActivity {

    @Injection
    BusDriverLocationServcice bdls;


    @Injection
    BusDriverLocationView bdlv;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        bdlv.driver_loc_map.onCreate(savedInstanceState);
        bdls.init(this);
    }

    @OnClick({R.id.driver_loc_ll, R.id.driver_loc_sub, R.id.driver_loc_black})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.driver_loc_ll:
                //回到当前点
                bdls. getUserLocation();
                break;
            case R.id.driver_loc_black:
                this.finish();
                break;
            case R.id.driver_loc_sub:
                //提交位置
                bdls.doSubmit();
                break;

        }
    }

    @Override
    public void closeActivity() {
        OkHttpUtils.getInstance().cancelTag("BusDriverLocationActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        bdlv.driver_loc_map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        bdlv.driver_loc_map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        bdlv.driver_loc_map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        bdlv.driver_loc_map.onSaveInstanceState(outState);
    }
}
