package com.kuaqu.module_common_class.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MyNestedScrollingChild extends LinearLayout implements NestedScrollingChild {
    private NestedScrollingChildHelper mChildHelper;
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private int mLastTouchX, mLastTouchY;
    private int showHeight;

    public MyNestedScrollingChild(Context context) {
        super(context);
        init();
    }

    public MyNestedScrollingChild(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mChildHelper = new NestedScrollingChildHelper(this);
        mChildHelper.setNestedScrollingEnabled(true);
    }

    @Override
    public void setNestedScrollingEnabled(boolean var1) {
        mChildHelper.setNestedScrollingEnabled(var1);
    }

    /**
     * 允许嵌套滑动
     * @return
     */
    @Override
    public boolean isNestedScrollingEnabled() {
        return true;
    }

    @Override
    public boolean startNestedScroll(int var1) {
        return mChildHelper.startNestedScroll(var1);
    }

    @Override
    public void stopNestedScroll() {
        mChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int var1, int var2, int var3, int var4, @Nullable int[] var5) {
        return mChildHelper.dispatchNestedScroll(var1, var2, var3, var4, var5);
    }

    @Override
    public boolean dispatchNestedPreScroll(int var1, int var2, @Nullable int[] var3, @Nullable int[] var4) {
        return mChildHelper.dispatchNestedPreScroll(var1, var2, var3, var4);
    }

    @Override
    public boolean dispatchNestedFling(float var1, float var2, boolean var3) {
        return mChildHelper.dispatchNestedFling(var1, var2, var3);
    }

    @Override
    public boolean dispatchNestedPreFling(float var1, float var2) {
        return mChildHelper.dispatchNestedPreFling(var1, var2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchY = (int) (event.getRawY() + 0.5f);
                int nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE;
                nestedScrollAxis |= ViewCompat.SCROLL_AXIS_HORIZONTAL;
                startNestedScroll(nestedScrollAxis);
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) (event.getX() + 0.5f);
                int y = (int) (event.getRawY() + 0.5f);
                int dx = mLastTouchX - x;
                int dy = mLastTouchY - y;
                mLastTouchY = y;
                mLastTouchX = x;
                if (dispatchNestedPreScroll(dx, dy, mScrollConsumed, mScrollOffset)) {
                    dy -= mScrollConsumed[1];
                    Log.e("dadysua","dsajifw"+dy);
                    if (dy == 0) {
                        return true;
                    }
                } else {
                    Log.e("dadysua","====zi"+mScrollOffset[1]);
                    scrollBy(0, dy);
                }
                break;
        }
        return true;
    }

    @Override
    public void scrollTo(int x, int y) {
        int mh = getMeasuredHeight();
        int maxY = mh - showHeight;
        if (y < 0) {
            y = 0;
        }
        if (y > maxY) {
            y = maxY;
        }
        super.scrollTo(x, y);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (showHeight <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            showHeight = getMeasuredHeight();
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(1000000, MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

