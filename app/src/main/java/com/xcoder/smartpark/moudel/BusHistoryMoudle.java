package com.xcoder.smartpark.moudel;

import java.io.Serializable;

/**
 * Created by xcoder_xz on 2016/12/25 0025.
 * 班车--我的预约--历史预约
 */

public class BusHistoryMoudle implements Serializable {
    private String MATTER;//班车描述
    private String NUMBER;//车牌
    private String LINE_TYPE;//线路类型 0上班 1回家 2临时
    private String BUS_LINE_ID;//线路ID
    private String USER_ID;//用户ID
    private String BUS_RESERVE_ID;//预约ID
    private String BUS_NAME;//班车名称
    private String DRIVER_NAME;//司机名称
    private String MODEL;//车型
    private String DRIVER_TEL;//司机电话
    private String SITE_NAME;//站点名称
    private String BUS_ID;//班车ID
    private String STATUS;//状态0预约成功 1取消预约
    private String RESERVE_TIME;//预约时间
    private String BUS_NUM_ID;//车次
    private String COLOR;//颜色
    private String BUS_SITE_ID;//站点ID
    private String TYPE;//0预约当天 1长期预约
    private String LINE_NAME;//线路名称

    public String getLINE_NAME() {
        return LINE_NAME;
    }

    public void setLINE_NAME(String LINE_NAME) {
        this.LINE_NAME = LINE_NAME;
    }

    public BusHistoryMoudle() {
        super();
    }

    public String getMATTER() {
        return MATTER;
    }

    public void setMATTER(String MATTER) {
        this.MATTER = MATTER;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getLINE_TYPE() {
        return LINE_TYPE;
    }

    public void setLINE_TYPE(String LINE_TYPE) {
        this.LINE_TYPE = LINE_TYPE;
    }

    public String getBUS_LINE_ID() {
        return BUS_LINE_ID;
    }

    public void setBUS_LINE_ID(String BUS_LINE_ID) {
        this.BUS_LINE_ID = BUS_LINE_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getBUS_RESERVE_ID() {
        return BUS_RESERVE_ID;
    }

    public void setBUS_RESERVE_ID(String BUS_RESERVE_ID) {
        this.BUS_RESERVE_ID = BUS_RESERVE_ID;
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

    public String getSITE_NAME() {
        return SITE_NAME;
    }

    public void setSITE_NAME(String SITE_NAME) {
        this.SITE_NAME = SITE_NAME;
    }

    public String getBUS_ID() {
        return BUS_ID;
    }

    public void setBUS_ID(String BUS_ID) {
        this.BUS_ID = BUS_ID;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getRESERVE_TIME() {
        return RESERVE_TIME;
    }

    public void setRESERVE_TIME(String RESERVE_TIME) {
        this.RESERVE_TIME = RESERVE_TIME;
    }

    public String getBUS_NUM_ID() {
        return BUS_NUM_ID;
    }

    public void setBUS_NUM_ID(String BUS_NUM_ID) {
        this.BUS_NUM_ID = BUS_NUM_ID;
    }

    public String getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(String COLOR) {
        this.COLOR = COLOR;
    }

    public String getBUS_SITE_ID() {
        return BUS_SITE_ID;
    }

    public void setBUS_SITE_ID(String BUS_SITE_ID) {
        this.BUS_SITE_ID = BUS_SITE_ID;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
