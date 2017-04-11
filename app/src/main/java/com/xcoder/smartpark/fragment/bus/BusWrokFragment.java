package com.xcoder.smartpark.fragment.bus;

import android.view.LayoutInflater;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.service.bus.BusWrokService;

/**
 * Created by Administrator on 2016/12/14 0014.
 *  班车---上班
 */

public class BusWrokFragment extends BaseFragment {
    private View view;
    @Injection
    BusWrokService bws;

    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.bus_wrok_main, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        bws.init(this);
    }

    @Override
    public void closeFragment() {
        //清除此对象
        if(bws.refreshAndload!=null){
            bws.refreshAndload.onDestroy();
            bws.refreshAndload=null;
        }
    }
}
