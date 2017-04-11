package com.xcoder.smartpark.activity.other;

import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragmentActivity;
import com.xcoder.smartpark.service.other.UseAuditService;

/**
 * Created by xcoder_xz on 2016/12/30 0030.
 * 其他--用车审核
 */
@ContentView(R.layout.other_use_audit_main)
public class UseAuditActivity extends BaseFragmentActivity {
    @Injection
    UseAuditService uaService;


    @Override
    public void createActivity() {
        uaService.init(this);
        uaService.selectFragment(uaService.waitTag);
    }

    @OnClick({R.id.auit_wait_bt, R.id.auit_black, R.id.auit_already_bt, R.id.auit_car_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auit_wait_bt:
                //未审核
                uaService.selectFragment(uaService.waitTag);
                break;
            case R.id.auit_black:
                this.finish();
                break;
            case R.id.auit_already_bt:
                //已审核
                uaService.selectFragment(uaService.lreadyTag);
                break;
            case R.id.auit_car_location:
                //车辆位置
                openActivity(UseAuditCarLocationActivity.class);
                break;
        }
    }


    @Override
    public void closeActivity() {
        try {
            if (uaService.auditAlreadyFragment != null) {
                uaService.auditAlreadyFragment = null;
            }
            if (uaService.auditWaitFragment != null) {
                uaService.auditWaitFragment = null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }
}
