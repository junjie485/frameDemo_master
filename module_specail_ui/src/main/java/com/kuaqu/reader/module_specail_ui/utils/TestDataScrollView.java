package com.kuaqu.reader.module_specail_ui.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class TestDataScrollView extends ScrollView {
    private View topView;
    private int topHeight;
    private int lastX,lastY;
    private int mTouchSlop;
    public TestDataScrollView(Context context) {
        super(context);
    }

    public TestDataScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int currentX= (int) ev.getX();
        int currentY= (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=currentX;
                lastY=currentY;
                requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_MOVE:
                int fy=Math.abs(lastY-currentY);
                lastY=currentY;
                if(mTouchSlop<fy&&getScrollY()<topHeight){
                    return true;
                }else {
                    requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ViewGroup viewGroup= (ViewGroup) getChildAt(0);
        topView=viewGroup.getChildAt(0);
        topHeight=topView.getMeasuredHeight();
        Log.e("yyyyy","-->"+topHeight);
    }
}
