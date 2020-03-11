package com.kuaqu.reader.module_common_ui.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_common_ui.R;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class VideoActivity extends BaseActivity {
    private SurfaceView sfv;//能够播放图像的控件
    private SeekBar sb;//进度条
    private String path ;//本地文件路径
    private SurfaceHolder holder;
    private MediaPlayer player;//媒体播放器
    private Button Play;//播放按钮
    private Timer timer;//定时器
    private TimerTask task;//定时器任务
    private int position = 0;
    private boolean isPause;
    private VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();

    }

    private void initView() {
        sfv = (SurfaceView) findViewById(R.id.sfv);
        sb = (SeekBar) findViewById(R.id.sb);
        Play = (Button) findViewById(R.id.play);
        Play.setEnabled(false);
        holder=sfv.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (player != null) {
                    player.seekTo(seekBar.getProgress());
                }
            }
        });
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //为了避免图像控件还没有创建成功，用户就开始播放视频，造成程序异常，所以在创建成功后才使播放按钮可点击
                Log.d("zhangdi","surfaceCreated");
                Play.setEnabled(true);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d("zhangdi","surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //当程序没有退出，但不在前台运行时，因为surfaceview很耗费空间，所以会自动销毁，
                // 这样就会出现当你再次点击进程序的时候点击播放按钮，声音继续播放，却没有图像
                //为了避免这种不友好的问题，简单的解决方式就是只要surfaceview销毁，我就把媒体播放器等
                //都销毁掉，这样每次进来都会重新播放，当然更好的做法是在这里再记录一下当前的播放位置，
                //每次点击进来的时候把位置赋给媒体播放器，很简单加个全局变量就行了。
                Log.d("zhangdi","surfaceDestroyed");
                if (player != null) {
                    position = player.getCurrentPosition();
                    stop();
                }
            }
        });
        //第二种视频播放方式
        video = (VideoView) findViewById(R.id.video);
        String url=Environment.getExternalStorageDirectory().getPath()+"/DCIM/bb.mp4";
      /*  Uri uri =Uri.parse(url);
        video.setVideoURI(uri);*/
        video.setVideoPath(url);
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                video.start();
            }
        });
    }
    public void play(View view){
        play();
    }
    public void pause(View view){
        pause();
    }
    public void stop(View view){
        stop();
    }
    private void play(){
        Play.setEnabled(false);
        if(isPause){
            isPause=false;
            player.start();
            return;
        }
        path = Environment.getExternalStorageDirectory().getPath()+"/DCIM/aa.mp4";
        File file = new File(path);
        if (!file.exists()) {//判断需要播放的文件路径是否存在，不存在退出播放流程
            Toast.makeText(this,"文件路径不存在",Toast.LENGTH_LONG).show();
            return;
        }

        try {
            player = new MediaPlayer();
            player.setDataSource(path);
            player.setDisplay(holder);//将影像播放控件与媒体播放控件关联起来

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {//视频播放完成后，释放资源
                    Play.setEnabled(true);
                    stop();
                }
            });

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //媒体播放器就绪后，设置进度条总长度，开启计时器不断更新进度条，播放视频
                    Log.d("zhangdi","onPrepared");
                    sb.setMax(player.getDuration());//设置视频总长度
                    timer = new Timer();
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            if (player != null) {
                                int time = player.getCurrentPosition();
                                sb.setProgress(time);
                            }
                        }
                    };
                    timer.schedule(task,0,500);
                    sb.setProgress(position);
                    player.seekTo(position);
                    player.start();
                }
            });

            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void stop(){
        isPause = false;
        if (player != null) {
            sb.setProgress(0);
            player.stop();
            player.release();
            player = null;
            if (timer != null) {
                timer.cancel();
            }
            Play.setEnabled(true);
        }

    }

    private void pause() {
        if (player != null && player.isPlaying()) {
            player.pause();
            isPause = true;
            Play.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }
}
