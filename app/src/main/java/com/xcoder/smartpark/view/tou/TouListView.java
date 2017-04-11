package com.xcoder.smartpark.view.tou;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2017/3/22 0022.
 * 访客列表
 */

public class TouListView {

//    @ViewInject(R.id.tou_share_tv)
//    public TextView tou_share_tv;//分享预约
//
//    @ViewInject(R.id.tou_register_tv)
//    public TextView tou_register_tv;//预约登记

    @ViewInject(R.id.tou_main_srl)
    public SwipeRefreshLayout tou_main_srl;//swip

    @ViewInject(R.id.tou_main_lv)
    public ListView tou_main_lv;//listview


    @ViewInject(R.id.tou_main_layout)
    public RelativeLayout tou_main_layout;
}
