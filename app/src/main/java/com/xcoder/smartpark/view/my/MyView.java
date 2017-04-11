package com.xcoder.smartpark.view.my;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.widget.view.CircleImageView;

/**
 * Created by Administrator on 2016/12/9 0009.
 */

public class MyView {
    @ViewInject(R.id.my_mysetting_iv)
    private LinearLayout my_mysetting_iv;//右上角设置按钮

    @ViewInject(R.id.my_main_head_cir)
    public CircleImageView my_main_head_cir;//头像

    @ViewInject(R.id.my_main_name_tv)
    public TextView my_main_name_tv;

    @ViewInject(R.id.my_edit_tv)
    public TextView my_edit_tv;
}
