package com.kuaqu.reader.module_common_ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kuaqu.reader.module_common_ui.R;
import com.kuaqu.reader.module_common_ui.utils.AudioRecordManager;

import java.util.ArrayList;
import java.util.List;
//文件路径:/storage/emulated/0/DCIM/arm
public class AudioActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "AudioRecordActivity";
    private Button btn_start, btn_stop, btn_play, btn_onplay;
    private AudioRecordManager mManager;
    //申请权限列表
    private int REQUEST_CODE = 1001;
    private String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };
    //拒绝权限列表
    private List<String> refusePermissions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        AudioRecordManager.init();//初始化目录
        initView();
        checkPermission();
    }

    /**
     * 6.0以上要动态申请权限
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(this,
                        permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    refusePermissions.add(permissions[i]);
                }
            }
            if (!refusePermissions.isEmpty()) {
                String[] permissions = refusePermissions.toArray(new String[refusePermissions.size()]);
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
            }
        }

    }

    /**
     * 权限结果回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults != null) {
                for(int i = 0 ; i<permissions.length;i++){
                    if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                        Log.d(TAG,permissions[i]+"   被禁用");
                    }
                }
            }
        }
    }

    private void initView() {
        btn_start = findViewById(R.id.record_start);
        btn_stop = findViewById(R.id.record_stop);
        btn_play = findViewById(R.id.record_play);
        btn_onplay = findViewById(R.id.record_noplay);
        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        btn_onplay.setOnClickListener(this);
        //初始化
        mManager = AudioRecordManager.NewInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.record_start) {//录音
            mManager.startRecord();

        } else if (i == R.id.record_stop) {//停止
            mManager.stopRecord();

        } else if (i == R.id.record_play) {//播放
            mManager.playRecord();

        } else if (i == R.id.record_noplay) {//停止
            mManager.stopPlayRecord();

        } else {
        }
    }
}

