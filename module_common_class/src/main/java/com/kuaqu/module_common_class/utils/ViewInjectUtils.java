package com.kuaqu.module_common_class.utils;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ViewInjectUtils {
    private static final String METHOD_SET_CONTENTVIEW = "setContentView";
    private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";

    public static void inject(Activity activity) {

        injectContentView(activity);
        injectViews(activity);

    }

    private static void injectViews(Activity activity) {
        Class<? extends Activity> classzz = activity.getClass();
        Field[] fields = classzz.getDeclaredFields();
        for (Field field : fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int viewId = viewInject.value();
                if (viewId != -1) {
                    try {
                        Method method = classzz.getMethod(METHOD_FIND_VIEW_BY_ID, int.class);
                        Object o = method.invoke(activity, viewId);
                        field.setAccessible(true);
                        field.set(activity, o);
                    } catch (Exception e) {

                    }
                }
            }

        }
    }

    private static void injectContentView(Activity activity) {
        Class<? extends Activity> classzz = activity.getClass();
        // 查询类上是否存在ContentView注解
        ContentView contentView = classzz.getAnnotation(ContentView.class);
        if(contentView!=null){
            int contentViewLayoutId = contentView.value();
            try
            {
                Method method = classzz.getMethod(METHOD_SET_CONTENTVIEW, int.class);
                method.setAccessible(true);
                method.invoke(activity, contentViewLayoutId);
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }

    }

}
