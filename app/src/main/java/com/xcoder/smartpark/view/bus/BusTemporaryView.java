package com.xcoder.smartpark.view.bus;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/19 0019.
 */

public class BusTemporaryView {

    @ViewInject(R.id.bus_temporary_srl)
    public SwipeRefreshLayout bus_temporary_srl;

    @ViewInject(R.id.bus_temporary_lv)
    public ListView bus_temporary_lv;
}
