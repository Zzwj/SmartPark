package com.xcoder.smartpark.view.other;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/30 0030.
 * 用车审核--未审核
 */

public class AuditWaitView {
    @ViewInject(R.id.audit_wait_main_lv)
    public ListView audit_wait_main_lv;

    @ViewInject(R.id.audit_wait_main_srl)
    public SwipeRefreshLayout audit_wait_main_srl;

}
