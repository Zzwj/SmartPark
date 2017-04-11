package com.xcoder.smartpark.view.my;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by Administrator on 2016/12/16 0016.
 * 我的--我的消息
 */

public class MyMessageView {
    @ViewInject(R.id.my_message_lv)
    public ListView my_message_lv;

    @ViewInject(R.id.my_message_srl)
    public SwipeRefreshLayout my_message_srl;
}
