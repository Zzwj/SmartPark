package com.xcoder.smartpark.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcoder.lib.utils.Utils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.login.LoginActivity;
import com.xcoder.smartpark.activity.other.BusDriverActivity;
import com.xcoder.smartpark.activity.other.PatrolManActivity;
import com.xcoder.smartpark.activity.other.UseAuditActivity;
import com.xcoder.smartpark.util.ToastUtil;

/**
 * Created by xcoder_xz on 2016/12/29 0029.
 * 访客悬浮按钮的其他内容
 */

public class UseOtherDialog extends Dialog implements View.OnClickListener {

    public LinearLayout l2;

    public UseOtherDialog(Context context) {
        super(context,R.style.AppProgressDialogStyle);
    }

    public int [] visButton=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.use_other_dialog);
        TextView tv1= (TextView) findViewById(R.id.use_other_tv1);
        tv1.setOnClickListener(this);
        TextView tv2= (TextView) findViewById(R.id.use_other_tv2);
        tv2.setOnClickListener(this);
        TextView tv3= (TextView) findViewById(R.id.use_other_tv3);
        tv3.setOnClickListener(this);
        TextView tv4= (TextView) findViewById(R.id.use_other_tv4);
        tv4.setOnClickListener(this);
        LinearLayout l1= (LinearLayout) findViewById(R.id.use_other_l1);
        l2 = (LinearLayout) findViewById(R.id.use_other_l2);
        if(visButton!=null){
            tv1.setVisibility(View.GONE);
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            for (int i:visButton){
                switch (i){
                    case 1:
                        tv1.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        tv2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        tv3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        tv4.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }

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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.use_other_tv1:
                Intent intent = new Intent(getContext(), PatrolManActivity.class);
                getContext().startActivity(intent);

                break;
            case R.id.use_other_tv2:
                break;
            case R.id.use_other_tv3:
                Intent intent3 = new Intent(getContext(), UseAuditActivity.class);
                getContext().startActivity(intent3);
                break;
            case R.id.use_other_tv4:
                Intent intent4 = new Intent(getContext(), BusDriverActivity.class);
                getContext().startActivity(intent4);
                break;
        }
    }
}
