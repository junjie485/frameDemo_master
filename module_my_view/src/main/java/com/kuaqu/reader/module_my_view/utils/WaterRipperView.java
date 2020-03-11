package com.kuaqu.reader.module_my_view.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class WaterRipperView extends View{
    private Paint mPaint;
    private int dx;
    private int mItemWaveLength=800;
    private Path path;


    public WaterRipperView(Context context) {
        super(context);
    }

    public WaterRipperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint();
        path=new Path();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }
    //二阶贝塞尔曲线：  path.quadTo（dx1,dy1,dx2,dy2）绝对坐标。rQuadTo(float dx1, float dy1, float dx2, float dy2)相对坐标（相对于上一个终点坐标位置）。

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        int originY = 300;
        int halfWaveLen = mItemWaveLength/2;
        path.moveTo(-mItemWaveLength+dx,originY);
        for(int i=-mItemWaveLength;i<getMeasuredWidth()+mItemWaveLength;i+=mItemWaveLength){
            path.rQuadTo(halfWaveLen/2,-100,halfWaveLen,0);//此时终点坐标是（halfWaveLen,0）,下个贝塞尔曲线坐标，由此为基础算出。
            path.rQuadTo(halfWaveLen/2,100,halfWaveLen,0);
        }
        path.lineTo(getWidth(),getHeight());
        path.lineTo(0,getHeight());
        path.close();
        canvas.drawPath(path,mPaint);
    }
    public void startAnim(){
        ValueAnimator animator=ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                dx= (int) valueAnimator.getAnimatedValue();
                invalidate();//刷新ondraw（）方法
            }
        });
        animator.start();
    }
}
