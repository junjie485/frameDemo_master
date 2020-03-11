package com.kuaqu.reader.module_my_view.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup{



    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //要使viewGroup支持子控件的layout_margin参数，必须重载generateLayoutParams()函数
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }
    /*
    * 测量控件：如果mode是EXACTLY，则宽是measureWidth。如果mode是AT_MOST,则宽需要自己计算
    * 遍历子view，测量每个字view的宽高，然后利用MarginLayoutParams，获取左右margain，对所有子view
    * 的宽高相加，得到viewgroup的宽高。
    * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth=MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight=MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode=MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode=MeasureSpec.getMode(heightMeasureSpec);

        int lineWidth=0;int lineHeight=0;
        int width=0;int height=0;
        int count=getChildCount();
        for(int i=0;i<count;i++){
            View child=getChildAt(i);
            // 测量每一个child的宽和高
            measureChild(child,widthMeasureSpec,heightMeasureSpec);//注意：不执行这步，无法得到子view宽高
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth=child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            int childHeight=child.getMeasuredHeight()+lp.topMargin+lp.topMargin;
            if(lineWidth+childWidth>measureWidth){
                width=Math.max(lineWidth,childWidth);
                height+=lineHeight;
            }else {
                lineWidth+=childWidth;
                lineHeight=Math.max(lineHeight,childHeight);
            }
            //最后一行是不会超出width范围的，所以要单独处理
            if (i == count -1){
                height += lineHeight;
                width = Math.max(width,lineWidth);
            }

        }
        setMeasuredDimension((measureWidthMode==MeasureSpec.EXACTLY)?measureWidth:width,(measureHeightMode==MeasureSpec.EXACTLY)?measureHeight:height);
    }

    /*
    *计算子view在viewgroup的位置。
    * */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
            int count=getChildCount();
            int lineWidth=0;int lineHeight=0;
            int top=0,left=0;
            for(int i=0;i<count;i++){
                View child=getChildAt(i);
                MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
                int childWidth=child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
                int childHeight=child.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
                if(childWidth+lineWidth>getMeasuredWidth()){
                    top+=lineHeight;
                    left=0;
                    lineWidth=childWidth;
                    lineHeight=childHeight;
                }else {
                    lineHeight=Math.max(lineHeight,childHeight);
                    lineWidth+=childWidth;
                }
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc =lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                child.layout(lc, tc, rc, bc);
                //将left置为下一子控件的起始点
                left+=childWidth;
            }
    }
}
