package com.kuaqu.module_common_class.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyNestedScrollingParent extends LinearLayout implements NestedScrollingParent {
    private NestedScrollingParentHelper mParentHelper;
    private ImageView iv;
    private TextView tv;
    private MyNestedScrollingChild nsv;
    private int ivHeight, tvHeight;

    public MyNestedScrollingParent(Context context) {
        super(context);
        init();
    }

    public MyNestedScrollingParent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iv = (ImageView) getChildAt(0);
        tv = (TextView) getChildAt(1);
        nsv = (MyNestedScrollingChild) getChildAt(2);
        iv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ivHeight = iv.getMeasuredHeight();
            }
        });
        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tvHeight = tv.getMeasuredHeight();
            }
        });

    }

    private void init() {
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    /**
     * @param child
     * @param target
     * @param nestedScrollAxes 嵌套滑动的坐标系，也就是用来判断是X轴滑动还是Y轴滑动，返回true或者false，返回false就没得玩了
     * @return 如果要接受嵌套滑动操作，就返回true吧
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View var1, @NonNull View var2, int var3) {
        mParentHelper.onNestedScrollAccepted(var1, var2, var3);
    }

    @Override
    public void onStopNestedScroll(@NonNull View var1) {
        mParentHelper.onStopNestedScroll(var1);
    }

    @Override
    public void onNestedScroll(@NonNull View var1, int var2, int var3, int var4, int var5) {
    }

    /**
     * @param child
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(@NonNull View child, int dx, int dy, @NonNull int[] consumed) {
        Log.e("dadysua","onNestedPreScroll"+dy);
        if (showImage(dy) || hideImage(dy)) {
            consumed[1] = dy;//完全消费有y轴的滑动
            scrollBy(0, dy);
        }
    }

    @Override
    public boolean onNestedFling(@NonNull View var1, float var2, float var3, boolean var4) {
        return super.onNestedFling(var1, var2, var3, var4);
    }

    @Override
    public boolean onNestedPreFling(@NonNull View var1, float var2, float var3) {
        return super.onNestedPreFling(var1, var2, var3);
    }

    @Override
    public int getNestedScrollAxes() {
        return super.getNestedScrollAxes();
    }

    /**
     * 滑动控制在图片的高度
     *
     * @param x
     * @param y
     */
    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > ivHeight) {
            y = ivHeight;
        }
        super.scrollTo(x, y);
    }

    /**
     * 往上往下滑，当Child滑动完了，只要没有滑动到起始位置（getScrollY==0），就一直往下滑，当滑动到起始位置时，滑动事件再传给Childs
     *首先，有父控件消耗滑动事件，如果图片隐藏则交由子控件处理滑动事件，当子控件下拉到图片显示的位置，交由父控件消耗事件
     * @param dy
     * @return
     */
    private boolean showImage(int dy) {
        if (dy < 0) {
            if (getScrollY() > 0 && nsv.getScrollY() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 往上滑是隐藏图片
     *
     * @param dy >0是往上滑动，<0是往下滑动
     *           getScrollY()往上滑动值越大
     * @return 往上滑，当滑动的距离超过图片的高度时，则将滑动事件传给Child
     */
    private boolean hideImage(int dy) {
        if (dy > 0) {
            if (getScrollY() < ivHeight) {
                return true;
            }
        }
        return false;
    }
}

