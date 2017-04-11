package com.xcoder.smartpark.view.tou;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by Administrator on 2016/12/15 0015.
 * 访客详情
 */

public class TouDetailView {

    @ViewInject(R.id.tou_detail_name)
    public TextView tou_detail_name;//访客姓名

    @ViewInject(R.id.tou_detail_phone)
    public TextView tou_detail_phone;//联系方式

    @ViewInject(R.id.tou_detail_phone_iv)
    public ImageView tou_detail_phone_iv;

    @ViewInject(R.id.tou_detail_card)
    public TextView tou_detail_card;//身份证号

    @ViewInject(R.id.tou_detail_wrok)
    public TextView tou_detail_wrok;//所属公司

    @ViewInject(R.id.tou_detail_thing)
    public TextView tou_detail_thing;//拜访事项

    @ViewInject(R.id.tou_detail_time)
    public TextView tou_detail_time;//拜访时间

    @ViewInject(R.id.tou_detail_number)
    public TextView tou_detail_number;//到访人数

    @ViewInject(R.id.tou_detail_car_card)
    public TextView tou_detail_car_card;//车牌号

    @ViewInject(R.id.tou_detail_remarks)
    public TextView tou_detail_remarks;//备注

    @ViewInject(R.id.tou_detail_bottom_ll)
    public LinearLayout tou_detail_bottom_ll;//底部同意拒绝


}
