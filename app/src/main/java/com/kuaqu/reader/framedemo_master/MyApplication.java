package com.kuaqu.reader.framedemo_master;

import android.app.Application;

import com.kuaqu.reader.component_base.base.BaseApplication;

public class MyApplication extends BaseApplication {

    private static MyApplication myApplication;
    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this ;
    }
}
