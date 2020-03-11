package com.kuaqu.module_common_class.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class NestChild extends View implements NestedScrollingChild {
    private NestedScrollingChildHelper childHelper;

    public NestChild(Context context) {
        this(context, null);
    }

    public NestChild(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestChild(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        childHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

/*    @Override
    public boolean hasNestedScrollingParent() {
        return childHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return childHelper.isNestedScrollingEnabled();
    }*/

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        childHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return childHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        childHelper.stopNestedScroll();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow) {
        //滚动之后将剩余滑动传给父类
        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
        //子View滚动之前将滑动距离传给父类
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return childHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    private int mOldY;
    private int[] mConsumed = new int[2];
    private int[] mOffset = new int[2];

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //启动滑动，传入方向
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                mOldY= (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y= (int) event.getRawY();
                int offsetY=y-mOldY;
                //通知父类，如果返回true，表示父类消耗了触摸
                if(dispatchNestedPreScroll(0,offsetY,mConsumed,mOffset)){
                    Log.e("嵌套滑动","子控件消费"+mConsumed[1]);
                    //减去父控件消耗距离，得到自身需移动距离
                    offsetY-=mConsumed[1];
                }
                int unConsumed = 0;
                float targetY = getTranslationY() + offsetY;
                if (targetY > -500 && targetY < 500) {
                    setTranslationY(targetY);
                } else {
                    unConsumed = offsetY;
                    offsetY = 0;
                }
                Log.e("嵌套滑动",""+offsetY+"==="+unConsumed);
//                滚动完成之后，通知当前滑动的状态
                dispatchNestedScroll(0, offsetY, 0, unConsumed, mOffset);
                mOldY = y;
                break;
            case MotionEvent.ACTION_UP:
                //滑动结束
                stopNestedScroll();
                break;
        }
        return true;
    }
}
