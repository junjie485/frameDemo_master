package com.kuaqu.reader.module_specail_ui.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.kuaqu.reader.module_specail_ui.R;

public class StickyNavLayout2 extends LinearLayout implements NestedScrollingParent {
    private View topView, nav, viewPager;
    private int topHeight;
    private Scroller scroller;
    private ValueAnimator mOffsetAnimator;
    private Interpolator mInterpolator;
    public StickyNavLayout2(Context context) {
        super(context);
    }

    public StickyNavLayout2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller=new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = findViewById(R.id.topView);
        nav = findViewById(R.id.nav);
        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
       topHeight= topView.getMeasuredHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //手动设置viewpager高度
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = getMeasuredHeight() - nav.getMeasuredHeight();
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes& ViewCompat.SCROLL_AXIS_VERTICAL)!=-0;//接受嵌套滑动
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
        boolean isTopHidden=dy>0&&getScrollY()<topHeight;
        boolean isTopShow=dy<0&&getScrollY()>0&&!ViewCompat.canScrollVertically(target,-1);
        if(isTopHidden||isTopShow){
            scrollBy(0,dy);
            consumed[1]=dy;
        }

    }
    private int TOP_CHILD_FLING_THRESHOLD = 3;
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        //如果是recyclerView 根据判断第一个元素是哪个位置可以判断是否消耗
        //这里判断如果第一个元素的位置是大于TOP_CHILD_FLING_THRESHOLD的
        //认为已经被消耗，在animateScroll里不会对velocityY<0时做处理
        if (target instanceof RecyclerView && velocityY < 0) {
            final RecyclerView recyclerView = (RecyclerView) target;
            final View firstChild = recyclerView.getChildAt(0);
            final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD;
        }
        if (!consumed) {//没有被消耗完
            animateScroll(velocityY, computeDuration(0),consumed);
        } else {
            animateScroll(velocityY, computeDuration(velocityY),consumed);
        }
        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
       /* if(getScrollY()>=topHeight){
            return false;
        }
        fling((int) velocityY);
        return true;*/
        //不做拦截 可以传递给子View
        return false;
    }

    private void fling(int velocityY) {
        scroller.fling(0,getScrollY(),0,velocityY,0,0,0,topHeight);
        invalidate();
    }
    @Override
    public void computeScroll(){
        if(scroller.computeScrollOffset()){
            scrollTo(0,scroller.getCurrY());
            invalidate();
        }
    }
    @Override
    public void scrollTo(int x,int y){
        if(y<0){
            y=0;
        }
        if(y>topHeight){
            y=topHeight;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }
    }

    /**
     * 根据速度计算滚动动画持续时间
     * @param velocityY
     * @return
     */
    private int computeDuration(float velocityY) {
        final int distance;
        if (velocityY > 0) {
            distance = Math.abs(topView.getHeight() - getScrollY());
        } else {
            distance = Math.abs(topView.getHeight() - (topView.getHeight() - getScrollY()));
        }


        final int duration;
        velocityY = Math.abs(velocityY);
        if (velocityY > 0) {
            duration = 3 * Math.round(1000 * (distance / velocityY));
        } else {
            final float distanceRatio = (float) distance / getHeight();
            duration = (int) ((distanceRatio + 1) * 150);
        }

        return duration;

    }

    private void animateScroll(float velocityY, final int duration,boolean consumed) {
        final int currentOffset = getScrollY();
        final int topHeight = topView.getHeight();
        if (mOffsetAnimator == null) {
            mOffsetAnimator = new ValueAnimator();
            mOffsetAnimator.setInterpolator(mInterpolator);
            mOffsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (animation.getAnimatedValue() instanceof Integer) {
                        scrollTo(0, (Integer) animation.getAnimatedValue());
                    }
                }
            });
        } else {
            mOffsetAnimator.cancel();
        }
        mOffsetAnimator.setDuration(Math.min(duration, 600));

        if (velocityY >= 0) {
            mOffsetAnimator.setIntValues(currentOffset, topHeight);
            mOffsetAnimator.start();
        }else {
            //如果子View没有消耗down事件 那么就让自身滑倒0位置
            if(!consumed){
                mOffsetAnimator.setIntValues(currentOffset, 0);
                mOffsetAnimator.start();
            }

        }
    }

}
