package com.xcoder.smartpark.activity.my;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.MainActivity;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.my.MyInfoService;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.widget.dialog.AppDialog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * 我的-我的信息
 * Created by jiangkun on 16/12/16.
 */
@ContentView(R.layout.my_myinfo_main)
public class MyInfoActivity extends BaseActivity{

    @Injection
    private MyInfoService myInfoService;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        myInfoService.init(this);
    }

    @Override
    public void closeActivity() {

    }

    @OnClick({R.id.my_myinfo_submit_rl,R.id.my_myinfo_header_iv,R.id.my_myinfo_company_rl,R.id.my_myinfo_department_rl,R.id.my_myinfo_job_rl,R.id.my_setting_back_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_myinfo_submit_rl:

                myInfoService.submituserinfo();
                break;
            case R.id.my_myinfo_header_iv:
                AppDialog appDialog = new AppDialog(MyInfoActivity.this, "获取照片方式", "拍照", "相册") {
                    @Override
                    public void buttonLeft() {
                        super.buttonLeft();
                        ToastUtil.showToast("点击拍照");
                        myInfoService.startCamera();

                    }

                    @Override
                    public void buttonRight() {
                        super.buttonRight();
                        ToastUtil.showToast("点击相册");
                        myInfoService.startGallery();
                    }
                };

                //设置可以点击返回按键
                appDialog.getDialog().setCancelable(true);

                break;
            case R.id.my_myinfo_company_rl:
                myInfoService.chooseCompany();
                break;
            case R.id.my_myinfo_department_rl:
                myInfoService.chooseDepartment();
                break;
            case R.id.my_myinfo_job_rl:
                myInfoService.chooseJob();
                break;
            case R.id.my_setting_back_iv:
                finish();
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        myInfoService.onActivityResult(requestCode, resultCode, data);
    }

}
