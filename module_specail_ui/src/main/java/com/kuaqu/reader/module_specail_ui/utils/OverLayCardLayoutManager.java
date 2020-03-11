package com.kuaqu.reader.module_specail_ui.utils;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

public class OverLayCardLayoutManager extends RecyclerView.LayoutManager {
    public final static int MAX_SHOW_NUM=6;
    public static float SCALE_GAP=0.05f;
    public static int TRANS_Y_GAP=45;
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        detachAndScrapAttachedViews(recycler);
        int itemCount=getItemCount();
        if(itemCount<0){
            return;
        }
        int bottomPosition;
        if(itemCount<MAX_SHOW_NUM){
            bottomPosition=0;
        }else {
            bottomPosition=itemCount-MAX_SHOW_NUM;
        }
        for(int i=bottomPosition;i<itemCount;i++){
            View view=recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view,0,0);
            int widthSpace=getWidth()-getDecoratedMeasuredWidth(view);
            int heightSpace=getHeight()-getDecoratedMeasuredHeight(view);
            layoutDecoratedWithMargins(view,widthSpace/2,heightSpace/2, widthSpace / 2 + getDecoratedMeasuredWidth(view), heightSpace / 2 + getDecoratedMeasuredHeight(view));

            int level=itemCount-i-1;
            if(level>0){//0代表最顶层
                view.setScaleX(1-SCALE_GAP*level);
                if (level < MAX_SHOW_NUM - 1) {
                    view.setTranslationY(TRANS_Y_GAP * level);
                    view.setScaleY(1 -SCALE_GAP * level);
                } else {//第N层在 向下位移和Y方向的缩小的成都与 N-1层保持一致
                    view.setTranslationY(TRANS_Y_GAP * (level - 1));
                    view.setScaleY(1 - SCALE_GAP * (level - 1));
                }
            }
        }
    }
}
