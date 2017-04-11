package com.xcoder.smartpark.view.my;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by Administrator on 2016/12/16 0016.
 * 我的   我的钱包
 */

public class MyMoneyView {

    @ViewInject(R.id.sharetest)
    public TextView sharetest;

    @ViewInject(R.id.my_money_srl)
    public SwipeRefreshLayout my_money_srl;

    @ViewInject(R.id.my_money_lv)
    public ListView my_money_lv;
}
