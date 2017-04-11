package com.xcoder.smartpark.view.home;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class HomeSelectView {

    @ViewInject(R.id.home_select_headtv)
    public TextView home_select_headtv;


    @ViewInject(R.id.select_bc_ll)
    public LinearLayout select_bc_ll;//班车


    @ViewInject(R.id.select_bc_1)
    public ImageView select_bc_1;//班车1

    @ViewInject(R.id.select_bc_2)
    public ImageView select_bc_2;//班车2

    @ViewInject(R.id.select_bc_3)
    public ImageView select_bc_3;//班车3


    @ViewInject(R.id.select_fk_ll)
    public LinearLayout select_fk_ll;//访客


    @ViewInject(R.id.select_fk_1)
    public ImageView select_fk_1;//访客1

    @ViewInject(R.id.select_fk_2)
    public ImageView select_fk_2;//访客2

    @ViewInject(R.id.select_fk_3)
    public ImageView select_fk_3;//访客3


    @ViewInject(R.id.select_yc_ll)
    public LinearLayout select_yc_ll;//用车


    @ViewInject(R.id.select_yc_1)
    public ImageView select_yc_1;//用车1

    @ViewInject(R.id.select_yc_2)
    public ImageView select_yc_2;//用车2

    @ViewInject(R.id.select_yc_3)
    public ImageView select_yc_3;//用车3
}
