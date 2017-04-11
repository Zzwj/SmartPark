package com.xcoder.lib.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by xz on 2016/12/3 0003.
 */

public class BaseApplication extends Application {
    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        //配置app的文件缓存目录的文件夹名称
        AppPathManager.initPathManager("SmartPark");

    }

    /**
     * @return
     * @方法说明:获得程序上下
     * @方法名称:getAppContext
     * @返回值:Context
     */
    public static Context getAppContext() {
        return BaseApplication.context;
    }

}
