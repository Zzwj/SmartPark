package com.xcoder.smartpark.activity.other;

import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragmentActivity;
import com.xcoder.smartpark.service.other.BusDriverSubscribeService;

/**
 * Created by xcoder_xz on 2016/12/31 0031.
 * 其他--公车---预约信息
 */
@ContentView(R.layout.other_bus_driver_subscribe_main)
public class BusDriverSubscribeActivity extends BaseFragmentActivity {
    @Injection
    BusDriverSubscribeService bdss;

    @Override
    public void createActivity() {
        bdss.init(this);
        bdss.selectFragment(bdss.gogingTag);
    }

    @OnClick({R.id.driver_sub_black,R.id.driver_sub_go_bt,R.id.driver_sub_his_bt})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.driver_sub_black:
                this.finish();
                break;
            case R.id.driver_sub_go_bt:
                //预约中
                bdss.selectFragment(bdss.gogingTag);
                break;
            case R.id.driver_sub_his_bt:
                //历史预约
                bdss.selectFragment(bdss.historyTag);
                break;
        }
    }

    @Override
    public void closeActivity() {
        try {
            if (bdss.bDSGFragment != null) {
                bdss.bDSGFragment = null;
            }
            if (bdss.bDSHFragment != null) {
                bdss.bDSHFragment = null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
