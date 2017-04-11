package com.xcoder.smartpark.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.xcoder.lib.app.AppActivityManager;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.lib.utils.NetUtil;
import com.xcoder.lib.utils.Utils;


/**
 * @类名:BaseFragmentActivity
 * @类描FragmentActivity基类，所有FragmentActivity子类都需继承此类
 * @作Administrator
 * @修改
 * @修改时间:
 * @修改备注:
 * @版本:
 * 
 */
public abstract class BaseFragmentActivity extends FragmentActivity {


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		AppActivityManager.getScreenManager().pushActivity(this);
		ViewUtils.inject(this);
		createActivity();
	}

	public abstract void createActivity();

	/**
	 * @方法说明:启动指定activity
	 * @方法名称:openActivity
	 * @param pClass
	 * @返回void
	 */
	public void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	/**
	 * @方法说明:启动到指定activity，Bundle传递对象（作用个界面之间传递数据）
	 * @方法名称:openActivity
	 * @param pClass
	 * @param pBundle
	 * @返回void
	 */
	public void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	/**
	 * @方法说明:根据界面name启动到指定界 * @方法名称:openActivity
	 * @param pAction
	 * @返回void
	 */
	public void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	/**
	 * @方法说明:根据界面name启动到指定界面，Bundle传递对象（作用个界面之间传递数据）
	 * @方法名称:openActivity
	 * @param pAction
	 * @param pBundle
	 * @返回void
	 */
	public void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	/**
	 * @方法说明:A-Activity需要在B-Activtiy中执行一些数据操作，而B-Activity又要将，执行操作数据的结果返回给A-Activity
	 * @方法名称:openActivityResult
	 * @param pClass
	 * @返回void
	 */
	public void openActivityResult(Class<?> pClass) {
		openActivityResult(pClass, null);
	}

	/**
	 * @方法说明:A-Activity需要在B-Activtiy中执行一些数据操作， 
	 *                                         而B-Activity又要将，执行操作数据的结果返回给A-Activity
	 *                                         Bundle传递对象（作用个界面之间传递数据）
	 * @方法名称:openActivityResult
	 * @param pClass
	 * @param pBundle
	 * @返回void
	 */
	public void openActivityResult(Class<?> pClass, Bundle pBundle) {
		openActivityResult(pClass, pBundle, 0);
	}

	/**
	 * @方法说明:A-Activity需要在B-Activtiy中执行一些数据操作， 
	 *                                         而B-Activity又要将，执行操作数据的结果返回给A-Activity
	 *                                         Bundle传递对象（作用个界面之间传递数据）
	 * @方法名称:openActivityResult
	 * @param pClass
	 * @param pBundle
	 * @param requestCode
	 * @返回void
	 */
	public void openActivityResult(Class<?> pClass, Bundle pBundle,
								   int requestCode) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivityForResult(intent, requestCode);
	}

	/**
	 * @方法说明:获取string资源字段土司
	 * @方法名称:showShortToast
	 * @param pResId
	 * @返回void
	 */
	public void showShortToast(int pResId) {
		showShortToast(getString(pResId));
	}

	/**
	 * @方法说明:长时间土 * @方法名称:showLongToast
	 * @param pMsg
	 * @返回void
	 */
	public void showLongToast(String pMsg) {
		Toast.makeText(this, pMsg, Toast.LENGTH_LONG).show();
	}

	/**
	 * @方法说明:短时间土 * @方法名称:showShortToast
	 * @param pMsg
	 * @返回void
	 */
	public void showShortToast(String pMsg) {
		Toast.makeText(this, pMsg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * @方法说明:退出栈中所有Activity
	 * @方法名称:finishBase
	 * @返回void
	 */
	public void finishBase() {
		AppActivityManager.getScreenManager().popAllActivityExceptOne(
				getClass());
		finish();
	}

	/*
	 * @重写方法onDestroy
	 * 
	 * @父类:@see android.support.v4.app.FragmentActivity#onDestroy()
	 * 
	 * @方法说明: Copyright:(c)-2014烈焰鸟网络科技有限公司
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeActivity();
		AppActivityManager.getScreenManager().popActivity(this);
	}

	/**
	 * @方法说明:手动释放内存
	 * @方法名称:releaseMemory
	 * @返回void
	 */
	public abstract void closeActivity();

	/*
	 * @重写方法onResume
	 * 
	 * @父类:@see android.support.v4.app.FragmentActivity#onResume()
	 * 
	 * @方法说明: Copyright:(c)-2014烈焰鸟网络科技有限公司
	 */
	@Override
	protected void onResume() {
		super.onResume();
		uiRefresh();
	}

	public void uiRefresh() {
	}

	public void refresh(Object... param) {
	}

	/**
	 * @方法说明:有网络处 * @方法名称:onConnect
	 * @param type
	 * @返回void
	 */
	public void onConnect(NetUtil.netType type) {
		// showShortToast("当前网络是：" + type.toString());
	}

	/**
	 * @方法说明:无网络处 * @方法名称:onDisConnect
	 * @返回void
	 */
	public void onDisConnect() {
		showShortToast("当前无网络");
	}

	/*
	 * @重写方法 onKeyDown
	 * 
	 * @父类:@see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 * 
	 * @方法说明:
	 */
	@SuppressWarnings("static-access")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK) {
			if (Utils.backDouble(800)) {
				return super.onKeyDown(keyCode, event);
			}
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}





}
