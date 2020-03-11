package com.kuaqu.reader.module_my_view.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kuaqu.reader.module_my_view.R;

public class GuaGuaCardView extends View {
    private Paint mPaint;
    private int mWidth, mHeight;
    private Bitmap srcBmp, dstBmp;
    private Rect mBounds;
    private String text = "恭喜你，中大奖了！！！";
    private Path path;
    private float preX;
    private float preY;

    public GuaGuaCardView(Context context) {
        super(context);
    }

    public GuaGuaCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(30);
        mPaint.setStyle(Paint.Style.STROKE);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=getWidth();
        mHeight=getHeight();
        Bitmap ssBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ss);
        srcBmp=Bitmap.createBitmap(mWidth,mHeight, Bitmap.Config.ARGB_8888);
        Canvas c=new Canvas(srcBmp);
        c.drawBitmap(ssBmp,null,new RectF(0,0,mWidth,mHeight),new Paint());

        dstBmp = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);//设置绘制路径的范围

        mBounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), mBounds);
        path = new Path();
        Log.e("刮刮卡","--->"+getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(32);
        canvas.drawText(text, mWidth / 2 - mBounds.width() / 2, mHeight / 2 - mBounds.height() / 2, paint);
        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        Canvas can = new Canvas(dstBmp);
        can.drawPath(path, mPaint);
        canvas.drawBitmap(dstBmp, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(srcBmp, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerID);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(),event.getY());
                preX = event.getX();
                preY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX = (preX+event.getX())/2;
                float endY = (preY+event.getY())/2;
                path.quadTo(preX,preY,endX,endY);
                preX = event.getX();
                preY =event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("数据",left+"==="+top+"==="+right+"=="+bottom);
    }
}
