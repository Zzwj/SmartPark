package com.xcoder.smartpark.fragment.other;

import android.view.LayoutInflater;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.moudel.Visitor;
import com.xcoder.smartpark.service.other.BusDriverSubscribeGoingService;
import com.xcoder.smartpark.widget.dialog.ShareSubDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xcoder_xz on 2016/12/31 0031.
 * 其他--公车---预约信息---预约中
 */

public class BusDriverSubscribeGoingFragment extends BaseFragment{
    private View view;

    @Injection
    BusDriverSubscribeGoingService bdsgs;

    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.other_bus_driver_subscribe_going_main, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        bdsgs.init(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void closeFragment() {
        try{
            EventBus.getDefault().unregister(this);
            OkHttpUtils.getInstance().cancelTag("BusDriverSubscribeGoingFragment");
            if(bdsgs.refreshAndload!=null){
                bdsgs.refreshAndload.onDestroy();
                bdsgs.refreshAndload=null;
            }
            if(bdsgs.driverSubscribeGoingAdapter!=null){
                bdsgs.driverSubscribeGoingAdapter=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String s) {
        if("设置状态成功，刷新预约中列表".equals(s)) {
            bdsgs.refreshAndload.immediatelyRefresh();
        }
    }

}
