package com.kuaqu.reader.module_common_ui.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kuaqu.reader.module_common_ui.db.ThreadDAOImpl;
import com.kuaqu.reader.module_common_ui.response.FileInfo;
import com.kuaqu.reader.module_common_ui.response.ThreadInfo;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DownLoadTask {
    private Context mContext;
    private FileInfo mFileInfo;
    private ThreadDAOImpl dao;
    private int mFinished = 0;
    public boolean isPause = false;

    public DownLoadTask(Context context, FileInfo fileInfo) {
        mContext = context;
        mFileInfo = fileInfo;
        //创建数据库实例
        dao = new ThreadDAOImpl(context);
    }

    public void download() {
        List<ThreadInfo> threadInfos = dao.queryThread(mFileInfo.url);
        ThreadInfo threadInfo = null;
        if (threadInfos.size() == 0) {
            //如果数据库中没有就根据下载文件信息创建一个新的下载信息实例对象
            threadInfo = new ThreadInfo();
            threadInfo.id = 0;
            threadInfo.url = mFileInfo.url;
            threadInfo.thread_start = 0;
            threadInfo.thread_end = mFileInfo.length;
            threadInfo.finished = 0;
        } else {
            //获取数据库返回的下载信息实例对象
            threadInfo = threadInfos.get(0);
        }
        //创建子线程下载
        DownLoadThread loadThread = new DownLoadThread(threadInfo);
        loadThread.start();
    }

    class DownLoadThread extends Thread {
        ThreadInfo mThreadInfo;

        public DownLoadThread(ThreadInfo threadInfo) {
            this.mThreadInfo = threadInfo;
        }

        @Override
        public void run() {
            super.run();
            //向数据库中插入一条信息
            if (!dao.isExists(mThreadInfo.url, mThreadInfo.id)) {
                //插入一条新的下载记录信息
                dao.insertThread(mThreadInfo);
            }
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try {
                URL url = new URL(mThreadInfo.url);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                //设置下载位置
                int start = mThreadInfo.thread_start + mThreadInfo.finished;
                conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadInfo.thread_end);//注意
                //设置一个文件写入位置
                File file = new File(DownLoadService.DOWNLOAD_PATH, mFileInfo.fileName);
                raf = new RandomAccessFile(file, "rwd");
                //设置文件写入位置
                raf.seek(start);//注意
                Intent intent = new Intent(DownLoadService.ACTION_UPDATE);
                mFinished += mThreadInfo.finished;
                //开始下载了
                if (conn.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                    input = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len;
                    while ((len = input.read(buffer)) != -1) {
                        Log.e("字节",len+"");
                        raf.write(buffer, 0, len);
                        mFinished += len;
                        Log.e("DownLoadService",mFinished+"");
                        intent.putExtra("finished", mFinished * 100 / mFileInfo.length);
                        mContext.sendBroadcast(intent);
                        //下载暂停是要把进度保存在数据库中
                        if (isPause) {
                            //暂停时更新数据库中的下载信息
                            dao.updateThread(mThreadInfo.url, mThreadInfo.id, mFinished);
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.disconnect();
                    }
                    if (raf != null) {
                        raf.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
