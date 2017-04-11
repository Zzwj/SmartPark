package com.xcoder.smartpark.activity.other;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.other.BusDriverServcice;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by xcoder-xz on 2016/12/30 0030.
 * 公车司机
 */
@ContentView(R.layout.other_bus_driver_main)
public class BusDriverActivity extends BaseActivity {

    @Injection
    BusDriverServcice bds;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        bds.init(this);
    }

    @OnClick({R.id.bus_driver_black, R.id.bus_driver_sub,R.id.bus_driver_location,R.id.bus_driver_location2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bus_driver_black:
                this.finish();
                break;
            case R.id.bus_driver_sub:
                //预约信息
                Bundle bundle=new Bundle();
                bundle.putString("","");
                //携带值，跳转
                openActivity(BusDriverSubscribeActivity.class,bundle);
                break;
            case R.id.bus_driver_location:
                //查看车辆位置
                Bundle b=new Bundle();
                b.putSerializable("carmsg",bds.carmsg);
                openActivity(BusDriverLocationActivity.class, b);
                break;

            case R.id.bus_driver_location2:
                //查看车辆位置
                Bundle b2=new Bundle();
                b2.putSerializable("carmsg",bds.carmsg2);
                openActivity(BusDriverLocationActivity.class, b2);
                break;
        }
    }

    @Override
    public void closeActivity() {
        try{
            OkHttpUtils.getInstance().cancelTag("BusDriverActivity");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
