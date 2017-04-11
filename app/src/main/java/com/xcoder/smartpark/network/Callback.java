package com.xcoder.smartpark.network;

import org.json.JSONObject;

public interface Callback {

	void onSuccess(JSONObject jsonObject, String state, String msg);//正确

	void onFailure(JSONObject rawJsonObj, String state, String msg);//服务器返回的错误情况

	void onError(Exception exception);//客户端自己的错误
}
