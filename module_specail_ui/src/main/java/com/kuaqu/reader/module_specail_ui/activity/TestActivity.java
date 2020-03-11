package com.kuaqu.reader.module_specail_ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.adapter.TestAdapter;
import com.kuaqu.reader.module_specail_ui.fragment.StudyFragment;
import com.kuaqu.reader.module_specail_ui.utils.TestDataScrollView;

import java.util.ArrayList;
import java.util.List;
/*
*  事件分发的两种处理机制：
*  1.scrollview嵌套recyclerview，通过onitercept来手动处理
*  2.利用嵌套滑动，处理。
* StickyNavLayout使用第一种方式
* StickyNavLayout2使用第二种方式
*
* */

public class TestActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments=new ArrayList<>();
    private String[] mTitles = new String[] { "简介", "评价", "相关" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_test);
        initView();
        initData();

    }

    private void initData() {
        for(int i=0;i<mTitles.length;i++){
            fragments.add(new StudyFragment().getInstance(i));
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initView() {
        tabLayout=findViewById(R.id.nav);
        viewPager=findViewById(R.id.viewPager);
    }
}
