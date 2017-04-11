package com.xcoder.smartpark.view;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/5 0005.
 * 这个类用于初始化MainActivity的View
 */

public class MainView {
    @ViewInject(R.id.main_rg)
    public RadioGroup main_rg;

    @ViewInject(R.id.main_rb1)
    public RadioButton main_rb1;

    @ViewInject(R.id.main_rb2)
    public RadioButton main_rb2;

    @ViewInject(R.id.main_rb3)
    public RadioButton main_rb3;

    @ViewInject(R.id.main_rb4)
    public RadioButton main_rb4;

    @ViewInject(R.id.main_layout)
    public RelativeLayout main_layout;

   ///----------角色按钮快//

    @ViewInject(R.id.main_other)
    public ImageButton main_other;//角色按钮

    @ViewInject(R.id.main_other_bt)
    public LinearLayout main_other_bt;//按纽


    @ViewInject(R.id.other_patrol_man_ll)
    public LinearLayout other_patrol_man_ll;//巡更路线

    @ViewInject(R.id.other_money_ll)
    public LinearLayout other_money_ll;//资产盘查

    @ViewInject(R.id.other_audit_ll)
    public LinearLayout other_audit_ll;//用车审批

    @ViewInject(R.id.other_driver_ll)
    public LinearLayout other_driver_ll;//公车司机

    @ViewInject(R.id.other_bus_ll)
    public LinearLayout other_bus_ll;//班车信息

    @ViewInject(R.id.other_user_ll)
    public LinearLayout other_user_ll;//人员定位


}
