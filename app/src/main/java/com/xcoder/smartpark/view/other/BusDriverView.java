package com.xcoder.smartpark.view.other;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.widget.view.CircleImageView;


/**
 * Created by xcoder-xz on 2016/12/30 0030.
 * 公车司机
 */

public class BusDriverView {
    @ViewInject(R.id.bus_driver_head)
    public CircleImageView bus_driver_head;

    @ViewInject(R.id.bus_driver_det)
    public TextView bus_driver_det;//车辆档位信息

    @ViewInject(R.id.bus_driver_car)
    public TextView bus_driver_car;//车辆信息


    @ViewInject(R.id.bus_driver_content)
    public TextView bus_driver_content;//备注


    @ViewInject(R.id.bus_driver_driver)
    public TextView bus_driver_driver;//司机名字

    @ViewInject(R.id.bus_driver_phone)//司机电话
    public TextView bus_driver_phone;

    @ViewInject(R.id.bus_driver_last_time)
    public  TextView bus_driver_last_time;

    @ViewInject(R.id.bus_driver_location)
    public TextView bus_driver_location;


    @ViewInject(R.id.bus_driver_head2)
    public CircleImageView bus_driver_head2;

    @ViewInject(R.id.bus_driver_det2)
    public TextView bus_driver_det2;//车辆档位信息

    @ViewInject(R.id.bus_driver_car2)
    public TextView bus_driver_car2;//车辆信息


    @ViewInject(R.id.bus_driver_content2)
    public TextView bus_driver_content2;//备注


    @ViewInject(R.id.bus_driver_driver2)
    public TextView bus_driver_driver2;//司机名字

    @ViewInject(R.id.bus_driver_phone2)//司机电话
    public TextView bus_driver_phone2;

    @ViewInject(R.id.bus_driver_last_time2)
    public  TextView bus_driver_last_time2;

    @ViewInject(R.id.bus_driver_location2)
    public TextView bus_driver_location2;

    @ViewInject(R.id.bus_driver_msglv2)
    public LinearLayout bus_driver_msglv2;


}
