package com.xcoder.smartpark.view.bus;

import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class BusListView {

    @ViewInject(R.id.bus_main_bt_ll)
    public LinearLayout bus_main_bt_ll;//选择栏

    @ViewInject(R.id.bus_main_wrok_bt)
    public TextView bus_main_wrok_bt;//上班

    @ViewInject(R.id.bus_main_home_bt)
    public TextView bus_main_home_bt;//回家

    @ViewInject(R.id.bus_main_temporary_bt)
    public TextView bus_main_temporary_bt;//临时

    @ViewInject(R.id.bus_main_fl)
    public FrameLayout bus_main_fl;//帧布局，用来做切换界面

}
