package com.kuaqu.module_common_class.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class NestParent extends LinearLayout implements NestedScrollingParent {
    private NestedScrollingParentHelper parentHelper;
    public NestParent(Context context) {
        this(context,null);
    }

    public NestParent(Context context, @Nullable AttributeSet attrs) {
       this(context,attrs,0);
    }

    public NestParent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        parentHelper=new NestedScrollingParentHelper(this);
    }
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        //child 嵌套滑动的子控件(当前控件的子控件),target,手指触摸的控件
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        parentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        parentHelper.onStopNestedScroll(child);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {


    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //开始滑动之前
//        super.onNestedPreScroll(target, dx, dy, consumed);
        Log.e("嵌套滑动","父控件消费"+dy+"===="+consumed[1]);
        if(getChildAt(0).getTranslationY()+dy>100){
            consumed[1]=dy;
        }else {
            getChildAt(0).setTranslationY(getChildAt(0).getTranslationY() + dy);
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return parentHelper.getNestedScrollAxes();
    }

}
