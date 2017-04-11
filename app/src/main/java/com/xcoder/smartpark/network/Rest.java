package com.xcoder.smartpark.network;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.util.MD5Util;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Response;

/**
 * 用于请求的类
 * xcoder_xz
 */
public class Rest {

    private String method;
    private RequestCall callFile;//文件请求

    public Rest(String method) {
        this.method = method;
        okParams.put("TOKEN", MD5Util.encode("E050D0E795C84B9DBD07FD0DB228DD32" + "a12b3c"));
    }

    /**
     * @param method    请求路径
     * @param userId    用户id
     * @param userToken 用户登陆的token
     */
    public Rest(String method, String userId, String userToken) {
        this.method = method;
        okParams.put("TOKEN", MD5Util.encode("E050D0E795C84B9DBD07FD0DB228DD32" + "a12b3c"));
        okParams.put("STOKEN", MD5Util.encode(userToken + "a12b3c"));
        okParams.put("USERID", userId);
    }

    public Rest addParam(String paramName, String paramValue) {
        okParams.put(paramName, paramValue);
        return this;
    }


    private Map<String, String> okParams = new ArrayMap<String, String>();

    /**
     * 此方法用于标识tag，的get请求
     *
     * @param object
     * @param callback
     * @用于可能中途需要手动停止请求的操作
     */
    public void get(Object object, final Callback callback) {
        OkHttpUtils
                .get()
                .url(AppNet.appNet + method)
                .tag(object)
                .params(okParams)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        httpCallback(response, callback);
                    }
                });
    }


    /**
     * get请求
     *
     * @param callback
     */
    public void get(final Callback callback) {
        OkHttpUtils
                .get()
                .url(AppNet.appNet + method)
                .params(okParams)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        httpCallback(response, callback);
                    }
                });

    }

    /**
     * 同步get请求
     *
     * @return
     * @throws IOException
     */
    public Response getSync() throws IOException {
        Response execute = OkHttpUtils.get().url(AppNet.appNet + method).params(okParams).build().execute();
        return execute;
    }

    /**
     * @param object
     * @param callback
     * @see带tag的post请求；
     * @用于可能中途需要手动停止请求的操作
     */
    public void post(Object object, final Callback callback) {
        //开发阶段增加接口名、参数、返回值打印，便于调试，发布版本需要关闭打印
        LogUtils.d("rest---接口名:" + AppNet.appNet + method + "  参数:" + okParams.toString());
        OkHttpUtils
                .post()
                .url(AppNet.appNet + method)
                .params(okParams)
                .tag(object)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        //如果是canceled或者scket closed，就是说明是主动关闭网络的
                        if (!TextUtils.equals(e.getMessage(), "Canceled") && !TextUtils.equals(e.getMessage(), "Socket closed")) {
                            callback.onError(e);
                            e.printStackTrace();
                        }
                        LogUtils.e("rest---exception:" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d("rest---response:" + response);
                        httpCallback(response, callback);
                    }
                });
    }

    /**
     * @param object
     * @param file
     * @param callback
     * @see带tag和file的post请求；
     * @用于可能中途需要手动停止请求的操作
     */
    public void post(Object object, String fileKey, File file, final Callback callback) {
        OkHttpUtils
                .post()
                .url(AppNet.appNet + method)
                .params(okParams)
                .addFile(fileKey, file.getName(), file)
                .tag(object)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        LogUtils.e("Exception:" + e);
                        //如果是canceled或者scket closed，就是说明是主动关闭网络的
                        if (!TextUtils.equals(e.getMessage(), "Canceled") && !TextUtils.equals(e.getMessage(), "Socket closed")) {
                            callback.onError(e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d("response:" + response);
                        httpCallback(response, callback);
                    }
                });
    }


    /**
     * @param callback
     * @see一般的post的请求
     */
    public void post(final Callback callback) {
        //开发阶段增加接口名、参数、返回值打印，便于调试，发布版本需要关闭打印
        //  LogUtils.d("rest---接口名:"+AppNet.appNet + method + "  参数:"+ okParams.toString());
        OkHttpUtils
                .post()
                .url(AppNet.appNet + method)
                .params(okParams)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        LogUtils.e("rest---exception:" + e);
                        //如果是canceled或者scket closed，就是说明是主动关闭网络的
                        if (!TextUtils.equals(e.getMessage(), "Canceled") && !TextUtils.equals(e.getMessage(), "Socket closed")) {
                            callback.onError(e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d("rest---response:" + response);
                        httpCallback(response, callback);
                    }
                });
    }


    /**
     * 对请求的二次封装返回码
     *
     * @param response
     * @param callback
     */
    private void httpCallback(String response, Callback callback) {
        try {
            JSONObject raw = new JSONObject(response);
            String status = raw.getString("status");//状态码
            String msg = raw.has("msg") ? raw.getString("msg") : "";//状态消息

            if (TextUtils.equals(status, "0000")) {
                // 成功
                callback.onSuccess(raw, status, msg);
            } else if (TextUtils.equals(status, "0001")) {
                // 参数不完整或不正确
                callback.onFailure(raw, status, msg);
            } else if (TextUtils.equals(status, "9992")) {
                //后台执行失败
                callback.onFailure(raw, status, msg);
            } else if (TextUtils.equals(status, "9999")) {
                //执行不成功
                callback.onFailure(raw, status, msg);
            } else if (TextUtils.equals(status, "450")) {
                //地址有误
                callback.onFailure(raw, status, msg);
            } else {
                //未知错误
                callback.onFailure(raw, status, msg);
            }
        } catch (Exception exception) {
            //未知错误
            callback.onError(exception);
            exception.printStackTrace();
        }
    }

    /**
     * @param object:请求标识
     * @param fileUrl:下载路径
     * @param saveFilePath:文件保存路径
     * @param saveFileName:文件名字
     * @param callback
     * @用于可能中途需要手动停止请求的操作
     */
    public void getFile(Object object, String fileUrl, String saveFilePath, String saveFileName, final FileCallback callback) {
        final File file = new File(saveFilePath, saveFileName);
        if (file.exists()) {
            //文件已存在
            callback.onFailure(null, null, "文件已存在");
            return;
        }

        OkHttpUtils.get().url(fileUrl).tag(object).build().execute(new FileCallBack(saveFilePath, saveFileName) {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                if (e.getMessage().equals("Not Found")) {
                    callback.onFailure(null, null, "服务器文件地址有误");
                    return;
                } else {
                    callback.onFailure(null, null, "下载错误,请重试");
                }
            }

            @Override
            public void onResponse(File response, int id) {
                callback.onSuccess(response, null, "下载完成");
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                callback.inProgress(progress, total);
            }
        });
    }

    /**
     * @param fileUrl:下载路径
     * @param saveFilePath:文件保存路径
     * @param saveFileName:文件名字
     * @param callback
     */
    public void getFile(String fileUrl, String saveFilePath, String saveFileName, final FileCallback callback) {
        final File file = new File(saveFilePath, saveFileName);
        if (file.exists()) {
            //文件已存在
            callback.onFailure(null, null, "文件已存在");
            return;
        }

        callFile = OkHttpUtils.get().url(fileUrl).build();
        callFile.execute(new FileCallBack(saveFilePath, saveFileName) {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                if (e.getMessage().equals("Not Found")) {
                    callback.onFailure(null, null, "服务器文件地址有误");
                    return;
                } else {
                    callback.onFailure(null, null, "下载错误,请重试");
                }
            }

            @Override
            public void onResponse(File response, int id) {
                callback.onSuccess(response, null, "下载完成");
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                callback.inProgress(progress, total);
            }
        });
    }

    /**
     * 停止下载文件
     */
    public void stopGetFile() {
        try {
            callFile.cancel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * @param object
     * @param file
     * @param callback
     * @see——多错误日志文件上传
     * @用于可能中途需要手动停止请求的操作
     */
    public void postFiles(Object object, String url, String name, String fileName, File file, final Callback callback) {
        OkHttpUtils
                .post()
                .url(url)
                .params(okParams)
                .addFile(name, fileName, file)
                .tag(object)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        LogUtils.e("Exception:" + e);
                        //如果是canceled或者scket closed，就是说明是主动关闭网络的
                        if (!TextUtils.equals(e.getMessage(), "Canceled") && !TextUtils.equals(e.getMessage(), "Socket closed")) {
                            callback.onError(e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d("response:" + response);
                       httpCallback(response, callback);
//                        callback.onSuccess();
                    }
                });
    }


    /**
     * @param object
     * @param callback
     * @see带tag的post请求；
     * @用于可能中途需要手动停止请求的操作
     */
    public void postErr(Object object, String url, final Callback callback) {
        //开发阶段增加接口名、参数、返回值打印，便于调试，发布版本需要关闭打印
        OkHttpUtils
                .post()
                .url(url)
                .params(okParams)
                .tag(object)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        //如果是canceled或者scket closed，就是说明是主动关闭网络的
                        if (!TextUtils.equals(e.getMessage(), "Canceled") && !TextUtils.equals(e.getMessage(), "Socket closed")) {
                            callback.onError(e);
                            e.printStackTrace();
                        }
                        LogUtils.e("rest---exception:" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d("rest---response:" + response);
                        httpCallback(response, callback);
                    }
                });
    }
}
