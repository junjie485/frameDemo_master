package com.kuaqu.reader.module_specail_ui.utils;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.kuaqu.reader.component_base.utils.ScreenUtils;

public class MyTestScrollView extends ScrollView implements NestedScrollingParent {
    private boolean ntercepted = false;
    int touchSlop;
    private int lastX,lastY;
    Scroller scroller = new Scroller(getContext());
    private VelocityTracker velocityTracker=VelocityTracker.obtain();
    public MyTestScrollView(Context context) {
        super(context);
    }

    public MyTestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("构造函数", "2");
        touchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyTestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("构造函数", "3");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.e("嵌套滑动", "parent=====onStartNestedScroll");
        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        super.onNestedScrollAccepted(child, target, axes);
        Log.e("嵌套滑动", "parent=====onNestedScrollAccepted");
    }

    //接收子view滑动回调
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
        Log.e("嵌套滑动", "parent=====onNestedPreScroll");
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        Log.e("嵌套滑动", "parent=====onNestedScroll");
    }

    @Override
    public void onStopNestedScroll(View target) {
        super.onStopNestedScroll(target);
        Log.e("嵌套滑动", "parent=====onStopNestedScroll");
    }
}
