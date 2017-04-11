package com.xcoder.smartpark.view.use;

import android.support.design.widget.TextInputEditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder-xz on 2016/12/22 0022.
 * 用车--预约
 */

public class UseSubscribeView {
  /*  @ViewInject(R.id.timepicker)
    public LinearLayout timepicker;

    @ViewInject(R.id.year)
    public WheelView year;

    @ViewInject(R.id.month)
    public WheelView month;

    @ViewInject(R.id.day)
    public WheelView day;

    @ViewInject(R.id.hour)
    public WheelView hour;

    @ViewInject(R.id.min)
    public WheelView min;*/

    @ViewInject(R.id.use_sub_scroll)
    public ScrollView use_sub_scroll;

    @ViewInject(R.id.use_sub_head)
    public ImageView use_sub_head;

    @ViewInject(R.id.use_sub_det)
    public TextView use_sub_det;

    @ViewInject(R.id.use_sub_car)
    public TextView use_sub_car;

    @ViewInject(R.id.use_sub_content)
    public TextView use_sub_content;

    @ViewInject(R.id.use_sub_driver)
    public TextView use_sub_driver;

    @ViewInject(R.id.use_sub_phone)
    public TextView use_sub_phone;

    @ViewInject(R.id.use_time_start_show_tv)
    public TextView use_time_start_show_tv;

    @ViewInject(R.id.use_time_end_show_tv)
    public TextView use_time_end_show_tv;

    @ViewInject(R.id.use_sub_checkunabletime_rl)
    public RelativeLayout use_sub_checkunabletime_rl;

    @ViewInject(R.id.use_subscribe_startspace_tiet)
    public TextInputEditText use_subscribe_startspace_tiet;

    @ViewInject(R.id.use_subscribe_endspace_tiet)
    public TextInputEditText use_subscribe_endspace_tiet;

    @ViewInject(R.id.use_subscribe_reason_tiet)
    public TextInputEditText use_subscribe_reason_tiet;

}
