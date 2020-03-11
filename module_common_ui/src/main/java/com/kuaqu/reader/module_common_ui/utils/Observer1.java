package com.kuaqu.reader.module_common_ui.utils;

import android.util.Log;

public class Observer1 implements Observer{
    private Subject subject ;
    public Observer1(Subject subject) {
        this.subject=subject;
        subject.registerObserver(this);
    }

    @Override
    public void update(String msg) {
        Log.e("data","observer1 得到 3D 号码 -->" + msg);
    }
}
