package com.kuaqu.reader.module_main_mall.module;


import android.app.Application;

import com.kuaqu.reader.component_base.base.BaseApplication;


/**
 * @Created by TOME .
 * @时间 2018/4/26 10:26
 * @描述 ${模块路由初始化}
 */

public class MyApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //RouterConfig.init(this, true);
    }
}
