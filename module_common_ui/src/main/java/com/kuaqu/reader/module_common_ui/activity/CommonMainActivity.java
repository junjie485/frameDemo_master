package com.kuaqu.reader.module_common_ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_data.constant.RouterURLS;
import com.kuaqu.reader.module_common_ui.R;

import butterknife.ButterKnife;

@Route(path = RouterURLS.COMMON_UI)
public class CommonMainActivity extends BaseActivity implements View.OnClickListener {
    private Button progressBar, serviceBtn, textStyleBtn, perBtn, adapterBtn,densityBtn,cameraBtn,statusBtn,treeBtn;
    private Button videoBtn,imgBtn,statusBarBtn,audioBtn,combineBtn,recordVideoBtn,webViewBtn,downLoadBtn,compressBtn;
    private TextView title;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title = findViewById(R.id.title);
        progressBar = findViewById(R.id.progressBar);
        serviceBtn = findViewById(R.id.serviceBtn);
        textStyleBtn = findViewById(R.id.textStyleBtn);
        adapterBtn = findViewById(R.id.adapterBtn);
        perBtn = findViewById(R.id.perBtn);
        densityBtn=findViewById(R.id.densityBtn);
        videoBtn=findViewById(R.id.videoBtn);
        imgBtn=findViewById(R.id.imgBtn);
        statusBarBtn=findViewById(R.id.statusBarBtn);
        audioBtn=findViewById(R.id.audioBtn);
        combineBtn=findViewById(R.id.combineBtn);
        recordVideoBtn=findViewById(R.id.recordVideoBtn);
        webViewBtn=findViewById(R.id.webViewBtn);
        downLoadBtn=findViewById(R.id.downLoadBtn);
        cameraBtn=findViewById(R.id.cameraBtn);
        statusBtn=findViewById(R.id.statusBtn);
        compressBtn=findViewById(R.id.compressBtn);
        treeBtn=findViewById(R.id.treeBtn);
        textStyleBtn.setOnClickListener(this);
        progressBar.setOnClickListener(this);
        serviceBtn.setOnClickListener(this);
        perBtn.setOnClickListener(this);
        adapterBtn.setOnClickListener(this);
        densityBtn.setOnClickListener(this);
        videoBtn.setOnClickListener(this);
        imgBtn.setOnClickListener(this);
        statusBarBtn.setOnClickListener(this);
        audioBtn.setOnClickListener(this);
        combineBtn.setOnClickListener(this);
        recordVideoBtn.setOnClickListener(this);
        webViewBtn.setOnClickListener(this);
        downLoadBtn.setOnClickListener(this);
        cameraBtn.setOnClickListener(this);
        statusBtn.setOnClickListener(this);
        compressBtn.setOnClickListener(this);
        treeBtn.setOnClickListener(this);
        title.setText("常用控件大全");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.progressBar) {
            intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.serviceBtn) {
            intent = new Intent(this, MusicActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.textStyleBtn) {
            intent = new Intent(this, TextStyleActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.perBtn) {
            intent = new Intent(this, ApplyPermisionActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.adapterBtn) {
            intent = new Intent(this, RecyclerViewActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.densityBtn){
            intent = new Intent(this, DensityActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.videoBtn){
            intent = new Intent(this, VideoActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.imgBtn){
            intent = new Intent(this, ImageActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.statusBarBtn){
//            intent = new Intent(this, StatusBarActivity.class);
//            startActivity(intent);
            intent=new Intent();
            intent.setAction("com.ljj.MY_ACTION");
            intent.addCategory("com.ljj.MY_CATEGORY");
            startActivity(intent);
        }else if(view.getId()==R.id.audioBtn){
            intent = new Intent(this, AudioActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.combineBtn){
            intent = new Intent(this, VideoPartActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.recordVideoBtn){
            intent = new Intent(this, RecordVideoActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.webViewBtn){
            intent = new Intent(this, WebViewActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.downLoadBtn){
            intent = new Intent(this, DownLoadActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.cameraBtn){
            intent = new Intent(this, CameraActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.statusBtn){
            intent = new Intent(this, StatusActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.compressBtn){
            intent = new Intent(this, PictureCompressActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.treeBtn){
            intent = new Intent(this, ListTreeActivity.class);
            startActivity(intent);
        }
    }



    public void onBack(View view) {
        finish();
    }

}
