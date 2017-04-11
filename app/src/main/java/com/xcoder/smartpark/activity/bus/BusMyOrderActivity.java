package com.xcoder.smartpark.activity.bus;

import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragmentActivity;
import com.xcoder.smartpark.moudel.Visitor;
import com.xcoder.smartpark.service.bus.BusMyOrderService;
import com.xcoder.smartpark.widget.dialog.ShareSubDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xcoder-xz on 2016/12/18 0018.
 * 班车---我的预约
 */
@ContentView(R.layout.bus_my_order_main)
public class BusMyOrderActivity extends BaseFragmentActivity {
    @Injection
    BusMyOrderService bmos;

    @Override
    public void createActivity() {
        bmos.init(this);
        bmos.selectFragment(bmos.haveTag);
    }


    @OnClick({R.id.my_order_black, R.id.my_order_have_bt,R.id.my_order_history_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_order_black:
                this.finish();
                break;
            case R.id.my_order_have_bt:
                bmos.selectFragment(bmos.haveTag);
                break;
            case R.id.my_order_history_bt:
                bmos.selectFragment(bmos.historyTag);
                break;
        }
    }


    @Override
    public void closeActivity() {

    }





}
