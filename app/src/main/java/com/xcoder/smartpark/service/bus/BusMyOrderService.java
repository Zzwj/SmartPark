package com.xcoder.smartpark.service.bus;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.bus.BusMyOrderActivity;
import com.xcoder.smartpark.fragment.bus.BusMyOrderHaveFragment;
import com.xcoder.smartpark.fragment.bus.BusMyOrderHistoryFragment;
import com.xcoder.smartpark.view.bus.BusMyOrderView;

/**
 * Created by xcoder_xz on 2016/12/18 0018.
 * 班车---我的预约
 */

public class BusMyOrderService {
    @Injection
    BusMyOrderView bmov;
    private BusMyOrderActivity busMyOrderActivity;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BusMyOrderHistoryFragment busMyOrderHistoryFragment;//历史预约
    private BusMyOrderHaveFragment busMyOrderHaveFragment;//预约未出行
    public String historyTag = "历史预约";
    public String haveTag = "已预约";

    public void init(BusMyOrderActivity busMyOrderActivity) {
        this.busMyOrderActivity = busMyOrderActivity;
        fragmentManager = busMyOrderActivity.getSupportFragmentManager();
    }

    /**
     * 两个按钮的颜色控制，和fragment的切换
     *
     * @param tag
     */
    public void selectFragment(String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);/* 想要显示一个fragment,先隐藏所有fragment，防止重叠 */
        if (TextUtils.equals(tag, haveTag)) {//已预约
            bmov.my_order_future_bt.setTextColor(busMyOrderActivity.getResources().getColor(R.color.white));
            bmov.my_order_future_bt.setBackgroundResource(R.drawable.bus_main_bt_no_border_shape);
            bmov.my_order_history_bt.setTextColor(busMyOrderActivity.getResources().getColor(R.color.app_green_color));
            bmov.my_order_history_bt.setBackgroundResource(R.drawable.bus_main_bt_border_shape);

                /* 如果fragment1已经存在则将其显示出来 */
            if (busMyOrderHaveFragment != null)
                fragmentTransaction.show(busMyOrderHaveFragment);
            /* 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add */
            else {
                busMyOrderHaveFragment = new BusMyOrderHaveFragment();
                fragmentTransaction.add(R.id.my_order_fl, busMyOrderHaveFragment);
            }
        } else {//历史预约
            bmov.my_order_future_bt.setTextColor(busMyOrderActivity.getResources().getColor(R.color.app_green_color));
            bmov.my_order_future_bt.setBackgroundResource(R.drawable.bus_main_bt_border_shape);
            bmov.my_order_history_bt.setTextColor(busMyOrderActivity.getResources().getColor(R.color.white));
            bmov.my_order_history_bt.setBackgroundResource(R.drawable.bus_main_bt_no_border_shape);

               /* 如果fragment1已经存在则将其显示出来 */
            if (busMyOrderHistoryFragment != null)
                fragmentTransaction.show(busMyOrderHistoryFragment);
            /* 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add */
            else {
                busMyOrderHistoryFragment = new BusMyOrderHistoryFragment();
                fragmentTransaction.add(R.id.my_order_fl, busMyOrderHistoryFragment);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏fagment
     */
    public void hideFragment(FragmentTransaction ft) {
        if (busMyOrderHistoryFragment != null) {
            ft.hide(busMyOrderHistoryFragment);
        }
        if (busMyOrderHaveFragment != null) {
            ft.hide(busMyOrderHaveFragment);
        }
    }
}
