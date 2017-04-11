package com.xcoder.smartpark.util;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.xcoder.lib.app.AppPathManager;
import com.xcoder.lib.utils.Utils;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by xcoder_xz on 2017/1/5 0005.
 * 异步上传app崩溃的异常信息
 */

public class CrashHandlerAsyncTask extends AsyncTask {
    private File[] files;

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            UploadLog();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查有没有目录下有没有errlog文件，如果有就上传
     */
    public void UploadLog() throws Exception {
        File dirFile = new File(AppPathManager.getSaveLogPath());
        if (!dirFile.exists()) {
            return;
        }
        if (dirFile.isDirectory()) {
            //返回文件夹中有的数据
            files = dirFile.listFiles();
            //先判断下有没有权限，如果没有权限的话，就不执行了
            if (null == files)
                return;
            if (files.length > 0) {
                //读取文件，转成对象
                InputStreamReader read = null;
                try {
                    read = new InputStreamReader(new FileInputStream(files[0]));//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String strJson = "";
                    String lineTxt = null;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        strJson += lineTxt;
                    }
                    read.close();
                    // AppErrMoudle appErrMoudle = JSON.parseObject(strJson, AppErrMoudle.class);
                    Rest rest = new Rest("");
                    rest.addParam("inJson", strJson);
                    // rest.addParam("versionCode", appErrMoudle.getVersionCode());
//                    rest.addParam("versionName", appErrMoudle.getVersionName());
//                    rest.addParam("errContent", appErrMoudle.getErrContent());
//                    rest.addParam("errNewTime", appErrMoudle.getErrNewTime());
//                    rest.addParam("errPhone", appErrMoudle.getErrPhone());
                    //    rest.postErr("errLog", "http://123.56.45.223:8099/framework/errorcollect/uploadErrorInfo", new Callback() {
                    rest.postFiles("", "http://123.56.45.223:8099/framework/errorcollect/uploadErrorInfo", "file", files[0].getName(), files[0], new Callback() {
                        public void onSuccess(JSONObject jsonObject, String state, String msg) {
                            try {
                                if (TextUtils.equals(msg, "停止获取错误文件")) {
                                    ConfigSP.setIsSaveErrLog(false);
                                }
                                Utils.deleteFile(files[0]);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                            if (TextUtils.equals(msg, "停止获取错误文件")) {
                                ConfigSP.setIsSaveErrLog(false);
                            }
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        read.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
