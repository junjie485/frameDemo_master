package com.kuaqu.reader.module_common_ui.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class TestService extends IntentService {
    private final String TAG = "hehe";

    public TestService() {
        super("TestService");
    }

    //核心方法，执行耗时操作,并且只会创建一个工作线程，串行执行
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG,"onHandleIntent");
        String action = intent.getStringExtra("param");
        if(action.equals("s1"))Log.i(TAG,"启动service1");
        else if(action.equals("s2"))Log.i(TAG,"启动service2");
        else if(action.equals("s3"))Log.i(TAG,"启动service3");

        //让服务休眠2秒
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){e.printStackTrace();}
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind");
        return super.onBind(intent);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
        Log.e(TAG,"setIntentRedelivery");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }
}
