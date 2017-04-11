package com.xcoder.smartpark.activity.login;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.login.LoginService;

/**
 * Created by Administrator on 2016/12/5 0005.
 * 登陆
 */
@ContentView(R.layout.login_main)
public class LoginActivity extends BaseActivity {
    @Injection
    private LoginService ls;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        ls.init(this);
    }

    @Override
    public void closeActivity() {

    }

    @OnClick({R.id.login_dologin_tv,R.id.login_delete_iv,R.id.login_security_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_dologin_tv:
                ls.dologin();
                break;

            case R.id.login_delete_iv:
                ls.clearSecurity();
                break;

            case R.id.login_security_tv:
                ls.getsecuritycode();
                break;
        }

    }

}
