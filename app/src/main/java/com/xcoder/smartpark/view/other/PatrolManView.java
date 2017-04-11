package com.xcoder.smartpark.view.other;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by jiangkun on 16/12/30.
 * 巡更员
 */

public class PatrolManView {

    @ViewInject(R.id.other_patrolman_main_rv)
    public RecyclerView other_patrolman_main_rv;

    @ViewInject(R.id.other_patrolman_lv)
    public ListView other_patrolman_lv;

    @ViewInject(R.id.other_patrolman_srl)
    public SwipeRefreshLayout other_patrolman_srl;

    @ViewInject(R.id.other_patrolman_black)
    public LinearLayout other_patrolman_black;

}
