package com.xcoder.smartpark.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.xcoder.smartpark.R;

/**
 * Created by xcoder-xz on 2017/1/1 0001.
 * 公车司机--预约信息--设置状态的dialog
 */

public abstract class OtherDriverSubscribeGoingDialog extends Dialog {

    public String time_begin;//开始时间
    public String time_end;//结束时间
    public String dialog_people;//司机
    public String dialog_phone;//电话
    public String flag = "1";
    public String flag_1 = "1";
    public Button going_dialog_oncar;
    public Button going_dialog_submit;


    public  OtherDriverSubscribeGoingDialog(Context context) {
        super(context, R.style.AppDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    public void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        //setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.other_driver_subscribe_going_dialog);
        //名字
        TextView going_dialog_time_begin = (TextView) findViewById(R.id.going_dialog_time_begin);
        going_dialog_time_begin.setText(time_begin);
        //路线
        TextView going_dialog_time_end = (TextView) findViewById(R.id.going_dialog_time_end);
        going_dialog_time_end.setText(time_end);
        //详细
        TextView going_dialog_people = (TextView) findViewById(R.id.going_dialog_people);
        going_dialog_people.setText(dialog_people);
        //司机
        TextView going_dialog_phone = (TextView) findViewById(R.id.going_dialog_phone);
        going_dialog_phone.setText(dialog_phone);

        //确认
        going_dialog_submit = (Button) findViewById(R.id.going_dialog_submit);
        going_dialog_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSubmit(view);
                dismiss();
            }
        });

        going_dialog_oncar = (Button) findViewById(R.id.going_dialog_oncar);
        going_dialog_oncar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doUpCar(view);
                dismiss();
            }
        });

        if(flag.equals("1")) {
            going_dialog_oncar.setVisibility(View.VISIBLE);
        }else {
            going_dialog_oncar.setVisibility(View.GONE);
        }

        if(flag_1.equals("1")) {
            going_dialog_submit.setVisibility(View.VISIBLE);
        }else {
            going_dialog_submit.setVisibility(View.GONE);
        }

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
    }

    public abstract void doSubmit(View view);

    public abstract void doUpCar(View view);

    @Override
    public void show() {
        super.show();
    }
}
