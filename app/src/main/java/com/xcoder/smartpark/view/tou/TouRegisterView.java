package com.xcoder.smartpark.view.tou;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/16 0016.
 */

public class TouRegisterView {
    @ViewInject(R.id.tou_register_name_til)
    public TextInputLayout tou_register_name_til;//访客姓名的layout

    @ViewInject(R.id.tou_register_name_tiet)
    public EditText tou_register_name_tiet;//访客姓名的et

    @ViewInject(R.id.tou_register_phone_til)
    public TextInputLayout tou_register_phone_til;//联系方式的layout

    @ViewInject(R.id.tou_register_phone_tiet)
    public TextInputEditText tou_register_phone_tiet;//联系方式的et

    @ViewInject(R.id.tou_register_card_til)
    public TextInputLayout tou_register_card_til;//身份证的layout

    @ViewInject(R.id.tou_register_card_tiet)
    public TextInputEditText tou_register_card_tiet;//身份证的et

    @ViewInject(R.id.tou_register_wrok_til)
    public TextInputLayout tou_register_wrok_til;//所属公司的layout

    @ViewInject(R.id.tou_register_wrok_tiet)
    public TextInputEditText tou_register_wrok_tiet;//所属公司的et

    @ViewInject(R.id.tou_register_thing_til)
    public TextInputLayout tou_register_thing_til;//拜访事项的layout

    @ViewInject(R.id.tou_register_thing_tiet)
    public TextInputEditText tou_register_thing_tiet;//拜访事项的et

    @ViewInject(R.id.tou_register_time)
    public TextView tou_register_time;//时间的选择


    @ViewInject(R.id.tou_register_number_til)
    public TextInputLayout tou_register_number_til;//到访人数的layout

    @ViewInject(R.id.tou_register_number_tiet)
    public TextInputEditText tou_register_number_tiet;//到访人数的et

    @ViewInject(R.id.tou_register_car_cared_til)
    public TextInputLayout tou_register_car_cared_til;//车牌号的layout

    @ViewInject(R.id.tou_register_car_cared_tiet)
    public TextInputEditText tou_register_car_cared_tiet;//车牌号数的et

    @ViewInject(R.id.tou_register_remarks_til)
    public TextInputLayout tou_register_remarks_til;//备注的layout

    @ViewInject(R.id.tou_register_remarks_tiet)
    public TextInputEditText tou_register_remarks_tiet;//备注的et



}
