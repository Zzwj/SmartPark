
package com.xcoder.lib.annotation.event;

import android.widget.TabHost;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
* 类名：OnTabChange
* 作者：CJ
* 创建时间：2014-10-24-上午11:07:20
* 修改记录：
* 修改人　　		修改时间　　		版本		描述
*----------------------------------------------------------
*
*
*----------------------------------------------------------
* Copyright (c)-2014烈焰鸟网络科技有限公司
*/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
       listenerType = TabHost.OnTabChangeListener.class,
       listenerSetter = "setOnTabChangeListener",
       methodName = "onTabChange")
public @interface OnTabChange {
   int[] value();

   int[] parentId() default 0;
}
