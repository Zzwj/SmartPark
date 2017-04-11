package com.xcoder.smartpark.moudel;

import java.io.Serializable;

/**
 * Created by xcoder_xz on 2017/1/5 0005.
 * 记录app的错误信息
 */

public class AppErrMoudle implements Serializable{
    private int errId;//错误id
    private String appName;//app的名字
    private String versionCode;//app版本号
    private String versionName;//app版本名称
    private String errName;//错误log文件的名称（.txt?.xml）
    private String errContent;//错误备注
    private String errPath;//错误文件的地址
    private String errNewTime;//错误文件的生成时间
    private String errUploadTime;//错误文件的上传时间
    private String errPhone;//错误手机信息
    private String errMsg;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getErrId() {
        return errId;
    }

    public void setErrId(int errId) {
        this.errId = errId;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getErrName() {
        return errName;
    }

    public void setErrName(String errName) {
        this.errName = errName;
    }

    public String getErrContent() {
        return errContent;
    }

    public void setErrContent(String errContent) {
        this.errContent = errContent;
    }

    public String getErrPath() {
        return errPath;
    }

    public void setErrPath(String errPath) {
        this.errPath = errPath;
    }

    public String getErrNewTime() {
        return errNewTime;
    }

    public void setErrNewTime(String errNewTime) {
        this.errNewTime = errNewTime;
    }

    public String getErrUploadTime() {
        return errUploadTime;
    }

    public void setErrUploadTime(String errUploadTime) {
        this.errUploadTime = errUploadTime;
    }

    public String getErrPhone() {
        return errPhone;
    }

    public void setErrPhone(String errPhone) {
        this.errPhone = errPhone;
    }
}
