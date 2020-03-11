package com.kuaqu.module_common_class.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView
{
    int value();
}
//@Target表示该注解可以用于什么地方，可能的类型TYPE（类）,FIELD（成员变量）
//@Retention表示：表示需要在什么级别保存该注解信息；我们这里设置为运行时。