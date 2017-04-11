package com.xcoder.smartpark.activity.use;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.use.UseCarLocationService;
import com.xcoder.smartpark.view.use.UseCarLocationView;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by xcoder_xz on 2016/12/29 0029.
 * 用车--我的预约---查看车两位置信息
 */
@ContentView(R.layout.use_car_location_main)
public class UseCarLocationActivity extends BaseActivity {
    @Injection
    UseCarLocationView uclv;

    @Injection
    UseCarLocationService ucls;


    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        uclv.use_car_map.onCreate(savedInstanceState);
        ucls.init(this);
    }

    @OnClick({R.id.use_car_black, R.id.use_car_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.use_car_black:
                this.finish();
                break;
            case R.id.use_car_ll:
                //点击获取当前位置
                ucls.getUserLocation();
                break;
        }
    }

    @Override
    public void closeActivity() {
        OkHttpUtils.getInstance().cancelTag("UseCarLocationActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        uclv.use_car_map.onDestroy();
        ucls.stopLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        uclv.use_car_map.onResume();

        // 重新加载的时候，要判断地图是否加载，需要重新唤起车辆位置循环
        ucls.startLoad();

    }



    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        uclv.use_car_map.onPause();
        //停止循环和访问接口
        ucls.stopLoad();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        uclv.use_car_map.onSaveInstanceState(outState);
    }

}
