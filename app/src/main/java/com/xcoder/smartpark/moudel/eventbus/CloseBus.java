package com.xcoder.smartpark.moudel.eventbus;

/**
 * Created by xcoder_xz on 2016/12/16 0016.
 * 用来关闭所有activity
 */

public class CloseBus {
    private Boolean isClose=false;

    public CloseBus() {
        super();
    }

    public CloseBus(Boolean isClose) {
        this.isClose = isClose;
    }

    public Boolean getClose() {
        return isClose;
    }

    public void setClose(Boolean close) {
        isClose = close;
    }
}
