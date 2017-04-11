package com.xcoder.smartpark.activity.bus;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragmentActivity;
import com.xcoder.smartpark.service.bus.BusListService;

/**
 * Created by xcoder_ on 2017/3/22 0022.
 * 班车预约
 */
@ContentView(R.layout.bus_list_activity)
public class BusListActivity extends BaseFragmentActivity{

@Injection
    BusListService bls;
    @Override
    public void createActivity() {
        bls.init(this);
        bls.selectFragment(bls.wrokTag);
    }

    @OnClick({ R.id.bus_main_home_bt, R.id.bus_main_wrok_bt, R.id.bus_main_temporary_bt,R.id.bus_list_black})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bus_list_black:
                finish();
                break;
            case R.id.bus_main_home_bt:
                //回家
                bls.selectFragment(bls.homeTag);
                break;
            case R.id.bus_main_wrok_bt:
                //上班
                bls.selectFragment(bls.wrokTag);
                break;
            case R.id.bus_main_temporary_bt:
                //临时
                bls.selectFragment(bls.temporaryTag);
                break;
        }
    }

    @Override
    public void closeActivity() {

    }
}
