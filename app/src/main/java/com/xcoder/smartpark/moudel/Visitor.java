package com.xcoder.smartpark.moudel;

import java.io.Serializable;

/**
 * Created by jiangkun on 16/12/28.
 */

public class Visitor implements Serializable{

    private String VISITOR_NUM;//访问人数
    private String CAR_NUMBER;//车牌
    private String MATTER;//拜访事项
    private String PHONE;//电话号码
    private String VISITOR_TIME;//预约时间
    private String USER_ID;//被访问人ID
    private String AUDITUSER_ID;//审核人ID
    private String NAME;//访问人姓名
    private String VISITOR_USER_ID;//
    private String USER_TEL;//被访问人电话
    private String NOTES;//备注
    private String AUDIT_STATUS;//状态0待审核 1同意 2拒绝
    private String VISITOR_STATUS;//
    private String USERNAME;//姓名
    private String ID;
    private String OWNED_COMPANY;//所属公司
    private String PASS_NUMBER;//通行证号码
    private String ID_CARD;//身份证
    private String AUDIT_TIME;//审核时间

    public String getVISITOR_NUM() {
        return VISITOR_NUM;
    }

    public void setVISITOR_NUM(String VISITOR_NUM) {
        this.VISITOR_NUM = VISITOR_NUM;
    }

    public String getCAR_NUMBER() {
        return CAR_NUMBER;
    }

    public void setCAR_NUMBER(String CAR_NUMBER) {
        this.CAR_NUMBER = CAR_NUMBER;
    }

    public String getMATTER() {
        return MATTER;
    }

    public void setMATTER(String MATTER) {
        this.MATTER = MATTER;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getVISITOR_TIME() {
        return VISITOR_TIME;
    }

    public void setVISITOR_TIME(String VISITOR_TIME) {
        this.VISITOR_TIME = VISITOR_TIME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getAUDITUSER_ID() {
        return AUDITUSER_ID;
    }

    public void setAUDITUSER_ID(String AUDITUSER_ID) {
        this.AUDITUSER_ID = AUDITUSER_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getVISITOR_USER_ID() {
        return VISITOR_USER_ID;
    }

    public void setVISITOR_USER_ID(String VISITOR_USER_ID) {
        this.VISITOR_USER_ID = VISITOR_USER_ID;
    }

    public String getUSER_TEL() {
        return USER_TEL;
    }

    public void setUSER_TEL(String USER_TEL) {
        this.USER_TEL = USER_TEL;
    }

    public String getNOTES() {
        return NOTES;
    }

    public void setNOTES(String NOTES) {
        this.NOTES = NOTES;
    }

    public String getAUDIT_STATUS() {
        return AUDIT_STATUS;
    }

    public void setAUDIT_STATUS(String AUDIT_STATUS) {
        this.AUDIT_STATUS = AUDIT_STATUS;
    }

    public String getVISITOR_STATUS() {
        return VISITOR_STATUS;
    }

    public void setVISITOR_STATUS(String VISITOR_STATUS) {
        this.VISITOR_STATUS = VISITOR_STATUS;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOWNED_COMPANY() {
        return OWNED_COMPANY;
    }

    public void setOWNED_COMPANY(String OWNED_COMPANY) {
        this.OWNED_COMPANY = OWNED_COMPANY;
    }

    public String getPASS_NUMBER() {
        return PASS_NUMBER;
    }

    public void setPASS_NUMBER(String PASS_NUMBER) {
        this.PASS_NUMBER = PASS_NUMBER;
    }

    public String getID_CARD() {
        return ID_CARD;
    }

    public void setID_CARD(String ID_CARD) {
        this.ID_CARD = ID_CARD;
    }

    public String getAUDIT_TIME() {
        return AUDIT_TIME;
    }

    public void setAUDIT_TIME(String AUDIT_TIME) {
        this.AUDIT_TIME = AUDIT_TIME;
    }
}
