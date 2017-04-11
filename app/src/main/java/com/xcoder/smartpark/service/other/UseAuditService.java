package com.xcoder.smartpark.service.other;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.other.UseAuditActivity;
import com.xcoder.smartpark.fragment.other.AuditAlreadyFragment;
import com.xcoder.smartpark.fragment.other.AuditWaitFragment;
import com.xcoder.smartpark.view.other.UseAuditView;

/**
 * Created by xcoder_xz on 2016/12/30 0030.
 * 用车=--审批
 */

public class UseAuditService {
    @Injection
    UseAuditView uav;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public AuditWaitFragment auditWaitFragment;//待审批
    public AuditAlreadyFragment auditAlreadyFragment;//已审批
    public String waitTag = "待审批";
    public String lreadyTag = "已审批";

    private UseAuditActivity uaActivity;

    public void init(UseAuditActivity uaActivity) {
        this.uaActivity = uaActivity;
        fragmentManager = uaActivity.getSupportFragmentManager();
    }

    /**
     * 两个按钮的颜色控制，和fragment的切换
     *
     * @param tag
     */
    public void selectFragment(String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);/* 想要显示一个fragment,先隐藏所有fragment，防止重叠 */
        if (TextUtils.equals(tag, waitTag)) {//待审批
            uav.auit_wait_bt.setTextColor(uaActivity.getResources().getColor(R.color.white));
            uav.auit_wait_bt.setBackgroundResource(R.drawable.bus_main_bt_no_border_shape);
            uav.auit_already_bt.setTextColor(uaActivity.getResources().getColor(R.color.app_green_color));
            uav.auit_already_bt.setBackgroundResource(R.drawable.bus_main_bt_border_shape);

            if (auditWaitFragment != null)
                fragmentTransaction.show(auditWaitFragment);
            else {
                auditWaitFragment = new AuditWaitFragment();
                fragmentTransaction.add(R.id.auit_fl, auditWaitFragment);
            }
        } else {//已审批
            uav.auit_wait_bt.setTextColor(uaActivity.getResources().getColor(R.color.app_green_color));
            uav.auit_wait_bt.setBackgroundResource(R.drawable.bus_main_bt_border_shape);
            uav.auit_already_bt.setTextColor(uaActivity.getResources().getColor(R.color.white));
            uav.auit_already_bt.setBackgroundResource(R.drawable.bus_main_bt_no_border_shape);

            if (auditAlreadyFragment != null)
                fragmentTransaction.show(auditAlreadyFragment);
            else {
                auditAlreadyFragment = new AuditAlreadyFragment();
                fragmentTransaction.add(R.id.auit_fl, auditAlreadyFragment);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏fagment
     */
    public void hideFragment(FragmentTransaction ft) {
        if (auditWaitFragment != null) {
            ft.hide(auditWaitFragment);
        }
        if (auditAlreadyFragment != null) {
            ft.hide(auditAlreadyFragment);
        }
    }

}
