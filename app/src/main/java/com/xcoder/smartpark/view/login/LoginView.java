package com.xcoder.smartpark.view.login;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;

/**
 * Created by Administrator on 2016/12/5 0005.
 */

public class LoginView {

    @ViewInject(R.id.login_phone_et)
    public EditText login_phone_et;//用户输入手机号

    @ViewInject(R.id.login_security_et)
    public EditText login_security_et;//用户输入验证码

    @ViewInject(R.id.login_dologin_tv)
    public TextView login_dologin_tv;//用户点击登录按钮

    @ViewInject(R.id.login_security_tv)
    public TextView login_security_tv;//用户点击获取验证码

    @ViewInject(R.id.login_delete_iv)
    public ImageView login_delete_iv;

}
