package com.xcoder.smartpark.moudel;

import java.io.Serializable;

/**
 * Created by jiangkun on 16/12/28.
 */

public class CarMoudel implements Serializable {

    private String NUMBER;//车牌号码
    private String CAR_NAME;//车辆简称
    private String LONGITUDE;//经度
    private String PHONE;//司机手机号
    private String CAR_ID;//车辆ID
    private String CAR_TIMES;//约车次数
    private String DRIVER_NAME;//司机名称
    private String MODEL;//车型
    private String STREET;//街道
    private String LAST_TIME;//最近约车时间
    private String BRAND;//品牌
    private String DISTRICT;//县
    private String STATUS;//车辆状态 0预约 1不可预约
    private String CITY;//城市
    private String CAR_TYPE;//车辆类型
    private String COLOR;//颜色
    private String NOTE;//车辆描述
    private String ADDRESS;//车辆详细地址
    private String DRIVER_ID;//司机ID
    private String PROVINCE;//0: 公车 1：专车
    private String CAR_BOX;//变速箱类型
    private String LATITUDE;//纬度

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

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getCAR_ID() {
        return CAR_ID;
    }

    public void setCAR_ID(String CAR_ID) {
        this.CAR_ID = CAR_ID;
    }

    public String getCAR_TIMES() {
        return CAR_TIMES;
    }

    public void setCAR_TIMES(String CAR_TIMES) {
        this.CAR_TIMES = CAR_TIMES;
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

    public String getSTREET() {
        return STREET;
    }

    public void setSTREET(String STREET) {
        this.STREET = STREET;
    }

    public String getLAST_TIME() {
        return LAST_TIME;
    }

    public void setLAST_TIME(String LAST_TIME) {
        this.LAST_TIME = LAST_TIME;
    }

    public String getBRAND() {
        return BRAND;
    }

    public void setBRAND(String BRAND) {
        this.BRAND = BRAND;
    }

    public String getDISTRICT() {
        return DISTRICT;
    }

    public void setDISTRICT(String DISTRICT) {
        this.DISTRICT = DISTRICT;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getCAR_TYPE() {
        return CAR_TYPE;
    }

    public void setCAR_TYPE(String CAR_TYPE) {
        this.CAR_TYPE = CAR_TYPE;
    }

    public String getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(String COLOR) {
        this.COLOR = COLOR;
    }

    public String getNOTE() {
        return NOTE;
    }

    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getDRIVER_ID() {
        return DRIVER_ID;
    }

    public void setDRIVER_ID(String DRIVER_ID) {
        this.DRIVER_ID = DRIVER_ID;
    }

    public String getPROVINCE() {
        return PROVINCE;
    }

    public void setPROVINCE(String PROVINCE) {
        this.PROVINCE = PROVINCE;
    }

    public String getCAR_BOX() {
        return CAR_BOX;
    }

    public void setCAR_BOX(String CAR_BOX) {
        this.CAR_BOX = CAR_BOX;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }
}
