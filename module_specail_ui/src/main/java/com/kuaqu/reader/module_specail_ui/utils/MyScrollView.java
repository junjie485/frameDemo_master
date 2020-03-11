package com.kuaqu.reader.module_specail_ui.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by baoyunlong on 16/6/8.
 */
public class MyScrollView extends ScrollView {
    private static String TAG=MyScrollView.class.getName();

    public void setScrollListener(ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    private ScrollListener mScrollListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(mScrollListener!=null){
                    int contentHeight=getChildAt(0).getMeasuredHeight();
                    int scrollHeight=getHeight();
                    Log.e("MyScrollView","scrollY:"+getScrollY()+"contentHeight:"+contentHeight+" scrollHeight"+scrollHeight+"mwasureHegiht"+getMeasuredHeight());

                    int scrollY=getScrollY();
                    mScrollListener.onScroll(scrollY);

                    if(scrollY+scrollHeight>=contentHeight||contentHeight<=scrollHeight){//如果滚动高度加上当前高度大于topscrollview整体高度，到底了。
                        mScrollListener.onScrollToBottom();
                    }else {
                        mScrollListener.notBottom();
                    }

                    if(scrollY==0){
                        mScrollListener.onScrollToTop();
                    }

                }
                break;
        }
        boolean result=super.onTouchEvent(ev);
//        requestDisallowInterceptTouchEvent(false);
        Log.e("多动动","-->"+result);
        return result;
    }


    public interface ScrollListener{
        void onScrollToBottom();
        void onScrollToTop();
        void onScroll(int scrollY);
        void notBottom();
    }
}
