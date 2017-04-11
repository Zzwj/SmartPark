package com.xcoder.smartpark.view.use;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2017/3/22 0022.
 * 用车
 */

public class UseListView {
    @ViewInject(R.id.use_main_srl)
    public SwipeRefreshLayout use_main_srl;//swip

    @ViewInject(R.id.use_main_lv)
    public ListView use_main_lv;
}
