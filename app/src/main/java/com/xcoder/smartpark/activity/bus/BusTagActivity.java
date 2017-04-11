package com.xcoder.smartpark.activity.bus;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.bus.BusTagService;
import com.xcoder.smartpark.view.bus.BusTagView;

/**
 * Created by xcoder-xz on 2016/12/22 0022.
 * 常驻标注
 */
@ContentView(R.layout.bus_tag_main)
public class BusTagActivity extends BaseActivity {
    @Injection
    BusTagService bts;

    @Injection
    BusTagView btv;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        btv.bus_tag_map.onCreate(savedInstanceState);
        bts.init(this);
    }

    @OnClick({R.id.bus_tag_black,R.id.bus_tag_loca_ll,R.id.bus_tag_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bus_tag_black:
                finish();
                break;
            case R.id.bus_tag_loca_ll:
                bts.doUserLoca();
                break;
            case R.id.bus_tag_submit:
                //提交标注
                bts.doSubmit();
                break;
        }
    }

    @Override
    public void closeActivity() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        btv.bus_tag_map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        btv.bus_tag_map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        btv.bus_tag_map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        btv.bus_tag_map.onSaveInstanceState(outState);
    }
}
