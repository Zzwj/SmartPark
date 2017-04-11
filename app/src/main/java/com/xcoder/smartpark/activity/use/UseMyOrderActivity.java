package com.xcoder.smartpark.activity.use;

import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragmentActivity;
import com.xcoder.smartpark.service.use.UseMyOrderService;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by xcoder-xz on 2016/12/19 0019.
 * 用车  我的预约
 */
@ContentView(R.layout.use_my_order_main)
public class UseMyOrderActivity extends BaseFragmentActivity {

    @Injection
    UseMyOrderService umos;

    @Override
    public void createActivity() {
        umos.init(this);
        umos.selectFragment(umos.futureTag);
    }

    @OnClick({R.id.use_order_black, R.id.use_order_future_bt, R.id.use_order_history_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.use_order_black:
                //退出
                this.finish();
                break;
            case R.id.use_order_future_bt:
                umos.selectFragment(umos.futureTag);
                break;
            case R.id.use_order_history_bt:
                umos.selectFragment(umos.historyTag);
                break;
        }
    }


    @Override
    public void closeActivity() {

    }
}
