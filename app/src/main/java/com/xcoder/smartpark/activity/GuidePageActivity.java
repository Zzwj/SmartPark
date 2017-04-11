package com.xcoder.smartpark.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.login.LoginActivity;
import com.xcoder.smartpark.app.AppRuntimePermission;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.GuidePageService;
import com.xcoder.smartpark.view.GuidePageView;

/**
 * Created by xcoder_xz on 2016/12/15 0015.
 * 引导页
 */
@ContentView(R.layout.guide_page_main)
public class GuidePageActivity extends BaseActivity {

    @Injection
    GuidePageService gps;

    @Injection
    GuidePageView gpv;

    private AppRuntimePermission arp;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        //权限检查
        //权限检查完毕
        //渐变动画
        // 引导页时间控制停留
        arp = new AppRuntimePermission(this) {
            //权限检查完毕
            @Override
            public void perTrue() {
                //渐变动画
//                ObjectAnimator oa = ObjectAnimator.ofFloat(gpv.guide_page_iv, "alpha", 0, 1);
//                oa.setDuration(4000);
//                oa.start();

                gpv.guide_t1.setVisibility(View.VISIBLE);
                float curTranslationX = gpv.guide_t1.getTranslationX();
                ObjectAnimator animator = ObjectAnimator.ofFloat(gpv.guide_t1, "translationX",
                        -1000f, curTranslationX);
                animator.setInterpolator(new BounceInterpolator());
                animator.setDuration(2500);
                //animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();

                gpv.guide_t2.setVisibility(View.VISIBLE);
                float curTranslationX2 = gpv.guide_t2.getTranslationX();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(gpv.guide_t2, "translationX",
                        1000f, curTranslationX2);
                //animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator2.setInterpolator(new BounceInterpolator());
                animator2.setDuration(2500);
                animator2.start();

                // TODO Auto-generated method stub
                gps.init(GuidePageActivity.this);
                // 引导页时间控制停留
               gps.timejump();
//               openActivity(LoginActivity.class);
//                finish();
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        arp.MyRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void closeActivity() {

    }
}
