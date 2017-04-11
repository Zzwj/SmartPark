package com.xcoder.smartpark.fragment.other;

import android.view.LayoutInflater;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.service.other.AuditWaitService;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by xcoder_xz on 2016/12/30 0030.
 * 用车审批 待审批
 */

public class AuditWaitFragment extends BaseFragment{
    private View view;

    @Injection
    AuditWaitService aws;

    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.other_audit_wait_main, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        aws.init(this);
    }


    @Override
    public void closeFragment() {
        OkHttpUtils.getInstance().cancelTag("AuditWaitFragment");

    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d("AuditWaitFragment---onResume---");
    }
}
