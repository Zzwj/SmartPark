 

package com.xcoder.lib.annotation.event;

import android.widget.AdapterView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
* 类名：OnItemLongClick
* 类描述：长按监听事件
* 作者：CJ
* 创建时间：2014-10-24-上午11:05:13
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
       listenerType = AdapterView.OnItemLongClickListener.class,
       listenerSetter = "setOnItemLongClickListener",
       methodName = "onItemLongClick")
public @interface OnItemLongClick {
   int[] value();

   int[] parentId() default 0;
}
