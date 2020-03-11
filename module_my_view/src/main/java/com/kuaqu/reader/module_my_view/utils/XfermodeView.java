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

import com.kuaqu.reader.module_my_view.R;

public class XfermodeView extends View {
    private int mWidth = 400;
    private int mHeight = 400;
    private Bitmap DstBmp;
    private Bitmap SrcBmp;
    private Paint mPaint;

    public XfermodeView(Context context) {
        super(context);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        DstBmp = getDstBitmap();
        SrcBmp = getSrcBitmap();
    }
    //SRC_IN:将两图像合并重合，图像相交部分按规则保留，图像重合不相交部分，将像素点抹除透明。注意：两图像的所在位置关系
    //图像混合模式：首先，创建两个bitmap图层对象，(这是合并基础)。其次将两个bitmap合并为一个新的bitmap图层，然后将其绘制到原图层上。
    //另类：创建bitmap图层，然后先绘制图形，在利用图像混合，与bitmap合并，效果同上。如果先绘制bitmap，再图像混合 图形，则只显示图形。
    //解析：先绘制图形的话，会生成一个图层，然后与bitmap合并，是正常的，而后绘制图形，相当于在绘制了bitmap的图层上继续绘制图形，导致图像被图形遮盖。
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bit = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas can = new Canvas(bit);
        can.drawBitmap(DstBmp, 0, 0, mPaint);//绘制bitmap的位置
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        can.drawBitmap(SrcBmp, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.drawBitmap(bit, 0, 0, mPaint);

        //另一种实现方式。由于画在了主图层上，进行混合模式之后，相交之外的都变成了透明色，会变黑。
 /*       int layerID= canvas.saveLayer(0,0,mWidth,mHeight,mPaint,Canvas.ALL_SAVE_FLAG);
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        canvas.drawOval(new RectF(0, 0, mWidth * 3 / 4, mHeight * 3 / 4), paint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(SrcBmp, 0, 0, mPaint);
        canvas.restoreToCount(layerID);*/


     /*   Bitmap bit = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas can = new Canvas(bit);
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        can.drawOval(new RectF(0, 0, mWidth * 3 / 4, mHeight * 3 / 4), paint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        can.drawBitmap(SrcBmp, 0, 0, mPaint);
        canvas.drawBitmap(bit, 0, 0, mPaint);*/

    }

    private Bitmap getSrcBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);//蓝色
        canvas.drawRect(new RectF(mWidth / 3, mHeight / 3, mWidth, mHeight), paint);
        return bitmap;
    }

    private Bitmap getDstBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        canvas.drawOval(new RectF(0, 0, mWidth * 3 / 4, mHeight * 3 / 4), paint);
        return bitmap;
    }
}
