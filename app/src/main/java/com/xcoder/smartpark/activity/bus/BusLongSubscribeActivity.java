//package com.xcoder.smartpark.activity.bus;
//
//import android.os.Bundle;
//import android.view.View;
//
//import com.xcoder.lib.annotation.ContentView;
//import com.xcoder.lib.annotation.Injection;
//import com.xcoder.lib.annotation.event.OnClick;
//import com.xcoder.smartpark.R;
//import com.xcoder.smartpark.app.base.BaseActivity;
//import com.xcoder.smartpark.service.bus.BusLongSubscribeService;
//import com.xcoder.smartpark.view.bus.BusLongSubscribeView;
//import com.zhy.http.okhttp.OkHttpUtils;
//
///**
// * Created by xcoder_xz on 2016/12/24 0024.
// *  班车 长期预约
// */
//@ContentView(R.layout.bus_long_subscribe_main)
//public class BusLongSubscribeActivity extends BaseActivity{
//
//    @Injection
//    BusLongSubscribeService blss;
//
//    @Injection
//    BusLongSubscribeView blsv;
//
//    @Override
//    public void onXCoderCreate(Bundle savedInstanceState) {
//        blsv.bus_sub_mapview.onCreate(savedInstanceState);
//        blss.intent=getIntent();
//        blss.init(this);
//    }
//
//
//    @OnClick({R.id.bus_sub_black, R.id.bus_sub_loca_ll,R.id.bus_sub_submit})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.bus_sub_black:
//                this.finish();
//                break;
//            case R.id.bus_sub_loca_ll:
//
//                blss.doUserLoca();
//                break;
//            case R.id.bus_sub_submit:
//                //提交
//                blss.doSubmit();
//                break;
//        }
//    }
//
//    @Override
//    public void closeActivity() {
//        OkHttpUtils.getInstance().cancelTag("BusLongSubscribeActivity");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
//        blsv.bus_sub_mapview.onDestroy();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
//        blsv.bus_sub_mapview.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
//        blsv.bus_sub_mapview.onPause();
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
//        blsv.bus_sub_mapview.onSaveInstanceState(outState);
//    }
//}
