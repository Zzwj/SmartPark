package com.xcoder.smartpark.widget.popup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.other.BusDriverActivity;
import com.xcoder.smartpark.activity.other.PatrolManActivity;
import com.xcoder.smartpark.activity.other.UseAuditActivity;

/**
 * Created by xcoder_xz on 2016/12/29 0029.
 * 访客悬浮按钮的其他内容
 */

public class UseOtherPopup {
    private Context context;
    private View view;
    private PopupWindow popupwindow_menu_add;

    public UseOtherPopup(Context context) {
        this.context = context;
        initView();
    }


    private void initView() {
        view = LayoutInflater.from(context).inflate(
                R.layout.other_main_popup, null);
        LinearLayout patrol = (LinearLayout) view.findViewById(R.id.other_patrol_man_ll);//巡更路线
        LinearLayout money = (LinearLayout) view.findViewById(R.id.other_money_ll);//资产盘查
        LinearLayout audit = (LinearLayout) view.findViewById(R.id.other_audit_ll);//用车审批
        LinearLayout driver = (LinearLayout) view.findViewById(R.id.other_driver_ll);//公车司机
        LinearLayout bus = (LinearLayout) view.findViewById(R.id.other_bus_ll);//班车信息
        LinearLayout user = (LinearLayout) view.findViewById(R.id.other_user_ll);//人员定位
    }

    public void show(View showAsViewBottom) {
        popupwindow_menu_add = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupwindow_menu_add.setFocusable(true);// 监听返回键
        popupwindow_menu_add.setTouchable(true);// 这个控制PopupWindow内部控件的点击事件
        popupwindow_menu_add.setOutsideTouchable(true);// 点击区域外消失
        // 注：http://www.07net01.com/2015/07/887080.html
        ColorDrawable dw = new ColorDrawable();
        popupwindow_menu_add.setBackgroundDrawable(dw);
     //   popupwindow_menu_add.setAnimationStyle(R.style.OtherPopupAnim);

        if (popupwindow_menu_add.isShowing() && null != popupwindow_menu_add) {
            popupwindow_menu_add.dismiss();
        } else {
            // x,y
            int[] location = new int[2];
            showAsViewBottom.getLocationOnScreen(location);
            // 设置popup出场动画
          //  popupwindow_menu_add.showAsDropDown(showAsViewBottom, xoff, yoff);
            popupwindow_menu_add.showAtLocation(showAsViewBottom, Gravity.LEFT, 0, 0);
        }
    }
}
