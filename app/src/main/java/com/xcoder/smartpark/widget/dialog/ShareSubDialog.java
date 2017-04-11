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

import com.xcoder.smartpark.R;
import com.xcoder.smartpark.util.zxing.EncodingHandler;

/**
 * Created by xcoder_xz on 2016/12/20 0020.
 * 预约的分享dialog
 */

public class ShareSubDialog extends Dialog implements View.OnClickListener {

    private String showContent = "加载中...";
    private Boolean isCancelabl = true;//后退键关闭
    private Boolean isCanceledOnTouchOutside = false;//碰触dialog外面关闭
    public Button tou_subscribe_close;
    public ImageView tou_subscribe_iv;
    public LinearLayout tou_subscribe_sms;//短信
    public LinearLayout tou_subscribe_wechat;//邮件
    public LinearLayout tou_subscribe_email;//邮箱

    private Bitmap bitmap;
    public String sendRQString = "园区欢迎你";
    public String subName = "";//名字
    public String subCard = "";//同行码
    public String subTime = "";//时间
    public Bitmap bitmapRQ;

    public ShareSubDialog(Context context) {
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
        setContentView(R.layout.tou_subscribe_dialog);

        TextView tou_subscribe_name = (TextView) findViewById(R.id.tou_subscribe_name);
        tou_subscribe_name.setText(subName+"");
        TextView tou_subscribe_card = (TextView) findViewById(R.id.tou_subscribe_card);
        tou_subscribe_card.setText(subCard+"");
        TextView tou_subscribe_time = (TextView) findViewById(R.id.tou_subscribe_time);
        tou_subscribe_time.setText(subTime+"");
        //

        tou_subscribe_close = (Button) findViewById(R.id.tou_subscribe_close);
        tou_subscribe_close.setOnClickListener(this);
        tou_subscribe_iv = (ImageView) findViewById(R.id.tou_subscribe_iv);
        tou_subscribe_sms = (LinearLayout) findViewById(R.id.tou_subscribe_sms);
        tou_subscribe_email = (LinearLayout) findViewById(R.id.tou_subscribe_email);
        tou_subscribe_wechat = (LinearLayout) findViewById(R.id.tou_subscribe_wechat);
        //得到app图标
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_min_ico);
        //得到二维码
        bitmapRQ = EncodingHandler.createQRCode(sendRQString, 600, 600, bitmap);
        //
        tou_subscribe_iv.setImageBitmap(bitmapRQ);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tou_subscribe_close:
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //bitmap清掉
        if (bitmap.isRecycled())
            bitmap.recycle();
        if (bitmap.isRecycled())
            bitmapRQ.recycle();
    }
}
