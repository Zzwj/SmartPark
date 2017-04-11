package com.xcoder.smartpark.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @类名:BaseFragment
 * @类描述:碎片基类，所有fragment子类需继承此类
 * @作者:Administrator
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @版本:
 * 
 */
public abstract class BaseFragment extends Fragment {
	//private Context context;// 上下文

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return initLayout(inflater);// 布局;
	}

//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//		context = activity;
//	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState == null) {// 数据初始化
			dataInit();
		} else {// 数据恢复
			dataRestore(savedInstanceState);
		}
		initData();// 事件
	}

	/**
	 * @方法说明:布局
	 * @方法名称:layout
	 * @param inflater
	 * @return
	 * @返回值:View
	 */
	public abstract View initLayout(LayoutInflater inflater);

	/**
	 * @方法说明:数据初始化
	 * @方法名称:dataInit
	 * @返回值:void
	 */
	public void dataInit() {
	}

	@Override
	public void onResume() {
		super.onResume();
		resume();
	}




	public void resume() {
	}

	/**
	 * @方法说明:数据恢复
	 * @方法名称:dataRestore
	 * @param savedInstanceState
	 * @返回值:void
	 */
	public void dataRestore(Bundle savedInstanceState) {
	}

	/**
	 * @方法说明:处理
	 * @方法名称:eventDispose
	 * @返回值:void
	 */
	public void initData() {
	}

//	/**
//	 * @方法说明:获得上下文环境
//	 * @方法名称:getContext
//	 * @return
//	 * @返回值:Context
//	 */
//	public Context getContext() {
//		return context;
//	}

	/**
	 * @方法说明:接收主activity传递的消息
	 * @方法名称:receiverAttach
	 * @param param
	 * @返回值:void
	 */
	public void receiverAttach(Object[] param) {
	}

	/**
	 * @类名:OnHeadlineSelectedListener
	 * @类描述:管理类事件触发
	 */
	public interface OnHeadlineSelectedListener {
		void onArticleSelected(Object[] param);
	}

	/**
	 * @方法说明:界面刷新
	 * @方法名称:uiRefrsh
	 * @返回值:void
	 */
	public void uiRefrsh() {
	}

	/**
	 * @方法说明:按键处理
	 * @方法名称:onKeyDown
	 * @param keyCode
	 * @param event
	 * @return
	 * @返回值:boolean
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		closeFragment();
	}

	/**
	 * @方法说明:手动释放内存
	 * @方法名称:releaseMemory
	 * @返回值:void
	 */
	public abstract void closeFragment();

	/**
	 * @方法说明:启动指定activity
	 * @方法名称:openActivity
	 * @param pClass
	 * @返回值:void
	 */
	public void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	/**
	 * @方法说明:启动到指定activity，Bundle传递对象（作用于2个界面之间传递数据）
	 * @方法名称:openActivity
	 * @param pClass
	 * @param pBundle
	 * @返回值:void
	 */
	public void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this.getContext(), pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	/**
	 * @方法说明:根据界面name启动到指定界面
	 * @方法名称:openActivity
	 * @param pAction
	 * @返回值:void
	 */
	public void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	/**
	 * @方法说明:根据界面name启动到指定界面，Bundle传递对象（作用于2个界面之间传递数据）
	 * @方法名称:openActivity
	 * @param pAction
	 * @param pBundle
	 * @返回值:void
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
	 * @返回值:void
	 */
	public void openActivityResult(Class<?> pClass) {
		openActivityResult(pClass, null);
	}

	/**
	 * @方法说明:A-Activity需要在B-Activtiy中执行一些数据操作， 
	 *                                         而B-Activity又要将，执行操作数据的结果返回给A-Activity
	 *                                         ， Bundle传递对象（作用于2个界面之间传递数据）
	 * @方法名称:openActivityResult
	 * @param pClass
	 * @param pBundle
	 * @返回值:void
	 */
	public void openActivityResult(Class<?> pClass, Bundle pBundle) {
		openActivityResult(pClass, pBundle, 0);
	}

	/**
	 * @方法说明:A-Activity需要在B-Activtiy中执行一些数据操作， 
	 *                                         而B-Activity又要将，执行操作数据的结果返回给A-Activity
	 *                                         ， Bundle传递对象（作用于2个界面之间传递数据）
	 * @方法名称:openActivityResult
	 * @param pClass
	 * @param pBundle
	 * @param requestCode
	 * @返回值:void
	 */
	public void openActivityResult(Class<?> pClass, Bundle pBundle,
								   int requestCode) {
		Intent intent = new Intent(this.getContext(), pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivityForResult(intent, requestCode);
	}




}
