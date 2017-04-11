package com.xcoder.smartpark.app;

import com.chinamobile.app.lib.bl.WifiInterface;
import com.chinamobile.app.lib.util.SettingUtility;
import com.chinamobile.app.lib.util.StringUtil;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.xcoder.lib.app.BaseApplication;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.CrashHandler;
import com.xcoder.smartpark.util.map.GetUserLocation;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

import static cn.jpush.android.api.JPushInterface.getRegistrationID;

/**
 * Created by xcoder on 2016/12/5 0005.
 */

public class    SmartParkApplication extends BaseApplication {



    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance();
        //极光推送初始化
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);

        //保存极光推送设备号，用户登录时需要传入
        String RegistrationID = getRegistrationID(this);
        LogUtils.d("JpushRegistrationID:"+RegistrationID);
        ConfigSP.saveJpushRegistrationID(RegistrationID);

        initUm();
        initMap();
        initOkHttp();

        /**
         * initWifi
         */
        //傻B文档写的渣死算了
        WifiInterface.init(this);
        String url = SettingUtility.getWsmpurl();
        if(!StringUtil.isNotEmpty(url)) {
            url = "";
            SettingUtility.setWsmpurl(url);
        }

        String ssid = SettingUtility.getSsid();
        if(StringUtil.isNotEmpty(ssid)) {
            ssid = "";
            SettingUtility.setSsid(ssid);
        }

        String vnoCode = SettingUtility.getVnoCode();
        if(StringUtil.isNotEmpty(vnoCode)) {
            vnoCode = "";
            SettingUtility.setVnoCode(vnoCode);
        }

        WifiInterface.initEnv(url,"",ssid,vnoCode);
    }

    public void initMap() {
       new GetUserLocation();
    }

    /**
     * initUm
     */
    public void initUm() {
        UMShareAPI.get(this);

        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(false);
        config.isOpenShareEditActivity(true);
        config.setSinaAuthType(UMShareConfig.AUTH_TYPE_SSO);
        config.setFacebookAuthType(UMShareConfig.AUTH_TYPE_SSO);
        config.setShareToLinkedInFriendScope(UMShareConfig.LINKED_IN_FRIEND_SCOPE_ANYONE);

//       MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType. E_UM_NORMAL);
//        MobclickAgent.setCatchUncaughtExceptions(true);
//        Config.DEBUG = true;
        //微信
        PlatformConfig.setWeixin("wx4be406a15644ccd5","44b6a5387524b880607f6d0214c8faef");

    }

    /**
     * initOkhttp
     */
    public void initOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }

}
