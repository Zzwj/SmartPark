package com.xcoder.smartpark.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.util.LoginUtil;
import com.xcoder.smartpark.util.zxing.EncodingHandler;

/**
 * Created by xcoder_xz on 2016/12/20 0020.
 * 预约的分享dialog
 */

public class ShareSubUrlDialog extends Dialog  {

    private String showContent = "加载中...";
    private Boolean isCancelabl = true;//后退键关闭
    private Boolean isCanceledOnTouchOutside = false;//碰触dialog外面关闭
    public Button tou_subscribe_close;
    public LinearLayout tou_subscribe_sms;//短信
    public LinearLayout tou_subscribe_wechat;//邮件
    public LinearLayout tou_subscribe_email;//邮箱

    public String sendRQString = "园区欢迎你";

    public ShareSubUrlDialog(Context context) {
        super(context, R.style.AppDialog);
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
        setContentView(R.layout.tou_subscribe_url_dialog);


        tou_subscribe_close = (Button) findViewById(R.id.tou_subscribe_url_close);
        tou_subscribe_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tou_subscribe_sms = (LinearLayout) findViewById(R.id.tou_subscribe_url_sms);
        tou_subscribe_email = (LinearLayout) findViewById(R.id.tou_subscribe_url_email);
        tou_subscribe_wechat = (LinearLayout) findViewById(R.id.tou_subscribe_url_wechat);
        //
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



    @Override
    public void dismiss() {
        super.dismiss();
    }
}
