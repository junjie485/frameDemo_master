package com.kuaqu.module_common_class.utils;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;


/**
 * @author :created by ${yangpf}
 * 时间:2018/7/6 15
 * 邮箱：xxx@.qq.com
 */
public class IHCallback extends ItemTouchHelper.Callback {

    private IOperationData moveAdapter;//接口回调

    public IHCallback(IOperationData moveAdapter) {
        this.moveAdapter = moveAdapter;
    }

//    主要设置两个参数：dragFlags 拖拽标识。swipeFlags 滑动标识
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //允许上下的拖动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //只允许从右向左侧滑
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    //当拖拽时，会不断回调onMove（）方法。
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        Log.e("hhhhhhh","拖拽"+viewHolder.getAdapterPosition()+"===="+target.getAdapterPosition());
        moveAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }
    //当滑动完成时，会回调onSwiped（）方法。
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Log.e("hhhhhhh","侧滑"+viewHolder.getAdapterPosition());
        moveAdapter.onItemDissmiss(viewHolder.getAdapterPosition());
    }
//无论是拖拽还是滑动，都会调用该方法（监听拖拽，滑动过程）。
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        Log.e("hhhhhhh","onChildDraw："+viewHolder.getAdapterPosition());
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //滑动时改变Item的透明度
            final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
        }
    }
//    这个方法是长按事件触发时回调，这里我们把选中的条目透明度或者背景色改变掉
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }
}
