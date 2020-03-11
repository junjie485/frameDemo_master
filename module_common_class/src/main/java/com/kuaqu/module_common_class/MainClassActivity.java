package com.kuaqu.module_common_class;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_data.constant.RouterURLS;

@Route(path = RouterURLS.COMMON_CLASS)
public class MainClassActivity extends BaseActivity implements View.OnClickListener{
    private Button lrucache_btn,rv_btn,scroll_fold_btn,item_help_btn,kotlin_btn,bluetooth_btn,vf_btn,nest_btn,timer_btn,java_btn,handle_btn,service_btn,wifi_btn;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_main);
        lrucache_btn=findViewById(R.id.lrucache_btn);
        scroll_fold_btn=findViewById(R.id.scroll_fold_btn);
        item_help_btn=findViewById(R.id.item_help_btn);
        kotlin_btn=findViewById(R.id.kotlin_btn);
        bluetooth_btn=findViewById(R.id.bluetooth_btn);
        handle_btn=findViewById(R.id.handle_btn);
        service_btn=findViewById(R.id.service_btn);
        vf_btn=findViewById(R.id.vf_btn);
        rv_btn=findViewById(R.id.rv_btn);
        nest_btn=findViewById(R.id.nest_btn);
        timer_btn=findViewById(R.id.timer_btn);
        java_btn=findViewById(R.id.java_btn);
        wifi_btn=findViewById(R.id.wifi_btn);
        nest_btn.setOnClickListener(this);
        lrucache_btn.setOnClickListener(this);
        rv_btn.setOnClickListener(this);
        scroll_fold_btn.setOnClickListener(this);
        item_help_btn.setOnClickListener(this);
        kotlin_btn.setOnClickListener(this);
        bluetooth_btn.setOnClickListener(this);
        vf_btn.setOnClickListener(this);
        timer_btn.setOnClickListener(this);
        java_btn.setOnClickListener(this);
        handle_btn.setOnClickListener(this);
        service_btn.setOnClickListener(this);
        wifi_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i=view.getId();
        if(i==R.id.lrucache_btn){
            intent=new Intent(this,LrucacheActivity.class);
            startActivity(intent);
        }else  if(i==R.id.rv_btn){
            intent=new Intent(this,RecyclerActivity.class);
            startActivity(intent);
        }else  if(i==R.id.rv_btn){
            intent=new Intent(this,ScrollFoldActivity.class);
            startActivity(intent);
        }else  if(i==R.id.item_help_btn){
            intent=new Intent(this,ItemHelperActivity.class);
            startActivity(intent);
        }else if(i==R.id.kotlin_btn){
            intent=new Intent(this,KotlinActivity.class);
            startActivity(intent);
        }else if(i==R.id.bluetooth_btn){
            intent=new Intent(this,BlueToothActivity.class);
            startActivity(intent);
        }else if(i==R.id.vf_btn){
            intent=new Intent(this,ViewFlipperActivity.class);
            startActivity(intent);
        }else if(i==R.id.nest_btn){
            intent=new Intent(this,NestScrollActivity.class);
            startActivity(intent);
        }else if(i==R.id.timer_btn){
            intent=new Intent(this, TimersDemoActivity.class);
            startActivity(intent);
//            NotifyMethod();
        }else if(i==R.id.java_btn){
            intent=new Intent(this, JavaBaseActivity.class);
            startActivity(intent);
        }else if(i==R.id.handle_btn){
            intent=new Intent(this, HandleThreadActivity.class);
            startActivity(intent);
        }else if(i==R.id.service_btn){
            intent=new Intent(this, MyIntentServiceActivity.class);
            startActivity(intent);
        }else if(i==R.id.wifi_btn){
            intent=new Intent(this, WifiActivity.class);
            startActivity(intent);
        }
    }

    public void NotifyMethod(){
        PendingIntent pendingIntent= (PendingIntent) PendingIntent.getService(this,0,new Intent(this,MainClassActivity.class),0);
        NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("这是内容文本")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round))
                .setContentTitle("这是标题")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setShowWhen(true)//设置显示通知时间
                .build();
        manager.notify(0,builder.build());

    }

}
