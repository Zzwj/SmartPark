package com.xcoder.smartpark.view.other;

import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/30 0030.
 * 用车=--审批
 */

public class UseAuditView {
    @ViewInject(R.id.auit_wait_bt)
    public TextView auit_wait_bt;//待审批按钮

    @ViewInject(R.id.auit_already_bt)
    public TextView auit_already_bt;//已审批按钮
}
