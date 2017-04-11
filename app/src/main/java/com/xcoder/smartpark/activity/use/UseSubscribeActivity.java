package com.xcoder.smartpark.activity.use;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.use.UseSubscribeService;

/**
 * Created by xcoder_xz on 2016/12/22 0022.
 * 用车--预约
 */
@ContentView(R.layout.use_subscribe_main)
public class UseSubscribeActivity extends BaseActivity {

    @Injection
    UseSubscribeService uss;


    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        uss.init(this);
    }

    @OnClick({R.id.use_sub_top_rl,R.id.use_sub_submit,R.id.use_time_start_show_rl,
            R.id.use_time_end_show_rl,R.id.use_sub_checkunabletime_rl,R.id.use_sub_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.use_sub_top_rl:
                finish();
                break;
            case R.id.use_sub_submit:
                uss.doSubmit();
                //提交
                break;
            case R.id.use_time_start_show_rl:
                uss.selectTime("start");
                break;
            case R.id.use_time_end_show_rl:
                uss.selectTime("end");
                break;
//            case R.id.use_time_start_show_tv:
//                uss.selectTime("start");
//                break;
//            case R.id.use_time_end_show_tv:
//                uss.selectTime("end");
//                break;

            case R.id.use_sub_checkunabletime_rl:
                uss.showunabletime();
                break;
            case R.id.use_sub_phone:
                uss.toPhone();
                break;
        }
    }

    /**
     * 监听Back键按下事件,方法2: 注意: 返回值表示:是否能完全处理该事件 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            try {
                // 如果有选择器弹出，则关闭选择器
                if (uss.timePicker != null && uss.timePicker.isShowing()) {
                    uss.timePicker.dismiss();
                } else {
                    finish();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }



    @Override
    public void closeActivity() {

    }
}
