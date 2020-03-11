package com.kuaqu.reader.module_my_view.activity;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_my_view.R;
import com.kuaqu.reader.module_my_view.utils.MyImageView;

import java.util.ArrayList;
import java.util.List;

public class CustomViewActivity extends BaseActivity {
    private TextView title;
    private static int state = 1;
    private static int num1 = 1;
    private static int num2 = 2;
    CustomViewActivity t;
    MyImageView myImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        title=findViewById(R.id.title);
        myImg=findViewById(R.id.myImg);
        title.setText("自定义viewgroup,view基础");
        t=new CustomViewActivity();
        List<Rect> rects=new ArrayList<>();
        Rect rect=new Rect(0,0,100,100);
        Rect rect2=new Rect(700,0,1000,100);
        rects.add(rect);
        rects.add(rect2);
        myImg.setRects(rects);
        myImg.setCallBack(new MyImageView.CallBack() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(CustomViewActivity.this, "点击事件触发"+position, Toast.LENGTH_SHORT).show();
            }
        });




//        initThread();

    }

    private void initThread() {
        new Thread(new Runnable(){
            @Override
            public void run(){
                while(true){
                    //两个线程都用t对象作为锁，保证每个交替期间只有一个线程在打印
                    synchronized(t){
                        // 如果state!=1, 说明此时尚未轮到线程1打印, 线程1将调用t的wait()方法, 直到下次被唤醒
                        if(state!=1){
                            try{
                                t.wait();
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 当state=1时, 轮到线程1打印5次数字
                        for(int j=0;j<5;j++){
                            Log.e("线程1","-->"+num1);
                            num1 +=2;
                        }
//                        // 线程1打印完成后, 将state赋值为2, 表示接下来将轮到线程2打印
//                        state = 2;
//                        // notifyAll()方法唤醒在t上wait的线程2, 同时线程1将退出同步代码块, 释放t锁
//                        t.notifyAll();
                    }
                }
            }
        }).start();


    }

  public void onPoints(View view){
        state=2;
      new Thread(new Runnable(){
          @Override
          public void run(){
              while(num2<10){
                  synchronized(t){
                      if(state!=2){
                          try{
                              t.wait();
                          }catch(InterruptedException e){
                              e.printStackTrace();
                          }
                      }
                      try {
                          Thread.sleep(2000);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                      for(int i=0;i<5;i++){
                          Log.e("线程2","-->"+num2);
                          num2 +=2;
                      }
                      state=1;
                      t.notifyAll();
                  }

              }
          }
      }).start();
  }
}
