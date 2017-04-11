package com.xcoder.smartpark.view.other;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcder_xz on 2016/12/30 0030.
 * 用车审核---已审核
 */

public class AuditAlreadyView {

    @ViewInject(R.id.audit_already_main_lv)
    public ListView audit_already_main_lv;

    @ViewInject(R.id.audit_already_main_srl)
    public SwipeRefreshLayout audit_already_main_srl;

}


