package com.xcoder.lib.annotation.event;

import android.widget.AbsListView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
* 类名：OnScrollStateChanged
* 类描述：
* 作者：CJ
* 创建时间：2014-10-24-上午11:06:49
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
       listenerType = AbsListView.OnScrollListener.class,
       listenerSetter = "setOnScrollListener",
       methodName = "onScrollStateChanged")
public @interface OnScrollStateChanged {
   int[] value();

   int[] parentId() default 0;
}
