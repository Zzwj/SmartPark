package com.xcoder.smartpark.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.xcoder.smartpark.R;

/**
 * Created by xcoder_xz on 2016/12/25 0025.
 * 班车--我的预约---已预约---取消预约
 */

public abstract class BusCancelSubDialog extends Dialog {
    public String name;
    public String shift;
    public String address;
    public String time;


    public BusCancelSubDialog(Context context) {
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
        setContentView(R.layout.bus_sub_cancel_dialog);
        //
        TextView sub_cancel__name = (TextView) findViewById(R.id.sub_cancel__name);//车名
        sub_cancel__name.setText(name);
        TextView sub_cancel_shift = (TextView) findViewById(R.id.sub_cancel_shift);//车路线
        sub_cancel_shift.setText(shift);
        TextView sub_cancel_addre = (TextView) findViewById(R.id.sub_cancel_addre);//上车地点
        sub_cancel_addre.setText(address);
        TextView sub_cancel_time = (TextView) findViewById(R.id.sub_cancel_time);//上车时间
        sub_cancel_time.setText(time);
        Button sub_dialog_no = (Button) findViewById(R.id.sub_dialog_no);
        sub_dialog_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        Button sub_dialog_yes = (Button) findViewById(R.id.sub_dialog_yes);
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
