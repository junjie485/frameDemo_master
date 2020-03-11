package com.kuaqu.reader.module_specail_ui.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/9/5.
 * Description : InnerRecyclerView
 */

public class InnerRecyclerView extends RecyclerView {

    private boolean isNeedConsume;
    private float downX ;    //按下时 的X坐标
    private float downY ;    //按下时 的Y坐标
    private int maxY;



    private NeedIntercepectListener needIntercepectListener;
    public InnerRecyclerView(Context context) {
        super(context);
    }

    public InnerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x= e.getX();
        float y = e.getY();
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                //将按下时的坐标存储
                downX = x;
                downY = y;
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                        //获取到距离差
                        float dx= x-downX;
                        float dy = y-downY;
                        Log.d("ACTION_MOVE","ACTION_MOVE");
                        //通过距离差判断方向
                        int orientation = getOrientation(dx, dy);
                        int[] location={0,0};
                        getLocationOnScreen(location);//获取view控件在整个屏幕坐标位置
                        switch (orientation) {
                            case 'b':
                                Log.e("dsioa","往上滑");
                                //内层RecyclerView下拉到最顶部时候不再处理事件
                                if(!canScrollVertically(-1)){
                                    Log.e("dsioa","外部列表往上滑");
                                    getParent().requestDisallowInterceptTouchEvent(false);
                                    if(needIntercepectListener!=null){
                                        needIntercepectListener.needIntercepect(false);
                                    }
                                }else{
                                    Log.e("dsioa","内部列表往上滑");
                                    getParent().requestDisallowInterceptTouchEvent(true);
                                    if(needIntercepectListener!=null){
                                        needIntercepectListener.needIntercepect(true);
                                    }
                                }
                                break;
                            case 't':
                                Log.e("maxY",maxY+"");
                                Log.e("maxY",location[1]+"-----<");
                                if(location[1]<=maxY){
                                    getParent().requestDisallowInterceptTouchEvent(true);//禁止父布局拦截
                                    if(needIntercepectListener!=null){
                                        needIntercepectListener.needIntercepect(true);
                                        Log.e("不要拦截","不要拦截");
                                    }
                                }else{
                                    getParent().requestDisallowInterceptTouchEvent(false);//允许父布局拦截
                                    if(needIntercepectListener!=null){
                                        needIntercepectListener.needIntercepect(false);
                                        return true;
                                    }
                                }
                                break;
                            case 'r':
                                getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                            //左右滑动交给ViewPager处理
                            case 'l':
                                getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                        }
                        break;
        }
        return super.onTouchEvent(e);
    }



    private int getOrientation(float dx, float dy) {
        if (Math.abs(dx)>Math.abs(dy)){
            //X轴移动
            return dx>0?'r':'l';//右,左
        }else{
            //Y轴移动
            return dy>0?'b':'t';//下//上
        }
    }

    public void setMaxY(int height) {
        this.maxY=height;
    }

    public interface NeedIntercepectListener{
        void needIntercepect(boolean needIntercepect);
    }
    public void setNeedIntercepectListener(NeedIntercepectListener needIntercepectListener) {
        this.needIntercepectListener = needIntercepectListener;
    }
}
