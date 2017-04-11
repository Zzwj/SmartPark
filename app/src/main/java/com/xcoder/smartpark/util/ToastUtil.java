package com.xcoder.smartpark.util;

import android.widget.Toast;

import com.xcoder.smartpark.app.SmartParkApplication;

public class ToastUtil {

	public static void showToast(String string) {
		Toast.makeText(SmartParkApplication.getAppContext(), string, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 暂无更多数据吐司
	 */
	public static void noData() {
		Toast.makeText(SmartParkApplication.getAppContext(), "暂无更多数据", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 无网络吐司
	 * 
	 * @param
	 */
	public static void noNet() {
		Toast.makeText(SmartParkApplication.getAppContext(), "网络繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();
	}
}
