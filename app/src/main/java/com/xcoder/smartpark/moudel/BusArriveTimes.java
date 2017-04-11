package com.xcoder.smartpark.moudel;

import java.io.Serializable;

/**
 * Created by xcoder_xz on 2016/12/24 0024.
 * 班车 --时间表
 */

public class BusArriveTimes implements Serializable{
    private String ARRIVAL_TIME;//班次时间
    private String BUS_NUM_ID;//班次ID
    private String BUS_LINE_ID;//线路ID

    public BusArriveTimes() {
        super();
    }

    public String getARRIVAL_TIME() {
        return ARRIVAL_TIME;
    }

    public void setARRIVAL_TIME(String ARRIVAL_TIME) {
        this.ARRIVAL_TIME = ARRIVAL_TIME;
    }

    public String getBUS_NUM_ID() {
        return BUS_NUM_ID;
    }

    public void setBUS_NUM_ID(String BUS_NUM_ID) {
        this.BUS_NUM_ID = BUS_NUM_ID;
    }

    public String getBUS_LINE_ID() {
        return BUS_LINE_ID;
    }

    public void setBUS_LINE_ID(String BUS_LINE_ID) {
        this.BUS_LINE_ID = BUS_LINE_ID;
    }
}
