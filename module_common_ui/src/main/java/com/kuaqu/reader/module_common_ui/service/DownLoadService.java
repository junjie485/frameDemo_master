package com.kuaqu.reader.module_common_ui.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.kuaqu.reader.module_common_ui.response.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.http.Url;

public class DownLoadService extends Service {
    private static final String TAG = "DownLoadService";
    //开始下载
    public static final String ACTION_START = "ACTION_START";
    //暂停下载
    public static final String ACTION_STOP = "ACTION_STOP";
    //更新进度
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/downloads";
    private static final int MSG_INIT = 0;
    private DownLoadTask task;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FileInfo fileInfo= (FileInfo) intent.getSerializableExtra("fileInfo");
        if(ACTION_START.equals(intent.getAction())){
            //开启线程，获取文件长度
            InitThread initThread=new InitThread(fileInfo);
            initThread.start();
        }else if(ACTION_STOP.equals(intent.getAction())){
            if (task != null) {
                //暂停下载
                task.isPause = true;
            }
        }


        return super.onStartCommand(intent, flags, startId);
    }
    class InitThread extends Thread{
        private FileInfo mFileInfo;

        public InitThread(FileInfo fileInfo) {
            this.mFileInfo = fileInfo;
        }
        @Override
        public void run() {
            super.run();
            int length=-1;
            RandomAccessFile faf = null;
            HttpURLConnection conn=null;
            try {
                conn= (HttpURLConnection) new URL(mFileInfo.url).openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                    length=conn.getContentLength();
                }
                if(length<0){
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);//下载路径
                if (!dir.exists()) {
                    dir.mkdir();
                }
                //在本地创建文件
                File file = new File(dir, mFileInfo.fileName);
                faf = new RandomAccessFile(file, "rwd");//表示可以 r：读 w:写 d:删除
                faf.setLength(length);
                mFileInfo.length=length;
                Message message = handler.obtainMessage(MSG_INIT, mFileInfo);
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (conn != null) {
                        conn.disconnect();
                    }
                    if (faf != null) {
                        faf.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == MSG_INIT){
                FileInfo fileInfo= (FileInfo) msg.obj;
                //开启下载任务
                task=new DownLoadTask(DownLoadService.this,fileInfo);
                task.download();
            }
        }
    };
}
