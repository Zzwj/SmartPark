package com.xcoder.smartpark.view.bus;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/18 0018.
 * 班车--我的预约---历史预约
 */

public class BusMyOrderHistoryView {
    @ViewInject(R.id.order_history_srl)
    public SwipeRefreshLayout order_history_srl;

    @ViewInject(R.id.order_history_lv)
    public ListView order_history_lv;
}
