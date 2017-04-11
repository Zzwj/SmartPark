package com.xcoder.smartpark.moudel;

import java.io.Serializable;

/**
 * 班车路线列表中返回的班车信息
 * Created by jiangkun on 16/12/28.
 */

public class BusMessageMoudle implements Serializable{
    private String NUMBER;//车牌
    private String BUS_NUM;//座位数
    private String LONGITUDE;//经度
    private String BUS_LINE_ID;//线路ID
    private String BUS_BOX;//变速器类型
    private String BUS_NAME;//班车名称
    private String DRIVER_NAME;//司机名称
    private String MODEL;//班车类型
    private String DRIVER_TEL;//司机电话
    private String BUS_ID;//班车ID
    private String BRAND;//班车品牌
    private String STATUS;//班车状态
    private String SEND_TIME;//发车时间
    private String COLOR;//颜色
    private String ADDRESS;//地址
    private String LATITUDE;//纬度
    private String DRIVER_USERID;//班车司机ID
    private String YYNUM;//预约人数
    private String UPNUM;//上车人数

    public String getDRIVER_USERID() {
        return DRIVER_USERID;
    }

    public void setDRIVER_USERID(String DRIVER_USERID) {
        this.DRIVER_USERID = DRIVER_USERID;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getBUS_NUM() {
        return BUS_NUM;
    }

    public void setBUS_NUM(String BUS_NUM) {
        this.BUS_NUM = BUS_NUM;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getBUS_LINE_ID() {
        return BUS_LINE_ID;
    }

    public void setBUS_LINE_ID(String BUS_LINE_ID) {
        this.BUS_LINE_ID = BUS_LINE_ID;
    }

    public String getBUS_BOX() {
        return BUS_BOX;
    }

    public void setBUS_BOX(String BUS_BOX) {
        this.BUS_BOX = BUS_BOX;
    }

    public String getBUS_NAME() {
        return BUS_NAME;
    }

    public void setBUS_NAME(String BUS_NAME) {
        this.BUS_NAME = BUS_NAME;
    }

    public String getDRIVER_NAME() {
        return DRIVER_NAME;
    }

    public void setDRIVER_NAME(String DRIVER_NAME) {
        this.DRIVER_NAME = DRIVER_NAME;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

    public String getDRIVER_TEL() {
        return DRIVER_TEL;
    }

    public void setDRIVER_TEL(String DRIVER_TEL) {
        this.DRIVER_TEL = DRIVER_TEL;
    }

    public String getBUS_ID() {
        return BUS_ID;
    }

    public void setBUS_ID(String BUS_ID) {
        this.BUS_ID = BUS_ID;
    }

    public String getBRAND() {
        return BRAND;
    }

    public void setBRAND(String BRAND) {
        this.BRAND = BRAND;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSEND_TIME() {
        return SEND_TIME;
    }

    public void setSEND_TIME(String SEND_TIME) {
        this.SEND_TIME = SEND_TIME;
    }

    public String getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(String COLOR) {
        this.COLOR = COLOR;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getYYNUM() {
        return YYNUM;
    }

    public void setYYNUM(String YYNUM) {
        this.YYNUM = YYNUM;
    }

    public String getUPNUM() {
        return UPNUM;
    }

    public void setUPNUM(String UPNUM) {
        this.UPNUM = UPNUM;
    }
}
