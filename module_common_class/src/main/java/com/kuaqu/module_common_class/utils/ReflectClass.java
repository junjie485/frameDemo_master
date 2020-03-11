package com.kuaqu.module_common_class.utils;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectClass {
    private final static String TAG = "peter.log.ReflectClass";

    // 创建对象
    public static void reflectNewInstance() {
        try {
            Class<?> bookclass = Class.forName("com.kuaqu.module_common_class.utils.Book");//返回类的对象
            Book book = (Book) bookclass.newInstance();//获取对象实例
            book.setName("android万岁");
            book.setAuthor("刘某某");
            Log.d(TAG, "reflectNewInstance book = " + book.toString());
            Log.d(TAG, "=== " + bookclass.getClass() + "-->" + bookclass.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 反射私有的构造方法
    public static void reflectPrivateConstructor() {
        try {
            Class<?> bookclass = Class.forName("com.kuaqu.module_common_class.utils.Book");
            Constructor<?> constructor = bookclass.getDeclaredConstructor(String.class, String.class);//获取私有构造器
            constructor.setAccessible(true);
            Book book = (Book) constructor.newInstance("android万岁", "刘某某");//根据传递的参数创建类的对象
            Log.d(TAG, "reflectPrivateConstructor book = " + book.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 反射私有属性
    public static void reflectPrivateField() {
        try {
            Class<?> bookclass = Class.forName("com.kuaqu.module_common_class.utils.Book");
            Book book = (Book) bookclass.newInstance();
            Field field = bookclass.getDeclaredField("TAG");//获得私有属性
            field.setAccessible(true);
            String tag = (String) field.get(book);//得到属性值
            Log.d(TAG, "reflectPrivateField tag = " + tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 反射私有方法
    public static void reflectPrivateMethod() {
        try {
            Class<?> bookclass = Class.forName("com.kuaqu.module_common_class.utils.Book");
            Book book = (Book) bookclass.newInstance();
            Method method = bookclass.getDeclaredMethod("declaredMethod", int.class);//获取私有方法
            method.setAccessible(true);
            String tag= (String) method.invoke(book,0);//获取方法返回值
            Log.d(TAG,"reflectPrivateMethod string = " + tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获得系统Zenmode值
    public static int getZenMode() {
        int zenMode = -1;
        try {
            Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
            Method mGetService = cServiceManager.getMethod("getService", String.class);
            Object oNotificationManagerService = mGetService.invoke(null, Context.NOTIFICATION_SERVICE);
            Class<?> cINotificationManagerStub = Class.forName("android.app.INotificationManager$Stub");
            Method mAsInterface = cINotificationManagerStub.getMethod("asInterface", IBinder.class);
            Object oINotificationManager = mAsInterface.invoke(null,oNotificationManagerService);
            Method mGetZenMode = cINotificationManagerStub.getMethod("getZenMode");
            zenMode = (int) mGetZenMode.invoke(oINotificationManager);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return zenMode;
    }

    // 关闭手机
    public static void shutDown() {
        try {
            Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
            Method mGetService = cServiceManager.getMethod("getService",String.class);
            Object oPowerManagerService = mGetService.invoke(null,Context.POWER_SERVICE);
            Class<?> cIPowerManagerStub = Class.forName("android.os.IPowerManager$Stub");
            Method mShutdown = cIPowerManagerStub.getMethod("shutdown",boolean.class,String.class,boolean.class);
            Method mAsInterface = cIPowerManagerStub.getMethod("asInterface",IBinder.class);
            Object oIPowerManager = mAsInterface.invoke(null,oPowerManagerService);
            mShutdown.invoke(oIPowerManager,true,null,true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void shutdownOrReboot(final boolean shutdown, final boolean confirm) {
        try {
            Class<?> ServiceManager = Class.forName("android.os.ServiceManager");
            // 获得ServiceManager的getService方法
            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);
            // 调用getService获取RemoteService
            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);
            // 获得IPowerManager.Stub类
            Class<?> cStub = Class.forName("android.os.IPowerManager$Stub");
            // 获得asInterface方法
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            // 调用asInterface方法获取IPowerManager对象
            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
            if (shutdown) {
                // 获得shutdown()方法
                Method shutdownMethod = oIPowerManager.getClass().getMethod(
                        "shutdown", boolean.class, String.class, boolean.class);
                // 调用shutdown()方法
                shutdownMethod.invoke(oIPowerManager, confirm, null, false);
            } else {
                // 获得reboot()方法
                Method rebootMethod = oIPowerManager.getClass().getMethod("reboot",
                        boolean.class, String.class, boolean.class);
                // 调用reboot()方法
                rebootMethod.invoke(oIPowerManager, confirm, null, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
