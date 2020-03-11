package com.kuaqu.reader.module_common_ui.utils;

import java.util.ArrayList;
import java.util.List;
/*
* 观察者模式：主要是一对多的关系。一个主题对应多个对象，将观察者（对象）与被观察者（主题）完全解耦。
* 两者之间的联系都是通过接口来实现
*例子：订阅系统：用户订阅了相关主题内容（公总号），公众号通过发送消息给订阅的所有用户
* */
public class ObjectFor3D implements Subject {

    private List<Observer> observers = new ArrayList<Observer>();
    private String msg;
    @Override
    public void registerObserver(Observer observer) {
            observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int index = observers.indexOf(observer);
        if(index>=0){
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObserver() {
            for(Observer observer:observers){
                observer.update(msg);
            }
    }
    public void setMsg(String msg){
        this.msg=msg;
    }
}
