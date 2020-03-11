package com.kuaqu.reader.module_my_view.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

public class VerticalLinearLayout extends ViewGroup {
    private Scroller scroller;
    private VelocityTracker tracker;
    private int lastY;
    private int scroStart, scroEnd;
    private int mScreenHeight;
    private int currentPage = 0;
    private OnPageChangeListener mOnPageChangeListener;

    public VerticalLinearLayout(Context context) {
        super(context);
    }

    public VerticalLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
        tracker = VelocityTracker.obtain();
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
            lp.height = mScreenHeight * getChildCount();
            setLayoutParams(lp);
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);

                child.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        tracker.addMovement(event);
        tracker.computeCurrentVelocity(1000);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scroStart = getScrollY();
                lastY = y;
                Log.e("ioioio", "-->" + getMeasuredHeight()+"===="+getHeight());
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = lastY - y;
                //边界控制
                if (dy > 0 && getScrollY() + dy > getMeasuredHeight() - mScreenHeight) {
                    dy = (getMeasuredHeight() - mScreenHeight - getScrollY());
                }
                if (dy < 0 && getScrollY() + dy < 0) {
                    dy = -getScrollY();
                }
                scrollBy(0, dy);
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                scroEnd = getScrollY();
                if (scroEnd - scroStart > 0) {//从上向下
                    if ((scroEnd - scroStart) > mScreenHeight / 2 || Math.abs(tracker.getYVelocity()) > 600) {
                        scroller.startScroll(0, getScrollY(), 0, mScreenHeight - (scroEnd - scroStart));
                    } else {
                        scroller.startScroll(0, getScrollY(), 0, -(scroEnd - scroStart));
                    }
                } else {//从下向上
                    if (scroStart - scroEnd > mScreenHeight / 2 || Math.abs(tracker.getYVelocity()) > 600) {
                        scroller.startScroll(0, getScrollY(), 0, -mScreenHeight - (scroEnd - scroStart));
                    } else {
                        scroller.startScroll(0, getScrollY(), 0, -(scroEnd - scroStart));
                    }
                }
                postInvalidate();
                break;
        }
        return true;

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            invalidate();
        } else {
            int position = getScrollY() / mScreenHeight;

            Log.e("xxx", position + "," + currentPage);
            if (position != currentPage) {
                if (mOnPageChangeListener != null) {
                    currentPage = position;
                    mOnPageChangeListener.onPageChange(currentPage);
                }
            }

        }
    }

    public interface OnPageChangeListener {
        void onPageChange(int currentPage);
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

}
