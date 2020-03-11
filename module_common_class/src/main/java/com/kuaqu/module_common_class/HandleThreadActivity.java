package com.kuaqu.module_common_class;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kuaqu.reader.component_base.base.BaseActivity;

import java.time.temporal.Temporal;

public class HandleThreadActivity extends BaseActivity {
    private TextView textView;
    HandlerThread handlerThread;
    Handler handler;

    @SuppressLint("HandlerLeak")
    Handler uiHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 11:
                    Log.e("dsuida","----huidiao");
                    textView.setText("" + msg.obj);
                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_thread);
        textView = findViewById(R.id.textView);
        //创建HandlerThread实例
        handlerThread = new HandlerThread("handlerThread");
        //开始运行线程
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()) {//将handle与线程中looper对象绑定，一般线程是没有looper对象的。
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {//这里执行耗时操作，之后通知主线程。(串行执行handle)
                    case 1:
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("dsuida","------3");
                        uiHandle.sendMessage(uiHandle.obtainMessage(11, "你吃了吗"));
                        break;
                    case 2:
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("dsuida","------5");
                        uiHandle.sendMessage(uiHandle.obtainMessage(11, "吃过了，嘛呢"));
                        break;
                }
            }
        };

    }



    public void sendInfo(View view) {
        handler.sendEmptyMessage(1);
    }

    public void sendInfo2(View view) {
        handler.sendEmptyMessage(2);
    }
}
