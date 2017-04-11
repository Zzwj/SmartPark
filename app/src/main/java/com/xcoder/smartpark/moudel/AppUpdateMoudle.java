package com.xcoder.smartpark.moudel;

/**
 * Created by xcoder_xz on 2017/1/2 0002.
 * app更新的moudle
 */

public class AppUpdateMoudle {
    private String UPDATE_DESCRIPTION;
    private String RELEASE_TIME;
  //  private String Max(VERSION_NUMBER);
    private String VERSION_CODE;
    private String FILEPATH;
    private String ID;
    private String MANDATORYUPGRADE;
    private String ISACTIVE;

    public String getUPDATE_DESCRIPTION() {
        return UPDATE_DESCRIPTION;
    }

    public void setUPDATE_DESCRIPTION(String UPDATE_DESCRIPTION) {
        this.UPDATE_DESCRIPTION = UPDATE_DESCRIPTION;
    }

    public String getRELEASE_TIME() {
        return RELEASE_TIME;
    }

    public void setRELEASE_TIME(String RELEASE_TIME) {
        this.RELEASE_TIME = RELEASE_TIME;
    }

    public String getVERSION_CODE() {
        return VERSION_CODE;
    }

    public void setVERSION_CODE(String VERSION_CODE) {
        this.VERSION_CODE = VERSION_CODE;
    }

    public String getFILEPATH() {
        return FILEPATH;
    }

    public void setFILEPATH(String FILEPATH) {
        this.FILEPATH = FILEPATH;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMANDATORYUPGRADE() {
        return MANDATORYUPGRADE;
    }

    public void setMANDATORYUPGRADE(String MANDATORYUPGRADE) {
        this.MANDATORYUPGRADE = MANDATORYUPGRADE;
    }

    public String getISACTIVE() {
        return ISACTIVE;
    }

    public void setISACTIVE(String ISACTIVE) {
        this.ISACTIVE = ISACTIVE;
    }
}
