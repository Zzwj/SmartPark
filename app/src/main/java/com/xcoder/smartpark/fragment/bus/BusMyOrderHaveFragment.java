package com.xcoder.smartpark.fragment.bus;

import android.view.LayoutInflater;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.service.bus.BusMyOrderHaveService;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xcoder_xz on 2016/12/18 0018.
 * 我的预约---已预约
 */

public class BusMyOrderHaveFragment extends BaseFragment{
    private View view;
    @Injection
    BusMyOrderHaveService bmofs;

    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.bus_my_order_have_main, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        bmofs.init(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void closeFragment() {
        OkHttpUtils.getInstance().cancelTag("BusMyOrderHaveFragment");
        EventBus.getDefault().unregister(this);
        //清除此对象
        if(bmofs.refreshAndload!=null){
            bmofs.refreshAndload.onDestroy();
            bmofs.refreshAndload=null;
        }
    }

    /**
     * 收到列表adapter中的消息，刷新页面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String s) {
        if("取消预约后刷新列表".equals(s)) {
            bmofs.refreshAndload.immediatelyRefresh();
        }
    }
}
