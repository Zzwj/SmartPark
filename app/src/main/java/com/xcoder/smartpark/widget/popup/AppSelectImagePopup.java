package com.xcoder.smartpark.widget.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xcoder.smartpark.R;


/**
 * 相册或者拍照的选择popup
 *
 * @author xz
 */
public class AppSelectImagePopup implements OnClickListener {
    private Context context;
    private View view;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private PopupWindow popupwindow_menu_add;

    public TextView getT1() {
        return t1;
    }

    public TextView getT2() {
        return t2;
    }

    public AppSelectImagePopup(Context context) {
        this.context = context;
        initView();
        dismissPopup();
    }

    public void initView() {
        view = LayoutInflater.from(context).inflate(
                R.layout.app_select_image_popup, null);
        t1 = (TextView) view.findViewById(R.id.release_image_popup_t1);
        t2 = (TextView) view.findViewById(R.id.release_image_popup_t2);
        t3 = (TextView) view.findViewById(R.id.release_image_popup_t3);
        t3.setOnClickListener(this);
    }

    public void show(View showAsViewBottom) {
        popupwindow_menu_add = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupwindow_menu_add.setFocusable(true);// 监听返回键
        popupwindow_menu_add.setTouchable(true);// 这个控制PopupWindow内部控件的点击事件
        popupwindow_menu_add.setOutsideTouchable(true);// 点击区域外消失
        // 注：http://www.07net01.com/2015/07/887080.html
        ColorDrawable dw = new ColorDrawable();
        popupwindow_menu_add.setBackgroundDrawable(dw);
        popupwindow_menu_add.setAnimationStyle(R.style.AppPopupAnim);
        // 添加pop窗口关闭事件
        backgroundAlpha(0.6f);
        // 添加pop窗口关闭事件
        popupwindow_menu_add.setOnDismissListener(new poponDismissListener());

        if (popupwindow_menu_add.isShowing() && null != popupwindow_menu_add) {
            popupwindow_menu_add.dismiss();
        } else {
            // x,y
            int[] location = new int[2];
            showAsViewBottom.getLocationOnScreen(location);
            // 设置popup出场动画
            popupwindow_menu_add.showAtLocation(showAsViewBottom,
                    Gravity.BOTTOM, location[0], location[0]
                            - popupwindow_menu_add.getHeight());
        }
    }

    /**
     * 关闭
     */
    public void dismissPopup() {
        try {
            if (popupwindow_menu_add.isShowing()
                    && null != popupwindow_menu_add) {
                popupwindow_menu_add.dismiss();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.release_image_popup_t3:
                dismissPopup();
                break;

            default:
                break;
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        if (bgAlpha == 1) {
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        ((Activity) context).getWindow().setAttributes(lp);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            // Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }
}
