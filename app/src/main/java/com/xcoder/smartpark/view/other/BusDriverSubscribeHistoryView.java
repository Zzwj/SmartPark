package com.xcoder.smartpark.view.other;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/31 0031.
 * 其他--公车---预约信息---历史预约
 */

public class BusDriverSubscribeHistoryView {

    @ViewInject(R.id.sub_his_srl)
    public SwipeRefreshLayout sub_his_srl;

    @ViewInject(R.id.sub_his_lv)
    public ListView sub_his_lv;

}
