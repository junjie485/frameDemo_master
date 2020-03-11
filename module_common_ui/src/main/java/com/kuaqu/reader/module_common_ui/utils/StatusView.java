package com.kuaqu.reader.module_common_ui.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kuaqu.reader.module_common_ui.R;

public class StatusView extends FrameLayout {

    private Context context;
    // 当前显示的 View
    private View currentView;
    // 原始内容 View
    private View contentView;

    // 状态布局文件 Id 声明
    private @LayoutRes
    int loadingLayoutId = R.layout.sv_loading_layout;
    private @LayoutRes
    int emptyLayoutId = R.layout.sv_empty_layout;
    private @LayoutRes
    int errorLayoutId = R.layout.sv_error_layout;

    // 状态布局 View 缓存集合
    private SparseArray<View> viewArray = new SparseArray<>();
    // 状态布局 View 显示时的回调接口集合
    private SparseArray<StatusViewConvertListener> listenerArray = new SparseArray<>();
    // 索引对应的状态布局 Id 集合
    private SparseIntArray layoutIdArray = new SparseIntArray();

    public StatusView(@NonNull Context context) {
        this(context,null);
    }

    public StatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
     this(context,attrs,0);
    }

    public StatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
    //Activity XML根布局View的父View其实就是一个 id 为android.R.id.content的 View
    //默认布局,会覆盖整个xml，包括标题栏
    public static StatusView init(Activity activity){
        View contentView=((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
        return init(contentView);
    }
    //覆盖根布局下指定任意一个布局模块
    public static StatusView init(Activity activity,int viewId){
        View rootView=((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
        View view=rootView.findViewById(viewId);
        return init(view);
    }

    //Fragment XML 根布局 View 的父 View 可以通过fragment.getView()方法得到
    public static StatusView init(Fragment fragment,int viewId){
        View rootView=fragment.getView();
        View contentView=null;
        if(rootView!=null){
           contentView= rootView.findViewById(viewId);
        }
        return init(contentView);
    }

    /**
     * 用 StatusView 替换要使用多状态布局的 View
     */
    private static StatusView init(View contentView) {
        if (contentView == null) {
            throw new RuntimeException("ContentView can not be null!");
        }
        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent == null) {
            throw new RuntimeException("ContentView must have a parent view!");
        }
        ViewGroup.LayoutParams lp = contentView.getLayoutParams();
        int index = parent.indexOfChild(contentView);
        parent.removeView(contentView);
        StatusView statusView = new StatusView(contentView.getContext());
        statusView.addView(contentView);
        statusView.setContentView(contentView);
        parent.addView(statusView, index, lp);
        return statusView;
    }
    /**
     * 设置 Loading 布局首次显示时的回调，可在回调中更新布局、绑定事件等
     */
    public void setOnLoadingViewConvertListener(StatusViewConvertListener listener) {
        listenerArray.put(loadingLayoutId, listener);
    }

    /**
     * 设置 Empty 布局首次显示时的回调，可在回调中更新布局、绑定事件等
     */
    public void setOnEmptyViewConvertListener(StatusViewConvertListener listener) {
        listenerArray.put(emptyLayoutId, listener);
    }

    /**
     * 设置 Error 布局首次显示时的回调，可在回调中更新布局、绑定事件等
     */
    public void setOnErrorViewConvertListener(StatusViewConvertListener listener) {
        listenerArray.put(errorLayoutId, listener);
    }

    private void setContentView(View contentView) {
        this.contentView = currentView = contentView;
    }

    public void setLoadingView(int loadingLayoutId) {
        this.loadingLayoutId = loadingLayoutId;
    }

    public void setEmptyView(int emptyLayoutId) {
        this.emptyLayoutId = emptyLayoutId;
    }

    public void setErrorView(int errorLayoutId) {
        this.errorLayoutId = errorLayoutId;
    }
    public void showLoadingView(){
        switchStatusView(loadingLayoutId);
    }
    /**
     * 显示 原始内容 布局
     */
    public void showContentView() {
        switchStatusView(contentView);
    }
    /**
     * 显示 Empty 布局
     */
    public void showEmptyView() {
        switchStatusView(emptyLayoutId);
    }

    /**
     * 显示 Error 布局
     */
    public void showErrorView() {
        switchStatusView(errorLayoutId);
    }

    private void switchStatusView(int layoutId) {
        View statusView=viewArray.get(layoutId);
        if(statusView==null){
            statusView=LayoutInflater.from(context).inflate(layoutId, null);
            viewArray.put(layoutId,statusView);
            StatusViewConvertListener listener = listenerArray.get(layoutId);
            ViewHolder viewHolder = ViewHolder.create(statusView);
            if (listener != null) {
                listener.onConvert(viewHolder);
            }
        }
        removeView(currentView);
        currentView=statusView;
        addView(currentView);
    }
    private void switchStatusView(View statusView){
        if(statusView==currentView){
            return;
        }
        removeView(currentView);
        currentView=statusView;
        addView(currentView);
    }

}
