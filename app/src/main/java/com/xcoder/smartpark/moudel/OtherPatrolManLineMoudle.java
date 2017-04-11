package com.xcoder.smartpark.moudel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangkun on 16/12/30.
 * 巡更线路
 */

public class OtherPatrolManLineMoudle implements Serializable{

    private String STATUS;
    private List<OtherPatrolManSituationMoudle> PLIST;
    private String PATROLLINE_ID;//巡更线路ID
    private String PATROLLINE_NAME;//巡更线路名称

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public List<OtherPatrolManSituationMoudle> getPLIST() {
        return PLIST;
    }

    public void setPLIST(List<OtherPatrolManSituationMoudle> PLIST) {
        this.PLIST = PLIST;
    }

    public String getPATROLLINE_ID() {
        return PATROLLINE_ID;
    }

    public void setPATROLLINE_ID(String PATROLLINE_ID) {
        this.PATROLLINE_ID = PATROLLINE_ID;
    }

    public String getPATROLLINE_NAME() {
        return PATROLLINE_NAME;
    }

    public void setPATROLLINE_NAME(String PATROLLINE_NAME) {
        this.PATROLLINE_NAME = PATROLLINE_NAME;
    }
}
