package com.kuaqu.reader.module_common_ui.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.Random;

public class MusicService extends Service implements AudioManager.OnAudioFocusChangeListener, MediaPlayer.OnCompletionListener {
    public MediaPlayer mMediaPlayer = new MediaPlayer();
    private static final String TAG = "MediaService";
    private AudioManager audioManager;
    //标记当前歌曲的序号
    private int currentPosition = 0;
    //歌曲路径
    private String[] musicPath = new String[]{
            Environment.getExternalStorageDirectory() + "/Sounds/a1.mp3",
            Environment.getExternalStorageDirectory() + "/Sounds/a2.mp3",
            Environment.getExternalStorageDirectory() + "/Sounds/a3.mp3",
    };
    private Random random = new Random();
    //播放模式
    public static final int ORDER_PLAY = 1;//顺序播放
    public static final int RANDOM_PLAY = 2;//随机播放
    public static final int SINGLE_PLAY = 3;//单曲循环
    private int play_mode = ORDER_PLAY;//播放模式,默认为顺序播放

    //set方法
    public void setPlay_mode(int play_mode) {
        this.play_mode = play_mode;
    }

    //get方法
    public int getPlay_mode() {
        return play_mode;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        iniMediaPlayerFile(currentPosition);
        mMediaPlayer.setOnCompletionListener(this);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        switch (play_mode) {
            case ORDER_PLAY://顺序播放
                if (!(currentPosition == musicPath.length - 1)) {
                    nextMusic();//下一首
                } else {
                    Log.e(TAG, "已播放完毕");
                }

                break;
            case RANDOM_PLAY://随机播放
                //生成[0,musicPath.length)的随机数
                iniMediaPlayerFile(random.nextInt(musicPath.length));
                playMusic();
                break;
            case SINGLE_PLAY://单曲循环
                Log.e(TAG, "--->" + currentPosition);
                iniMediaPlayerFile(currentPosition);
                playMusic();
                break;
            default:
                break;
        }
    }


    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public void playMusic() {
        if (!mMediaPlayer.isPlaying()) {
            //如果还没开始播放，就开始
            mMediaPlayer.start();
        }
    }

    /**
     * 暂停播放
     */
    public void pauseMusic() {
        if (mMediaPlayer.isPlaying()) {
            //如果还没开始播放，就开始
            mMediaPlayer.pause();
        }
    }

    /**
     * 下一首
     */
    public void nextMusic() {
        if (mMediaPlayer != null && currentPosition < musicPath.length && currentPosition >= 0) {
            //这里的if只要是为了不让歌曲的序号越界
            if (!(currentPosition == musicPath.length - 1)) {
                currentPosition = currentPosition + 1;
            }
            iniMediaPlayerFile(currentPosition);
            playMusic();
            Log.e("焦点","下一首");
            audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN);
        }
    }

    /**
     * 上一首
     */
    public void preciousMusic() {
        if (mMediaPlayer != null && currentPosition < musicPath.length && currentPosition > 0) {
            //这里的if只要是为了不让歌曲的序号越界
            if (!(currentPosition == 0)) {
                currentPosition = currentPosition - 1;
            }
            iniMediaPlayerFile(currentPosition);
            playMusic();
        }
    }


    /**
     * 关闭播放器
     */
    public void closeMedia() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    /**
     * 获取歌曲长度
     **/
    public int getProgress() {

        return mMediaPlayer.getDuration();
    }

    /**
     * 获取播放位置
     */
    public int getPlayPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * 播放指定位置
     */
    public void seekToPositon(int msec) {
        mMediaPlayer.seekTo(msec);
    }


    private void iniMediaPlayerFile(int dex) {
        //获取文件路径
        try {
            //此处的两个方法需要捕获IO异常
            //设置音频文件到MediaPlayer对象中
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(musicPath[dex]);
            //让MediaPlayer对象准备
            mMediaPlayer.prepare();
        } catch (IOException e) {
            Log.d(TAG, "设置资源，准备阶段出错");
            e.printStackTrace();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "解除绑定");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "销毁");
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_LOSS:
                Log.e("焦点", "Loss");
                audioManager.abandonAudioFocus(this);
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                Log.e("焦点", "LOSS_TRANSIENT");
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                Log.e("焦点", "Duck");
                if (mMediaPlayer.isPlaying()) {
                    //短暂失去焦点，先暂停。同时将标志位置成重新获得焦点后就开始播放
                    mMediaPlayer.setVolume(0.3f, 0.3f);
//                    mMediaPlayer.pause();
                }
                break;
            case AudioManager.AUDIOFOCUS_GAIN:
                Log.e("焦点", "Gain");
                //重新获得焦点，且符合播放条件，开始播放
                mMediaPlayer.setVolume(1.0f, 1.0f);
                break;
        }
    }
}

