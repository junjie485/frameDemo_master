package com.kuaqu.reader.module_specail_ui.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.Scroller;

import com.kuaqu.reader.module_specail_ui.R;
/*
*
*
* */

public class StickyNavLayout extends LinearLayout {
    private View topView, nav, viewPager;
    private int mTouchSlop;
    private VelocityTracker tracker;
    private OverScroller scroller;
    private int topHeight;
    private int lastY;
    private boolean mDragging=false,isTopHidden=false;
    private RecyclerView recyclerView;
    private int mMaximumVelocity, mMinimumVelocity;

    public StickyNavLayout(Context context) {
        super(context);
    }

    public StickyNavLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyNavLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        tracker = VelocityTracker.obtain();
        scroller = new OverScroller(context);
        mMaximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = findViewById(R.id.topView);
        nav = findViewById(R.id.nav);
        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //手动设置viewpager高度
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = getMeasuredHeight() - nav.getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        topHeight = topView.getMeasuredHeight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y= (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("888888","+++++ACTION_DOWN");
                lastY=y;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("888888","+++++++ACTION_MOVE");
                int dy=y-lastY;
                getCurrentRecycler();
                if(Math.abs(dy)>mTouchSlop){
                    mDragging=true;
                    if(!isTopHidden||(recyclerView.getScrollY() == 0 && isTopHidden && dy > 0)){
                        Log.e("shijian","拦截"+isTopHidden+"===="+recyclerView.getScrollY());
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void getCurrentRecycler() {
        recyclerView=viewPager.findViewById(R.id.recyclerView);
    }
    //这里解释一下down的时候为什么返回true
    //当你的触点在头部时，会直接走到ontouch方法中，不经过intercept的move action

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int y= (int) ev.getY();
        tracker.addMovement(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("888888","----ACTION_DOWN");
                lastY=y;
                tracker.clear();
                tracker.addMovement(ev);
                if(!scroller.isFinished()){
                    scroller.abortAnimation();
                }
               return true;
            case MotionEvent.ACTION_MOVE://线性布局滑动
                Log.e("888888","----ACTION_MOVE");
                int dy=y-lastY;
                if(!mDragging&&Math.abs(dy)>mTouchSlop){
                    mDragging=true;
                }
                if (mDragging)
                {
                    scrollBy(0, -dy);
                    lastY = y;
                }

                break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                if (!scroller.isFinished())
                {
                    scroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_UP://执行惯性滑动
                Log.e("888888","----ACTION_UP");
                mDragging=false;
                tracker.computeCurrentVelocity(1000,mMaximumVelocity);
                int yvelocity= (int) tracker.getYVelocity();
                Log.e("shujiui",yvelocity+"==="+topHeight);
                scroller.fling(0, getScrollY(), 0, -yvelocity, 0, 0,  0, topHeight);
                invalidate();
                tracker.clear();
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll(){
        if(scroller.computeScrollOffset()){
            scrollTo(0,scroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public void scrollTo(int x, int y){
        if (y < 0)
        {
            y = 0;
        }
        if (y > topHeight)
        {
            y = topHeight;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }

        isTopHidden = getScrollY() == topHeight;

    }

}
