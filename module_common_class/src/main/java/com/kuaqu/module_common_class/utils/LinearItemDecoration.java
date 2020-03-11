package com.kuaqu.module_common_class.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuaqu.module_common_class.R;

public class LinearItemDecoration extends RecyclerView.ItemDecoration{
    private Paint mPaint;
    private Bitmap mMedalBmp;
    public LinearItemDecoration(Context context){
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        BitmapFactory.Options options = new BitmapFactory.Options();
        mMedalBmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.xunzhang);

    }

//Canvas c: 是指通过getItemOffsets撑开的空白区域所对应的画布，通过这个canvas对象，可以在getItemOffsets所撑出来的区域任意绘图。
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        for (int i=0;i<childCount;i++){
            View child = parent.getChildAt(i);
            //动态获取outRect的left值
            int left = manager.getLeftDecorationWidth(child);
            int cx = left/2;
            int cy = child.getTop()+child.getHeight()/2;
            c.drawCircle(cx,cy,20,mPaint);
        }


    }
   //onDrawOver 是绘制在最上层的，所以它的绘制位置并不受限制，可以绘制到outRect范围外边。
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //画勋章
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);
            int left = manager.getLeftDecorationWidth(child);
            if (index % 5 == 0) {
                c.drawBitmap(mMedalBmp, left - mMedalBmp.getWidth() / 2, child.getTop()+(child.getHeight()-mMedalBmp.getHeight())/2, mPaint);
            }
        }
        //画蒙版
        View temp = parent.getChildAt(0);
        LinearGradient gradient = new LinearGradient(parent.getWidth() / 2, 0, parent.getWidth() / 2, temp.getHeight() * 3,
                0xff0000ff, 0x000000ff, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        c.drawRect(0, 0, parent.getWidth(), temp.getHeight() * 3, mPaint);

    }

    //outRect就是表示在item的上下左右所撑开的距离
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left=200;
        outRect.bottom=1;
    }
}
