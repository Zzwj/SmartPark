package com.xcoder.smartpark.network;

/**
 * Created by xcoder_xz on 2016/12/5 0005.
 * 此类用于存放，网页，和一些静态常量的
 */

public class AppNet {
    private static final String API_TEST = "http://123.56.45.223:9080/hyq";//测试域名
    private static final String API_FORMAL = "http://61.232.6.34:9080/hyq";//正式域名

    public static final String appNet = API_FORMAL;

    public static final String API_IMAGE=appNet + "/uploadFiles/uploadImgs/";//图片路径

    public static final String APP_Key="";

    //嵌套H5页面汇总
    public static String appvisitor = "/appvisitor/goAdd.ndo";//访客预约
    public static String apppatrol = "/apppatrol/goMap.ndo";//人员统计
    public static String appbus = "/appbus/goBusDetailList.ndo";//司机查看页面拼接"?BUS_ID=#BUS_ID"
    public static String apphelp = "/appvisitor/goHelp.ndo";//使用说明
    public static String appabout = "/appvisitor/goAbout.ndo";//关于
    public static String appnotice = "/appvisitor/goNotice.ndo";//预约说明
    public static String appcarnotice = "/appcar/Introductions.ndo";//预约车说明
}
