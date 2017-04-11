package com.xcoder.smartpark.activity.my;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.HtmlActivity;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.network.AppNet;
import com.xcoder.smartpark.service.my.MySettingService;
import com.xcoder.smartpark.util.AppUpdate;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.widget.dialog.AppDialog;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;

/**
 * Created by jiangkun on 16/12/16.
 * 我的-我的设置
 */

@ContentView(R.layout.my_mysetting_main)
public class MySettingActivity extends BaseActivity {
    @Injection
    private MySettingService mySettingService;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        mySettingService.init(this);
    }

    @Override
    public void closeActivity() {

    }

    @OnClick({R.id.my_setting_back_iv, R.id.my_setting_about_rl,
            R.id.my_setting_explain_rl, R.id.my_setting_cache_tv, R.id.my_setting_out_rl, R.id.my_mysetting_appupdate_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_setting_back_iv:
                this.finish();
                break;
            case R.id.my_setting_about_rl:
                //关于
                Bundle bundle = new Bundle();
                bundle.putString("htmlTitle", "关于");
                bundle.putString("htmlUrl", AppNet.appNet + AppNet.appabout);
                openActivity(HtmlActivity.class, bundle);
                break;
            case R.id.my_setting_explain_rl:
                //使用说明
                Bundle bundle2 = new Bundle();
                bundle2.putString("htmlTitle", "使用说明");
                bundle2.putString("htmlUrl", AppNet.appNet + AppNet.apphelp);
                openActivity(HtmlActivity.class, bundle2);
                break;
            case R.id.my_setting_cache_tv:
                //缓存
                //睡眠3秒后，关闭app
                try {
                    AppProgressDialog.showProgress(MySettingActivity.this);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AppProgressDialog.dismiss(MySettingActivity.this);
                            ToastUtil.showToast("清除完毕");
                        }
                    }, 1000);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.my_setting_out_rl:
                //退出登录
                new AppDialog(this, "确认注销登陆?", "取消", "确定") {
                    @Override
                    public void buttonRight() {
                        super.buttonRight();
                        mySettingService.logout();
                    }
                };
                break;
            case R.id.my_mysetting_appupdate_rl:
                //app更新
                new AppUpdate(this).getVersion(1);
                break;
        }
    }

}
