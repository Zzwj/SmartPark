package com.xcoder.smartpark.view.my;

import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2017/1/7 0007.
 * 公告详情
 */

public class MyMessageDetailView {
    @ViewInject(R.id.my_message_detail_tv)
    public TextView my_message_detail_tv;

    @ViewInject(R.id.my_message_detail_title)
    public TextView my_message_detail_title;
}
