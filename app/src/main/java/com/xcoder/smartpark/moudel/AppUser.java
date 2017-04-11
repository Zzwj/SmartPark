package com.xcoder.smartpark.moudel;

import java.io.Serializable;

/**
 * 用户
 * Created by Administrator on 2016/12/15 0015.
 */

public class AppUser implements Serializable{
    public String DEPT_ID;//部门ID
    public String NUMBER;//工号
    public String START_TIME;//注册时间
    public String USER_TOKEN;//token
    public String IP;//地址
    public String PHONE;//电话
    public String SEX;//性别
    public String USER_ID;//用户ID
    public String LAST_LOGIN;//最近登录时间
    public String NAME;//昵称
    public String HEADIMGURL;//头像
    public String STATUS;//状态：0 冻结 1：正常
    public String END_TIME;//到期时间
    public String USER_BALANCE;//余额
    public String AUDIT_STATUS;//状态：0 待审核，1：审核通过 2：审核未通过
    public String BZ;//备注
    public String USERNAME;//姓名
    public String ROLE_ID;//职位ID
    public String COMPANY_ID;//公司ID
    public String BIRTHDAY;//生日
    public String RNAME;//角色名
    public String BIANMA;//角色编码

    public String DEVICE_CODE;
    public String ADDRESS;
    public String RFIDCODE;
    public String LONGITUDE;
    public String LATITUDE;

    public String getDEVICE_CODE() {
        return DEVICE_CODE;
    }

    public void setDEVICE_CODE(String DEVICE_CODE) {
        this.DEVICE_CODE = DEVICE_CODE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getRFIDCODE() {
        return RFIDCODE;
    }

    public void setRFIDCODE(String RFIDCODE) {
        this.RFIDCODE = RFIDCODE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getRNAME() {
        return RNAME;
    }

    public void setRNAME(String RNAME) {
        this.RNAME = RNAME;
    }

    public String getBIANMA() {
        return BIANMA;
    }

    public void setBIANMA(String BIANMA) {
        this.BIANMA = BIANMA;
    }

    public String getDEPT_ID() {
        return DEPT_ID;
    }

    public void setDEPT_ID(String DEPT_ID) {
        this.DEPT_ID = DEPT_ID;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getSTART_TIME() {
        return START_TIME;
    }

    public void setSTART_TIME(String START_TIME) {
        this.START_TIME = START_TIME;
    }

    public String getUSER_TOKEN() {
        return USER_TOKEN;
    }

    public void setUSER_TOKEN(String USER_TOKEN) {
        this.USER_TOKEN = USER_TOKEN;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getLAST_LOGIN() {
        return LAST_LOGIN;
    }

    public void setLAST_LOGIN(String LAST_LOGIN) {
        this.LAST_LOGIN = LAST_LOGIN;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getHEADIMGURL() {
        return HEADIMGURL;
    }

    public void setHEADIMGURL(String HEADIMGURL) {
        this.HEADIMGURL = HEADIMGURL;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public String getUSER_BALANCE() {
        return USER_BALANCE;
    }

    public void setUSER_BALANCE(String USER_BALANCE) {
        this.USER_BALANCE = USER_BALANCE;
    }

    public String getAUDIT_STATUS() {
        return AUDIT_STATUS;
    }

    public void setAUDIT_STATUS(String AUDIT_STATUS) {
        this.AUDIT_STATUS = AUDIT_STATUS;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getROLE_ID() {
        return ROLE_ID;
    }

    public void setROLE_ID(String ROLE_ID) {
        this.ROLE_ID = ROLE_ID;
    }

    public String getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(String COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
    }

    public String getBIRTHDAY() {
        return BIRTHDAY;
    }

    public void setBIRTHDAY(String BIRTHDAY) {
        this.BIRTHDAY = BIRTHDAY;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "DEPT_ID='" + DEPT_ID + '\'' +
                ", NUMBER='" + NUMBER + '\'' +
                ", START_TIME='" + START_TIME + '\'' +
                ", USER_TOKEN='" + USER_TOKEN + '\'' +
                ", IP='" + IP + '\'' +
                ", PHONE='" + PHONE + '\'' +
                ", SEX='" + SEX + '\'' +
                ", USER_ID='" + USER_ID + '\'' +
                ", LAST_LOGIN='" + LAST_LOGIN + '\'' +
                ", NAME='" + NAME + '\'' +
                ", HEADIMGURL='" + HEADIMGURL + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", END_TIME='" + END_TIME + '\'' +
                ", USER_BALANCE='" + USER_BALANCE + '\'' +
                ", AUDIT_STATUS='" + AUDIT_STATUS + '\'' +
                ", BZ='" + BZ + '\'' +
                ", USERNAME='" + USERNAME + '\'' +
                ", ROLE_ID='" + ROLE_ID + '\'' +
                ", COMPANY_ID='" + COMPANY_ID + '\'' +
                ", BIRTHDAY='" + BIRTHDAY + '\'' +
                ", RNAME='" + RNAME + '\'' +
                ", BIANMA='" + BIANMA + '\'' +
                ", DEVICE_CODE='" + DEVICE_CODE + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                ", RFIDCODE='" + RFIDCODE + '\'' +
                ", LONGITUDE='" + LONGITUDE + '\'' +
                ", LATITUDE='" + LATITUDE + '\'' +
                '}';
    }
}
