package com.xcoder.smartpark.activity.bus;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.bus.BusSubscribeService;
import com.xcoder.smartpark.view.bus.BusSubscribeView;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by xcoder_xz on 2016/12/21 0021.
 * 班车 预约
 */
@ContentView(R.layout.bus_subscribe_main)
public class BusSubscribeActivity extends BaseActivity {
    @Injection
    BusSubscribeService bss;

    @Injection
    BusSubscribeView bsv;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        bsv.bus_sub_mapview.onCreate(savedInstanceState);
        bss.intent=getIntent();
        bss.init(this);
    }

    @OnClick({R.id.bus_sub_black, R.id.bus_sub_time_iv,R.id.bus_sub_loca_ll,R.id.bus_sub_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bus_sub_black:
                this.finish();
                break;
            case R.id.bus_sub_time_iv:
                //班车的时间选择
                bss.selectTime();
                break;
            case R.id.bus_sub_loca_ll:
                //设置用户地点
              //  bss.doUserLoca();
                break;
            case R.id.bus_sub_submit:
                //提交预约‘
                bss.doSubmit();
                break;
        }
    }


    @Override
    public void closeActivity() {
        OkHttpUtils.getInstance().cancelTag("BusSubscribeActivity");
    }


    /**
     * 监听Back键按下事件,方法2: 注意: 返回值表示:是否能完全处理该事件 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            try {
                // 如果有选择器弹出，则关闭选择器
                if (bss.timePicker != null && bss.timePicker.isShowing()) {
                    bss.timePicker.dismiss();
                } else {
                    finish();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        bsv.bus_sub_mapview.onDestroy();
        bss.stopLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        bsv.bus_sub_mapview.onResume();
        bss.startLoad();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        bsv.bus_sub_mapview.onPause();
        bss.stopLoad();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        bsv.bus_sub_mapview.onSaveInstanceState(outState);
    }


}
