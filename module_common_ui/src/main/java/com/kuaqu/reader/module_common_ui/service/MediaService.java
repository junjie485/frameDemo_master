package com.kuaqu.reader.module_common_ui.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.Random;

public class MediaService extends Service implements MediaPlayer.OnCompletionListener{
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private static final String TAG = "MediaService";
    private Messenger messenger;
    //标记当前歌曲的序号
    private int currentPosition = 0;
    private Handler mHandler = new Handler();
    //歌曲路径
    private String[] musicPath = new String[]{
            Environment.getExternalStorageDirectory() + "/Sounds/a1.mp3",
            Environment.getExternalStorageDirectory() + "/Sounds/a2.mp3",
            Environment.getExternalStorageDirectory() + "/Sounds/a3.mp3",
    };
    private Random random = new Random();
    //播放模式
    public static final int ORDER_PLAY2 = 1;//顺序播放
    public static final int RANDOM_PLAY2 = 2;//随机播放
    public static final int SINGLE_PLAY2 = 3;//单曲循环
    private static int play_mode = ORDER_PLAY2;//播放模式,默认为顺序播放
    //set方法
    public void setPlay_mode(int play_mode) {
        this.play_mode = play_mode;
    }

    //get方法
    public int getPlay_mode() {
        return play_mode;
    }
    public MediaService getService(){
        return MediaService.this;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        iniMediaPlayerFile(currentPosition);
        mediaPlayer.setOnCompletionListener(this);
        mHandler.post(mRunnable);
    }
    private void iniMediaPlayerFile(int dex) {
        //获取文件路径
        try {
            //此处的两个方法需要捕获IO异常
            //设置音频文件到MediaPlayer对象中
            mediaPlayer.reset();
            mediaPlayer.setDataSource(musicPath[dex]);
            //让MediaPlayer对象准备
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.d(TAG, "设置资源，准备阶段出错");
            e.printStackTrace();
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String option = intent.getStringExtra("option");
        if (messenger == null) {
            messenger = (Messenger) intent.getExtras().get("messenger");
        }
        if ("开始".equals(option)) {
            playMusic();
        } else if ("上一首".equals(option)) {
            preciousMusic();
        } else if ("下一首".equals(option)) {
            nextMusic();
        } else if ("停止".equals(option)) {
            stop();
        } else if ("跳转".equals(option)) {
            seekPlay(intent.getIntExtra("progress", -1));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
    public class MyBinder extends Binder {
        public MediaService getService(){
            return MediaService.this;
        }
    }
    public void playMusic() {
        if (!mediaPlayer.isPlaying()) {
            //如果还没开始播放，就开始
            mediaPlayer.start();
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * 下一首
     */
    public void nextMusic() {
        if (mediaPlayer != null && currentPosition < musicPath.length && currentPosition >= 0) {
            //这里的if只要是为了不让歌曲的序号越界
            if (!(currentPosition == musicPath.length-1)) {
                currentPosition = currentPosition + 1;
            }
            iniMediaPlayerFile(currentPosition);
            playMusic();
        }
    }

    /**
     * 上一首
     */
    public void preciousMusic() {
        if (mediaPlayer != null && currentPosition < musicPath.length && currentPosition > 0) {
            //这里的if只要是为了不让歌曲的序号越界
            if (!(currentPosition ==0)){
                currentPosition = currentPosition - 1;
            }
            iniMediaPlayerFile(currentPosition);
            playMusic();
        }
    }

    //停止播放
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public void seekPlay(int progress) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(progress);
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Message message = Message.obtain();
                message.arg1 = mediaPlayer.getCurrentPosition();
                message.arg2 = mediaPlayer.getDuration();
                message.what = 22;
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mHandler.postDelayed(mRunnable, 1000);
        }
    };

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.e(TAG,"play_mode:"+play_mode);
        switch (play_mode) {
            case ORDER_PLAY2://顺序播放
                Log.e(TAG,"顺序播放");
                if(!(currentPosition==musicPath.length-1)){
                    nextMusic();//下一首
                }else {
                    Log.e(TAG,"已播放完毕");
                }
                break;
            case RANDOM_PLAY2://随机播放
                Log.e(TAG,"随机播放");
                //生成[0,musicPath.length)的随机数
                iniMediaPlayerFile(random.nextInt(musicPath.length));
                playMusic();
                break;
            case SINGLE_PLAY2://单曲循环
                Log.e(TAG,"单曲循环");
                Log.e(TAG,"--->"+currentPosition);
                iniMediaPlayerFile(currentPosition);
                playMusic();
                break;
            default:
                break;
        }
    }
}
