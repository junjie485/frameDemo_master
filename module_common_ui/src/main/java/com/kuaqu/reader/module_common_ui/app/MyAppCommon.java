package com.kuaqu.reader.module_common_ui.app;

import com.kuaqu.reader.component_base.base.BaseApplication;
import com.kuaqu.reader.component_base.utils.Density;

public class MyAppCommon extends BaseApplication{
    public static MyAppCommon app;
    public static MyAppCommon getInstance() {
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
//        Density.setDensity(MyAppCommon.getAppContext(),360f);
    }
}
