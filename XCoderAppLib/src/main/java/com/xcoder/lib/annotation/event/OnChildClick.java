package com.xcoder.lib.annotation.event;

import android.widget.ExpandableListView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
* 类名：OnChildClick
* 类描述：用当可折叠列表里的子元素(child)被点击的时候被调用的回调方法。
* 作者：CJ

*/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
       listenerType = ExpandableListView.OnChildClickListener.class,
       listenerSetter = "setOnChildClickListener",
       methodName = "onChildClick")
public @interface OnChildClick {
   int[] value();

   int[] parentId() default 0;
}
