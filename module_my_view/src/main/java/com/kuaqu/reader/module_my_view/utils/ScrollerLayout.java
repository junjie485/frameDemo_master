package com.kuaqu.reader.module_my_view.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class ScrollerLayout extends ViewGroup {
    private float xDown = 0;
    private float lastX = 0;
    private int mTouchSlop;
    private Scroller scroller;
    private VelocityTracker tracker;

    public ScrollerLayout(Context context) {
        super(context);
    }
    //利用Scroller和VelocityTracker主要是完成控件的惯性滑动。比如：当手指松开时，当速度达到某个阈值或滑动距离超过控件一半时，则跳到下一界面
    //否则，则回退到当前界面。
    //Scroller:1.Scroller scroller=new Scroller(context);2.当手指松开时scroller.startScroll(startX(水平方向滚动的偏移值)，startY(垂直方向滚动的偏移值),dx(水平方向滑动的距离),dy(垂直方向滑动的距离) )
    //3.重写computeScroll()方法，完成后续滑动。
    //VelocityTracker: 1.VelocityTracker tarcker=VelocityTracker.obtain();2.在ontouchEvent中 tracker.addMovement(event),同时设置速率  tracker.computeCurrentVelocity(1000);
    //3.获取速率：tracker.getXVelocity()；注意：从上向下滑：速率是负的，从左向右滑动：速率是负的。
    //GestureDetector:主要是用于识别各种各样的手势操作，他比OnTouchEvent区分手势更多。各手势的具体操作，由自己编写
    //1.GestureDetector gesture=new GestureDetector(new simpleGestureListener()); 2.创建simpleGestureListener类，继承GestureDetector.SimpleOnGestureListener。
    //3.在ontouch()中 return gesture.onTouchEvent(event);
    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
        tracker=VelocityTracker.obtain();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        for (int k = 0; k < getChildCount(); k++) {
            View child = getChildAt(k);
            child.layout(k * child.getMeasuredWidth(), 0, (k + 1) * child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        xDown=ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX=xDown;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx=Math.abs(lastX-xDown);
                if(dx>mTouchSlop){
                    return true;
                }
                lastX=xDown;
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        xDown=event.getX();
        tracker.addMovement(event);
        tracker.computeCurrentVelocity(1000);
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                int scrolledX= (int) (lastX-xDown);
                Log.e("滑动","---->"+scrolledX);
                scrollBy(scrolledX,0);
                lastX=xDown;
                break;
            case MotionEvent.ACTION_UP:
                Log.e("速率","--->"+tracker.getXVelocity());
                if(tracker.getXVelocity()<-400){
                    int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                    int dx = targetIndex * getWidth() - getScrollX();
                    scroller.startScroll(getScrollX(),0,dx,0);//主要难算的点在于dx
                    invalidate();
                }else {
                    int dx=0-getScrollX();
                    scroller.startScroll(getScrollX(),0,dx,0);
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }
}
