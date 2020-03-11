package com.kuaqu.reader.module_main_mall.arouter;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kuaqu.reader.component_data.constant.RouterURLS;
import com.kuaqu.reader.module_main_mall.R;

/**
 * @Created by TOME .
 * @时间 2018/4/26 10:19
 * @描述 ${路由中心}
 */
//ARouter 提供了大量的参数类型 跳转携带 https://blog.csdn.net/zhaoyanjun6/article/details/76165252
public class RouterCenter {

    /**
     * 常用控件ui
     */
    public static void toCommonUI() {
        ARouter.getInstance().build(RouterURLS.COMMON_UI).withTransition(R.anim.my_alpha_action,R.anim.my_scale_action).navigation();
    }


    /**
     * 自定义控件集合
     */
    public static void toMyViewList() {
        ARouter.getInstance().build(RouterURLS.MY_VIEW).withString("key","传值").navigation();
//        ARouter.getInstance().build(RouterURLS.MY_VIEW).withString("key","传值").navigation(context,110);
    }

    /**
     * 界面特效ui
     */
    public static void toSpecialUI() {
        ARouter.getInstance().build(RouterURLS.SPECIAL_UI).navigation();
    }
    /**
     * materialDesign
     */
    public static void toMaterialDesign() {
        ARouter.getInstance().build(RouterURLS.MATERIAL_DESIGN).navigation();
    }

    public static void toTest() {
        ARouter.getInstance().build(RouterURLS.TEST_ACTIVITY).navigation();
    }

    /**
     * 常用类实例
     */
    public static void toCommonClass() {
        ARouter.getInstance().build(RouterURLS.COMMON_CLASS).navigation();
    }
}
