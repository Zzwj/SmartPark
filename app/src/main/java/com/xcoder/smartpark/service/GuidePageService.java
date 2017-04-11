package com.xcoder.smartpark.service;

import android.app.Activity;
import android.content.Intent;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.activity.GuidePageActivity;
import com.xcoder.smartpark.activity.MainActivity;
import com.xcoder.smartpark.activity.login.LoginActivity;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.view.GuidePageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xcoder_xz on 2016/12/15 0015.
 */

public class GuidePageService {
    @Injection
    GuidePageView gpv;

    private GuidePageActivity guidePageActivity;

    public void init(GuidePageActivity guidePageActivity) {
        this.guidePageActivity = guidePageActivity;
    }

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            //获取缓存的用户信息
            AppUser appUser = ConfigSP.getUserInfo();

            if(appUser != null) {
                LogUtils.d("appUser="+appUser.toString());
            }

            //用户填写过信息，没有被冻结，审核通过状态
            if(null != appUser && !"".equals(appUser.getUSERNAME()) && "1".equals(appUser.getSTATUS()) && "1".equals(appUser.getAUDIT_STATUS())) {
                guidePageActivity.openActivity(MainActivity.class);
                guidePageActivity.finish();
            }else {
                Intent intent = new Intent(guidePageActivity, LoginActivity.class);
                guidePageActivity.startActivity(intent);
                guidePageActivity.finish();
            }
        }
    };

    public void timejump(){
        timer.schedule(task, 3000 * 1);
        }
}
