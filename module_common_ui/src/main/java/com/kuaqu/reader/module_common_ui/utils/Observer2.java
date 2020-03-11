package com.kuaqu.reader.module_common_ui.utils;

import android.util.Log;

public class Observer2 implements Observer{

    public Observer2(Subject subject) {
        subject.registerObserver(this);
    }

    @Override
    public void update(String msg) {
        System.out.println("observer2 得到 3D 号码 -->" + msg );
        Log.e("data","observer2 得到 3D 号码 -->" + msg );
    }
}
