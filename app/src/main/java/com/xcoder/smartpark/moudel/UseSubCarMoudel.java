package com.xcoder.smartpark.moudel;

import java.io.Serializable;

/**
 * Created by xcoder_xz on 2016/12/28 0028.
 * 用车预约
 */

public class UseSubCarMoudel implements Serializable{
    private String RESERVE_END_TIME;//预约结束时间
    private String RESERVE_START_TIME;//预约开始时间
    private String DRIVER_ID;//司机ID

    private String CAR_RESERVE_ID;//主键

    private String USER_ID;////约车人
    private String CAR_ID;//车辆ID

    private String STATUS;//0：车辆可约 1：车辆不可约


    public String getRESERVE_END_TIME() {
        return RESERVE_END_TIME;
    }

    public void setRESERVE_END_TIME(String RESERVE_END_TIME) {
        this.RESERVE_END_TIME = RESERVE_END_TIME;
    }

    public String getRESERVE_START_TIME() {
        return RESERVE_START_TIME;
    }

    public void setRESERVE_START_TIME(String RESERVE_START_TIME) {
        this.RESERVE_START_TIME = RESERVE_START_TIME;
    }

    public String getDRIVER_ID() {
        return DRIVER_ID;
    }

    public void setDRIVER_ID(String DRIVER_ID) {
        this.DRIVER_ID = DRIVER_ID;
    }

    public String getCAR_RESERVE_ID() {
        return CAR_RESERVE_ID;
    }

    public void setCAR_RESERVE_ID(String CAR_RESERVE_ID) {
        this.CAR_RESERVE_ID = CAR_RESERVE_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getCAR_ID() {
        return CAR_ID;
    }

    public void setCAR_ID(String CAR_ID) {
        this.CAR_ID = CAR_ID;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
