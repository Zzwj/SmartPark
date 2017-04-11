package com.xcoder.smartpark.moudel;

import java.io.Serializable;

/**
 * 用户身份对象（公司、部门、职位统一用这个对象）
 * Created by jiangkun on 16/12/22.
 */

public class UserIdentity implements Serializable {
    private String PNAME;//分组
    private String ZD_ID;//主键
    private String JB;//级别
    private String BIANMA;//编码
    private String NAME;//名称
    private String ORDY_BY;//排序
    private String PARENT_ID;//父节点
    private String P_BM;//父节点编码

    public String getPNAME() {
        return PNAME;
    }

    public void setPNAME(String PNAME) {
        this.PNAME = PNAME;
    }

    public String getZD_ID() {
        return ZD_ID;
    }

    public void setZD_ID(String ZD_ID) {
        this.ZD_ID = ZD_ID;
    }

    public String getJB() {
        return JB;
    }

    public void setJB(String JB) {
        this.JB = JB;
    }

    public String getBIANMA() {
        return BIANMA;
    }

    public void setBIANMA(String BIANMA) {
        this.BIANMA = BIANMA;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getORDY_BY() {
        return ORDY_BY;
    }

    public void setORDY_BY(String ORDY_BY) {
        this.ORDY_BY = ORDY_BY;
    }

    public String getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(String PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    public String getP_BM() {
        return P_BM;
    }

    public void setP_BM(String p_BM) {
        P_BM = p_BM;
    }

    @Override
    public String toString() {
        return "UserIdentity{" +
                "PNAME='" + PNAME + '\'' +
                ", ZD_ID='" + ZD_ID + '\'' +
                ", JB='" + JB + '\'' +
                ", BIANMA='" + BIANMA + '\'' +
                ", NAME='" + NAME + '\'' +
                ", ORDY_BY='" + ORDY_BY + '\'' +
                ", PARENT_ID='" + PARENT_ID + '\'' +
                ", P_BM='" + P_BM + '\'' +
                '}';
    }
}
