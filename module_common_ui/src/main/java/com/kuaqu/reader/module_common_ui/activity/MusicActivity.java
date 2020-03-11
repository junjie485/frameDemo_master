package com.kuaqu.reader.module_common_ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.SoundPool;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_common_ui.R;
import com.kuaqu.reader.module_common_ui.service.MediaService;
import com.kuaqu.reader.module_common_ui.service.MusicService;
import com.kuaqu.reader.module_common_ui.service.TestService;

import java.text.SimpleDateFormat;

import static com.kuaqu.reader.module_common_ui.service.MediaService.ORDER_PLAY2;
import static com.kuaqu.reader.module_common_ui.service.MediaService.RANDOM_PLAY2;
import static com.kuaqu.reader.module_common_ui.service.MediaService.SINGLE_PLAY2;
import static com.kuaqu.reader.module_common_ui.service.MusicService.ORDER_PLAY;
import static com.kuaqu.reader.module_common_ui.service.MusicService.RANDOM_PLAY;
import static com.kuaqu.reader.module_common_ui.service.MusicService.SINGLE_PLAY;

public class MusicActivity extends BaseActivity implements View.OnClickListener{
    private Button BtnPlayorPause, BtnPre, BtnNext,play_mode,testBtn;
    private TextView MusicTime, MusicTotal;
    private SeekBar MusicSeekBar;
    private MusicService mMyBinder;
    Intent MediaServiceIntent;
    private static final String TAG = "MainActivity";
    private Handler mHandler = new Handler();
    private MediaService mediaService=new MediaService();
    private SimpleDateFormat time = new SimpleDateFormat("m:ss");
    private int type=1;//1.代表通过bindservice与activity通信 2.代表startservice与activity通信
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 22:
                    int curduration=msg.arg1;
                    int totalduration=msg.arg2;
                    MusicTime.setText(time.format(curduration)+"s");
                    MusicTotal.setText(time.format(totalduration)+"s");
                    MusicSeekBar.setMax(totalduration);
                    MusicSeekBar.setProgress(curduration);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();
        initListner();

    }
    private void initListner() {
        MusicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(type==1){
                    mMyBinder.seekToPositon(seekBar.getProgress());
                }else {
                    MusicSeekBar.setProgress(seekBar.getProgress());
                    startMusicService("跳转",seekBar.getProgress());
                }
            }
        });
    }

    private void initView() {
        BtnPlayorPause = findViewById(R.id.BtnPlayorPause);
        BtnPre = findViewById(R.id.BtnPre);
        BtnNext = findViewById(R.id.BtnNext);
        MusicTime = findViewById(R.id.MusicTime);
        MusicTotal = findViewById(R.id.MusicTotal);
        MusicSeekBar = findViewById(R.id.MusicSeekBar);
        testBtn=findViewById(R.id.testBtn);
        play_mode=findViewById(R.id.play_mode);
        BtnPlayorPause.setOnClickListener(this);
        BtnPre.setOnClickListener(this);
        BtnNext.setOnClickListener(this);
        play_mode.setOnClickListener(this);
        testBtn.setOnClickListener(this);
        if(type==1){
            MediaServiceIntent = new Intent(this, MusicService.class);
            //判断权限够不够，不够就给
            if (ContextCompat.checkSelfPermission(MusicActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MusicActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);
            } else {
                startService(MediaServiceIntent);//绑定之前，调用，可以防止acitivity退出后，service被销毁
                //够了就设置路径等，准备播放
                bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
            }
        }

    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = ((MusicService.MyBinder) service).getService();
            Log.d(TAG, "Service与Activity已连接");
            MusicSeekBar.setMax(mMyBinder.getProgress());
            mHandler.post(mRunnable);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.BtnPlayorPause) {
            Toast.makeText(this, "播放", Toast.LENGTH_SHORT).show();
          if(type==1){
              mMyBinder.playMusic();
          }else {
              startMusicService("开始");
          }

        } else if (i == R.id.BtnPre) {
            Toast.makeText(this, "上一首", Toast.LENGTH_SHORT).show();
            if(type==1){
                mMyBinder.preciousMusic();
            }else {
                startMusicService("上一首");
            }

        } else if (i == R.id.BtnNext) {
            Toast.makeText(this, "下一首", Toast.LENGTH_SHORT).show();
            if(type==1){
                mMyBinder.nextMusic();
            }else {
                startMusicService("下一首");
            }
        } else if (i == R.id.play_mode){
            if(type==1){
                switch (mMyBinder.getPlay_mode()){
                    case ORDER_PLAY:
                        mMyBinder.setPlay_mode(RANDOM_PLAY);
                        play_mode.setText("随机播放");
                        break;
                    case RANDOM_PLAY:
                        mMyBinder.setPlay_mode(SINGLE_PLAY);
                        play_mode.setText("单曲循环");
                        break;
                    case SINGLE_PLAY:
                        mMyBinder.setPlay_mode(ORDER_PLAY);
                        play_mode.setText("顺序播放");
                        break;
                }
            }else {
                switch (mediaService.getPlay_mode()){
                    case ORDER_PLAY2:
                        mediaService.setPlay_mode(RANDOM_PLAY2);
                        play_mode.setText("随机播放");
                        break;
                    case RANDOM_PLAY2:
                        mediaService.setPlay_mode(SINGLE_PLAY2);
                        play_mode.setText("单曲循环");
                        break;
                    case SINGLE_PLAY2:
                        mediaService.setPlay_mode(ORDER_PLAY2);
                        play_mode.setText("顺序播放");
                        break;
                }
            }

        }else if(i==R.id.testBtn){
            Toast.makeText(this, "执行测试", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, TestService.class);
            intent.putExtra("param","s1");
            startService(intent);
            Intent intent2=new Intent(this, TestService.class);
            intent2.putExtra("param","s2");
            startService(intent2);
            Intent intent3=new Intent(this, TestService.class);
            intent3.putExtra("param","s3");
            startService(intent3);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("aappp","返回");
        finish();
    }

    //获取到权限回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                } else {
                    Toast.makeText(this, "权限不够获取不到音乐，程序将退出", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            MusicSeekBar.setProgress(mMyBinder.getPlayPosition());
            MusicTotal.setText(time.format(mMyBinder.getProgress()) + "s");
            MusicTime.setText(time.format(mMyBinder.getPlayPosition()) + "s");
            mHandler.postDelayed(mRunnable, 1000);
        }
    };

    private void startMusicService(String option) {
        Intent intentService = new Intent(MusicActivity.this, MediaService.class);
        intentService.putExtra("option", option);
        intentService.putExtra("messenger", new Messenger(handler));
        startService(intentService);
    }

    private void startMusicService(String option, int progress) {
        Intent intentService = new Intent(MusicActivity.this, MediaService.class);
        intentService.putExtra("option", option);
        intentService.putExtra("progress", progress);
        intentService.putExtra("messenger", new Messenger(handler));//使用messenger跨进程通信，绑定一个handle
        startService(intentService);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    /*    mMyBinder.closeMedia();
        unbindService(mServiceConnection);*/
    }

    public void onSoundPool(View view) throws Exception {
        //设置最多可容纳5个音频流，音频的品质为5
        SoundPool soundPool=new SoundPool(5, AudioManager.STREAM_SYSTEM, 5);
//       int res=  soundPool.load(this,R.raw.duang,1);
       int res= soundPool.load(getAssets().openFd("biaobiao.mp3") ,1);
        //1,声音ID号 2.左声道 3.右声道 4.优先级 5.-1表示无限循环，0表示不循环 6.播放速率
        soundPool.play(res,1,1,0,0,1);
    }
}
