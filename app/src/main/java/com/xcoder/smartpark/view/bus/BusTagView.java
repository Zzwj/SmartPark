package com.xcoder.smartpark.view.bus;

import android.widget.EditText;
import android.widget.ListView;

import com.amap.api.maps2d.MapView;
import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/22 0022.
 * 班车---标注
 */

public class BusTagView {

    @ViewInject(R.id.bus_tag_map)
    public MapView bus_tag_map;


    @ViewInject(R.id.bus_tag_et)
    public EditText bus_tag_et;

    @ViewInject(R.id.bus_tag_listview)
    public ListView bus_tag_listview;
}
