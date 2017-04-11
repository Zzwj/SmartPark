package com.xcoder.smartpark.view.bus;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/14 0014.
 */

public class BusWrokView {

    @ViewInject(R.id.bus_wrok_main_srl)
    public SwipeRefreshLayout bus_wrok_main_srl;

    @ViewInject(R.id.bus_wrok_main_lv)
    public ListView bus_wrok_main_lv;
}
