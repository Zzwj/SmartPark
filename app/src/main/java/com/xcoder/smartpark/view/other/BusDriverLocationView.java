package com.xcoder.smartpark.view.other;

import android.widget.TextView;

import com.amap.api.maps2d.MapView;
import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.widget.view.CircleImageView;


/**
 * Created by xcoder-xz on 2016/12/30 0030.
 * 公车司机---车辆位置
 */

public class BusDriverLocationView {
    @ViewInject(R.id.driver_loc_map)
    public MapView driver_loc_map;

}
