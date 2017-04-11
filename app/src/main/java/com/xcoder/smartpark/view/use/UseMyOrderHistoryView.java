package com.xcoder.smartpark.view.use;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder-xz on 2016/12/19 0019.
 * 用车--我的预约---历史预约
 */

public class UseMyOrderHistoryView {

    @ViewInject(R.id.use_history_srl)
    public SwipeRefreshLayout use_history_srl;


    @ViewInject(R.id.use_history_lv)
    public ListView use_history_lv;
}


