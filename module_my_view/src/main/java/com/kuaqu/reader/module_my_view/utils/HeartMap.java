package com.kuaqu.reader.module_my_view.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.kuaqu.reader.module_my_view.R;

public class HeartMap extends View {
    private Paint mPaint;
    private Bitmap dstBmp,srcBmp;
    private int dx=0;
    private int mItemWaveLength = 0;
    public HeartMap(Context context) {
        super(context);
    }

    public HeartMap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint();

        mPaint.setColor(Color.RED);

        dstBmp= BitmapFactory.decodeResource(getResources(), R.mipmap.heartmap);
        srcBmp=Bitmap.createBitmap(dstBmp.getWidth(),dstBmp.getHeight(), Bitmap.Config.ARGB_8888);
        mItemWaveLength=dstBmp.getWidth();
        startanim();
    }

    private void startanim() {
        ValueAnimator animator=ValueAnimator.ofInt(0,mItemWaveLength);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx= (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setDuration(6000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Canvas car=new Canvas(srcBmp);
        //清空bitmap
        car.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);

        car.drawRect(dstBmp.getWidth()-dx,0,dstBmp.getWidth(),dstBmp.getHeight(),mPaint);

        Bitmap bitmap=Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c=new Canvas(bitmap);
        c.drawBitmap(dstBmp,0,0,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        c.drawBitmap(srcBmp,0,0,mPaint);
        mPaint.setXfermode(null);
        canvas.drawBitmap(bitmap,0,0,mPaint);
    }
}
