package com.xcoder.smartpark.activity.tou;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.tou.TouRegisterService;

/**
 * Created by xcoder_xz on 2016/12/16 0016.
 * 预约登记
 */
@ContentView(R.layout.tou_register_main)
public class TouRegisterActivity extends BaseActivity {
    @Injection
    TouRegisterService trs;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        trs.init(this);
    }


    @OnClick({R.id.tou_register_black, R.id.tou_register_time_iv, R.id.tou_register_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tou_register_black:
                this.finish();
                break;
            case R.id.tou_register_time_iv:
                //选择日期
                trs.selectTime();
                break;
            case R.id.tou_register_submit:
                //提交
                trs.submit();
                break;
        }
    }

    /**
     * 监听按下事件，
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 判断是否需要隐藏软键盘
     *
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
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
                if (trs.timePicker != null && trs.timePicker.isShowing()) {
                    trs.timePicker.dismiss();
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
