package com.xcoder.smartpark.service.other;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.other.BusDriverSubscribeActivity;
import com.xcoder.smartpark.fragment.other.AuditAlreadyFragment;
import com.xcoder.smartpark.fragment.other.AuditWaitFragment;
import com.xcoder.smartpark.fragment.other.BusDriverSubscribeGoingFragment;
import com.xcoder.smartpark.fragment.other.BusDriverSubscribeHistoryFragment;
import com.xcoder.smartpark.view.other.BusDriverSubscribeView;

/**
 * Created by xcoder_xz on 2016/12/31 0031.
 * * 其他--公车---预约信息
 */

public class BusDriverSubscribeService {


    @Injection
    BusDriverSubscribeView bdsv;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public BusDriverSubscribeGoingFragment bDSGFragment;//预约中
    public BusDriverSubscribeHistoryFragment bDSHFragment;//历史预约
    public String gogingTag = "预约中";
    public String historyTag = "历史预约";

    private BusDriverSubscribeActivity bdsActivity;

    public void init(BusDriverSubscribeActivity bdsActivity) {
        this.bdsActivity = bdsActivity;
        fragmentManager = bdsActivity.getSupportFragmentManager();
    }


    /**
     * 两个按钮的颜色控制，和fragment的切换
     *
     * @param tag
     */
    public void selectFragment(String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);/* 想要显示一个fragment,先隐藏所有fragment，防止重叠 */
        if (TextUtils.equals(tag, gogingTag)) {//已预约
            bdsv.driver_sub_go_bt.setTextColor(bdsActivity.getResources().getColor(R.color.white));
            bdsv.driver_sub_go_bt.setBackgroundResource(R.drawable.bus_main_bt_no_border_shape);
            bdsv.driver_sub_his_bt.setTextColor(bdsActivity.getResources().getColor(R.color.app_green_color));
            bdsv.driver_sub_his_bt.setBackgroundResource(R.drawable.bus_main_bt_border_shape);

            if (bDSGFragment != null)
                fragmentTransaction.show(bDSGFragment);
            else {
                bDSGFragment = new BusDriverSubscribeGoingFragment();
                fragmentTransaction.add(R.id.driver_sub_fl, bDSGFragment);
            }
        } else {//历史预约
            bdsv.driver_sub_go_bt.setTextColor(bdsActivity.getResources().getColor(R.color.app_green_color));
            bdsv.driver_sub_go_bt.setBackgroundResource(R.drawable.bus_main_bt_border_shape);
            bdsv.driver_sub_his_bt.setTextColor(bdsActivity.getResources().getColor(R.color.white));
            bdsv.driver_sub_his_bt.setBackgroundResource(R.drawable.bus_main_bt_no_border_shape);

            if (bDSHFragment != null)
                fragmentTransaction.show(bDSHFragment);
            else {
                bDSHFragment = new BusDriverSubscribeHistoryFragment();
                fragmentTransaction.add(R.id.driver_sub_fl, bDSHFragment);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏fagment
     */
    public void hideFragment(FragmentTransaction ft) {
        if (bDSGFragment != null) {
            ft.hide(bDSGFragment);
        }
        if (bDSHFragment != null) {
            ft.hide(bDSHFragment);
        }
    }

}
