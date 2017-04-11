package com.xcoder.smartpark.fragment.bus;

import android.view.LayoutInflater;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.service.bus.BusMyOrderHistoryService;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by xcoder-xz on 2016/12/18 0018.
 * 我的预约 ---历史预约
 */

public class BusMyOrderHistoryFragment extends BaseFragment {
    private View view;
    @Injection
    BusMyOrderHistoryService bmohs;

    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.bus_my_order_history_main, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        bmohs.init(this);
    }

    @Override
    public void closeFragment() {
        OkHttpUtils.getInstance().cancelTag("BusMyOrderHistoryFragment");
        //清除此对象
        if(bmohs.refreshAndload!=null){
            bmohs.refreshAndload.onDestroy();
            bmohs.refreshAndload=null;
        }
    }
}
