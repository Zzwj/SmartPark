package com.xcoder.smartpark.util;

import com.xcoder.lib.utils.SharedPreferencesSava;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.util.map.LocationModel;

/**
 * Created by xcoder_xz on 2016/12/5 0005.
 *
 * @ConfigSP 用户缓存数据的
 * 此类所用的context，必须为application的context，防止内存溢出
 */


public class ConfigSP {

    private static String loginOut = null;// 退出登录标记
    private static AppUser appUser = null;//用户信息
    private static LocationModel locationModel;//定位信息
    private static Boolean isSaveErrLog = true;//是否保存错误日志
    private static Boolean isLEADER = false;


    public static Boolean getIsLEADER() {
        return SharedPreferencesSava.getInstance().getBooleanValue(SmartParkApplication.getAppContext(), "ConfigSP",
                "isLEADER", isLEADER);
    }

    public static void setIsLEADER(Boolean isLEADER) {
        ConfigSP.isLEADER = isLEADER;
        SharedPreferencesSava.getInstance().savaBooleanValue(
                SmartParkApplication.getAppContext(), "ConfigSP",
                "isLEADER", isLEADER);
    }


    public static Boolean getIsSaveErrLog() {
        return SharedPreferencesSava.getInstance().getBooleanValue(SmartParkApplication.getAppContext(), "ConfigSP",
                "isSaveErrLog", isSaveErrLog);
    }

    public static void setIsSaveErrLog(Boolean isSaveErrLog) {
        ConfigSP.isSaveErrLog = isSaveErrLog;
        SharedPreferencesSava.getInstance().savaBooleanValue(
                SmartParkApplication.getAppContext(), "ConfigSP",
                "isSaveErrLog", isSaveErrLog);
    }


    /**
     * 保存定位信息
     */
    public static void setLocation(LocationModel locationModel) {
        locationModel = locationModel;
        SharedPreferencesSava.getInstance().savaObject(
                SmartParkApplication.getAppContext(), "ConfigSP",
                "locationModel", locationModel);
    }

    /**
     * 得到定位信息
     *
     * @return
     */
    public static LocationModel getLocation() {
        return (LocationModel) SharedPreferencesSava.getInstance().getObject(
                SmartParkApplication.getAppContext(), "ConfigSP",
                "locationModel");
    }


    /**
     * 保存用户信息
     *
     * @param appUser
     */
    public static void saveUserInfo(AppUser appUser) {
        SharedPreferencesSava.getInstance().savaObject(
                SmartParkApplication.getAppContext(), "ConfigSP",
                "appUser", appUser);
    }

    /**
     * 查询用户信息
     *
     * @return
     */
    public static AppUser getUserInfo() {
        appUser = (AppUser) SharedPreferencesSava.getInstance().getObject(
                SmartParkApplication.getAppContext(), "ConfigSP",
                "appUser");
        return appUser;
    }

    /**
     * 保存标记
     *
     * @param loginOut
     */
    public static void setLoginOut(String loginOut) {
        ConfigSP.loginOut = loginOut;
        SharedPreferencesSava.getInstance().savaStringValue(
                SmartParkApplication.getAppContext(), "ConfigSP",
                "loginOut", loginOut);
    }

    /**
     * 读取标记
     *
     * @return
     */
    public static String getLoginOut() {
        return SharedPreferencesSava.getInstance().getStringValue(
                SmartParkApplication.getAppContext(), "ConfigSP",
                "loginOut");
    }

    /**
     * 保存用户登录手机号
     *
     * @param loginPhone
     */
    public static void saveUserLoginPhone(String loginPhone) {
        SharedPreferencesSava.getInstance().savaStringValue(
                SmartParkApplication.getAppContext(), "ConfigSP",
                "loginPhone", loginPhone);
    }

    /**
     * 获取用户登录手机号
     */
    public static String getUserLoginPhone() {
        return SharedPreferencesSava.getInstance().getStringValue(
                SmartParkApplication.getAppContext(), "ConfigSP",
                "loginPhone");
    }

    /**
     * 保存极光推送设备号
     *
     * @param RegistrationID
     */
    public static void saveJpushRegistrationID(String RegistrationID) {
        SharedPreferencesSava.getInstance().savaStringValue(
                SmartParkApplication.getAppContext(), "ConfigSP",
                "RegistrationID", RegistrationID);
    }

    /**
     * 获取极光推送设备号
     *
     * @return
     */
    public static String getJpushRegistrationID() {
        return SharedPreferencesSava.getInstance().getStringValue(
                SmartParkApplication.getAppContext(), "ConfigSP",
                "RegistrationID");
    }
}
