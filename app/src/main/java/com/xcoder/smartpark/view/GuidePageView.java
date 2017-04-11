package com.xcoder.smartpark.view;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/15 0015.
 * 引导页
 */

public class GuidePageView {

    @ViewInject(R.id.guide_page_iv)
    public ImageView guide_page_iv;

    @ViewInject(R.id.guide_t1)
    public ImageView guide_t1;

    @ViewInject(R.id.guide_t2)
    public ImageView guide_t2;
}
