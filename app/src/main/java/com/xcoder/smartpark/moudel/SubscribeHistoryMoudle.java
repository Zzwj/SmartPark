package com.xcoder.smartpark.moudel;

import java.io.Serializable;

/**
 * Created by xcoder-xz on 2017/1/1 0001.
 *  其他--公车---预约信息---历史预约
 */

public class SubscribeHistoryMoudle implements Serializable{
    private String RESERVE_START_TIME;//预约开始时间
    private String NUMBER;//车牌号
    private String CAR_NAME;//车辆简称
    private String CAR_ID;//车辆ID
    private String USER_ID;//预约人ID
    private String DRIVER_NAME;//司机名称
    private String MODEL;//车型
    private String USER_NAME;//预约人名称
    private String DRIVER_TEL;//司机电话
    private String CAR_RESERVE_ID;//预约表主键
    private String BRAND;//品牌
    private String RESERVE_END_TIME;//预约结束时间
    private String STATUS;//0待审核 1未出行 2已出行 3 审核未通过
    private String COLOR;//颜色
    private String CAR_TYPE;//车辆类型
    private String DRIVER_ID;//司机ID
    private String CAR_BOX;//变速箱
    private String PHONE;//预约人电话

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getRESERVE_START_TIME() {
        return RESERVE_START_TIME;
    }

    public void setRESERVE_START_TIME(String RESERVE_START_TIME) {
        this.RESERVE_START_TIME = RESERVE_START_TIME;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getCAR_NAME() {
        return CAR_NAME;
    }

    public void setCAR_NAME(String CAR_NAME) {
        this.CAR_NAME = CAR_NAME;
    }

    public String getCAR_ID() {
        return CAR_ID;
    }

    public void setCAR_ID(String CAR_ID) {
        this.CAR_ID = CAR_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
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

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getDRIVER_TEL() {
        return DRIVER_TEL;
    }

    public void setDRIVER_TEL(String DRIVER_TEL) {
        this.DRIVER_TEL = DRIVER_TEL;
    }

    public String getCAR_RESERVE_ID() {
        return CAR_RESERVE_ID;
    }

    public void setCAR_RESERVE_ID(String CAR_RESERVE_ID) {
        this.CAR_RESERVE_ID = CAR_RESERVE_ID;
    }

    public String getBRAND() {
        return BRAND;
    }

    public void setBRAND(String BRAND) {
        this.BRAND = BRAND;
    }

    public String getRESERVE_END_TIME() {
        return RESERVE_END_TIME;
    }

    public void setRESERVE_END_TIME(String RESERVE_END_TIME) {
        this.RESERVE_END_TIME = RESERVE_END_TIME;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(String COLOR) {
        this.COLOR = COLOR;
    }

    public String getCAR_TYPE() {
        return CAR_TYPE;
    }

    public void setCAR_TYPE(String CAR_TYPE) {
        this.CAR_TYPE = CAR_TYPE;
    }

    public String getDRIVER_ID() {
        return DRIVER_ID;
    }

    public void setDRIVER_ID(String DRIVER_ID) {
        this.DRIVER_ID = DRIVER_ID;
    }

    public String getCAR_BOX() {
        return CAR_BOX;
    }

    public void setCAR_BOX(String CAR_BOX) {
        this.CAR_BOX = CAR_BOX;
    }
}
