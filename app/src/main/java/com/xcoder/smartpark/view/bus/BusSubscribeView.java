package com.xcoder.smartpark.view.bus;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps2d.MapView;
import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.widget.view.CircleImageView;

/**
 * Created by xcoder_xz on 2016/12/21 0021.
 * 班车---预约
 */

public class BusSubscribeView {

    @ViewInject(R.id.bus_sub_head)
    public ImageView bus_sub_head;//头像


    @ViewInject(R.id.bus_sub_name)
    public TextView bus_sub_name;//班车名字

    @ViewInject(R.id.bus_sub_path)
    public TextView bus_sub_path;//班车路线

    @ViewInject(R.id.bus_sub_user)
    public TextView bus_sub_user;//司机


    @ViewInject(R.id.bus_sub_phone)
    public TextView bus_sub_phone;//司机电话

    @ViewInject(R.id.bus_sub_det)
    public TextView bus_sub_det;//班车详情

    @ViewInject(R.id.bus_sub_time)
    public TextView bus_sub_time;//班车时间

    @ViewInject(R.id.bus_sub_shift_rl)
    public RecyclerView bus_sub_shift_rl;//班次的

    @ViewInject(R.id.bus_sub_site_rl)
    public RecyclerView bus_sub_site_rl;//站点的


    @ViewInject(R.id.bus_sub_mapview)
    public MapView bus_sub_mapview;//地图


    @ViewInject(R.id.bus_sub_scroll)
    public ScrollView bus_sub_scroll;

    @ViewInject(R.id.bus_sub_busmsg)
    public RelativeLayout bus_sub_busmsg;

    @ViewInject(R.id.bus_sub_timepicker_rl)
    public RelativeLayout bus_sub_timepicker_rl;

    @ViewInject(R.id.bus_sub_timepicker_view)
    public View bus_sub_timepicker_view;

    @ViewInject(R.id.bus_sub_time_iv)
    public ImageView bus_sub_time_iv;

    @ViewInject(R.id.bus_sub_submit)
    public TextView bus_sub_submit;

}
