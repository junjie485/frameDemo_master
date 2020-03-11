package com.kuaqu.reader.module_specail_ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.ImageView;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.adapter.SimpleFragmentPagerAdapter;
import com.kuaqu.reader.module_specail_ui.fragment.OneFragment;
import com.kuaqu.reader.module_specail_ui.fragment.TwoFragment;

import java.util.ArrayList;

public class EventActivity extends BaseActivity {
    private ArrayList<Fragment> list_fragment = new ArrayList<>();
    private ArrayList<String> list_title = new ArrayList<>();
    private OneFragment mOneFragment;
    private TwoFragment mTwoFragment;


    private SimpleFragmentPagerAdapter pagerAdapter;

    private ViewPager viewPager;

    private TabLayout tabLayout;
    ImageView img;
/*
* 源码解析：1.判断何时将滑动事件交由父控件处理，何时由子控件处理
* 2.父控件何时进行拦截，以及拦截后如何滚动到指定界面
* 3.如何断定滚动松手后，界面回弹还是跳转到下一界面
* */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        initControls();
    }
    /**
     * 初始化各控件
     */
    private void initControls() {
        img = (ImageView) findViewById(R.id.img);
        //初始化各fragment
        mOneFragment = new OneFragment();
        mTwoFragment = new TwoFragment();
        list_fragment.add(mOneFragment);
        list_fragment.add(mTwoFragment);
        list_title.add("第一个页面");
        list_title.add("第二个页面");


        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this, list_fragment, list_title);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
