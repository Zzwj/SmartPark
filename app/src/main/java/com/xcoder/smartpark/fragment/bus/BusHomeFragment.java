package com.xcoder.smartpark.fragment.bus;


import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.service.bus.BusHomeService;
import com.xcoder.smartpark.util.ToastUtil;

import org.json.JSONObject;

/**
 * Created by xcoder_xz on 2016/12/14 0014.
 * 班车--回家
 */

public class BusHomeFragment extends BaseFragment {
    private View view;
    @Injection
    BusHomeService bhs;

    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.bus_home_main, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        bhs.init(this);


    }

    @Override
    public void closeFragment() {
        //清除此对象
        if(bhs.refreshAndload!=null){
            bhs.refreshAndload.onDestroy();
            bhs.refreshAndload=null;
        }
    }
}
