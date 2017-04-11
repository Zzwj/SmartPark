package com.xcoder.smartpark.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.app.AppPathManager;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.AppErrMoudle;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by xcoder_xz on 2017/1/1 0001.
 * 处理app的一些异常，并记录上传到特定的服务器
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    //CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    private FileOutputStream fs;
    private BufferedOutputStream bos;
    private Map<String, File> map;

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public CrashHandler() {
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //异步向服务器发送错误日志
        CrashHandlerAsyncTask crashHandlerAsyncTask = new CrashHandlerAsyncTask();
        crashHandlerAsyncTask.execute();
    }




    @Override
    public void uncaughtException(Thread t, final Throwable e) {
        if (mDefaultHandler != null) {
            //把错误收集保存到SD卡中
            //如果用户没有处理则让系统默认的异常处理器来处理
            handleException(e);
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    ToastUtil.showToast("程序发生未知错误!请重新启动");
                    Looper.loop();
                }
            }.start();

            try {
                //睡眠3秒后，关闭app
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                mDefaultHandler.uncaughtException(t, e);
            }
        }
        mDefaultHandler.uncaughtException(t, e);
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private Boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        try {
            if (ConfigSP.getIsSaveErrLog()) {
                //判断路径有没有,如果没有就创建
                File dir = new File(AppPathManager.getSaveLogPath());
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, AppPathManager.getLogName());
                //写入log，加上版本号，和app名称
                //得到版本号
                PackageManager manager = SmartParkApplication.context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(SmartParkApplication.context.getPackageName(), 0);
                String appVersionName = info.versionName; // 版本名
                int currentVersionCode = info.versionCode; // 版本号
                String release = Build.VERSION.RELEASE;//获取版本名
                String model = Build.MODEL;//获取手机型号

                AppErrMoudle appErrMoudle = new AppErrMoudle();
                appErrMoudle.setAppName("智慧园区");
                appErrMoudle.setVersionName(appVersionName);
                appErrMoudle.setVersionCode(currentVersionCode + "");
                appErrMoudle.setErrPhone("手机型号" + model + "-" + "手机版本号" + release);
                appErrMoudle.setErrNewTime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
//            StringBuffer sb = new StringBuffer();
//            sb.append("App:SmarkPart(智慧园区)" + "\n");
//            sb.append("appName:" + appVersionName + "\n");
//            sb.append("appCode:" + currentVersionCode + "\n");
//            sb.append("appRelease(手机版本号):" + release + "\n");
//            sb.append("appModel(手机型号 ):" + model + "\n");
//            sb.append("appNewTime(错误生成时间 ):" + new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date()) + "\n");
//            sb.append("appErrMessage:" + ex.getMessage() + "\n");
//            sb.append("appErr:" + "\n");
                //写入错误信息
                StringBuffer sb = new StringBuffer();
                StackTraceElement[] elements = ex.getStackTrace();
                for (int i = 0; i < elements.length; i++) {
                    sb.append(elements[i].toString() + "\n");
                }
                appErrMoudle.setErrMsg("错误信息:" + ex.getMessage() + "-" + "错误详细:" + sb.toString());
                String appErr = JSON.toJSONString(appErrMoudle);

                fs = new FileOutputStream(file);
                bos = new BufferedOutputStream(fs);
                bos.write(appErr.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bos.flush();
                bos.close();
                fs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
