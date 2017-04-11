package com.xcoder.smartpark.view.bus;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/14 0014.
 */

public class BusHomeView {

    @ViewInject(R.id.bus_home_main_srl)
    public SwipeRefreshLayout bus_home_main_srl;

    @ViewInject(R.id.bus_home_main_lv)
    public ListView bus_home_main_lv;

}
