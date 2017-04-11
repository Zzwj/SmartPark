package com.xcoder.smartpark.service.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.lib.utils.RegularUtils;
import com.xcoder.lib.utils.SharedPreferencesSava;
import com.xcoder.smartpark.activity.MainActivity;
import com.xcoder.smartpark.activity.login.LoginActivity;
import com.xcoder.smartpark.activity.my.MyInfoActivity;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.login.LoginView;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/5 0005.
 */

public class LoginService {
    @Injection
    LoginView lv;

    private LoginActivity loginActivity;

    public void init(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        lv.login_phone_et.setText(ConfigSP.getUserLoginPhone());
    }

    //执行登录功能
    public void dologin() {

        try {
            final String phone = lv.login_phone_et.getText().toString().trim();
            String code = lv.login_security_et.getText().toString().trim();

            if(phone.equals("") || code.equals("")) {
                ToastUtil.showToast("手机号和验证码不能为空");
                return;
            }
            String s = ConfigSP.getJpushRegistrationID();

            if(RegularUtils.isMobileExact(phone)) {
                AppProgressDialog.showProgress(loginActivity);
                Rest rest=new Rest("/appuser/login.nla");
                rest.addParam("PHONE",phone);
                rest.addParam("CODE",code);
                rest.addParam("DEVICE_CODE", ConfigSP.getJpushRegistrationID());
                rest.post(new Callback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject, String state, String msg) {
                        AppProgressDialog.dismiss(loginActivity);
                        try {
                            LogUtils.d("登陆接口返回："+jsonObject.toString());
                            JSONObject jb = jsonObject.getJSONObject("dataset");
                            AppUser appUser = JSON.parseObject(jb.toString(), AppUser.class);
                            ConfigSP.saveUserInfo(appUser);
                            ConfigSP.saveUserLoginPhone(phone);

                            //账户被冻结，直接退出
                            if(appUser.getSTATUS().equals("0")) {
                                ToastUtil.showToast("您的账户已被冻结");
                                return;
                            }

                            //如果username有内容，但是审核状态为0，说明是审核中。新创建的用户审核状态为0，但是username没有值
                            if(appUser.getAUDIT_STATUS().equals("0") && !appUser.getUSERNAME().equals("")) {
                                ToastUtil.showToast("您的资料正在审核中");
                                return;
                            }

                            //资料审核未通过，跳转填写界面重新提交
                            if(appUser.getAUDIT_STATUS().equals("2")) {
                                ToastUtil.showToast("审核不通过，请联系管理员！");
                                return;
//                                loginActivity.openActivity(MyInfoActivity.class);
//                                loginActivity.finish();
                            }else {
                                ToastUtil.showToast("登录成功");
                                //如果USERNAME为空，说明用户没有上传过个人信息，以USERNAME为准NAME是昵称的意思USERNAME才是真实的用户名
                                if(appUser.getUSERNAME().equals("")) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("flag", "new");
                                    loginActivity.openActivity(MyInfoActivity.class, bundle);
                                }else {
                                    loginActivity.openActivity(MainActivity.class);
                                }
                                loginActivity.finish();
                            }
                        }catch (Exception exception){
                            onError(exception);
                            exception.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                        AppProgressDialog.dismiss(loginActivity);
                        ToastUtil.showToast(msg);
                    }

                    @Override
                    public void onError(Exception exception) {
                        AppProgressDialog.dismiss(loginActivity);
                        ToastUtil.showToast("网络繁忙，请稍后再试");
                    }
                });

            }else {
                ToastUtil.showToast("您输入的手机号不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("Exception:"+e.toString());
        }

    }

    //清除验证码信息
    public void clearSecurity() {
        lv.login_security_et.getText().clear();
    }

    //获取验证码
    public void getsecuritycode() {
        try {
            timer.start();//启动秒表
            LogUtils.d("点击获取验证码");
            AppProgressDialog.showProgress(loginActivity);
            String phone = lv.login_phone_et.getText().toString().trim();
            if(RegularUtils.isMobileExact(phone)) {
                Rest rest=new Rest("/appuser/sendValidateCode.nla");
                rest.addParam("PHONE",phone);
                rest.post(new Callback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject, String state, String msg) {
                        AppProgressDialog.dismiss(loginActivity);
                        ToastUtil.showToast("验证码发送成功");
                    }

                    @Override
                    public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                        AppProgressDialog.dismiss(loginActivity);
                        ToastUtil.showToast(msg);
                    }

                    @Override
                    public void onError(Exception exception) {
                        LogUtils.e("exception:"+exception);
                        AppProgressDialog.dismiss(loginActivity);
                        ToastUtil.showToast("网络繁忙，请稍后再试");
                    }
                });
            }else{
                ToastUtil.showToast("您的手机号不正确，请重新输入");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("Exception:"+e.getMessage());
        }

    }

    //发送验证码计时器
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            lv.login_security_tv.setText((millisUntilFinished / 1000) + "秒后可重发");
            lv.login_security_tv.setClickable(false);
        }

        @Override
        public void onFinish() {
            lv.login_security_tv.setEnabled(true);
            lv.login_security_tv.setText("获取验证码");
            lv.login_security_tv.setClickable(true);
        }
    };
}
