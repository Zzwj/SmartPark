package com.xcoder.smartpark.widget.dialog;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.xcoder.smartpark.R;
import com.xcoder.smartpark.util.ToastUtil;

/**
 * Created by xcoder_xz on 2016/12/9 0009.
 * app全局通用的加载框
 */

public class AppProgressDialog extends ProgressDialog {


    private String showContent = "加载中...";
    private Boolean isCancelabl = true;//后退键关闭
    private Boolean isCanceledOnTouchOutside = false;//碰触dialog外面关闭
    private static AppProgressDialog appProgressDialog;

    /**
     * @param context
     * @see默认使用加载中
     */
    public static void showProgress(Context context) {
        showProgress(context, null, null, null);
    }

    /**
     * @param context
     * @param showContent
     * @see自己写的加载内容
     */
    public static void showProgress(Context context, String showContent) {
        showProgress(context, showContent, null, null);
    }

    /**
     * @param context
     * @param showContent              显示加载标识
     * @param isCancelabl              是否后退键取消
     * @param isCanceledOnTouchOutside 是否点击dialog外面取消
     */
    public static void showProgress(Context context, String showContent, Boolean isCancelabl, Boolean isCanceledOnTouchOutside) {
        try {
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    return;
                }
            }
            if (appProgressDialog != null && appProgressDialog.isShowing()) {
                return;
            }
            appProgressDialog = new AppProgressDialog(context, showContent, isCancelabl, isCanceledOnTouchOutside);
            appProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * @param context
     * @param showContent              显示的内容
     * @param isCancelabl              是否返回键关闭
     * @param isCanceledOnTouchOutside 是否点击外面关闭
     * @see多余操作用这个
     */
    public AppProgressDialog(Context context, String showContent, Boolean isCancelabl, Boolean isCanceledOnTouchOutside) {
        super(context, R.style.AppProgressDialogStyle);
        if (!TextUtils.isEmpty(showContent)) {
            this.showContent = showContent;
        }
        if (isCancelabl != null) {
            this.isCancelabl = isCancelabl;
        }
        if (isCanceledOnTouchOutside != null) {
            this.isCanceledOnTouchOutside = isCanceledOnTouchOutside;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(isCancelabl);
        setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        setContentView(R.layout.app_progress_dialog_main);
        TextView tv = (TextView) findViewById(R.id.app_progress_tv);
        tv.setText(showContent);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

    /**
     * dismiss the mDialogTextView
     */
    public static void dismiss(Context context) {
        try {
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    appProgressDialog = null;
                    return;
                }
            }
            if (appProgressDialog != null && appProgressDialog.isShowing()) {
                Context loadContext = appProgressDialog.getContext();
                if (loadContext != null && loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        appProgressDialog = null;
                        return;
                    }
                }
                appProgressDialog.dismiss();
                appProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            appProgressDialog = null;
        }
    }


    @Override
    public void dismiss() {
        super.dismiss();
        try {
            if (appProgressDialog != null && appProgressDialog.isShowing()) {
                Context loadContext = appProgressDialog.getContext();
                if (loadContext != null && loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        appProgressDialog = null;
                        return;
                    }
                }
                appProgressDialog.dismiss();
                appProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            appProgressDialog = null;
        }
    }
}
