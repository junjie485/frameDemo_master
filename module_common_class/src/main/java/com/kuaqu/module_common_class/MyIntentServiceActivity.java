package com.kuaqu.module_common_class;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.kuaqu.module_common_class.utils.MyIntentService;
import com.kuaqu.reader.component_base.base.BaseActivity;

public class MyIntentServiceActivity extends BaseActivity implements MyIntentService.UpdateUI {
    private ImageView imageView;
    private String url[] = {
            "https://img-blog.csdn.net/20160903083245762",
            "https://img-blog.csdn.net/20160903083252184",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083311972",
            "https://img-blog.csdn.net/20160903083319668",
            "https://img-blog.csdn.net/20160903083326871"
    };
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            imageView.setImageBitmap((Bitmap) msg.obj);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        imageView=findViewById(R.id.imageView);

        Intent intent=new Intent(this,MyIntentService.class);
        for(int i=0;i<url.length;i++){
            intent.putExtra(MyIntentService.DOWNLOAD_URL,url[i]);
            intent.putExtra(MyIntentService.INDEX_FLAG,i);
            startService(intent);
        }
        MyIntentService.setUpdateUI(this);
    }

    @Override
    public void updateUI(Message message) {
        handler.sendMessageDelayed(message,message.what * 1000);
    }
}
