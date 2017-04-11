package com.xcoder.smartpark.view.use;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder-xz on 2016/12/19 0019.
 * 用车--我的预约---未出行
 */

public class UseMyOrderFutureView {

    @ViewInject(R.id.use_future_srl)
    public SwipeRefreshLayout use_future_srl;


    @ViewInject(R.id.use_future_lv)
    public ListView use_future_lv;


}
