package com.xcoder.smartpark.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcoder.lib.utils.Utils;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/5 0005.
 * app全局通用的弹出提示Dialog
 */

public abstract class AppDialog implements View.OnClickListener {
    private Context context;
    private AlertDialog dialog;

    /**
     * 获取当前dialog方法，便于APP做控制
     * @return
     */
    public AlertDialog getDialog() {
        return dialog;
    }

    /**
     * @param context
     * @param message    内容
     * @param rightButton 右边按钮
     * @see显示一个内容，和一个按钮
     */
    public AppDialog(Context context, String message, String rightButton) {
        initView(context, null, message, null, rightButton, false);
    }

    /**
     * @param context
     * @param message       内容
     * @param leftButton  左边按钮
     * @param rightButton 右边按钮
     * @see显示一个内容，两个按钮
     */
    public AppDialog(Context context, String message, String leftButton, String rightButton) {
        initView(context, null, message, leftButton, rightButton, false);
    }

    /**
     * @param context
     * @param title      标题
     * @param message    内容
     * @param rightButton 左按钮
     * @param isSingle   是否只显示一个按钮
     * @see显示标题，内容，一个按钮
     */
    public AppDialog(Context context, String title, String message, String rightButton, Boolean isSingle) {
        initView(context, title, message, null, rightButton, true);
    }

    /**
     * @param context
     * @param title       标题
     * @param message     内容
     * @param leftButton  左边按钮
     * @param rightButton 右边按钮
     * @see显示标题，内容，两个按钮（全部显示）
     */
    public AppDialog(Context context, String title, String message, String leftButton, String rightButton) {
        initView(context, title, message, leftButton, rightButton, false);
    }


    public void initView(Context context, String title, String message, String leftButton, String rightButton, Boolean isSingle) {
        View view = LayoutInflater.from(context).inflate(R.layout.app_dialog_main, null);
        TextView diaTitle = (TextView) view.findViewById(R.id.app_dialog_title);//标题
        View line1 = view.findViewById(R.id.app_dialog_linehor1);//标题分割线
        TextView diaMessage = (TextView) view.findViewById(R.id.app_dialog_message);//内容
        View line2 = view.findViewById(R.id.app_dialog_linehor2);//内容分割线
        LinearLayout buttonLin = (LinearLayout) view.findViewById(R.id.app_dialog_buttonll);//button布局
        Button leftB = (Button) view.findViewById(R.id.app_dialog_buttonleft);//左button
        leftB.setOnClickListener(this);
        View line3 = view.findViewById(R.id.app_dialog_linever);//button中间的分割线
        Button rightB = (Button) view.findViewById(R.id.app_dialog_buttonright);//右button
        rightB.setOnClickListener(this);

        if (leftButton == null) {
            //左边button为null
            if (title == null) {
                diaTitle.setVisibility(View.GONE);
                line1.setVisibility(View.GONE);
            }else{
                diaTitle.setText(title);
            }
            diaMessage.setText(message);
            rightB.setText(rightButton);
            line3.setVisibility(View.GONE);
            leftB.setVisibility(View.GONE);

        } else {
            if (title == null) {
                diaTitle.setVisibility(View.GONE);
                line1.setVisibility(View.GONE);

            }else{
                diaTitle.setText(title);
            }
            diaMessage.setText(message);
            leftB.setText(leftButton);
            rightB.setText(rightButton);
        }
///////////------initDialog-----------//////////
        // 创建对话
        dialog = new AlertDialog.Builder(context).create();
        // 设置返回键失
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        // dialog.getWindow().setType(
        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        // 显示对话
        dialog.show();
        // 必须放到显示对话框下面，否则显示不出效果
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Utils.getPhoneWidth(context) * 4 / 5;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        // 加载布局组件
        dialog.getWindow().setContentView(view);
    }


    public  void buttonRight(){

    }

    public void buttonLeft() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_dialog_buttonleft:
                buttonLeft();
                dismiss();
                break;
            case R.id.app_dialog_buttonright:
                buttonRight();
                dismiss();
                break;
        }
    }


    /**
     * @方法说明:让警告框消失
     * @方法名称:dismiss
     * @返回值:void
     */
    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog.cancel();
            dialog = null;
        }
    }

}
