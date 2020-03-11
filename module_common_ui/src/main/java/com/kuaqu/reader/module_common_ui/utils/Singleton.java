package com.kuaqu.reader.module_common_ui.utils;
/*
单例模式的几种写法
另外，尽量不要在单例的构造方法获取上下文，会造成内存泄漏
* */
public class Singleton {
    private static Singleton singleton = null;


    //第一种写法，线程不安全（懒汉）
    public static Singleton getInstance(){
        if(singleton==null){
            singleton=new Singleton();
        }else {
            return singleton;
        }
        return singleton;
    }
    //第二种写法,线程安全的写法（懒汉）
    public static Singleton getSingleton(){
        if(singleton==null){
            synchronized (Singleton.class){
                if(singleton==null){
                    singleton=new Singleton();
                }
            }
        }
        return singleton;
    }
  /*  //饿汉式
    private static Singleton instance = new Singleton();
    public static Singleton getInstance() {
        return instance;
    }*/
}
