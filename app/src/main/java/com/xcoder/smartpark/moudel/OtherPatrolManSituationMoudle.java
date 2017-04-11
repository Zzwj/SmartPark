package com.xcoder.smartpark.moudel;

import java.io.Serializable;

/**
 * Created by jiangkun on 16/12/30.
 */

public class OtherPatrolManSituationMoudle implements Serializable{
    private String RADDRESS;//巡更地址
    private String STATUS;//
    private String IS_PATROL;//0:未巡更 1：已巡更
    private String PATROLLINE_ID;//线路ID
    private String RFIDDETECTOR_ID;//rfid设备ID
    private String SORT;//排序
    private String USER_ID;//巡更员ID
    private String PATROLLINE_PLACE_ID;//
    private String UPDATE_TIME;//巡更时间

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public String getRADDRESS() {
        return RADDRESS;
    }

    public void setRADDRESS(String RADDRESS) {
        this.RADDRESS = RADDRESS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getIS_PATROL() {
        return IS_PATROL;
    }

    public void setIS_PATROL(String IS_PATROL) {
        this.IS_PATROL = IS_PATROL;
    }

    public String getPATROLLINE_ID() {
        return PATROLLINE_ID;
    }

    public void setPATROLLINE_ID(String PATROLLINE_ID) {
        this.PATROLLINE_ID = PATROLLINE_ID;
    }

    public String getRFIDDETECTOR_ID() {
        return RFIDDETECTOR_ID;
    }

    public void setRFIDDETECTOR_ID(String RFIDDETECTOR_ID) {
        this.RFIDDETECTOR_ID = RFIDDETECTOR_ID;
    }

    public String getSORT() {
        return SORT;
    }

    public void setSORT(String SORT) {
        this.SORT = SORT;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getPATROLLINE_PLACE_ID() {
        return PATROLLINE_PLACE_ID;
    }

    public void setPATROLLINE_PLACE_ID(String PATROLLINE_PLACE_ID) {
        this.PATROLLINE_PLACE_ID = PATROLLINE_PLACE_ID;
    }
}
