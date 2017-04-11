package com.xcoder.smartpark.activity.bus;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.bus.BusMyOrderLocationService;
import com.xcoder.smartpark.view.bus.BusMyOrderLocationView;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by xcoder-xz on 2016/12/25 0025.
 * 实时位置
 */
@ContentView(R.layout.bus_my_order_location_main)
public class BusMyOrderLocationActivity extends BaseActivity {

    @Injection
    BusMyOrderLocationService bmls;

    @Injection
    BusMyOrderLocationView bmlv;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        bmlv.bus_location_map.onCreate(savedInstanceState);
        bmls.init(this);

    }

    @OnClick({R.id.bus_location_black,R.id.bus_location_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bus_location_black:
                this.finish();
                break;
            case R.id.bus_location_ll:
                //得到用户当前的点
                bmls.getUserLocation();
                break;
        }
    }

    @Override
    public void closeActivity() {
        //停止这个activity的所有请求
        OkHttpUtils.getInstance().cancelTag("BusMyOrderLocationActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        bmlv.bus_location_map.onDestroy();
        bmls.stopLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        bmlv.bus_location_map.onResume();
        bmls.startLoad();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        bmlv.bus_location_map.onPause();
        bmls.stopLoad();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        bmlv.bus_location_map.onSaveInstanceState(outState);
    }

}
