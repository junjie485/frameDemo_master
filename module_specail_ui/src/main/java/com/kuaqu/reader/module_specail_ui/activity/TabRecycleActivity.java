package com.kuaqu.reader.module_specail_ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_base.utils.ScreenUtils;
import com.kuaqu.reader.component_base.widget.ScrollLinearLayoutManager;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.adapter.TabRecyAdapter;
import com.kuaqu.reader.module_specail_ui.contract.TabRecyBean;
import com.kuaqu.reader.module_specail_ui.utils.CustomScrollView;

import java.util.ArrayList;
import java.util.List;

public class TabRecycleActivity extends BaseActivity {

    /**
     * 占位tablayout，用于滑动过程中去确定实际的tablayout的位置
     */
    private TabLayout holderTabLayout;
    /**
     * 实际操作的tablayout，
     */
    private TabLayout realTabLayout;
    private CustomScrollView scrollView;
    private RecyclerView container;
    private LinearLayout top_content_liner;
    private String[] tabTxt = {"客厅", "卧室", "餐厅", "书房", "阳台", "儿童房"};
    private TabRecyAdapter adapter;
    private List<TabRecyBean> anchorList = new ArrayList<>();

    //判读是否是scrollview主动引起的滑动，true-是，false-否，由tablayout引起的
    private boolean isScroll;
    //记录上一次位置，防止在同一内容块里滑动 重复定位到tablayout
    private int lastPos = 0;
    //监听判断最后一个模块的高度，不满一屏时让最后一个模块撑满屏幕
    private ViewTreeObserver.OnGlobalLayoutListener listener;

    ScrollLinearLayoutManager manager;
    private int topHeight;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_recycle);

        holderTabLayout = findViewById(R.id.tablayout_holder);
        realTabLayout = findViewById(R.id.tablayout_real);
        scrollView = findViewById(R.id.scrollView);
        container = findViewById(R.id.container);
        top_content_liner=findViewById(R.id.top_content_liner);
        scrollView.requestFocus();
        for (int i = 0; i < tabTxt.length; i++) {
            TabRecyBean anchorView = new TabRecyBean(tabTxt[i],tabTxt[i]);
            anchorList.add(anchorView);
        }
        for (int i = 0; i < tabTxt.length; i++) {
            holderTabLayout.addTab(holderTabLayout.newTab().setText(tabTxt[i]));
            realTabLayout.addTab(realTabLayout.newTab().setText(tabTxt[i]));
        }

        manager=new ScrollLinearLayoutManager(this);
        manager.setScrollEnable(false);
        container.setLayoutManager(manager);
        adapter=new TabRecyAdapter(R.layout.tab_recy_item,anchorList,this,lastViewHeight());
        container.setAdapter(adapter);

        listener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //一开始让实际的tablayout 移动到 占位的tablayout处，覆盖占位的tablayout
                realTabLayout.setTranslationY(holderTabLayout.getTop());
                realTabLayout.setVisibility(View.VISIBLE);
                container.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
                topHeight=top_content_liner.getMeasuredHeight();
            }
        };
        container.getViewTreeObserver().addOnGlobalLayoutListener(listener);


        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isScroll = true;
                }
                return false;
            }
        });
        //监听scrollview滑动
        scrollView.setCallbacks(new CustomScrollView.Callbacks() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {
                //根据滑动的距离y(不断变化的) 和 holderTabLayout距离父布局顶部的距离(这个距离是固定的)对比，
                //当y < holderTabLayout.getTop()时，holderTabLayout 仍在屏幕内，realTabLayout不断移动holderTabLayout.getTop()距离，覆盖holderTabLayout
                //当y > holderTabLayout.getTop()时，holderTabLayout 移出，realTabLayout不断移动y，相对的停留在顶部，看上去是静止的
                int translation = Math.max(y, holderTabLayout.getTop());
                Log.e("jssooo",y+";;;;"+ holderTabLayout.getTop()+"--"+holderTabLayout.getBottom());
                realTabLayout.setTranslationY(translation);
                if (isScroll) {
                    for (int i = tabTxt.length - 1; i >= 0; i--) {
                        //需要y减去顶部内容区域的高度(具体看项目的高度，这里demo写死的200dp)
                        if (y -topHeight>  manager.findViewByPosition(i).getTop() - 10) {
                            setScrollPos(i);
                            break;
                        }
                    }
                }

            }
        });

        //实际的tablayout的点击切换
        realTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                isScroll = false;
                int pos = tab.getPosition();
                int top =  manager.findViewByPosition(pos).getTop();
                //同样这里滑动要加上顶部内容区域的高度(这里写死的高度)
                scrollView.smoothScrollTo(0, top +topHeight);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
    private void setScrollPos(int newPos) {
        if (lastPos != newPos) {
            realTabLayout.setScrollPosition(newPos, 0, true);
        }
        lastPos = newPos;
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public int lastViewHeight(){
        int screenH = getScreenHeight();
        int statusBarH = getStatusBarHeight(TabRecycleActivity.this);
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        holderTabLayout.measure(w, h);
        int tabH = holderTabLayout.getMeasuredHeight();
        Log.e("datatatat","===="+tabH);
        int lastH = screenH - statusBarH - tabH - ScreenUtils.dp2px(TabRecycleActivity.this,16);
        return lastH;
    }

}
