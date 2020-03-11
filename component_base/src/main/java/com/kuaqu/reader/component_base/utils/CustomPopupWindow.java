package com.kuaqu.reader.component_base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
/*
* 1.设置布局
* 2.设置宽高
* 3.设置点击区域外消失
* 4.设置透明背景
* 5.设置显示位置
* 6.将布局xml跟标签都设置满屏，内容部分进行白色填充，余下未用的部分会以new ColorDrawable(0xb0000000)透明度填充，同时监听ontouch事件
* */
public class CustomPopupWindow {
    private PopupWindow mPopupWindow;
    private static Context mContext;
    private View contentview;

    public CustomPopupWindow(Builder builder) {
        contentview = LayoutInflater.from(mContext).inflate(builder.contentviewid, null);
        mPopupWindow = new PopupWindow(contentview, builder.width, builder.height);
        mPopupWindow.setOutsideTouchable(builder.outsidecancel);
        mPopupWindow.setFocusable(builder.fouse);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//不设置背景，前两个属性就没有用。
        mPopupWindow.setAnimationStyle(builder.animstyle);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp= ((Activity) mContext).getWindow().getAttributes();
                lp.alpha=1.0f;
                ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                ((Activity) mContext).getWindow().setAttributes(lp);
            }
        });
    }

    /**
     * popup 消失
     */
    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 根据id获取view
     * * @param viewid
     * * @return
     */
    public View getItemView(int viewid) {
        if (mPopupWindow != null) {
            return this.contentview.findViewById(viewid);
        }
        return null;
    }

    /**
     * 根据父布局，显示位置
     * * @param rootviewid
     * * @param gravity
     * * @param x
     * * @param y
     * * @return
     */
    public CustomPopupWindow showAtLocation(View rootview, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(rootview, gravity, x, y);
        }
        return this;
    }

    /**
     * 根据id获取view ，并显示在该view的位置
     * * @param targetviewId
     * * @param offx
     * * @param offy
     * * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public CustomPopupWindow showAsDropDown(View targetview, int offx, int offy) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(targetview,offx, offy);
        }
        return this;
    }

    /**
     * 显示在 targetview 的不同位置
     * * @param targetview
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public CustomPopupWindow showAsDropDown(View targetview) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(targetview);
        }
        return this;
    }

    /**
     * 根据id设置焦点监听
     * * @param viewid
     * * @param listener
     */
    public void setOnFocusListener(int viewid, View.OnFocusChangeListener listener) {
        View view = getItemView(viewid);
        view.setOnFocusChangeListener(listener);
    }


    public static class Builder {
        private int contentviewid;
        private int width;
        private int height;
        private boolean fouse;
        private boolean outsidecancel;
        private int animstyle;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setContentView(int contentviewid) {
            this.contentviewid = contentviewid;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setFouse(boolean fouse) {
            this.fouse = fouse;
            return this;
        }

        public Builder setOutSideCancel(boolean outsidecancel) {
            this.outsidecancel = outsidecancel;
            return this;
        }
        public Builder setBackGroundAlpha(float level){
            WindowManager.LayoutParams params =  ((Activity) mContext).getWindow().getAttributes();
            params.alpha = level;
            ((Activity) mContext).getWindow().setAttributes(params);
            return this;
        }

        public Builder setAnimationStyle(int animstyle) {
            this.animstyle = animstyle;
            return this;
        }

        public CustomPopupWindow builder() {
            return new CustomPopupWindow(this);
        }

    }
}
