package com.xcoder.smartpark.activity.other.demo;

import java.io.Serializable;

/**
 * 资产判断上传mac返回的资产实体
 * Created by jiangkun on 17/3/6.
 */

public class BLEEntity implements Serializable{
    private String INVENTORYUSER;
    private String CREATEDATE;
    private String MACADDRESS;
    private String ASSETS_GOODS_ID;
    private String IBEACONUUID;
    private String ASSETS_LOCATION_ID;
    private String NOTES;
    private String LOCATIONSTATUS;
    private String ASSETNAME;
    private String STATUS;
    private String NEWASSETS_LOCATION_ID;
    private String IBEACONMAJOR;
    private String IBEACONMINOR;
    private String LIGHTSTAUTS;
    private String ELECTRICITY;

    public String getINVENTORYUSER() {
        return INVENTORYUSER;
    }

    public void setINVENTORYUSER(String INVENTORYUSER) {
        this.INVENTORYUSER = INVENTORYUSER;
    }

    public String getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(String CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public String getMACADDRESS() {
        return MACADDRESS;
    }

    public void setMACADDRESS(String MACADDRESS) {
        this.MACADDRESS = MACADDRESS;
    }

    public String getASSETS_GOODS_ID() {
        return ASSETS_GOODS_ID;
    }

    public void setASSETS_GOODS_ID(String ASSETS_GOODS_ID) {
        this.ASSETS_GOODS_ID = ASSETS_GOODS_ID;
    }

    public String getIBEACONUUID() {
        return IBEACONUUID;
    }

    public void setIBEACONUUID(String IBEACONUUID) {
        this.IBEACONUUID = IBEACONUUID;
    }

    public String getASSETS_LOCATION_ID() {
        return ASSETS_LOCATION_ID;
    }

    public void setASSETS_LOCATION_ID(String ASSETS_LOCATION_ID) {
        this.ASSETS_LOCATION_ID = ASSETS_LOCATION_ID;
    }

    public String getNOTES() {
        return NOTES;
    }

    public void setNOTES(String NOTES) {
        this.NOTES = NOTES;
    }

    public String getLOCATIONSTATUS() {
        return LOCATIONSTATUS;
    }

    public void setLOCATIONSTATUS(String LOCATIONSTATUS) {
        this.LOCATIONSTATUS = LOCATIONSTATUS;
    }

    public String getASSETNAME() {
        return ASSETNAME;
    }

    public void setASSETNAME(String ASSETNAME) {
        this.ASSETNAME = ASSETNAME;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getNEWASSETS_LOCATION_ID() {
        return NEWASSETS_LOCATION_ID;
    }

    public void setNEWASSETS_LOCATION_ID(String NEWASSETS_LOCATION_ID) {
        this.NEWASSETS_LOCATION_ID = NEWASSETS_LOCATION_ID;
    }

    public String getIBEACONMAJOR() {
        return IBEACONMAJOR;
    }

    public void setIBEACONMAJOR(String IBEACONMAJOR) {
        this.IBEACONMAJOR = IBEACONMAJOR;
    }

    public String getIBEACONMINOR() {
        return IBEACONMINOR;
    }

    public void setIBEACONMINOR(String IBEACONMINOR) {
        this.IBEACONMINOR = IBEACONMINOR;
    }

    public String getLIGHTSTAUTS() {
        return LIGHTSTAUTS;
    }

    public void setLIGHTSTAUTS(String LIGHTSTAUTS) {
        this.LIGHTSTAUTS = LIGHTSTAUTS;
    }

    public String getELECTRICITY() {
        return ELECTRICITY;
    }

    public void setELECTRICITY(String ELECTRICITY) {
        this.ELECTRICITY = ELECTRICITY;
    }


}
