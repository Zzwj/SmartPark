package com.xcoder.smartpark.service.use;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.use.UseMyOrderActivity;
import com.xcoder.smartpark.fragment.use.UseMyOrderFutureFragment;
import com.xcoder.smartpark.fragment.use.UseMyOrderHistoryFragment;
import com.xcoder.smartpark.view.use.UseMyOrderView;

/**
 * Created by xcoder_xz on 2016/12/19 0019.
 * 用车    我的预约
 */

public class UseMyOrderService {
    private UseMyOrderActivity useMyOrderActivity;
    private UseMyOrderFutureFragment useMyOrderFutureFragment;
    private UseMyOrderHistoryFragment useMyOrderHistoryFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public String historyTag = "历史预约";
    public String futureTag = "预约未出行";

    @Injection
    UseMyOrderView umov;

    public void init(UseMyOrderActivity useMyOrderActivity) {
        this.useMyOrderActivity = useMyOrderActivity;
        fragmentManager = useMyOrderActivity.getSupportFragmentManager();
    }

    /**
     * 两个按钮的颜色控制，和fragment的切换
     *
     * @param tag
     */
    public void selectFragment(String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);/* 想要显示一个fragment,先隐藏所有fragment，防止重叠 */
        if (TextUtils.equals(tag, futureTag)) {//预约未出行
            umov.use_order_future_bt.setTextColor(useMyOrderActivity.getResources().getColor(R.color.white));
            umov.use_order_future_bt.setBackgroundResource(R.drawable.bus_main_bt_no_border_shape);
            umov.use_order_history_bt.setTextColor(useMyOrderActivity.getResources().getColor(R.color.app_green_color));
            umov.use_order_history_bt.setBackgroundResource(R.drawable.bus_main_bt_border_shape);

                /* 如果fragment1已经存在则将其显示出来 */
            if (useMyOrderFutureFragment != null)
                fragmentTransaction.show(useMyOrderFutureFragment);
            /* 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add */
            else {
                useMyOrderFutureFragment = new UseMyOrderFutureFragment();
                fragmentTransaction.add(R.id.use_order_fl, useMyOrderFutureFragment);
            }
        } else {//历史预约
            umov.use_order_future_bt.setTextColor(useMyOrderActivity.getResources().getColor(R.color.app_green_color));
            umov.use_order_future_bt.setBackgroundResource(R.drawable.bus_main_bt_border_shape);
            umov.use_order_history_bt.setTextColor(useMyOrderActivity.getResources().getColor(R.color.white));
            umov.use_order_history_bt.setBackgroundResource(R.drawable.bus_main_bt_no_border_shape);

               /* 如果fragment1已经存在则将其显示出来 */
            if (useMyOrderHistoryFragment != null)
                fragmentTransaction.show(useMyOrderHistoryFragment);
            /* 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add */
            else {
                useMyOrderHistoryFragment = new UseMyOrderHistoryFragment();
                fragmentTransaction.add(R.id.use_order_fl, useMyOrderHistoryFragment);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏fagment
     */
    public void hideFragment(FragmentTransaction ft) {
        if (useMyOrderFutureFragment != null) {
            ft.hide(useMyOrderFutureFragment);
        }
        if (useMyOrderHistoryFragment != null) {
            ft.hide(useMyOrderHistoryFragment);
        }
    }

}
