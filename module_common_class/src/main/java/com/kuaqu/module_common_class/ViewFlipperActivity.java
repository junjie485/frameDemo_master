package com.kuaqu.module_common_class;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;
/*
*ViewFlipper是一个切换控件，一般用于图片的切换，当然它是可以添加View的
*showNext： 显示ViewFlipper里的下一个View
showPrevious： 显示ViewFlipper里的上一个View
* */
public class ViewFlipperActivity extends AppCompatActivity {
    private ViewFlipper flipper;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);
        flipper=findViewById(R.id.vf);
        flipper.addView(View.inflate(this,R.layout.view_ad01,null));
        flipper.addView(View.inflate(this,R.layout.view_ad02,null));
        flipper.addView(View.inflate(this,R.layout.view_ad03,null));


        viewPager=findViewById(R.id.viewPager);
        ImageView imageView=new ImageView(this);
        imageView.setImageResource(R.mipmap.anim2);
        ImageView imageView2=new ImageView(this);
        imageView2.setImageResource(R.mipmap.anim5);
        viewPager.addView(imageView);
        viewPager.addView(imageView2);

    }
}
