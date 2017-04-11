package com.xcoder.smartpark.view.my;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by jiangkun on 16/12/16.
 */

public class MySettingView {

    @ViewInject(R.id.my_setting_about_rl)
    public RelativeLayout my_setting_about_rl;//关于

    @ViewInject(R.id.my_setting_explain_rl)
    public RelativeLayout my_setting_explain_rl;//使用说明

    @ViewInject(R.id.my_setting_cache_tv)
    public TextView my_setting_cache_tv;//缓存

    @ViewInject(R.id.my_setting_out_rl)
    public RelativeLayout my_setting_out_rl;//退出登陆

    @ViewInject(R.id.my_mysetting_appupdate_rl)
    public RelativeLayout my_mysetting_appupdate_rl;//app更新

    @ViewInject(R.id.my_mysetting_appupdate_code)
    public TextView my_mysetting_appupdate_code;//当前app版本

}
