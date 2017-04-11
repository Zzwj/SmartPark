package com.xcoder.smartpark.moudel;

import java.io.Serializable;

/**
 * Created by xcoder_xz on 2017/1/7 0007.
 * 公告
 */

public class MessageMoudel implements Serializable{
    private String NOTICEINFO_ID;
    private String TITLE;
    private String SENDTIME;
    private String CONTENT;

    public String getNOTICEINFO_ID() {
        return NOTICEINFO_ID;
    }

    public void setNOTICEINFO_ID(String NOTICEINFO_ID) {
        this.NOTICEINFO_ID = NOTICEINFO_ID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getSENDTIME() {
        return SENDTIME;
    }

    public void setSENDTIME(String SENDTIME) {
        this.SENDTIME = SENDTIME;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }
}
