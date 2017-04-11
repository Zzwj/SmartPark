package com.xcoder.smartpark.view.bus;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/18 0018.
 * 我的预约
 */

public class BusMyOrderView {

    @ViewInject(R.id.my_order_bt_ll)
    public LinearLayout my_order_bt_ll;//选择按钮的布局

    @ViewInject(R.id.my_order_have_bt)
    public TextView my_order_future_bt;//预约未出行

    @ViewInject(R.id.my_order_history_bt)
    public TextView my_order_history_bt;//历史预约


}
