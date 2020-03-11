package com.kuaqu.reader.module_specail_ui.utils;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
/*
* 假设有三个view排列 A,B,C
* A位置[-1],B位置[0],C位置[1]
* 从右向左滑动时
* B[0,-1] ,C[1,0] A消失，在各自区间，view不是同一个。
* 从左向右滑动时
* A[-1,0] B[0,1] C消失 。
*
* */
public class RotateDownPageTransformer implements ViewPager.PageTransformer {
    private float mMaxRotate = 15f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1) {//左边页滑出了左边
            page.setRotation(mMaxRotate * -1);
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight());
//            Log.e("滑动", "" + page.getTag() + "==" + position);
        } else if (position <= 1) {//中间滑到左边第一页，右边滑到中间
            if (position < 0) {//[-1,0]
                page.setPivotX(page.getWidth() * (0.5f + 0.5f * (-position)));
                page.setPivotY(page.getHeight());
                page.setRotation(mMaxRotate * position);
                Log.e("滑动", "" + page.getTag() + "==" + position+"角度"+(mMaxRotate * position));
            } else {//[0,1]
                page.setPivotX(page.getWidth() * 0.5f * (1 - position));
                page.setPivotY(page.getHeight());
                page.setRotation(mMaxRotate * position);
//                Log.e("滑动", "" + page.getTag() + "==" + position);
            }
        } else {//右边页滑出屏幕
            page.setRotation(mMaxRotate);
            page.setPivotX(page.getWidth() * 0);
            page.setPivotY(page.getHeight());
        }

    }
}
