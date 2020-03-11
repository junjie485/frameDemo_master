package com.kuaqu.module_common_class.utils;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyIntentService extends IntentService {
    public static final String DOWNLOAD_URL="download_url";
    public static final String INDEX_FLAG="index_flag";
    public static UpdateUI updateUI;

    public interface  UpdateUI{
        void updateUI(Message message);
    }

    public static void setUpdateUI(UpdateUI updateUI) {
        MyIntentService.updateUI = updateUI;
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //在子线程中进行网络请求
        Bitmap bitmap=downloadUrlBitmap(intent.getStringExtra(DOWNLOAD_URL));
        Log.e("dsiapodoa","------"+intent.getStringExtra(DOWNLOAD_URL));
        Message msg1 = new Message();
        msg1.what = intent.getIntExtra(INDEX_FLAG,0);
        msg1.obj =bitmap;
        //通知主线程去更新UI
        if(updateUI!=null){
            Log.e("dsiapodoa","------");
            updateUI.updateUI(msg1);
        }

    }

    private Bitmap downloadUrlBitmap(String data) {
        BufferedInputStream in = null;
        Bitmap bitmap=null;
        try {
            URL  url=new URL(data);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(connection.getInputStream(), 8 * 1024);
            bitmap= BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }
}
