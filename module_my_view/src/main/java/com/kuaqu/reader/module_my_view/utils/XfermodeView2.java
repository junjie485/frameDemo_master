package com.kuaqu.reader.module_my_view.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class XfermodeView2 extends View {
    private Paint mPaint;
    private Bitmap dstBmp,srcBmp;
    private int width = 400;
    private int height = 400;

    public XfermodeView2(Context context) {
        super(context);
    }

    public XfermodeView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        dstBmp= getDstBitmap();
        srcBmp=getSrcBitmap();
        mPaint = new Paint();

    }
    //设置图像混合模式，如果两个图像不相交，则默认显示dst目标图层，如果只相交一部分，则显示dst图层和相交部分
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap=Bitmap.createBitmap(width*2,height*2, Bitmap.Config.ARGB_8888);
        Canvas c=new Canvas(bitmap);

        c.drawBitmap(dstBmp,0,0,mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        c.drawBitmap(srcBmp,0,0,mPaint);


        mPaint.setXfermode(null);
        canvas.drawBitmap(bitmap,0,0,mPaint);



    }

    private Bitmap getSrcBitmap() {
        Bitmap bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFF66AAFF);
        canvas.drawRect(0,0,width,height,p);
        return bitmap;
    }

    public Bitmap getDstBitmap(){
        Bitmap bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFFFFCC44);
        canvas.drawOval(new RectF(0, 0, width, height), p);
        return bitmap;
    }
}
