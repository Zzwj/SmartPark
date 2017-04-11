package com.xcoder.smartpark.fragment.use;

import android.view.LayoutInflater;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.service.use.UseMyOrderHistoryService;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by xcoder_xz on 2016/12/19 0019.
 * 用车--我的预约---历史预约
 */

public class UseMyOrderHistoryFragment extends BaseFragment {
    private View view;
    @Injection
    UseMyOrderHistoryService umohs;

    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.use_my_order_history_main, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        umohs.init(this);
    }

    @Override
    public void closeFragment() {
        OkHttpUtils.getInstance().cancelTag("UseMyOrderHistoryFragment");
        //清除此对象
        if(umohs.refreshAndload!=null){
            umohs.refreshAndload.onDestroy();
            umohs.refreshAndload=null;
        }
    }
}
