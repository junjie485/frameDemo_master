package com.kuaqu.reader.module_my_view.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/*
* 画布操作
* */

public class TestPaint extends View {
    private Paint mPaint1;
    public TestPaint(Context context) {
        super(context);
    }

    public TestPaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint1=new Paint();
        mPaint1.setColor(Color.BLUE);
        mPaint1.setStrokeWidth(5);
        mPaint1.setStyle(Paint.Style.STROKE);
    }
    //平移：会改变原点坐标。平移后绘制图形，都是以新的原点坐标进行绘制。通过carvas.save()和carvas.restore().来恢复坐标原点位置。（坐标系有改变）
    //缩放：执行缩放后，画布大小会进行相应的缩放（坐标系），之后绘制的图形都会进行缩放，通过carvas.save()和carvas.restore().可以恢复到缩放前状态。（坐标系有改变）
    //旋转：执行旋转后，画布方向会进行相应的旋转（坐标系），之后绘制的图形都会进行旋转，通过carvas.save()和carvas.restore().可以恢复到旋转前状态。（坐标系有改变）
    //裁剪：执行裁剪后，只有裁剪区域可以进行绘制，其他区域是不可用的。而且坐标系是没有发生变化的。通过carvas.save()和carvas.restore().可以恢复到裁剪前状态。即：所有区域可绘制。（坐标系没有改变）
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*平移*/
  /*      canvas.drawRect(200,200,700,700,mPaint1);
        mPaint1.setColor(Color.GRAY);
        canvas.save();
        canvas.translate(200,200);
        canvas.drawRect(200,200,700,700,mPaint1);
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(20);
        canvas.drawText("你好呀",50,50,paint);
        canvas.restore();
        canvas.drawText("你好呀",50,50,paint);*/

        /*裁剪*/
    /*    canvas.save();
        canvas.clipRect(200, 200, 700, 700);//截取画布大小为
        canvas.drawColor(Color.RED);

        mPaint1.setStyle(Paint.Style.FILL);
        canvas.drawRect(200,200,500,500,mPaint1);
        Paint paint=new Paint();
        paint.setTextSize(20);
        paint.setColor(Color.RED);
        canvas.drawText("你回到",250,250,paint);
        canvas.restore();
        canvas.drawText("你回到",50,50,paint);*/


        /*缩放*/
      /*  canvas.drawRect(200,200,700,700,mPaint1);
        canvas.save();
        canvas.scale(0.5f,0.5f,450,450);
        mPaint1.setColor(Color.GRAY);
        canvas.drawRect(200,200,700,700,mPaint1);
        Paint paint=new Paint();
        paint.setTextSize(20);
        paint.setColor(Color.RED);
        canvas.drawText("你回到",50,50,paint);
        canvas.restore();
        canvas.drawText("你回到",50,50,paint);*/

        /*旋转*/
      /*  canvas.drawRect(200,200,700,700,mPaint1);
        canvas.save();
        canvas.rotate(20);
        mPaint1.setColor(Color.GRAY);
        canvas.drawRect(200,200,700,700,mPaint1);
        Paint paint=new Paint();
        paint.setTextSize(20);
        paint.setColor(Color.RED);
        canvas.drawText("你回到",50,50,paint);
        canvas.restore();
        canvas.drawText("你回到",50,50,paint);*/


    }
}
