package com.xcoder.smartpark.moudel;

import java.io.Serializable;

/**
 * Created by xcoder_xz on 2016/12/22 0022.
 * 班车--预约---站点
 */

public class BusSubSiteMoudel implements Serializable{
    private String STATUS;//状态
    private String BUS_LINE_ID;//线路ID
    private String SITE_ORDER;//站点排序
    private String COUNT;//站点预约人数
    private String BUS_SITE_ID;//站点ID
    private String SITE_NAME;//站点名称


    public BusSubSiteMoudel() {
        super();
    }

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

    public String getSITE_ORDER() {
        return SITE_ORDER;
    }

    public void setSITE_ORDER(String SITE_ORDER) {
        this.SITE_ORDER = SITE_ORDER;
    }

    public String getCOUNT() {
        return COUNT;
    }

    public void setCOUNT(String COUNT) {
        this.COUNT = COUNT;
    }

    public String getBUS_SITE_ID() {
        return BUS_SITE_ID;
    }

    public void setBUS_SITE_ID(String BUS_SITE_ID) {
        this.BUS_SITE_ID = BUS_SITE_ID;
    }

    public String getSITE_NAME() {
        return SITE_NAME;
    }

    public void setSITE_NAME(String SITE_NAME) {
        this.SITE_NAME = SITE_NAME;
    }

    @Override
    public String toString() {
        return "BusSubSiteMoudel{" +
                "STATUS='" + STATUS + '\'' +
                ", BUS_LINE_ID='" + BUS_LINE_ID + '\'' +
                ", SITE_ORDER='" + SITE_ORDER + '\'' +
                ", COUNT='" + COUNT + '\'' +
                ", BUS_SITE_ID='" + BUS_SITE_ID + '\'' +
                ", SITE_NAME='" + SITE_NAME + '\'' +
                '}';
    }
}
