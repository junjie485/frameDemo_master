package com.kuaqu.reader.module_my_view.utils;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class ZoomImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {
    private Matrix matrix=new Matrix();
    private static final float SCALE_MAX=4.0f;
    private float initScale=1.0f;
    private float [] matrixValues=new float[9];
    private ScaleGestureDetector gestureDetector;
    private boolean once;
    private int lastPointerCount;
    private boolean isCanDrag;
    private float lastX,lastY;
    private int mTouchSlop;
    private boolean isCheckLeftAndRight,isCheckTopAndBottom;

    public ZoomImageView(Context context) {
        this(context,null);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setScaleType(ScaleType.MATRIX);
        gestureDetector=new ScaleGestureDetector(context,this);
        setOnTouchListener(this);
        mTouchSlop= ViewConfiguration.getTouchSlop();
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale=getScale();
        float scaleFactor=gestureDetector.getScaleFactor();
        Log.e("hhhhhhh",scale+"===="+scaleFactor+"===="+initScale);
        if ((scale < SCALE_MAX && scaleFactor > 1.0f)
                || (scale > initScale && scaleFactor < 1.0f))
        {
            /**
             * 最大值最小值判断
             */
            if (scaleFactor * scale < initScale)
            {
                scaleFactor = initScale / scale;
            }
            if (scaleFactor * scale > SCALE_MAX)
            {
                scaleFactor = SCALE_MAX / scale;
            }
            /**
             * 设置缩放比例
             */
            matrix.postScale(scaleFactor, scaleFactor, gestureDetector.getFocusX(),
                    gestureDetector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(matrix);
        }

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    public float getScale(){
        matrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        float x=0,y=0;
        int pointCount=event.getPointerCount();
        for(int i=0;i<pointCount;i++){
            x+=event.getX(i);
            y+=event.getY(i);
        }
         x=x/pointCount;
         x=y/pointCount;
        if(pointCount!=lastPointerCount){
            isCanDrag=false;
            lastX=x;
            lastY=y;
        }
        lastPointerCount=pointCount;
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                float dx = x - lastX;
                float dy = y - lastY;
                if(!isCanDrag){
                    isCanDrag=isCanDrag(dx,dy);
                }
                if(isCanDrag) {
                    RectF rectF = getMatrixRectF();
                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        // 如果宽度小于屏幕宽度，则禁止左右移动
                        if (rectF.width() < getWidth()) {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        }
                        // 如果高度小雨屏幕高度，则禁止上下移动
                        if (rectF.height() < getHeight()) {
                            dy = 0;
                            isCheckTopAndBottom = false;
                        }
                        matrix.postTranslate(dx, dy);
                        checkMatrixBounds();
                        setImageMatrix(matrix);
                    }
                }

                lastX=dx;
                lastY=dy;
                break;
        }

        return true;
    }
    private boolean isCanDrag(float dx, float dy)
    {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
       getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if(!once){
            Drawable drawable=getDrawable();
            if(drawable==null){
                return;
            }
            int width=getWidth();
            int height=getHeight();
            int dw=drawable.getIntrinsicWidth();
            int dh=drawable.getIntrinsicHeight();
            float scale=1.0f;
            if(dw>width&&dh<height){
                scale=width*1.0f/dw;
            }
            if(dh>height&&dw<width){
                scale=height*1.0f/dh;
            }
            if(dw>width&&dh>height){
                scale=Math.min(dw*1.0f/width,dh*1.0f/height);
            }
            initScale=scale;
            //图片一致屏幕中心
            matrix.setTranslate((width-dw)/2,(height-dh)/2);
            matrix.setScale(initScale,initScale,width/2,height/2);
            setImageMatrix(matrix);
            once=false;
        }
    }

    private void checkBorderAndCenterWhenScale()
    {

        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width)
        {
            if (rect.left > 0)
            {
                deltaX = -rect.left;
            }
            if (rect.right < width)
            {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height)
        {
            if (rect.top > 0)
            {
                deltaY = -rect.top;
            }
            if (rect.bottom < height)
            {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width)
        {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height)
        {
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        Log.e("iiiiii", "deltaX = " + deltaX + " , deltaY = " + deltaY);

        matrix.postTranslate(deltaX, deltaY);

    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    private RectF getMatrixRectF()
    {
        Matrix max = matrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d)
        {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            max.mapRect(rect);
        }
        return rect;
    }

    private void checkMatrixBounds()
    {
        RectF rect = getMatrixRectF();

        float deltaX = 0, deltaY = 0;
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        // 判断移动或缩放后，图片显示是否超出屏幕边界
        if (rect.top > 0 && isCheckTopAndBottom)
        {
            deltaY = -rect.top;
        }
        if (rect.bottom < viewHeight && isCheckTopAndBottom)
        {
            deltaY = viewHeight - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight)
        {
            deltaX = -rect.left;
        }
        if (rect.right < viewWidth && isCheckLeftAndRight)
        {
            deltaX = viewWidth - rect.right;
        }
        matrix.postTranslate(deltaX, deltaY);
    }


}
