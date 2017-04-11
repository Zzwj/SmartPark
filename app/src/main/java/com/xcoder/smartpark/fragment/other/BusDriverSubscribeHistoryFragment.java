package com.xcoder.smartpark.fragment.other;

import android.view.LayoutInflater;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.service.other.BusDriverSubscribeGoingService;
import com.xcoder.smartpark.service.other.BusDriverSubscribeHistoryService;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xcoder_xz on 2016/12/31 0031.
 * 其他--公车---预约信息---历史预约
 */

public class BusDriverSubscribeHistoryFragment extends BaseFragment{

@Injection
    BusDriverSubscribeHistoryService bdshs;

    private View view;
    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.other_bus_driver_subscribe_history_main, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        bdshs.init(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void closeFragment() {
        try{
            EventBus.getDefault().unregister(this);
            OkHttpUtils.getInstance().cancelTag("BusDriverSubscribeHistoryFragment");
            if(bdshs.refreshAndload!=null){
                bdshs.refreshAndload.onDestroy();
                bdshs.refreshAndload=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String s) {
        if("设置状态成功，刷新预约中列表".equals(s)) {
            bdshs.refreshAndload.immediatelyRefresh();
        }
    }
}
