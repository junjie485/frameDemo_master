package com.kuaqu.reader.module_my_view.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class ShimmerTextView extends TextView {
    private Paint mPaint;
    private int mDx;
    private LinearGradient linearGradient;
    public ShimmerTextView(Context context) {
        super(context);
    }


    public ShimmerTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint=getPaint();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ValueAnimator animator=ValueAnimator.ofInt(0,2*getMeasuredWidth());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDx= (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(2000);
        animator.start();
        int[] color={getCurrentTextColor(),0xff00ff00,getCurrentTextColor()};
        float[] pos={0,0.5f,1};
        linearGradient=new LinearGradient(-getMeasuredWidth(),0,0,0,color,pos, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Matrix matrix=new Matrix();
        matrix.setTranslate(mDx,0);
        linearGradient.setLocalMatrix(matrix);
        mPaint.setShader(linearGradient);
        super.onDraw(canvas);
    }
}
