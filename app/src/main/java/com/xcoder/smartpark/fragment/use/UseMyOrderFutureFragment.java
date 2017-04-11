package com.xcoder.smartpark.fragment.use;

import android.view.LayoutInflater;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.service.use.UseMyOrderFutureService;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by xcoder-xz on 2016/12/19 0019.
 * 用车--我的预约--预约未出行
 */

public class UseMyOrderFutureFragment extends BaseFragment {
    private View view;
    @Injection
    UseMyOrderFutureService umofs;

    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.use_my_order_future_main, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        umofs.init(this);
    }

    @Override
    public void closeFragment() {
        try {
            OkHttpUtils.getInstance().cancelTag("UseMyOrderFutureFragment");
            //清除此对象
            if (umofs.refreshAndload != null) {
                umofs.refreshAndload.onDestroy();
                umofs.refreshAndload = null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
