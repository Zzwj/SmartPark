package com.xcoder.smartpark.fragment.other;

import android.view.LayoutInflater;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.service.other.AuditAlreadyService;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xcoder_xz on 2016/12/30 0030.
 * 用车审核---已审核
 */

public class AuditAlreadyFragment extends BaseFragment{
    private View view;
    @Injection
    AuditAlreadyService aas;


    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.other_audit_already_main, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了
        return view;
    }


    @Override
    public void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        aas.init(this);
    }

    @Override
    public void closeFragment() {
        OkHttpUtils.getInstance().cancelTag("AuditAlreadyFragment");
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String s) {
        if(s.equals("请已审批刷新")) {
            LogUtils.d("AuditAlreadyFragment收到EventBus消息:"+s);
            aas.refreshAndload.immediatelyRefresh();
        }
    }
}
