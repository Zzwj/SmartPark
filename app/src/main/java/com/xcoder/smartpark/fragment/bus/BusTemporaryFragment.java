package com.xcoder.smartpark.fragment.bus;

import android.view.LayoutInflater;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.service.bus.BusTemporaryService;

/**
 * Created by xcoder_xz on 2016/12/19 0019.
 * 班车 临时
 */

public class BusTemporaryFragment extends BaseFragment{
    @Injection
    BusTemporaryService bts;

    private View view;
    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.bus_temporary_main, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        bts.init(this);
    }

    @Override
    public void closeFragment() {
        //清除此对象
        if(bts.refreshAndload!=null){
            bts.refreshAndload.onDestroy();
            bts.refreshAndload=null;
        }
    }
}
