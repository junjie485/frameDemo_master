package com.kuaqu.reader.module_my_view.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MyImageView extends ImageView {
    private List<Rect> mRects=new ArrayList<>();
    private int width,height;
    private int lastX,lastY,pos;
    private CallBack callBack;

   public  interface CallBack{
        public void onItemClick(int position);
    }
    public void setCallBack(CallBack callBack){
        this.callBack=callBack;
    }

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height=h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX= (int) event.getX();
                lastY= (int) event.getY();
                //判断触摸点是否在矩形区域内
               if(isContainerRect(lastX,lastY)){
                    callBack.onItemClick(pos);
               }else {
                   pos=0;
               }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean isContainerRect(int x, int y) {
        for(int i=0;i<mRects.size();i++){
            if(mRects.get(i).contains(x,y))  {
                pos=i;
                return true;
            }
        }
       return false;
    }

    public void setRects(List<Rect> list){
        mRects=list;
    }
}
