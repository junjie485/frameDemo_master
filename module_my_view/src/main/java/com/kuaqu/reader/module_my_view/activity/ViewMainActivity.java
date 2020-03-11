package com.kuaqu.reader.module_my_view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_data.constant.RouterURLS;
import com.kuaqu.reader.module_my_view.R;
@Route(path = RouterURLS.MY_VIEW)
public class ViewMainActivity extends BaseActivity implements View.OnClickListener{
    private Button foldText,paintBtn,myViewBtn,besselBtn,fermodeBtn,timerBtn,pickerBtn;
    private Button slantedBtn,bitmapBtn,pBarBtn,animBtn,stereBtn,scaleBtn;
    private TextView title;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_main);
        initView();
    }

    private void initView() {
        title=findViewById(R.id.title);
        foldText=findViewById(R.id.foldText);
        paintBtn=findViewById(R.id.paintBtn);
        myViewBtn=findViewById(R.id.myViewBtn);
        besselBtn=findViewById(R.id.besselBtn);
        fermodeBtn=findViewById(R.id.fermodeBtn);
        timerBtn=findViewById(R.id.timerBtn);
        pickerBtn=findViewById(R.id.pickerBtn);
        slantedBtn=findViewById(R.id.slantedBtn);
        bitmapBtn=findViewById(R.id.bitmapBtn);
        pBarBtn=findViewById(R.id.pBarBtn);
        animBtn=findViewById(R.id.animBtn);
        stereBtn=findViewById(R.id.stereBtn);
        scaleBtn=findViewById(R.id.scaleBtn);
        pBarBtn.setOnClickListener(this);
        bitmapBtn.setOnClickListener(this);
        timerBtn.setOnClickListener(this);
        foldText.setOnClickListener(this);
        paintBtn.setOnClickListener(this);
        myViewBtn.setOnClickListener(this);
        besselBtn.setOnClickListener(this);
        fermodeBtn.setOnClickListener(this);
        pickerBtn.setOnClickListener(this);
        slantedBtn.setOnClickListener(this);
        animBtn.setOnClickListener(this);
        stereBtn.setOnClickListener(this);
        scaleBtn.setOnClickListener(this);
        title.setText("自定义控件大全");
        String dd=getIntent().getStringExtra("key");
        Toast.makeText(this, dd, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        int i=view.getId();
        if(i==R.id.foldText){
            intent=new Intent(this,FoldTextActivity.class);
            startActivity(intent);
        }else if(i==R.id.paintBtn){
            startActivity(new Intent(this,PaintActivity.class));
        }else if(i==R.id.myViewBtn){
            startActivity(new Intent(this,CustomViewActivity.class));
        }else if(i==R.id.besselBtn){
            startActivity(new Intent(this,BesselActivity.class));
        }else if(i==R.id.fermodeBtn){
            startActivity(new Intent(this,XFermodeActivity.class));
        }else if(i==R.id.timerBtn){
            startActivity(new Intent(this,TimerActivity.class));
        }else if(i==R.id.pickerBtn){
            startActivity(new Intent(this,ClockActivity.class));
        }else if(i==R.id.slantedBtn){
            startActivity(new Intent(this, SlantTvActivity.class));
        }else if(i==R.id.bitmapBtn){
            startActivity(new Intent(this, BitmapShaderActivity.class));
        }else if(i==R.id.pBarBtn){
            startActivity(new Intent(this, ProgressBarActivity.class));
        }else if(i==R.id.animBtn){
            startActivity(new Intent(this, AnimationActivity.class));
        }else if(i==R.id.stereBtn){
//            startActivity(new Intent(this, StereActivity.class));
            startActivity(new Intent(this, TestActivity.class));
        }else if(i==R.id.scaleBtn){
            startActivity(new Intent(this, PictureScaleActivity.class));
        }

    }
    public void onBack(View view){
        finish();
    }
}
