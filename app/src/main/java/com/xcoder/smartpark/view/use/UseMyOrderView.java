package com.xcoder.smartpark.view.use;

import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/19 0019.
 * 用车   我的预约
 */

public class UseMyOrderView {

    @ViewInject(R.id.use_order_future_bt)
    public TextView use_order_future_bt;//预约未出行

    @ViewInject(R.id.use_order_history_bt)
    public TextView use_order_history_bt;//历史预约
}
