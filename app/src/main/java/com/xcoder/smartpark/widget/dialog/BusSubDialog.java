package com.xcoder.smartpark.widget.dialog;

import android.app.Dialog;
import android.content.Context;
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
 * Created by xcoder_xz on 2016/12/24 0024.
 * 班车的预约成功dialog
 */


public abstract class BusSubDialog extends Dialog {

    public String str_dialog_name;//名字
    public String str_dialog_shift;//路线
    public String str_dialog_det;//详细
    public String str_dialog_driver;//司机
    public String str_dialog_phone;//电话号码
    public String str_dialog_addre;//上车地点
    public String str_dialog_time;//时间
    public String str_dialog_time_det;//时间详细
    public Button sub_dialog_no;//取消
    public Button sub_dialog_yes;//确认

    public BusSubDialog(Context context) {
        super(context, R.style.AppDialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    public void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.bus_sub_dialog);
        //名字
        TextView sub_dialog_name = (TextView) findViewById(R.id.sub_dialog_name);
        sub_dialog_name.setText(str_dialog_name);
        //路线
        TextView sub_dialog_shift = (TextView) findViewById(R.id.sub_dialog_shift);
        sub_dialog_shift.setText(str_dialog_shift);
        //详细
        TextView sub_dialog_det = (TextView) findViewById(R.id.sub_dialog_det);
        sub_dialog_det.setText(str_dialog_det);
        //司机
        TextView sub_dialog_driver = (TextView) findViewById(R.id.sub_dialog_driver);
        sub_dialog_driver.setText(str_dialog_driver);
        //电话号码
        TextView sub_dialog_phone = (TextView) findViewById(R.id.sub_dialog_phone);
        sub_dialog_phone.setText(str_dialog_phone);
        //上车地点
        TextView sub_dialog_addre = (TextView) findViewById(R.id.sub_dialog_addre);
        sub_dialog_addre.setText(str_dialog_addre);
        //时间
        TextView sub_dialog_time = (TextView) findViewById(R.id.sub_dialog_time);
        sub_dialog_time.setText(str_dialog_time);
        //时间详细
        TextView sub_dialog_time_det = (TextView) findViewById(R.id.sub_dialog_time_det);
        sub_dialog_time_det.setText(str_dialog_time_det);
        //取消
        sub_dialog_no = (Button) findViewById(R.id.sub_dialog_no);
        sub_dialog_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //确认
        sub_dialog_yes = (Button) findViewById(R.id.sub_dialog_yes);
        sub_dialog_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSubmit(view);
            }
        });
        //
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
    }

    public abstract void doSubmit(View view);

    @Override
    public void show() {
        super.show();
    }
}
