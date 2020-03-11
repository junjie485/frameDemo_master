package com.kuaqu.reader.module_my_view.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class RippleView extends Button {
    private Paint paint;
    private int mX, mY;
    private int currentRadius;
    private RadialGradient radialGradient;
    private int DEFAULT_RADIUS = 50;
    private ObjectAnimator animator;
    public RippleView(Context context) {
        super(context);
    }


    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = (int) event.getX();
                mY = (int) event.getY();
                setRadius(DEFAULT_RADIUS);
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(animator!=null&&animator.isRunning()){
                    animator.cancel();
                }
                animator=ObjectAnimator.ofInt(this,"radius",DEFAULT_RADIUS,getWidth());
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        setRadius(0);
                    }
                });
                animator.setDuration(600);
                animator.start();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void setRadius(int default_radius) {
        currentRadius=default_radius;
        if(currentRadius>0){
            radialGradient=new RadialGradient(mX,mY,currentRadius,0x00FFFFFF, 0xFF58FAAC, Shader.TileMode.CLAMP);
            paint.setShader(radialGradient);
        }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mX,mY,currentRadius,paint);
    }
}
