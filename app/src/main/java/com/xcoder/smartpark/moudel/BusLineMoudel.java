package com.xcoder.smartpark.moudel;

import java.io.Serializable;
import java.util.List;

/**
 * 班车线路基础类
 * Created by jiangkun on 16/12/29.
 */

public class BusLineMoudel implements Serializable{
    private String STATUS;//状态 0:冻结 1：正常
    private String BUS_LINE_ID;//班车线路ID
    private String LINE_TYPE;//线路类型
    private String END_STATION;//终点站
    private String LINE_NAME;//班车线路名称
    private List<BusMessageMoudle> RESULT;//班车信息列表
    private String START_STATION;//始发站

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getBUS_LINE_ID() {
        return BUS_LINE_ID;
    }

    public void setBUS_LINE_ID(String BUS_LINE_ID) {
        this.BUS_LINE_ID = BUS_LINE_ID;
    }

    public String getLINE_TYPE() {
        return LINE_TYPE;
    }

    public void setLINE_TYPE(String LINE_TYPE) {
        this.LINE_TYPE = LINE_TYPE;
    }

    public String getEND_STATION() {
        return END_STATION;
    }

    public void setEND_STATION(String END_STATION) {
        this.END_STATION = END_STATION;
    }

    public String getLINE_NAME() {
        return LINE_NAME;
    }

    public void setLINE_NAME(String LINE_NAME) {
        this.LINE_NAME = LINE_NAME;
    }

    public List<BusMessageMoudle> getRESULT() {
        return RESULT;
    }

    public void setRESULT(List<BusMessageMoudle> RESULT) {
        this.RESULT = RESULT;
    }

    public String getSTART_STATION() {
        return START_STATION;
    }

    public void setSTART_STATION(String START_STATION) {
        this.START_STATION = START_STATION;
    }
}
