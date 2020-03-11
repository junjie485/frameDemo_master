package com.kuaqu.reader.module_common_ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_base.utils.PermissionListener;
import com.kuaqu.reader.component_base.utils.PermissionsUtil;
import com.kuaqu.reader.module_common_ui.R;
import com.kuaqu.reader.module_common_ui.db.ThreadDAOImpl;
import com.kuaqu.reader.module_common_ui.response.FileInfo;
import com.kuaqu.reader.module_common_ui.service.DownLoadService;
/*
* 流程：开启一个下载文件服务-->创建子线程获取文件长度--->要实现断点续传需要建立本地数据库保存路径和下载进度
* --->读取数据库是否有未完成的下载链接--->开始下载（RandomAccessFile与conn.setRequestProperty）确定下载开始位置
* --->读取字节流时，每循环一次通过广播发送进度--->暂停时保存进度到数据库。
* */
public class DownLoadActivity extends BaseActivity {
    private ProgressBar progress_bar;
    private TextView txt_result;
    private EditText edit_fname;
    private FileInfo fileInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        edit_fname=findViewById(R.id.edit_fname);
        txt_result=findViewById(R.id.txt_result);
        progress_bar=findViewById(R.id.progress_bar);
        progress_bar.setMax(100);
        String downLoadFileUrl =edit_fname.getText().toString();
        int indexOf = downLoadFileUrl.lastIndexOf("/");
        String charSequence = downLoadFileUrl.substring(indexOf + 1, downLoadFileUrl.length());
        fileInfo=new FileInfo();
        fileInfo.id=0;
        fileInfo.url=edit_fname.getText().toString();
        fileInfo.fileName=charSequence;
        fileInfo.length=0;
        fileInfo.finished=0;

        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownLoadService.ACTION_UPDATE);
        registerReceiver(mBroadcastReceiver, filter);

    }
    public void btnStart(View view){
        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                Intent intent=new Intent(DownLoadActivity.this, DownLoadService.class);
                intent.setAction(DownLoadService.ACTION_START);
                intent.putExtra("fileInfo",fileInfo);
                startService(intent);
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                Toast.makeText(DownLoadActivity.this, "请在设置中打开相关权限", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }
    public void btnStop(View view){
        Intent intent=new Intent(this, DownLoadService.class);
        intent.setAction(DownLoadService.ACTION_STOP);
        intent.putExtra("fileInfo",fileInfo);
        startService(intent);
    }
    /**
     * 更新ui的广播接收器
     */
    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == DownLoadService.ACTION_UPDATE) {
                int finished = intent.getIntExtra("finished", 0);
                //设置下载进度
                progress_bar.setProgress(finished);
                txt_result.setText(finished+"%");
                int progress = progress_bar.getProgress();
                if (progress == progress_bar.getMax()) {
                    Toast.makeText(DownLoadActivity.this, "下载完毕", Toast.LENGTH_LONG).show();

                }
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        unregisterReceiver(mBroadcastReceiver);
    }
}
