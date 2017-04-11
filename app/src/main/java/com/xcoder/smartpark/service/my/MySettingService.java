package com.xcoder.smartpark.service.my;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.activity.login.LoginActivity;
import com.xcoder.smartpark.activity.my.MySettingActivity;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.eventbus.CloseBus;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.my.MySettingView;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * Created by jiangkun on 16/12/16.
 */

public class MySettingService {
    @Injection
    private MySettingView msv;

    private MySettingActivity mySettingActivity;

    public void init(MySettingActivity mySettingActivity) {
        this.mySettingActivity = mySettingActivity;

        msv.my_mysetting_appupdate_code.setText(getCode()+"");
    }

    //退出登录
    public void logout() {
        AppProgressDialog.showProgress(mySettingActivity);
        //获取登录后存储的appuser对象
        AppUser appUser = ConfigSP.getUserInfo();
        Rest rest=new Rest("/appuser/loginout.nla");
        rest.addParam("USER_ID", appUser.getUSER_ID());
        rest.post(new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                AppProgressDialog.dismiss(mySettingActivity);
                ToastUtil.showToast("您已退出登陆");
                AppUser appUserexit = new AppUser();
                ConfigSP.saveUserInfo(appUserexit);

                mySettingActivity.openActivity(LoginActivity.class);
                mySettingActivity.finishBase();
                EventBus.getDefault().post(new CloseBus(true));
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                AppProgressDialog.dismiss(mySettingActivity);
                ToastUtil.showToast(msg);
            }

            @Override
            public void onError(Exception exception) {
                AppProgressDialog.dismiss(mySettingActivity);
                ToastUtil.showToast("网络繁忙，请稍后再试");
            }
        });
    }


    public String getCode() {
        String code = "";
        try {
            PackageManager manager = SmartParkApplication.context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(SmartParkApplication.context.getPackageName(), 0);
            code = info.versionName;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return code;
    }

}
