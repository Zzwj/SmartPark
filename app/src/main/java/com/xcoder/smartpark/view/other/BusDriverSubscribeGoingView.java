package com.xcoder.smartpark.view.other;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/31 0031.
 * 其他--公车---预约信息---预约中
 */

public class BusDriverSubscribeGoingView {
    @ViewInject(R.id.sub_going_srl)
    public SwipeRefreshLayout sub_going_srl;

    @ViewInject(R.id.sub_going_lv)
    public ListView sub_going_lv;
}
