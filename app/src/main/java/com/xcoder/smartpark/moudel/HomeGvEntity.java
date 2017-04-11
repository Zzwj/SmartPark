package com.xcoder.smartpark.moudel;

/**
 * Created by xcoder_xz on 2017/4/1 0001.
 * 首页-图标
 */

public class HomeGvEntity {
    /*
ASSETS	资产管理
BUS	班车
BUSDRIVER	班车司机
CAR	用车
CARAPPROVAL	用车审批
CARDRIVER	公车司机
CATERING	餐饮人员
CONFERENCEROOM	会议室
ENERGY	能源管理
INTELLIGENTLIFE	智慧生活
INTRODUCE	信息港园区介绍
NOTICE	通知公告
PATROL	巡更管理
PERSONNEL_STATISTICS	人员统计
PROPERTY	物业服务
PROPERTY_USER	物业人员
RESTAURANT	餐饮服务
TSMARKET	跳骚市场
VEDIO	视频监控
VISITOR	访客
     */
    private int SORTD;//id
    private String APPRESOURCE_ID;//类型名
    private String typeUrl;//类型图片url
    private Integer typeDrawable;//类型资源文件
    private Integer LEVE;//类型标识

    public HomeGvEntity() {
    }

    public HomeGvEntity(int SORTD, String APPRESOURCE_ID, String typeUrl, Integer typeDrawable, Integer LEVE) {
        this.SORTD = SORTD;
        this.APPRESOURCE_ID = APPRESOURCE_ID;
        this.typeUrl = typeUrl;
        this.typeDrawable = typeDrawable;
        this.LEVE = LEVE;
    }

    public HomeGvEntity(String APPRESOURCE_ID, Integer typeDrawable, Integer LEVE) {
        this.APPRESOURCE_ID = APPRESOURCE_ID;
        this.typeDrawable = typeDrawable;
        this.LEVE = LEVE;
    }

    public int getSORTD() {
        return SORTD;
    }

    public void setSORTD(int SORTD) {
        this.SORTD = SORTD;
    }

    public String getAPPRESOURCE_ID() {
        return APPRESOURCE_ID;
    }

    public void setAPPRESOURCE_ID(String APPRESOURCE_ID) {
        this.APPRESOURCE_ID = APPRESOURCE_ID;
    }

    public String getTypeUrl() {
        return typeUrl;
    }

    public void setTypeUrl(String typeUrl) {
        this.typeUrl = typeUrl;
    }

    public Integer getTypeDrawable() {
        return typeDrawable;
    }

    public void setTypeDrawable(Integer typeDrawable) {
        this.typeDrawable = typeDrawable;
    }

    public Integer getLEVE() {
        return LEVE;
    }

    public void setLEVE(Integer LEVE) {
        this.LEVE = LEVE;
    }
}
