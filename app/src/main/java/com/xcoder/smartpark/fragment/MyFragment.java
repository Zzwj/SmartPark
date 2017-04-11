package com.xcoder.smartpark.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.my.MyInfoActivity;
import com.xcoder.smartpark.activity.my.MyMessageActivity;
import com.xcoder.smartpark.activity.my.MyMoneyActivity;
import com.xcoder.smartpark.activity.my.MySettingActivity;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.moudel.Visitor;
import com.xcoder.smartpark.service.my.MyService;
import com.xcoder.smartpark.widget.dialog.ShareSubDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xcoder_xz on 2016/12/6 0006.
 * 我的 fragment
 */

public class MyFragment extends BaseFragment {
    private View view;
    @Injection
    MyService ms;

    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.my_fragment_main, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了
        return view;
    }

    /**
     * 开始数据操作
     */
    @Override
    public void initData() {
        super.initData();
        ms.init(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void closeFragment() {
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.my_mysetting_iv, R.id.my_message_rl, R.id.my_money_rl,R.id.my_edit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_mysetting_iv:
                openActivity(MySettingActivity.class);
                break;
            case R.id.my_message_rl:
                //我的公告
                openActivity(MyMessageActivity.class);
                break;
            case R.id.my_money_rl:
                //我的钱包
                openActivity(MyMoneyActivity.class);
                break;
            case R.id.my_edit_tv:
                openActivity(MyInfoActivity.class);
                break;

        }
    }


    /**
     * 用户提交信息成功后，刷新当前界面信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String s) {
        if("刷新用户信息".equals(s)) {
            ms.refresh();
        }
    }
}
