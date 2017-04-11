package com.xcoder.smartpark.network;


import java.io.File;

/**
 * Created by Administrator on 2016/10/9 0009.
 */

public interface FileCallback {

    /**
     *
     *            返回的原始json对象
     * @param state
     * @param msg
     */
    void onSuccess(File response, String state, String msg);

    void inProgress(float progress, long total);

    void onFailure(Exception error, String state, String msg);

    void onError();


}
