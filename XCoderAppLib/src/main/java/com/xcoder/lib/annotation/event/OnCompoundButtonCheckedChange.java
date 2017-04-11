
package com.xcoder.lib.annotation.event;

import android.widget.CompoundButton;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
* 类名：OnCompoundButtonCheckedChange
* 类描述：在按钮选中状态发生改变时被调用
* 作者：CJ
* 创建时间：2014-10-24-上午10:53:18
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
       listenerType = CompoundButton.OnCheckedChangeListener.class,
       listenerSetter = "setOnCheckedChangeListener",
       methodName = "onCheckedChanged")
public @interface OnCompoundButtonCheckedChange {
   int[] value();

   int[] parentId() default 0;
}
