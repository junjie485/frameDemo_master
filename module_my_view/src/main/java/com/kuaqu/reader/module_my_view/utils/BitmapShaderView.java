package com.kuaqu.reader.module_my_view.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kuaqu.reader.module_my_view.R;
/*
* 注意：bitmap生成通过bitmap.createBitmap();
*
* */
public class BitmapShaderView extends View {
    private Paint mPaint;
    private Bitmap mBitmap, bitmapBg;
    private int mDx = -1, mDy = -1;


    public BitmapShaderView(Context context) {
        super(context);
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.scenery);



    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        if (bitmapBg == null) {//主要是将bitmap图像缩放至控件大小，生成bitmapBg图像
            bitmapBg = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas1 = new Canvas(bitmapBg);
            canvas1.drawBitmap(mBitmap, null, new Rect(0, 0, getWidth(), getHeight()), mPaint);
        }

        if (mDx != -1 && mDy != -1) {
            mPaint.setShader(new BitmapShader(bitmapBg, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawCircle(mDx, mDy, 150, mPaint);
        }

      /*  //缩放bitmap图像至控件大小的另一种方法
        BitmapShader bitmapShader=new BitmapShader(mBitmap,Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix=new Matrix();
        float scale=(float)getWidth()/mBitmap.getWidth();
        float scale2=(float)getHeight()/mBitmap.getHeight();
        matrix.setScale(scale,scale2);
        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);
        if(mDx!=-1&&mDy!=-1){
            canvas.drawCircle(mDx,mDy,150,mPaint);
        }*/

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDx= (int) event.getX();
                mDy= (int) event.getY();
                return true; //事件拦截，一定要返回true
            case MotionEvent.ACTION_MOVE:
                mDx= (int) event.getX();
                mDy= (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                mDx= -1;
                mDy=-1;
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }
}
