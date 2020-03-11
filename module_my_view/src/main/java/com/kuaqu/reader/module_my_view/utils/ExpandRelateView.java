package com.kuaqu.reader.module_my_view.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class ExpandRelateView extends RelativeLayout {
    private int width;
    private int height;
    private Paint paint;
    private float scale;
    private Path path;

    public ExpandRelateView(Context context) {
      this(context,null);

    }

    public ExpandRelateView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ExpandRelateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint=new Paint();
        paint.setDither(true);
        paint.setColor(Color.WHITE);
        path=new Path();
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=getMeasuredWidth();
        height=getMeasuredHeight();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        RectF rect=new RectF(0,0, width*scale,height);
        canvas.clipRect(rect);//先绘制裁剪部分，在绘制显示内容
        super.dispatchDraw(canvas);
        canvas.restore();

    }
    public void startAnim(){
        ValueAnimator animator=ValueAnimator.ofFloat(0,1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale= (float) animation.getAnimatedValue();
                Log.e("dasuida","-->"+scale);
                invalidate();
            }
        });
        animator.setDuration(2000);
        animator.start();
    }
}
